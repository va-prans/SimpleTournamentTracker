package Logic.PageLogic;

import CoreClasses.Match;
import CoreClasses.Player;
import CoreClasses.Team;
import CoreClasses.Tournament;
import DatabaseWrapper.DatabaseCalls;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by NSA on 22-04-2017.
 */
public class TournamentCreationLogic {

    DatabaseCalls databaseCalls = new DatabaseCalls();

    public ObservableList<Team> getExistingTeams() {

        ObservableList<Team> teams = FXCollections.observableArrayList();
        ArrayList<Team> teamsArray = databaseCalls.getAllTeams();

        for (int i = 0; i < teamsArray.size() ; i++) {
            if (!teamsArray.get(i).getTeamName().equals("DUMMY")) {
                teams.add(teamsArray.get(i));
            }

        }

        return teams;
    }

    public void addTeamToDB(Team team) {

        databaseCalls.addTeamToDB(team);

    }

    public ObservableList<Player> getExistingPlayers() {

        ObservableList<Player> players = FXCollections.observableArrayList();
        ArrayList<Player> playersArray = databaseCalls.getAllPlayers();

        for (int i = 0; i < playersArray.size() ; i++) {
            if (!playersArray.get(i).getName().equals("DUMMY")) {
                players.add(playersArray.get(i));
            }
        }

        return players;
    }

    public void addPlayerToDB(Player player){

        databaseCalls.addPlayerToDB(player);

    }

    public void createNewTournament(ObservableList teams, String name) {
        String uniqueID;
        String uniqueIDT = UUID.randomUUID().toString();
        ArrayList<Team> teamsArray = new ArrayList<>();
        for (int i = 0; i < teams.size() ; i++) {

            teamsArray.add((Team) teams.get(i));

        }
        if((teamsArray.size()%2)!=0){

            /*Player dummy1 = new Player(UUID.randomUUID().toString(), "DUMMY");
            Player dummy2 = new Player(UUID.randomUUID().toString(), "DUMMY");
            addPlayerToDB(dummy1);
            addPlayerToDB(dummy2);
            ArrayList<Player> dummyPlayers1 = new ArrayList<>();
            dummyPlayers1.add(dummy1);
            dummyPlayers1.add(dummy2);
            Team dummyTeam = new Team("DUMMY", dummyPlayers1, UUID.randomUUID().toString());
            addTeamToDB(dummyTeam);*/
            //waazaa
            Team dummyTeam = databaseCalls.getTeamFromDB("7dee61b2-1cca-4b9c-82dc-d9e205a7e8f4");
            teamsArray.add(dummyTeam);
        }



        ArrayList<ArrayList<Match>> brackets;
        ArrayList<Match> matches = new ArrayList<>();
        for (int i = 0; i < teams.size() ; i+=2) {

            uniqueID = UUID.randomUUID().toString();
            Team[] matchTeams = {teamsArray.get(i), teamsArray.get(i+1)};
            matches.add(new Match(matchTeams, 0, uniqueID, uniqueIDT, 0, 5));

        }

        brackets = new ArrayList<>();
        brackets.add(matches);
        Tournament tournament = new Tournament(brackets, uniqueIDT, name);
        tournament.printMatches();
        databaseCalls.addTournamentToDB(tournament);
        CurrentUser.setCurrentTournament(tournament);

    }

}
