package DsaProject;
import java.util.Arrays;
import java.util.Random;

public class MultiThreadedSorting {

    // Single-threaded bubble sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap temp and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Multi-threaded bubble sort
    public static void parallelBubbleSort(int[] arr, int numThreads) throws InterruptedException {
        int n = arr.length;
        int chunkSize = n / numThreads;

        // Create an array to hold threads
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int startIndex = i * chunkSize;
            final int endIndex = (i == numThreads - 1) ? n : (i + 1) * chunkSize;

            // Create and start a new thread for each chunk
            threads[i] = new Thread(() -> {
                bubbleSort(Arrays.copyOfRange(arr, startIndex, endIndex));
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Merge the sorted chunks
        for (int i = 0; i < numThreads - 1; i++) {
            int mid = (i + 1) * chunkSize;
            int endIndex = (i + 2 == numThreads) ? n : (i + 2) * chunkSize;
            merge(arr, i * chunkSize, mid, endIndex);
        }
    }

    // Merge function to combine two sorted halves into a single sorted array
    private static void merge(int[] arr, int left, int mid, int right) {
        // Merge the two halves
        int[] leftArr = Arrays.copyOfRange(arr, left, mid);
        int[] rightArr = Arrays.copyOfRange(arr, mid, right);
        int i = 0, j = 0, k = left;

        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        // Copy remaining elements from both arrays
        while (i < leftArr.length) {
            arr[k++] = leftArr[i++];
        }

        while (j < rightArr.length) {
            arr[k++] = rightArr[j++];
        }
    }

    public static void main(String[] args) {
    	int[] arr = new int[10000];
        Random random = new Random();

        // Filling the array with random integers
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000); // Change 1000 to the desired range of random integers
        }
        int numThreads = 2;

        // Single-threaded sorting
        long startTime = System.nanoTime();
        bubbleSort(arr);
        long endTime = System.nanoTime();
        long singleThreadedTime = endTime - startTime;

        System.out.println("Single-threaded sorted array: " + Arrays.toString(arr));

        // Multi-threaded sorting
        int[] arrMultiThreaded = arr; // Reset the array
        startTime = System.nanoTime();
        try {
            parallelBubbleSort(arrMultiThreaded, numThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.nanoTime();
        long multiThreadedTime = endTime - startTime;

        System.out.println("Multi-threaded sorted array: " + Arrays.toString(arrMultiThreaded));

        // Print run time difference
        System.out.println("Single-threaded Time: " + singleThreadedTime + " ns");
        System.out.println("Multi-threaded Time: " + multiThreadedTime + " ns");
        System.out.println("Run time difference: " + (singleThreadedTime - multiThreadedTime) + " ns");
    }
}
