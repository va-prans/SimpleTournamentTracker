package Logic.PageLogic;

import CoreClasses.Match;
import CoreClasses.Tournament;
import DatabaseWrapper.DatabaseCalls;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by NSA on 25-04-2017.
 */
public class TournamentLoadLogic {

    DatabaseCalls databaseCalls = new DatabaseCalls();

    public ObservableList<Tournament> getAllTournaments() {

        ObservableList<Tournament> tournaments;
        tournaments = databaseCalls.getAllTournaments();

        return tournaments;
    }

    public void getTournament(String tournamentID, String name) {

        Tournament tournament = new Tournament(databaseCalls.getTournamentMatches(tournamentID), tournamentID, name);
        CurrentUser.setCurrentTournament(tournament);

    }

}
