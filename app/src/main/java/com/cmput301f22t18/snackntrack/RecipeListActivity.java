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
    ArrayList<Recipe> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // for testing
        testList = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setTitle("Milk");
        testList.add(recipe);
        // end test

        fab = findViewById(R.id.recipe_list_action_button);
        recyclerView = findViewById(R.id.recipe_list);
        recipeListAdapter = new RecipeListAdapter(this, recipeList.getRecipeList(), this);
        recyclerView.setAdapter(recipeListAdapter);

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                // implement this if we want to swipe the item
//            }
//        });
//
//        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onNoteClick(int position) {
        Toast toast = Toast.makeText(getApplicationContext(), "This is a toast", Toast.LENGTH_SHORT);
        toast.show();
    }
}