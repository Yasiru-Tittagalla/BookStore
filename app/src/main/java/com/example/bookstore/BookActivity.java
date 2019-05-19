package com.example.bookstore;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription;
    private ImageView tvimage;
    private Button readbutton;

    private static final String LOG_TAG = BookActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tvtitle = (TextView) findViewById(R.id.desctitle);
        tvdescription = (TextView) findViewById(R.id.descdescription);
        tvimage = (ImageView) findViewById(R.id.descbookthumbnail);
        readbutton = (Button) findViewById(R.id.descreadbutton);

        // receive data
        Intent intent = getIntent();
        String title = intent.getExtras().getString("BookTitle");
        String description = intent.getExtras().getString("Description");
        String thumbnail = intent.getExtras().getString("Thumbnail");
        final String webReaderLink = intent.getExtras().getString("WebLink");

        readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WebView webView = new WebView(getApplication());
//                setContentView(webView);
//                webView.loadUrl(webReaderLink);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webReaderLink));
                startActivity(browserIntent);
            }
        });

        // setting values
        tvtitle.setText(title);
        tvdescription.setText(description);
        Picasso.get().load(thumbnail).into(tvimage);
    }
}
