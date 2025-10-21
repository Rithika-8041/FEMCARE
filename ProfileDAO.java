import java.sql.*;

public class ProfileDAO {

    public static boolean saveProfile(Integer userId, String name, String weight, String height) {
        String checkSql = "SELECT user_id FROM profiles WHERE user_id=?";
        String insertSql = "INSERT INTO profiles(user_id,name,weight,height) VALUES(?,?,?,?)";
        String updateSql = "UPDATE profiles SET name=?, weight=?, height=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, userId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                PreparedStatement psUpd = conn.prepareStatement(updateSql);
                psUpd.setString(1, name);
                psUpd.setString(2, weight);
                psUpd.setString(3, height);
                psUpd.setInt(4, userId);
                psUpd.executeUpdate();
            } else {
                PreparedStatement psIns = conn.prepareStatement(insertSql);
                psIns.setInt(1, userId);
                psIns.setString(2, name);
                psIns.setString(3, weight);
                psIns.setString(4, height);
                psIns.executeUpdate();
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public static UserProfile getProfile(Integer userId) {
        String sql = "SELECT name,weight,height FROM profiles WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserProfile p = new UserProfile();
                p.userId = userId;
                p.name = rs.getString("name");
                p.weight = rs.getString("weight");
                p.height = rs.getString("height");
                return p;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static class UserProfile { 
        Integer userId; 
        String name; 
        String weight; 
        String height; 
    }
}
