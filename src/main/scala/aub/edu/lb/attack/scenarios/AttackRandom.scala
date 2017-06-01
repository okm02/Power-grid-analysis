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

    val exploitNetwork = compGraphStruct.map(f => degreeBasedRemoval(f._2._1, f._2._2))
    var removalScores = exploitNetwork.reduce((a, b) => a ++ b)
    val random = new Random
    removalScores = (random.shuffle(removalScores.toList)).toArray

    for (i <- 1 until removalScores.length) {
      removalScores(i) = removalScores(i) + removalScores(i - 1)
    }
    removalScores = removalScores.map(f => (f / totalGraphConn.toDouble) * 100.0)

    removalScores

  }

  def degreeBasedRemoval(compVertex: HashSet[VertexId], compEdges: HashSet[(VertexId, VertexId)]): Array[Double] = {

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