package com.cmput301f22t18.snackntrack;


import android.app.Activity;
import android.content.Context;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddIngredientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // private static final String ARG_PARAM1 = "param1";
    // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // private String mParam1;
    // private String mParam2;

    FloatingActionButton floatingActionButton;
    Spinner unitSpinner, locationSpinner, categorySpinner;
    ImageButton pickBestBeforeButton;
    Button cancelButton, addButton;

    String description, location, unit, category;
    Date bestBefore;
    int amount;
    Storage storage;

    EditText descriptionEditText, amountEditText, bestBeforeEditText;

    public AddIngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AddEditIngredient.
     */
    // TODO: Add parameter to AddIngredient to reuse as Edit Ingredient
    public static AddIngredientFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return new AddIngredientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        storage = (Storage) getArguments().getSerializable("storage");
        Log.d("DEBUG", storage.getStorage().get(0).toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
        initSpinner(view);
        ImageButton imageButton = view.findViewById(R.id.back_button);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        amountEditText = view.findViewById(R.id.amount_edit_text);
        bestBeforeEditText = view.findViewById(R.id.best_before_date);

        floatingActionButton = getActivity().findViewById(R.id.add_ingredient_fab);
        pickBestBeforeButton = view.findViewById(R.id.date_picker_button);
        pickBestBeforeButton.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getChildFragmentManager(), "datePicker");

        });

        cancelButton = view.findViewById(R.id.cancel_add_ingredient_button);
        addButton = view.findViewById(R.id.add_ingredient_button);

        imageButton.setOnClickListener(v -> goBack());

        cancelButton.setOnClickListener(v -> goBack());
        addButton.setOnClickListener(v -> addIngredient());

        // Set FAB to hide
        floatingActionButton.hide();


    }

    public static void hideSoftKeyboard(Activity activity) {
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
                    hideSoftKeyboard(getActivity());
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

    public void onItemSelectedLocation(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        location = (String) parent.getItemAtPosition(pos);
    }

    public void onItemSelectedUnit(AdapterView<?> parent, View view,
                                       int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        unit = (String) parent.getItemAtPosition(pos);
    }

    public void onItemSelectedCategory(AdapterView<?> parent, View view,
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
        Log.d("DEBUG", location);
        Log.d("DEBUG", unit);
        Ingredient ingredient = new Ingredient(description, location,
                unit, category, amount, bestBefore);
        Log.d("DEBUG", ingredient.toString());
        storage.addIngredient(ingredient);
        Log.i("DEBUG", "Added Ingredient!");
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
        if (Objects.equals(amountEditText.getText().toString(), "")) return false;
        return true;
    }

    public void initSpinner(View view) {
        locationSpinner = view.findViewById(R.id.location_spinner);
        unitSpinner = view.findViewById(R.id.unit_spinner);
        categorySpinner = view.findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.units_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> location_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ingredient_locations_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> category_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ingredient_categories_array, android.R.layout.simple_spinner_item);

        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitSpinner.setAdapter(unit_adapter);
        locationSpinner.setAdapter(location_adapter);
        categorySpinner.setAdapter(category_adapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedLocation(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedUnit(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedCategory(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
