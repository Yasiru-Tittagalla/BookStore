/*
This file is for the tab 2
which is for the search tab
when the user search for a book title,
it gets the book data from the google api
 */

package com.example.bookstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tab2SearchActivity extends Fragment {

    private EditText searchText;
    private Button searchButton;
    JSONObject jsonObject;
    JSONArray jsonArray;
    BookAdapter bookAdapter;


    // when the activity is created, run this method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab2_search, container, false);

        final ListView listView = rootView.findViewById(R.id.listView);
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
                                 bookAdapter = new BookAdapter(getContext(),R.layout.row_layout);
                                listView.setAdapter(bookAdapter);
//                        Log.d(LOG_TAG, "item length is " + itemsArray.length());

                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject book = itemsArray.getJSONObject(i);
                                    String title = null;
                                    String imageUrl =null;
                                    String description = null;
                                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");


                                    try {
                                        title = volumeInfo.getString("title");
                                        description = volumeInfo.getString("description");
                                        JSONObject imageObject = volumeInfo.optJSONObject("imageLinks");
                                            imageUrl = imageObject.getString("smallThumbnail");

                                            Books books = new Books(title,description,imageUrl);
                                            bookAdapter.add(books);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (title != null) {
                                        Toast.makeText(getActivity(), "Title of the book is"+title, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch(Exception e) {
                                e.printStackTrace();
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
//    class customeManager extends BaseAdapter{
    class BookAdapter extends ArrayAdapter {
        List list = new ArrayList();
        public BookAdapter(Context context, int reousrce){
            super((Context) context,reousrce);
        }

        public  void add(Books object){
            super.add(object);
            list.add(object);
        }
        @Override
        public int getCount() {

          return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row;
            row = convertView;
            BookHolder bookHolder;
            if(row == null){
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.row_layout,parent,false);
                bookHolder = new BookHolder();
                bookHolder.textView2 = (TextView) row.findViewById(R.id.textView2);
                bookHolder.textView3 = (TextView) row.findViewById(R.id.textView3);
                bookHolder.imageView = (ImageView) row.findViewById(R.id.imageView);
                row.setTag(bookHolder);
            }
            else {
                    bookHolder = (BookHolder) row.getTag();
            }
            Books books = (Books) getItem(position);
            bookHolder.textView2.setText(books.getTitle());
            bookHolder.textView3.setText(books.getDescription());
//            bookHolder.imageView.setText(books.getImageUrl());
            URL imageUrl = null;
            try {
                imageUrl = new URL(books.getImageUrl());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            bookHolder.imageView.setImageBitmap(bitmap);
//            Picasso.get().load(books.getImageUrl()).into(bookHolder.imageView);
            return row;
        }

    }
    static class BookHolder{
        TextView textView2,textView3;
        ImageView imageView;
    }
}
