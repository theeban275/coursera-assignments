import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class CircularSuffixArray {

    private static class CircularString implements CharSequence, Comparable<CircularString> {

        private final CharSequence sequence;
        private final int offset;

        public CircularString(CharSequence s, int offset) {
            this.sequence = s;
            this.offset = offset;
        }

        public int offset() {
            return offset;
        }

        @Override
        public int length() {
            return sequence.length();
        }

        @Override
        public char charAt(int index) {
            return sequence.charAt((index + offset) % length());
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return new CircularString(sequence.subSequence(start, end), offset);
        }

        @Override
        public IntStream chars() {
            return sequence.chars();
        }

        @Override
        public IntStream codePoints() {
            return sequence.codePoints();
        }

        @Override
        public int compareTo(CircularString o) {
            for (int i = 0; i < length() && i < o.length(); i++) {
                if (charAt(i) < o.charAt(i)) {
                    return -1;
                } else if (charAt(i) > o.charAt(i)) {
                    return 1;
                }
            }
            return length() - o.length();
        }
    }

    private final CircularString[] suffixes;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new NullPointerException("Argument is null");
        }

        suffixes = new CircularString[s.length()];
        for (int i = 0; i < s.length(); i++) {
            suffixes[i] = new CircularString(s, i);
        }

        Arrays.sort(suffixes, new Comparator<CircularString>() {
            @Override
            public int compare(CircularString o1, CircularString o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public int length() {
        return suffixes.length;
    }

    public int index(int i) {
        if (i < 0 || i >= length()) {
           throw new IndexOutOfBoundsException("index " + i + " is out of bounds");
        }

        return suffixes[i].offset();
    }

}

