package aub.edu.lb.spatial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BenchmarkCommon {
	
	public static void buildGraph() throws FileNotFoundException {
		Scanner inputGraph = new Scanner(new File("input/graph1.txt"));
		
		// build graph
		Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
		while(inputGraph.hasNextInt()) {
			int from = inputGraph.nextInt();
			int to = inputGraph.nextInt();
			if(!graph.containsKey(from)) graph.put(from, new ArrayList<Integer>());
			if(!graph.containsKey(to)) graph.put(to, new ArrayList<Integer>());
			graph.get(from).add(to);
			graph.get(to).add(from);
		}
		
		// counter number of nodes for each degree
		Map<Integer, Integer> mapDegreeCounter = new TreeMap<Integer, Integer>();
		for(Map.Entry<Integer, List<Integer>> entry: graph.entrySet()) {
			int degree = entry.getValue().size();
			if(!mapDegreeCounter.containsKey(degree)) mapDegreeCounter.put(degree, 0);
			mapDegreeCounter.put(degree, mapDegreeCounter.get(degree) + 1);
		}
		
		
		// output bench
		PrintStream output = new PrintStream(new File("bench/outputDegreeCounter.txt"));
		output.println("# degree count");
		for(Map.Entry<Integer, Integer> entry: mapDegreeCounter.entrySet()) {
			output.println(entry.getKey() + " " + entry.getValue());
		}
		inputGraph.close();
	}
}
