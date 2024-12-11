import java.util.*;

public interface OrderedDeletelessDictionary<K extends Comparable<K>, V>{

    // Indicates whether any key-value pairs are present in the dictionary.
    public boolean isEmpty();

    // Returns the number of key-value pairs present in the dictionary.
    public int size();

    // Associates the given value with the given key.
    // If the key was already in the dictionary then
    // we update the dictionary so that the given value
    // is associated with that key. The previous value 
    // is then returned.
    // If the key was not already present we add the new
    // key-value pair and return null.
    // For this assignment the running time should be linear
    // in terms of the height of the tree data structure used.
    public V insert(K key, V value);

    // Returns the value associated with the given key.
    // Returns null if the key does not exist.
    // Throws an IllegalStateException if the dictionary is empty.
    // For this assignment the running time should be linear
    // in terms of the height of the tree data structure used.
    public V find(K key);

    // Returns the largest key in the dictionary that is smaller
    // than the given key. In other words, if we had a list of
    // keys currently in the dictionary, and then inserted the
    // given key at index i, we would want to return the key
    // at index i-1
    // For this assignment the running time should be linear
    // in terms of the height of the tree data structure used.
    public K findPrevKey(K key);

    // Returns the smallest key in the dictionary that is larger
    // than the given key. In other words, if we had a list of
    // keys currently in the dictionary, and then inserted the
    // given key at index i, we would want to return the key
    // at index i+1
    // For this assignment the running time should be linear
    // in terms of the height of the tree data structure used.
    public K findNextKey(K key);

    // Returns a list of all of the keys in the dictionary.
    // The list should be sorted in ascending order (index 0
    // has the smallest key, index size-1 has the largest).
    // For this assignment the running time should be linear
    // in terms of the size of the data structure.
    public List<K> getKeys();

    // Returns a list of all of the value in the dictionary.
    // The list should be sorted in ascending order by key
    // (index 0 has the value associated with the smallest key, 
    // index size-1 has the value associated with the largest).
    // For this assignment the running time should be linear
    // in terms of the size of the data structure.
    public List<V> getValues();
}