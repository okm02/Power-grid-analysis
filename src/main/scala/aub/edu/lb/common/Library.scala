package aub.edu.lb.common

import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import scala.math.max
import java.io.FileWriter
import scala.io.Source

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

  /* 
   * code to generate replicated graph
   * 
   * def main(args: Array[String]) {

    val filename = "input/graph1.txt"
    val replicas = Array(2, 4, 8, 16, 32, 64)
    val graph = readFile(filename)
    for (replica <- replicas) {
      val outputFile = "input/graph" + replica + ".txt"
      val replicatedGraph = replicate(graph, replica, outputFile)
    }

  }

  def readFile(filename: String): Array[(Long, Long)] = {
    val size = 679965
    val graph = new Array[(Long, Long)](size)
    var i = 0
    for (line <- Source.fromFile(filename).getLines()) {
      val splitLine = line.split(" ").map(_.toInt)
      val src = splitLine(0)
      val dst = splitLine(1)
      graph(i) = (src, dst)
      i = i + 1
    }
    graph
  }

  def replicate(graph: Array[(Long, Long)], numOfRep: Int, outputFile: String) {

    val maxSrc = graph.maxBy(f => f._1)._1
    val maxDst = graph.maxBy(f => f._2)._2
    val maxBoth = max(maxSrc, maxDst) + 1
    for (currentReplica <- 0 until numOfRep) {
      val localMax = maxBoth * currentReplica
      val rep = graph.map(f => (f._1 + localMax, f._2 + localMax))
      printResult(rep, outputFile)
    }
  }

  def printResult(result: Array[(Long, Long)], outputFile: String) {

    val printStream = new FileWriter(outputFile, true)
    for (i <- 0 until result.size) {
      printStream.write(result(i)._1 + " " + result(i)._2 + "\n")
    }
    printStream.close()
  }*/

}