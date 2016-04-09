import edu.princeton.cs.algs4.*;

import java.awt.*;

class SeamCarver {

    private final Picture picture;

    public SeamCarver(Picture picture) {
        checkNotNull(picture);
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        checkCoordsInBounds(x, y, width(), height());

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return 1000.00;
        }

        double deltaX = computeEnergyDelta(picture.get(x + 1, y), picture.get(x - 1, y));
        double deltaY = computeEnergyDelta(picture.get(x, y + 1), picture.get(x, y - 1));

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
        return null;
    }

    public int[] findVerticalSeam() {
       return null;
    }

    public void removeHorizontalSeam(int[] seam) {
        checkNotNull(seam);
        checkValidSeam(seam, height());
        if (height() <= 1) {
            throw new IllegalArgumentException("cannot remove horizontal seam for picture with only 1 pixel height");
        }
    }

    public void removeVerticalSeam(int[] seam) {
        checkNotNull(seam);
        checkValidSeam(seam, width());
        if (width() <= 1) {
            throw new IllegalArgumentException("cannot remove vertical seam for picture with only 1 pixel width");
        }
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    private void checkCoordsInBounds(int x, int y, int w, int h) {
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