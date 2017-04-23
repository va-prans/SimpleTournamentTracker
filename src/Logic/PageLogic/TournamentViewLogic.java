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
 * Created by NSA on 23-04-2017.
 */
public class TournamentViewLogic {

   DatabaseCalls databaseCalls = new DatabaseCalls();

   public ObservableList<Match> addBracket(ObservableList<Match> previousBracketMatches){

       ObservableList<Match> addedMatches = FXCollections.observableArrayList();
       ObservableList<Team> winningTeams = FXCollections.observableArrayList();
       for (int i = 0; i < previousBracketMatches.size() ; i++) {
           Team team = previousBracketMatches.get(i).getTeams()[previousBracketMatches.get(i).getIndexOfWinningTeam()];
           winningTeams.add(team);
       }

       if((winningTeams.size()%2)!=0){
           if (winningTeams.size() != 1) {
               winningTeams.add(createDummyTeam());
           }
       }

       for (int i = 0; i < winningTeams.size() ; i+=2) {

           String uniqueID = UUID.randomUUID().toString();
           Team[] matchTeams = {winningTeams.get(i), winningTeams.get(i+1)};
           Match match = new Match(matchTeams, 0, uniqueID, previousBracketMatches.get(0).getTournamentID(), previousBracketMatches.get(0).getBracket() + 1, 5);
           databaseCalls.addMatchToDB(match);
           addedMatches.add(match);
           //dankles

       }

       return addedMatches;
   }

   public Team createDummyTeam() {

       Player dummy1 = new Player(UUID.randomUUID().toString(), "DUMMY");
       Player dummy2 = new Player(UUID.randomUUID().toString(), "DUMMY");
       databaseCalls.addPlayerToDB(dummy1);
       databaseCalls.addPlayerToDB(dummy2);
       ArrayList<Player> dummyPlayers1 = new ArrayList<>();
       dummyPlayers1.add(dummy1);
       dummyPlayers1.add(dummy2);
       Team dummyTeam = new Team("DUMMY", dummyPlayers1, UUID.randomUUID().toString());
       databaseCalls.addTeamToDB(dummyTeam);
       return dummyTeam;

   }

   public void updateMatch(Match match){
       databaseCalls.updateMatch(match);
   }

   public Team getTeamFromDB(String uniqueID) {

       return databaseCalls.getTeamFromDB(uniqueID);

   }

   public ObservableList<Player> getPlayersInTeam(String teamID) {

       Team team = getTeamFromDB(teamID);
       ObservableList<Player> players = FXCollections.observableArrayList();
       players.add(team.getPlayers().get(0));
       players.add(team.getPlayers().get(1));
       return players;

   }

    public ObservableList<ObservableList<Match>> getMatchesFromTournament() {

        Tournament tournament = CurrentUser.getCurrentTournament();
        ObservableList<ObservableList<Match>> allMatches = FXCollections.observableArrayList();
        ArrayList<ArrayList<Match>> brackets = tournament.getBrackets();

        for (int i = 0; i < brackets.size() ; i++) {

            ObservableList<Match> matches = FXCollections.observableArrayList();

            for (int j = 0; j < brackets.get(i).size() ; j++) {

                matches.add(brackets.get(i).get(j));

            }

            allMatches.add(matches);

        }

        return allMatches;
    }

}
