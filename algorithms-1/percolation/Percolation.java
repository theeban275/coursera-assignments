import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

public class Percolation {

    private int num;
    private WeightedQuickUnionUF unionFind;
    private int topSite;
    private int bottomSite;
    private boolean[] sitesOpen;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        num = N;
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        topSite = N * N;
        bottomSite = topSite + 1;

        for (int i = 0; i < N; i++) {
            unionFind.union(site(0, i), topSite);
            unionFind.union(site(N - 1, i), bottomSite);
        }

        sitesOpen = new boolean[N * N];
    }

    public void open(int i, int j) {
        if (isOpen(i, j)) {
            return ;
        }

        if (isOpenNoException(i - 1, j)) {
            unionFind.union(site(i - 1, j), site(i, j));
        }
        if (isOpenNoException(i + 1, j)) {
            unionFind.union(site(i + 1, j), site(i, j));
        }
        if (isOpenNoException(i, j - 1)) {
            unionFind.union(site(i, j - 1), site(i, j));
        }
        if (isOpenNoException(i, j + 1)) {
            unionFind.union(site(i, j + 1), site(i, j));
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
        return !isOpen(i, j);
    }

    public boolean percolates() {
        return unionFind.connected(topSite, bottomSite);
    }

    private boolean isOpenNoException(int i, int j) {
         return isWithinBounds(i, j) && isOpen(i, j);
    }

    private boolean isWithinBounds(int i, int j) {
        return (i >= 0 && i < num) && (j >= 0 && j < num);
    }

    private int site(int i, int j) {
         return i * num + j;
    }

    public static void main(String[] args) {

    }

}
