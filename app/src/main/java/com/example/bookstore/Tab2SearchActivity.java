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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tab2SearchActivity extends Fragment {

    private EditText searchText;
    private Button searchButton;
    JSONObject jsonObject;
    JSONArray jsonArray;

    // when the activity is created, run this method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab2_search, container, false);

        ListView listView = rootView.findViewById(R.id.listView);
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
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            String Books = NetworkUtils.bookJSONString;

                            try {
                                JSONObject jsonObject = new JSONObject(Books);
                                JSONArray itemsArray = jsonObject.getJSONArray("items");

//                        Log.d(LOG_TAG, "item length is " + itemsArray.length());

                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject book = itemsArray.getJSONObject(i);
                                    String title = null;
                                    String imageUrl =null;
                                    String description = null;
                                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                                    try {
                                        title = volumeInfo.getString("title");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (title != null) {
                                        Toast.makeText(getActivity(), "Title of the book is"+title, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch(Exception e) {
                                Toast.makeText(getActivity(), "Nothing Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, 5000);


                    showInList();
                } else {
                    Toast.makeText(getActivity(), "Please enter a book name", Toast.LENGTH_SHORT).show();
                }

            }

            private void showInList() {
            }
        });

        return rootView;
    }

    // custom class for the adapter to display the list of values
    class customeManager extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
