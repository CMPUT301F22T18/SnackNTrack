package com.cmput301f22t18.snackntrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {
    private RecipeList recipeList;
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeList = new RecipeList();
        insertTestRecipes();
        recipeListAdapter = new RecipeListAdapter(recipeList);
        recyclerView = this.findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setAdapter(recipeListAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

        FloatingActionButton fab = findViewById(R.id.add_recipe_fab);
        fab.show();
        fab.setOnClickListener(view -> {
            if (savedInstanceState == null) {
                recyclerView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, AddRecipeFragment.class, null)
                        .addToBackStack("AddRecipe")
                        .commit();

            }
        });


    }

    private void insertTestRecipes() {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        in1.add(new Ingredient("Bread", "pieces", "Wheat", 2));
        recipeList.addRecipe(new Recipe("Sandwich", 5, "no comment", 1, "Lunch", in1));

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("A", "pieces", "C", 2));
        recipeList.addRecipe(new Recipe("Soup", 20, "no comment", 1, "Dinner", in2));
    }
}