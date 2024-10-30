public class ArrayStack<T> implements MyStack<T> {
    private T[] stack;
    private int back;
    private int size;

    public ArrayStack() {
        this.stack = (T[]) new Object[10];
        this.back = 0;
        this.size = 0;
    }

    // Adds an item into the stack
    public void push(T item) {
        // resize stack when stack contains length - 1 elements
        // because back should only equal 0 when stack is
        // empty
        if (this.size == this.stack.length - 1) {
            // call function to resize queue
            resize(item);
        } else {
            this.stack[this.back] = item;
            this.back = (this.back + 1) % this.stack.length;
        }
        this.size++;
    }

    private void resize(T item) {
        T[] newStack = (T[]) new Object[this.stack.length * 2];
        // add all queue.length - 1 items to the new array
        // double the size of the previous array
        int idx = 0;
        int i = 0;
        while(i < this.stack.length - 1) {
            newStack[i] = this.stack[idx];
            idx = (idx + 1) % this.stack.length;
            i++;
        }
        newStack[i] = item;
        this.back = i + 1;
        this.stack = newStack;
    }

    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        this.back = (this.back - 1) % this.stack.length;
        this.size--;
        return this.stack[this.back];
    }

    // Returns the most recently added item in the stack
    // throws an IllegalStateException if the stack is empty
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return this.stack[(this.back - 1) % this.stack.length];
    }

    // Returns the number of items in the stack
    public int size() {
        return this.size;
    }

    // Returns a boolean indicating whether the stack has items
    public boolean isEmpty() {
        // note: back == 0 if and only if (<-->) the stack is empty
        return this.size == 0;
    }
}
