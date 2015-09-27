import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class DequeTest {

    private Deque<String> deque;

    @Before
    public void setup() {
       deque = new Deque<String>();
    }

    @Test
    public void testAddFirst() {
        deque.addFirst("one");
        assertTrue(Arrays.equals(deque.toArray(), new String[]{ "one" }));
    }

    @Test
    public void testAddFirstOrder() {
        deque.addFirst("one");
        deque.addFirst("two");
        assertTrue(Arrays.equals(deque.toArray(), new String[]{ "two", "one" }));
    }

    @Test(expected=NullPointerException.class)
    public void testAddFirstNullItems() {
        deque.addFirst(null);
    }

    @Test
    public void testAddLast() {
         deque.addLast("one");
         assertTrue(Arrays.equals(deque.toArray(), new String[] { "one" }));
    }

    @Test
    public void testAddLastOrder() {
        deque.addLast("one");
        deque.addLast("two");
        assertTrue(Arrays.equals(deque.toArray(), new String[] { "one", "two" }));
    }

    @Test(expected=NullPointerException.class)
    public void lastAddLastNullItems() {
        deque.addLast(null);
    }

    @Test
    public void testAddFirstAndAddLast() {
         deque.addFirst("one");
         deque.addLast("two");
         assertTrue(Arrays.equals(deque.toArray(), new String[] { "one", "two" }));
    }

    @Test
    public void testAddLastAndAddFirst() {
         deque.addLast("one");
         deque.addFirst("two");
         assertTrue(Arrays.equals(deque.toArray(), new String[] { "two", "one" }));
    }

    @Test
    public void testIsEmpty() {
         assertTrue(deque.isEmpty());
    }

    @Test
    public void testIsEmptyWithItems() {
        deque.addFirst("one");
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testSize() {
         deque.addFirst("one");
         deque.addLast("two");
         deque.addFirst("three");
         assertEquals(deque.size(), 3);
    }

    @Test
    public void testSizeAfterRemovingFirst() {
        deque.addFirst("one");
        deque.addFirst("two");
        deque.removeFirst();
        assertEquals(deque.size(), 1);
    }

    @Test
    public void testSizeAfterRemovingLast() {
        deque.addFirst("one");
        deque.addFirst("two");
        deque.removeLast();
        assertEquals(deque.size(), 1);
    }

    @Test
    public void testRemoveFirst() {
        deque.addLast("one");
        deque.addLast("two");
        assertEquals(deque.removeFirst(), "one");
        assertTrue(Arrays.equals(deque.toArray(), new String[] { "two" }));
    }

    @Test
    public void testRemoveLast() {
        deque.addLast("one");
        deque.addLast("two");
        assertEquals(deque.removeLast(), "two");
        assertTrue(Arrays.equals(deque.toArray(), new String[] { "one" }));
    }

    @Test
    public void testMixingAddAndRemove() {
        deque.addFirst("one");
        deque.removeFirst();
        deque.addLast("two");
        deque.removeLast();
        assertTrue(Arrays.equals(deque.toArray(), new String[] {}));
    }

    @Test(expected=NoSuchElementException.class)
    public void testRemoveFirstWithNoItems() {
        deque.removeFirst();
    }

    @Test(expected=NoSuchElementException.class)
    public void testRemoveLastWithNoItems() {
        deque.removeLast();
    }

    @Test
    public void testIterator() {
        deque.addFirst("one");
        deque.addFirst("two");
        deque.addFirst("three");
        Iterator<String> iterator = deque.iterator();
        String[] items = new String[] { "three", "two", "one" };
        for (int i = 0; i < items.length; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(iterator.next(), items[i]);
        }
        assertFalse(iterator.hasNext());
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testIteratorRemove() {
        Iterator<String> iterator = deque.iterator();
        iterator.remove();
    }

    @Test(expected=NoSuchElementException.class)
    public void testIteratorNextWithNoItems() {
        Iterator<String> iterator = deque.iterator();
        iterator.next();
    }
}
