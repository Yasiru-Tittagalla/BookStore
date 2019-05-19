/*
This file is for the tab 3
which is for the favorites tab
it loads the logged in user's favorites
from the database
The user can insert, update, delete his favorites
from this activity
 */

package com.example.bookstore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Tab3FavoritesActivity extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference wishRef = db.collection("Users").document("wish list");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_favorites, container, false);
        return rootView;
    }

}
