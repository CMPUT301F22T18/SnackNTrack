package com.cmput301f22t18.snackntrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Recipe;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * This class is a custom adapter for the recipe list.
<<<<<<< HEAD
 * Attributes:
 * recipeArrayList {@link ArrayList< Recipe >}
 * context {@link Context}
=======
 * Attributes: recipeArrayList, context, myOnNoteListener
>>>>>>> 88c2856 (fixed UI for recipeList cards, fixed Recipe empty constructor, added Glide library (not sure about AppGlideModule), trying to process image within RecipeListAdapter)
 *
 * @author Casper Nguyen
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private final ArrayList<Recipe> recipeArrayList;
    private final Context context;
    private final OnNoteListener myOnNoteListener;

    /**
     * This is the constructor for the class RecipeListAdapter
     * @param context provides the context
     * @param recipeArrayList an ArrayList of Recipes
     * @param myOnNoteListener an OnNoteListener object
     */
    public RecipeListAdapter(Context context, ArrayList<Recipe> recipeArrayList, OnNoteListener myOnNoteListener) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
        this.myOnNoteListener = myOnNoteListener;
    }

    /**
     * This class provides a reference to the type of views that you are using.
     * It is a custom ViewHolder for RecyclerView, a nested class in RecipeListAdapter.
     * @author Casper Nguyen
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView recipeTitle;
        private final TextView recipeCategory;
        private final TextView recipePrepTime;
        private final TextView recipeServings;
        private final ImageView recipeImage;
        OnNoteListener onNoteListener;

        /**
         * This method is the constructor for the ViewHolder of RecipeListAdapter
         * @param itemView a particular item view
         * @param onNoteListener an OnNoteListener object
         */
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            recipeTitle = (TextView) itemView.findViewById(R.id.recipe_title_text_view);
            recipeCategory = (TextView) itemView.findViewById(R.id.recipe_category_text_view);
            recipePrepTime = (TextView) itemView.findViewById(R.id.recipe_prep_time_text_view);
            recipeServings = (TextView) itemView.findViewById(R.id.recipe_servings_text_view);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image_view);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        /**
         * This method gets the position of the item if the item is clicked
         * @param v the item view
         */
        @Override
        public void onClick(View v) {
            this.onNoteListener.onNoteClick(getAbsoluteAdapterPosition());
        }
    }

    /**
     * This method is invoked by the layout manager. It creates new views, each of which
     * defines the UI of the list item
     * @param parent the parent ViewGroup
     * @param viewType a view type
     * @return a ViewHolder object for each item view
     */
    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recipe_list, parent, false);

        return new ViewHolder(view, this.myOnNoteListener);
    }

    /**
     * This method is invoked by the layout manager. It replaces the contents of a view.
     * @param holder a ViewHolder object for RecipeListAdapter
     * @param position indicates the position of the view in the layout
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipeArrayList.get(position);
        String prepTimeString = recipe.getPrepTime() + " minutes";
        String recipeServingsString = recipe.getServings() + " servings";
        String recipePhotoURL = recipe.getPhotoURL();
        holder.recipeTitle.setText(recipe.getTitle());
        holder.recipeCategory.setText(recipe.getCategory());
        holder.recipePrepTime.setText(prepTimeString);
        holder.recipeServings.setText(recipeServingsString);
        Glide.with(this.context).load(recipePhotoURL).into(holder.recipeImage);
    }

    /**
     * This method returns the size of the list
     * @return the size of recipeArrayList
     */
    @Override
    public int getItemCount() {
        return this.recipeArrayList.size();
    }

    /**
     * This is an interface nested in RecipeListAdapter
     */
    public interface OnNoteListener {
        void onNoteClick(int position);
    }

}

