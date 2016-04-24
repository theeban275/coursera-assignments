import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;


public class BoggleSolverTest {

    @Test
    public void testScoreOf_wordInDictionary() {
        assertEquals(0, boggleSolver().scoreOf(null));
        assertEquals(0, boggleSolver().scoreOf(""));
        assertEquals(0, boggleSolver().scoreOf("A"));
        assertEquals(0, boggleSolver().scoreOf("AB"));
        assertEquals(1, boggleSolver().scoreOf("ABC"));
        assertEquals(1, boggleSolver().scoreOf("ABCD"));
        assertEquals(2, boggleSolver().scoreOf("ABCDE"));
        assertEquals(3, boggleSolver().scoreOf("ABCDEF"));
        assertEquals(5, boggleSolver().scoreOf("ABCDEFG"));
        assertEquals(11, boggleSolver().scoreOf("ABCDEFGH"));
        assertEquals(11, boggleSolver().scoreOf("ABCDEFGHI"));
    }

    @Test
    public void testScoreOf_wordNotInDictionary() {
        assertEquals(0, boggleSolver().scoreOf("HELLO"));
    }

    @Test
    public void testGetAllValidWords_noValidWords() {
        assertIterableEquals(new ArrayList<String>(), boggleSolver("HELLO").getAllValidWords(boggleBoard(new char[][] {{ 'H', 'E', 'L', 'L', 'A' }})));
    }

    @Test
    public void testGetAllValidWords_oneValidWord() {
        assertIterableEquals(words("HELLO"), boggleSolver("HELLO").getAllValidWords(boggleBoard(new char[][] {{ 'H', 'E', 'L', 'L', 'O' }})));
    }

    @Test
    public void testGetAllValidWords_oneValidWordWithQu() {
        assertIterableEquals(words("QUIET"), boggleSolver("QUIET").getAllValidWords(boggleBoard(new char[][] {{ 'Q', 'I', 'E', 'T' }})));
    }

    @Test
    public void testGetAllValidWords_multipleValidWords() {
        assertIterableEquals(words("HELL", "HELLO"), boggleSolver("HELL", "HELLO").getAllValidWords(boggleBoard(new char[][] {{ 'H', 'E', 'L', 'L', 'O' }})));
    }

    @Test
    public void testGetAllValidWords_multipleValidWordsWithDuplicates() {
        assertIterableEquals(words("HELL", "HELLO"), boggleSolver("HELL", "HELLO").getAllValidWords(boggleBoard(new char[][] {{ 'H', 'A', 'B'} , {'E', 'C', 'D'} , {'L', 'L', 'O' }})));
    }

    @Test public void testScoreOf_board4x4() {
        BoggleSolver solver = boggleSolverFrom("dictionary-algs4.txt");
        BoggleBoard board = boggleBoardFrom("board4x4.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            score += solver.scoreOf(word);
        }
        assertEquals(33, score);
    }

    @Test
    public void testScoreOf_boardq() {
        BoggleSolver solver = boggleSolverFrom("dictionary-algs4.txt");
        BoggleBoard board = boggleBoardFrom("board-q.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            score += solver.scoreOf(word);
        }
        assertEquals(84, score);
    }

    private BoggleSolver boggleSolver() {
       return new BoggleSolver(new String[] { null, "", "A", "AB", "ABC", "ABCD", "ABCDE", "ABCDEF", "ABCDEFG", "ABCDEFGH", "ABCDEFGHI" });
    }

    private BoggleSolver boggleSolver(String ...dictionary) {
       return new BoggleSolver(dictionary);
    }

    private BoggleSolver boggleSolverFrom(String filename) {
        In in = new In("src/test/resources/boggle/" + filename);
        return new BoggleSolver(in.readAllLines());
    }

    private BoggleBoard boggleBoard(char[][] board) {
       return new BoggleBoard(board);
    }

    private BoggleBoard boggleBoardFrom(String filename) {
        return new BoggleBoard("src/test/resources/boggle/" + filename);
    }

    private Iterable<String> words(String ...words) {
        return Arrays.asList(words);
    }

    private void assertIterableEquals(Iterable<String> it1, Iterable<String> it2) {
        ArrayList<String> list1 = toList(it1);
        ArrayList<String> list2 = toList(it2);
        assertEquals(list1, list2);
    }

    private ArrayList<String> toList(Iterable<String> it) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : it) {
            list.add(s);
        }
        list.sort(Comparator.naturalOrder());
        return list;
    }

}
