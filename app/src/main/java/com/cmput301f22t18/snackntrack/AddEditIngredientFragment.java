package com.cmput301f22t18.snackntrack;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Contract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddEditIngredientFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    Spinner unitSpinner, locationSpinner, categorySpinner;
    ImageButton pickBestBeforeButton;
    Button cancelButton, addButton;

    String description, location, unit, category, function;
    Date bestBefore;
    int amount;
    int index;
    Storage storage;
    Ingredient item;

    EditText descriptionEditText, amountEditText, bestBeforeEditText;

    ArrayList<String> units, locations, categories;

    ArrayAdapter<String> unit_adapter, location_adapter, category_adapter;

    public AddEditIngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AddEditIngredient.
     */
    // TODO: Add parameter to AddIngredient to reuse as Edit Ingredient
    @NonNull
    @Contract(" -> new")
    public static AddEditIngredientFragment newInstance() {
        return new AddEditIngredientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        storage = (Storage) getArguments().getSerializable("storage");
        item = (Ingredient) getArguments().getSerializable("item");
        index = getArguments().getInt("position");
        if (item != null)
            Log.d("DEBUG", item.toString());
        units = getArguments().getStringArrayList("units");
        locations = getArguments().getStringArrayList("locations");
        categories = getArguments().getStringArrayList("categories");
        function = getArguments().getString("function");
        Log.d("DEBUG", storage.getStorageList().get(0).toString());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("location", this,
                (requestKey, bundle) -> {
            String result = bundle.getString("new_item");
            locations.add(locations.size() - 1, result);
            location_adapter.notifyDataSetChanged();
        });

        getParentFragmentManager().setFragmentResultListener("unit", this,
                (requestKey, bundle) -> {
            String result = bundle.getString("new_item");
            units.add(units.size() - 1, result);
            unit_adapter.notifyDataSetChanged();
        });

        getParentFragmentManager().setFragmentResultListener("category", this,
                (requestKey, bundle) -> {
            String result = bundle.getString("new_item");
            categories.add(categories.size() - 1,result);
            category_adapter.notifyDataSetChanged();
        });

        setupUI(view);
        locationSpinner = view.findViewById(R.id.location_spinner);
        unitSpinner = view.findViewById(R.id.unit_spinner);
        categorySpinner = view.findViewById(R.id.category_spinner);

        initSpinner(view);

        ImageButton imageButton = view.findViewById(R.id.back_button);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        amountEditText = view.findViewById(R.id.amount_edit_text);
        bestBeforeEditText = view.findViewById(R.id.best_before_date);

        if (item != null) {
            descriptionEditText.setText(item.getDescription());
            amountEditText.setText(String.format(Locale.CANADA, "%d", item.getAmount()));
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.CANADA);
            bestBeforeEditText.setText(df.format(item.getBestBeforeDate()));
            locationSpinner.setSelection(location_adapter.getPosition(item.getLocation()));
            unitSpinner.setSelection(unit_adapter.getPosition(item.getUnit()));
            categorySpinner.setSelection(category_adapter.getPosition(item.getCategory()));
        }

        floatingActionButton = requireActivity().findViewById(R.id.add_ingredient_fab);
        pickBestBeforeButton = view.findViewById(R.id.date_picker_button);
        pickBestBeforeButton.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getChildFragmentManager(), "datePicker");

        });

        cancelButton = view.findViewById(R.id.cancel_add_ingredient_button);
        addButton = view.findViewById(R.id.add_ingredient_button);
        if (function != null) {
            addButton.setText(function);
            TextView title = view.findViewById(R.id.add_ingredient_fragment_title_text_view);
            String newTitle = function + " Ingredient";
            title.setText(newTitle);
        }
        imageButton.setOnClickListener(v -> goBack());

        cancelButton.setOnClickListener(v -> goBack());
        addButton.setOnClickListener(v -> addIngredient());

        // Set FAB to hide
        floatingActionButton.hide();


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

    public void onItemSelectedLocation(@NonNull AdapterView<?> parent, View view,
                                       int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        location = (String) parent.getItemAtPosition(pos);
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
            Log.i("DEBUG", "Cannot add empty!");
            return;
        }
        description = descriptionEditText.getText().toString();
        amount = Integer.parseInt(amountEditText.getText().toString());
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
            bestBefore = df.parse(bestBeforeEditText.getText().toString());

        }
        catch (Exception e) {
            Log.d("Debug", bestBeforeEditText.getText().toString());
            Log.d("DEBUG", "Cannot parse");
            return;
        }
        location = locationSpinner.getSelectedItem().toString();
        unit = unitSpinner.getSelectedItem().toString();
        category = categorySpinner.getSelectedItem().toString();
        Log.d("DEBUG", location);
        Log.d("DEBUG", unit);
        Ingredient ingredient = new Ingredient(description, location,
                unit, category, amount, bestBefore);
        Log.d("DEBUG", ingredient.toString());
        if (item == null) {
            storage.addIngredient(ingredient);
            Log.i("DEBUG", "Added Ingredient!");
        }
        else {
            Bundle result = new Bundle();
            result.putSerializable("new_item", ingredient);
            requireActivity().getSupportFragmentManager()
                    .setFragmentResult("editIngredient", result);
            Log.i("DEBUG", "Edited Ingredient!");
        }
        floatingActionButton.show();
        requireActivity().getSupportFragmentManager().popBackStackImmediate();

    }

    public void goBack() {
        Log.i("Debug", "Pressed Back!");
        floatingActionButton.show();
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
        location_adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, locations);
        category_adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitSpinner.setAdapter(unit_adapter);
        locationSpinner.setAdapter(location_adapter);
        categorySpinner.setAdapter(category_adapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != location_adapter.getCount() - 1)
                    onItemSelectedLocation(parent, view, position, id);
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "location");
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("Add Location").
                            add(AddEditCustomValueFragment.class, bundle, "Add Location")
                            .commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != unit_adapter.getCount() - 1)
                    onItemSelectedUnit(parent, view, position, id);
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "unit");
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction().addToBackStack("Add Unit").
                            add(AddEditCustomValueFragment.class, bundle, "Add Unit")
                            .commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != category_adapter.getCount() - 1)
                    onItemSelectedCategory(parent, view, position, id);
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "category");
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("Add Category").
                            add(AddEditCustomValueFragment.class, bundle, "Add Category")
                            .commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
