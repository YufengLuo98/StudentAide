/*
 *Written by: Yufeng Luo
 * UNTESTED
 *
 * User login will check information entered serverside and use StringRequest response to determine whether or not credentials are known in database
 * On successful login, new Intent will be created taking users to MainActivity
 *
 *
 * */

package com.group04.studentaide;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginActivityGuest extends AppCompatActivity {


    Button mlogInButton;
    Button mLogOutButton;
    Button mRegisterButton;
    EditText minputEmail;
    EditText minputPassword;
    TextView signedInUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_guest);
        Log.d("loginGuestReached", "Hi");

        signedInUser = findViewById(R.id.signedInUserLabel);
        mlogInButton = (Button) findViewById(R.id.login2);
        mLogOutButton = findViewById(R.id.logoutButton);
        mRegisterButton = findViewById(R.id.register2);
        minputEmail = (EditText) findViewById(R.id.emailInputLogin2);
        minputPassword = (EditText) findViewById(R.id.password2);

        //If the response from server is success, allow login
        if (user != null)
            signedInUser.setText("Logged in as " + user.getDisplayName().toUpperCase());
        else
            signedInUser.setText("Logged in as Guest");

        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerScreen = new Intent(loginActivityGuest.this, registration.class);
                startActivity(registerScreen);
            }
        });
        mlogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFire();
            }
        });
        mLogOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signOut();
            }
        });

    }

    private void loginFire(){
        minputEmail = findViewById(R.id.emailInputLogin2);
        minputPassword = findViewById(R.id.password2);
        String email = minputEmail.getText().toString().trim();
        String password = minputPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            minputEmail.setError("Please enter your email");
            minputEmail.requestFocus(); // requestFocus will make the focus go to this box that is empty
        }

        if (TextUtils.isEmpty(password)) {
            minputPassword.setError("Please enter your password");
            minputPassword.requestFocus(); // requestFocus will make the focus go to this box that is empty
        }
        if (email.indexOf('@') == -1){
            minputEmail.setError("Please enter a valid email");
            minputEmail.requestFocus();
        }
        else {
            Log.d("InitiateSignIn", "Sign in was initiated.");
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("hwa135", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent returnMain = new Intent(loginActivityGuest.this, MainActivity.class);
                                Toast.makeText(loginActivityGuest.this, "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(returnMain);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("hwa136", "signInWithEmail:failure", task.getException());
                                Toast.makeText(loginActivityGuest.this, "Please check your email and password.",
                                        Toast.LENGTH_LONG).show();
                                // ...
                            }

                            // ...
                        }
                    });
        }


    }

    private void signOut(){
        if (user != null) {
            mAuth.signOut();
            Toast.makeText(loginActivityGuest.this, "You have signed out.", Toast.LENGTH_LONG).show();
            Intent returnMain = new Intent(loginActivityGuest.this, MainActivity.class);
            startActivity(returnMain);
        }
        else
            Toast.makeText(loginActivityGuest.this, "You are not signed in.", Toast.LENGTH_LONG).show();
    }


}

