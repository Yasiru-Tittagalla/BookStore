/*
This file is for the tab 2
which is for the search tab
when the user search for a book title,
it gets the book data from the google api
 */

package com.example.bookstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/** In this activity we are searching for the books and displaying them
  in list so that the user can wishlist them for feature reading  **/
public class Tab2SearchActivity extends Fragment {

    private EditText searchText;
    private Button searchButton,wishButton;
    final Tab2SearchActivity context = this;
    JSONObject jsonObject;
    JSONArray jsonArray;
    BookAdapter bookAdapter;
    ProgressBar progressBar;
    public String id;

    // when the activity is created, run this method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab2_search, container, false);

        final ListView listView = rootView.findViewById(R.id.listView);
        searchText = (EditText)rootView.findViewById(R.id.editText2);

//         progressBar = rootView.findViewById(R.id.progressbar);
searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        inputMethodManager.HIDE_NOT_ALWAYS);
            }

            // check network state
            //loading bar while data are being fetched
//                progressBar.setVisibility(View.VISIBLE);
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo == null || !networkInfo.isConnected()) {
//                    progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Please check the network connection", Toast.LENGTH_SHORT).show();
                return false;
            }

            // to search the book
            String text = searchText.getText().toString();
            if(!text.isEmpty()){
                new FetchBooks().execute(text, "search");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //fetch the books information into a string
                        String Books = NetworkUtils.bookJSONString;

                        try {
                            //convert to jsonobject
                            JSONObject jsonObject = new JSONObject(Books);
                            //create a json array
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
                                    //set the values into view
                                    title = volumeInfo.getString("title");
                                    description = volumeInfo.getString("description");
                                    id = book.getString("id");
                                    JSONObject imageObject = volumeInfo.optJSONObject("imageLinks");
                                    imageUrl = imageObject.getString("thumbnail");

                                    //create the object with setted values
                                    Books books = new Books(title,description,imageUrl);
                                    bookAdapter.add(books);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch(Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Nothing Found", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 5000);


            } else {
                Toast.makeText(getActivity(), "Please enter a book name", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
        }

//   searchButton.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View v) {

                // hide the keyboard after searching


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
                bookHolder.wishListButton = (Button) row.findViewById(R.id.wishButton);
                row.setTag(bookHolder);
            }
            else {
                    bookHolder = (BookHolder) row.getTag();
            }
            Books books = (Books) getItem(position);
            bookHolder.textView2.setText(books.getTitle());
            bookHolder.textView3.setText(books.getDescription());
            Picasso.get().load(books.getImageUrl()).into(bookHolder.imageView);
            bookHolder.wishListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent wishIntent = new Intent(v.getContext() ,WishListDialog.class);
                    wishIntent.putExtra("userName", LoginActivity.userEmail);
                    wishIntent.putExtra("ID",id);

                    startActivity(wishIntent);
                }
            });
            return row;
        }

    }
    static class BookHolder{
        TextView textView2,textView3;
        ImageView imageView;
        Button wishListButton;
    }
}
