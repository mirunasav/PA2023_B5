import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * singleton, deci o fac sa aiba o singura instanta statica si globala
 */
public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";
    private static Connection connection = null;

    private Database() {
    }

    public static Connection getConnection() {
        if (Database.connection == null)
            Database.createconnection();
        return Database.connection;
    }

    private static void createconnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Cannot connect to DB; " + e.toString());
        }
    }

    public static void closeConnection() {
        try {
            Database.connection.close();
        } catch (SQLException e) {
            System.out.println("error when closing DB! " + e.toString());
        }
    }

}
