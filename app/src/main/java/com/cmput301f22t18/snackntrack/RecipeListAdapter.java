package com.cmput301f22t18.snackntrack;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    /**
     *
     */
    private ArrayList<Recipe> recipeListArray;

    /**
     *
     * @param recipeList
     */
    public RecipeListAdapter(RecipeList recipeList) {
        this.recipeListArray = recipeList.getRecipeList();
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_row_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleTextView().setText(recipeListArray.get(position).getTitle());
        holder.getCategoryTextView().setText(recipeListArray.get(position).getCategory());

        // TODO: Change prep time representation of Recipe class, for now we'll assume prep time is in minutes
        String prepTimeString = recipeListArray.get(position).getPrepTime() + " minutes";
        holder.getPrepTimeTextView().setText(prepTimeString);
        holder.getServingsTextView().setText(Integer.toString(recipeListArray.get(position).getServings()));

        // TODO: Add ability to change image
        //holder.recipeImageView.setImageURI();
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return recipeListArray.size();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView categoryTextView;
        private final TextView prepTimeTextView;
        private final TextView servingsTextView;
        private final ImageView recipeImageView;
        private final ImageView prepTimeImage;
        private final ImageView servingsImage;

        public ViewHolder(@NonNull View view) {
            super(view);

            titleTextView = view.findViewById(R.id.recipe_title_text_view);
            categoryTextView = view.findViewById(R.id.recipe_category_text_view);
            prepTimeTextView = view.findViewById(R.id.recipe_prep_time_text_view);
            servingsTextView = view.findViewById(R.id.recipe_servings_text_view);

            recipeImageView = view.findViewById(R.id.recipe_image_view);
            prepTimeImage = view.findViewById(R.id.prep_time_image);
            servingsImage = view.findViewById(R.id.servings_image);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getCategoryTextView() {
            return categoryTextView;
        }

        public TextView getPrepTimeTextView() {
            return prepTimeTextView;
        }

        public TextView getServingsTextView() {
            return servingsTextView;
        }

    }


}
