/*
    Written by Alexander Wang, Jason Leung
    1. A class that is used to retrieve various pieces of information about the user, such as document reference ID
 */

package com.group04.studentaide;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InformationRetrieval {

    private static InformationRetrieval ourInstance = null;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String studentDocumentID;
    private FirebaseUser user;

    // Constructor
    public InformationRetrieval(){

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            String uid = user.getUid();
            db.collection("Students")
                    .whereEqualTo("User_ID", uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("documentIDGet", "Document ID is: " + document.getId());
                                    studentDocumentID = document.getId();
                                }
                            } else {
                                Log.v("signedinwrong", "you're signed into the wrong account for testing");
                            }
                        }
                    });

        }

    }

    // When the user changes accounts, update the document ID
    public void updateID() {

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            String uid = user.getUid();
            db.collection("Students")
                    .whereEqualTo("User_ID", uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("documentIDGet", "Document ID is: " + document.getId());
                                    studentDocumentID = document.getId();
                                }
                            } else {
                                Log.v("signedinwrong", "you're signed into the wrong account for testing");
                            }
                        }
                    });

        } else {

            studentDocumentID = null;

        }

    }

    public static InformationRetrieval getInstance() {
        if (ourInstance == null){
            ourInstance = new InformationRetrieval();
        }
        return ourInstance;
    }


    // Returns the user's document ID
    public String getDocumentID() {

        Log.v("Hareye", studentDocumentID);
        return studentDocumentID;

    }
}