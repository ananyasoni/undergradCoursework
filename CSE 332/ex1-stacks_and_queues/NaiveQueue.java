import java.util.*;

public class NaiveQueue<T> implements MyQueue<T>{
    private List<T> queue;

    public NaiveQueue(){
        this.queue = new ArrayList<>();
    }

    public int size(){
        return this.queue.size();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public T dequeue(){
        return queue.remove(0);
    }

    public void enqueue(T item){
        queue.add(item);
    }

    public T peek(){
        return queue.get(0);
    }
}
