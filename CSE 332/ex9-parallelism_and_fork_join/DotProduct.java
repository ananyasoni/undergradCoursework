import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DotProduct {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior matches Sequential.dotProduct
    // Has linear work and log(n) span
    public static int dotProduct(int[] a, int[]b, int cutoff){
        DotProduct.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(a, b, 0, a.length)); // parameters to match constructor
    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        // fields for DotProductTask
        private final int[] vector1;
        private final int[] vector2;
        private final int lo, hi;



        public DotProductTask(int[] arr1, int[] arr2, int lo, int hi) {
            this.vector1 = arr1;
            this.vector2 = arr2;
            this.lo = lo; // inclusive starting index
            this.hi = hi; // exclusive ending index
        }

        @Override
        public Integer compute(){
            // Step 1. Base Case (i.e. Sequential Dot Product Case)
            if (hi - lo <= DotProduct.CUTOFF) {
                int dotProduct = 0;
                for (int i = lo; i < hi; i++) {
                    dotProduct += vector1[i] * vector2[i];
                }
                return dotProduct;
            } else {
                // Step 2. Recursive Case (i.e. Parallel/Forking case)
                // 1. Make sure to fork() the left task first
                // 2. Then compute() the right task
                // 3. Then wait for the leftResult by calling join() on the left
                //    task before combining results
                int mid = ((hi + lo) /  2);
                DotProductTask left = new DotProductTask(vector1, vector2, lo, mid);
                DotProductTask right = new DotProductTask(vector1, vector2, mid, hi);
                left.fork();
                int rightResult = right.compute();
                int leftResult = left.join();

                // Step 3. Combining the left and right tasks' results
                return leftResult + rightResult;
            }
        }
    }
}
