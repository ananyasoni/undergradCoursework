import java.util.*;

public class DonorGraph {
    private List<List<Match>> adjList;

    // The donatingTo array indicates which repient each donor is
    // affiliated with. Specifically, the donor at index i has volunteered
    // to donate a kidney on behalf of recipient donatingTo[i].
    // The matchScores 2d array gives the match scores associated with each
    // donor-recipient pair. Specifically, matchScores[x][y] gives the
    // HLA score for donor x and recipient y.
    public DonorGraph(int[] donorToBenefit, int[][] matchScores){
        // add edge from recipient 1 to recipient 2
        // if there exists a donor such that the donor's
        // beneficiary is recipient 1 and the donor's
        // match is recipient 2 and no such edge existed
        // before (no duplicate edges)
        adjList = new ArrayList<>();
        for (int i = 0; i < matchScores[0].length; i++) {
            adjList.add(new ArrayList<Match>());
        }
        // check each donor and their beneficiary and match
        for (int donor = 0; donor < donorToBenefit.length; donor++) {
            int beneficiary = donorToBenefit[donor];
            int[] currDonorsMatchScores = matchScores[donor];
            for (int recipient = 0; recipient < currDonorsMatchScores.length; recipient++) {
                // valid match - HLA Score >= 60 between donor and recipient
                if (currDonorsMatchScores[recipient] >= 60) {
                    // add edge from beneficiary to donorMatch in adjList at index
                    // beneficiary if the edge from beneficiary to donorMatch doesn't
                    // already exist
                    Match nextMatch = new Match(donor, beneficiary, recipient);
                    boolean edgeExists = false;
                    List<Match> edges = adjList.get(beneficiary);
                    for (Match nextEdge : edges) {
                        if (nextEdge.beneficiary == nextMatch.beneficiary && nextEdge.recipient == nextMatch.recipient) {
                            edgeExists = true;
                        }
                    }
                    if (!edgeExists) {
                        adjList.get(beneficiary).add(nextMatch);
                    }
                }
            }
        }
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public boolean isAdjacent(int start, int end){
        for(Match m : adjList.get(start)){
            if(m.recipient == end)
                return true;
        }
        return false;
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public int getDonor(int beneficiary, int recipient){
        for(Match m : adjList.get(beneficiary)){
            if(m.recipient == recipient)
                return m.donor;
        }
        return -1;
    }



    // returns true or false to indicate whether there
    // is some cycle which includes the given recipient.
    public boolean hasCycle(int recipient) {
        return !findCycle(recipient).isEmpty();
    }

    // returns a chain of matches to make a donor cycle
    // which includes the given recipient.
    // Returns an empty list if no cycle exists.
    public List<Match> findCycle(int recipient) {
        return findCycleHelper(recipient, recipient, new HashSet<>(), new ArrayList<>());
    }

    public List<Match> findCycleHelper(int recipient, int curr, Set<Integer> visited, List<Match> cycleSoFar) {
        visited.add(curr);
        for (Match match : this.adjList.get(curr)) {
            // Check for a cycle that includes the target recipient
            if (match.recipient == recipient && !cycleSoFar.isEmpty()) {
                cycleSoFar.add(match); // Complete the cycle
                return cycleSoFar;
            }
            // Visit unvisited neighbors - explore
            if (!visited.contains(match.recipient)) {
                cycleSoFar.add(match);
                List<Match> result = findCycleHelper(recipient, match.recipient, visited, cycleSoFar);
                if (!result.isEmpty()) {
                    return result;
                }
                // Backtrack - unchoose
                cycleSoFar.remove(cycleSoFar.size() - 1);
            }
        }
        return new ArrayList<>(); // No cycle found
    }
}

//    // returns a chain of matches to make a donor cycle
//    // which includes the given recipient.
//    // Returns an empty list if no cycle exists.
//    public List<Match> findCycle(int recipient) {
//        return findCycleHelper(recipient, recipient, new ArrayList<>(), new ArrayList<>());
//    }
//
//    public List<Match> findCycleHelper(int recipient, int curr, List<Integer> visited, List<Match> cycleSoFar) {
//        visited.add(curr);
//        for (Match match : this.adjList.get(curr)) {
//            // check whether the current neighbor recipient matches the target recipient which means there is a cycle
//            if (match.recipient == recipient) {
//                cycleSoFar.add(match);
//                return cycleSoFar;
//            }
//            if (!visited.contains(match.recipient)) {
//                cycleSoFar.add(match);
//                List<Match> result = findCycleHelper(recipient, match.recipient, visited, cycleSoFar);
//                if (!result.isEmpty()) {
//                    return result;
//                }
//                // Backtrack - unchoose
//                cycleSoFar.remove(cycleSoFar.size() - 1);
//            }
//        }
//        return new ArrayList<>(); // no valid cycle found through current path
//    }
//}
