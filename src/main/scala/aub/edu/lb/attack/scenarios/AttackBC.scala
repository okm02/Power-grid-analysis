package aub.edu.lb.attack.scenarios

import org.apache.spark.SparkContext._
import org.apache.spark._
import org.apache.spark.graphx._
import scala.collection.mutable.HashSet
import aub.edu.lb.common._
import java.io._
import org.apache.spark.graphx.Graph.graphToGraphOps
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

object AttackBC extends AttackScenario {

  /**
   * A method that :
   * 1) constructs an rdd mapping each SCC Id to a set of vertices and edges belonging to this component
   * 2) runs BCremoval an all SCC in parrallel
   * 3) orders the returned results based on BC score
   * 4) compute loss in the whole graph
   */
  def attack(graph: Graph[Int, Int]): Array[Double] = {

    val connectedComp = graph.connectedComponents()
    var changeStructureV = connectedComp.vertices.map(f => (f._2, {
      val set = new HashSet[VertexId]()
      set.add(f._1)
      set
    }))
    changeStructureV = changeStructureV.reduceByKey((a, b) => a.union(b))

    var changeStructureE = connectedComp.triplets.map(f => (f.srcAttr, {
      val set = new HashSet[(VertexId, VertexId)]()
      set.add((f.srcId, f.dstId))
      set
    }))
    changeStructureE = changeStructureE.reduceByKey((a, b) => a.union(b))

    val componentConnectivityRDD = changeStructureV.map(f => (f._1, (f._2.size * f._2.size)))
    val totalGraphConn = componentConnectivityRDD.map(a => a._2).reduce((a, b) => a + b)

    val compGraphStruct = changeStructureV.join(changeStructureE)

    val exploitNetwork = compGraphStruct.map(f => BCBasedRemoval(f._1, f._2._1, f._2._2))

    val removalScores = exploitNetwork.reduce((a, b) => append(a, b))

    var scores = removalScores.map(f => f._2)
    for (i <- 1 until scores.length) {
      scores(i) = scores(i) + scores(i - 1)
    }
    scores = scores.map(f => (f / totalGraphConn.toDouble) * 100.0)

    scores
  }

  /**
   * A method that :
   * 1) computes Betweenness Centrality of the vertices of the SCC once
   * 2) removes one vertex at a time in descending BC scores
   * 3) runs SCC to compute the current connectivity of the component
   * 4) computes the effect of the vertex by subtracting the connectivity before and after removing the vertex
   */
  private def BCBasedRemoval(compId: VertexId, compVertex: HashSet[VertexId], compEdges: HashSet[(VertexId, VertexId)]): Array[(Double, Double)] = {

    val testA = compVertex.clone()
    val testB = compEdges.clone()
    var componentConnectivity = (compVertex.size * compVertex.size).toDouble

    val attacks = new Array[(Double, Double)](compVertex.size)
    val scoresMap = BetweennessCentrality.computeScores(testA, testB)

    for (i <- 0 until attacks.length) {

      val tuple = scoresMap.maxBy(f => f._2)
      val maxId = tuple._1
      testA -= maxId
      scoresMap -= maxId
      val uniqueEdges = testB.filter(p => (p._1 == maxId | p._2 == maxId))
      for ((k, v) <- uniqueEdges) {
        testB -= ((k, v))
      }

      val removed = SCC.computeComponents(testA, testB)

      val loss = (componentConnectivity - removed.toDouble)
      componentConnectivity = removed.toDouble
      attacks(i) = (tuple._2, loss)
    }

    attacks
  }

  override def toString = "BC"
}

