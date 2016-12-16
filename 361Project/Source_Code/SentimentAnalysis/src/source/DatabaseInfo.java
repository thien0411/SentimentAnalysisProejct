package source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Has information about DB for program to connect to using JDBC
 *
 * Created by Christian on 11/10/16.
 */

public class DatabaseInfo {

    // TODO:  make sure Christian has given you a dump file and you have a local MySQL server running
    // eventually we should create a shared DB (probably on cse.unl.edu--we can talk to sys admins)
    public static final String url = "jdbc:mysql://localhost/Twitterific";
    public static final String username = "Group9";
    public static final String password = "CATTA";

    static public Connection getConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            System.out.println("InstantiationException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return conn;
    }
}
