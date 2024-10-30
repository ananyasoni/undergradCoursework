import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public abstract class BinaryHeap <T extends Comparable<T>> implements MyPriorityQueue<T> {
    protected int size; // Maintains the size of the data structure
    protected T[] arr; // The array containing all items in the data structure
    // index 0 must be utilized
    protected Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    // move the item at index i "rootward" until
    // the heap property holds
    protected void percolateUp(int i) {}

    // move the item at index i "leafward" until
    // the heap property holds
    protected void percolateDown(int i) {}

    // Adds the given item into the priority queue
    public void insert(T item) {
        if (size == this.arr.length) {
            resize();
        }
        this.arr[this.size] = item;
        // add item to hash map containing
        // items and their corresponding indexes
        this.itemToIndex.put(item, this.size);
        // percolate up
        percolateUp(this.size);
        this.size++;
    }

    // copy all items into a larger array double the size of the previous array
    // to make more room for more elements in the heap
    private void resize() {
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length*2);
        for (int i = 0; i < this.arr.length; i++) {
            larger[i] = this.arr[i];
        }
        this.arr = larger;
    }


    // removes and returns the "top priority" item from the priority queue
    // Throws an IllegalStateException if the data structure is empty.
    // The running time of this method should be O(log n).
    public T extract() {
        if (isEmpty()) {
            throw new IllegalStateException("Empty!");
        }
        T minItem = this.arr[0];
        this.arr[0] = this.arr[this.size - 1];
        this.size--;
        // percolate down
        percolateDown(0);
        // remove minItem from hash map containing
        // items and their corresponding indexes
        this.itemToIndex.remove(minItem);
        return minItem;
    }

    // Removes the item at the given index from the min heap
    // Maintains the heap property after execution
    // The running time of this method should be O(log n).
    private T remove(int index) {
        T removedItem = this.arr[index];
        this.arr[index] = this.arr[this.size - 1];
        this.itemToIndex.put(this.arr[this.size - 1], index);
        this.size--;
        // percolate down
        percolateDown(index);
        return removedItem;
    }

    // Removes the given item from the min heap
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log n).
    public void remove(T item) {
        if(!itemToIndex.containsKey(item)){
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
        }
        remove(itemToIndex.get(item));
        this.itemToIndex.remove(item);
    }

    // To be called after an item's priority has been changed
    // by client code.
    // The input is a reference to the items whose priority
    // has changed. This operation will then update the
    // underlying data structure as necessary to ensure
    // it operates correctly.
    // The running time of this method should be O(log n).
    protected void updatePriority(int index) {}

    // This method gets called after the client has
    // changed an item in a way that may change its
    // priority. In this case, the client should call
    // updatePriority on that changed item so that
    // the heap can restore the heap property.
    // Throws an IllegalArgumentException if the given
    // item is not an element of the priority queue.
    // The input is a reference to the item whose priority
    // has changed. This operation will then update the
    // underlying data structure as necessary to ensure
    // it operates correctly.
    // The running time of this method should be O(log n).
    public void updatePriority(T item) {
        if(!itemToIndex.containsKey(item)) {
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
        }
        updatePriority(itemToIndex.get(item));
    }

    // Returns a boolean indicating whether the
    // heap is empty
    public boolean isEmpty(){
        return size == 0;
    }

    // Returns the size of the heap
    public int size(){
        return size;
    }

    // Returns the topmost element in the
    // heap ie: the smallest item
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Empty!");
        }
        return arr[0];
    }

    // Returns all contents of the priority queue as a list.
    // The running time of this method should be O(n).
    public List<T> toList(){
        List<T> copy = new ArrayList<>();
        for(int i = 0; i < size; i++){
            copy.add(i, arr[i]);
        }
        return copy;
    }

    // Returns a string representation of the heap
    public String toString(){
        if(size == 0){
            return "[]";
        }
        String str = "[(" + arr[0] + " " + itemToIndex.get(arr[0]) + ")";
        for(int i = 1; i < size; i++ ){
            str += ",(" + arr[i] + " " + itemToIndex.get(arr[i]) + ")";
        }
        return str + "]";
    }
}
