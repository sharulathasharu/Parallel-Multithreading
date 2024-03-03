package DsaProject;

import java.util.Arrays;
import java.util.Random;

	public class MergeSorting {

	    // Single-threaded merge sort
	    public static void mergeSort(int[] arr) {
	        if (arr.length > 1) {
	            int mid = arr.length / 2;
	            int[] left = Arrays.copyOfRange(arr, 0, mid);
	            int[] right = Arrays.copyOfRange(arr, mid, arr.length);

	            mergeSort(left);
	            mergeSort(right);

	            merge(arr, left, right);
	        }
	    }

	    private static void merge(int[] arr, int[] left, int[] right) {
	        int i = 0, j = 0, k = 0;
	        while (i < left.length && j < right.length) {
	            if (left[i] <= right[j]) {
	                arr[k++] = left[i++];
	            } else {
	                arr[k++] = right[j++];
	            }
	        }

	        while (i < left.length) {
	            arr[k++] = left[i++];
	        }

	        while (j < right.length) {
	            arr[k++] = right[j++];
	        }
	    }

	    // Multi-threaded merge sort
	    public static void parallelMergeSort(int[] arr, int numThreads) {
	        if (numThreads <= 1) {
	            mergeSort(arr);
	        } else {
	            int mid = arr.length / 2;

	            int[] left = Arrays.copyOfRange(arr, 0, mid);
	            int[] right = Arrays.copyOfRange(arr, mid, arr.length);

	            Thread leftThread = new Thread(() -> parallelMergeSort(left, numThreads / 2));
	            Thread rightThread = new Thread(() -> parallelMergeSort(right, numThreads / 2));

	            leftThread.start();
	            rightThread.start();

	            try {
	                leftThread.join();
	                rightThread.join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }

	            merge(arr, left, right);
	        }
	    }

	    public static void main(String[] args) {
	    	int[] arr = new int[10];
	        Random random = new Random();

	        // Filling the array with random integers
	        for (int i = 0; i < arr.length; i++) {
	            arr[i] = random.nextInt(1000); // Change 1000 to the desired range of random integers
	        }
	        int numThreads = 2;

	        // Single-threaded sorting
	        long startTime = System.nanoTime();
	        mergeSort(arr);
	        long endTime = System.nanoTime();
	        long singleThreadedTime = endTime - startTime;

	        System.out.println("Single-threaded sorted array: " + Arrays.toString(arr));

	        // Multi-threaded sorting
	        int[] arrMultiThreaded = arr; // Reset the array
	        startTime = System.nanoTime();
	        parallelMergeSort(arrMultiThreaded, numThreads);
	        endTime = System.nanoTime();
	        long multiThreadedTime = endTime - startTime;

	        System.out.println("Merge sorted array: " + Arrays.toString(arrMultiThreaded));

	        // Print run time difference
	        System.out.println("Single-threaded Time: " + singleThreadedTime + " ns");
	        System.out.println("Multi-threaded Time: " + multiThreadedTime + " ns");
	        System.out.println("Run time difference: " + (singleThreadedTime - multiThreadedTime) + " ns");
	    }
	}


