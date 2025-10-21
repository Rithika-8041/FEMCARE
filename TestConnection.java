import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("✓ Connection successful!");
            
            // Check users
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Users in database ---");
            while(rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Username: " + rs.getString("username") + ", Password: " + rs.getString("password"));
            }
            
            conn.close();
        } catch (SQLException e) {
            System.out.println("✗ Connection failed!");
            e.printStackTrace();
        }
    }
}