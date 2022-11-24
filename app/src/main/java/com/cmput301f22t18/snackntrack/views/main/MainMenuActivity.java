package com.cmput301f22t18.snackntrack.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.RecipeListFragment;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.views.storage.StorageActivity;
import com.cmput301f22t18.snackntrack.views.storage.StorageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    StorageFragment storageFragment;
    RecipeListFragment recipeListFragment;
    String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        storageFragment = new StorageFragment();
        RecipeList rl = new RecipeList();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference cr = db.collection("recipeLists")
//                .document(user.getUid()).collection("recipes");
//        cr.addSnapshotListener((value, error) -> {
//            if (error != null) {
//                Log.w(TAG, "Listen failed.", error);
//                return;
//            }
//            rl.getRecipeList().clear();
//            for (QueryDocumentSnapshot doc : value) {
//                CollectionReference cr2 = cr.document(doc.getId()).collection("ingredients");
//                Recipe r = doc.toObject(Recipe.class);
//                for (DocumentSnapshot doc2 : cr2.get().getResult()) {
//                    Log.d(TAG, doc2.toObject(Ingredient.class).toString());
//                }
//                rl.addRecipe(doc.toObject(Recipe.class));
//
//            }
//        });
        recipeListFragment = RecipeListFragment.newInstance(rl);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.storage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, storageFragment).commit();
                        return true;

                    case R.id.recipes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, recipeListFragment).commit();
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
