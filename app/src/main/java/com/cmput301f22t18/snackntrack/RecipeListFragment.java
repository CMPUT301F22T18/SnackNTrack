package com.cmput301f22t18.snackntrack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        fab = requireView().findViewById(R.id.recipe_list_action_button);
        recyclerView = requireView().findViewById(R.id.recipe_list);

        if(getArguments() != null) {
            recipeList = (RecipeList) getArguments().getSerializable(ARG_TEXT);
        }

        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipeList.getRecipeList(), this);
        recyclerView.setAdapter(recipeListAdapter);

        return v;
    }

    /**
     *
     * @param position
     */
    @Override
    public void onNoteClick(int position) {
        //TODO: start an activity to view the recipe that was clicked
    }
}