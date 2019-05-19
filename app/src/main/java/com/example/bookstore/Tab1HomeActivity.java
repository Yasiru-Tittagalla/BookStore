/*
This file is for the tab 1
which is for the home tab
it loads all the books to the front page
 */

package com.example.bookstore;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab1HomeActivity extends Fragment {

    private static final String LOG_TAG = Tab1HomeActivity.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_home, container, false);

        // get books from the api
        new FetchBooks().execute("spider man");

        // wait till the api call is over
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(LOG_TAG, NetworkUtils.bookJSONString);
//            }
//        }, 5000);

        return rootView;
    }

}
