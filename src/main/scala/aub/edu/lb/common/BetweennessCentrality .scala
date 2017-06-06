package aub.edu.lb.common
import org.apache.spark.graphx.VertexId
import scala.collection.mutable.HashSet
import scala.collection.mutable.HashMap
import scala.collection.mutable.Stack
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer

object BetweennessCentrality {

  
  /** Implementation of Betweenneess Centrality 
   *  takes as input a set of vertices and edges (belonging to one SCC)
   * returns a map having each vertex flagged with it's BC score
   */
  def computeScores(vertices: HashSet[VertexId], edges: HashSet[(VertexId, VertexId)]): HashMap[VertexId, Double] = {

    val scores = new HashMap[VertexId, Double]()
    for (vertex <- vertices) {
      scores.put(vertex, 0.0)
    }

    for (source <- vertices) {
      val stack = Stack[VertexId]()
      val queue = Queue[VertexId]()

      /**
       * data structures initializations
       */
      val dist = new HashMap[VertexId, Int]()
      val sigma = new HashMap[VertexId, Double]()
      val delta = new HashMap[VertexId, Double]()
      val predecessors = new HashMap[VertexId, ListBuffer[VertexId]]()

      for (vertex <- vertices) {
        if (vertex == source) {
          dist.put(vertex, 0)
          sigma.put(vertex, 1.0)
        } else {
          dist.put(vertex, -1)
          sigma.put(vertex, 0.0)
        }
        predecessors.put(vertex, ListBuffer[VertexId]())
        delta.put(vertex, 0.0)
      }

      /**
       * process the graph in a top-down fashion by runing a BFS
       */
      queue.enqueue(source)
      while (!queue.isEmpty) {

        val tempSource = queue.dequeue()
        stack.push(tempSource)

        val edgesOfSource = edges.filter(p => p._1 == tempSource)
        if (!edgesOfSource.isEmpty) {
          for ((src, dst) <- edgesOfSource) {
            if (dist(dst) < 0) {
              queue.enqueue(dst)
              dist(dst) = (dist(src) + 1)
            }
            if (dist(dst) == (dist(src) + 1)) {
              sigma(dst) = sigma(src) + sigma(dst)
              predecessors(dst).+=(src)
            }
          }

        }

      }

      /**
       * process the graph in a bottom-up manner and as you climb compute the 
       * partial scores of each vertex
       */
      while (!stack.isEmpty) {
        val tempSource = stack.pop()
        if (!predecessors(tempSource).isEmpty) {
          for (dst <- predecessors(tempSource)) {
            delta(dst) = delta(dst) + (sigma(dst) / sigma(tempSource)) * (1.0 + delta(tempSource))
          }
        }
        if (tempSource != source) {
          scores(tempSource) = scores(tempSource) + delta(tempSource)
        }
      }
    }

    scores
  }

}