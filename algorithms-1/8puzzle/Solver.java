import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solver {

    private static class HammingPriority implements Comparator<BoardNode> {

        @Override
        public int compare(BoardNode p, BoardNode q) {
            int hp = p.hamming() + p.numMoves();
            int hq = q.hamming() + q.numMoves();
            return hp - hq;
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

    }

    private static class ManhattanPriority implements Comparator<BoardNode> {

        @Override
        public int compare(BoardNode p, BoardNode q) {
            int mp = p.manhattan() + p.numMoves();
            int mq = q.manhattan() + q.numMoves();
            return mp - mq;
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

    }

    private static class BoardNode {

        private Board board;
        private BoardNode parent;
        private int numMoves;

        public BoardNode(Board board) {
            this.board = board;
            this.numMoves = 0;
        }

        public BoardNode(Board board, BoardNode parent) {
            this.board = board;
            this.parent = parent;
            this.numMoves = parent.numMoves() + 1;
        }

        public Board board() {
            return board;
        }

        public BoardNode parent() {
            return parent;
        }

        public int hamming() {
            return board.hamming();
        }

        public int manhattan() {
            return board.manhattan();
        }

        public Iterable<Board> neighbors() {
            return board.neighbors();
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public int numMoves() {
            return numMoves;
        }
    }

    private static class BoardQueue extends MinPQ<BoardNode> {

        public BoardQueue(Comparator<BoardNode> comparator, Board initial) {
            super(comparator);
            insert(new BoardNode(initial));
        }

        public void move() {
            BoardNode node = min();
            for (Board b : node.neighbors()) {
                insertBoard(b, node);
            }
            delMin();
        }

        public void insertBoard(Board board, BoardNode parent) {
            for (Board b : moves()) {
                if (b.equals(board)) {
                    return;
                }
            }
            insert(new BoardNode(board, parent));
        }

        public ArrayList<Board> moves() {
            if (isEmpty()) {
                return null;
            }
            BoardNode node = min();
            ArrayList<Board> boards = new ArrayList<Board>();
            while (node != null) {
                boards.add(node.board());
                node = node.parent();
            }
            Collections.reverse(boards);
            return boards;
        }

        public boolean isSolved() {
            if (isEmpty()) {
                return false;
            }
            return min().isGoal();
        }

    }

    private BoardQueue boardQueue;
    private BoardQueue twinBoardQueue;

    private ArrayList<Board> boardMoves;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }

        boardQueue = new BoardQueue(new ManhattanPriority(), initial);
        twinBoardQueue = new BoardQueue(new ManhattanPriority(), initial.twin());

        while (true) {
            if (boardQueue.isEmpty() || boardQueue.isSolved()
                    || twinBoardQueue.isSolved()) {
                break;
            }
            boardQueue.move();
            if (!twinBoardQueue.isEmpty()) {
                twinBoardQueue.move();
            }
        }

        if (boardQueue.isSolved()) {
            boardMoves = boardQueue.moves();
        }
    }

    public boolean isSolvable() {
        return boardMoves != null;
    }

    public int moves() {
        if (boardMoves == null) {
            return -1;
        }
        return boardMoves.size() - 1;
    }

    public Iterable<Board> solution() {
        return boardMoves;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        // System.out.println(initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
