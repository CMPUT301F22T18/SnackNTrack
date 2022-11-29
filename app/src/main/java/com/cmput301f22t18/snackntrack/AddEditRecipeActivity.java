package com.cmput301f22t18.snackntrack;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;


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
    String title, category, comments, photoURL, recipeID;
    int prepTime, servings;
    ArrayList<Ingredient> ingredients;
    Recipe recipe;
    RecyclerView recyclerView;
    FrameLayout addImagePerimeter;
    ImageView recipeImage;

    ArrayList<String> categories;
    ArrayAdapter<String> categoryAdapter;

    private final String TAG = "AddEditRecipeActivity";
    boolean editing;
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

        // Get recipe from list fragment or view activity
        Intent intent = getIntent();
        recipe = (Recipe) intent.getParcelableExtra("recipe");
        recipeID = (String) intent.getSerializableExtra("recipeID");
        editing = true;

        // Create new recipe if no recipe provided
        if (recipe == null) {
            recipe = new Recipe();
            ingredients = new ArrayList<Ingredient>();
            recipe.setRecipeIngredients(ingredients);
            editing = false;
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
                /*else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "category");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("Add Category").
                            add(AddEditCustomValueFragment.class, bundle, "Add Category")
                            .commit();

                }*/ //Does not work due to deletion of AddEditCustomValue...
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
        if (editing) {
            if (recipe.getPhotoURL() != null) {
                Glide.with(this).load(photoURL).into(recipeImage);
                addImageButton.setText("Change the image");
            }
            titleEditText.setText(recipe.getTitle());
            commentsEditText.setText(recipe.getComments());
            servingsEditText.setText(Integer.toString(recipe.getServings()));
            prepTimeEditText.setText(Integer.toString(recipe.getPrepTime()));
            categorySpinner.setSelection(categoryAdapter.getPosition(recipe.getCategory()));
            ingredients = recipe.getRecipeIngredients();

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
            bundle.putParcelable("recipe", recipe);

            // The following will work for now as AddEditIngredientActivity is WIP
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("Add Ingredient To Recipe")
                    .add(R.id.fragment_container_view, AddIngredientToRecipeFragment.class, bundle)
                    .commit();
            ingredientAdapter.notifyDataSetChanged();

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
            addImageButton.setText("Change image");
        });

        addRecipeButton.setOnClickListener(v -> {
            Log.i("Debug", "Pressed add recipe!");
            addEditRecipe();
        });

    }

    /**
     * Add or edit recipe to recipe list, as long as required fields
     * are filled.
     */
    public void addEditRecipe() {
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
            recipe.setRecipeIngredients(ingredients);
            /*if (recipe != null)
                recipeList.addRecipe(recipe); // TODO: IMPORTANT: recipeList may require reference in bundle
            else
                recipeList.setRecipe(position, recipe);*/
            updateDatabase();
            finish();

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

    /**
     * Add or edit a recipe in the database
     */
    public void updateDatabase() {
        if (!editing) { // add recipe in DB
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                db.collection("recipeLists")
                        .document(user.getUid())
                        .collection("recipes")
                        .document()
                        .set(recipe)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "successfully written recipe!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "error writing recipe", e);
                            }
                        });

            }
        }//TODO: properly upload image to firebase then use firebase url instead of local
        else { // edit recipe in DB
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                CollectionReference cr = db.collection("recipeLists")
                        .document(user.getUid())
                        .collection("recipes");
                cr.document(recipeID)
                        .set(recipe)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "Document successfully edited!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error editing document", e);
                            }
                        });
            }
        }
    }
}