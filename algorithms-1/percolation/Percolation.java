import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int num;

    private WeightedQuickUnionUF fullFind;
    private WeightedQuickUnionUF percolatesFind;

    private int topSite;
    private int bottomSite;

    private boolean[] sitesOpen;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        num = N;

        fullFind = new WeightedQuickUnionUF(N * N + 2);
        percolatesFind = new WeightedQuickUnionUF(N * N + 2);

        topSite = N * N;
        bottomSite = topSite + 1;

        for (int i = 0; i < N; i++) {
            fullFind.union(site(1, i + 1), topSite);
            percolatesFind.union(site(1, i + 1), topSite);
            percolatesFind.union(site(N, i + 1), bottomSite);
        }

        sitesOpen = new boolean[N * N];
    }

    public void open(int i, int j) {
        if (isOpen(i, j)) {
            return;
        }

        if (canUnion(i - 1, j)) {
            fullFind.union(site(i - 1, j), site(i, j));
            percolatesFind.union(site(i - 1, j), site(i, j));
        }
        if (canUnion(i + 1, j)) {
            fullFind.union(site(i + 1, j), site(i, j));
            percolatesFind.union(site(i + 1, j), site(i, j));
        }
        if (canUnion(i, j - 1)) {
            fullFind.union(site(i, j - 1), site(i, j));
            percolatesFind.union(site(i, j - 1), site(i, j));
        }
        if (canUnion(i, j + 1)) {
            fullFind.union(site(i, j + 1), site(i, j));
            percolatesFind.union(site(i, j + 1), site(i, j));
        }

        sitesOpen[site(i, j)] = true;
    }

    public boolean isOpen(int i, int j) {
        if (!isWithinBounds(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        return sitesOpen[site(i, j)];
    }

    public boolean isFull(int i, int j) {
        if (isOpen(i, j)) {
            return fullFind.connected(topSite, site(i, j));
        }
        return false;
    }

    public boolean percolates() {
        return percolatesFind.connected(topSite, bottomSite);
    }

    private boolean canUnion(int i, int j) {
         return isWithinBounds(i, j) && isOpen(i, j);
    }

    private boolean isWithinBounds(int i, int j) {
        return (i >= 1 && i <= num) && (j >= 1 && j <= num);
    }

    private int site(int i, int j) {
         return (i - 1) * num + (j - 1);
    }

}
