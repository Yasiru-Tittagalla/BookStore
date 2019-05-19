package com.example.bookstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription;
    private ImageView tvimage;
    private Button readbutton;
    private Button wishlistbutton;

    private static final String LOG_TAG = BookActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tvtitle = (TextView) findViewById(R.id.desctitle);
        tvdescription = (TextView) findViewById(R.id.descdescription);
        tvimage = (ImageView) findViewById(R.id.descbookthumbnail);
        readbutton = (Button) findViewById(R.id.descreadbutton);
        wishlistbutton = (Button) findViewById(R.id.descwishlist);

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

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        wishlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(getApplicationContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                //Setting message manually and performing action on button click
                builder.setMessage("Please add a wishlist note")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                    finish();
                                Toast.makeText(getApplicationContext(),input.getText(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Adding to wishlist");
                alert.show();
            }
        });

        // setting values
        tvtitle.setText(title);
        tvdescription.setText(description);
        Picasso.get().load(thumbnail).into(tvimage);
    }
}
