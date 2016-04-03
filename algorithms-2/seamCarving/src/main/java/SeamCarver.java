import edu.princeton.cs.algs4.*;

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
        return 0.0;
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