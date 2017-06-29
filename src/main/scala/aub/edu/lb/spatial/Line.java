package aub.edu.lb.spatial;

public class Line implements Comparable<Line> {
	public Point p1, p2;
	public double distance = 0;

	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		distance = p1.distance(p2);
	}

	public double distance() {
		return p1.distance(p2);
	}

	@Override
	public int compareTo(Line o) {
		if (distance < o.distance)
			return -1;
		if (distance > o.distance)
			return 1;
		if (p1.compareTo(o.p1) != 0)
			return p1.compareTo(o.p1);
		if (p2.compareTo(o.p2) != 0)
			return p1.compareTo(o.p1);
		return 0;
	}
}
