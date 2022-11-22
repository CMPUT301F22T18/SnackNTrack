package com.cmput301f22t18.snackntrack.controllers;

import android.util.ArrayMap;
import android.util.Log;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

/**
 * This Class Represents the Database Controller.
 * Its main purpose is to execute queries and get the objects from database
 * Its responsibilities include fetching and updating data
 * @author Duc Ho
 */
public class DBController {
    public String TAG = "Database";

    public DBController() {}

    public Storage getDatabaseStorage() {
        Storage storage = new Storage();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null)
        {
            db.collection("Storages")
                    .document(user.getUid()).get().addOnSuccessListener(task -> {
                        Object ingredients = task.get("ingredients");
                        if (ingredients != null) {
                            ArrayList list = (ArrayList) ingredients;
                            list.forEach(item -> {
                                Map<String, Object> map = (Map) item;
                                Ingredient ingredient = new Ingredient(map);
                                //Log.d(TAG, ingredient.toString());
                                storage.addIngredient(ingredient);
                            });
                        }
                    });
            return storage;
        }

        return storage;
    }
}
