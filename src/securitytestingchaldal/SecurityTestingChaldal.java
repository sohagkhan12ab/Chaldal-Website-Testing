package securitytestingchaldal;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SecurityTestingChaldal {

    public static void main(String[] args) {
        HttpURLConnection connection = null; // Declare connection variable

        try {
            // Specify the URL
            URL url = new URL("https://chaldal.com/"); // Chaldal homepage

            // Open connection
            connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Set a timeout for connection and reading
            connection.setConnectTimeout(5000); // 5 seconds
            connection.setReadTimeout(5000);   // 5 seconds

            // Connect to the URL
            connection.connect();

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Failed to connect to the website. Response code: " + responseCode);
            }

            // Read and print the response content
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Ensure the connection is disconnected
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
