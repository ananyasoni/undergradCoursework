import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("General Case 1")
    public void firstCaseTest() {
        int budget = 500;
        List<Location> loci = new ArrayList<>();
        Location firstLoc = new Location("Location #1", 100, 400);
        Location secondLoc = new Location("Location #2", 150, 600);
        loci.add(firstLoc);
        loci.add(secondLoc);


        Set<Location> expected = new HashSet<Location>();
        expected.add(firstLoc);

        Set<Location> actual = Client.allocateRelief(500, loci).getLocations();
        assertEquals(expected, actual, "Allocate Relief picked " + actual + " instead of " + expected);
    }

    @Test
    @DisplayName("Different Costs and Different Population Sizes: ")
    public void secondCaseTest() {
        int budget = 100;
        List<Location> loci = new ArrayList<>();
        Location firstLoc = new Location("Location #1", 150, 100);
        Location secondLoc = new Location("Location #2", 100, 50);
        loci.add(firstLoc);
        loci.add(secondLoc);

        Set<Location> expected = new HashSet<Location>();
        expected.add(firstLoc);
        Set<Location> actual = Client.allocateRelief(budget, loci).getLocations();
        assertTrue(expected.equals(actual));
    }

    @Test
    @DisplayName("Test tie case: ")
    public void thirdCaseTest() {
        int budget = 1000;
        List<Location> loci = new ArrayList<>();
        Location firstLoc = new Location("Location #1", 500, 200);
        Location secondLoc = new Location("Location #2", 500, 600);
        //over budget
        Location thirdLoc = new Location("Location #3", 10000, 2000);
        Location fourthLoc = new Location("Location #4", 1000, 1000);
        loci.add(firstLoc);
        loci.add(secondLoc);
        loci.add(thirdLoc);
        loci.add(fourthLoc);

        Set<Location> expected = new HashSet<Location>();
        expected.add(firstLoc);
        expected.add(secondLoc);
        Set<Location> actual = Client.allocateRelief(budget, loci).getLocations();
        assertTrue(expected.equals(actual));
    }
}
