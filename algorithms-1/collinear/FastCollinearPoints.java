import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class FastCollinearPoints {

    private static final int COLLINEAR_SIZE = 3;

    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        if (points.length == 0) {
            return;
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
        HashMap<Double, ArrayList<Point>> segmentSlopes = new HashMap<Double, ArrayList<Point>>();
        int length = newPoints.length - COLLINEAR_SIZE;
        for (int i = 0; i < length; i++) {
            Point p = newPoints[i];
            Arrays.sort(newPoints, i + 1, newPoints.length, p.slopeOrder());

            Point minp = p;
            Point maxp = p;
            int slopeEqualCount = 0;
            double slopePX = Double.NEGATIVE_INFINITY;
            ArrayList<Point> collinearPoints = new ArrayList<Point>();
            for (int j = i + 1; j < newPoints.length; j++) {
                Point q = newPoints[j];
                double slopePQ = p.slopeTo(q);
                if (slopePQ == slopePX) {
                    minp = minPoint(minp, q);
                    maxp = maxPoint(maxp, q);
                    slopeEqualCount++;
                    collinearPoints.add(q);
                } else {
                    if (slopeEqualCount >= COLLINEAR_SIZE) {
                        addLineSegment(segmentSlopes, slopePX, minp, maxp, collinearPoints);
                    }

                    minp = minPoint(p, q);
                    maxp = maxPoint(p, q);
                    slopeEqualCount = 1;
                    slopePX = slopePQ;
                    collinearPoints.clear();
                    collinearPoints.add(p);
                    collinearPoints.add(q);
                }
            }

            if (slopeEqualCount >= COLLINEAR_SIZE) {
                addLineSegment(segmentSlopes, slopePX, minp, maxp, collinearPoints);
            }
        }
    }

    private void addLineSegment(HashMap<Double, ArrayList<Point>> segmentSlopes, Double slope, Point minp, Point maxp, ArrayList<Point> collinearPoints) {
        if (segmentSlopes.get(slope) != null) {
            ArrayList<Point> points = segmentSlopes.get(slope);
            if (points.contains(minp) || points.contains(maxp)) {
                return;
            } else {
                points.addAll(collinearPoints);
            }
        } else {
            ArrayList<Point> points = new ArrayList<Point>();
            points.addAll(collinearPoints);
            segmentSlopes.put(slope, points);
        }
        segments.add(new LineSegment(minp, maxp));
    }

    private Point minPoint(Point p, Point q) {
        if (p.compareTo(q) < 0) {
            return p;
        }
        return q;
    }

    private Point maxPoint(Point p, Point q) {
        if (p.compareTo(q) > 0) {
            return p;
        }
        return q;
    }

}
