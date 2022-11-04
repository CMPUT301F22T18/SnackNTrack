package com.cmput301f22t18.snackntrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This class is a custom adapter for the recipe list.
 * Attributes:
 * recipeArrayList {@link ArrayList<Recipe>}
 * context {@link Context}
 *
 * @author SCWinter259
 * @version 1.0.0
 * @see RecyclerView
 * @see Recipe
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private ArrayList<Recipe> recipeArrayList;
    private Context context;
    private OnNoteListener myOnNoteListener;

    /**
     * This is the constructor for the class {@link RecipeListAdapter}
     * @param context provides the context
     * @param recipeArrayList an ArrayList of Recipes
     * @param myOnNoteListener an OnNoteListener object
     * @since 1.0.0
     */
    public RecipeListAdapter(Context context, ArrayList<Recipe> recipeArrayList, OnNoteListener myOnNoteListener) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
        this.myOnNoteListener = myOnNoteListener;
    }

    /**
     * This class provides a reference to the type of views that you are using. It is a custom {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
     * @since 1.0.0
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
         * @since 1.0.0
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
         * @since 1.0.0
         */
        @Override
        public void onClick(View v) {
            this.onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    /**
     * This method is invoked by the layout manager. It creates new views, each of which
     * defines the UI of the list item
     * @param parent the parent ViewGroup
     * @param viewType a view type
     * @return a ViewHolder object for each item view
     * @since 1.0.0
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
     * @since 1.0.0
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipeArrayList.get(position);
        holder.recipeTitle.setText(recipe.getTitle());
        holder.recipeCategory.setText(recipe.getCategory());
        // TODO: Change prep time representation of Recipe class, for now we'll assume prep time is in minutes
        String prepTimeString = recipe.getPrepTime() + " mins";
        holder.recipePrepTime.setText(prepTimeString);
        holder.recipeServings.setText(String.valueOf(recipe.getServings()));
        //TODO: bind image (requires that the recipe has to have an image)
    }

    /**
     * This method returns the size of the list
     * @return the size of recipeArrayList
     * @since 1.0.0
     */
    @Override
    public int getItemCount() {
        return this.recipeArrayList.size();
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}