package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * This class is a fragment for the recipe list.
 *
 * @author Casper Nguyen
 */
public class RecipeListFragment extends Fragment implements RecipeListAdapter.OnNoteListener, PopupMenu.OnMenuItemClickListener {
    private static final String ARG_TEXT = "recipeList";

    RecyclerView recyclerView;
    RecipeList recipeList;
    RecipeListAdapter recipeListAdapter;
    FloatingActionButton fab;
    ImageButton sortButton;
    TextView headerText;
    ArrayList<String> recipeIDs;

    public RecipeListFragment() {}

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
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // find the views for this fragment
        fab = v.findViewById(R.id.recipe_list_action_button);
        recyclerView = v.findViewById(R.id.recipe_list);
        sortButton = v.findViewById(R.id.sort_button_recipe_list);
        headerText = v.findViewById(R.id.recipe_list_header_text);

        recipeList = new RecipeList();

        // setting the list for this fragment
        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipeList.getRecipeList(), this);
        recyclerView.setAdapter(recipeListAdapter);
        setUpRecipeList("Default");

        // Controls the floating action button
        fab.setOnClickListener(view -> {
            //TODO: move to add recipe screen
            //Toast.makeText(this.getContext(), "fab clicked", Toast.LENGTH_SHORT).show();
        });

        // When clicked on the sort button, some choices are shown
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(RecipeListFragment.this.getContext(), v);
                popup.setOnMenuItemClickListener(RecipeListFragment.this);
                popup.inflate(R.menu.recipe_list_sort_menu);
                popup.show();
            }
        });

        return v;
    }

    /**
     * This method is called when the user click an item in the recipe list.
     * It moves the user to ViewRecipeActivity
     * @param position the position of the item
     */
    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
        intent.putExtra("recipe", recipeList.getRecipeList().get(position));
        intent.putExtra("recipeID", recipeIDs.get(position));
        startActivity(intent);
    }

    /**
     * This method controls what happens when we click on an item in the pop up menu
     * @param item an item in the pop up menu
     * @return true if an item is clicked, false otherwise
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.recipe_list_sort_title) {
            //Toast.makeText(this.getContext(), "Title", Toast.LENGTH_SHORT).show();
            setUpRecipeList("Title");
            return true;
        }
        else if(id == R.id.recipe_list_sort_time) {
            //Toast.makeText(this.getContext(), "Time", Toast.LENGTH_SHORT).show();
            setUpRecipeList("Time");
            return true;
        }
        else if(id == R.id.recipe_list_sort_servings) {
            //Toast.makeText(this.getContext(), "Servings", Toast.LENGTH_SHORT).show();
            setUpRecipeList("Servings");
            return true;
        }
        else if(id == R.id.recipe_list_sort_category) {
            //Toast.makeText(this.getContext(), "Category", Toast.LENGTH_SHORT).show();
            setUpRecipeList("Category");
            return true;
        }
        else if(id == R.id.recipe_list_sort_default) {
            //Toast.makeText(this.getContext(), "Default", Toast.LENGTH_SHORT).show();
            setUpRecipeList("Default");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method is used to query and update data on this fragment
     * @param option an option on the pop up menu (for sorting). Use Default case if user have not chosen an sorting option
     */
    public void setUpRecipeList(String option) {
        String mode;
        if(option.equals("Title")) {
            mode = "title";
        }
        else if(option.equals("Time")) {
            mode = "prepTime";
        }
        else if(option.equals("Servings")) {
            mode = "servings";
        }
        else if(option.equals("Category")) {
            mode = "category";
        }
        else {
            mode = "default";
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            recipeList.getRecipeList().clear();
            recipeIDs = new ArrayList<>();
            String uid = user.getUid();
            FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = fireStore.collection("recipeLists").document(uid).collection("recipes");
            Query query;
            if(!mode.equals("default")) {
                Log.d("debug", mode);
                query = collectionReference.orderBy(mode);
            }
            else {
                query = collectionReference;
            }

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            recipeList.getRecipeList().clear();
                            recipeIDs.clear();
                            if(task.isSuccessful()) {
                                for(QueryDocumentSnapshot recipeDocument : task.getResult()) {
                                    // do something
                                    Recipe recipe = recipeDocument.toObject(Recipe.class);
                                    String id = recipeDocument.getId();

                                    recipeIDs.add(id);
                                    recipeList.addRecipe(recipe);
                                    recipeListAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                Log.d("DEBUG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
                }
            });
        }
    }
}