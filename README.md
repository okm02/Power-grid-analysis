# Power Grid Analysis

Contingency analysis is a security function to assess the ability of a power grid to sustain various combinations of power grid component failures at energy control centers.
Our project tends to explore the strength of the Lebanese Power Grid in substaining failiures.
The power grid is a sparse graph.
We implemented 4 scenarios in which we exploit the network and these are : Cascading attack,one time based Betweenness Attack,one time based
Vertex Degree Attack and random attack.
The difference between all scenarios is to measure the precision while removing in different order and metric.

## The Algorithm

1) Run SCC on graph
2) Construct RDD having (SCCID -> (Vertex Set in this SCC,Edge Set in this SCC)
3) run one scenario on all SCC's in parrallel by calling map 
   each map returns a SCC local removal loss
4) reduce the RDD by ordering the arrays based on the metric used
5) Compute the loss on the global graph scale	

Cascading scenario: we compute each time Betweenness Centrality of the vertices of the graph and we remove the vertex with the highest BC score.

BC scenario: we compute Betweenness Centrality of the vertices of the graph once and we remove the vertices in descending BC  score order.

Degree scenario: we compute the degree of the vertices of the graph once and we remove the vertices in descending degree order.

Random scenario : vertices are removed in random order.

To compute the loss on the local SCC scale : we run SCC before removing a vertex and after removing the vertex. Each SCC returns a integer 
indicating the total number of vertices that can reach each other in the SCC.The difference of both indicates the loss.
As an example : assume the simple undirected graph (A->B , A->C , B->C) before removing vertex A the total connectivity is equal to 9( 3 vertices reachable to each other) , removing A would result (B->C) thus total connectivity is equal to 2.Hence the effect of removing 
vertex A is equal to (9-2) = 7.

To compute loss on the whole graph scale, aggregate each pair of scores sequentially then diide all of them by the initial connectivity of
graph. 

## Prerequisites
```
Scala IDE
Hadoop
```

## Running the tests
Right click the driver in aub.edu.lb.App package and select run as Scala application. The algorithm is designed to benchmark all
scenarios each with different number of threads and partitions. Results are saved in folder called bench.


## Authors
See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
