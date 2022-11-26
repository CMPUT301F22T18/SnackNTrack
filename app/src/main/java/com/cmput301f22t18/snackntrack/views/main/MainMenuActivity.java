package com.cmput301f22t18.snackntrack.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f22t18.snackntrack.MealPlanActivity;
import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.RecipeListFragment;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.views.storage.StorageActivity;
import com.cmput301f22t18.snackntrack.views.storage.StorageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    StorageFragment storageFragment;
    RecipeListFragment recipeListFragment;
    MealPlanActivity mealPlanActivity;
    String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        storageFragment = new StorageFragment();
        mealPlanActivity = new MealPlanActivity();
        RecipeList rl = new RecipeList();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cr = db.collection("recipeLists")
                .document(user.getUid()).collection("recipes");
        cr.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            rl.getRecipeList().clear();
            for (QueryDocumentSnapshot doc : value) {
                Recipe r = doc.toObject(Recipe.class);
                r.setRecipeIngredients(new ArrayList<>());
                CollectionReference cr2 = cr
                        .document(doc.getId())
                        .collection("ingredients");
                cr2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null)
                                    r.addIngredient(document.toObject(Ingredient.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                    }
                });
                rl.addRecipe(r);
            }
        });
        Log.d(TAG, rl.toString());
        recipeListFragment = RecipeListFragment.newInstance(rl);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        final int id_storage = R.id.storage;
        final int id_recipes = R.id.recipes;
        final int id_mealPlan = R.id.mealPlan;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                                .replace(R.id.fragment_container_main,
                                        RecipeListFragment.newInstance(rl))
                                .commit();
                        return true;

                    case id_mealPlan:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_main,
                                        mealPlanActivity)
                                .commit();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.storage);
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
