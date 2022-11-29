package com.cmput301f22t18.snackntrack.views.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f22t18.snackntrack.MealPlanFragment;
import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.RecipeListFragment;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.views.shoppingList.ShoppingListFragment;
import com.cmput301f22t18.snackntrack.views.storage.StorageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    StorageFragment storageFragment;
    RecipeListFragment recipeListFragment;
    MealPlanFragment mealPlanFragment;
    ShoppingListFragment shoppingListFragment;
    String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        storageFragment = new StorageFragment();
        mealPlanFragment = new MealPlanFragment();
        recipeListFragment = new RecipeListFragment();
        shoppingListFragment = new ShoppingListFragment();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            bottomNavigationView = findViewById(R.id.bottom_navigation_view);
            final int id_storage = R.id.storage;
            final int id_recipes = R.id.recipes;
            final int id_mealPlan = R.id.mealPlan;
            final int id_shoppingList = R.id.shoppingList;
            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case id_storage:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_main, storageFragment)
                                .commit();
                        return true;

                    case id_recipes:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_main, recipeListFragment)
                                .commit();
                        return true;

                    case id_mealPlan:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_main,
                                        mealPlanFragment)
                                .commit();
                        return true;
                    case id_shoppingList:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_main,
                                        shoppingListFragment)
                                .commit();
                        return true;
                }
                return false;
            });
            bottomNavigationView.setSelectedItemId(R.id.storage);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FirebaseAuth.getInstance().signOut();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
