public class ArrayQueue<T>  implements MyQueue<T> {
    private T[] queue;
    private int front;
    private int back;
    private int size;

    public ArrayQueue() {
        this.queue = (T[]) new Object[10];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    // Adds an item into the queue.
    public void enqueue(T item) {
        // resize queue when queue contains length - 1 elements
        // because back should only equal front when queue is
        // empty
        if (this.size == this.queue.length - 1) {
            // call function to resize queue
            resize(item);
        } else {
            this.queue[this.back] = item;
            this.back = (this.back + 1) % this.queue.length;
        }
        this.size++;
    }

    private void resize(T item) {
        T[] newQueue = (T[]) new Object[this.queue.length * 2];
        // add all queue.length - 1 items to the new array
        // double the size of the previous array
        int idx = this.front;
        int i = 0;
        while(i < this.queue.length - 1) {
           newQueue[i] = this.queue[idx];
           idx = (idx + 1) % this.queue.length;
           i++;
        }
        newQueue[i] = item;
        this.front = 0;
        this.back = i + 1;
        this.queue = newQueue;
    }

    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T data = this.queue[front];
        this.front = (this.front + 1) % this.queue.length;
        size--;
        return data;
    }

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return this.queue[this.front];
    }

    // Return the number of items currently in the queue
    public int size() {
        return this.size;
    }

    // Returns a boolean to indicate whether the queue has items
    public boolean isEmpty() {
        // note: back == front if and only if (<-->) the queue is empty
        return this.size == 0;
    }
}
