package com.example.tp2_ex1;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpRunnable implements Runnable {
    // Define the attributes : the result and the connection
    private String result;
    private HttpURLConnection urlConnection;

    /**
     * Class constructor with the connection
     * @param urlConnection
     */
    public HttpRunnable(HttpURLConnection urlConnection) {
        this.urlConnection = urlConnection;
    }

    /**
     * Get the result of the http request
     * @return The result
     */
    public String getResult() {
        return this.result;
    }

    @Override
    public void run() {
        try {
            // Read the stream from the connection
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Convert it to a string and store it in the class instance
            result = readStream(in);

            // Log the result
            Log.i("HttpRunnable", result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Used to convert an InputStream to a String
     * @param is
     * @return The result
     * @throws IOException
     */
    private String readStream(InputStream is) throws IOException {
        // Create the string builder
        StringBuilder sb = new StringBuilder();

        // Create a buffer from the inputStream
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);

        // Read and add each line
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }

        // Close the stream and return the String result
        is.close();
        return sb.toString();
    }
}
