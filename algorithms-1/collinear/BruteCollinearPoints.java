import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private static final int COLLINEAR_SIZE = 3;

    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
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

        int length = points.length - COLLINEAR_SIZE;
        for (int i = 0; i < length; i++) {
            Point p = points[i];

            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                double slopePQ = p.slopeTo(q);

                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    double slopePR = p.slopeTo(r);

                    if (slopePQ != slopePR) {
                        continue;
                    }

                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        double slopePS = p.slopeTo(s);

                        if (slopePQ != slopePS) {
                            continue;
                        }

                        segments.add(createLineSegment(p, q, r, s));
                    }
                }
            }
        }
    }

    private LineSegment createLineSegment(Point p, Point q, Point r, Point s) {
        Point[] points = (Point[]) new Point[4];
        points[0] = p;
        points[1] = q;
        points[2] = r;
        points[3] = s;
        Arrays.sort(points);
        return new LineSegment(points[0], points[3]);
    }

}
