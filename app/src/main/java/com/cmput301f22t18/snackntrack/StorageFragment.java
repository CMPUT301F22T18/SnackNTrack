package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StorageFragment extends Fragment{
    private Storage storage;
    private StorageAdapter storageAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> unit_list, location_list, category_list;
    FloatingActionButton fab;

    public StorageFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment Storage Fragment.
     */
    @NonNull
    @Contract(" -> new")
    public static StorageFragment newInstance() {
        return new StorageFragment();
    }
    private String TAG = "DEBUG";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = new Storage();
        initStorage();

        unit_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        location_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_locations_array)));
        category_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));
        storageAdapter = new StorageAdapter(storage, requireActivity().getSupportFragmentManager(), this,
                unit_list, location_list, category_list);
    }
    /**
     * This method is called when all the views are created
     * @param inflater a LayoutInflater
     * @param container a ViewGroup
     * @param savedInstanceState a Bundle
     * @return a View v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_storage, container, false);
        recyclerView = v.findViewById(R.id.storage_recycler_view);
        recyclerView.setAdapter(storageAdapter);



        fab = v.findViewById(R.id.add_ingredient_fab);
        fab.show();
        fab.setOnClickListener(view -> {
            if (savedInstanceState == null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", (Serializable) storage);
                bundle.putStringArrayList("units", unit_list);
                bundle.putStringArrayList("locations", location_list);
                bundle.putStringArrayList("categories", category_list);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, AddEditIngredientFragment.class, bundle)
                        .addToBackStack("AddIngredient")
                        .commit();

            }
        });
        return v;
    }

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