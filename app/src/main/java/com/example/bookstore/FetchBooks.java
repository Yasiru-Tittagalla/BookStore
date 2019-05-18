package com.example.bookstore;

import android.os.AsyncTask;
import android.widget.TextView;

public class FetchBooks extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
