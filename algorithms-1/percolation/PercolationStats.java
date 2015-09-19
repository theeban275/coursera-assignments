import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionunionFind;

import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;

public class PercolationStats {

    private static final double INTERVAL_VALUE = 1.96;

    private double thresholds[];

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            thresholds[i] = computeThreshold(N);
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

    private double computeThreshold(int N) {
        Percolation percolation = new Percolation(N);
        int count = 0;
        int size = N * N;
        for (int i = 0; i < size; i++) {
            // TODO pick amoung blocked sites
            // percolation.open(row, col);
            if (percolation.percolates()) {
                break;
            }
            count++;
        }
        return count / (double) size;
    }

    public static void main(String[] args) {

    }

}

