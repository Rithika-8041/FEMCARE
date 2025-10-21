import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/femcare360";
    private static final String USER = "root";
    private static final String PASS = "123456789"; // replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
