import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueueTest {

    private RandomizedQueue queue;

    @Before
    public void setup() {
        queue = new RandomizedQueue<String>();
    }

    @Test
    public void testSize() {
        queue.enqueue("one");
        queue.enqueue("two");
        queue.enqueue("three");
        queue.enqueue("four");
        queue.enqueue("five");
        queue.enqueue("six");
        queue.enqueue("seven");
        queue.enqueue("eight");
        assertEquals(queue.size(), 8);
    }

    @Test
    public void testSizeWithNoItems() {
        assertEquals(queue.size(), 0);
    }

    @Test
    public void testSizeAfterDequeue() {
        queue.enqueue("one");
        queue.enqueue("two");
        queue.dequeue();
        assertEquals(queue.size(), 1);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testIsEmptyWithItems() {
        queue.enqueue("one");
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testEnqueue() {
        queue.enqueue("one");
        assertTrue(Arrays.equals(queue.toArray(), new String[] { "one" }));
    }

    @Test(expected=NullPointerException.class)
    public void testEnqueueNullItems() {
        queue.enqueue(null);
    }

    @Test
    public void testDequeue() {
        queue.enqueue("one");
        assertEquals(queue.dequeue(), "one");
        assertTrue(Arrays.equals(queue.toArray(), new String[] {} ));
    }

    @Test(expected=NoSuchElementException.class)
    public void testDequeueWithNoItems() {
        queue.dequeue();
    }

    @Test
    public void testSample() {
        queue.enqueue("one");
        assertEquals(queue.sample(), "one");
        assertTrue(Arrays.equals(queue.toArray(), new String[] { "one" }));
    }

    @Test(expected=NoSuchElementException.class)
    public void testSampleWithNoItems() {
        queue.sample();
    }

    @Test
    public void testIterator() {
        queue.enqueue("one");
        Iterator<String> iterator = queue.iterator();
        String[] items = new String[] { "one" };
        for (int i = 0; i < items.length; i++) {
             assertTrue(iterator.hasNext());
             assertEquals(iterator.next(), "one");
        }
        assertFalse(iterator.hasNext());
    }

    @Test(expected=NoSuchElementException.class)
    public void testIteratorWithNoItems() {
        Iterator<String> iterator = queue.iterator();
        iterator.next();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testIteratorRemove() {
        Iterator<String> iterator = queue.iterator();
        iterator.remove();
    }
}
