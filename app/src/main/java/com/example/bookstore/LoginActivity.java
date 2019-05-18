/*
This is the login activity
it gets the details from the user and validate them
 */

package com.example.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  implements OnClickListener{

    Button logIn;
    FirebaseAuth mAuth;
    EditText editTextPassword, editTextUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form
        findViewById(R.id.textViewSignup).setOnClickListener(this);
        logIn = findViewById(R.id.buttonLogin);
        logIn.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
              doLogin();
          }


      });
    }

    // starting sign up activity onclick
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(this, SignUpActivity.class));
            break;
        }
    }

    // this is the login method which validates username and password
    private void doLogin() {
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextEmail);


        String email = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

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

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                }
                else
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

