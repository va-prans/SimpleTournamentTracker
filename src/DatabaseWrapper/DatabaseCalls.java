package DatabaseWrapper;

import CoreClasses.Match;
import CoreClasses.Player;
import CoreClasses.Team;
import CoreClasses.Tournament;

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


    public void addPlayerToDB(Player player) {

        String sql = "INSERT INTO `players` (`id`, `playername`) " +
                "VALUES ('" + player.getUniqueID() + "', '" + player.getName() + "');";

        sqlQuery(sql);
    }

    public Player getPlayerFromDB(String uniqueID) {

        Player player = null;
        ResultSet resultSet = sqlQueryWithReturn("SELECT * FROM players WHERE id = '" + uniqueID + "'");
        try {
            if (resultSet.next()) {
                player = new Player(resultSet.getString(1), resultSet.getString(2));
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
              players.add(new Player(resultSet.getString(1), resultSet.getString(2)));
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

    public void updateMatch(Match match){

        String sql = "UPDATE matches SET teamwon = '" + match.getIndexOfWinningTeam() + "', matchplayed = '" + match.getMatchPlayed() + "' WHERE id = '" + match.getUniqueID() + "'";
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
