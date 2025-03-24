import java.sql.*;
import java.util.*;

public class DBUtils {

    public static Connection connect(String dbPath) throws SQLException {
        if (dbPath.isEmpty()) {
            throw new IllegalArgumentException("Missing path to .db file: Please fill this out in HW2.java");
        }
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    public static int printResultSet(ResultSet rs) {
        try {
            if (rs == null) {
                System.out.println("No results to display.");
                return 0;
            }
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Calculate column widths
            int[] columnWidths = new int[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnWidths[i - 1] = Math.max(metaData.getColumnName(i).length(), 15); // Minimum width of 15
            }

            // Read rows to determine max width for each column
            List<String[]> rows = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    row[i - 1] = value != null ? value : "NULL";
                    columnWidths[i - 1] = Math.max(columnWidths[i - 1], row[i - 1].length());
                }
                rows.add(row);
            }

            // Print headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-" + columnWidths[i - 1] + "s  ", metaData.getColumnName(i));
            }
            System.out.println();
            for (int width : columnWidths) {
                System.out.print("-".repeat(width + 2));
            }
            System.out.println();

            // Print rows
            for (String[] row : rows) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.printf("%-" + columnWidths[i] + "s  ", row[i]);
                }
                System.out.println();
            }

            System.out.printf("\nCardinality: %d rows\n", rows.size());
            return rows.size();
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }


    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
