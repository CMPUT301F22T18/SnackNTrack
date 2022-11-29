package com.cmput301f22t18.snackntrack;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIngredientToRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIngredientToRecipeFragment extends Fragment {

    Spinner unitSpinner, categorySpinner;
    Button cancelButton, addButton;
    ImageButton deleteButton;

    String description, unit, category;
    int amount;
    Recipe recipe;
    Ingredient ingredient;

    EditText descriptionEditText, amountEditText;

    ArrayList<CharSequence> units;
    ArrayList<CharSequence> categories;

    ArrayAdapter<CharSequence> unit_adapter, category_adapter;

    public AddIngredientToRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AddIngredientToRecipe.
     */
    // TODO: Add parameter to AddIngredient to reuse as Edit Ingredient
    @NonNull
    @Contract(" -> new")
    public static AddIngredientToRecipeFragment newInstance() {
        return new AddIngredientToRecipeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        recipe = (Recipe) getArguments().getParcelable("recipe");
        ingredient = (Ingredient) getArguments().getParcelable("ingredient");
        Log.d("DEBUG", "created");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_ingredient_to_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("unit", this,
                (requestKey, bundle) -> {
                    // We use a String here, but any type that can be put in a Bundle is supported
                    String result = bundle.getString("new_item");
                    unit_adapter.insert(result, unit_adapter.getCount() - 1);
                });

        getParentFragmentManager().setFragmentResultListener("category", this,
                (requestKey, bundle) -> {
                    // We use a String here, but any type that can be put in a Bundle is supported
                    String result = bundle.getString("new_item");
                    category_adapter.insert(result, category_adapter.getCount() - 1);
                });

        units = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        categories = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));

        setupUI(view);
        unitSpinner = view.findViewById(R.id.unit_spinner);
        categorySpinner = view.findViewById(R.id.category_spinner);

        initSpinner(view);

        ImageButton imageButton = view.findViewById(R.id.back_button);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        amountEditText = view.findViewById(R.id.amount_edit_text);

        cancelButton = view.findViewById(R.id.cancel_add_ingredient_button);
        addButton = view.findViewById(R.id.add_ingredient_button);
        deleteButton = view.findViewById(R.id.recipe_ingredient_delete_button);

        if (ingredient != null) {
            descriptionEditText.setText(ingredient.getDescription());
            amountEditText.setText(Integer.toString(ingredient.getAmount()));
            categorySpinner.setSelection(category_adapter.getPosition(ingredient.getCategory()));
            unitSpinner.setSelection(unit_adapter.getPosition(ingredient.getCategory()));
        }

        imageButton.setOnClickListener(v -> goBack());

        cancelButton.setOnClickListener(v -> goBack());
        addButton.setOnClickListener(v -> addIngredient());
        deleteButton.setOnClickListener(v -> {
            recipe.deleteIngredient(ingredient);
            goBack();
        });


    }

    public static void hideSoftKeyboard(@NonNull Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(requireActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void onItemSelectedUnit(@NonNull AdapterView<?> parent, View view,
                                   int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        unit = (String) parent.getItemAtPosition(pos);
    }

    public void onItemSelectedCategory(@NonNull AdapterView<?> parent, View view,
                                       int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        category = (String) parent.getItemAtPosition(pos);
    }

    public void addIngredient(){
        if (!inputValidate()) {
            Toast.makeText(getActivity(), "Fields must not be empty!", Toast.LENGTH_LONG).show();
            Log.i("DEBUG", "Cannot add empty!");
            return;
        }
        description = descriptionEditText.getText().toString();
        amount = Integer.parseInt(amountEditText.getText().toString());
        unit = unitSpinner.getSelectedItem().toString();
        category = categorySpinner.getSelectedItem().toString();
        Log.d("DEBUG", unit);
        Ingredient ingredient = new Ingredient(description, unit, category, amount);
        Log.d("DEBUG", ingredient.toString());
        recipe.addIngredient(ingredient);
        Log.i("DEBUG", "Added Ingredient to Recipe!");
        requireActivity().getSupportFragmentManager().popBackStackImmediate();

    }

    public void goBack() {
        Log.i("Debug", "Pressed Back!");
        requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    public boolean inputValidate() {
        if (Objects.equals(descriptionEditText.getText().toString(), "")) return false;
        if (Objects.equals(unit, "")) return false;
        if (Objects.equals(category, "")) return false;
        return !Objects.equals(amountEditText.getText().toString(), "");
    }

    public void initSpinner(View view) {




        unit_adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, units);
        category_adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitSpinner.setAdapter(unit_adapter);
        categorySpinner.setAdapter(category_adapter);
    }
}