package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String HOSTNAME = "localhost";
    private static final String DBNAME = "mydatabase";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "user";

    public static Connection getMyConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + HOSTNAME + ":3306/" + DBNAME;

        Connection conn = DriverManager.getConnection(connectionURL, USERNAME, PASSWORD);
        return conn;
    }

}
