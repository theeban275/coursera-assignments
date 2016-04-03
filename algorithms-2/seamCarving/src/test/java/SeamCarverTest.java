import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

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

    private void testIllegalEnergyRange(int x, int y) {
        new SeamCarver(getPicture()).energy(x, y);
    }

    private Picture getPicture() {
        return new Picture(1, 1);
    }

    private Picture getPicture(int width, int height) {
        return new Picture(width, height);
    }

    private SeamCarver getSeamCarver() {
        return new SeamCarver(getPicture());
    }

    private SeamCarver getSeamCarver(Picture picture) {
        return new SeamCarver(picture);
    }

}