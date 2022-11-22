package com.cmput301f22t18.snackntrack;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class StorageActivity extends AppCompatActivity {
    private Storage storage;
    private ArrayList<String> unit_list, location_list, category_list;
    FloatingActionButton fab;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        storage = new Storage();
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
        assert(user != null);
        final DocumentReference documentReference =
                db.collection("Storages").document(user.getUid());

        documentReference.addSnapshotListener((value, error) -> {
            assert value != null;
            Object ingredients = value.get("ingredients");
            if (ingredients != null) {
                @SuppressWarnings("unchecked")
                ArrayList<Object> list = (ArrayList<Object>)ingredients;

                list.forEach(item -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) item;
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
                bundle.putSerializable("storage", storage);
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