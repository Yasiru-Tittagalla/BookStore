/*
This file is for the tab 4
which is for the reminder tab
if the user needs a reminder to read a book,
this activity tab will memorize it and remind on time
 */

package com.example.bookstore;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tab3WishlistActivity extends Fragment {

    WishAdapter wishAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String LOG_TAG = Tab3WishlistActivity.class.getSimpleName();
    public static List<Book> bookList = new ArrayList<Book>();
    final Tab3WishlistActivity context = this;
    public String id;
    public EditText newWish;

    private ArrayList<String> bookIds = new ArrayList<String>();
    private ArrayList<String> bookWishes = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_wishlist, container, false);

        final ListView listView = rootView.findViewById(R.id.listView);

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
//                                            Log.d(LOG_TAG, "book id is " + entry1.getValue());
                                            bookIds.add(entry1.getValue().toString());
                                        }
                                        if(entry1.getKey().equals("wish")) {
//                                            Log.d(LOG_TAG, "wish is " + entry1.getValue());
                                            bookWishes.add(entry1.getValue().toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                }

                FetchBooks.bookList = new ArrayList<Book>();
                FetchBooks.wishList = new ArrayList<Book>();

                //send in the each book id of the wish list to fetch its data
                wishAdapter = new WishAdapter(getContext(),R.layout.wish_row_layout);
                listView.setAdapter(wishAdapter);
                for (String bookId: bookIds ) {
                    new FetchBooks().execute("id:" + bookId, "wishList");
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int iterator = 0;
                        for (Book wishBook : FetchBooks.wishList) {
//                            Log.d(LOG_TAG, "ids are " +  bookIds);
//                            Log.d(LOG_TAG, "wishes are " +  bookWishes);
                            wishBook.setWish(bookWishes.get(iterator));
                            Log.d(LOG_TAG, "current wish is " +  bookWishes.get(iterator));
                            wishAdapter.add(wishBook);
                            iterator++;
                        }
                    }
                }, 4000);

                for (String wish : bookWishes) {

                }

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 7000);

            }
        });

        return rootView;
    }

class WishAdapter extends ArrayAdapter{

    List list = new ArrayList();
    public WishAdapter(Context context, int reousrce){
        super((Context) context,reousrce);
    }

    public  void add(Book object){
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
        WishHolder wishHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.wish_row_layout,parent,false);
            wishHolder = new WishHolder();
//            newWish = row.findViewById(R.id.editText);
            wishHolder.textView2 = (TextView) row.findViewById(R.id.textView2);
            wishHolder.wishUpdate = (EditText) row.findViewById(R.id.wishUpdate);
            wishHolder.imageView = (ImageView) row.findViewById(R.id.imageView);
            wishHolder.deleteWish = (Button) row.findViewById(R.id.deleteWish);
            wishHolder.updateWish = (Button) row.findViewById(R.id.updateWish);
            row.setTag(wishHolder);
        }
        else {
            wishHolder = (Tab3WishlistActivity.WishHolder) row.getTag();
        }
        Book book = (Book) getItem(position);
//        Log.d(LOG_TAG, "getting item " + position);
        wishHolder.textView2.setText(book.getTitle());
        wishHolder.wishUpdate.setText(book.getWish());
        Picasso.get().load(book.getThumbnail()).into(wishHolder.imageView);
        wishHolder.updateWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update the wish
                Log.d(LOG_TAG, "udpated wish ");
//                DocumentReference wishRef = db.collection("wishlist")
//                        .document(/*BookId*/);
//                wishRef.update("wish",newWish.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(getContext(),"Updated Sucessfully",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
        wishHolder.deleteWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete the wish
                Log.d(LOG_TAG, "deleted wish ");
//                DocumentReference wishRef = db.collection("wishlist")
//                        .document(/*id of the wish*/);
//                wishRef.delete();
            }
        });
        return row;
    }
}

    static class WishHolder{
        TextView textView2;
        ImageView imageView;
        Button deleteWish,updateWish;
        EditText wishUpdate;
    }

}
