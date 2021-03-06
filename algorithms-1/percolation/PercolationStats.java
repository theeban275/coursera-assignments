import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double INTERVAL_VALUE = 1.96;

    private double[] thresholds;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        int[] indexes = new int[N * N];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        thresholds = new double[T];
        for (int i = 0; i < thresholds.length; i++) {
            StdRandom.shuffle(indexes);
            thresholds[i] = computeThreshold(indexes, N);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - interval();
    }

    public double confidenceHi() {
        return mean() + interval();
    }

    private double interval() {
        return INTERVAL_VALUE * stddev() / Math.sqrt(thresholds.length);
    }

    private double computeThreshold(int[] indexes, int N) {
        Percolation percolation = new Percolation(N);
        int count = 0;
        int size = N * N;
        for (int i = 0; i < size; i++) {
            count++;
            int random_index = indexes[i];
            int row = random_index / N;
            int col = random_index - row * N;
            percolation.open(row + 1, col + 1);
            if (percolation.percolates()) {
                break;
            }
        }
        return count / (double) size;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean                    = %f\n", stats.mean());
        StdOut.printf("stddev                  = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n",
                stats.confidenceLo(), stats.confidenceHi());
    }

}

