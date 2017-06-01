package aub.edu.lb.attack.scenarios

import org.apache.spark.SparkContext._
import org.apache.spark._
import org.apache.spark.graphx._
import scala.collection.mutable.HashSet
import aub.edu.lb.common._
import java.io._
import org.apache.spark.graphx.Graph.graphToGraphOps
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

object AttackCascading extends AttackScenario {

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

    val exploitNetwork = compGraphStruct.map(f => BCBasedRemoval(f._2._1, f._2._2))
    val removalScores = exploitNetwork.reduce((a, b) => append(a, b))

    var scores = removalScores.map(f => f._2)
    for (i <- 1 until scores.length) {
      scores(i) = scores(i) + scores(i - 1)
    }
    scores = scores.map(f => (f / totalGraphConn.toDouble) * 100.0)
    scores
  }

  private def BCBasedRemoval(compVertex: HashSet[VertexId], compEdges: HashSet[(VertexId, VertexId)]): Array[(Double, Double)] = {

    val testA = compVertex.clone()
    val testB = compEdges.clone()
    var componentConnectivity = (compVertex.size * compVertex.size).toDouble

    val attacks = new Array[(Double, Double)](compVertex.size)

    for (i <- 0 until attacks.length) {

      val scoresMap = BetweennessCentrality.computeScores(testA, testB)
      val tuple = scoresMap.maxBy(_._2)
      val maxId = tuple._1
      testA -= maxId
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

  override def toString = "Cascading"

}

