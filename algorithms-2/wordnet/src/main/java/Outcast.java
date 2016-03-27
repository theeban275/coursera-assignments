import edu.princeton.cs.algs4.*;

public class Outcast {

    private final WordNet wordNet;

    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        int maxDistance = -1;
        String outcast = null;
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance += wordNet.distance(nouns[i], nouns[j]);
            }
            if (maxDistance == -1 || maxDistance < distance) {
                maxDistance = distance;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

}
