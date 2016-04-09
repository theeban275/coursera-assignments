import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SeamCarverTest {

    @Test(expected = NullPointerException.class)
    public void testConverterWithNullPicture() {
       new SeamCarver(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveHorizontalSeamWithNullArgument() {
        getSeamCarver().removeHorizontalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeamWithSeamOfInvalidLength() {
        getSeamCarver().removeHorizontalSeam(new int[2]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeamWithSeamOfInvalidIndex() {
        getSeamCarver().removeHorizontalSeam(new int[] { 1 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeamWithSeamOfInvalidDifference() {
        getSeamCarver(getPicture(4, 4)).removeHorizontalSeam(new int[] { 1, 3, 3, 4 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHorizontalSeamWithPictureHeightLessThanOne() {
        getSeamCarver().removeHorizontalSeam(new int[] { 0 });
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveVerticalSeamWithNullArgument() {
        getSeamCarver().removeVerticalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeamWithSeamOfInvalidLength() {
        getSeamCarver().removeVerticalSeam(new int[2]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeamWithSeamOfInvalidIndex() {
        getSeamCarver().removeVerticalSeam(new int[] { 1 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeamWithSeamOfInvalidDifference() {
        getSeamCarver(getPicture(4, 4)).removeVerticalSeam(new int[] { 1, 3, 3, 4 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVerticalSeamWithPictureWidthLessThanOne() {
        getSeamCarver().removeHorizontalSeam(new int[] { 0 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergyWithXLessThanZero() {
        testIllegalEnergyRange(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergyWithXGreaterThanOrEqualToWidth() {
        testIllegalEnergyRange(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergyWithYLessThanZero() {
        testIllegalEnergyRange(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnergyWithYGreaterThanOrEqualToHeight() {
        testIllegalEnergyRange(0, 1);
    }

    @Test
    public void testEnergyOf3x4Picture() {
        Picture picture = getPicture("3x4.png");
        PicturePoint[][] picturePoints = getPicturePoints("3x4.printseams.txt", 3, 4);
        testEnergyOfPicture(picture, picturePoints);
    }

    @Test
    public void testEnergyOf12x10Picture() {
        Picture picture = getPicture("12x10.png");
        PicturePoint[][] picturePoints = getPicturePoints("12x10.printseams.txt", 12, 10);
        testEnergyOfPicture(picture, picturePoints);
    }

    @Test
    public void testVerticalSeamPath3x4Picture() {
        PicturePoint[][] picturePoints = getPicturePoints("3x4.printseams.txt", 3, 4);
        SeamCarver seamCarver = getSeamCarver(getPicture("3x4.png"));
        assertArrayEquals(getVerticalSeamPath(picturePoints), seamCarver.findVerticalSeam());
    }

    @Test
    public void testHorizontalSeamPath3x4Picture() {
        PicturePoint[][] picturePoints = getPicturePoints("3x4.printseams.txt", 3, 4);
        SeamCarver seamCarver = getSeamCarver(getPicture("3x4.png"));
        assertArrayEquals(getHorizontalSeamPath(picturePoints), seamCarver.findHorizontalSeam());
    }

    @Test
    public void testRemoveHorizontalSeam() {
        SeamCarver seamCarver = getSeamCarver(getPicture(3, 3));
        seamCarver.removeHorizontalSeam(new int[] { 0, 1, 2 });
        assertEquals(3, seamCarver.width());
        assertEquals(2, seamCarver.height());
    }

    @Test
    public void testRemoveVerticalSeam() {
        SeamCarver seamCarver = getSeamCarver(getPicture(3, 3));
        seamCarver.removeVerticalSeam(new int[] { 0, 1, 2 });
        assertEquals(2, seamCarver.width());
        assertEquals(3, seamCarver.height());
    }

    private void testEnergyOfPicture(Picture picture, PicturePoint[][] picturePoints) {
        SeamCarver seamCarver = getSeamCarver(picture);
        for (int row = 0; row < picturePoints.length; row++) {
            PicturePoint[] points = picturePoints[row];
            for (int col = 0; col < points.length; col++) {
                assertEquals(points[col].energy, seamCarver.energy(col, row), 0.01);
            }
        }
    }

    private int[] getVerticalSeamPath(PicturePoint[][] picturePoints) {
        int[] path = new int[picturePoints.length];
        for (int row = 0; row < picturePoints.length; row++) {
            PicturePoint[] points = picturePoints[row];
            for (int col = 0; col < points.length; col++) {
                if (points[col].isMinVertical) {
                    path[row] = col;
                }
            }
        }
        return path;
    }

    private int[] getHorizontalSeamPath(PicturePoint[][] picturePoints) {
        int[] path = new int[picturePoints[0].length];
        for (int row = 0; row < picturePoints.length; row++) {
            PicturePoint[] points = picturePoints[row];
            for (int col = 0; col < points.length; col++) {
                if (points[col].isMinHorizontal) {
                    path[col] = row;
                }
            }
        }
        return path;
    }

    private void testIllegalEnergyRange(int x, int y) {
        new SeamCarver(getPicture()).energy(x, y);
    }

    private Picture getPicture() {
        return new Picture(1, 1);
    }

    private Picture getPicture(int width, int height) {
        return new Picture(width, height);
    }

    private Picture getPicture(String filename) {
        return new Picture("src/assets/seamCarving/" + filename);
    }

    private SeamCarver getSeamCarver() {
        return new SeamCarver(getPicture());
    }

    private SeamCarver getSeamCarver(Picture picture) {
        return new SeamCarver(picture);
    }

    static class PicturePoint {
        double energy;
        boolean isMinVertical;
        boolean isMinHorizontal;
    }

    private PicturePoint[][] getPicturePoints(String filename, int width, int height) {
        PicturePoint[][] points = new PicturePoint[height][width];

        In in = new In("src/assets/seamCarving/"  + filename);
        boolean isVertical = true;
        int row = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();

            if (isVertical && line.startsWith("Horizontal")) {
                isVertical = false;
                row = 0;
            }

            if (line.startsWith("1000")) {
                parsePoints(line, points[row++], isVertical);
            }
        }

        return points;
    }

    private void parsePoints(String line, PicturePoint[] points, boolean isVertical) {
        String[] pointEnergies = line.split("\\s+");
        for (int i = 0; i < pointEnergies.length; i++) {
            String pointEnergy = pointEnergies[i];

            boolean isMin = pointEnergy.endsWith("*");

            if (points[i] ==  null) {
                points[i] = new PicturePoint();
                points[i].energy = Double.parseDouble(pointEnergy.replace("*", ""));
            }

            if (isVertical) {
                points[i].isMinVertical = isMin;
            } else {
                points[i].isMinHorizontal = isMin;
            }
        }
    }

}