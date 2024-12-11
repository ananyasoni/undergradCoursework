import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ParallelPrefix {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    public static int[] prefixSum(int[] arr, int cutoff){
        ParallelPrefix.CUTOFF = cutoff;
        Node root = POOL.invoke(new BuildTreeTask(arr, 0, arr.length));
        int[] prefixSum = new int[arr.length];
        POOL.invoke(new PopulatePrefixSum(arr, prefixSum, root, false, null, null));
        return prefixSum;
    }

    private static class Node{
        public int lo;
        public int hi;
        public int sum;
        public int leftSum;
        public Node left;
        public Node right;

        public Node(int lo, int hi, int sum){
            this.lo = lo;
            this.hi = hi;
            this.sum = sum;
        }

    }

    private static class BuildTreeTask extends RecursiveTask<Node>{
        private int[] input;
        private int lo;
        private int hi;

        public BuildTreeTask(int[] input, int lo, int hi){
            this.input = input;
            this.lo = lo;
            this.hi = hi;
        }

        public Node compute(){
            if(hi-lo <= ParallelPrefix.CUTOFF){
                int sum = 0;
                for(int i = lo; i < hi; i++){
                    sum += input[i];
                }
                return new Node(lo, hi, sum);
            }else{
                int mid = lo + (hi-lo)/2;
                BuildTreeTask leftTask = new BuildTreeTask(input, lo, mid);
                leftTask.fork();
                BuildTreeTask rightTask = new BuildTreeTask(input, mid, hi);
                Node rightNode = rightTask.compute();
                Node leftNode = leftTask.join();
                int sum = rightNode.sum + leftNode.sum;
                Node parent = new Node(lo, hi, sum);
                parent.left = leftNode;
                parent.right = rightNode;
                return parent;
            }
        }
    }

    private static class PopulatePrefixSum extends RecursiveAction{
        private int[] input;
        private int[] output;
        private int lo;
        private int hi;
        private Node root;
        private boolean isLeftChild;
        private Node parent;
        private Node sibling;

        public PopulatePrefixSum(int[] input, int[] output, Node root, boolean isLeftChild, Node parent, Node sibling){
            this.input = input;
            this.output = output;
            this.lo = root.lo;
            this.hi = root.hi;
            this.root = root;
            this.parent = parent;
            this.sibling = sibling;
            this.isLeftChild = isLeftChild;
        }

        public void compute(){
            // fill in the left sum for each node
            if(parent == null){
                root.leftSum = 0;
            }
            else if(isLeftChild){
                root.leftSum = parent.leftSum;
            } else{
                root.leftSum = parent.leftSum + sibling.sum;
            }
            if(root.left == null && root.right == null){
                int sum = root.leftSum;
                for(int i = lo; i < hi; i++){
                    sum += input[i];
                    output[i] = sum;
                }
            }else{
                PopulatePrefixSum leftSubtree = new PopulatePrefixSum(input, output, root.left, true, root, root.right);
                leftSubtree.fork();
                PopulatePrefixSum rightSubtree = new PopulatePrefixSum(input, output, root.right, false, root, root.left);
                rightSubtree.compute();
                leftSubtree.join();
            }
        }
    }

}
