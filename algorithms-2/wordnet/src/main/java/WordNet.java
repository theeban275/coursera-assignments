import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {

    private static class Synset {
        public Integer id;
        public String synset;
        public String description;

        public Synset(Integer id, String synset, String description) {
            this.id = id;
            this.synset = synset;
            this.description = description;
        }

        public static Synset createFrom(String line) {
            String[] parts = line.split(",");
            return new Synset(Integer.parseInt(parts[0]), parts[1], parts[2]);
        }
    }

    private final ArrayList<Synset> synsets;
    private final HashMap<String, ArrayList<Synset>> nounsToSynsets;
    private final Digraph digraph;
    private final SAP sap;

    public WordNet(String synsetsFilename, String hypernymsFilename) {
        In synsetsIn = new In(synsetsFilename);
        In hypernymsIn = new In(hypernymsFilename);

        synsets = new ArrayList<>();
        nounsToSynsets = new HashMap<>();
        while (synsetsIn.hasNextLine()) {
            Synset synset = Synset.createFrom(synsetsIn.readLine());
            synsets.add(synset);

            String[] nouns = synset.synset.split(" ");
            for (String noun: nouns) {
                if (!nounsToSynsets.containsKey(noun)) {
                    nounsToSynsets.put(noun, new ArrayList<>());
                }
                nounsToSynsets.get(noun).add(synset);
            }
        }

        digraph = new Digraph(synsets.size());
        while (hypernymsIn.hasNextLine()) {
            String[] parts = hypernymsIn.readLine().split(",");
            int synsetId = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                digraph.addEdge(synsetId, Integer.parseInt(parts[i]));
            }
        }

        checkRootedDag(digraph);

        sap = new SAP(digraph);
    }

    public Iterable<String> nouns() {
        return nounsToSynsets.keySet();
    }

    public boolean isNoun(String word) {
        checkNull(word);
        return nounsToSynsets.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        checkNoun(nounA);
        checkNoun(nounB);
        return sap.length(synsetIdsForNoun(nounA), synsetIdsForNoun(nounB));
    }

    public String sap(String nounA, String nounB) {
        checkNoun(nounA);
        checkNoun(nounB);
        return synsetForId(sap.ancestor(synsetIdsForNoun(nounA), synsetIdsForNoun(nounB)));
    }

    private Iterable<Integer> synsetIdsForNoun(String noun) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Synset synset: nounsToSynsets.get(noun)) {
           ids.add(synset.id);
        }
        return ids;
    }

    private String synsetForId(Integer id) {
        return synsets.get(id).synset;
    }

    private void checkNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    private void checkNoun(String noun) {
        checkNull(noun);
        if (!isNoun(noun)) {
            throw new IllegalArgumentException(noun + " is a valid synset noun");
        }
    }

    private void checkRootedDag(Digraph digraph) {
        int rootCount = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) {
                rootCount++;
            }
        }
        boolean hasCycle = new DirectedCycle(digraph).hasCycle();
        if (rootCount > 1 || hasCycle) {
            throw new IllegalArgumentException("not rooted dag");
        }
    }

}
