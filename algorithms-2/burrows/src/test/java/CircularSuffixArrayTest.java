import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CircularSuffixArrayTest {

    @Test(expected = NullPointerException.class)
    public void testNullString() {
        suffixArray(null);
    }

    @Test
    public void testLengthEmptyString() {
        assertEquals(0, suffixArray("").length());
    }

    @Test
    public void testLengthString() {
        assertEquals(12, suffixArray("ABRACADABRA!").length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexNegative() {
        suffixArray("ABRACADABRA!").index(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBounds() {
        suffixArray("ABRACADABRA!").index(13);
    }

    @Test
    public void testIndex() {
        CircularSuffixArray suffix = suffixArray("ABRACADABRA!");
        assertEquals(11, suffix.index(0));
        assertEquals(0, suffix.index(3));
        assertEquals(6, suffix.index(9));
    }

    private CircularSuffixArray suffixArray(String s) {
        return new CircularSuffixArray(s);
    }

}
