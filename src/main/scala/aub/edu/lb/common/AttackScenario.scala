package aub.edu.lb.common
import org.apache.spark.graphx._

/**
 * An interface to oblige all scenarios to implement the cascading Attack method
 * This method takes a Graph as input and returns an array of scores
 * designating the graph loss of connectivity
 */
trait AttackScenario {

  def attack(graph: Graph[Int, Int]): Array[(VertexId,Double)]

  /**
   * It mergers two arrays based on nodes' scores (e.g., degree, betweeness centrality)
   */
  def append(a: Array[(VertexId,Double, Double)], b: Array[(VertexId,Double, Double)]): Array[(VertexId,Double, Double)] = {
    var i = 0
    var j = 0
    var k = 0
    val total = new Array[(VertexId,Double, Double)](a.length + b.length)
    while (i < a.length && j < b.length) {
      if (a(i)._2 > b(j)._2) {
        total(k) = a(i)
        i = i + 1
      } else {
        total(k) = b(j)
        j = j + 1
      }
      k = k + 1
    }
    if (i == a.length) {
      for (r <- j until b.length) {
        total(k) = b(r)
        k = k + 1
      }
    } else {
      for (r <- i until a.length) {
        total(k) = a(r)
        k = k + 1
      }
    }
    total
  }
}