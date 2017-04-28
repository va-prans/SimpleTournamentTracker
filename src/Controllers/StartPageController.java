package Controllers;

import Logic.PageLogic.CurrentUser;
import Logic.PageLogic.StartPageLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPageController {

@FXML Button AdminLoginButton;
@FXML TextField usernameField;
@FXML TextField passwordField;
@FXML Label incorrectDetailsWarning;

    StartPageLogic startPageLogic = new StartPageLogic();

    public void AdminLogin(ActionEvent actionEvent) {

        try {
            adminLoginExecute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void keyPressedEvent(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                adminLoginExecute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void adminLoginExecute() throws IOException {

        if (startPageLogic.adminSuccessfulLogin(usernameField.getText(), passwordField.getText())) {

            CurrentUser.setAdmin(true);
            CurrentUser.setUsername(usernameField.getText());
            Stage window = Main.getStage();
            Parent root = FXMLLoader.load(getClass().getResource("Views/AdminPage.fxml"));
            window.setTitle("AdminPage");
            window.setScene(new Scene(root));
            window.show();

        }

        else {

            incorrectDetailsWarning.setText("Incorrect Password or Username.");

        }
    }

    public void guestLogin(ActionEvent actionEvent) throws IOException {

        CurrentUser.setAdmin(false);
        Stage window = Main.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("Views/TournamentLoad.fxml"));
        window.setTitle("AdminPage");
        window.setScene(new Scene(root));
        window.show();

    }
}
