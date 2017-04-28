package Logic.PageLogic;

import CoreClasses.Tournament;

/**
 * Created by NSA on 22-04-2017.
 */
public class CurrentUser {

    public static Tournament currentTournament;
    private static String username;
    private static boolean isAdmin;

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static void setAdmin(boolean admin) {
        isAdmin = admin;
    }

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
