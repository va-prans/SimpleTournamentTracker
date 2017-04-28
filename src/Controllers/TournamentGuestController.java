package Controllers;

import CoreClasses.Match;
import CoreClasses.Player;
import CoreClasses.Team;
import Logic.PageLogic.CurrentUser;
import Logic.PageLogic.TournamentViewLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by NSA on 28-04-2017.
 */
public class TournamentGuestController {

    @FXML TableView<Match> bracketsTable;
    @FXML TableView <Team> team0Table;
    @FXML TableView <Team> team1Table;
    @FXML ChoiceBox<Integer> choiceBox;
    @FXML TableView <Player> playerTable;
    @FXML Label tournamentNameLabel;

    ObservableList<ObservableList<Match>> allMatches = FXCollections.observableArrayList();
    String currentTeamID;
    TournamentViewLogic tournamentViewLogic = new TournamentViewLogic();

    @FXML public void initialize() {

        Thread one = new Thread(() -> {
            startSetMatchtables();
        });
        one.start();
        tournamentNameLabel.setText("  [Tournament Name]:  " + CurrentUser.getCurrentTournament().getName() + "  ");

    }


    public void team0TableMouseClicked(MouseEvent mouseEvent) {


        if (team0Table.getSelectionModel().getSelectedItem() != null) {
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
        currentTeamID = teamID;
        Thread one = new Thread(() -> {
            ObservableList<Player> players = tournamentViewLogic.getPlayersInTeam(teamID);
            playerTable.setItems(players);
        });
        one.start();
    }

    public void mouseClickedOnMatchTable(MouseEvent mouseEvent) {

        if (bracketsTable.getSelectionModel().getSelectedItem() != null) {
            Match selectedMatch = bracketsTable.getSelectionModel().getSelectedItem();
            teamTablesDisplay(selectedMatch.getTeams()[0].getUniqueID(), selectedMatch.getTeams()[1].getUniqueID());
        }

    }

    public void teamTablesDisplay(String team0UniqueID, String team1UniqueID) {

        Thread one = new Thread(() -> {
            try {

                ObservableList<Team> team0 = FXCollections.observableArrayList();
                ObservableList<Team> team1 = FXCollections.observableArrayList();
                team0.add(tournamentViewLogic.getTeamFromDB(team0UniqueID));
                team1.add(tournamentViewLogic.getTeamFromDB(team1UniqueID));
                team0Table.setItems(team0);
                team1Table.setItems(team1);

            } catch (NullPointerException e) {

            }
        });
        one.start();


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

    public void backToStart(ActionEvent actionEvent) throws IOException {

        Stage stage = Main.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/AdminPage.fxml"));
        stage.setTitle("AdminPage");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
