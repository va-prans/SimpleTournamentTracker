package DatabaseWrapper;

import CoreClasses.Match;
import CoreClasses.Player;
import CoreClasses.Team;
import CoreClasses.Tournament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by NSA on 22-04-2017.
 */
public class DatabaseCalls {

    SingletonDatabase singletonDatabase; //= new SingletonDatabase();
    Connection connection = null;

    public void updatePlayerScore(Player player) {

        String sql = "UPDATE players SET score = '" + player.getScore() + "' WHERE id = '" + player.getUniqueID() + "'";
        sqlQuery(sql);

    }

    public Tournament getTournament(String tournamentID) {

        Tournament tournament = null;



        return tournament;
    }

    public ObservableList<Tournament> getAllTournaments() {

        ObservableList<Tournament> tournaments = FXCollections.observableArrayList();
        ResultSet resultSet = sqlQueryWithReturn("SELECT * FROM tournament");
        try {
            while (resultSet.next()) {
                tournaments.add(new Tournament(resultSet.getString(2), resultSet.getString(1)));
            }
            connection.close();
            return tournaments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPlayerToDB(Player player) {

        String sql = "INSERT INTO `players` (`id`, `playername`, `email`, `dateofbirth`, `score`) " +
                "VALUES ('" + player.getUniqueID() + "', '" + player.getName() + "', '" + player.getMail() + "', '" + player.getDateOfBirth() + "', '" + player.getScore() + "');";

        sqlQuery(sql);
    }

    public Player getPlayerFromDB(String uniqueID) {

        Player player = null;
        ResultSet resultSet = sqlQueryWithReturn("SELECT * FROM players WHERE id = '" + uniqueID + "'");
        try {
            if (resultSet.next()) {
                player = new Player(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
            }
            connection.close();
            return player;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<Player> getAllPlayers() {

        ArrayList<Player> players = new ArrayList<>();
        ResultSet resultSet = sqlQueryWithReturn("SELECT * FROM players");
        try {
            while (resultSet.next()) {
              players.add(new Player(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    public void addTeamToDB(Team team) {

        String sql = "INSERT INTO `team` (`id`, `player1id`, `player2id`, `teamname`) " +
                "VALUES ('" + team.getUniqueID() + "', '" + team.getPlayers().get(0).getUniqueID() + "', '" + team.getPlayers().get(1).getUniqueID() + "', '" + team.getTeamName() + "');";
        sqlQuery(sql);

    }

    public Team getTeamFromDB(String uniqueID){

        Team team = null;
        ResultSet resultSet = sqlQueryWithReturn("SELECT * FROM team WHERE id = '" + uniqueID + "'");
        try {
            if (resultSet.next()) {
                ArrayList<Player> players = new ArrayList<>();
                players.add(getPlayerFromDB(resultSet.getString(2)));
                players.add(getPlayerFromDB(resultSet.getString(3)));
                team = new Team(resultSet.getString(4), players, resultSet.getString(1));
                connection.close();
            }
            return team;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Team> getAllTeams() {

        ArrayList<Team> teams = new ArrayList<>();
        ResultSet resultSet = sqlQueryWithReturn("SELECT * FROM team");
        try {
            while (resultSet.next()) {
                ArrayList<Player> players = new ArrayList<>();
                players.add(getPlayerFromDB(resultSet.getString(2)));
                players.add(getPlayerFromDB(resultSet.getString(3)));
                teams.add(new Team(resultSet.getString(4), players, resultSet.getString(1)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    public void addTournamentToDB(Tournament tournament) {

        String sql = "INSERT INTO `tournament` (`name`, `id`) " +
                "VALUES ('" + tournament.getName() + "', '" + tournament.getUniqueID() + "');";
        sqlQuery(sql);

        for (int i = 0; i < tournament.getBrackets().get(0).size() ; i++) {

            Match currentMatch = tournament.getBrackets().get(0).get(i);
            addMatchToDB(currentMatch);

        }

    }

    public void addMatchToDB(Match match) {

        String sql = "INSERT INTO `matches` (`id`, `team0`, `team1`, `teamwon`, `teamlost`, `matchplayed`, `tournamentid`, `tournamentbracket`) " +
                "VALUES ('" + match.getUniqueID() + "', '" + match.getTeams()[0].getUniqueID() + "', '" + match.getTeams()[1].getUniqueID() + "', '" + match.getIndexOfWinningTeam()
                + "', '" + match.getIndexOfLosingTeam() + "', '" + match.isMatchPlayed() + "', '" + match.getTournamentID() + "', '" + match.getBracket() + "');";
        sqlQuery(sql);

    }

    public ArrayList<ArrayList<Match>> getTournamentMatches (String tournamentID) {

        ArrayList<ArrayList<Match>> brackets = new ArrayList<>();
        for (int i = 0; i <= getAmountOfBrackets(tournamentID) ; i++) {
            brackets.add(new ArrayList<>());
        }
        System.out.println("brackets size = " + brackets.size());
        ResultSet resultSet = sqlQueryWithReturn("SELECT * FROM matches WHERE tournamentid = '" + tournamentID + "'");
        try {
            while (resultSet.next()){
                Team[] teams = {getTeamFromDB(resultSet.getString(2)), getTeamFromDB(resultSet.getString(3))};
                Match match = new Match(teams, resultSet.getInt(6), resultSet.getString(1), resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(4), resultSet.getInt(9), resultSet.getInt(10));
                brackets.get(resultSet.getInt(8)).add(match);
                System.out.println("added match to bracket: " + resultSet.getInt(8));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return brackets;
        }

        return brackets;
    }

    public int getAmountOfBrackets(String tournamentID) {
        int bracketAmount = 0;
        ResultSet resultSet = sqlQueryWithReturn("SELECT MAX(tournamentbracket) AS bracketamount FROM matches WHERE tournamentid = '" + tournamentID + "'");
        try {
            if (resultSet.next()) {
                bracketAmount = resultSet.getInt("bracketamount");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bracketAmount;
    }

    public void updateMatch(Match match){

        String sql = "UPDATE matches SET teamwon = '" + match.getIndexOfWinningTeam() + "', matchplayed = '" + match.getMatchPlayed() +  "', team0score = '" + match.getTeam0Score() + "', team1score = '" + match.getTeam1Score() + "' WHERE id = '" + match.getUniqueID() + "'";
        sqlQuery(sql);

    }

    public String getPassword(String username) {

        String password = null;
        ResultSet passRS = sqlQueryWithReturn("SELECT password FROM admins WHERE name = '" + username + "'");
        try {
            if(passRS.next()){
                password = passRS.getString(1);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;

    }

    public void sqlQuery(String sqlQuery) {

        DatabaseConnection databaseConnection  = singletonDatabase.getDatabaseConnection();

        try {

            connection = databaseConnection.getConn();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sqlQuery);
            connection.close();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public ResultSet sqlQueryWithReturn(String sqlQuery) {

        DatabaseConnection databaseConnection  = singletonDatabase.getDatabaseConnection();
        //Connection connection = null;
        ResultSet resultSet = null;

        try {

            connection = databaseConnection.getConn();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            //connection.close();
            return resultSet;

        }

        catch (SQLException e) {

            e.printStackTrace();
            return null;

        }




    }

}
