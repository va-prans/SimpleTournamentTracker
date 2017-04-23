package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Views/StartPage.fxml"));
        stage.setTitle("StartPage");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void startPage() throws IOException {



    }


    public static void main(String[] args) {
        launch(args);
    }
}
