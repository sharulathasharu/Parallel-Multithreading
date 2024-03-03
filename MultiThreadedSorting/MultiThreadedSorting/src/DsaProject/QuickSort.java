package DsaProject;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSort {

    // Single-threaded quicksort
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Multi-threaded quicksort using Fork-Join framework
    public static class ParallelQuickSort extends RecursiveAction {
        private static final int THRESHOLD = 1000;
        private int[] arr;
        private int low, high;

        public ParallelQuickSort(int[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
                if (high - low < THRESHOLD) {
                    quickSort(arr, low, high);
                } else {
                    int pivotIndex = partition(arr, low, high);

                    ParallelQuickSort leftTask = new ParallelQuickSort(arr, low, pivotIndex - 1);
                    ParallelQuickSort rightTask = new ParallelQuickSort(arr, pivotIndex + 1, high);

                    invokeAll(leftTask, rightTask);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5, 6};
        int[] arrCopy = Arrays.copyOf(arr, arr.length);

        // Single-threaded quicksort
        long startTime = System.nanoTime();
        quickSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();
        long singleThreadedTime = endTime - startTime;

        System.out.println("Single-threaded sorted array: " + Arrays.toString(arr));

        // Multi-threaded quicksort
        arr = Arrays.copyOf(arrCopy, arrCopy.length); // Reset the array
        startTime = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ParallelQuickSort(arr, 0, arr.length - 1));
        endTime = System.nanoTime();
        long multiThreadedTime = endTime - startTime;

        System.out.println("Multi-threaded sorted array: " + Arrays.toString(arr));

        // Print run time difference
        System.out.println(" QuickSorted-Single-threaded Time: " + singleThreadedTime + " ns");
        System.out.println(" QuickSorted-Multi-threaded Time: " + multiThreadedTime + " ns");
        System.out.println("Run time difference: " + (singleThreadedTime - multiThreadedTime) + " ns");
    }
}
