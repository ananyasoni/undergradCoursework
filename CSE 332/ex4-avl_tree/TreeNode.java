public class TreeNode<K,V> {
    K key;
    V value;
    public int height;
    public TreeNode<K,V> left;
    public TreeNode<K,V> right;

    public TreeNode(K key, V value){
        this.key = key;
        this.value = value;
        height = 0;
        this.left = null;
        this.right = null;
    }

    public TreeNode(K key, V value, TreeNode<K,V> left, TreeNode<K,V> right){
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
        updateHeight();
    }

    // Recalculates the height of this node.
    // Assumes that the height of both child nodes
    // are correct.
    public void updateHeight(){
        int leftHeight = left==null ? -1 : left.height;
        int rightHeight = right==null ? -1 : right.height;
        height = Math.max(leftHeight, rightHeight)+1;
    }

    public String toString(){
        return "[" + "key: " + key + ", value: " + value + ", height: " + height + "]";
    }
    
}
