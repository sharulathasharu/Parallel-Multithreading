public class MatrixMultiplicationCombined {

    public static void main(String[] args) {
        int[][] matrixA = {
                {1, 2, 3},
                {4, 5, 6}
        };

        int[][] matrixB = {
                {7, 8},
                {9, 10},
                {11, 12}
        };

        System.out.println("Matrix A:");
        MatrixMultiplication.printMatrix(matrixA);

        System.out.println("Matrix B:");
        MatrixMultiplication.printMatrix(matrixB);

        // Single-threaded matrix multiplication
        long startTime = System.currentTimeMillis();
        int[][] resultMatrixSingleThreaded = MatrixMultiplication.multiply(matrixA, matrixB);
        long endTime = System.currentTimeMillis();
        long singleThreadedDuration = endTime - startTime;

        System.out.println("Single-Threaded Result Matrix:");
        MatrixMultiplication.printMatrix(resultMatrixSingleThreaded);
        System.out.println("Single-Threaded Duration: " + singleThreadedDuration + " milliseconds");

        // Multi-threaded matrix multiplication
        int numThreads = 2; // Adjust the number of threads based on your system

        startTime = System.currentTimeMillis();
        int[][] resultMatrixMultiThreaded = MatrixMultiplicationMultiThreaded.multiply(matrixA, matrixB, numThreads);
        endTime = System.currentTimeMillis();
        long multiThreadedDuration = endTime - startTime;

        System.out.println("Multi-Threaded Result Matrix:");
        MatrixMultiplication.printMatrix(resultMatrixMultiThreaded);
        System.out.println("Multi-Threaded Duration: " + multiThreadedDuration + " milliseconds");
    }
}
