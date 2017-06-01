package aub.edu.lb.common

import org.apache.spark.SparkContext
import org.apache.spark.graphx._

object Library {
  /**
   *  Constructs an undirected graph from a file having the following format
   *  x:src -> y:dst
   */
  def constructGraph(sc: SparkContext, fileName: String, numOfPartitions: Int): Graph[Int, Int] = {
    val file = sc.textFile(fileName).repartition(numOfPartitions)
    // forward sided directed edges
    val edgesFront = file.map { line =>
      val row = line.split(" ")
      Edge(row(0).toLong, row(1).toLong, 1)
    }
    // rear sided directed edges
    val edgesBack = file.map { line =>
      val row = line.split("\\s+")
      Edge(row(1).toLong, row(0).toLong, 1)
    }
    // undirected graph consists of both forward and rear edges so take their union
    val totalEdges = edgesFront.union(edgesBack)
    Graph.fromEdges(totalEdges, 1)
  }
}