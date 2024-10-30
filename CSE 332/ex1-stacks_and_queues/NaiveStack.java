import java.util.*;

public class NaiveStack<T> implements MyStack<T> {
    private List<T> stack;

    public NaiveStack(){
        stack = new ArrayList<T>();
    }

    public int size(){
        return stack.size();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public void push(T value){
        stack.add(0, value);
    }

    public T peek(){
        return stack.get(0);
    }

    public T pop(){
        return stack.remove(0);
    }

}
