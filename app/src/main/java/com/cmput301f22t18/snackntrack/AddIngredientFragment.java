package com.cmput301f22t18.snackntrack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    Spinner unit, location, category;
    ImageButton pickBestBeforeButton;
    Button cancel, add;

    public AddIngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AddEditIngredient.
     */
    // TODO: Add paramete to AddIngredient to reuse as Edit Ingredient
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
        /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

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
        ImageButton imageButton = view.findViewById(R.id.back_button);
        floatingActionButton = getActivity().findViewById(R.id.add_ingredient_fab);
        location = view.findViewById(R.id.location_spinner);
        unit = view.findViewById(R.id.unit_spinner);
        category = view.findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.units_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> location_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ingredient_locations_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> category_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ingredient_categories_array, android.R.layout.simple_spinner_item);

        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unit.setAdapter(unit_adapter);
        location.setAdapter(location_adapter);
        category.setAdapter(category_adapter);

        pickBestBeforeButton = view.findViewById(R.id.date_picker_button);

        pickBestBeforeButton.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getChildFragmentManager(), "datePicker");

        });

        cancel = view.findViewById(R.id.cancel_add_ingredient_button);

        imageButton.setOnClickListener(v -> goBack());

        cancel.setOnClickListener(v -> goBack());

        // Set FAB to hide

        floatingActionButton.hide();
    }

    public void goBack() {
        Log.i("Debug", "Pressed Back!");
        floatingActionButton.show();
        requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}