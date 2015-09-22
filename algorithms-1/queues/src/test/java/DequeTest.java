import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DequeTest {

    private Deque deque;

    @Before
    public void setup() {
       deque = new Deque();
    }

    @Test
    public void testIsEmptyReturnsTrue() {
        assertEquals(deque.isEmpty(), true);
    }
}
