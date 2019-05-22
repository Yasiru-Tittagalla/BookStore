/*
This file is for the tab 1
which is for the home tab
it loads all the books to the front page
 */

package com.example.bookstore;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

//        // get books from the api
//        new FetchBooks().execute("subject:Thriller");
//
//        // wait till the api call is over
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                for (Book book : FetchBooks.bookList) {
////                    Log.d(LOG_TAG, "web reader link" + book.getWebReaderLink());
////                }
//                RecyclerView myrv = (RecyclerView)getActivity().findViewById(R.id.recyclerview_id);
//                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), FetchBooks.bookList);
//                myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
//                myrv.setAdapter(myAdapter);
//            }
//        }, 2000);


        runCallback(new Runnable()
        {
            @Override
            public void run()
            {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView myrv = (RecyclerView)getActivity().findViewById(R.id.recyclerview_id);
                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), FetchBooks.bookList);
                        myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        myrv.setAdapter(myAdapter);
                    }
                }, 2000);
            }
        });



        return rootView;
    }

    private void runCallback(Runnable callback)
    {
        new FetchBooks().execute("subject:Thriller", "home");
        callback.run();
    }


}
