public class AVLTree  <K extends Comparable<K>, V> extends BinarySearchTree<K,V> {

    public AVLTree(){
        super();
    }

    // Navigate the tree as you would with find until you either find the key (in which case you
    // update the value and return the old one), or else reach an empty spot in the tree (in
    // which case you add your new key-value pair as a new leaf node at that spot and return null).
    // If you added a node, update the heights of the ancestor nodes as necessary, checking to see
    // if any node becomes unbalanced. If a node becomes unbalanced, perform the necessary rotations
    // to correct the imbalance (make sure you update the nodes’ heights after rotation!)
    @Override
    public V insert(K key, V value) {
        V answer = null;
        if (root == null) {
            root = new TreeNode<>(key, value);
            size++;
        } else {
            answer = super.find(key);
            if(answer == null){
                size++;
            }
            // x = change(x) pattern
            // ie: root = change(root)
            root = insertHelper(key, value, root);
        }
        return answer;
    }

    public TreeNode<K,V> insertHelper(K key, V value, TreeNode<K,V> curr) {
        // BST insertion
        if (curr == null){
            return new TreeNode<>(key, value);
        }
        int currMinusNew = curr.key.compareTo(key);
        if (currMinusNew == 0) {
            curr.value = value;
        } else if (currMinusNew < 0) {
            // x = change(x) pattern
            curr.right = insertHelper(key, value, curr.right);
        } else {
            // x = change(x) pattern
            curr.left = insertHelper(key, value, curr.left);
        }
        curr.updateHeight();
        // balance subtree by performing necessary rotations
        TreeNode<K,V> balancedSubtree = balance(curr);
        return balancedSubtree;
    }

    private TreeNode<K,V> balance(TreeNode<K,V> curr) {
        // From leaf to root, check if each node is unbalanced
        // If a node is unbalanced then at the deepest unbalanced node:
        // Case LL or LR
        if (balanceFactor(curr) > 1) {
            // check balance factor of left child if == -1 case LR
            //  Case LR: If we inserted into the right subtree of the left child then: rotate left at
            //  the left child and then rotate right at the root
            if (balanceFactor(curr.left) == -1) {
                return leftRightRotation(curr);
            //  Case LL: If we inserted in the left subtree of the left child then: rotate right
            } else {
                return rightRotation(curr);
            }
        // Case RR or RL
        } else if (balanceFactor(curr) < -1) {
            // check balance factor of right child if == 1 case RL
            //  Case RL: If we inserted into the left subtree of the right child then: rotate right at
            //  the right child and then rotate left at the root
            if (balanceFactor(curr.right) == 1) {
                return rightLeftRotation(curr);
            //  Case RR: If we inserted in the right subtree of the right child then: rotate left
            } else {
                return leftRotation(curr);

            }
        }
        // Done after either reaching the root or applying one of the above cases
        return curr;
    }

    // Case 1: Single-Right Rotation
    // Insert Location: Left of Left
    public TreeNode<K,V> rightRotation(TreeNode<K,V> problemNode) {
        TreeNode<K,V> temp = problemNode.left.right;
        TreeNode<K,V> newRoot = problemNode.left;
        newRoot.right = problemNode;
        problemNode.left = temp;
        problemNode.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    // Case 4: Single-Left Rotation
    // Insert Location: Right of Right
    public TreeNode<K,V> leftRotation(TreeNode<K,V> problemNode) {
        TreeNode<K,V> temp = problemNode.right.left;
        TreeNode<K,V> newRoot = problemNode.right;
        newRoot.left = problemNode;
        problemNode.right = temp;
        problemNode.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    // Case 2: Double Right-Left Rotation
    // Insert Location: Left of Right (Path: problemNode -> R -> L)
    public TreeNode<K,V> rightLeftRotation(TreeNode<K,V> problemNode) {
        problemNode.right = rightRotation(problemNode.right);
        TreeNode<K,V> newRoot = leftRotation(problemNode);
        return newRoot;
    }

    // Case 3: Double Left-Right Rotation
    // Insert Location: Right of Left (Path: problemNode -> L -> R)
    public TreeNode<K,V> leftRightRotation(TreeNode<K,V> problemNode) {
        problemNode.left = leftRotation(problemNode.left);
        TreeNode<K,V> newRoot = rightRotation(problemNode);
        return newRoot;
    }

    // Returns the balance factor of the current node
    // which is defined as the height of the left subtree
    // minus the height of the right subtree of a particular node
    public int balanceFactor(TreeNode<K,V> curr) {
        if (curr == null || (curr.right == null && curr.left == null)) {
            return 0;
        }
        if (curr.right == null || curr.left == null) {
            if (curr.right == null && curr.left != null) {
                return curr.left.height + 1;
            } else if (curr.right != null && curr.left == null) {
                return -1 - curr.right.height;
            }
        }
        return curr.left.height - curr.right.height;
    }
}

