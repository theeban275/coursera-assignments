import java.util.ArrayList;

public class Board {

    private int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = copyBoardArray(blocks, blocks.length);
    }

    public int dimension() {
        if (blocks == null) {
            return -1;
        }
        return blocks.length;
    }

    public int hamming() {
        int count = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isBlankBlock(i, j)) {
                    continue;
                }
                if (!isValidBlock(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int sum = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isBlankBlock(i, j)) {
                    continue;
                }
                if (!isValidBlock(i, j)) {
                    int value = blocks[i][j] - 1;
                    int row = value / n;
                    int col = value - (row * n);
                    sum += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return sum;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int n = dimension();
        Board twin = cloneBoard();
        // assume n >= 2
        if (twin.isBlankBlock(0, 0)) {
            twin.swapBlocks(0, 1, 1, 0);
        } else if (twin.isBlankBlock(0, 1)) {
            twin.swapBlocks(0, 0, 1, 0);
        } else {
            twin.swapBlocks(0, 0, 0, 1);
        }
        return twin;
    }

    public boolean equals(Object board) {
        if (board == null) {
            return false;
        }
        if (!board.getClass().equals(this.getClass())) {
            return false;
        }
        Board b = (Board) board;
        if (b.dimension() != dimension()) {
            return false;
        }
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (b.blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int n = dimension();
        int r = 0;
        int c = 0;
        boolean found = false;
        for (r = 0; r < n; r++) {
            for (c = 0; c < n; c++) {
                if (isBlankBlock(r, c)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        // up
        if (r > 0) {
            neighbors.add(cloneBoard().swapBlocks(r, c, r - 1, c));
        }
        // down
        if (r < n - 1) {
            neighbors.add(cloneBoard().swapBlocks(r, c, r + 1, c));
        }
        // left
        if (c > 0) {
            neighbors.add(cloneBoard().swapBlocks(r, c, r, c - 1));
        }
        // right
        if (c < n - 1) {
            neighbors.add(cloneBoard().swapBlocks(r, c, r, c + 1));
        }

        return neighbors;
    }

    public String toString() {
        int n = dimension();
        StringBuffer sb = new StringBuffer();
        sb.append(dimension());
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            sb.append(" ");
            for (int j = 0; j < n; j++) {
                sb.append(blockString(i, j));
                if (j < n - 1) {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Board cloneBoard() {
        return new Board(copyBoardArray(blocks, dimension()));
    }

    private int[][] copyBoardArray(int[][] array, int n) {
        int[][] blocksCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocksCopy[i][j] = array[i][j];
            }
        }
        return blocksCopy;
    }

    private boolean isBlankBlock(int r, int c) {
        return blocks[r][c] == 0;
    }

    private Board swapBlocks(int r1, int c1, int r2, int c2) {
        int temp = blocks[r1][c1];
        blocks[r1][c1] = blocks[r2][c2];
        blocks[r2][c2] = temp;
        return this;
    }

    private boolean isValidBlock(int r, int c) {
        return blocks[r][c] == ((r * dimension()) + c + 1);
    }

    private String blockString(int r, int c) {
        return String.valueOf(blocks[r][c]);
    }
}
