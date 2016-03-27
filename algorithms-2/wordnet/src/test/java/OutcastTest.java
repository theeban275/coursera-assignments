import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutcastTest {

    @Test
    public void testOutcast() {
        // given
        WordNet wordNet = new WordNet(file("synsets.txt"), file("hypernyms.txt"));
        Outcast outcast = new Outcast(wordNet);

        String[] files = new String[] { file("outcast5.txt"), file("outcast8.txt"), file("outcast11.txt") };
        String[] results = new String[] { "table", "bed", "potato" };

        for (int i = 0; i < files.length; i++) {
            In nouns = new In(files[i]);

            // when
            String noun = outcast.outcast(nouns.readAllStrings());

            // then
            assertEquals(results[i], noun);
        }
    }

    private String file(String name) {
        return "src/test/resources/wordnet/" + name;
    }
}
