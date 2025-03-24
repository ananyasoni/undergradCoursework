import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private HW2 hw2;

    @BeforeEach
    void setUp() {
        hw2 = new HW2();
        System.out.println("==== Starting Test ====");
    }

    @AfterEach
    void tearDown() {
        System.out.println("==== Ending Test ====\n");
        DBUtils.closeConnection(hw2.conn);
    }

    @Test
    void testProblem3() throws SQLException {
        System.out.println("Running testProblem3...\n");
        hw2.conn = DBUtils.connect(HW2.DB_PATH);
        ResultSet results = hw2.problem3(5000);

        int expectedCardinality = 2;
        int actualCardinality = DBUtils.printResultSet(results);

        assertEquals(expectedCardinality, actualCardinality, "Problem 3 failed.");
    }
}
