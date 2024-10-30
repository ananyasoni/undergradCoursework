public class LinkedStack<T> implements MyStack<T> {
    private int size;
    private ListNode<T> front;

    public LinkedStack() {
        this.size = 0;
        front = null;
    }

    // Adds an item into the stack
    public void push(T item) {
        ListNode temp = this.front;
        this.front = new ListNode(item);
        this.front.next = temp;
        size++;
    }

    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = this.front.data;
        this.front = this.front.next;
        this.size--;
        return data;
    }

    // Returns the most recently added item in the stack
    // throws an IllegalStateException if the stack is empty
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return this.front.data;
    }

    // Returns the number of items in the stack
    public int size() {
        return this.size;
    }

    // Returns a boolean indicating whether the stack has items
    public boolean isEmpty() {
        return this.size == 0;
    }

    private static class ListNode<T>{
        private final T data;
        private ListNode<T> next;

        private ListNode(T data, ListNode<T> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(T data){
            this.data = data;
        }
    }
}
