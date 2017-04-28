package CoreClasses;

import java.time.LocalDateTime;

/**
 * Created by NSA on 21-04-2017.
 */
public class Player {

    private String name;
    private String uniqueID;
    private String eMail;
    private LocalDateTime dateOfBirth;
    private String score;
    private String matchesPlayed;
    private String tournamentsWon;

    public Player(String uniqueID, String name) {
        this.name = name;
        this.uniqueID = uniqueID;
    }

    public Player(String name, String uniqueID, String eMail, LocalDateTime dateOfBirth, String score, String matchesPlayed, String tournamentsWon) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.eMail = eMail;
        this.dateOfBirth = dateOfBirth;
        this.score = score;
        this.matchesPlayed = matchesPlayed;
        this.tournamentsWon = tournamentsWon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}
