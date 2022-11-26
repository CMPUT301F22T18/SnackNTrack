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

import java.util.ArrayList;

public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanAdapter.ViewHolder> {

    private ArrayList<Recipe> recipeList;
    private Context context;
    private DailyPlanAdapter.OnNoteListener myOnNoteListener;

    /**
     * This is the constructor for the class {@link DailyPlanAdapter}
     * @param context provides the context
     * @param recipeList an ArrayList of DailyPlans
     * @param myOnNoteListener an OnNoteListener object
     * @since 1.0.0
     */
    public DailyPlanAdapter(Context context, ArrayList<Recipe> recipeList, DailyPlanAdapter.OnNoteListener myOnNoteListener) {
        this.context = context;
        this.recipeList = recipeList;
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
        DailyPlanAdapter.OnNoteListener onNoteListener;

        /**
         * This method is the constructor for the ViewHolder of RecipeListAdapter
         * @param itemView a particular item view
         * @param onNoteListener an OnNoteListener object
         * @since 1.0.0
         */
        public ViewHolder(@NonNull View itemView, DailyPlanAdapter.OnNoteListener onNoteListener) {
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
    public DailyPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recipe_list, parent, false);

        return new DailyPlanAdapter.ViewHolder(view, this.myOnNoteListener);
    }

    /**
     * This method is invoked by the layout manager. It replaces the contents of a view.
     * @param holder a ViewHolder object for RecipeListAdapter
     * @param position indicates the position of the view in the layout
     * @since 1.0.0
     */
    @Override
    public void onBindViewHolder(@NonNull DailyPlanAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(0);
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
        return this.recipeList.size();
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
