import java.util.ArrayList;
import java.util.HashSet;

public class BoggleSolver {

    private static class Node {

        private String word;
        private Node[] links;

        public Node() {
            this.links = new Node[26];
        }

        public String word() {
            return word;
        }

        public Node getLink(char c) {
            return links[c - 'A'];
        }

        public void addLink(char c, Node node) {
            links[c - 'A'] = node;
        }

    }

    private static class Dictionary {

        private Node root;

        public Dictionary(String[] dictionary) {
            root = new Node();

            // Note: assume words are not null
            for (String word: dictionary) {
                putWord(word);
            }
        }

        public void putWord(String word) {
            if (word == null || word.length() < 3) {
                return;
            }

            root = putWord(root, word, 0);
        }

        private Node putWord(Node node, String word, int index) {
            if (word == null) {
                return node;
            }

            if (node == null) {
                node = new Node();
            }

            if (index == word.length()) {
                node.word = word;
            } else {
                char c = word.charAt(index);
                node.addLink(c, putWord(node.getLink(c), word, index + 1));
            }

            return node;
        }

        public boolean hasWord(String word) {
            if (word == null) {
                return false;
            }

            return hasWord(root, word, 0);
        }

        private boolean hasWord(Node node, String word, int index) {
            if (node == null) {
                return false;
            }

            if (index == word.length()) {
                return node.word() != null;
            }

            char c = word.charAt(index);
            return hasWord(node.getLink(c), word, index + 1);
        }

        public Node getNode(char c) {
            return nextNode(root, c);
        }

        public Node nextNode(Node node, char c) {
            if (c == 'Q') {
                node = node.getLink('Q');
                if (node == null) {
                    return null;
                } else {
                    return node.getLink('U');
                }
            }
            return node.getLink(c);
        }

    }

    private static class MatchBoggleWords {

        private final Dictionary dictionary;
        private final BoggleBoard board;
        private final HashSet<String> words;
        private final boolean[] visited;

        public MatchBoggleWords(Dictionary dictionary, BoggleBoard board) {
            this.dictionary = dictionary;
            this.board = board;
            this.words = new HashSet<String>();

            int n = board.rows() * board.cols();
            this.visited = new boolean[n];
            for (int i = 0; i < n; i++) {
                matchWords(dictionary.getNode(getLetter(i)), i);
            }
        }

        private void matchWords(Node node, int index) {
            if (node == null) {
                return;
            }

            visited[index] = true;

            if (node.word != null) {
                words.add(node.word);
            }

            for (int i : adjacent(index)) {
                matchWords(dictionary.nextNode(node, getLetter(i)), i);
            }

            visited[index] = false;
        }

        private Iterable<Integer> adjacent(int index) {
            int row = index / board.cols();
            int col = index - row * board.cols();

            ArrayList<Integer> indexes = new ArrayList<Integer>();

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int r = row + i;
                    int c = col + j;
                    if (r < 0 || r > board.rows() - 1 || c < 0 || c > board.cols() - 1) continue;

                    int adjIndex = r * board.cols() + c;
                    if (visited[adjIndex]) continue;

                    indexes.add(adjIndex);
                }
            }


            return indexes;
        }

        private char getLetter(int index) {
            int row = index / board.cols();
            int col = index - row * board.cols();
            return board.getLetter(row, col);
        }

        public Iterable<String> getWords() {
            return words;
        }

    }

    private static final int[] lengthToScore = { 0, 0, 0, 1, 1, 2, 3, 5, 11 };

    private final Dictionary dictionary;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new Dictionary(dictionary);
    }

    public int scoreOf(String word) {
        if (!dictionary.hasWord(word)) {
            return 0;
        }

        int index = word.length();
        if (index >= lengthToScore.length) {
            index = lengthToScore.length - 1;
        }

        return lengthToScore[index];
    }


    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("board is null");
        }

        return new MatchBoggleWords(dictionary, board).getWords();
    }

}
