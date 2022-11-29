package com.cmput301f22t18.snackntrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cmput301f22t18.snackntrack.models.Recipe;

import java.io.Serializable;
import java.util.Objects;

/**
 * An activity to view the recipe
 *
 * @author Casper Nguyen
 */
public class ViewRecipeActivity extends AppCompatActivity {
    Recipe recipe;
    String recipeID;
    ImageButton backButton;
    TextView recipeTitle;
    ImageButton editButton;
    ImageView recipeImage;
    TextView recipeCategory;
    TextView recipePreptime;
    TextView recipeServings;
    ListView ingredientsView;
    TextView recipeComments;
    ImageButton deleteButton;
    ViewRecipeIngredientListAdapter ingredientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        // get the extra from RecipeListFragment
        recipe = getIntent().getParcelableExtra("recipe");
        recipeID = (String) getIntent().getSerializableExtra("recipeID");

        // find the views
        backButton = findViewById(R.id.view_recipe_back_button);
        recipeTitle = findViewById(R.id.view_recipe_recipe_title);
        editButton = findViewById(R.id.view_recipe_edit_button);
        recipeImage = findViewById(R.id.view_recipe_image_view);
        recipeCategory = findViewById(R.id.view_recipe_category);
        recipePreptime = findViewById(R.id.view_recipe_prepTime);
        recipeServings = findViewById(R.id.view_recipe_servings);
        ingredientsView = findViewById(R.id.view_recipe_ingredient_list);
        recipeComments = findViewById(R.id.view_recipe_comments);
        deleteButton = findViewById(R.id.view_recipe_delete_button);

        // fill the views
        recipeTitle.setText(recipe.getTitle());
        Glide.with(this).load(recipe.getPhotoURL()).into(recipeImage);
        recipeCategory.setText(recipe.getCategory());
        String prepTimeString = recipe.getPrepTime() + " minutes";
        recipePreptime.setText(prepTimeString);
        String servingsString = recipe.getServings() + " servings";
        recipeServings.setText(servingsString);
        ingredientListAdapter = new ViewRecipeIngredientListAdapter(this, recipe.getRecipeIngredients());
        ingredientsView.setAdapter(ingredientListAdapter);
        recipeComments.setText(recipe.getComments());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // end the activity, get people back to where they were before
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecipeActivity.this, AddEditRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", (Serializable) recipe);
                bundle.putSerializable("recipeID", (Serializable) recipeID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: lead people to a fragment asking are you sure you want to delete the recipe
                DeleteRecipeFragment dialog = new DeleteRecipeFragment(recipeID);
                dialog.show(getSupportFragmentManager(), null);
            }
        });
    }
}