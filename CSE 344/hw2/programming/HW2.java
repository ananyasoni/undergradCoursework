import java.sql.*;

public class HW2 {

    public static final String DB_PATH = "../hw2.db";
    public Connection conn;

    public ResultSet problem3(int distance) throws SQLException {
        String query = "SELECT DISTINCT cname, origin_city_name FROM flights JOIN carriers " +
                       "ON carriers.cid = flights.op_unique_carrier WHERE flights.distance > ?;";
        // Pre-compiles the query into a PreparedStatement Object
        PreparedStatement ps = conn.prepareStatement(query);
        ps.clearParameters();
        // Sets the first parameter (the first “?”) to the value of the
        // variable distance
        ps.setInt(1, distance);
        return ps.executeQuery();
    }
}
