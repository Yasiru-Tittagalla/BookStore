package com.example.bookstore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

import android.content.Intent;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  implements OnClickListener{

    TextView signUp;
    Button logIn;
    SignInButton signInButton;
    FirebaseAuth mAuth;
    GoogleApiClient googleApiClient;
    EditText editTextPassword, editTextUserName;
    private static  final  String TAG = "SignInActivity";
    private static  final  int RC_SIGN_IN = 9001;
    GoogleApiClient.OnConnectionFailedListener ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form
      findViewById(R.id.textViewSignup).setOnClickListener(this);
      signInButton = findViewById(R.id.googleSignIn);
      signInButton.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, ad)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

      logIn = findViewById(R.id.buttonLogin);
      logIn.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
              doLogin();
          }


      });
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.textViewSignup:
        startActivity(new Intent(this, SignUpActivity.class));
        break;
    case R.id.googleSignIn:
googleSignIn();
    break;
}
    }

    private void googleSignIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

    }

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

