package CoreClasses;

import java.util.ArrayList;

/**
 * Created by NSA on 21-04-2017.
 */
public class Tournament {

    private ArrayList<ArrayList<Match>> brackets;
    private String uniqueID;
    private String name;

    public Tournament(ArrayList<ArrayList<Match>> brackets, String uniqueID, String name) {

        this.uniqueID = uniqueID;
        this.brackets = brackets;
        this.name = name;

    }

    public Tournament(String uniqueID, String name) {

        this.uniqueID = uniqueID;
        this.name = name;

    }

    public void printMatches(){


        for (int i = 0; i < brackets.get(0).size() ; i++) {

            Match currentMatch = brackets.get(0).get(i);
            Team[] matchTeams = currentMatch.getTeams();
            System.out.println("--------------------------------------------------");
            System.out.println("[" + matchTeams[0].getPlayers().get(0).getName() + " and " + matchTeams[0].getPlayers().get(1).getName() + "] vs [" +
                    matchTeams[1].getPlayers().get(0).getName() + " and " + matchTeams[1].getPlayers().get(1).getName() + "]");
            System.out.println(matchTeams[0].getTeamName() + " vs " + matchTeams[1].getTeamName());

        }

    }

    public ArrayList<ArrayList<Match>> getBrackets() {
        return brackets;
    }

    public void setBrackets(ArrayList<ArrayList<Match>> brackets) {
        this.brackets = brackets;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
