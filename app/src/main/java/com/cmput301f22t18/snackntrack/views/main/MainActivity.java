package com.cmput301f22t18.snackntrack.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.AppUser;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    FirebaseUser user;
    String TAG_ERROR = "ERROR";
    String TAG_INFO = "INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        Button button_getting_started = findViewById(R.id.button_getting_started);
        button_getting_started.setOnClickListener(v -> startSignIn());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            launchMenu();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            launchMenu();
        }
    }

    private void launchMenu() {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.setClass(getApplicationContext(), MainMenuActivity.class);
        startActivity(i);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            (result) -> {
                IdpResponse response = result.getIdpResponse();
                if (result.getResultCode() != RESULT_OK) {
                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // response.getError().getErrorCode() and handle the error.
                    // ...
                    if (response == null) {
                        Log.d(TAG_ERROR, "Cancel signing in");
                    }
                    else if  (response.getError() != null) {
                        Log.e(TAG_ERROR, response.getError().getMessage());
                    }
                }
                // No error
                else {
                    // Get the current user
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String userName = user.getDisplayName();
                        if (userName != null)
                            Log.i(TAG_INFO, userName);

                        // Create a storage document
                        String uid = user.getUid();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference cf = db.collection("storages");
                        DocumentReference df = cf
                                .document(uid);
                        df.get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG_INFO, "Document exists!");
                                        } else {
                                            Log.d(TAG_INFO, "Document does not exist!");
                                        }
                                    } else {
                                        Log.d(TAG_ERROR, "Failed with: ", task.getException());
                                }});
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DocumentReference dr = db.collection("users").document(uid);
                        AppUser appUser = new AppUser();
                        appUser.initializeNewLabels();
                        dr.set(appUser, SetOptions.merge()).addOnCompleteListener(
                                        (task) -> Log.d("INFO", "New user create"))
                                .addOnFailureListener(
                                        (error) -> Log.e("ERROR", error.getLocalizedMessage()));
                        launchMenu();
                    }

                }
            });

    private void startSignIn() {
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.Theme_SnackNTrack)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                .build();

        signInLauncher.launch(signInIntent);
    }

}