import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

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

    private static class BurrowsWheelerDecoder {

        private String decoded;

        private BurrowsWheelerDecoder(String s, int first) {
            int length = s.length();

            CharacterIndex[] characters = new CharacterIndex[length];
            for (int i = 0; i < length; i++) {
               characters[i] = new CharacterIndex(s.charAt(i), i);
            }
            Arrays.sort(characters);

            StringBuilder builder = new StringBuilder();
            builder.append(characters[first].character());
            int nextIndex = characters[first].index();
            for (int i = 1; i < length; i++) {
                builder.append(characters[nextIndex].character());
                nextIndex = characters[nextIndex].index();
            }

            decoded = builder.toString();
        }

        private char decodeCharAt(int index) {
            return decoded.charAt(index);
        }
    }

    private static class CharacterIndex implements Comparable<CharacterIndex> {
        private final char character;
        private final int index;

        private CharacterIndex(char character, int index) {
            this.character = character;
            this.index = index;
        }

        private char character() {
            return character;
        }

        private int index() {
            return index;
        }

        @Override
        public int compareTo(CharacterIndex o) {
            int diff = character() - o.character();
            if (diff == 0) {
                return index() - o.index();
            }
            return diff;
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

    private static int readByte() {
        return BinaryStdIn.readByte();
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
        if ("+".equals(code)) {
            encode();
        } else if ("-".equals(code)) {
            decode();
        } else {
            System.out.println("Usage: java BurrowsWheeler (+|-) < filename");
        }
    }
}
