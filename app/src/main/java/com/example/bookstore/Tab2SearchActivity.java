/*
This file is for the tab 2
which is for the search tab
when the user search for a book title,
it gets the book data from the google api
 */

package com.example.bookstore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tab2SearchActivity extends Fragment {

    private EditText searchText;
    private Button searchButton;

    // when the activity is created, run this method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_search, container, false);

        searchText = (EditText)rootView.findViewById(R.id.editText2);
        searchButton = (Button)rootView.findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // hide the keyboard after searching
                if (getActivity().getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            inputMethodManager.HIDE_NOT_ALWAYS);
                }

                // check network state
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo == null || !networkInfo.isConnected()) {
                    Toast.makeText(getActivity(), "Please check the network connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                // to search the book
                String text = searchText.getText().toString();
                if(!text.isEmpty()){
                    new FetchBooks().execute(text);
                } else {
                    Toast.makeText(getActivity(), "Please enter a book name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
