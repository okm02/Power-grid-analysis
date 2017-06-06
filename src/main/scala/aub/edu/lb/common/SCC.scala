package aub.edu.lb.common
import org.apache.spark.graphx.VertexId
import scala.collection.mutable.HashSet
import scala.collection.mutable.Stack

/*
 * This object is responsible to compute the connectivity of the graph
 * by finding the size of each connected component in the graph
 */

object SCC {

  /**
   * a method that computes the connectivity ( how many nodes can reach each other)
   * of a graph by running SCC
   *
   */
  def computeComponents(vertices: HashSet[VertexId], edges: HashSet[(VertexId, VertexId)]): Int = {

    if (!vertices.isEmpty) {
      val visited = new HashSet[VertexId]() // store visited vertices

      def DFS(source: VertexId): Int = {

        // put the source in the stack
        val s = new Stack[VertexId]()
        s.push(source)

        var count = 0 // a counter to count the number of vertices within one SCC

        while (!s.isEmpty) {
          val temp = s.pop() // remove vertex
          if (!visited.contains(temp)) { // if this vertex has not been visited before
            visited += temp
            val subedges = edges.filter(p => p._1 == temp) // find all edges comming out from this vertex
            if (!subedges.isEmpty) { // a check used since one component might have only one vertex
              for ((src, dst) <- subedges) { // iterate over edges
                if (!visited.contains(dst)) { // if destination is not visited hence it belongs to this component
                  count = count + 1
                  s.push(dst)
                }
              }
            }
          }
        }

        count
      }

      var total = 0
      for (v <- vertices) {
        // if the vertex still isn't visited then it still doesn't belong to any component hence we need to start
        //  a DFS from this source
        if (!visited.contains(v)) {
          val score = DFS(v) + 1 // add one to include the source vertex
          // here the score is squared because the conncectivity is from each source
          // we need to have a total since one component might be split into many others
          total = total + (score * score)
        }
      }
      total
    } else {
      // we removed the last component in the graph
      // so the connectivity of this compnent is equal to zero
      0
    }

  }

}