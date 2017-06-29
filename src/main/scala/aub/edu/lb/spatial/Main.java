package aub.edu.lb.spatial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	static NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

	/**
	 * The file gridwithLatLong.csv is not published for security/privacy reasons.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws FileNotFoundException, ParseException {
		computeCorrelation("bench/LossCascading.txt", "bench/gridwithLatLon.csv", "bench/correlationLossCasCading.csv");
		computeCorrelation("bench/LossDegree.txt", "bench/gridwithLatLon.csv", "bench/correlationLossDegree.csv");
	}

	public static void computeCorrelation(String fileLoss, String fileGraph, String fileOutput)
			throws FileNotFoundException, ParseException {
		Scanner inputId = new Scanner(new File(fileLoss));
		Scanner inputPoints = new Scanner(new File(fileGraph));

		Map<Long, Double> mapIdLoss = readIdLoss(inputId);
		Set<Point> points = readLatLong(inputPoints);
		points = setLosses(mapIdLoss, points);
		Map<Integer, Integer> correlation = new TreeMap<Integer, Integer>();
		HashSet<Point> visitedPoints = new HashSet<Point>();
		int i = 1;
		for (Point p1 : points) {
			i++;
			if (i % 1000 == 0) System.out.println(i); // for benchmarks

			TreeSet<Line> lines = new TreeSet<Line>();
			for (Point p2 : points) {
				if (!visitedPoints.contains(p2)) {
					lines.add(new Line(p1, p2));
				}
			}
			visitedPoints.add(p1);

			for (int r = 0; r <= 200000; r += 5000) {
				Line min = new Line(new Point(0, 0, 0), new Point(r, 0, 0));
				Line max = new Line(new Point(0, 0, 0), new Point(r + 1, 0, 0));
				int size = lines.subSet(min, true, max, false).size();
				if (!correlation.containsKey(r)) {
					correlation.put(r, 0);
				}
				correlation.put(r, correlation.get(r) + size);
			}
		}

		PrintStream outputCorr = new PrintStream(new File(fileOutput));
		outputCorr.println("Id,Corr,CummCorr");
		int globalCounter = 0;
		for (Map.Entry<Integer, Integer> entry : correlation.entrySet()) {
			int radius = entry.getKey();
			int counter = entry.getValue();
			globalCounter += counter;
			outputCorr.println(radius + "," + counter + "," + globalCounter);
		}
	}

	private static Map<Long, Double> readIdLoss(Scanner inputId) {
		Map<Long, Double> mapIdLoss = new HashMap<Long, Double>();
		while (inputId.hasNextLine()) {
			String line = inputId.nextLine();
			String[] values = line.split(",");
			mapIdLoss.put(Long.parseLong(values[0].substring(1)),
					Double.parseDouble(values[1].substring(0, values[1].length() - 1)));

		}
		return mapIdLoss;
	}

	private static Set<Point> readLatLong(Scanner inputLatLong) throws ParseException {
		Set<Point> points = new HashSet<Point>();

		inputLatLong.nextLine();
		while (inputLatLong.hasNextLine()) {
			String line = inputLatLong.nextLine();
			String[] values = line.split(";");
			Long id = (long) Double.parseDouble(values[1]);
			Double lat = format.parse(values[4]).doubleValue();
			Double longt = format.parse(values[5]).doubleValue();
			points.add(new Point(lat, longt, id));
		}
		return points;
	}

	private static Set<Point> setLosses(Map<Long, Double> mapIdLoss, Set<Point> points) {
		Set<Point> pointsSorted = new TreeSet<Point>();
		for (Point point : points) {
			if (mapIdLoss.containsKey(point.getId())) {
				point.setLoss(mapIdLoss.get(point.getId()));
				pointsSorted.add(point);
			}
		}
		return pointsSorted;
	}
	
}
