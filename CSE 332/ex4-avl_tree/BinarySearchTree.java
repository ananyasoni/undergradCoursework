import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedDeletelessDictionary<K,V> {

    // Fields were made public to assist with autograding
    // Do not change the visibility of these fields :)
    public TreeNode<K, V> root;
    public int size;

    public BinarySearchTree(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public V find(K key){
        if(root == null){
            throw new IllegalStateException();
        }
        return find(key, root);
    }

    //recursive helper
    private V find(K key, TreeNode<K,V> curr){
        if(curr == null){
            return null;
        }
        int currMinusNew = curr.key.compareTo(key); // negative if curr is smaller
        if(curr.key.equals(key)){
            return curr.value;
        } else if(currMinusNew < 0){
            // curr is smaller so key must be to the right.
            return find(key, curr.right);
        } else{
            return find(key, curr.left);
        }
    }

    // Returns the smallest key which is greater than the given key
    // Returns null if the given key is greater than or equal to the largest
    // key present or if the tree is empty
    public K findNextKey(K key) {
        if (root == null) {
            return null;
        }
        return findNextKeyHelper(key, root, null); // Start with nextKey as null
    }

    private K findNextKeyHelper(K key, TreeNode<K,V> curr, K nextKey) {
        // base case: reach null
        if (curr == null) {
            return nextKey; // If no more nodes, return the next key found
        }

        int currMinusNew = curr.key.compareTo(key); // Compare current key with given key
        if (currMinusNew <= 0) {
            // Current key is less than or equal to the given key, search in the right subtree
            return findNextKeyHelper(key, curr.right, nextKey);
        } else {
            // Current key is greater than the given key, potential nextKey
            // Update nextKey and search in the left subtree for a smaller candidate
            return findNextKeyHelper(key, curr.left, curr.key);
        }
    }

    // Returns the largest key which is less than the given key
    // Returns null if the given key is less than or equal to the smallest
    // key present or if the tree is empty
    public K findPrevKey(K key){
        if (root == null) {
            return null;
        }
        return findPrevKeyHelper(key, root, null); // Start with prevKey as null
    }

    private K findPrevKeyHelper(K key, TreeNode<K,V> curr, K prevKey) {
        // base case: reach null
        if (curr == null) {
            return prevKey; // If no more nodes, return the next key found
        }

        int currMinusNew = curr.key.compareTo(key); // Compare current key with given key
        if (currMinusNew >= 0) {
            // Current key is greater than or equal to the given key, search in the left subtree
            return findPrevKeyHelper(key, curr.left, prevKey);
        } else {
            // Current key is less than the given key, potential prevKey
            // Update nextKey and search in the right subtree for a larger candidate
            return findPrevKeyHelper(key, curr.right, curr.key);
        }
    }

    public V insert(K key, V value){
        V answer = find(key, root);
        if(answer == null){
            size++;
        }
        root = insert(key, value, root);
        root.updateHeight();
        return answer;
    }

    private TreeNode<K,V> insert(K key, V value, TreeNode<K,V> curr){
        if (curr == null){
            return new TreeNode<>(key, value);
        }
        int currMinusNew = curr.key.compareTo(key);
        if(currMinusNew==0){
            curr.value = value;
        } else if(currMinusNew < 0){
            curr.right = insert(key, value, curr.right);
        } else{
            curr.left = insert(key, value, curr.left);
        }
        curr.updateHeight();
        return curr;
    }

    public List<V> getValues(){
        List<V> values = new ArrayList<>();
        inorderFillValues(values, root);
        return values;
    }

    private void inorderFillValues(List<V> values, TreeNode<K,V> curr){
        if(curr != null){
            inorderFillValues(values, curr.left);
            values.add(curr.value);
            inorderFillValues(values, curr.right);
        }
    }

    public List<K> getKeys(){
        List<K> keys = new ArrayList<K>();
        inorderFillKeys(keys, root);
        return keys;
    }

    private void inorderFillKeys(List<K> keys, TreeNode<K,V> curr){
        if(curr != null){
            inorderFillKeys(keys, curr.left);
            keys.add(curr.key);
            inorderFillKeys(keys, curr.right);
        }
    }

    public void printSideways() {
        printSideways(root, 0);
    }

    private void printSideways(TreeNode<K,V> root, int level) {
        if (root != null) {
            printSideways(root.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }
            System.out.println(root.key);
            printSideways(root.left, level + 1);
        }
    }
}
