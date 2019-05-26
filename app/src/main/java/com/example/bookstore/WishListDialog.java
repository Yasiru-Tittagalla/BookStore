package com.example.bookstore;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
/** This is the dialog that pops up when the user tries to
 * enter a book into his/her wish list this will take users
 * wish and save the book in the database with relevant info
 */
public class WishListDialog extends Activity {

    String userName = null;
    String ISBN =null;
    String wishMessage = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    AlertDialog dialog =null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent i1 = getIntent();
        //get values from the intent
        Bundle extra = i1.getExtras();
        userName =extra.getString("userName");
        ISBN =extra.getString("ID");
        wishMessage =extra.getString("wishMessage");
        LayoutInflater li = LayoutInflater.from(this);
        final View WishView = li.inflate(R.layout.wish_list_layout, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(WishView);
        final EditText userInput = (EditText) WishView.findViewById(R.id.editTextDialogUserInput);
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                savedInstanceState.clear();
            }
        });
        alertDialogBuilder.setPositiveButton("Add To Wish List", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
      dialog = alertDialogBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String wish = userInput.getText().toString();
                if (!wish.isEmpty()) {
                    //get the user logged in
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                   String userEmail = user.getEmail();
                    saveToDatabase(userEmail,ISBN,wish);
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Pleas Enter A Wish Message",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveToDatabase(String userName, String isbn, String wishMessage) {

        //create the firebase ref
        DocumentReference newUserRef = db.collection("wishlist")
                .document(userName + "_" + isbn);
        //create the wish objcet with values
        userWishList u1 = new userWishList(userName,isbn,wishMessage);

        //save to the database
        newUserRef.set(u1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Added To Wish List",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
