import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable <K,V> implements DeletelessDictionary<K,V>{
    private List<Item<K,V>>[] table; // the table is an array of linked lists of items.
    private int size;
    // primes: 1, 3, 5, 7, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853
    private static int[] primes = {11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853};
    // helps compute the next prime number when resizing hash table (new size = 2^nextPower + 1)
    private static int nextPower;
    private static int primesIndex;

    public ChainingHashTable(){
        table = (LinkedList<Item<K,V>>[]) Array.newInstance(LinkedList.class, primes[0]);
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<>();
        }
        size = 0;
        nextPower = 14;
        primesIndex = 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    // Associates the given value with the given key.
    // If the key was already in the dictionary then
    // we update the dictionary so that the given value
    // is associated with that key. The previous value
    // is then returned.
    // If the key was not already present we add the new
    // key-value pair and return null.
    // For this assignment the running time should be constant.
    public V insert(K key, V value) {
        // check if need to resize
        // resize condition -> load factor >= 2
        // calculate load factor = n / length of table
        int loadFactor = this.size / this.table.length;
        if (loadFactor >= 2) {
            resize(); // resizes hash table to next prime number
        }
        int index = hashFunction(key);
        // iterate though linked list in hash table at index
        // returned by hash function - check if key already
        // exists and if so replace value mapped to key
        // with new value and return old value
        List<Item<K,V>> curr = this.table[index];
        for (int i = 0; i < curr.size(); i++) {
            Item<K,V> nextItem = curr.get(i);
            if (nextItem.key.equals(key)) {
                V oldValue = nextItem.value;
                nextItem.value = value;
                return oldValue;
            }
        }
        // if key doesn't already
        // exist add new key and value
        // and update the size
        curr.add(new Item<K,V>(key, value));
        this.size++;
        return null;
    }

    // Returns the value associated with the given key.
    // Returns null if the key does not exist.
    // Throws an IllegalStateException if the dictionary is empty.
    // For this assignment the running time should be constant.
    public V find(K key){
        if (isEmpty()) {
            throw new IllegalStateException("Empty Hash Table");
        }
        // use hash function to get index
        int index = hashFunction(key);
        // iterate though linked list in hash table at index
        // returned by hash function
        List<Item<K,V>> curr = this.table[index];
        for (int i = 0; i < curr.size(); i++) {
            // if key of curr item
            // matches return value mapped
            // to key in curr
            Item<K,V> nextItem = curr.get(i);
            if (nextItem.key.equals(key)) {
                return nextItem.value;
            }
        }
        return null;
    }

    // Resizes hash table and rehashes
    // elements in old hash table once load
    // factor reaches 2 (For separate chaining
    // we want load factor < 2)
    private void resize() {
        List<Item<K,V>>[] oldTable = this.table; // store old table
        // create new array of size next prime
        if (this.primesIndex < primes.length) {
            this.table = (LinkedList<Item<K,V>>[]) Array.newInstance(LinkedList.class, primes[primesIndex]);
            primesIndex++;
        } else {
            // if run out of precomputed primes, compute next prime by 2^nextPower + 1
            this.table = (LinkedList<Item<K,V>>[]) Array.newInstance(LinkedList.class, (int)Math.pow(2, this.nextPower) + 1);
            nextPower++;
        }
        // initialize table with empty linked lists
        // note: important to avoid null pointer exception
        // as default value when initializing array is null
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<>();
        }
        // insert all items in old hash table to new hash table
        for (int i = 0; i < oldTable.length; i++) {
            List<Item<K,V>> curr = oldTable[i];
            // if curr is null then bucket at index i of hash table doesn't
            // exist
            for (int j = 0; j < curr.size(); j++) {
                Item<K,V> nextItem = curr.get(j);
                int newIndex = hashFunction(nextItem.key);
                List<Item<K,V>> newLinkedList = this.table[newIndex];
                newLinkedList.add(nextItem);
            }
        }
    }

    // Indicates whether or not the given key is present in
    // the hash table. It should always return false if the
    // hash table is empty.
    // For this assignment the running time should be constant.
    public boolean contains(K key){
        if (isEmpty()) {
            return false;
        }
        // use hash function to get index
        int index = hashFunction(key);
        // iterate though linked list in hash table at index
        // returned by hash function
        List<Item<K,V>> curr = this.table[index];
        for (int i = 0; i < curr.size(); i++) {
            if (curr.get(i).key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    // Returns the index the given key is
    // mapped to in this Hash Table
    private int hashFunction(K key) {
        return Math.abs(key.hashCode() % this.table.length);
    }

    // Returns a list of all the keys in the dictionary.
    // The list's order should be that of getValues
    // (index 0 has the key associated with value found at
    // index 0 of getValues).
    // For this assignment the running time should be linear
    // in terms of the size of the data structure.
    public List<K> getKeys(){
        List<K> keys = new ArrayList<>();
        // iterate through table
        for (int i = 0; i < this.table.length; i++) {
            List<Item<K,V>> linkedList = this.table[i];
            // iterate through linked list
            for (int j = 0; j < linkedList.size(); j++) {
                Item<K,V> nextItem = linkedList.get(j);
                keys.add(nextItem.key);
            }
        }
        return keys;
    }

    // Returns a list of all the values in the dictionary.
    // The list's order should be that of getKeys
    // (index 0 has the value associated with key found at
    // index 0 of getKeys).
    // For this assignment the running time should be linear
    // in terms of the size of the data structure.
    public List<V> getValues(){
        List<V> values = new ArrayList<>();
        // iterate through table
        for (int i = 0; i < this.table.length; i++) {
            List<Item<K,V>> linkedList = this.table[i];
            // iterate through linked list
            for (int j = 0; j < linkedList.size(); j++) {
                Item<K,V> nextItem = linkedList.get(j);
                values.add(nextItem.value);
            }
        }
        return values;
    }

    public String toString(){
        String s = "{";
        s += table[0];
        for(int i = 1; i < table.length; i++){
            s += "," + table[i];
        }
        return s+"}";
    }

}
