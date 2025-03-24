import java.util.*;

class Flight {
    public String origin;
    public String dest;
    public String op_unique_carrier;
    /* other fields from the real data intentionally omitted */

    public Flight(String origin, String dest, String op_unique_carrier) {
        this.origin = origin;
        this.dest = dest;
        this.op_unique_carrier = op_unique_carrier;
    }
}

class OutputRow {
    public int competition_factor;
    public int count;

    public OutputRow(int competition_factor, int count) {
        this.competition_factor = competition_factor;
        this.count = count;
    }

    @Override
    public String toString() {
        return "(" + competition_factor + ", " + count + ")";
    }
}

public class HW4 {
    // The "competition factor" on a route is the number of distinct carriers who fly
    // that route. For each distinct competition factor, find how many distinct routes
    // have that competition factor. Report columns competition_factor and num_routes.
    // Sort the output by competition factor from highest to lowest.
    public static List<OutputRow> problem2(List<Flight> flights) {
        // for each route find the list of distinct carriers that fly that route
        Map<String, Set<String>> routeToCarriers = new HashMap<>();
        for (Flight flight : flights) {
            String nextFlight = "(" + flight.origin + ", " + flight.dest + ")";
            if (!routeToCarriers.containsKey(nextFlight)) {
                routeToCarriers.put(nextFlight, new HashSet<>());
            }
            routeToCarriers.get(nextFlight).add(flight.op_unique_carrier);
        }
        // for each route find the number of distinct carriers that fly that route (ie: competition factor)
        Map<String, Integer> routeToNumCarrier = new HashMap<>();
        for (String route : routeToCarriers.keySet()) {
            int nextCompetitionFactor = routeToCarriers.get(route).size();
            routeToNumCarrier.put(route, nextCompetitionFactor);
        }
        // for each distinct competition factor, find the distinct routes
        // have that competition factor
        Map<Integer, Set<String>> competitionFactorToRoutes = new HashMap<>();
        for (String route : routeToNumCarrier.keySet()) {
            int nextCompetitionFactor = routeToNumCarrier.get(route);
            if (!competitionFactorToRoutes.containsKey(nextCompetitionFactor)) {
                competitionFactorToRoutes.put(nextCompetitionFactor, new HashSet<>());
            }
            competitionFactorToRoutes.get(nextCompetitionFactor).add(route);
        }
        // for each distinct competition factor, find the number of distinct routes
        // that have that competition factor
        Map<Integer, Integer> competitionFactorToNumRoutes = new HashMap<>();
        for (int competitionFactor : competitionFactorToRoutes.keySet()) {
            competitionFactorToNumRoutes.put(competitionFactor, competitionFactorToRoutes.get(competitionFactor).size());
        }
        List<OutputRow> res = new ArrayList<>();
        for (int competitionFactor : competitionFactorToNumRoutes.keySet()) {
            res.add(new OutputRow(competitionFactor, competitionFactorToNumRoutes.get(competitionFactor)));
        }
        Collections.sort(res, (a, b) -> b.competition_factor - a.competition_factor);
        return res;
    }

    public static void main(String[] args) {
        List<Flight> mock_flights = new ArrayList<>();
        mock_flights.add(new Flight("SEA", "ORD", "Alaska"));
        mock_flights.add(new Flight("SEA", "ORD", "United"));
        mock_flights.add(new Flight("SEA", "ORD", "Delta"));

        mock_flights.add(new Flight("SEA", "SFO", "Alaska"));
        mock_flights.add(new Flight("SEA", "SFO", "Delta"));

        mock_flights.add(new Flight("SFO", "LAX", "Alaska"));
        mock_flights.add(new Flight("SFO", "LAX", "Delta"));

        mock_flights.add(new Flight("SFO", "ORD", "United"));

        mock_flights.add(new Flight("SEA", "JNU", "Alaska"));

        List<OutputRow> output = problem2(mock_flights);
        for (OutputRow row : output) {
            System.out.println(row);
        }
    }
}

// Expected output:
//
//     (3, 1)
//     (2, 2)
//     (1, 2)
//
// Your code will be tested on other data, so do not hard code the answer.
