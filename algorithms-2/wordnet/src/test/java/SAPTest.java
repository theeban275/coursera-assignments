import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SAPTest {
    // specification http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html

    @Test
    public void testSap() {
        // given
        SAP sap = new SAP(digraph());

        // when
        int ancestor = sap.ancestor(3, 11);
        int length = sap.length(3, 11);

        // then
        assertEquals(1, ancestor);
        assertEquals(4, length);
    }

    @Test
    public void testSapWithSameVertex() {
        // given
        SAP sap = new SAP(digraph());

        // when
        int ancestor = sap.ancestor(3, 3);
        int length = sap.length(3, 3);

        // then
        assertEquals(3, ancestor);
        assertEquals(0, length);
    }

    @Test
    public void testSapWithOneVertexAsParent() {
        // given
        SAP sap = new SAP(digraph());

        // when
        int ancestor = sap.ancestor(3, 1);
        int length = sap.length(3, 1);

        // then
        assertEquals(1, ancestor);
        assertEquals(1, length);
    }

    @Test
    public void testSapWithNoAncestor() {
        // given
        SAP sap = new SAP(digraph());

        // when
        int ancestor = sap.ancestor(3, 6);
        int length = sap.length(3, 6);

        // then
        assertEquals(-1, ancestor);
        assertEquals(-1, length);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testSapAncestorWithInvalidVertex() {
        // given
        SAP sap = new SAP(digraph());

        // when
        sap.ancestor(3, -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testSapLengthWithInvalidVertex() {
        // given
        SAP sap = new SAP(digraph());

        // when
        sap.length(3, -1);
    }

    @Test
    public void testSapMultiplePaths() {
        // given
        SAP sap = new SAP(digraph("digraph2"));

        // when
        int ancestor = sap.ancestor(1, 5);
        int length = sap.length(1, 5);

        // then
        assertEquals(0, ancestor);
        assertEquals(2, length);
    }

    private Digraph digraph() {
        return new Digraph(new In("src/test/resources/wordnet/digraph1.txt"));
    }

    private Digraph digraph(String name) {
        return new Digraph(new In("src/test/resources/wordnet/" + name + ".txt"));
    }
}
