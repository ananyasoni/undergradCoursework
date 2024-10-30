import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMaxHeap<T extends Comparable<T>> extends BinaryHeap<T> {

    public BinaryMaxHeap(){
        // Creates an array of type T. To create a new array
        // anywhere else (e.g. to resize) use same structure.
        // The second argument is the size of the new array.
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    // move the item at index i "rootward" until
    // the heap property holds
    protected void percolateUp(int i) {
        T curr = this.arr[i];
        int currIdx = i;
        while (currIdx > 0 && curr.compareTo(this.arr[(currIdx - 1) / 2]) > 0) {
            // swap child and parent node
            this.arr[currIdx] = this.arr[(currIdx - 1) / 2];
            this.itemToIndex.put(this.arr[(currIdx - 1) / 2], currIdx);
            this.arr[(currIdx - 1) / 2] = curr;
            this.itemToIndex.put(curr, (currIdx - 1) / 2);
            // update currIdx to the index of curr's parent node
            // previously
            currIdx = (currIdx - 1) / 2;
        }
    }

    // move the item at index i "leafward" until
    // the heap property holds
    protected void percolateDown(int i) {
        T curr = this.arr[i];
        int currIdx = i;
        int leftIdx = currIdx * 2 + 1;
        int rightIdx = currIdx * 2 + 2;
        // keep percolating down while curr is greater than one of its children and
        // curr is not a leaf node

        // make sure curr has at least one child
        // ie: not a leaf node
        while (leftIdx <= this.size - 1) {
            int swapIdx = rightIdx;
            // if curr's right child does not exist OR curr's left child has a smaller value than
            // curr's right child then swap curr with left child instead of right child
            if (rightIdx > this.size - 1 || this.arr[leftIdx].compareTo(this.arr[rightIdx]) > 0) {
                swapIdx = leftIdx;
            }
            // check if curr is smaller than its left or right child OR just its
            // left child if its right child does not exist and if smaller swap
            if (this.arr[swapIdx].compareTo(curr) > 0) {
                // swap case
                this.arr[currIdx] = this.arr[swapIdx];
                this.itemToIndex.put(this.arr[swapIdx], currIdx);
                this.arr[swapIdx] = curr;
                this.itemToIndex.put(curr, swapIdx);
                currIdx = swapIdx;
                leftIdx = currIdx * 2 + 1;
                rightIdx = currIdx * 2 + 2;
            } else {
                // do not swap and immediately return
                // the heap property holds
                return;
            }
        }
    }

    // To be called after an item's priority has been changed
    // by client code.
    // The input is a reference to the items whose priority
    // has changed. This operation will then update the
    // underlying data structure as necessary to ensure
    // it operates correctly.
    // The running time of this method should be O(log n).
    protected void updatePriority(int index) {
        // Determines whether to percolate up/down the item at the given index
        // if greater than parent just percolate up otherwise percolate down
        if (this.arr[index].compareTo(this.arr[(index - 1) / 2]) > 0) {
            percolateUp(index);
        } else {
            percolateDown(index);
        }
    }
}
