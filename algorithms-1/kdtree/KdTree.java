import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {

    private class Node {

        public Point2D point;
        public Node left;
        public Node right;

        public Node(Point2D p) {
            point = p;
        }

    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }

        size++;
        root = insert(root, point, 1);
    }

    private Node insert(Node node, Point2D point, int level) {
        if (node == null) {
            return new Node(point);
        }

        if (isEven(level)) {
            if (point.y() < node.point.y()) {
                node.left = insert(node.left, point, level + 1);
            } else {
                node.right = insert(node.right, point, level + 1);
            }
        } else {
            if (point.x() < node.point.x()) {
                node.left = insert(node.left, point, level + 1);
            } else {
                node.right = insert(node.right, point, level + 1);
            }
        }

        return node;
    }

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }

        return contains(root, point, 1);
    }

    private boolean contains(Node node, Point2D point, int level) {
        if (node == null) {
            return false;
        }

        if (node.point.equals(point)) {
            return true;
        }

        if (isEven(level)) {
            if (point.y() < node.point.y()) {
                return contains(node.left, point, level + 1);
            } else {
                return contains(node.right, point, level + 1);
            }
        } else {
            if (point.x() < node.point.x()) {
                return contains(node.left, point, level + 1);
            } else {
                return contains(node.right, point, level + 1);
            }
        }
    }

    public void draw() {
        draw(root, new RectHV(0, 0, 1, 1), 1);
    }

    private void draw(Node node, RectHV rect, int level) {
        if (node == null) {
            return;
        }

        node.point.draw();

        if (isEven(level)) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), node.point.y(), rect.xmax(), node.point.y());
            StdDraw.setPenColor();
            draw(node.left, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y()), level + 1);
            draw(node.right, new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax()), level + 1);
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), rect.ymin(), node.point.x(), rect.ymax());
            StdDraw.setPenColor();
            draw(node.left, new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax()), level + 1);
            draw(node.right, new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()), level + 1);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        return range(root, rect, 1, new ArrayList<Point2D>());
    }

    private Iterable<Point2D> range(Node node, RectHV rect, int level, ArrayList<Point2D> rangePoints) {
        if (node == null) {
            return null;
        }

        if (rect.contains(node.point)) {
            rangePoints.add(node.point);
        }

        if (isEven(level)) {
            if (rect.ymin() < node.point.y()) {
                range(node.left, rect, level + 1, rangePoints);
            }
            if (rect.ymax() >= node.point.y()) {
                range(node.right, rect, level + 1, rangePoints);
            }
        } else {
            if (rect.xmin() < node.point.x()) {
                range(node.left, rect, level + 1, rangePoints);
            }
            if (rect.xmax() > node.point.x()) {
                range(node.right, rect, level + 1, rangePoints);
            }
        }

        return rangePoints;
    }

    private class QueryPoint {

        public Point2D point;
        public Point2D minPoint;
        public double minDistance;

        public QueryPoint(Point2D p) {
            point = p;
        }

        public void update(Point2D p) {
            double distance = point.distanceTo(p);
            if (minPoint == null || distance < minDistance) {
                minDistance = distance;
                minPoint = p;
            }
        }

    }

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }

        return nearest(root, new QueryPoint(point), 1).minPoint;
    }

    private QueryPoint nearest(Node node, QueryPoint queryPoint, int level) {
        if (node == null) {
            return queryPoint;
        }

        queryPoint.update(node.point);

        // check the half that contains point and other half if the rect is closer than the current min distance
        Point2D point = queryPoint.point;
        if (isEven(level)) {
            if (point.y() < node.point.y()) {
                nearest(node.left, queryPoint, level + 1);
                if (point.distanceTo(new Point2D(point.x(), node.point.y())) < queryPoint.minDistance) {
                    nearest(node.right, queryPoint, level + 1);
                }
            } else {
                nearest(node.right, queryPoint, level + 1);
                if (point.distanceTo(new Point2D(point.x(), node.point.y())) < queryPoint.minDistance) {
                    nearest(node.left, queryPoint, level + 1);
                }
            }
        } else {
            if (point.x() < node.point.x()) {
                nearest(node.left, queryPoint, level + 1);
                if (point.distanceTo(new Point2D(node.point.x(), point.y())) < queryPoint.minDistance) {
                    nearest(node.right, queryPoint, level + 1);
                }
            } else {
                nearest(node.right, queryPoint, level + 1);
                if (point.distanceTo(new Point2D(node.point.x(), point.y())) < queryPoint.minDistance) {
                    nearest(node.left, queryPoint, level + 1);
                }
            }
        }

        return queryPoint;
    }

    private boolean isEven(int n) {
        return n % 2 == 0;
    }

}
