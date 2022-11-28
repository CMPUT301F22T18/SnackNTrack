package com.cmput301f22t18.snackntrack;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.views.storage.AddEditCustomValueFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Activity for the creation or editing of a Recipe object
 * @author Mark Maligalig
 */

public class AddEditRecipeActivity extends AppCompatActivity {

    Button addIngredientButton, addImageButton;
    ImageButton backButton, addRecipeButton;
    Spinner categorySpinner;
    EditText titleEditText, commentsEditText, servingsEditText, prepTimeEditText;
    String title, category, comments, photoURL;
    int prepTime, servings, position;
    ArrayList<Ingredient> ingredients;
    RecipeList recipeList;
    Recipe recipe;
    RecyclerView recyclerView;
    FrameLayout addImagePerimeter;
    ImageView recipeImage;

    ArrayList<String> categories;
    ArrayAdapter<String> categoryAdapter;

    /**
     * Create the Activity
     * @param savedInstanceState most recent state data
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_recipe);

        backButton = this.findViewById(R.id.back_button);
        addIngredientButton = this.findViewById(R.id.add_ingredient_to_recipe_button);
        addRecipeButton = this.findViewById(R.id.add_recipe_button);
        addImagePerimeter = this.findViewById(R.id.add_edit_image_row);
        addImageButton = this.findViewById(R.id.add_image_button);
        recipeImage = this.findViewById(R.id.view_recipe_image_view);

        titleEditText = this.findViewById(R.id.title_edit_text);
        commentsEditText = this.findViewById(R.id.comments_edit_text);
        servingsEditText = this.findViewById(R.id.servings_edit_text);
        prepTimeEditText = this.findViewById(R.id.prep_time_edit_text);
        categorySpinner = this.findViewById(R.id.category_spinner);

        // Get RecipeList from fragment
        Intent intent = getIntent();
        recipeList = (RecipeList) intent.getSerializableExtra("RECIPE_LIST");

        // Check if RECIPE_POSITION was provided; i.e. check if add/edit mode
        position = intent.getIntExtra("RECIPE_POSITION", -1);
        if (position >= 0) {
            recipe = recipeList.getRecipeList().get(position);
        }
        else {
            // Create new recipe object if not editing recipe
            recipe = new Recipe();
            ingredients = new ArrayList<Ingredient>();
            recipe.setRecipeIngredients(ingredients);
        }

        // Adjust spinner
        categories = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.recipe_categories_array)));
        categorySpinner = this.findViewById(R.id.category_spinner);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != categoryAdapter.getCount() - 1)
                    category = (String) parent.getItemAtPosition(position).toString();
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "category");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("Add Category").
                            add(AddEditCustomValueFragment.class, bundle, "Add Category")
                            .commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        // TODO: Set category to new category made from custom fragment

        // If editing, set applicable inputs to match selected recipe
        if (position != -1) {
            if (recipe.getPhotoURL() != null) {
                Glide.with(this).load(photoURL).into(recipeImage);
                addImageButton.setText("Change the image");
            }
            titleEditText.setText(recipe.getTitle());
            commentsEditText.setText(recipe.getComments());
            servingsEditText.setText(Integer.toString(recipe.getServings()));
            prepTimeEditText.setText(Integer.toString(recipe.getPrepTime()));
            categorySpinner.setSelection(categoryAdapter.getPosition(recipe.getCategory()));

        }

        // For ingredients RecyclerView
        RecipeIngredientAdapter ingredientAdapter;
        ArrayList unit_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        ArrayList category_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.recipe_categories_array)));
        ingredientAdapter = new RecipeIngredientAdapter(recipe, getSupportFragmentManager(), this, unit_list, category_list);
        recyclerView = this.findViewById(R.id.recipe_ingredient_recycler_view);
        recyclerView.setAdapter(ingredientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Button operations
        addIngredientButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", (Serializable) recipe);

            // In testing, uncomment below to use mock fragment
            /*getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("Add Ingredient To Recipe")
                    .add(R.id.fragment_container_view, MockAddIngredientToRecipeFragment.class, bundle)
                    .commit();
            ingredientAdapter.notifyDataSetChanged();*/

            // TODO: Send Intent to AddEditIngredientActivity; below can be built upon
            //Intent ingredientIntent = new Intent(this, AddEditIngredientActivity.class);
            //ingredientIntent.putExtra("RECIPE", (Serializable) recipe);
            //startActivity(ingredientIntent);
            //ingredientAdapter.notifyDataSetChanged();

        });

        backButton.setOnClickListener(v -> {
            Log.i("Debug", "Pressed Back!");
            onBackPressed();
        });

        addImagePerimeter.setOnClickListener(v -> {
            addImageButton.performClick();
        });

        addImageButton.setOnClickListener(v -> {
            openFileDialog(v);
            addImageButton.setText("Change the image");
        });

        addRecipeButton.setOnClickListener(v -> {
            Log.i("Debug", "Pressed add recipe!");
            addRecipe();
            finish();
        });

    }

    /**
     * Add recipe to recipe list, as long as required fields
     * are filled.
     */
    public void addRecipe() {
        if (!validateInput()) {
            Toast.makeText(this, "Recipe requires title, prep time, servings, category!", Toast.LENGTH_LONG).show();
        }
        else {
            title = titleEditText.getText().toString();
            comments = commentsEditText.getText().toString();
            category = categorySpinner.getSelectedItem().toString();
            servings = Integer.parseInt(servingsEditText.getText().toString());
            prepTime = Integer.parseInt(prepTimeEditText.getText().toString());
            recipe.setTitle(title);
            recipe.setComments(comments);
            recipe.setCategory(category);
            recipe.setServings(servings);
            recipe.setPrepTime(prepTime);
            recipe.setPhotoURL(photoURL);
            recipeList.addRecipe(recipe);
            Log.d("TEST NAME", recipeList.getRecipeList().get(recipeList.getRecipeList().size()-1).getTitle());

        }
    }

    /**
     * Ensures user inputted at least the following fields:
     * Title, preparation time, # of servings, category
     * @return whether respective fields were completed
     */
    public boolean validateInput() {
        boolean allFieldsFilled = true;
        if (titleEditText.getText().toString().equals("") ||
                servingsEditText.getText().toString().equals("") ||
                prepTimeEditText.getText().toString().equals("") ||
                categorySpinner.getSelectedItem().toString().equals("")) {
            allFieldsFilled = false;
        }
        return allFieldsFilled;
    }

    /**
     * Adapted from https://www.youtube.com/watch?v=4EKlAvjY74U
     * to make file chooser (sActivityResultLauncher, openFileDialog)
     * to enable user to choose an image
     */

    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        photoURL = (String) uri.toString();
                    }
                }
            }
    );

    public void openFileDialog(View view) {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("image/*");
        data = Intent.createChooser(data, "Choose an image");
        sActivityResultLauncher.launch(data);
    }
}