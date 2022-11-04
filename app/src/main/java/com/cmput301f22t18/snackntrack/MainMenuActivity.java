package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

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
                Intent myIntent = new Intent(view.getContext(), RecipeListActivity.class);
                startActivity(myIntent);
            }

        });
    }
}
