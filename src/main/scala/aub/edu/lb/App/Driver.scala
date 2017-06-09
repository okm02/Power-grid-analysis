package aub.edu.lb.App

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import java.io._
import aub.edu.lb.common.AttackScenario
import aub.edu.lb.common.Library._
import aub.edu.lb.attack.scenarios._

object Driver {

  /**
   * Main that automates the benchmarks by runing different scenarios
   * on multiple threads and multiple rdd partitions
   */
  def main(args: Array[String]) {
    val threads = Array(64, 32, 16, 8, 4, 2, 1)
    val inputFileGraph = "input/graph.txt"
    val attackScenarios = Array(AttackDegree, AttackRandom, AttackBC, AttackCascading)
    for (attackScenario <- attackScenarios) {
      val ps = new PrintWriter(new File("bench/benchmarks" + attackScenario.toString + ".txt"))
      for (thread <- threads) {
        ps.write("Number of Threads " + thread + "\n")
        val conf = new SparkConf().setAppName("Attack").setMaster("local[" + thread + "]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        println("Thread: " + thread)

        for (partitions <- 4 to 32 by 2) {
          val time = bench(sc, attackScenario, partitions, inputFileGraph, null, false)
          ps.write(partitions + " " + time + "\n")
          println("done partitions " + partitions + " -- " + time)
        }
        sc.stop()
      }
      ps.close()
    }
  }

  /**
   * @ sc : spark context , used to construct graph with specified number of partitions
   * @ attackScenario : an object denoting which scenario to run
   * @ nbPartitions : number of partitions to split the graph
   * @ inputFileGraph : denotes the input text file
   * @ outputFile : a text file to write the end results of any scenario
   * @ debug : boolean to indicate weather to write results to folder or not
   */
  def bench(sc: SparkContext, attackScenario: AttackScenario,
            nbPartitions: Int,
            inputFileGraph: String, outputFile: String, debug: Boolean) = {
    val graph = constructGraph(sc, inputFileGraph, nbPartitions)
    val t0 = System.currentTimeMillis()
    val scores = attackScenario.attack(graph)
    val t1 = System.currentTimeMillis()
    val totalTime = (t1 - t0) / 1000.0

    if (debug) {
      val ps = new PrintWriter(new File(outputFile))
      for (i <- 0 until scores.length) {
        ps.write(scores(i) + "\n")
      }
      ps.close()
    }
    totalTime
  }

}
