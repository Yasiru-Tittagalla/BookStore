/*
This is the login activity
it gets the details from the user and validate them
 */

package com.example.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  implements OnClickListener{

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
                .requestIdToken("903678995544-1qdtn8rhl8otugog7bu40vms0v3egb0g.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, ad)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        findViewById(R.id.textViewSignup).setOnClickListener(this);
        logIn = findViewById(R.id.buttonLogin);
        logIn.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
              doLogin();
          }


      });

        // logo icon rotation
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);

        ImageView image= (ImageView) findViewById(R.id.logoImage);
        image.startAnimation(rotate);
    }

    // starting sign up activity onclick
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
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try{
                GoogleSignInAccount account = task.getResult();
                firebaseAuthWithGoogle(account);
            }
//            catch (ApiException e){
//                Log.w(TAG,"Error when Signing in with Google",e);
//            }

//        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user =mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"User Authentication Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*private void doLogin() {

        switch (v.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(this, SignUpActivity.class));
            break;
        }
    }*/


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

