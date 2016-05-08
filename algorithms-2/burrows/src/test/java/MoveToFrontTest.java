import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

// NOTE: Cannot run all tests together because BinaryStdIn current is statically
// initialised with the System.in and cannot be reset between tests
public class MoveToFrontTest {

    private ByteArrayOutputStream outStream;

    @Before
    public void setup() {
        outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
    }

    @Test
    public void testEncodeEmptyInput() {
        stdin("");
        MoveToFront.encode();
        assertEquals("", hexout());
    }

    @Test
    public void testEncodeSingleCharacter() {
        stdin("A");
        MoveToFront.encode();
        assertEquals("41", hexout());
    }

    @Test
    public void testEncodeMultipleCharacters() {
        stdin("ABRACADABRA!");
        MoveToFront.encode();
        assertEquals("41 42 52 02 44 01 45 01 04 04 02 26", hexout());
    }

    @Test
    public void testEncodeDuplicateCharacters() {
        stdin("AAA");
        MoveToFront.encode();
        assertEquals("41 00 00", hexout());
    }

    @Test
    public void testEncodeReorderMultipleCharacters() {
        stdin("ABCCBA");
        MoveToFront.encode();
        assertEquals("41 42 43 00 01 02", hexout());
    }

    @Test
    public void testDecodeEmptyInput() {
        hexin("");
        MoveToFront.decode();
        assertEquals("", stdout());
    }

    @Test
    public void testDecodeSingleCharacter() {
        hexin("41");
        MoveToFront.decode();
        assertEquals("A", stdout());
    }

    @Test
    public void testDecodeMultipleCharacters() {
        hexin("41 42 52 02 44 01 45 01 04 04 02 26");
        MoveToFront.decode();
        assertEquals("ABRACADABRA!", stdout());
    }

    @Test
    public void testDecodeDuplicateCharacters() {
        hexin("41 00 00");
        MoveToFront.decode();
        assertEquals("AAA", stdout());
    }

    @Test
    public void testDecodeReorderMultipleCharacters() {
        hexin("41 42 43 00 01 02");
        MoveToFront.decode();
        assertEquals("ABCCBA", stdout());
    }

    @Test
    public void testEncode() {
        stdin("ABC");
        MoveToFront.main(new String[] { "-" });
        assertEquals("41 42 43", hexout());
    }

    @Test
    public void testDecode() {
        hexin("41 42 43");
        MoveToFront.main(new String[] { "+" });
        assertEquals("ABC", stdout());
    }

    private void stdin(String s) {
        System.setIn(new ByteArrayInputStream(s.getBytes()));
    }

    private String stdout() {
        return outStream.toString();
    }

    private void hexin(String s) {
        byte[] bytes;

        if (!"".equals(s)) {
            String[] numbers = s.split(" ");
            bytes = new byte[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                bytes[i] = Byte.valueOf(numbers[i], 16);
            }
        } else {
            bytes = new byte[0];
        }

        System.setIn(new ByteArrayInputStream(bytes));
    }

    private String hexout() {
        StringBuilder builder = new StringBuilder();

        byte[] bytes = outStream.toByteArray();
        for (int i = 0; i < bytes.length; i++) {
            builder.append(String.format("%02X", bytes[i]));
            if (i != bytes.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
