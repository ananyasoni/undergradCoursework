import java.util.List;

public class RangeTree{
    private OrderedDeletelessDictionary<Double, Range> byStart;
    private OrderedDeletelessDictionary<Double, Range> byEnd;
    private int size;

    public RangeTree(){
        byStart = new AVLTree<>();
        byEnd = new AVLTree<>();
        size = 0;
    }
    
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // Return the Range which starts at the given time
    // The running time is O(log n)
    public Range findByStart(Double start){
        return byStart.find(start);
    }

    // Return the Range which ends at the given time
    // The running time is O(log n)
    public Range findByEnd(Double end){
        return byEnd.find(end);
    }

    // Gives a list of Ranges sorted by start time.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Range> getRanges(){
        return byStart.getValues();
    }

    // Gives a sorted list of start times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getStartTimes(){
        return byStart.getKeys();
    }

    // Gives a sorted list of end times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getEndTimes(){
        return byEnd.getKeys();
    }

    // Identifies whether or not the given range conflicts with any
    // ranges that are already in the data structure.
    // If the data structure is empty, then it does not conflict
    // with any ranges, so we should return false.
    // The running time of this method should be O(log n)
    public boolean hasConflict(Range query) {
        if (isEmpty()) {
            return false;
        }
        // Check for overlap in the `byStart` avl tree
        Double nextByStartKey = byStart.findNextKey(query.start);
        Double prevByStartKey = byStart.findPrevKey(query.start);
        if (prevByStartKey != null) {
            Range prevByStart = byStart.find(prevByStartKey);
            if (overlap(query, prevByStart)) {
                return true;
            }
        }
        if (nextByStartKey != null) {
            Range nextByStart = byStart.find(nextByStartKey);
            if (overlap(query, nextByStart)) {
                return true;
            }
        }
        // Check for overlap in the `byEnd` avl tree
        Double nextByEndKey = byEnd.findNextKey(query.end);
        Double prevByEndKey = byEnd.findPrevKey(query.end);
        if (prevByEndKey != null) {
            Range prevByEnd = byEnd.find(prevByEndKey);
            if (overlap(query, prevByEnd)) {
                return true;
            }
        }
        if (nextByEndKey != null) {
            Range nextByEnd = byEnd.find(nextByEndKey);
            if (overlap(query, nextByEnd)) {
                return true;
            }
        }
        return false;
    }

    private boolean overlap(Range range1, Range range2) {
        return (Math.min(range1.end, range2.end) > Math.max(range1.start, range2.start));
    }

    // Inserts the given range into the data structure if it has no conflict.
    // Does not modify the data structure if it does have a conflict.
    // Return value indicates whether or not the item was successfully
    // added to the data structure.
    // Running time should be O(log n)
    public boolean insert(Range query){
        if (hasConflict(query)) {
            return false;
        }
        byStart.insert(query.start, query);
        byEnd.insert(query.end, query);
        size++;
        return true;
    }
}
