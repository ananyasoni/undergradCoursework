package DebuggingMystery;
import java.util.*;

public class Mystery {
    private int value;
    private Random randy;

    public Mystery(int seed) {
        randy = new Random(seed);
        value = randy.nextInt(1, seed);
    }

    public int mysterify(int iterations) {
        for (int i = 0; i < iterations; i++) {
            int rand = randy.nextInt(1, value);
            System.out.println(rand);
            value *= rand;
            System.out.println("value " +  value);
        }
        return value;
    }
}
