import java.util.*;

public interface DeletelessDictionary<K, V>{

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
    // For this assignment the running time should be constant.
    public V insert(K key, V value);

    // Returns the value associated with the given key.
    // Returns null if the key does not exist.
    // Throws an IllegalStateException if the dictionary is empty.
    // For this assignment the running time should be constant.
    public V find(K key);

    // Indicates whether or not the given key is present in
    // the dictionary. It should always return false if the
    // dictionary is empty.
    // For this assignment the running time should be constant.
    public boolean contains(K key);

    // Returns a list of all of the keys in the dictionary.
    // The list's order should be that of getValues
    // (index 0 has the key associated with value found at
    // index 0 of getValues).
    // For this assignment the running time should be linear
    // in terms of the size of the data structure.
    public List<K> getKeys();

    // Returns a list of all of the value in the dictionary.
    // The list's order should be that of getKeys
    // (index 0 has the value associated with key found at
    // index 0 of getKeys).
    // For this assignment the running time should be linear
    // in terms of the size of the data structure.
    public List<V> getValues();
}