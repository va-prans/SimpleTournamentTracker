package Controllers;

import CoreClasses.Player;
import CoreClasses.Team;
import CoreClasses.Tournament;
import Logic.PageLogic.TournamentCreationLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by NSA on 22-04-2017.
 */
public class TournamentCreationController {

    String uniqueID;

    @FXML TextField playerNameField;
    @FXML TextField teamNameField;
    @FXML TextField tournamentNameField;
    @FXML TableView<Player> teamCreationTable;
    @FXML TableView<Team> createdTeamsTable;
    @FXML TableView<Player> existingPlayersTable;
    @FXML TableView<Team> existingTeamsTable;
    @FXML Label teamAlreadyAddedWarning;


    ObservableList<Player> players = FXCollections.observableArrayList();
    ObservableList<Team> teams = FXCollections.observableArrayList();

    ObservableList<Player> existingPlayers = FXCollections.observableArrayList();
    ObservableList<Team> existingTeams = FXCollections.observableArrayList();

    TournamentCreationLogic tournamentCreationLogic = new TournamentCreationLogic();

    @FXML public void initialize() {

       fetchExistingPlayers();
       fetchExistingTeams();

    }

    public void fetchExistingPlayers() {

        existingPlayers = tournamentCreationLogic.getExistingPlayers();
        existingPlayersTable.setItems(existingPlayers);

    }

    public void fetchExistingTeams() {

        existingTeams = tournamentCreationLogic.getExistingTeams();
        existingTeamsTable.setItems(existingTeams);

    }

    public void addNewPlayerToTeam(ActionEvent actionEvent) {

        if (players.size() <= 1) {

            uniqueID = UUID.randomUUID().toString();
            Player playerToAdd = new Player(uniqueID, playerNameField.getText());
            players.add(playerToAdd);
            tournamentCreationLogic.addPlayerToDB(playerToAdd);
            fetchExistingPlayers();
            teamCreationTable.setItems(players);

        }

        else {

            playerNameField.setText("Team Full.");

        }


    }

    public void removeFromTeam(ActionEvent actionEvent) {

        Player playerToRemove = teamCreationTable.getSelectionModel().getSelectedItem();

        for (int i = 0; i < players.size() ; i++) {
            if (players.get(i).getUniqueID().equals(playerToRemove.getUniqueID())) {

                players.remove(i);

            }
        }

        teamCreationTable.setItems(players);

    }

    public void createTeam(ActionEvent actionEvent) {

        ArrayList<Player> playersArray = new ArrayList();
        for (int i = 0; i < players.size() ; i++) {

            playersArray.add(players.get(i));

        }

        uniqueID = UUID.randomUUID().toString();
        Team teamToAdd = new Team(teamNameField.getText(), playersArray, uniqueID);
        teams.add(teamToAdd);
        tournamentCreationLogic.addTeamToDB(teamToAdd);
        fetchExistingTeams();
        createdTeamsTable.setItems(teams);

        players.clear();
        teamCreationTable.setItems(players);

    }


    public void addExistingPlayer(ActionEvent actionEvent) {

        if (players.size() <= 1) {

            Player existingPlayerToAdd = existingPlayersTable.getSelectionModel().getSelectedItem();
            players.add(existingPlayerToAdd);
            teamCreationTable.setItems(players);

        }

        else {

            playerNameField.setText("Team Full.");

        }
    }

    public void addExistingTeam(ActionEvent actionEvent) {

        boolean teamAlreadyAdded = false;
        Team existingTeamToAdd = existingTeamsTable.getSelectionModel().getSelectedItem();
        for (int i = 0; i < teams.size(); i++) {
            if (existingTeamToAdd.getUniqueID().equals(teams.get(i).getUniqueID())){
                teamAlreadyAdded = true;
            }
        }
        if (teamAlreadyAdded == false) {
            teams.add(existingTeamToAdd);
            createdTeamsTable.setItems(teams);
            teamAlreadyAddedWarning.setText("");
        }
        else {
            teamAlreadyAddedWarning.setText("Team already added to tournament. Choose another team.");
        }
    }

    public void removeTeam(ActionEvent actionEvent) {

        Team teamToRemove = createdTeamsTable.getSelectionModel().getSelectedItem();
        for (int i = 0; i < teams.size() ; i++) {
            if (teams.get(i).getUniqueID().equals(teamToRemove.getUniqueID())) {

                teams.remove(i);

            }
        }

        createdTeamsTable.setItems(teams);

    }

    public void create(ActionEvent actionEvent) throws IOException {

        tournamentCreationLogic.createNewTournament(teams, tournamentNameField.getText());
        Stage window = Main.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/TournamentPage.fxml"));
        window.setTitle("TournamentPage");
        window.setScene(new Scene(root));
        window.show();

    }
}
