package com.cmput301f22t18.snackntrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    RecipeList recipeList;
    String uid;

    private static final int CONTENT_VIEW_ID = 10101010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FirebaseFirestore fireStore;
        CollectionReference collectionReference;

        // setting the stage for fragment
        ConstraintLayout screen = new ConstraintLayout(this);
        screen.setId(CONTENT_VIEW_ID);
        this.setContentView(screen, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // user id
        uid = "P8oFVcti1XRhDEQJPtTH4DPmP0i1";

        fireStore = FirebaseFirestore.getInstance();
        // This collection reference is made specifically for the recipe list of a specific user
        collectionReference = fireStore.collection("recipeLists").document(uid).collection("recipes");

        Log.d("checkpoint", "collectionReference next");
        // Load data from the database to local
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d("collectionReference", "ran");
                recipeList.getRecipeList().clear();
                assert value != null;
                // for each recipe document in recipes
                for(QueryDocumentSnapshot recipeDocument : value) {
                    Recipe recipe = new Recipe(
                            (String) recipeDocument.getData().get("title"),
                            (int) recipeDocument.getData().get("prepTime"),
                            (String) recipeDocument.getData().get("comments"),
                            (int) recipeDocument.getData().get("servings"),
                            (String) recipeDocument.getData().get("category"),
                            new ArrayList<Ingredient>(),
                            (String) recipeDocument.getData().get("photoURL")
                    );
                    CollectionReference ingredientsCollectionReference = collectionReference.document(recipeDocument.getId()).collection("ingredients");
                    ingredientsCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            Log.d("ingredientCollectionReference", "ran");
                            assert value != null;
                            // for each ingredient in the recipe
                            for(QueryDocumentSnapshot ingredientDocument : value) {
                                recipe.addIngredient(ingredientDocument.toObject(Ingredient.class));
                            }
                        }
                    });
                    recipeList.addRecipe(recipe);
                }
            }
        });
        Log.d("recipeList data", recipeList.toString());
        getSupportFragmentManager().beginTransaction().replace(CONTENT_VIEW_ID, RecipeListFragment.newInstance(recipeList)).commit();
    }
}