import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class DequeTest {

    private Deque<String> deque;

    @Before
    public void setup() {
       deque = new Deque<String>();
    }

    @Test
    public void testAddFirstOnEmptyQueue() {
        deque.addFirst("one");
        assertTrue(Arrays.equals(deque.toArray(), new String[]{ "one" }));
    }

    @Test
    public void testAddFirstAddsItemsToFrontOfQueue() {
        deque.addFirst("one");
        deque.addFirst("two");
        assertTrue(Arrays.equals(deque.toArray(), new String[]{ "two", "one" }));
    }

    @Test(expected=NullPointerException.class)
    public void testAddFirstRaisesExceptionForNullItem() {
        deque.addFirst(null);
    }

    @Test
    public void testAddLastOnEmptyQueue() {
         deque.addLast("one");
         assertTrue(Arrays.equals(deque.toArray(), new String[] { "one" }));
    }

    @Test
    public void testAddLastAddsItemsToEndOfQueue() {
        deque.addLast("one");
        deque.addLast("two");
        assertTrue(Arrays.equals(deque.toArray(), new String[] { "one", "two" }));
    }

    @Test(expected=NullPointerException.class)
    public void testAddLastRaisesExceptionForNullItem() {
        deque.addLast(null);
    }

    @Test
    public void testAddLastAfterAddFirst() {
         deque.addFirst("one");
         deque.addLast("two");
         assertTrue(Arrays.equals(deque.toArray(), new String[] { "one", "two" }));
    }

    @Test
    public void testAddFirstAfterAddLast() {
         deque.addLast("one");
         deque.addFirst("two");
         assertTrue(Arrays.equals(deque.toArray(), new String[] { "two", "one" }));
    }

}
