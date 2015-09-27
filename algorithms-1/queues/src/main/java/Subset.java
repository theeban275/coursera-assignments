import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);

        String[] items = StdIn.readAll().trim().split(" ");

        RandomizedQueue queue = new RandomizedQueue<String>();
        for (int i = 0; i < items.length; i++) {
            queue.enqueue(items[i].trim());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }

}
