import edu.princeton.cs.algs4.*;

public class SAP {

    private Digraph graph;

    public SAP(Digraph graph) {
        this.graph = graph;
    }

    public int length(int v, int w) {
        return computeSAP(toPaths(v), toPaths(w)).minLength;
    }

    public int ancestor(int v, int w) {
        return computeSAP(toPaths(v), toPaths(w)).minVertex;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return computeSAP(toPaths(v), toPaths(w)).minLength;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return computeSAP(toPaths(v), toPaths(w)).minVertex;
    }

    private BreadthFirstDirectedPaths toPaths(int source) {
        return new BreadthFirstDirectedPaths(graph, source);
    }

    private BreadthFirstDirectedPaths toPaths(Iterable<Integer> sources) {
        return new BreadthFirstDirectedPaths(graph, sources);
    }

    private static class SAPResult {

        public int minLength;
        public int minVertex;

        public SAPResult() {
            this.minLength = -1;
            this.minVertex = -1;
        }

    }

    private SAPResult computeSAP(
            BreadthFirstDirectedPaths pathsV,
            BreadthFirstDirectedPaths pathsW) {
        SAPResult result = new SAPResult();
        for (int vertex = 0; vertex < graph.V(); vertex++) {
            if (pathsV.hasPathTo(vertex) && pathsW.hasPathTo(vertex)) {
                int length = pathsV.distTo(vertex) + pathsW.distTo(vertex);
                if (result.minLength == -1 || result.minLength > length) {
                    result.minVertex = vertex;
                    result.minLength = length;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
         while (!StdIn.isEmpty()) {
             int v = StdIn.readInt();
             int w = StdIn.readInt();
             int length   = sap.length(v, w);
             int ancestor = sap.ancestor(v, w);
             StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
         }
    }
}
