import java.util.*;

class Edge {
    public final int source;
    public final int destination;

    public Edge(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "(" + this.source + ", " + this.destination + ")";
    }
}


public class hw1 {
    // Returns only the source for each tuple in edges.
    // Does not include duplicate elements.
    public static List<Integer> sourcesNoDups(List<Edge> edges) {
        List<Integer> res = new ArrayList<>();
        for (Edge edge : edges) {
            // check if source of edge is already in res, if not
            // add to res
            boolean unique = true;
            for (int source : res) {
                if (source == edge.source) {
                    unique = false;
                    break;
                }
            }
            if (unique) {
                res.add(edge.source);
            }
        }
        return res;
    }

    // Returns all tuples of edges where source is less than destination.
    // The output is sorted by destination
    public static List<Edge> edgesWithSmallerSource(List<Edge> edges) {
        List<Edge> res = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.source < edge.destination) {
                res.add(edge);
            }
        }
        // sort res by destination
        Collections.sort(res, (a, b) -> a.destination - b.destination);
        return res;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(11, 6));
        edges.add(new Edge(2, 26));
        edges.add(new Edge(2, 4));
        edges.add(new Edge(5, 5));
        edges.add(new Edge(5, 5));
        edges.add(new Edge(5, 5));
        edges.add(new Edge(5, 5));
        edges.add(new Edge(4, 5));
        edges.add(new Edge(1, 5));
        edges.add(new Edge(11, 5));

        System.out.println(edges);
        System.out.println(sourcesNoDups(edges));
        System.out.println(edgesWithSmallerSource(edges));
        System.out.println(edges);
    }
}

// Expected output:
//
// [(11, 6), (2, 26), (2, 4), (5, 5)]
// [11, 2, 5]
// [(2, 4), (2, 26)]
// [(11, 6), (2, 26), (2, 4), (5, 5)]
//
//
// Note that you are allowed to print the second line (ie, the result of
// sourcesNoDups) in any order, but the other lines should be exactly as
// presented.
//
// Your code will be tested on other data, too, not just the provided main
// method, so do not try to hard code the answers.