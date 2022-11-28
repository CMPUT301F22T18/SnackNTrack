package com.cmput301f22t18.snackntrack.views.storage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.controllers.UnitAdapter;
import com.cmput301f22t18.snackntrack.models.AppUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PickUnitActivity extends AppCompatActivity {
    UnitAdapter unitAdapter;
    AppUser appUser;
    ArrayList<String> units;
    RecyclerView recyclerView;
    ImageButton confirmButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_unit);
        recyclerView = findViewById(R.id.pick_a_unit_list);
        confirmButton = findViewById(R.id.pick_a_unit_confirm_button);
        backButton = findViewById(R.id.pick_unit_back_button);

        appUser = new AppUser();
        units = new ArrayList<>();
        unitAdapter = new UnitAdapter(units, this);
        recyclerView.setAdapter(unitAdapter);
        setupUnits();

        confirmButton.setOnClickListener(v->pickUnit());
        backButton.setOnClickListener(v->finish());
    }

    private void pickUnit() {
        int checkPosition = unitAdapter.getCheckPosition();
        if (checkPosition != -1) {
            Intent intent = new Intent();
            intent.putExtra("unit", units.get(checkPosition));
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            finish();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupUnits() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference dr = db.collection("users").document(uid);
            dr.addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.e("FIREBASE ERROR", error.getLocalizedMessage());
                }
                else if (value != null) {
                    appUser = value.toObject(AppUser.class);
                    if (appUser != null)
                    {
                        Log.d("INFO", appUser.getUnits().toString());
                        units.clear();
                        units.addAll(appUser.getUnits());
                        unitAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}