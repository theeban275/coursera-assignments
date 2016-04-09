import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.Arrays;

public class SeamCarver {

    private static abstract class SeamPath {

        private final SeamCarver seamCarver;

        boolean[] marked;
        int[] edgeTo;
        double[] distTo;
        IndexMinPQ<Double> pq;

        SeamPath(SeamCarver seamCarver) {
            this.seamCarver = seamCarver;

            int n = seamCarver.width() * seamCarver.height();

            this.marked = new boolean[n];
            this.edgeTo = new int[n];
            this.distTo = new double[n];

            Arrays.fill(distTo, Double.POSITIVE_INFINITY);

            pq = new IndexMinPQ<>(n);
            initPQ();
            while (!pq.isEmpty()) {
                int minVertex = pq.delMin();
                visit(minVertex);
            }
        }

        abstract void initPQ();

        abstract void visit(int vertex);

        abstract int[] path();

        public void relax(int fromCol, int fromRow, int toCol, int toRow) {
            if (toRow >= height() || toCol >= width())
                return;

            int from = fromCol + fromRow * width();
            int to = toCol + toRow * width();
            double energy = energy(toCol, toRow);

            if (Double.compare(distTo[to], distTo[from] + energy) > 0) {
                distTo[to] = distTo[from] + energy;
                edgeTo[to] = from;
                if (pq.contains(to)) {
                    pq.decreaseKey(to, distTo[to]);
                } else {
                    pq.insert(to, distTo[to]);
                }
            }
        }

        double energy(int col, int row) {
            return seamCarver.energy(col, row);
        }

        int width() {
            return seamCarver.width();
        }

        int height() {
            return seamCarver.height();
        }

    }

    private static class VerticalSeamPath extends SeamPath {

        VerticalSeamPath(SeamCarver seamCarver) {
            super(seamCarver);
        }

        @Override
        void initPQ() {
           for (int i = 0; i < width(); i++) {
               distTo[i] = energy(i, 0);
               pq.insert(i, distTo[i]);
           }
        }

        @Override
        void visit(int vertex) {
            int row = vertex / width();
            int col = vertex - row * width();
            if (col == 0) {
                relax(col, row, col, row + 1);
                relax(col, row, col + 1, row + 1);
            } else if (col == width() - 1) {
                relax(col, row, col - 1, row + 1);
                relax(col, row, col, row + 1);
            } else {
                relax(col, row, col - 1, row + 1);
                relax(col, row, col, row + 1);
                relax(col, row, col + 1, row + 1);
            }
        }

        @Override
        int[] path() {
            int row = height() - 1;
            // find vertex with min dist
            double minValue = distTo[row * width()];
            int minVertex = row * width();
            for (int i = 1; i < width(); i++) {
                double value = distTo[i + row * width()];
                if (minValue > value) {
                    minValue = value;
                    minVertex = i + row * width();
                }
            }

            // build path
            int[] path = new int[height()];
            path[height() - 1] = minVertex;
            for (int i = height() - 1; i > 0; i--) {
                path[i - 1] = edgeTo[path[i]];
            }

            return path;
        }
    }


    private static class HorizontalSeamPath extends SeamPath {

        HorizontalSeamPath(SeamCarver seamCarver) {
            super(seamCarver);
        }

        @Override
        void initPQ() {
            for (int i = 0; i < height(); i++) {
                distTo[i * width()] = energy(0, i);
                pq.insert(i * width(), distTo[i * width()]);
            }
        }

        @Override
        void visit(int vertex) {
            int row = vertex / width();
            int col = vertex - row * width();
            if (row == 0) {
                relax(col, row, col + 1, row);
                relax(col, row, col + 1, row + 1);
            } else if (row == height() - 1) {
                relax(col, row, col + 1, row - 1);
                relax(col, row, col + 1, row);
            } else {
                relax(col, row, col + 1, row - 1);
                relax(col, row, col + 1, row);
                relax(col, row, col + 1, row + 1);
            }
        }

