package com.cmput301f22t18.snackntrack.views.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.cmput301f22t18.snackntrack.R;

public class AddIngredientActivity extends AppCompatActivity {
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        backButton = findViewById(R.id.add_ingredient_back_button);
        backButton.setOnClickListener(v->goBack());
    }

    public void goBack() {
        finish();
    }
}