package DatabaseWrapper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by NSA on 22-04-2017.
 */

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://awsdb.cpef84wr6uer.eu-central-1.rds.amazonaws.com:3306/";
    private static final String DB_NAME = "foosball";
    private static final String USER = "vaid";
    private static final String PASS = "12345678";


    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    DatabaseConnection.URL + DatabaseConnection.DB_NAME,
                    DatabaseConnection.USER,
                    DatabaseConnection.PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
