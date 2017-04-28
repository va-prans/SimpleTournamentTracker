package CoreClasses;

import java.util.ArrayList;

/**
 * Created by NSA on 21-04-2017.
 */
public class Team {

    private String teamName;
    private ArrayList<Player> players;
    private String uniqueID;
    private ArrayList<Match> matches;
    private String playerNames;
    private int score;
    private int tournamentsWon;


    public Team(String teamname, ArrayList<Player> players, String uniqueID) {

        this.teamName = teamname;
        this.players = players;
        this.uniqueID = uniqueID;
        this.playerNames = players.get(0).getName() + " and " + players.get(1).getName();

    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public String getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String playerNames) {
        this.playerNames = playerNames;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

}
