import edu.princeton.cs.algs4.StdRandom;

public class CircularSuffixArray {

    // NOTE: This is a modification of the Quick3string class from algs4.jar
    private class Quick3CircularSuffix {
        private static final int CUTOFF =  15;   // cutoff to insertion sort

        private final String str;

        private Quick3CircularSuffix(String s) {
            this.str = s;
        }

        private void sort(int[] a) {
            StdRandom.shuffle(a);
            sort(a, 0, a.length-1, 0);
        }

        // return the dth character of s, -1 if d = length of s
        private int charAt(int offset, int d) {
            if (d == length()) return -1;
            return charAt(d + offset);
        }

        // 3-way string quicksort a[lo..hi] starting at dth character
        private void sort(int[] a, int lo, int hi, int d) {
            // cutoff to insertion sort for small subarrays
            if (hi <= lo + CUTOFF) {
                insertion(a, lo, hi, d);
                return;
            }

            int lt = lo, gt = hi;
            int v = charAt(a[lo], d);
            int i = lo + 1;
            while (i <= gt) {
                int t = charAt(a[i], d);
                if      (t < v) exch(a, lt++, i++);
                else if (t > v) exch(a, i, gt--);
                else              i++;
            }

            // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
            sort(a, lo, lt-1, d);
            if (v >= 0) sort(a, lt, gt, d+1);
            sort(a, gt+1, hi, d);
        }

        // sort from a[lo] to a[hi], starting at the dth character
        private void insertion(int[] a, int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                    exch(a, j, j-1);
        }

        // exchange a[i] and a[j]
        private void exch(int[] a, int i, int j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        // is v less than w, starting at character d
        private boolean less(int v, int w, int d) {
            for (int i = d; i < length(); i++) {
                if (charAt(i + v) < charAt(i + w)) return true;
                if (charAt(i + v) > charAt(i + w)) return false;
            }
            return false;
        }

        private char charAt(int i) {
            if (i >= length()) {
                i -= length();
            }
            return str.charAt(i);
        }

        private int length() {
            return str.length();
        }

    }

    private final int[] offsets;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new NullPointerException("Argument is null");
        }

        offsets = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            offsets[i] = i;
        }

        new Quick3CircularSuffix(s).sort(offsets);
    }

    public int length() {
        return offsets.length;
    }

    public int index(int i) {
        if (i < 0 || i >= length()) {
           throw new IndexOutOfBoundsException("index " + i + " is out of bounds");
        }

        return offsets[i];
    }

}

