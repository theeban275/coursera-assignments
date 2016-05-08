import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static class FrontCharMap {

        private static final int NO_CHARS = 256;

        private final char[] chars;

        public FrontCharMap() {
            chars = new char[NO_CHARS];
            for (int i = 0; i < NO_CHARS; i++) {
                chars[i] = (char) i;
            }
        }

        private char get(final int index) {
            return chars[index];
        }

        private int index(final char c) {
            for (int i = 0; i < NO_CHARS; i++) {
               if (chars[i] == c) {
                   return i;
               }
            }
            return -1;
        }

        private void shift(final int index) {
            final char c = chars[index];
            System.arraycopy(chars, 0, chars, 1, index);
            chars[0] = c;
        }

    }

    public static void encode() {
        FrontCharMap charMap = new FrontCharMap();
        while (hasInput()) {
            char c = (char) (readByte() & 0xff);
            int index = charMap.index(c);
            charMap.shift(index);
            writeByte((byte) index);
        }
        close();
    }

    public static void decode() {
        FrontCharMap charMap = new FrontCharMap();
        while (hasInput()) {
            int index = readByte();
            char c = charMap.get(index);
            charMap.shift(index);
            writeByte((byte) c);
        }
        close();
    }

    private static boolean hasInput() {
       return !BinaryStdIn.isEmpty();
    }

    private static byte readByte() {
        return BinaryStdIn.readByte();
    }

    private static void writeByte(byte b) {
        BinaryStdOut.write(b);
    }

    private static void close() {
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        String code = args.length > 0 ? args[0] : null;
        if ("-".equals(code)) {
           encode();
        } else if ("+".equals(code)) {
           decode();
        } else {
            System.out.println("Usage: java MoveToFront (+|-) < filename");
        }
    }

}
