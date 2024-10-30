public interface MyQueue<T> {

    // Adds an item into the queue.
    public void enqueue(T item);


    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T dequeue();

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T peek();

    // Return the number of items currently in the queue
    public int size();

    // Returns a boolean to indicate whether the queue has items
    public boolean isEmpty();
}
