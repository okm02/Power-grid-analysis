package aub.edu.lb.attack.scenarios

import org.apache.spark.SparkContext._
import org.apache.spark._
import org.apache.spark.graphx._
import scala.collection.mutable.HashSet
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import aub.edu.lb.common._
import org.apache.spark.graphx.Graph.graphToGraphOps
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

object AttackRandom extends AttackScenario {

  /**
   * A method that :
   * 1) constructs an rdd mapping each SCC Id to a set of vertices and edges belonging to this component
   * 2) runs RandomBasedremoval an all SCC in parrallel
   * 3) randomly shuffles the returned results
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
    val connMap = componentConnectivityRDD.collect()
    val lossMap = collection.mutable.HashMap(connMap: _*)
    val totalGraphConn = componentConnectivityRDD.map(a => a._2).reduce((a, b) => a + b)

    val compGraphStruct = changeStructureV.join(changeStructureE)

    val exploitNetwork = compGraphStruct.map(f => RandomBasedRemoval(f._2._1, f._2._2))
    var removalScores = exploitNetwork.reduce((a, b) => a ++ b)
    val random = new Random
    removalScores = (random.shuffle(removalScores.toList)).toArray

    for (i <- 1 until removalScores.length) {
      removalScores(i) = removalScores(i) + removalScores(i - 1)
    }
    removalScores = removalScores.map(f => (f / totalGraphConn.toDouble) * 100.0)

    removalScores

  }

  /**
   * A method that :
   * 1) randomly chooses any vertex in the SCC
   * 2) removes the chosen vertex
   * 3) runs SCC to compute the current connectivity of the component
   * 4) computes the effect of the vertex by subtracting the connectivity before and after removing the vertex
   */
  def RandomBasedRemoval(compVertex: HashSet[VertexId], compEdges: HashSet[(VertexId, VertexId)]): Array[Double] = {

    val testA = compVertex.clone()
    val testB = compEdges.clone()
    var componentConnectivity = (compVertex.size * compVertex.size).toDouble
    val temp = testA.toArray
    val vertices = temp.to[ArrayBuffer]

    val attacks = new Array[Double](compVertex.size)
    val random = new Random
    for (i <- 0 until attacks.length) {

      val randomIndex = random.nextInt(vertices.size)
      val maxId = vertices(randomIndex)

      testA -= maxId
      vertices.remove(randomIndex)
      val uniqueEdges = testB.filter(p => (p._1 == maxId | p._2 == maxId))
      for ((k, v) <- uniqueEdges) {
        testB -= ((k, v))
      }

      val removed = SCC.computeComponents(testA, testB)
      val loss = (componentConnectivity - removed.toDouble)
      componentConnectivity = removed.toDouble
      attacks(i) = loss

    }
    attacks
  }

  override def toString = "Random"

}