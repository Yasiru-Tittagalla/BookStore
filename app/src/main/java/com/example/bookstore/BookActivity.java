package com.example.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription;
    private ImageView tvimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tvtitle = (TextView) findViewById(R.id.desctitle);
        tvdescription = (TextView) findViewById(R.id.descdescription);
        tvimage = (ImageView) findViewById(R.id.descbookthumbnail);

        // receive data
        Intent intent = getIntent();
        String title = intent.getExtras().getString("BookTitle");
        String description = intent.getExtras().getString("Description");
        String thumbnail = intent.getExtras().getString("Thumbnail");

        // setting values
        tvtitle.setText(title);
        tvdescription.setText(description);
        Picasso.get().load(thumbnail).into(tvimage);
    }
}
