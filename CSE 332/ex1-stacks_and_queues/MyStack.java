public interface MyStack<T> {

    // Adds an item into the stack
    public void push(T item);

    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    public T pop();

    // Returns the most recently added item in the stack
    // throws an IllegalStateException if the stack is empty
    public T peek();

    // Returns the number of items in the stack
    public int size();

    // Returns a boolean indicating whether the stack has items
    public boolean isEmpty();
}
