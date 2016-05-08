import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

// NOTE: Cannot run all tests together because BinaryStdIn current is statically
// initialised with the System.in and cannot be reset between tests
public class BurrowsWheelerTest {

    private ByteArrayOutputStream outStream;

    @Before
    public void setup() {
        outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
    }

    @Test
    public void testEncodeEmptyInput() {
        stdin("");
        BurrowsWheeler.encode();
        assertEquals("", hexout());
    }

    @Test
    public void testEncodeSingleCharacter() {
        stdin("A");
        BurrowsWheeler.encode();
        assertEquals("00 00 00 00 41", hexout());
    }

    @Test
    public void testEncodeMultipleCharacters() {
        stdin("ABRACADABRA!");
        BurrowsWheeler.encode();
        assertEquals("00 00 00 03 41 52 44 21 52 43 41 41 41 41 42 42", hexout());
    }

    @Test
    public void testEncodeDuplicateCharacters() {
        stdin("AAA");
        BurrowsWheeler.encode();
        assertEquals("00 00 00 00 41 41 41", hexout());
    }

    @Test
    public void testDecodeEmptyInput() {
        hexin("");
        BurrowsWheeler.decode();
        assertEquals("", stdout());
    }

    @Test
    public void testDecodeSingleCharacter() {
        hexin("00 00 00 00 41");
        BurrowsWheeler.decode();
        assertEquals("A", stdout());
    }

    @Test
    public void testDecodeMultipleCharacters() {
        hexin("00 00 00 03 41 52 44 21 52 43 41 41 41 41 42 42");
        BurrowsWheeler.decode();
        assertEquals("ABRACADABRA!", stdout());
    }

    @Test
    public void testDecodeDuplicateCharacters() {
        hexin("00 00 00 00 41 41 41");
        BurrowsWheeler.decode();
        assertEquals("AAA", stdout());
    }

    @Test
    public void testEncode() {
        stdin("ABRACADABRA!");
        BurrowsWheeler.main(new String[] { "-" });
        assertEquals("00 00 00 03 41 52 44 21 52 43 41 41 41 41 42 42", hexout());
    }

    @Test
    public void testDecode() {
        hexin("00 00 00 03 41 52 44 21 52 43 41 41 41 41 42 42");
        BurrowsWheeler.main(new String[] { "+" });
        assertEquals("ABRACADABRA!", stdout());
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
