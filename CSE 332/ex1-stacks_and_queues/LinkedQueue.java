public class LinkedQueue<E> implements MyQueue<E>{
    private int size;
    private ListNode<E> front;
    private ListNode<E> back;

    public LinkedQueue() {
        this.size = 0;
        this.front = null;
        this.back = null;
    }

    // Adds an item into the queue.
    public void enqueue(E item) {
        // empty queue case
        if (back == null) {
            this.back = new ListNode(item);
            this.front = back;
        } else {
            this.back.next = new ListNode(item);
            this.back = back.next;
        }
        size++;
    }

    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        E data = this.front.data;
        this.front = this.front.next;
        this.size--;
        // if the queue is now empty back and
        // front should both be null references
        if (this.size == 0) {
            this.back = null;
        }
        return data;
    }

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }

    // Return the number of items currently in the queue
    public int size() {
        return this.size;
    }

    // Returns a boolean to indicate whether the queue has items
    public boolean isEmpty() {
        return this.size == 0;
    }

    private static class ListNode<E>{
        private final E data;
        private ListNode<E> next;

        private ListNode(E data, ListNode<E> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(E data){
            this.data = data;
        }
    }
}
