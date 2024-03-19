// Ananya Soni 
// 02/21/2024
// CSE 123
// P2: Disaster Relief
// This class allows a user to find all possible allocations 
// based on the budget and the locations in need of help  
// as well as the most strategic allocation of resources to save 
// the most people with the provided budget.
import java.util.*;

public class Client {
    private static Random rand = new Random();

    public static void main(String[] args) throws Exception {
        // List<Location> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Location> scenario = createSimpleScenario();
        System.out.println(scenario);
        
        double budget = 2000;
        Allocation allocation = allocateRelief(budget, scenario);
        printResult(allocation, budget);
    }

    // Behavior: 
    //   - This method takes a budget and a list of locations and computes the Allocation 
    //     of resources that will result in the most people being helped with the given budget. 
    //     If there is more than one Allocation that will result in the most people being helped, 
    //     this method returns the Allocation that costs the least. If there is more than one 
    //     Allocation that will result in the most people being helped for the lowest cost, 
    //     this method returns the first found instance of such a Allocation.
    // Parameters:
    //   - double budget: maximum amount that can be used for an Allocation
    //   - List<Location> sites: list of locations in need of relief
    // Returns:
    //   - the best Allocation of resources such that the most people get helped
    // Exceptions:
    //   - N/A   
    public static Allocation allocateRelief(double budget, List<Location> sites) {
        //all possible allocations within the given budget
        Set<Allocation> possibleAllocations = generateOptions(budget, sites, new Allocation(),
                                                new HashSet<Allocation>());
        return findBestAllocation(possibleAllocations);    
    }

    // Behavior: 
    //   - This method generates and returns all the possible Allocations 
    //     within the provided budget. 
    // Parameters:
    //   - double budget: maximum amount that can be used for an Allocation
    //   - List<Location> sites: list of locations in need of relief
    //   - Allocation currAllocation: keeps track of the current Allocation being
    //     explored
    //   - Set<Allocation> allPossibleAllocations: set of all possible Allocations
    // Returns:
    //   - the set of all possible Allocations within the provided budget
    // Exceptions:
    //   - N/A   
    private static Set<Allocation> generateOptions(double budget, List<Location> sites, 
                        Allocation currAllocation, Set<Allocation> allPossibleAllocations) {
        //if the budget is greater than or equal 0 we want to add it to the set of all possible 
        //allocations
        if(budget >= 0) {
            allPossibleAllocations.add(currAllocation); 
            //implicit base case --> when list of locations is empty the size of sites is 0 
            //because we remove the nextLocation from sites in the choose phase
            for(int i = 0; i < sites.size(); i++) {
                    //choose: the next decision is the next location
                    //in the list of locations
                    Location nextLocation = sites.get(i);
                    currAllocation = currAllocation.withLoc(nextLocation);
                    sites.remove(i);
                    //explore:
                    //explore all the possible Allocations that can be generated with the 
                    //current budget after adding the nextLocation, list of sites, and current 
                    //updated Allocation
                    generateOptions(budget - nextLocation.getCost(), sites, currAllocation, 
                                    allPossibleAllocations);
                    //unchoose - opposite of choose 
                    sites.add(i, nextLocation);
                    currAllocation = currAllocation.withoutLoc(nextLocation);       
            }
        }
        return allPossibleAllocations;
    }

    // Behavior: 
    //   - This method finds the "best" Allocation given a set of Allocations. The
    //     best Allocation is defined as the the allocation of resources that will result in the 
    //     most people being helped. If there is more than one Allocation that will result in the 
    //     most people being helped, this method returns the Allocation that costs the least. 
    //     If there is more than one Allocation that will result in the most people being helped  
    //     for the lowest cost, this method returns the first found instance of such a Allocation.
    // Parameters:
    //   - Set<Allocation> allocations: set of Allocations 
    // Returns:
    //   - the best Allocation
    // Exceptions:
    //   - N/A   
    private static Allocation findBestAllocation(Set<Allocation> allocations) {
        int maxPeople = 0;
        double lowestCost = (double)Integer.MAX_VALUE;
        Allocation bestAllocation = new Allocation();
        for(Allocation allocation : allocations) {
            if(allocation.totalPeople() > maxPeople) {
                maxPeople = allocation.totalPeople();
                lowestCost = allocation.totalCost();
                bestAllocation = allocation;
            //tie-case
            } else if(allocation.totalPeople() == maxPeople && 
                        allocation.totalCost() < lowestCost) {
                maxPeople = allocation.totalPeople();
                lowestCost = allocation.totalCost();
                bestAllocation = allocation;
            }
        }
        return bestAllocation;
    }

    public static void printResult(Allocation alloc, double budget) {
        System.out.println("Result: ");
        System.out.println("  " + alloc);
        System.out.println("  People helped: " + alloc.totalPeople());
        System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
        System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));
    }

    public static List<Location> createRandomScenario(int numLocs, int minPop, int maxPop, double minCostPer, double maxCostPer) {
        List<Location> result = new ArrayList<>();

        for (int i = 0; i < numLocs; i++) {
            int pop = rand.nextInt(minPop, maxPop + 1);
            double cost = rand.nextDouble(minCostPer, maxCostPer) * pop;
            result.add(new Location("Location #" + i, pop, round2(cost)));
        }
        return result;
    }

    public static List<Location> createSimpleScenario() {
        List<Location> result = new ArrayList<>();

        result.add(new Location("Location #1", 50, 500));
        result.add(new Location("Location #2", 100, 700));
        result.add(new Location("Location #3", 60, 1000));
        result.add(new Location("Location #4", 20, 1000));
        result.add(new Location("Location #5", 200, 900));

        return result;
    }    

    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}

