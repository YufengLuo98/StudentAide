/*
 * Written by Alexander Wang
 *
 * The main screen has no real functions, just some buttons that lead to other functional pages.
 */

package com.group04.studentaide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class MainActivityEducator extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build();

    InformationRetrieval infoRetrieve = InformationRetrieval.getInstance();

    private Button loginScreen;
    private TextView greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_educator);

        loginScreen = findViewById(R.id.loginOpen);
        greeting = findViewById(R.id.greetingLabelMain);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        infoRetrieve.updateID();

        if(user != null){
            createGreeting();
        }
        else{
            greeting.setText("Welcome back.");
        }
        loginScreen.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent loginGuest = new Intent(MainActivityEducator.this, LoginActivityGuest.class);
                startActivity(loginGuest);
            }
        });

    }

    //Yufeng: I'm not sure if View view needs to be passed into these functions because when the functions are called
    //and the new Intent is created, startActivity will open the class that is associated with the Intent

    public void calendarScreen(View view){
        Intent calendar = new Intent(this, CalendarActivity.class);
        startActivity(calendar);
    }

    public void statsScreen(View view){
        Intent stats = new Intent(this, StudyStatistics.class);
        startActivity(stats);
    }

    private void createGreeting(){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        String fullName = user.getDisplayName();
        String arr[] = fullName.split(" ", 2);

        String firstName = arr[0];
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);

        if(timeOfDay >= 0 && timeOfDay < 3){
            greeting.setText("Studying so late " + firstName + "?");
        }else if(timeOfDay >= 3 && timeOfDay < 6){
            greeting.setText("Awake so early " + firstName + "?");
        }else if(timeOfDay >= 6 && timeOfDay < 12){
            greeting.setText("Good Morning, " + firstName);
        }else if(timeOfDay >= 12 && timeOfDay < 18){
            greeting.setText("Good Afternoon, " + firstName);
        }else if(timeOfDay >= 18 && timeOfDay < 21){
            greeting.setText("Good Evening, " + firstName);
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            greeting.setText("Studying so late " + firstName + "?");
        }
    }

}