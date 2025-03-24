import java.util.*;

class Flight {
    public String tail_num;
    public boolean canceled;
    /* other fields from the real data intentionally omitted */

    public Flight(String tail_num, boolean canceled) {
        this.tail_num = tail_num;
        this.canceled = canceled;
    }
}

class OutputRow {
    public String tail_num;
    public int count;

    public OutputRow(String tail_num, int count) {
        this.tail_num = tail_num;
        this.count = count;
    }

    @Override
    public String toString() {
        return "(" + tail_num + ", " + count + ")";
    }
}

public class HW3 {
    public static List<OutputRow> problem3(List<Flight> flights) {
        // Find how many non-canceled flights each tail number made.
        // Sort the output by the count from highest to lowest.
        // (Breaking ties between equal counts arbitrarily.) If a tail number has
        // canceled flights but no non-canceled flights, do not include it.
        List<OutputRow>  res = new ArrayList<>();
        Map<String, Integer> num_non_cancelled_flights = new HashMap<>();
        for (Flight f : flights) {
            if (f.canceled == false) {
                num_non_cancelled_flights.put(f.tail_num, num_non_cancelled_flights.getOrDefault(f.tail_num, 0) + 1);
            }
        }
        for (String tail_num : num_non_cancelled_flights.keySet()) {
            res.add(new OutputRow(tail_num, num_non_cancelled_flights.get(tail_num)));
        }
        Collections.sort(res, (a, b) -> b.count - a.count);
        return res;
    }

    public static void main(String[] args) {
        List<Flight> mock_flights = new ArrayList<>();
        mock_flights.add(new Flight("N45517", true));
        mock_flights.add(new Flight("N80847", false));
        mock_flights.add(new Flight("N1358V", false));
        mock_flights.add(new Flight("N80847", true));
        mock_flights.add(new Flight("N719SJ", false));
        mock_flights.add(new Flight("N1358V", true));
        mock_flights.add(new Flight("N80847", false));

        List<OutputRow> output = problem3(mock_flights);
        for (OutputRow row : output) {
            System.out.println(row);
        }
    }
}

// Expected output:
//
//     (N80847, 2)
//     (N719SJ, 1)
//     (N1358V, 1)
//
// (The two lines with count 1 may be printed in either order.)
//
// Your code will be tested on other data, so do not hard code the answer.
