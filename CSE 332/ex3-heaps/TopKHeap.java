import java.util.Map;
import java.util.HashMap;
import java.util.List;

public  class TopKHeap<T extends Comparable<T>> {
    private BinaryMinHeap<T> topK; // Holds the top k items
    private BinaryMaxHeap<T> rest; // Holds all items other than the top k
    private int size; // Maintains the size of the data structure
    private final int k; // The value of k
    private Map<T, MyPriorityQueue<T>> itemToHeap; // Keeps track of which heap contains each item.

    // Creates a topKHeap for the given choice of k.
    public TopKHeap(int k){
        topK = new BinaryMinHeap<>();
        rest = new BinaryMaxHeap<>();
        size = 0;
        this.k = k;
        itemToHeap = new HashMap<>();
    }

    // Returns a list containing exactly the
    // largest k items. The list is not necessarily
    // sorted. If the size is less than or equal to
    // k then the list will contain all items.
    // The running time of this method should be O(k).
    public List<T> topK() {
        return this.topK.toList();
    }

    // Add the given item into the data structure.
    // The running time of this method should be O(log(n)+log(k)).
    public void insert(T item) {
        this.size++;
        // if topK consists of less than k items than insert item into topK
        if (this.topK.size() < k) {
            topK.insert(item);
            this.itemToHeap.put(item, this.topK);
        } else {
            // if the min item in topK has a lower priority than the
            // item being inserted by this method then remove
            // the min item in topK and add it to rest and insert the item being
            // added by this method to topK
            if (item.compareTo(this.topK.peek()) > 0) {
                T minItemInTopK = this.topK.extract();
                this.topK.insert(item);
                this.rest.insert(minItemInTopK);
                this.itemToHeap.put(item, this.topK);
                this.itemToHeap.put(minItemInTopK, this.rest);
            } else {
                // otherwise insert the item in rest
                this.rest.insert(item);
                this.itemToHeap.put(item, this.rest);
            }
        }

    }

    // Indicates whether the given item is among the
    // top k items. Should return false if the item
    // is not present in the data structure at all.
    // The running time of this method should be O(1).
    public boolean isTopK(T item){
        return itemToHeap.get(item) == topK;
    }

    // To be used whenever an item's priority has changed.
    // The input is a reference to the items whose priority
    // has changed. This operation will then rearrange
    // the items in the data structure to ensure it
    // operates correctly.
    // Throws an IllegalArgumentException if the item is
    // not a member of the heap.
    // The running time of this method should be O(log(n)+log(k)).
    public void updatePriority(T item) {
        if (!this.itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("item does not exist!");
        }
        this.itemToHeap.get(item).updatePriority(item);
        // if topK contains k items AND the min item in topK has a lower priority than the
        // max item in rest then  remove the min item in topK and add it to rest and remove
        // the max item from rest and add it to topK
        if (this.topK.size() == k && this.topK.peek().compareTo(this.rest.peek()) < 0) {
            T minItemInTopK = this.topK.extract();
            T maxItemInRest = this.rest.extract();
            this.topK.insert(maxItemInRest);
            this.rest.insert(minItemInTopK);
            this.itemToHeap.put(maxItemInRest, this.topK);
            this.itemToHeap.put(minItemInTopK, this.rest);
        }
    }

    // Removes the given item from the data structure
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log(n)+log(k)).
    public void remove(T item) {
        if (!this.itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("item does not exist!");
        }
        this.itemToHeap.get(item).remove(item);
        this.size--;
    }
}
