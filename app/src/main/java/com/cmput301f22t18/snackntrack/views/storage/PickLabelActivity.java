package com.cmput301f22t18.snackntrack.views.storage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.controllers.LabelAdapter;
import com.cmput301f22t18.snackntrack.models.AppUser;
import com.cmput301f22t18.snackntrack.models.Label;
import com.cmput301f22t18.snackntrack.views.common.AddUnitDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PickLabelActivity extends AppCompatActivity {
    LabelAdapter labelAdapter;
    AppUser appUser;
    ArrayList<String> units;
    ArrayList<Label> locations, categories;
    RecyclerView recyclerView;
    ImageButton confirmButton, backButton, addLabelButton;
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_label);
        String labelType = this.getIntent().getStringExtra("labelType");
        setTitle(labelType);

        recyclerView = findViewById(R.id.pick_a_label_list);
        confirmButton = findViewById(R.id.pick_a_label_confirm_button);
        backButton = findViewById(R.id.pick_a_label_back_button);
        addLabelButton = findViewById(R.id.pick_a_label_add_button);
        if (labelType.equals("unit"))
            addLabelButton.setOnClickListener(v->openAddUnitDialog());

        appUser = new AppUser();
        units = new ArrayList<>();
        locations = new ArrayList<>();
        categories = new ArrayList<>();
        if (labelType.equals("unit"))
            labelAdapter = new LabelAdapter(units, this, labelType,
                    getSupportFragmentManager());
        else if (labelType.equals("location"))
            labelAdapter = new LabelAdapter(locations, this, labelType,
                    getSupportFragmentManager());
        else labelAdapter = new LabelAdapter(categories, this, labelType,
                    getSupportFragmentManager());
        recyclerView.setAdapter(labelAdapter);
        setupLabels();

        confirmButton.setOnClickListener(v->pickLabel(labelType));
        backButton.setOnClickListener(v->finish());
    }

    private void openAddUnitDialog() {
        AddUnitDialog addUnitDialog = new AddUnitDialog();
        addUnitDialog.show(getSupportFragmentManager(), AddUnitDialog.TAG);
    }

    /**
     * This function return the label that the user picks
     * @param labelType the type of label
     */
    private void pickLabel(String labelType) {
        int checkPosition = labelAdapter.getCheckPosition();
        if (checkPosition != -1) {
            Intent intent = new Intent();
            if (labelType.equals("unit"))
                intent.putExtra(labelType, units.get(checkPosition));
            else if (labelType.equals("location"))
                intent.putExtra(labelType, locations.get(checkPosition));
            else
                intent.putExtra(labelType, categories.get(checkPosition));
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            finish();
        }
    }

    /**
     * This function set the title of the activity
     * @param labelType the type of label the user wants to pick
     */
    private void setTitle(String labelType) {
        titleTextView = findViewById(R.id.pick_a_label_title);
        String labelCapitalized = labelType.substring(0, 1).toUpperCase() + labelType.substring(1);
        String title = "Pick a " + labelCapitalized;
        titleTextView.setText(title);
    }

    /**
     * This function setup the labels from the database
     */
    @SuppressLint("NotifyDataSetChanged")
    private void setupLabels() {
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
                        units.clear();
                        locations.clear();
                        categories.clear();
                        units.addAll(appUser.getUnits());
                        locations.addAll(appUser.getLocations());
                        categories.addAll(appUser.getCategories());
                        labelAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}