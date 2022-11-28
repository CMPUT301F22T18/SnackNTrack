package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This class is a fragment for the recipe list.
 *
 * @author SCWinter259
 */
public class RecipeListFragment extends Fragment implements RecipeListAdapter.OnNoteListener, PopupMenu.OnMenuItemClickListener {
    private static final String ARG_TEXT = "recipeList";

    RecyclerView recyclerView;
    RecipeList recipeList;
    RecipeListAdapter recipeListAdapter;
    FloatingActionButton fab;
    ImageButton sortButton;
    TextView headerText;

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
        fab = v.findViewById(R.id.recipe_list_action_button);
        recyclerView = v.findViewById(R.id.recipe_list);
        sortButton = v.findViewById(R.id.sort_button_recipe_list);
        headerText = v.findViewById(R.id.recipe_list_header_text);

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
            //Toast.makeText(this.getContext(), "fab clicked", Toast.LENGTH_SHORT).show();
        });

        // When clicked on the sort button, some choices are shown
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: let user select their way to sort the list
                PopupMenu popup = new PopupMenu(RecipeListFragment.this.getContext(), v);
                popup.setOnMenuItemClickListener(RecipeListFragment.this);
                popup.inflate(R.menu.recipe_list_sort_menu);
                popup.show();
            }
        });

        return v;
    }

    /**
     * This method is called when the user click an item in the recipe list.
     * It moves the user to ViewRecipeActivity
     * @param position the position of the item
     */
    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
        intent.putExtra("recipe", recipeList.getRecipeList().get(position));
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.recipe_list_sort_title) {
            Toast.makeText(this.getContext(), "Title", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.recipe_list_sort_time) {
            Toast.makeText(this.getContext(), "Time", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.recipe_list_sort_servings) {
            Toast.makeText(this.getContext(), "Servings", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.recipe_list_sort_category) {
            Toast.makeText(this.getContext(), "Category", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.recipe_list_sort_default) {
            Toast.makeText(this.getContext(), "Default", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            return false;
        }
    }
}