package DatabaseWrapper;

/**
 * Created by NSA on 22-04-2017.
 */
public class SingletonDatabase {

    private static DatabaseConnection databaseConnection;

    public static DatabaseConnection getDatabaseConnection() {

        if (databaseConnection == null) {

            databaseConnection = new DatabaseConnection();
            System.out.println("New DB connection established.");

        }

        else {

            //System.out.println("Connection already established, returning connection");

        }

        return databaseConnection;

    }

}
