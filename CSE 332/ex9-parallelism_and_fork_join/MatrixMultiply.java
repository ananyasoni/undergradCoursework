import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiply {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.multiply.
    // Ignoring the initialization of arrays, your implementation should have n^3 work and log(n) span
    public static int[][] multiply(int[][] a, int[][] b, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        int[][] product = new int[a.length][b[0].length];
        POOL.invoke(new MatrixMultiplyAction(a, b, product, 0, a.length, 0, a.length));
        return product;
    }

    // Behavior should match the 2d version of Sequential.dotProduct.
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[][] a, int[][] b, int row, int col, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(a, b, row, col, 0, a.length));
    }

    private static class MatrixMultiplyAction extends RecursiveAction {
        // fields for MatrixMultiplyAction
        private final int[][] matrix1;
        private final int[][] matrix2;
        private final int[][] product;
        private final int left;
        private final int right;
        private final int top;
        private final int bottom;

        public MatrixMultiplyAction(int[][] matrix1, int[][] matrix2, int[][] product, int left, int right, int top, int bottom){
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.product = product;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        public void compute(){
            if (bottom - top <= MatrixMultiply.CUTOFF || right - left <= MatrixMultiply.CUTOFF) {
                for (int row = top; row < bottom; row++) {
                    for (int col = left; col < right; col++) {
                        product[row][col] = dotProduct(matrix1, matrix2, row, col, MatrixMultiply.CUTOFF);
                    }
                }
            } else {
                int rowMid = ((bottom + top) /  2);
                int colMid = ((left + right) / 2);
                MatrixMultiplyAction topLeft = new MatrixMultiplyAction(matrix1, matrix2, product, left, colMid, top, rowMid);
                MatrixMultiplyAction topRight = new MatrixMultiplyAction(matrix1, matrix2, product, colMid, right, top, rowMid);
                MatrixMultiplyAction bottomLeft = new MatrixMultiplyAction(matrix1, matrix2, product, left, colMid, rowMid, bottom);
                MatrixMultiplyAction bottomRight = new MatrixMultiplyAction(matrix1, matrix2, product, colMid, right, rowMid, bottom);
                topLeft.fork();
                topRight.fork();
                bottomLeft.fork();
                bottomRight.compute();
                topLeft.join();
                topRight.join();
                bottomLeft.join();

            }
        }

    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        // fields for DotProductTask
        private final int[][] matrix1;
        private final int[][] matrix2;
        private final int row, col;
        private final int lo, hi;

        public DotProductTask(int[][] matrix1, int[][] matrix2, int row, int col, int lo, int hi){
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.row = row;
            this.col = col;
            this.lo = lo; // inclusive starting index
            this.hi = hi; // exclusive ending index
        }

        public Integer compute(){
            // Step 1. Base Case (i.e. Sequential Dot Product Case)
            if (hi - lo <= MatrixMultiply.CUTOFF) {
                // dot product of specified row in matrix 1 (row) and specified column (col) in matrix 2
                // from lo to hi
                int dotProduct = 0;
                for (int i = lo; i < hi; i++) {
                    dotProduct += matrix1[row][i] * matrix2[i][col];
                }
                return dotProduct;
            } else {
                // Step 2. Recursive Case (i.e. Parallel/Forking case)
                // 1. Make sure to fork() the left task first
                // 2. Then compute() the right task
                // 3. Then wait for the leftResult by calling join() on the left
                //    task before combining results
                int mid = ((hi + lo) /  2);
                DotProductTask left = new DotProductTask(matrix1, matrix2, row, col, lo, mid);
                DotProductTask right = new DotProductTask(matrix1, matrix2, row, col, mid, hi);
                left.fork();
                int rightResult = right.compute();
                int leftResult = left.join();

                // Step 3. Combining the left and right tasks' results
                return leftResult + rightResult;
            }
        }

    }
}
