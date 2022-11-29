package com.cmput301f22t18.snackntrack.views.storage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.RecipeListFragment;
import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StorageFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    Storage storage;
    RecyclerView recyclerView;
    StorageAdapter storageAdapter;
    ImageButton sortButton;
    ArrayList<String> ingredientIDs;
    FloatingActionButton fab;
    String ERROR_TAG = "STORAGE ERROR";
    ActivityResultLauncher<Intent> mGetContent;

    public StorageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent i = result.getData();
                        if (i != null) {
                            if (i.hasExtra("action") &&
                                    i.getStringExtra("action").equals("delete")) {
                                String id = i.getStringExtra("id");
                                int index = storage.getIds().indexOf(id);
                                storage.getIds().remove(id);
                                storage.getStorageList().remove(index);
                                storageAdapter.notifyItemChanged(index);
                            }
                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_storage, container, false);
        storage = new Storage();
        ingredientIDs = new ArrayList<>();
        storageAdapter = new StorageAdapter(this.getContext(), storage, mGetContent);
        recyclerView = v.findViewById(R.id.fragment_storage_ingredient_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(storageAdapter);
        fab = v.findViewById(R.id.add_ingredient_fab);

        fab.setOnClickListener(v1 -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), AddIngredientActivity.class);
            startActivity(intent);
        });
        setUpStorage("default");
        sortButton = v.findViewById(R.id.storage_sort_button);
        sortButton.setOnClickListener(this::displaySortingOptions);
        return v;
    }

    private void displaySortingOptions(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.storage_sort_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.storage_sort_description) {
            //Toast.makeText(this.getContext(), "Title", Toast.LENGTH_SHORT).show();
            setUpStorage("description");
            return true;
        }
        else if(id == R.id.storage_sort_date) {
            //Toast.makeText(this.getContext(), "Time", Toast.LENGTH_SHORT).show();
            setUpStorage("bestBeforeDate");
            return true;
        }
        else if(id == R.id.storage_sort_category) {
            //Toast.makeText(this.getContext(), "Servings", Toast.LENGTH_SHORT).show();
            setUpStorage("category");
            return true;
        }
        else if(id == R.id.storage_sort_location) {
            //Toast.makeText(this.getContext(), "Category", Toast.LENGTH_SHORT).show();
            setUpStorage("location");
            return true;
        }
        else if(id == R.id.storage_sort_default) {
            //Toast.makeText(this.getContext(), "Default", Toast.LENGTH_SHORT).show();
            setUpStorage("default");
            return true;
        }
        else {
            return false;
        }
    }

    public void setUpStorage(String option) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            storage.clearStorage();
            ingredientIDs = new ArrayList<>();
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Query cr = db.collection("storages")
                    .document(uid).collection("ingredients");
            if (!option.equals("default")) {
                cr = cr.orderBy(option);
            }
            cr.addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.d(ERROR_TAG, error.getLocalizedMessage());
                }
                else if (value != null) {
                    for (QueryDocumentSnapshot documentSnapshot: value) {
                        Ingredient ingredient = documentSnapshot.toObject(Ingredient.class);
                        String id = documentSnapshot.getId();
                        if (ingredientIDs.contains(id)) {
                            int position = ingredientIDs.indexOf(id);
                            storage.setIngredient(position, ingredient);
                            storageAdapter.notifyItemChanged(position);
                        }
                        else {
                            ingredientIDs.add(id);
                            storage.addId(id);
                            storage.addIngredient(ingredient);
                            storageAdapter
                                    .notifyItemChanged(storageAdapter.getItemCount() - 1);
                        }

                    }
                }
            });
        }
    }
}