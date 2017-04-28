package CoreClasses;

/**
 * Created by NSA on 21-04-2017.
 */
public class Player {

    private String name;
    private String uniqueID;
    private String mail;
    private String dateOfBirth;
    private int score;
    private String matchesPlayed;
    private String tournamentsWon;

    public Player(String uniqueID, String name) {
        this.name = name;
        this.uniqueID = uniqueID;
    }

    public Player(String uniqueID, String name, String eMail, String dateOfBirth, int score) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.mail = eMail;
        this.dateOfBirth = dateOfBirth;
        this.score = score;
        this.matchesPlayed = matchesPlayed;
        this.tournamentsWon = tournamentsWon;
    }

    public void incrementScore() {
        this.score++;

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(String matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public String getTournamentsWon() {
        return tournamentsWon;
    }

    public void setTournamentsWon(String tournamentsWon) {
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
