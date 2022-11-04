package com.cmput301f22t18.snackntrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity implements RecipeListAdapter.OnNoteListener {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecipeList recipeList;
    RecipeListAdapter recipeListAdapter;

    // for testing
//    ArrayList<Recipe> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // for testing
//        testList = new ArrayList<>();
//        Recipe recipe = new Recipe();
//        recipe.setTitle("Milk");
//        recipe.setCategory("Liquid");
//        recipe.setServings(3);
//        recipe.setPrepTime(0);
//        testList.add(recipe);
        // end test

        fab = findViewById(R.id.recipe_list_action_button);
        recyclerView = findViewById(R.id.recipe_list);
        recipeListAdapter = new RecipeListAdapter(this, recipeList.getRecipeList(), this); //recipeList.getRecipeList()  // testList
        recyclerView.setAdapter(recipeListAdapter);

    }

    @Override
    public void onNoteClick(int position) {
        //TODO: bring us to a fragment that shows recipe info
    }
}