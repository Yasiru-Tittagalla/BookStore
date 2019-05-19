/*
This file helps the activities to
fetch books from the book api
 */

package com.example.bookstore;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBooks extends AsyncTask<String, Void, String> {

    private static final String LOG_TAG = FetchBooks.class.getSimpleName();

    // call to the api in the background
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    // after getting info from the api
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            Log.d(LOG_TAG, "item length is " + itemsArray.length());

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null) {
                    Log.d(LOG_TAG, title);
                }
            }
        } catch(Exception e) {
            Log.d(LOG_TAG, "Nothing found");
        }
    }
}
