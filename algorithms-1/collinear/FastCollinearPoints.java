import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private static final int COLLINEAR_SIZE = 3;

    private ArrayList<LineSegment> segments;
    private ArrayList<Point> segmentStartPoints;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }

        raiseExceptionIfAnyPointNull(points);
        raiseExceptionIfAnyPointRepeated(points);

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
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
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void computeSegments(Point[] points) {
        segments = new ArrayList<LineSegment>();
        segmentStartPoints = new ArrayList<Point>();

        int length = points.length - COLLINEAR_SIZE;
        for (int i = 0; i < length; i++) {
            Point p = points[i];
            Arrays.sort(points, i + 1, points.length, p.slopeOrder());

            ArrayList<Point> collinearPoints = new ArrayList<Point>();
            collinearPoints.add(p);
            collinearPoints.add(points[i + 1]);
            double slopePX = p.slopeTo(points[i + 1]);
            for (int j = i + 2; j < points.length; j++) {
                Point q = points[j];

                double slopePQ = p.slopeTo(q);
                if (slopePQ == slopePX) {
                    collinearPoints.add(q);
                } else {
                    if (collinearPoints.size() > COLLINEAR_SIZE) {
                        addLineSegment(collinearPoints);
                    }

                    slopePX = slopePQ;
                    collinearPoints.clear();
                    collinearPoints.add(p);
                    collinearPoints.add(q);
                }
            }

            if (collinearPoints.size() > COLLINEAR_SIZE) {
                addLineSegment(collinearPoints);
            }
        }
    }

    private void addLineSegment(ArrayList<Point> points) {
        Collections.sort(points);
        LineSegment segment = new LineSegment(points.get(0), points.get(points.size() - 1));
        if (!hasSegmentStartPoint(points.get(0))) {
            segments.add(segment);
            segmentStartPoints.add(points.get(0));
        }
    }

    private boolean hasSegmentStartPoint(Point p) {
        for (int i = 0; i < segmentStartPoints.size(); i++) {
            if (segmentStartPoints.get(i).compareTo(p) == 0) {
                return true;
            }
        }
        return false;
    }

}
