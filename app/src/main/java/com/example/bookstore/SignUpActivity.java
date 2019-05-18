/*
This is the signup activity
it registers a user on the database
 */

package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextPassword, editTextUserName;
    Button signUpButton;
    TextView signIn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
     protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpButton = findViewById(R.id.buttonSignUp);
        signIn = findViewById(R.id.textViewLogin);
        progressBar =findViewById(R.id.progressbar);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser(){
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextEmail);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        String email = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
         progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "You Are Registerd Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            if( task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUpActivity.this, "This E-mail Is Already Registered With Our Service",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                        }

                            // ...

                    }
                });

        if (email.isEmpty()) {
            editTextUserName.setError("Email Is Required");
            editTextUserName.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUserName.setError("Please Enter A Valid E-mail");
            editTextUserName.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password Is Required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum Length Of The Password Should be 6");
            editTextPassword.requestFocus();
            return;
        }
    }
}
