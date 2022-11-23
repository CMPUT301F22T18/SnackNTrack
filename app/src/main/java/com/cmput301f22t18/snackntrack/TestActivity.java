package com.cmput301f22t18.snackntrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

public class TestActivity extends AppCompatActivity {
    RecipeList recipeList;
    FirebaseFirestore fireStore;
    CollectionReference collectionReference;
    StorageReference storageReference;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        uid = "P8oFVcti1XRhDEQJPtTH4DPmP0i1";

        fireStore = FirebaseFirestore.getInstance();
        // This collection reference is made specifically for the recipe list of a specific user
        collectionReference = fireStore.collection("recipeLists").document(uid).collection("recipes");
        storageReference = FirebaseStorage.getInstance().getReference();

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                recipeList.getRecipeList().clear();
                assert value != null;
                // for each recipe in recipes
                for(QueryDocumentSnapshot item : value) {
                    // this may have error because we have a nested collection
                    Recipe recipe = item.toObject(Recipe.class);
                }
            }
        });
    }
}