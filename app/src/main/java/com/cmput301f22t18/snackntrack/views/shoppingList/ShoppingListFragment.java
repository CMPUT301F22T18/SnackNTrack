package com.cmput301f22t18.snackntrack.views.shoppingList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.controllers.ShoppingListAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.ShoppingList;
import com.cmput301f22t18.snackntrack.models.MealPlan;
import com.cmput301f22t18.snackntrack.models.Storage;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


/**
 * This class is the fragment for the Shopping List
 *
 * @author Charlotte Kalutycz
 * @see ShoppingList
 * @see ShoppingListAdapter
 */
public class ShoppingListFragment extends Fragment {
    private static final String ARG_TEXT = "ShoppingList";
    private String TAG;

    private ShoppingList shoppingList;
    private ShoppingListAdapter shoppingListAdapter;
    private MealPlan mealPlan;
    private Storage storage;
    private RecyclerView recyclerView;
    ImageButton sortButton;
    ImageButton addButton;
    TextView headerText;

    /**
     * This method creates a new instance of the ShoppingListFragment
     * @return a new instance of ShoppingListFragment
     */
    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        // Get the views for this fragment
        recyclerView = v.findViewById(R.id.shopping_list_recycler_view);
        sortButton = v.findViewById(R.id.sort_button_shopping_list);
        addButton = v.findViewById(R.id.purchased_button);
        headerText = v.findViewById(R.id.shopping_list_header_text);

        // Get the current user
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert(user != null);
        String uid = user.getUid();

        // Get a copy of the storage
        TAG = "Storage";
        storage = new Storage();
        CollectionReference cf = db.collection("storages")
                .document(uid).collection("ingredients");

        cf.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            storage.clearStorage();
            for (QueryDocumentSnapshot doc : value) {
                storage.addIngredient(doc.toObject(Ingredient.class));
            }
        });

        // Get a copy of the meal plan
        TAG = "MealPlan";
        mealPlan = new MealPlan();
        cf = db.collection("mealPlans")
                .document(uid).collection("dailyPlans");

        cf.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            // Clear MealPlan
            for (QueryDocumentSnapshot doc : value) {
                mealPlan.addDailyPlan(doc.toObject(DailyPlan.class));
            }
        });

        // Fill shopping list
        shoppingList.calculateList(mealPlan, storage);

        // Create instance of the ShoppingListAdapter
        shoppingListAdapter = new ShoppingListAdapter(shoppingList);
        recyclerView.setAdapter(shoppingListAdapter);

        // When clicked on the sort button, some choices are shown
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: let user select their way to sort the list
                //Toast.makeText(v.getContext(), "sort button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get all ingredients currently checked
                ArrayList<Ingredient> checkedIngredients = shoppingListAdapter.getCheckedIngredients();
                // Add these ingredients to the storage, and remove from ShoppingList
                if (checkedIngredients.size() != 0) {
                    for (int i = 0; i < checkedIngredients.size(); i++) {
                        Bundle result = new Bundle();
                        result.putSerializable("new_item", checkedIngredients.get(i));
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        CollectionReference cr = db.collection("storages")
                                .document(user.getUid())
                                .collection("ingredients");
                        Query query = cr
                                .whereEqualTo("description", checkedIngredients.get(i).getDescription())
                                .whereEqualTo("amount", checkedIngredients.get(i).getAmount())
                                .whereEqualTo("category", checkedIngredients.get(i).getCategory())
                                .whereEqualTo("unit", checkedIngredients.get(i).getUnit());
                        Ingredient finalIngredient = checkedIngredients.get(i);
                        query.get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("DEBUG", document.getId() + " => " + document.getData());
                                    cr.document(document.getId()).set(finalIngredient);
                                }
                            } else {
                                Log.d("DEBUG", "Error getting documents: ", task.getException());
                            }
                        });
                        shoppingList.removeIngredient(checkedIngredients.get(i));
                    }
                    // Now that all ingredients have been added to the storage, clear checkedIngredients
                    checkedIngredients.clear();
                }
            }
        });

        return v;
    }

}
