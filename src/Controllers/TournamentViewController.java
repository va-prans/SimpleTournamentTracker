package Controllers;

import CoreClasses.Match;
import CoreClasses.Player;
import CoreClasses.Team;
import CoreClasses.Tournament;
import Logic.PageLogic.CurrentUser;
import Logic.PageLogic.TournamentViewLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by NSA on 23-04-2017.
 */
public class TournamentViewController {

    @FXML TableView <Match> bracketsTable;
    @FXML TableView <Team> team0Table;
    @FXML TableView <Team> team1Table;
    @FXML ChoiceBox <Integer> choiceBox;
    @FXML TableView <Player> playerTable;

    ObservableList<ObservableList<Match>> allMatches = FXCollections.observableArrayList();

    TournamentViewLogic tournamentViewLogic = new TournamentViewLogic();

    @FXML public void initialize() {

        startSetMatchtables();

    }

    public void addBracket(ActionEvent actionEvent) {

        boolean allGamesPlayed = true;
        for (int i = 0; i < allMatches.get(allMatches.size()-1).size() ; i++) {
            if (allMatches.get(allMatches.size()-1).get(i).getMatchPlayed() == 0) {
                allGamesPlayed = false;
            }
        }
        if (allGamesPlayed == true) {

            if (allMatches.get(allMatches.size()-1).size() >= 2) {
                allMatches.add(tournamentViewLogic.addBracket(allMatches.get(allMatches.size() - 1)));
                choiceBox.getItems().add(allMatches.size() - 1);
                choiceBox.setValue(allMatches.size() - 1);
            }
            else {
                System.out.println("no matches to make, winners are decided.");
            }
        }

        bracketsTable.setItems(allMatches.get(choiceBox.getValue()));
        bracketsTable.refresh();

    }

    public void team0Win(ActionEvent actionEvent) {

        System.out.println("team 0 win");
        Match selectedMatch = bracketsTable.getSelectionModel().getSelectedItem();
        for (int i = 0; i < allMatches.get(choiceBox.getValue()).size() ; i++) {
            if (selectedMatch.getUniqueID().equals(allMatches.get(choiceBox.getValue()).get(i).getUniqueID())) {
                allMatches.get(choiceBox.getValue()).get(i).setIndexOfWinningTeam(0);
                selectedMatch = allMatches.get(choiceBox.getValue()).get(i);
            }
        }
        bracketsTable.refresh();
        //updateDB
        tournamentViewLogic.updateMatch(selectedMatch);

    }

    public void team1Win(ActionEvent actionEvent) {

        System.out.println("team 1 win");
        Match selectedMatch = bracketsTable.getSelectionModel().getSelectedItem();
        for (int i = 0; i < allMatches.get(choiceBox.getValue()).size() ; i++) {
            if (selectedMatch.getUniqueID().equals(allMatches.get(choiceBox.getValue()).get(i).getUniqueID())) {
                allMatches.get(choiceBox.getValue()).get(i).setIndexOfWinningTeam(1);
            }
        }
        bracketsTable.refresh();
        //updateDB
        tournamentViewLogic.updateMatch(selectedMatch);

    }

    public void team0TableMouseClicked(MouseEvent mouseEvent) {

        if (team1Table.getSelectionModel().getSelectedItem() != null) {
            Team selectedTeam = team0Table.getSelectionModel().getSelectedItem();
            playerDisplay(selectedTeam.getUniqueID());
        }

    }


    public void team1TableMouseClicked(MouseEvent mouseEvent) {

        if (team1Table.getSelectionModel().getSelectedItem() != null) {
            Team selectedTeam = team1Table.getSelectionModel().getSelectedItem();
            playerDisplay(selectedTeam.getUniqueID());
        }

    }

    public void playerDisplay(String teamID){

       ObservableList<Player> players = tournamentViewLogic.getPlayersInTeam(teamID);
       playerTable.setItems(players);

    }

    public void mouseClickedOnMatchTable(MouseEvent mouseEvent) {

        if (bracketsTable.getSelectionModel().getSelectedItem() != null) {
            Match selectedMatch = bracketsTable.getSelectionModel().getSelectedItem();
            teamTablesDisplay(selectedMatch.getTeams()[0].getUniqueID(), selectedMatch.getTeams()[1].getUniqueID());
        }

    }

    public void teamTablesDisplay(String team0UniqueID, String team1UniqueID) {

        ObservableList<Team> team0 = FXCollections.observableArrayList();
        ObservableList<Team> team1 = FXCollections.observableArrayList();
        team0.add(tournamentViewLogic.getTeamFromDB(team0UniqueID));
        team1.add(tournamentViewLogic.getTeamFromDB(team1UniqueID));
        team0Table.setItems(team0);
        team1Table.setItems(team1);

    }

    public void startSetMatchtables(){

        allMatches = tournamentViewLogic.getMatchesFromTournament();
        for (int i = 0; i < allMatches.size() ; i++) {
            choiceBox.getItems().add(i);
        }

        choiceBox.setValue(0);
        bracketsTable.setItems(allMatches.get(choiceBox.getValue()));

    }

    public void showBracket(ActionEvent actionEvent) {

        bracketsTable.setItems(allMatches.get(choiceBox.getValue()));

    }
}
