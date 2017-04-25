package Controllers;

import Logic.PageLogic.CurrentUser;
import Logic.PageLogic.StartPageLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by NSA on 22-04-2017.
 */
public class AdminController {

    @FXML private Label welcomeMsg;

    @FXML public void initialize() {

        String username = CurrentUser.getUsername();
        welcomeMsg.setText("Welcome " + username);

    }


    public void back(ActionEvent actionEvent) throws IOException {

        Stage stage = Main.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/StartPage.fxml"));
        stage.setTitle("StartPage");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void newTournament(ActionEvent actionEvent) throws IOException {

        Stage stage = Main.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/TournamentCreation.fxml"));
        stage.setTitle("TourneyCreationPage");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void loadTournament(ActionEvent actionEvent) throws IOException {

        Stage stage = Main.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/TournamentLoad.fxml"));
        stage.setTitle("TourneyLoadPage");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
