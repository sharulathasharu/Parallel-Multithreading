package DsaProject;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MatrixMultiplicationServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/matrix-multiplication", new MatrixMultiplicationHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server is running on http://localhost:8080/matrix-multiplication");
    }

    static class MatrixMultiplicationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Perform matrix multiplication
            int[][] matrixA = {
                    {1, 2, 3},
                    {4, 5, 6}
            };

            int[][] matrixB = {
                    {7, 8},
                    {9, 10},
                    {11, 12}
            };

            int numThreads = 2;
            int[][] resultMatrixMultiThreaded = MatrixMultiplicationMultiThreaded.multiply(matrixA, matrixB, numThreads);

            // Build HTML response
            String htmlResponse = "<html><body>" +
                    "<h2>Matrix Multiplication Results</h2>" +
                    "<p>Matrix A:</p>" +
                    MatrixMultiplication.getMatrixHTML(matrixA) +
                    "<p>Matrix B:</p>" +
                    MatrixMultiplication.getMatrixHTML(matrixB) +
                    "<p>Multi-Threaded Result Matrix:</p>" +
                    MatrixMultiplication.getMatrixHTML(resultMatrixMultiThreaded) +
                    "</body></html>";

            // Set the response headers
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, htmlResponse.length());

            // Write the HTML response to the output stream
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(htmlResponse.getBytes());
            }
        }
    }
}
