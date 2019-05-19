package com.example.bookstore;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;

    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_book, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        // set the datae here
        myViewHolder.book_title.setText("Game of Thrones");
        Uri imageUri = Uri.parse("https://books.google.com/books/content?id=BWXhBQAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
        myViewHolder.book_thumbnail.setImageURI(imageUri);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_title;
        ImageView book_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            book_title = (TextView) itemView.findViewById(R.id.book_title_id);
            book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
        }
    }
}
