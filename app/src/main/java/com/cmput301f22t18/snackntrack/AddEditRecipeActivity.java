package com.cmput301f22t18.snackntrack;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.models.Storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Activity for the creation or editing of a Recipe object
 * @author Mark Maligalig
 */

public class AddEditRecipeActivity extends AppCompatActivity {

    // TODO: Add javadocs!!

    Button addIngredientButton, addRecipeButton, addImageButton;
    ImageButton backButton;
    Spinner categorySpinner;
    EditText titleEditText, commentsEditText, servingsEditText, prepTimeEditText;
    String title, category, comments, photoURL;
    int prepTime, servings;
    ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
    RecipeListAdapter recipeListAdapter;
    RecipeList recipeList;
    Recipe recipe;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_recipe);

        recipe = new Recipe(); // TODO: change when editing recipe

        // Get RecipeList from fragment
        Intent intent = getIntent();
        recipeList = (RecipeList) intent.getSerializableExtra("RECIPE_LIST");

        // Check if RECIPE_POS was provided
        int position = intent.getIntExtra("RECIPE_POSITION", -1);

        String mode = (String) intent.getSerializableExtra("MODE");

        // Adjust spinner
        ArrayList<String> categories = new ArrayList<>(); // TODO: move to @strings
        categorySpinner = this.findViewById(R.id.category_spinner);
        categories.add("Breakfast");
        categories.add("Lunch");
        categories.add("Dinner");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        // onViewCreated //
        backButton = this.findViewById(R.id.back_button);
        addIngredientButton = this.findViewById(R.id.add_ingredient_to_recipe_button);
        addRecipeButton = this.findViewById(R.id.add_recipe_button);
        addImageButton = this.findViewById(R.id.add_image_button);

        titleEditText = this.findViewById(R.id.title_edit_text);
        commentsEditText = this.findViewById(R.id.comments_edit_text);
        servingsEditText = this.findViewById(R.id.servings_edit_text);
        prepTimeEditText = this.findViewById(R.id.prep_time_edit_text);
        categorySpinner = this.findViewById(R.id.category_spinner);

        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();

        // If editing, set applicable inputs to match selected recipe
        if (position >= 0) {
            recipe = recipeList.getRecipeList().get(position);
            titleEditText.setText(recipe.getTitle());
            commentsEditText.setText(recipe.getComments());
            servingsEditText.setText(Integer.toString(recipe.getServings()));
            prepTimeEditText.setText(Integer.toString(recipe.getPrepTime()));
            //categorySpinner.setSelection();
            addRecipeButton.setText(mode);
        }

        // For ingredients RecyclerView
        StorageAdapter ingredientAdapter;
        Storage ingredients = new Storage();
        ArrayList unit_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        ArrayList location_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_locations_array)));
        ArrayList category_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));
        ingredientAdapter = new StorageAdapter(ingredients, getSupportFragmentManager(), this, unit_list, location_list, category_list);
        recyclerView = this.findViewById(R.id.recipe_ingredient_recycler_view);
        recyclerView.setAdapter(ingredientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Button operations

        addIngredientButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", (Serializable) recipe);

            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("Add Ingredient To Recipe")
                    .add(R.id.fragment_container_view, AddIngredientToRecipeFragment.class, bundle)
                    .commit();
        });

        backButton.setOnClickListener(v -> {
            Log.i("Debug", "Pressed Back!");
            onBackPressed();
        });

        addImageButton.setOnClickListener(v -> {
            openFileDialog(v);
        });

        addRecipeButton.setOnClickListener(v -> {
            Log.i("Debug", "Pressed add recipe!");
            addRecipe();
            finish(); //onBackPressed(); //finish();
        });

    }
    public void addRecipe() {
        if (!validateInput()) {
            Toast.makeText(this, "Fields cannot be empty (including image)!", Toast.LENGTH_LONG).show();
        }
        else {
            title = titleEditText.getText().toString();
            comments = commentsEditText.getText().toString();
            category = categorySpinner.getSelectedItem().toString();
            servings = Integer.parseInt(servingsEditText.getText().toString());
            prepTime = Integer.parseInt(prepTimeEditText.getText().toString());
            // Note photoURL is set in sActivityResultLauncher
            recipe.setTitle(title);
            recipe.setComments(comments);
            recipe.setCategory(category);
            recipe.setServings(servings);
            recipe.setPrepTime(prepTime);
            recipe.setPhotoURL(photoURL);
            recipeList.addRecipe(recipe);
            Log.d("PATH", photoURL);
            // TODO: Change when user edits
            Log.d("TEST NAME", recipeList.getRecipeList().get(0).getTitle());

        }
    }

    public boolean validateInput() {
        boolean allFieldsFilled = true;
        if (titleEditText.getText().toString().equals("") ||
                servingsEditText.getText().toString().equals("") ||
                commentsEditText.getText().toString().equals("") ||
                servingsEditText.getText().toString().equals("") ||
                prepTimeEditText.getText().toString().equals("") ||
                photoURL.equals("")) {
            allFieldsFilled = false;
        }
        return allFieldsFilled;
    }

    // Adapted from https://www.youtube.com/watch?v=4EKlAvjY74U to allow file chooser

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