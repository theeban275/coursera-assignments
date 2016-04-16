import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class BaseballElimination {

    private int numberOfTeams;
    private ArrayList<String> teams;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] games;

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
        return wins[teamIndex(team)];
    }

    public int losses(String team) {
        return losses[teamIndex(team)];
    }

    public int remaining(String team) {
        return remaining[teamIndex(team)];
    }

    public int against(String team1, String team2) {
        return games[teamIndex(team1)][teamIndex(team2)];
    }

    private int teamIndex(String team) {
        int index = teams.indexOf(team);
        if (index < 0) {
            throw new IllegalArgumentException("Invalid team: " + team);
        }
        return index;
    }
}
