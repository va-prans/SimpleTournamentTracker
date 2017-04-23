package CoreClasses;

/**
 * Created by NSA on 21-04-2017.
 */
public class Match {

    private Team[] teams;
    private int indexOfWinningTeam;
    private int indexOfLosingTeam;
    private int matchPlayed;
    private String indexOfWinningTeamString;
    private String matchPlayedString;
    private String uniqueID;
    private String tournamentID;
    private String team0;
    private String team1;
    private String matchTeams;
    private int bracket;

    public Match(Team[] teams, int matchPlayed, String uniqueID, String tournamentID, int bracket, int indexOfWinningTeam) {

        this.teams = teams;
        this.matchPlayed = matchPlayed;
        this.uniqueID = uniqueID;
        this.tournamentID = tournamentID;
        this.bracket = bracket;
        this.team0 = teams[0].getTeamName();
        this.team1 = teams[1].getTeamName();
        this.matchTeams = team0 + " vs " + team1;
        this.indexOfWinningTeam = indexOfWinningTeam;
        if (this.matchPlayed == 1) {
            this.matchPlayedString = "Yes";
            this.indexOfWinningTeamString = getTeams()[indexOfWinningTeam].getTeamName();
        }
        else if (this.matchPlayed == 0) {
            this.matchPlayedString = "No";
            this.indexOfWinningTeamString = "Match not played yet.";
        }

    }

    public String getIndexOfWinningTeamString() {
        return indexOfWinningTeamString;
    }

    public void setIndexOfWinningTeamString(String indexOfWinningTeamString) {
        this.indexOfWinningTeamString = indexOfWinningTeamString;
    }

    public String getTeam0() {
        return team0;
    }

    public void setTeam0(String team0) {
        this.team0 = team0;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public int getMatchPlayed() {
        return matchPlayed;
    }

    public String getMatchTeams() {
        return matchTeams;
    }

    public void setMatchTeams(String matchTeams) {
        this.matchTeams = matchTeams;
    }

    public int getBracket() {
        return bracket;
    }

    public void setBracket(int bracket) {
        this.bracket = bracket;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(String tournamentID) {
        this.tournamentID = tournamentID;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public int getIndexOfWinningTeam() {
        return indexOfWinningTeam;
    }

    public void setIndexOfWinningTeam(int indexOfWinningTeam) {
        System.out.println("setting index of winning team");
        this.indexOfWinningTeam = indexOfWinningTeam;
        setIndexOfWinningTeamString(getTeams()[indexOfWinningTeam].getTeamName());
        setMatchPlayed(1);
    }

    public int getIndexOfLosingTeam() {
        return indexOfLosingTeam;
    }

    public void setIndexOfLosingTeam(int indexOfLosingTeam) {
        this.indexOfLosingTeam = indexOfLosingTeam;
    }

    public int isMatchPlayed() {
        return matchPlayed;
    }

    public void setMatchPlayed(int matchPlayed) {
        System.out.println("setting match played");
        this.matchPlayed = matchPlayed;
        if (this.matchPlayed != 0){
            setMatchPlayedString("Yes");
        }
    }

    public String getMatchPlayedString() {
        return matchPlayedString;
    }

    public void setMatchPlayedString(String matchPlayedString) {
        this.matchPlayedString = matchPlayedString;
    }
}
