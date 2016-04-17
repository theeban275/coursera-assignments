import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BaseballElimination {

    private static class BaseballFlowNetwork {

        private final BaseballElimination be;
        private final FlowNetwork flowNetwork;
        private final FordFulkerson ff;

        private final int numGameVertices;
        private final int numTeamVertices;

        public BaseballFlowNetwork(BaseballElimination baseballElimination, int eliminationTeamVertex) {
            this.be = baseballElimination;

            numGameVertices = numberOfOtherTeamCombinations(be.numberOfTeams() - 1);
            numTeamVertices = be.numberOfTeams();

            // source vertex + target vertex + game vertices + team vertices
            this.flowNetwork = new FlowNetwork(2 + numGameVertices + numTeamVertices);

            int eliminationTeamMaxWins = be.wins(eliminationTeamVertex) + be.remaining(eliminationTeamVertex);

            int vertexCount = 0;
            for (int i = 0; i < be.numberOfTeams(); i++) {
                if (eliminationTeamVertex == i) {
                    continue;
                }

                for (int j = i + 1; j < be.numberOfTeams(); j++) {
                    if (eliminationTeamVertex == j) {
                        continue;
                    }

                    int gameVertex = firstGameVertex() + vertexCount++;

                    // 1. source to game edges
                    flowNetwork.addEdge(new FlowEdge(sourceVertex(), gameVertex, be.against(i, j)));

                    // 2. game to team edges
                    flowNetwork.addEdge(new FlowEdge(gameVertex, firstTeamVertex() + i, Double.POSITIVE_INFINITY));
                    flowNetwork.addEdge(new FlowEdge(gameVertex, firstTeamVertex() + j, Double.POSITIVE_INFINITY));
                }

                // 3. team to target edges
                flowNetwork.addEdge(new FlowEdge(firstTeamVertex() + i, targetVertex(), eliminationTeamMaxWins - be.wins(i)));
            }

            this.ff = new FordFulkerson(flowNetwork, sourceVertex(), targetVertex());
        }

        private int numberOfOtherTeamCombinations(int numTeams) {
            int i = 0;
            while (numTeams-- > 1) {
                i += numTeams;
            }
            return i;
        }

        private int sourceVertex() {
            return 0;
        }

        private int targetVertex() {
            return 1;
        }

        private int firstGameVertex() {
            return 2;
        }

        private int numberOfGameVertices() {
            return numGameVertices;
        }

        private int firstTeamVertex() {
            return firstGameVertex() + numberOfGameVertices();
        }

        private int numberOfTeamVertices() {
            return numTeamVertices;
        }

        public boolean areAllGameVerticesFull() {
            for (FlowEdge edge: flowNetwork.adj(sourceVertex())) {
                if (edge.flow() < edge.capacity()){
                   return false;
                }
            }
            return true;
        }

        public ArrayList<Integer> teamVerticesInFlow() {
            ArrayList<Integer> teams = new ArrayList<Integer>();
            for (FlowEdge edge: flowNetwork.adj(targetVertex())) {
                if (ff.inCut(edge.from())) {
                    teams.add(edge.from() - firstTeamVertex());
                }
            }
            return teams;
        }

    }

    private final int numberOfTeams;

    private final ArrayList<String> teams;

    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] games;

    public BaseballElimination(String filename) {
        In in = new In(filename);

        numberOfTeams = in.readInt();

        teams = new ArrayList<String>();
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        games = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(in.readString());
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; j++) {
               games[i][j] = in.readInt();
            }
        }
    }

    public int numberOfTeams() {
        return numberOfTeams;
    }

    public Iterable<String> teams() {
        return teams;
    }

    public int wins(String team) {
        return wins(teamNumber(team));
    }

    private int wins(int teamNumber) {
        return wins[teamNumber];
    }

    public int losses(String team) {
        return losses(teamNumber(team));
    }

    private int losses(int teamNumber) {
        return losses[teamNumber];
    }

    public int remaining(String team) {
        return remaining(teamNumber(team));
    }

    private int remaining(int teamNumber) {
        return remaining[teamNumber];
    }

    public int against(String team1, String team2) {
        return against(teamNumber(team1), teamNumber(team2));
    }

    private int against(int team1Number, int team2Number) {
        return games[team1Number][team2Number];
    }

    public boolean isEliminated(String team) {
        checkTeam(team);

        if (numberOfTeams() == 1) {
            return false;
        }

        if (isTrivialElimination(team)) {
            return true;
        }

        return !computeMaxFlowForTeam(team).areAllGameVerticesFull();
    }

    private boolean isTrivialElimination(String team) {
        return trivialElimination(team) >= 0;
    }

    private int trivialElimination(String team) {
        int teamWins = wins(team) + remaining(team);

        for (int i = 0; i < numberOfTeams(); i++) {
            String teamName = teamName(i);
            if (team.equals(teamName)) continue;

            int wins = wins(teamName);
            if (teamWins < wins) {
                return i;
            }
        }

        return -1;
    }

    public Iterable<String> certificateOfElimination(String team) {
        checkTeam(team);

        if (numberOfTeams() == 1) {
            return null;
        }

        if (isTrivialElimination(team)) {
            ArrayList<String> teams = new ArrayList<String>();
            teams.add(teamName(trivialElimination(team)));
            return teams;
        }

        ArrayList<String> teams = null;
        ArrayList<Integer> teamVerticesInFlow = computeMaxFlowForTeam(team).teamVerticesInFlow();
        if (!teamVerticesInFlow.isEmpty()) {
            teams = new ArrayList<String>();
            for (int teamNumber : teamVerticesInFlow) {
                teams.add(teamName(teamNumber));
            }
            Collections.sort(teams, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return teamNumber(o1) - teamNumber(o2);
                }
            });
        }

        return teams;
    }

    private BaseballFlowNetwork computeMaxFlowForTeam(String team) {
        BaseballFlowNetwork network = new BaseballFlowNetwork(this, teamNumber(team));
        return network;
    }

    private void checkTeam(String team) {
        teamNumber(team);
    }

    private int teamNumber(String team) {
        int index = teams.indexOf(team);
        if (index < 0) {
            throw new IllegalArgumentException("Invalid team: " + team);
        }
        return index;
    }

    private String teamName(int teamNumber) {
        return teams.get(teamNumber);
    }
}
