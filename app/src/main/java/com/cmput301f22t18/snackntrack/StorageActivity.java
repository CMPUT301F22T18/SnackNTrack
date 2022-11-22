package com.cmput301f22t18.snackntrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.cmput301f22t18.snackntrack.controllers.DBController;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StorageActivity extends AppCompatActivity {
    private Storage storage = new Storage();
    private ArrayList<String> unit_list, location_list, category_list;
    FloatingActionButton fab;

    private final String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        unit_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        location_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_locations_array)));
        category_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));

        StorageAdapter storageAdapter = new StorageAdapter(storage, getSupportFragmentManager(), this,
                unit_list, location_list, category_list);
        RecyclerView recyclerView = this.findViewById(R.id.storage_recycler_view);
        recyclerView.setAdapter(storageAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference documentReference =
                db.collection("Storages").document(user.getUid());

        documentReference.addSnapshotListener((value, error) -> {
            assert value != null;
            Object ingredients = value.get("ingredients");
            if (ingredients != null) {
                ArrayList list = (ArrayList) ingredients;
                list.forEach(item -> {
                    Map<String, Object> map = (Map) item;
                    Ingredient ingredient = new Ingredient(map);
                    //Log.d(TAG, ingredient.toString());
                    storage.addIngredient(ingredient);
                });
            }
            storageAdapter.notifyDataSetChanged();
        });

        fab = findViewById(R.id.add_ingredient_fab);
        fab.show();
        fab.setOnClickListener(view -> {
            if (savedInstanceState == null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", (Serializable) storage);
                bundle.putStringArrayList("units", unit_list);
                bundle.putStringArrayList("locations", location_list);
                bundle.putStringArrayList("categories", category_list);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, AddEditIngredientFragment.class, bundle)
                        .addToBackStack("AddIngredient")
                        .commit();

            }
        });
    }
}