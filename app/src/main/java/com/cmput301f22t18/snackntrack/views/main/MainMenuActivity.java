package com.cmput301f22t18.snackntrack.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.RecipeListActivity;
import com.cmput301f22t18.snackntrack.views.storage.StorageActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);
        //ActionBar actionBar = getSupportActionBar();
        //View v = actionBar.getCustomView();
        Button storageButton = findViewById(R.id.storage_id);
        storageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), StorageActivity.class);
                startActivity(myIntent);
            }

        });

        Button recipeButton = findViewById(R.id.recipe_list_id);
        recipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent myIntent = new Intent(view.getContext(), RecipeListActivity.class);
                //startActivity(myIntent);
            }

        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FirebaseAuth.getInstance().signOut();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
