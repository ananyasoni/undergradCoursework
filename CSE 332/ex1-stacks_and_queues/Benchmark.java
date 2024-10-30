import java.util.*;
import java.io.*;

public class Benchmark {

    public static final int CYCLES = 1000; // the number of times we repeatedly add/remove an item

    public static void main(String[] args) {
        csvData();
    }

    // generates a list of n random doubles
    public static double[] randomDataSet(int n) {
        Random rand = new Random();
        double[] dataSet = new double[n];
        for (int i = 0; i < n; i++) {
            dataSet[i] = rand.nextDouble();
        }
        return dataSet;
    }

    // parameter n is the number of random items we will enqueue.
    // Afterwards we will repeatedly enqueue and dequeue
    // CYCLES times, meanwhile measuring how long that will take.
    // The method returns an array of length 2, with index 0 containing
    // the value of n and index 1 containing the total time it took
    // to do all CYCLES enqueue/dequeue operations.
    public static long[] testEnqueueDequeue(int n, MyQueue<Double> q) {
        long[] times = new long[2];
        times[0] = n;
        double[] dataSet = randomDataSet(n);
        for (int i = 0; i < n; i++) {
            q.enqueue(dataSet[i]);
        }
        double itemToAdd = 100.0;
        long start = System.nanoTime();
        for(int i = 0; i < CYCLES; i++) {
            q.enqueue(itemToAdd);
            q.dequeue();
        }
        long finish = System.nanoTime();
        times[1] = finish - start;

        return times;
    }

    // generate n random numbers and time how long it takes
    // to run CYCLES enqueue/dequeue operations on a queue
    // already containing those numbers. Repeats this process
    // "trials" many times. Because java can sometimes take
    // some time go "warm up", we ignore the first "discard"
    // many of these trials.
    // reports the average time (in nanoseconds) taken
    // across all trials that were not ignored.
    public static double[] doMultipleTests(int n, int trials, int discard, MyQueue<Double> q) {
        long[] totalTimes = new long[2];
        for (int i = 0; i < trials; i++) {
            long[] times = testEnqueueDequeue(n, q);

            // Add to total if not discarding
            if (i >= discard) {
                for (int j = 0; j < times.length; j++) {
                    totalTimes[j] += times[j];
                }
            }
        }
        // Compute average
        double[] averageTimes = new double[2];
        for (int i = 0; i < totalTimes.length; i++) {
            averageTimes[i] = (double) totalTimes[i] / (trials - discard);
        }
        return averageTimes;
    }

    // parameter n is the number of random items we will push
    // afterwards we will repeatedly push and pop
    // CYCLES times, meanwhile measuring how long that will take.
    // The method returns an array of length 2, with index 0 containing
    // the value of n and index 1 containing the total time it took
    // to do all CYCLES push/pop operations.
    public static long[] testPushPop(int n, MyStack<Double> stk) {
        long[] times = new long[2];
        times[0] = n;
        double[] dataSet = randomDataSet(n);
        for (int i = 0; i < n; i++) {
            stk.push(dataSet[i]);
        }
        double itemToAdd = 100.0;
        long start = System.nanoTime();
        for(int i = 0; i < CYCLES; i++) {
            stk.push(itemToAdd);
            stk.pop();
        }
        long finish = System.nanoTime();
        times[1] = finish - start;

        return times;
    }

    // generate n random numbers and time how long it takes
    // to run CYCLES push/pop operations on a stack
    // already containing those numbers. Repeats this process
    // "trials" many times. Because java can sometimes take
    // some time go "warm up", we ignore the first "discard"
    // many of these trials.
    // reports the average time (in nanoseconds) taken
    // across all trials that were not ignored.
    public static double[] doMultipleTests(int n, int trials, int discard, MyStack<Double> stk) {
        long[] totalTimes = new long[2];
        for (int i = 0; i < trials; i++) {
            long[] times = testPushPop(n, stk);

            // Add to total if not discarding
            if (i >= discard) {
                for (int j = 0; j < times.length; j++) {
                    totalTimes[j] += times[j];
                }
            }
        }
        // Compute average
        double[] averageTimes = new double[2];
        for (int i = 0; i < totalTimes.length; i++) {
            averageTimes[i] = (double) totalTimes[i] / (trials - discard);
        }
        return averageTimes;
    }

    public static void csvData() {
        try {
            File resultsFile = new File("timeTest.csv");
            PrintWriter results = new PrintWriter(resultsFile);
            results.println("n,average time");

            for (int i = 10; i <= 10000; i+=100) {
                double[] times = doMultipleTests(i, 15, 5,
                                                        new LinkedStack<Double>() // change this line to test each data structure
                                                        );
                results.print(times[0]);
                for (int j = 1; j < times.length; j++) {
                    results.print(", " + times[j]);
                }
                results.println();
            }
            results.close();
        } catch (IOException e) {
            System.out.println("file error");
        }
    }
}
