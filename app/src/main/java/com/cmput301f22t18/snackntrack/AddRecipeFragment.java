package com.cmput301f22t18.snackntrack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Mark Maligalig
 * @version 1
 */
public class AddRecipeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // private static final String ARG_PARAM1 = "param1";
    // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // private String mParam1;
    // private String mParam2;

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    Button addIngredientButton, addRecipeButton;
    ImageButton backButton;
    Spinner categorySpinner;
    EditText titleEditText, commentsEditText, servingsEditText, prepTimeEditText;
    String title, category, comments;
    int prepTime, servings;
    ArrayList<Ingredient> recipeIngredients = new ArrayList<>();


    private ArrayList<String> categories = new ArrayList<>();

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance() {
        AddRecipeFragment fragment = new AddRecipeFragment();
        // Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (getArguments() != null) {
        //     mParam1 = getArguments().getString(ARG_PARAM1);
        //     mParam2 = getArguments().getString(ARG_PARAM2);
        // }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit_recipe, container, false);

        // Adjust spinner
        categorySpinner = view.findViewById(R.id.category_spinner);
        categories.add("Breakfast");
        categories.add("Lunch");
        categories.add("Dinner");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton imageButton = view.findViewById(R.id.back_button);
        floatingActionButton = getActivity().findViewById(R.id.add_recipe_fab);
        recyclerView = getActivity().findViewById(R.id.recipe_list_recycler_view);
        backButton = view.findViewById(R.id.back_button);
        addIngredientButton = view.findViewById(R.id.add_ingredient_to_recipe_button);
        addRecipeButton = view.findViewById(R.id.add_recipe_button);

        titleEditText = view.findViewById(R.id.title_edit_text);
        commentsEditText = view.findViewById(R.id.comments_edit_text);
        servingsEditText = view.findViewById(R.id.servings_edit_text);
        prepTimeEditText = view.findViewById(R.id.prep_time_edit_text);
        categorySpinner = view.findViewById(R.id.category_spinner);

        addIngredientButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            //bundle.putString();
            requireActivity().
                    getSupportFragmentManager().
                    beginTransaction().
                    addToBackStack("AddIngredientToRecipe").
                    add(AddIngredientFragment.class, bundle, null).
                    commit();
        });

        backButton.setOnClickListener(v -> {
            Log.i("Debug", "Pressed Back!");
            floatingActionButton.show();
            recyclerView.setVisibility(View.VISIBLE);
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        });

        addRecipeButton.setOnClickListener(v -> {
            Log.i("Debug", "Pressed add recipe!");
            floatingActionButton.show();
            recyclerView.setVisibility(View.VISIBLE);
            addRecipe();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        });

        // Set FAB to hide
        floatingActionButton.hide();
    }

    public void addRecipe() {
        title = titleEditText.getText().toString();
        comments = commentsEditText.getText().toString();
        category = categorySpinner.getSelectedItem().toString();
        servings = Integer.parseInt(servingsEditText.getText().toString());
        prepTime = Integer.parseInt(prepTimeEditText.getText().toString());
    }
}