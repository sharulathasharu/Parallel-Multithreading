package DsaProject;
import java.util.Arrays;

public class MatrixMultiplication {

    // Single-threaded matrix multiplication
    public static int[][] matrixMultiply(int[][] A, int[][] B) {
        int numRowsA = A.length;
        int numColsA = A[0].length;
        int numColsB = B[0].length;

        int[][] result = new int[numRowsA][numColsB];

        for (int i = 0; i < numRowsA; i++) {
            for (int j = 0; j < numColsB; j++) {
                for (int k = 0; k < numColsA; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }

    // Multi-threaded matrix multiplication
    public static int[][] parallelMatrixMultiply(int[][] A, int[][] B, int numThreads) throws InterruptedException {
        int numRowsA = A.length;
        int numColsA = A[0].length;
        int numColsB = B[0].length;

        int[][] result = new int[numRowsA][numColsB];

        Thread[] threads = new Thread[numThreads];
        int rowsPerThread = numRowsA / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int startRow = i * rowsPerThread;
            final int endRow = (i == numThreads - 1) ? numRowsA : (i + 1) * rowsPerThread;

            threads[i] = new Thread(() -> {
                for (int row = startRow; row < endRow; row++) {
                    for (int col = 0; col < numColsB; col++) {
                        for (int k = 0; k < numColsA; k++) {
                            result[row][col] += A[row][k] * B[k][col];
                        }
                    }
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] matrixA = {
                {2, 3},
                {4, 5}
        };

        int[][] matrixB = {
                {5, 6},
                {7, 8}
        };

        int numThreads = 2;

        // Single-threaded matrix multiplication
        long startTime = System.nanoTime();
        int[][] resultSingleThreaded = matrixMultiply(matrixA, matrixB);
        long endTime = System.nanoTime();
        long singleThreadedTime = endTime - startTime;

        System.out.println("Single-threaded result:");
        printMatrix(resultSingleThreaded);

        // Multi-threaded matrix multiplication
        startTime = System.nanoTime();
        int[][] resultMultiThreaded = new int[matrixA.length][matrixB[0].length];
        try {
            resultMultiThreaded = parallelMatrixMultiply(matrixA, matrixB, numThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.nanoTime();
        long multiThreadedTime = endTime - startTime;

        System.out.println("Multi-threaded result:");
        printMatrix(resultMultiThreaded);

        // Print run time difference
        System.out.println("Single-threaded Time: " + singleThreadedTime + " ns");
        System.out.println("Multi-threaded Time: " + multiThreadedTime + " ns");
        System.out.println("Run time difference: " + (singleThreadedTime - multiThreadedTime) + " ns");
    }

    // Helper method to print a matrix
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
