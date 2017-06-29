package aub.edu.lb.spatial;

public class Point implements Comparable<Point> {
	private double x;
	private double y;

	private long id;
	private double loss;

	public Point(double x, double y, long id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			return p.x == x && p.y == y && p.id == id;
		}
		return false;
	}

	public double distance(Point p) {
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
	}

	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}
	public long getId() {
		return id;
	}

	public double getLoss() {
		return loss;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public int compareTo(Point p) {
		if (loss < p.loss)
			return -1;
		if (loss > p.loss)
			return 1;
		if (id < p.id)
			return -1;
		if (id > p.id)
			return 1;
		return 0;
	}
}
