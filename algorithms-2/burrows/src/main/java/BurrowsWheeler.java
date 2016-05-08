import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static class BurrowsWheelerEncoder {

        private final String str;
        private final CircularSuffixArray suffixArray;

        private BurrowsWheelerEncoder(String s) {
            this.str = s;
            this.suffixArray = new CircularSuffixArray(s);
        }

        private int first() {
            for (int i = 0; i < suffixArray.length(); i++) {
                if (suffixArray.index(i) == 0) {
                    return i;
                }
            }
            return -1;
        }

        private char encodeCharAt(int index) {
            int i = suffixArray.index(index) - 1;
            if (i < 0) {
                i += suffixArray.length();
            }
            return str.charAt(i);
        }
    }

    // NOTE: This is a modification of the LSD class from algs4.jar
    private static class BurrowsWheelerDecoder {

        private final char[] t;
        private final char[] s;
        private final int[] next;

        private final char[] decoded;

        private BurrowsWheelerDecoder(String str, int first) {
            int length = str.length();
            t = str.toCharArray();
            s = new char[length];
            next = new int[length];
            sort();

            decoded = new char[length];
            int nextIndex = first;
            for (int i = 0; i < length; i++) {
                decoded[i] = s[nextIndex];
                nextIndex = next[nextIndex];
            }
        }

        private char decodeCharAt(int index) {
            return decoded[index];
        }

        private void sort() {
            int N = t.length;
            int R = 256;   // extend ASCII alphabet size

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++) {
                count[t[i] + 1]++;
            }

            // compute cumulates
            for (int r = 0; r < R; r++) {
                count[r+1] += count[r];
            }

            // move data
            for (int i = 0; i < N; i++) {
                int index = count[t[i]];
                s[index] = t[i];
                next[index] = i;
                count[t[i]]++;
            }
        }

    }

    public static void encode() {
        if (!hasInput()) {
           return;
        }

        String str = readString();
        int length = str.length();

        BurrowsWheelerEncoder encoder = new  BurrowsWheelerEncoder(str);
        writeInt(encoder.first());
        for (int i = 0; i < length; i++) {
            writeByte((byte) encoder.encodeCharAt(i));
        }

        close();
    }

    public static void decode() {
        if (!hasInput()) {
            return;
        }

        int first = readInt();
        String str = BinaryStdIn.readString();
        int length = str.length();

        BurrowsWheelerDecoder decoder = new BurrowsWheelerDecoder(str, first);
        for (int i = 0; i < length; i++) {
            writeByte((byte) decoder.decodeCharAt(i));
        }

        close();
    }

    private static String readString() {
       return BinaryStdIn.readString();
    }

    private static boolean hasInput() {
        return !BinaryStdIn.isEmpty();
    }

    private static void writeByte(byte b) {
        BinaryStdOut.write(b);
    }

    private static int readInt() {
        return BinaryStdIn.readInt();
    }

    private static void writeInt(int i) {
        BinaryStdOut.write(i);
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
            System.out.println("Usage: java BurrowsWheeler (+|-) < filename");
        }
    }
}
