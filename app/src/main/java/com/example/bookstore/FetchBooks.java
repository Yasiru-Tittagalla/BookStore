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

import java.util.ArrayList;
import java.util.List;

public class FetchBooks extends AsyncTask<String, Void, String> {

    public static List<Book> bookList = new ArrayList<Book>();
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
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                JSONObject accessInfo = book.getJSONObject("accessInfo");
                String title = null;
                String thumb_url = null;
                String description = null;
                String webReaderLink = null;
                String bookId = null;

                try {
                    title = volumeInfo.getString("title");
                    thumb_url = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                    description = volumeInfo.getString("description");
                    bookId = book.getString("id");
                    webReaderLink = accessInfo.getString("webReaderLink");
//                    authors = volumeInfo.getJSONArray("authors");
//                    for (int j = 0; j < authors.length(); ++j) {
//                        JSONObject rec = authors.getJSONObject(i);
//                        author = rec.getString("");
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null && thumb_url != null && webReaderLink != null
                    && description != null && bookId != null) {
//                    Log.d(LOG_TAG, title + " " + thumb_url);
                    bookList.add(new Book(title, thumb_url, webReaderLink, description, bookId));
                }
            }
        } catch(Exception e) {
            Log.d(LOG_TAG, "Nothing found");
        }
    }
}
