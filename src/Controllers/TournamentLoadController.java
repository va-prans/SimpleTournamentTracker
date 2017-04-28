package Controllers;

import CoreClasses.Tournament;
import Logic.PageLogic.TournamentLoadLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by NSA on 25-04-2017.
 */
public class TournamentLoadController {

    @FXML private TableView<Tournament> tournamentTableView;
    @FXML private TableColumn<Object, Object> tournamentName;
    @FXML private TableColumn<Object, Object> tournamentID;
    @FXML private Button loadTournamentButton;
    @FXML private Label loadingLabel;

    boolean doneLoading = false;
    private TournamentLoadLogic tournamentLoadLogic = new TournamentLoadLogic();
    private ObservableList<Tournament> tournaments = FXCollections.observableArrayList();

    public void initialize() {

        loadingLabel.setOpacity(0.0);
        fetchAllTournaments();

    }

    public void fetchAllTournaments(){

        tournamentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tournamentID.setCellValueFactory(new PropertyValueFactory<>("uniqueID"));
        tournaments = tournamentLoadLogic.getAllTournaments();
        tournamentTableView.setItems(tournaments);

    }

    /*public void loadTournament(ActionEvent actionEvent) throws IOException, InterruptedException {

        loadTournamentButton.setDisable(true);
        loadingLabel.setOpacity(1.0);
        if (tournamentTableView.getSelectionModel().getSelectedItem() != null) {


            Tournament selectedTournament = tournamentTableView.getSelectionModel().getSelectedItem();
            tournamentLoadLogic.getTournament(selectedTournament.getUniqueID(), selectedTournament.getName());
            tournamentViewWindow();

        }
    }*/


    public void loadTournament(){

        loadTournamentButton.setDisable(true);
        loadingLabel.setOpacity(1.0);

        Task<Void> databaseTask = new Task<Void>() {
            @Override
            public Void call(){
                Tournament selectedTournament = tournamentTableView.getSelectionModel().getSelectedItem();
                tournamentLoadLogic.getTournament(selectedTournament.getUniqueID(), selectedTournament.getName());
                return null;
            }
        };
        databaseTask.setOnSucceeded( event -> {
            loadingLabel.setOpacity(0.0);
            loadTournamentButton.setDisable(false);
            tournamentViewWindow();
        });
        new Thread(databaseTask).start();

    }

    public void tournamentViewWindow() {

        Stage window = Main.getStage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Views/TournamentPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setTitle("TournamentPage");
        window.setScene(new Scene(root));
        window.show();

    }
}
