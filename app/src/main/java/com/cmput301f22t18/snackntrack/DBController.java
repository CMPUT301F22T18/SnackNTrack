package com.cmput301f22t18.snackntrack;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBController {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public DBController(FirebaseFirestore  firebaseFirestore, FirebaseAuth mAuth, FirebaseUser user) {
        this.db = firebaseFirestore;
        this.mAuth = mAuth;
        this.user = user;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }
}
