package com.cmput301f22t18.snackntrack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This class is a fragment for the recipe list.
 *
 * @author SCWinter259
 * @see RecipeList
 * @see RecipeListAdapter
 */
public class RecipeListFragment extends Fragment implements RecipeListAdapter.OnNoteListener{
    private static final String ARG_TEXT = "recipeList";

    RecyclerView recyclerView;
    RecipeList recipeList;
    RecipeListAdapter recipeListAdapter;
    FloatingActionButton fab;
    ImageButton sortButton;

    /**
     * This method create a new instance of the RecipeListFragment
     * @param recipeList an instance of the RecipeList class
     * @return a new instance of the RecipeListFragment
     */
    public static RecipeListFragment newInstance(RecipeList recipeList) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEXT, recipeList);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This method is called when all the views are created
     * @param inflater a LayoutInflater
     * @param container a ViewGroup
     * @param savedInstanceState a Bundle
     * @return a View v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);



        // find the views for this fragment
        fab = requireView().findViewById(R.id.recipe_list_action_button);
        recyclerView = requireView().findViewById(R.id.recipe_list);
        sortButton = requireView().findViewById(R.id.sort_button_recipe_list);

        // get information for this fragment
        if(getArguments() != null) {
            recipeList = (RecipeList) getArguments().getSerializable(ARG_TEXT);
        }

        // setting the list for this fragment
        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipeList.getRecipeList(), this);
        recyclerView.setAdapter(recipeListAdapter);

        // Controls the floating action button
        fab.setOnClickListener(view -> {
            //TODO: move to add recipe screen
            Toast.makeText(this.getContext(), "fab clicked", Toast.LENGTH_SHORT).show();
        });

        // When clicked on the sort button, some choices are shown
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: let user select their way to sort the list
                Toast.makeText(v.getContext(), "sort button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    /**
     * This method is called when the user click an item in the recipe list
     * @param position the position of the item
     */
    @Override
    public void onNoteClick(int position) {
        //TODO: start an activity to view the recipe that was clicked
        Toast.makeText(this.getContext(), "item clicked", Toast.LENGTH_SHORT).show();
    }
}