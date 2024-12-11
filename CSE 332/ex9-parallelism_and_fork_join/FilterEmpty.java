import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FilterEmpty {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.filterEmpty
    // Ignoring the initialization of arrays, your implementation must have linear work and log(n) span
    public static String[] filterEmpty(String[] arr, int cutoff) {
        // filter/pack procedure
        FilterEmpty.CUTOFF = cutoff;
        // 1) do a map on the arr of strings
        int[] map = new int[arr.length];
        POOL.invoke(new MapAction(arr, map, 0, arr.length));
        // 2) do prefix sum on the map result (implementation provided for you in ParallelPrefix.java)
        int[] mapPrefixSum = ParallelPrefix.prefixSum(map, cutoff);
        // 3) initialize and array whose length matches the last value in the prefix sum result
        String[] filteredInput = new String[mapPrefixSum[mapPrefixSum.length - 1]];
        // 4) do a map to populate that new array.
        POOL.invoke(new PopulateAction(arr, filteredInput, map, mapPrefixSum, 0, arr.length));
        return filteredInput;
    }

    private static class MapAction extends RecursiveAction {
        // fields for FilterEmptyAction
        private final String[] input;
        private final int[] booleanArray;
        private final int lo, hi; // lo is inclusive and hi is exclusive

        public MapAction(String[] input, int[] booleanArray, int lo, int hi){
            this.input = input;
            this.booleanArray = booleanArray;
            this.lo = lo;
            this.hi = hi;
        }

        public void compute() {
            if (hi - lo <= FilterEmpty.CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    if (input[i].isEmpty()) {
                        booleanArray[i] = 0;
                    } else {
                        booleanArray[i] = 1;
                    }
                }
            } else {
                int mid = (hi + lo) / 2;
                MapAction left = new MapAction(input, booleanArray, lo, mid);
                MapAction right = new MapAction(input, booleanArray, mid, hi);
                left.fork();
                right.compute();
                left.join();
            }
        }

    }

    private static class PopulateAction extends RecursiveAction {
        // fields for PopulateAction
        private final String[] input;
        private final String[] filteredInput;
        private final int[] map;
        private final int[] mapPrefixSum;
        private final int lo, hi; // lo is inclusive and hi is exclusive

        public PopulateAction(String[] input, String[] filteredInput, int[] map, int[] mapPrefixSum, int lo, int hi){
            this.input = input;
            this.filteredInput = filteredInput;
            this.map = map;
            this.mapPrefixSum = mapPrefixSum;
            this.lo = lo;
            this.hi = hi;
        }

        public void compute() {
            if (hi - lo <= FilterEmpty.CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    if (map[i] == 1) {
                        int index = mapPrefixSum[i] - 1;
                        filteredInput[index] = input[i];
                    }
                }
            } else {
                int mid = (hi + lo) / 2;
                PopulateAction left = new PopulateAction(input, filteredInput, map, mapPrefixSum, lo, mid);
                PopulateAction right = new PopulateAction(input, filteredInput, map, mapPrefixSum, mid, hi);
                left.fork();
                right.compute();
                left.join();
            }
        }

    }

}
