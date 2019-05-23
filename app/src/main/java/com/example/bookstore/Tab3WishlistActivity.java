/*
This file is for the tab 4
which is for the reminder tab
if the user needs a reminder to read a book,
this activity tab will memorize it and remind on time
 */

package com.example.bookstore;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tab3WishlistActivity extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String LOG_TAG = Tab3WishlistActivity.class.getSimpleName();
    public static List<Book> bookList = new ArrayList<Book>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_wishlist, container, false);


        db.collection("wishlist")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // if the username is current username,
                        // get the book and the wish of the given id
                        // and show the book with wish note
                        Map<String, Object> objectMap;
                        objectMap = document.getData();
                        for(Map.Entry entry : objectMap.entrySet()){
                            if(entry.getKey().equals("userName")) {
                                if(entry.getValue().equals(LoginActivity.userEmail)){
                                    for(Map.Entry entry1 : objectMap.entrySet()){
                                        if(entry1.getKey().equals("bookId")) {
                                                Log.d(LOG_TAG, "book id is " + entry1.getValue());
                                        }
                                        if(entry1.getKey().equals("wish")) {
                                            Log.d(LOG_TAG, "wish is " + entry1.getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                }
            }
        });

//        try {
//            // get books from the api
//            new FetchBooks().execute("id:J7ywAAAAIAAJ");
//        } finally {
//            RecyclerView myrv = (RecyclerView)getActivity().findViewById(R.id.wishlistrecycler);
//            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), FetchBooks.bookList);
//            myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
//            myrv.setAdapter(myAdapter);
//        }

        runCallback(new Runnable()
        {
            @Override
            public void run()
            {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView myrv = (RecyclerView)getActivity().findViewById(R.id.wishlistrecycler);
                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), FetchBooks.wishList);
                        myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        myrv.setAdapter(myAdapter);
                    }
                }, 2000);
            }
        });



//        // wait till the api call is over
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 2000);

        return rootView;
    }

    private void runCallback(Runnable callback)
    {
        new FetchBooks().execute("id", "wishlist");
        callback.run();
    }

}