package com.cmput301f22t18.snackntrack.views.shoppingList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301f22t18.snackntrack.DailyPlan;
import com.cmput301f22t18.snackntrack.MealPlan;
import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.RecipeListFragment;
import com.cmput301f22t18.snackntrack.controllers.ShoppingListAdapter;
import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.ShoppingList;
import com.cmput301f22t18.snackntrack.models.Storage;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 *  * A simple {@link Fragment} subclass.
 *  * Use the {@link ShoppingListFragment#newInstance} factory method to
 *  * create an instance of this fragment.
 *
 * This class is the fragment for the Shopping List
 *
 * @author Charlotte Kalutycz
 * @see ShoppingList
 * @see ShoppingListAdapter
 */
public class ShoppingListFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
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
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Recipe> recipeArrayList;
    ArrayList<DocumentReference> ingredientDR;
    ArrayList<DocumentReference> dailyPlanDR;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * This method creates a new instance of the ShoppingListFragment
     * @return a new instance of ShoppingListFragment
     */
    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        recyclerView = v.findViewById(R.id.shopping_list);
        sortButton = v.findViewById(R.id.sort_button_shopping_list);
        addButton = v.findViewById(R.id.purchased_button);
        headerText = v.findViewById(R.id.shopping_list_header_text);

        // Get the current user
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert(user != null);
        String uid = user.getUid();

        // Create the ShoppingList
        shoppingList = new ShoppingList();

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

        ingredientArrayList = new ArrayList<Ingredient>();
        recipeArrayList = new ArrayList<Recipe>();
        ingredientDR = new ArrayList<DocumentReference>();
        dailyPlanDR = new ArrayList<DocumentReference>();

        cf = db.collection("mealPlans")
                .document(uid).collection("mealPlanList");

        cf.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Storage", "Listen failed.", error);
                return;
            }
            for (QueryDocumentSnapshot doc : value) {
                Log.w("DATE: ", doc.get("date").toString(), error);
                doc.get("date");
                ArrayList<DocumentReference> ingredients = (ArrayList<DocumentReference>) doc.get("ingredients");
                ArrayList<DocumentReference> recipes = (ArrayList<DocumentReference>) doc.get("recipes");
                DailyPlan dailyPlan = new DailyPlan();
                if (ingredients != null) {
                    ingredientDR.addAll(ingredients);
                    for (DocumentReference ingredient : ingredients) {
                        ingredient.addSnapshotListener((value2, error2) -> {
                            if (value2 != null) {
                                Ingredient ingredient2 = value2.toObject(Ingredient.class);
                                ingredientArrayList.add(ingredient2);
                                dailyPlan.addIngredient(ingredient2);
                                if (dailyPlanDR.contains(doc.getReference(  ))) {
                                    int position = dailyPlanDR.indexOf(doc.getReference());
                                    mealPlan.getDailyPlan().get(position).addIngredient(ingredient2);
                                    shoppingList.calculateList(mealPlan, storage);
                                } else {
                                    dailyPlanDR.add(doc.getReference());
                                    mealPlan.addDailyPlan(dailyPlan);
                                    shoppingList.calculateList(mealPlan, storage);
                                }
                            }
                        });
                    }
                }
                if (recipes != null) {
                    for (DocumentReference recipe : recipes) {
                        recipe.addSnapshotListener((value2, error2) -> {
                            if (value2 != null) {
                                Recipe recipe2 = value2.toObject(Recipe.class);
                                recipeArrayList.add(recipe2);
                                dailyPlan.addRecipe(recipe2);
                                if (dailyPlanDR.contains(doc.getReference())) {
                                    int position = dailyPlanDR.indexOf(doc.getReference());
                                    mealPlan.getDailyPlan().get(position).addRecipe(recipe2);
                                    shoppingList.calculateList(mealPlan, storage);
                                } else {
                                    dailyPlanDR.add(doc.getReference());
                                    mealPlan.addDailyPlan(dailyPlan);
                                    shoppingList.calculateList(mealPlan, storage);
                                }
                            }
                        });
                    }
                }
            }
        });

        // Create instance of the ShoppingListAdapter
        shoppingListAdapter = new ShoppingListAdapter(shoppingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(shoppingListAdapter);
        shoppingListAdapter.notifyDataSetChanged();


        // When the sort button is clicked, user can choose from description or category
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ShoppingListFragment.this.getContext(), v);
                popup.setOnMenuItemClickListener(ShoppingListFragment.this);
                popup.inflate(R.menu.shopping_list_sort_menu);
                popup.show();
            }
        });

        // When user clicks the green button, confirms whether or not they would like to move selected ingredients to the storage
        addButton.setOnClickListener(new View.OnClickListener() {
            ArrayList<Boolean> clear = new ArrayList<Boolean>();
            boolean yes = true;
            @Override
            public void onClick(View v) {
                // Display Confirmation
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
                        shoppingListAdapter.notifyDataSetChanged();
                    }
            }
        });

        return v;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.shopping_list_sort_description) {
            Toast.makeText(this.getContext(), "Description", Toast.LENGTH_SHORT).show();
            shoppingList.sort(("Description"));
            shoppingListAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.shopping_list_sort_category) {
            Toast.makeText(this.getContext(), "Category", Toast.LENGTH_SHORT).show();
            shoppingList.sort(("Category"));
            shoppingListAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.shopping_list_sort_default) {
            Toast.makeText(this.getContext(), "Default", Toast.LENGTH_SHORT).show();
            shoppingList.sort(("Default"));
            shoppingListAdapter.notifyDataSetChanged();
            return true;
        }
        else {
            return false;
        }
    }

}
