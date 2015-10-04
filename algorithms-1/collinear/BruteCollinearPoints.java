import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private static final int COLLINEAR_SIZE = 3;

    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        if (points.length == 0) {
            return;
        }

        computeSegments(points);
    }

    public int numberOfSegments() {
        if (segments == null) {
            return 0;
        }
        return segments.size();
    }

    public LineSegment[] segments() {
        if (segments == null) {
            return null;
        }
        LineSegment[] array = new LineSegment[segments.size()];
        segments.toArray(array);
        return array;
    }

    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

    private void raiseExceptionIfAnyPointNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }
    }

    private void raiseExceptionIfAnyPointRepeated(Point[] points) {
        Point lastPoint  = points[0];
        for (int i = 1; i < points.length; i++) {
            Point point = points[i];
            if (point.compareTo(lastPoint) == 0) {
                throw new IllegalArgumentException();
            }
            lastPoint = point;
        }
    }

    private void computeSegments(Point[] points) {
        Point[] newPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(newPoints);
        raiseExceptionIfAnyPointNull(newPoints);
        raiseExceptionIfAnyPointRepeated(newPoints);

        segments = new ArrayList<LineSegment>();
        int length = newPoints.length - COLLINEAR_SIZE;
        for (int i = 0; i < length; i++) {
            Point p = newPoints[i];

            for (int j = i + 1; j < newPoints.length; j++) {
                Point q = newPoints[j];
                double slopePQ = p.slopeTo(q);

                for (int k = j + 1; k < newPoints.length; k++) {
                    Point r = newPoints[k];
                    double slopePR = p.slopeTo(r);

                    if (slopePQ != slopePR) {
                        continue;
                    }

                    for (int l = k + 1; l < newPoints.length; l++) {
                        Point s = newPoints[l];
                        double slopePS = p.slopeTo(s);

                        if (slopePQ != slopePS) {
                            continue;
                        }

                        segments.add(new LineSegment(p, s));
                    }
                }
            }
        }
    }

}
