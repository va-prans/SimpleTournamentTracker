package Logic.PageLogic;

import CoreClasses.Tournament;

/**
 * Created by NSA on 22-04-2017.
 */
public class CurrentUser {

    public static Tournament currentTournament;
    static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    public static Tournament getCurrentTournament() {
        return currentTournament;
    }

    public static void setCurrentTournament(Tournament currentTournament) {
        CurrentUser.currentTournament = currentTournament;
    }
}
