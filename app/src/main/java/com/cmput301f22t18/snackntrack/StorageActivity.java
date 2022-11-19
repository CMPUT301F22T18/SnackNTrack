package com.cmput301f22t18.snackntrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StorageActivity extends AppCompatActivity {
    private Storage storage;
    private StorageAdapter storageAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> unit_list, location_list, category_list;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        storage = new Storage();
        initStorage();

        unit_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        location_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_locations_array)));
        category_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));

        storageAdapter = new StorageAdapter(storage, getSupportFragmentManager(), this,
                unit_list, location_list, category_list);
        recyclerView = this.findViewById(R.id.storage_recycler_view);
        recyclerView.setAdapter(storageAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));



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

    private String TAG = "DEBUG";

    private void initStorage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    Map<String, Object> map = document.getData();
                    assert user != null;
                    if (!Objects.equals(user.getEmail(), document.get("email").toString())) continue;
                    ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) map.get("storage");
                    for (HashMap<String, Object> o : list) {
                        Timestamp timestamp = (Timestamp) o.get("bestBeforeDate");
                        assert timestamp != null;
                        Ingredient ingredient = new Ingredient(
                                o.get("description").toString(),
                                o.get("location").toString(),
                                o.get("unit").toString(),
                                o.get("category").toString(),
                                Integer.parseInt(o.get("amount").toString()),
                                new Date(timestamp.getSeconds())
                        );
                        storage.addIngredient(ingredient);
                    }
                }
                storageAdapter.notifyDataSetChanged();
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
//        Date today = new Date();
//        Ingredient i1 = new Ingredient("Salt",
//                "Counter-top","shaker", "Spice", 2, today);
//        Ingredient i2 = new Ingredient("Pepper",
//                "Cupboard","shaker", "Spice", 1, today);
//        Ingredient i3 = new Ingredient("Sugar",
//                "Pantry","shaker", "Spice", 3, today);
//
//        storage.addIngredient(i1);
//        storage.addIngredient(i2);
//        storage.addIngredient(i3);
    }
}