package com.example.bookstore;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WishListDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String userName = null;
        String ISBN =null;
        String wishMessage = null;
        super.onCreate(savedInstanceState);
        Intent i1 = getIntent();
        Bundle extra = i1.getExtras();
        userName =extra.getString("userName");
        ISBN =extra.getString("ISBN");
        wishMessage =extra.getString("wishMessage");
        saveToDatabase(userName,ISBN,wishMessage);
        LayoutInflater li = LayoutInflater.from(this);
        final View WishView = li.inflate(R.layout.wish_list_layout, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(WishView);
        final EditText userInput = (EditText) WishView.findViewById(R.id.editTextDialogUserInput);

        /*alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String wish = userInput.getText().toString();
                if (!wish.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Pleas Enter A Wish Message",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveToDatabase(String userName, String isbn, String wishMessage) {

    }
}
