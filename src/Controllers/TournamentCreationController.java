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
import javafx.scene.control.*;
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
    @FXML ProgressIndicator progressCircle;
    @FXML Label loadingLabel;
    @FXML Label creatingTeamLabel;
    @FXML Button createTeamButton;


    ObservableList<Player> players = FXCollections.observableArrayList();
    ObservableList<Team> teams = FXCollections.observableArrayList();

    ObservableList<Player> existingPlayers = FXCollections.observableArrayList();
    ObservableList<Team> existingTeams = FXCollections.observableArrayList();

    TournamentCreationLogic tournamentCreationLogic = new TournamentCreationLogic();

    @FXML public void initialize() {

        Thread one = new Thread(() -> {

            fetchExistingPlayers();
            fetchExistingTeams();
            loadingLabel.setOpacity(0.0);
            progressCircle.setProgress(1.0);
            progressCircle.setOpacity(0.0);


        });
        one.start();

    }

    public void fetchExistingPlayers() {

        existingPlayers = tournamentCreationLogic.getExistingPlayers();
        existingPlayersTable.setItems(existingPlayers);

    }

    public void fetchExistingTeams() {

        Thread one = new Thread(() -> {
            double progres = 0.0;

            while (progres < 0.99) {
                progres += 0.01;
                progressCircle.setProgress(progres);
                try {
                    Thread.sleep(65);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        one.start();

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

        Thread one = new Thread(() -> {
            if (players.size() > 0) {
                creatingTeamLabel.setOpacity(1.0);
                createTeamButton.setDisable(true);
                ArrayList<Player> playersArray = new ArrayList();
                for (int i = 0; i < players.size(); i++) {

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
                createTeamButton.setDisable(false);
                creatingTeamLabel.setOpacity(0.0);
            }

            else {
                System.out.println("not enuf playas");
            }
        });
        one.start();

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