        @Override
        int[] path() {
            int col = width() - 1;
            // find vertex with min dist
            double minValue = distTo[col];
            int minVertex = col;
            for (int i = 1; i < height(); i++) {
                double value = distTo[col + i * width()];
                if (minValue > value) {
                    minValue = value;
                    minVertex = col + i * width();
                }
            }

            // build path
            int[] path = new int[width()];
            path[width() - 1] = minVertex;
            for (int i = width() - 1; i > 0; i--) {
                path[i - 1] = edgeTo[path[i]];
            }

            return path;
        }
    }

    private final Picture picture;
    private int width;
    private int height;
    private boolean[] discarded;

    public SeamCarver(Picture picture) {
        checkNotNull(picture);

        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
        this.discarded = new boolean[width * height];
    }

    public Picture picture() {
        Picture picture = new Picture(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                picture.set(col, row, color(col, row));
            }
        }
        return picture;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    private Color color(int col, int row) {
        while (discarded(col, row)) {
            col++;
        }
        return picture.get(col, row);
    }

    private boolean discarded(int col, int row) {
        return discarded[col + row * width()];
    }

    private void setDiscarded(int col, int row, boolean value) {
        discarded[col + row * width()] = value;
    }

    public double energy(int x, int y) {
        checkPointInBounds(x, y, width(), height());

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return 1000.0;
        }

        double deltaX = computeEnergyDelta(color(x + 1, y), color(x - 1, y));
        double deltaY = computeEnergyDelta(color(x, y + 1), color(x, y - 1));

        return Math.sqrt(deltaX + deltaY);
    }

    private double computeEnergyDelta(Color color1, Color color2) {
        float[] rgb1 = color1.getRGBColorComponents(null);
        float[] rgb2 = color2.getRGBColorComponents(null);
        double delta = 0;
        for (int i = 0; i < 3; i++) {
            double diff = (rgb1[i] - rgb2[i]) * 255;
            delta += diff * diff;
        }
        return delta;
    }

    public int[] findHorizontalSeam() {
        return convertSeamPathToRows(new HorizontalSeamPath(this).path());
    }

    public int[] findVerticalSeam() {
       return convertSeamPathToColumns(new VerticalSeamPath(this).path());
    }

    private int[] convertSeamPathToColumns(int[] path) {
        for (int i = 0; i < path.length; i++) {
            int vertex = path[i];
            int row = vertex / width();
            int col = vertex - row * width();
            path[i] = col;
        }
        return path;
    }

    private int[] convertSeamPathToRows(int[] path) {
        for (int i = 0; i < path.length; i++) {
            int vertex = path[i];
            int row = vertex / width();
            path[i] = row;
        }
        return path;
    }

    public void removeHorizontalSeam(int[] seam) {
        checkNotNull(seam);
        checkValidSeam(seam, height());
        if (height() <= 1) {
            throw new IllegalArgumentException("cannot remove horizontal seam for picture with only 1 pixel height");
        }

        for (int i = 0; i < seam.length; i++) {
            int row = seam[i];
            setDiscarded(i, row, true);
        }

        height--;
    }

    public void removeVerticalSeam(int[] seam) {
        checkNotNull(seam);
        checkValidSeam(seam, width());
        if (width() <= 1) {
            throw new IllegalArgumentException("cannot remove vertical seam for picture with only 1 pixel width");
        }

        for (int i = 0; i < seam.length; i++) {
            int col = seam[i];
            setDiscarded(col, i, true);
        }

        width--;
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    private void checkPointInBounds(int x, int y, int w, int h) {
        if (x < 0 || x >= w || y < 0 || y >= h) {
            throw new IllegalArgumentException("(" + x + "," + y + ") is out of range");
        }
    }

    private void checkValidSeam(int[] seam, int maxLength) {
        if (seam.length > maxLength) {
            throw new IllegalArgumentException("seam length is more than max allowed");
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= maxLength) {
                throw new IllegalArgumentException("seam value " + seam[i] + " is out of bounds");
            }
            if (i == 0) continue;
            if (seam[i] - seam[i-1] != 1) {
                throw new IllegalArgumentException("seam at index " + i + " differs from previous seam value by more than 1");
            }
        }
    }

}