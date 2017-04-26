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
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by NSA on 22-04-2017.
 */

/*
To do:
make sure players/teams with empty names can't be created //done
use a single DUMMY player/team for match creation when there are an odd number of teams //done
possibly don't re fetch all teams from DB when creating a new team - just add them to the existing teams list and refresh and add to DB on separate thread //done
add more statistics to existing players/teams + add more input variables for teams/players
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
    @FXML Label playerNameWarningLabel;
    @FXML Label teamNameWarningLabel;
    @FXML Label tournamentWarningLabel;


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

            if (!StringUtils.isBlank(playerNameField.getText())) {

                playerNameWarningLabel.setText("");
                uniqueID = UUID.randomUUID().toString();
                Player playerToAdd = new Player(uniqueID, playerNameField.getText());
                players.add(playerToAdd);
                tournamentCreationLogic.addPlayerToDB(playerToAdd);
                fetchExistingPlayers();
                teamCreationTable.setItems(players);
                playerNameField.clear();

            }

            else {

                playerNameWarningLabel.setText("Choose a name.");

            }
        }

        else {

            playerNameWarningLabel.setText("Team Full.");

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

            if (players.size() > 0) {

                if (!StringUtils.isBlank(teamNameField.getText())) {

                    String teamName = teamNameField.getText();
                    teamNameField.clear();
                    teamNameWarningLabel.setText("");
                    Thread one = new Thread(() -> {

                        creatingTeamLabel.setOpacity(1.0);
                        createTeamButton.setDisable(true);
                        ArrayList<Player> playersArray = new ArrayList();
                        for (int i = 0; i < players.size(); i++) {

                            playersArray.add(players.get(i));

                        }
                        uniqueID = UUID.randomUUID().toString();
                        Team teamToAdd = new Team(teamName, playersArray, uniqueID);
                        teams.add(teamToAdd);
                        tournamentCreationLogic.addTeamToDB(teamToAdd);
                        createdTeamsTable.setItems(teams);
                        existingTeams.add(teamToAdd);
                        existingTeamsTable.refresh();
                        players.clear();
                        teamCreationTable.setItems(players);
                        createTeamButton.setDisable(false);
                        creatingTeamLabel.setOpacity(0.0);

                    });

                    one.start();

                }

                else {

                    teamNameWarningLabel.setText("Choose a name.");

                }


            }

            else {

                teamNameWarningLabel.setText("Not enough players.");

            }



    }


    public void addExistingPlayer(ActionEvent actionEvent) {

        if (players.size() <= 1) {

            boolean playerAlreadyAdded = false;
            playerNameWarningLabel.setText("");
            Player existingPlayerToAdd = existingPlayersTable.getSelectionModel().getSelectedItem();

            for (int i = 0; i < players.size(); i++) {

                if (existingPlayerToAdd.getUniqueID().equals(players.get(i).getUniqueID())){

                    playerAlreadyAdded = true;

                }

            }
            if (!playerAlreadyAdded) {

                players.add(existingPlayerToAdd);
                teamCreationTable.setItems(players);
                teamAlreadyAddedWarning.setText("");

            }

            else {

                playerNameWarningLabel.setText("Player already added to team.");

            }

        }

        else {

            playerNameWarningLabel.setText("Team Full.");

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

            teamAlreadyAddedWarning.setText("Team already added to tournament.");

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

        if (teams.size() > 1) {

            if (!StringUtils.isBlank(tournamentNameField.getText())) {

                tournamentCreationLogic.createNewTournament(teams, tournamentNameField.getText());
                Stage window = Main.getStage();
                Parent root = FXMLLoader.load(getClass().getResource("Views/TournamentPage.fxml"));
                window.setTitle("TournamentPage");
                window.setScene(new Scene(root));
                window.show();

            }

            else {

                tournamentWarningLabel.setText("Choose tournament name.");

            }
        }

        else {

            tournamentWarningLabel.setText("Not enough teams.");

        }

    }
}
