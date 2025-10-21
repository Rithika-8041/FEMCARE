import java.sql.*;
import java.time.LocalDateTime;

public class SymptomDAO {

    public static void logSymptom(Integer userId, String symptom, String note) {
        String sql = "INSERT INTO symptoms(user_id,symptom,note,ts) VALUES(?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, symptom);
            ps.setString(3, note);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
            System.out.println("Logged symptom: " + symptom + " for user " + userId);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
