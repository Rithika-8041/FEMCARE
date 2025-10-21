import java.sql.*;
import java.security.MessageDigest;

public class UserDAO {

    private static String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b : hashBytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static boolean usernameExists(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public static boolean registerUser(String username, String password) {
        if(usernameExists(username)) return false;
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username,password) VALUES(?,?)");
            ps.setString(1, username);
            ps.setString(2, hash(password)); // store hashed password
            ps.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public static Integer loginUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, password FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String dbPass = rs.getString("password");
                if(dbPass.equals(hash(password))) return rs.getInt("id");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
