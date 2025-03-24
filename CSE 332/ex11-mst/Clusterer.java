import java.util.*;

public class Clusterer {
    private List<List<WeightedEdge<Integer, Double>>> adjList; // the adjacency list of the original graph
    private List<List<WeightedEdge<Integer, Double>>> mstAdjList; // the adjacency list of the minimum spanning tree
    private List<List<Integer>> clusters; // a list of k points, each representing one of the clusters.
    private double cost; // the distance between the closest pair of clusters

    public Clusterer(double[][] distances, int k){
        this.mstAdjList = new ArrayList<>();
        this.adjList = new ArrayList<>();
        this.clusters = new ArrayList<>();
        for (int i = 0; i < distances.length; i++) {
            this.adjList.add(new ArrayList<>());
            this.mstAdjList.add(new ArrayList<>());
            for (int j = 0; j < distances[0].length; j++) {
                if (distances[i][j] != 0) {
                    this.adjList.get(i).add(new WeightedEdge<Integer,Double>(i, j, distances[i][j]));
                }
            }
        }
        this.prims(0);
        this.makeKCluster(k);
    }

    // implement Prim's algorithm to find a MST of the graph.
    // in my implementation I used the mstAdjList field to store this.
    // Start with an empty tree 𝐴
    // Pick a start node
    //  Repeat 𝑉 − 1 times:
    //  Add the min-weight edge which connects to node
    //  in 𝐴 with a node not in 𝐴
    private void prims(int start) {

        Set<Integer> visited = new HashSet<>();
        PriorityQueue<WeightedEdge<Integer, Double>> pq = new PriorityQueue<>();
        pq.add(new WeightedEdge<>(start, start, 0.0));
        while (!pq.isEmpty()) {
            // choose the next smallest edge to take
            WeightedEdge<Integer, Double> minEdge = pq.poll();
            int source = minEdge.source;
            int destination = minEdge.destination;

            // If the destination node is already visited, skip the next smallest edge
            // in the priority queue
            if (visited.contains(destination)) {
                continue;
            }
            // Mark the destination node as visited
            visited.add(destination);
            // Add the edge to the MST adjacency list only if
            // it is not a self edge
            if (source != destination) {
                mstAdjList.get(source).add(new WeightedEdge<>(source, destination, minEdge.weight));
                mstAdjList.get(destination).add(new WeightedEdge<>(destination, source, minEdge.weight));
            }
            // Add outgoing edges from current node to the priority queue
            for (WeightedEdge<Integer, Double> nextEdge : adjList.get(destination)) {
                int neighbor = nextEdge.destination;
                if (!visited.contains(neighbor)) {
                    pq.add(nextEdge);
                }
            }
        }
    }


    // After making the minimum spanning tree, use this method to
    // remove its k-1 heaviest edges, then assign integers
    // to clusters based on which nodes are still connected by
    // the remaining MST edges.
    private void makeKCluster(int k) {

        // identify the k-1 largest edges used in the minimum spanning tree. Then, remove them
        // keep track of the minimum weight edge removed (ie: the cost of the last edge removed)
        List<WeightedEdge<Integer, Double>> allEdgesInMST = new ArrayList<>();
        for (List<WeightedEdge<Integer, Double>> edges : this.mstAdjList) {
            for (WeightedEdge<Integer, Double> nextEdge : edges) {
                if (nextEdge.source.compareTo(nextEdge.destination) < 0) {
                    allEdgesInMST.add(new WeightedEdge<Integer, Double>(nextEdge.source, nextEdge.destination, nextEdge.weight));
                } else {
                    allEdgesInMST.add(new WeightedEdge<Integer, Double>(nextEdge.destination, nextEdge.source, nextEdge.weight));
                }
            }
        }
        Collections.sort(allEdgesInMST);
        // remove 2(k - 1) heaviest edges (ie: k - 1 unique edges)
        // since the graph is undirected and edges are bidirectional
        for (int i = 0; i < k - 1; i++) {
            // remove the heaviest edge from the list of all edges in MST
            // and from the MST
            allEdgesInMST.remove(allEdgesInMST.size() - 1);
            WeightedEdge<Integer, Double> toRemove = allEdgesInMST.remove(allEdgesInMST.size() - 1);
            this.mstAdjList.get(toRemove.source).removeIf(edge -> edge.destination.equals(toRemove.destination));
            this.mstAdjList.get(toRemove.destination).removeIf(edge -> edge.destination.equals(toRemove.source));
            this.cost = toRemove.weight;
        }
        // identify clusters of connected nodes
        // use Breadth-First Search (BFS) to find connected nodes of clusters
        int[] clusterIds = new int[mstAdjList.size()];
        int currentClusterId = 1;
        for (int startNode = 0; startNode < clusterIds.length; startNode++) {
            // check if node not yet visited
            if (clusterIds[startNode] == 0) {
                // Perform BFS to assign nodes to current cluster id
                List<Integer> currentCluster = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();
                queue.add(startNode);
                clusterIds[startNode] = currentClusterId;

                while (!queue.isEmpty()) {
                    int currentNode = queue.poll();
                    // Add node to current cluster
                    currentCluster.add(currentNode);
                    for (WeightedEdge<Integer, Double> nextEdge : mstAdjList.get(currentNode)) {
                        int neighbor = nextEdge.destination;
                        // make sure node hasn't been assigned
                        // to a cluster
                        if (clusterIds[neighbor] == 0) {
                            clusterIds[neighbor] = currentClusterId;
                            queue.add(neighbor);
                        }
                    }
                }
                this.clusters.add(currentCluster);
                currentClusterId++;
            }
        }
    }

    public List<List<Integer>> getClusters(){
        return clusters;
    }

    public double getCost(){
        return cost;
    }

}
