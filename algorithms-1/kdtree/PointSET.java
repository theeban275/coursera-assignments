import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }
        points.add(point);
    }

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }
       return  points.contains(point);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        ArrayList<Point2D> rangePoints = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                rangePoints.add(p);
            }
        }
        return rangePoints;
    }

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }
        double minDistance  = Double.MAX_VALUE;
        Point2D minPoint = null;
        for (Point2D p : points) {
            double distance = point.distanceTo(p);
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = p;
            }
        }
        return minPoint;
    }

}
