import java.sql.*;

public class CycleDAO {

    public static boolean saveCycle(Integer userId, String lastPeriod, int cycleLength) {
        String checkSql = "SELECT user_id FROM cycles WHERE user_id=?";
        String insertSql = "INSERT INTO cycles(user_id,last_period,cycle_length) VALUES(?,?,?)";
        String updateSql = "UPDATE cycles SET last_period=?, cycle_length=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, userId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                PreparedStatement psUpd = conn.prepareStatement(updateSql);
                psUpd.setDate(1, Date.valueOf(lastPeriod));
                psUpd.setInt(2, cycleLength);
                psUpd.setInt(3, userId);
                psUpd.executeUpdate();
            } else {
                PreparedStatement psIns = conn.prepareStatement(insertSql);
                psIns.setInt(1, userId);
                psIns.setDate(2, Date.valueOf(lastPeriod));
                psIns.setInt(3, cycleLength);
                psIns.executeUpdate();
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public static CycleInfo getCycle(Integer userId) {
        String sql = "SELECT last_period,cycle_length FROM cycles WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CycleInfo c = new CycleInfo();
                c.userId = userId;
                c.lastPeriodDate = rs.getDate("last_period").toString();
                c.cycleLength = rs.getInt("cycle_length");
                return c;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static class CycleInfo {
        Integer userId;
        String lastPeriodDate;
        int cycleLength;
    }
}
