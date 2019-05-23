/*
This is a helper class for the fetch books class
this file manages all the connection items
to get connected and fetch the details from the API
 */
package com.example.bookstore;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    public static String bookJSONString = null;
    public static JSONObject jsonObject = null;
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // base URL for the books API
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";

    // this is the method that will return the information of the book
    static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // build the query string URI
            // limiting results to 10 items
            Uri builtUrl = Uri.parse(BASE_URL)
                    .buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "21")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestedURL = new URL(builtUrl.toString());
            urlConnection = (HttpURLConnection) requestedURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the response using input stream and a string buffer
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader((new InputStreamReader(inputStream)));
            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            bookJSONString = buffer.toString();
            Log.d(LOG_TAG, "book details are" + bookJSONString);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }
    }

}
