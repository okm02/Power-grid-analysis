package aub.edu.lb.attack.scenarios
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.graphx.EdgeDirection
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import scala.collection.mutable.HashSet
import scala.collection.mutable.HashMap
import scala.collection.mutable.Stack
import aub.edu.lb.common._

/**
 * Compute the degree once, then remove vertices in decreasing order w.r.t. the degree of the vertices
 */
object AttackDegree extends AttackScenario {

  def attack(graph: Graph[Int, Int]): Array[Double] = {

    val connectedComp = graph.connectedComponents() // compute scc for graph
    /*
     * here we construct an rdd having
     * componentId -> all vertices belonging to this scc
     */
    var changeStructureV = connectedComp.vertices.map(f => (f._2, {
      val set = new HashSet[VertexId]()
      set.add(f._1)
      set
    }))
    changeStructureV = changeStructureV.reduceByKey((a, b) => a.union(b))

    /*
     * Construct an rdd having
     * componentId -> all edges belonging to this scc
     */
    var changeStructureE = connectedComp.triplets.map(f => (f.srcAttr, {
      val set = new HashSet[(VertexId, VertexId)]()
      set.add((f.srcId, f.dstId))
      set
    }))
    changeStructureE = changeStructureE.reduceByKey((a, b) => a.union(b))

    val componentConnectivityRDD = changeStructureV.map(f => (f._1, (f._2.size * f._2.size)))
    val connMap = componentConnectivityRDD.collect()
    val lossMap = collection.mutable.HashMap(connMap: _*)
    val totalGraphConn = componentConnectivityRDD.map(a => a._2).reduce((a, b) => a + b)

    val compGraphStruct = changeStructureV.join(changeStructureE)

    val exploitNetwork = compGraphStruct.map(f => degreeBasedRemoval(f._2._1, f._2._2))
    var removalScores = exploitNetwork.reduce((a, b) => append(a, b))

    var scores = removalScores.map(f => f._2)
    for (i <- 1 until scores.length) {
      scores(i) = scores(i) + scores(i - 1)
    }
    scores = scores.map(f => (f / totalGraphConn.toDouble) * 100.0)

    scores
  }

  def degreeBasedRemoval(compVertex: HashSet[VertexId], compEdges: HashSet[(VertexId, VertexId)]): Array[(Double, Double)] = {

    val testA = compVertex.clone()
    val testB = compEdges.clone()

    var componentConnectivity = (compVertex.size * compVertex.size).toDouble

    var degrees = new Array[(VertexId, Int)](compVertex.size)
    var i = 0
    for (vertex <- compVertex) {
      val edges = compEdges.filter(f => f._1 == vertex || f._2 == vertex)
      degrees(i) = (vertex, edges.size)
      i = i + 1
    }
    degrees = degrees.sortBy(f => f._2).reverse

    val attacks = new Array[(Double, Double)](compVertex.size)
    for (i <- 0 until degrees.length) {

      val maxId = degrees(i)._1
      testA -= maxId
      val uniqueEdges = testB.filter(p => (p._1 == maxId | p._2 == maxId))
      for ((k, v) <- uniqueEdges) {
        testB -= ((k, v))
      }

      val removed = SCC.computeComponents(testA, testB)

      val loss = (componentConnectivity - removed.toDouble)
      componentConnectivity = removed.toDouble
      attacks(i) = (degrees(i)._2, loss)
    }
    attacks
  }

  override def toString = "Degree"

}