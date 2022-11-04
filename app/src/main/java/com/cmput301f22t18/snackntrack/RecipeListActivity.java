package com.cmput301f22t18.snackntrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author Mark Maligalig
 */
public class RecipeListActivity extends AppCompatActivity {
    private RecipeList recipeList;
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);


        recipeList = new RecipeList();
        insertTestRecipes();
        recipeListAdapter = new RecipeListAdapter(this, recipeList.getRecipeList(), null);
        recyclerView = this.findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setAdapter(recipeListAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

        fab = findViewById(R.id.add_recipe_fab);
        fab.show();
        fab.setOnClickListener(view -> {
            if (savedInstanceState == null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipeList", (Serializable) recipeList);

                recyclerView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, AddRecipeFragment.class, bundle)
                        .addToBackStack("AddRecipe")
                        .commit();

            }
        });


    }

    private void insertTestRecipes() {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        in1.add(new Ingredient("Bread", "pieces", "Wheat", 2));
        recipeList.addRecipe(new Recipe("Sandwich", 5, "none", 1, "Lunch", in1, null));

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("A", "pieces", "C", 2));
        recipeList.addRecipe(new Recipe("Soup", 10, "none", 2, "Dinner", in2, null));

    }
    
    // TOOD: Add onNoteClick, OnItemClickListener, etc to edit recipe...
}