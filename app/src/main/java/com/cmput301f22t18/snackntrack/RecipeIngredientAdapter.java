package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.views.storage.AddEditIngredientFragment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the Ingredient Adapter for the Add Edit Recipe Activity RecyclerView
 * Adapted from StorageAdapter made by Duc Ho
 * @author Mark Maligalig
 */
public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder> {
    private ArrayList<Ingredient> localDataSet;
    private final OnItemLongClickListener listener;


    public interface OnItemLongClickListener {
        void onClick(Ingredient item);
    }
    /**
     * Initialize the dataset of the Adapter.
     *
     * @param recipe The Recipe containing the data to be populated
     * by RecyclerView.
     */
    // TODO: Adapt AddEditIngredientActivity to edit recipe ingredients

    // TODO: Change requestKey to editRecipeIngredient?
    public RecipeIngredientAdapter(Recipe recipe,
                                   FragmentManager fm,
                                   LifecycleOwner owner,
                                   ArrayList<String> unit_list,
                                   ArrayList<String> category_list) {
        localDataSet = recipe.getRecipeIngredients();
        listener = item -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", (Serializable) item);
            int pos = localDataSet.indexOf(item);
            bundle.putInt("position", pos);
            bundle.putSerializable("recipe", (Serializable) recipe);
            bundle.putStringArrayList("units", unit_list);
            bundle.putStringArrayList("categories", category_list);
            bundle.putString("function", "Edit");

            fm.setFragmentResultListener("editIngredient", owner,
                    (requestKey, result) -> {
                        Ingredient new_item = (Ingredient) result.getSerializable("new_item");
                        recipe.setIngredient(pos, new_item);
                        localDataSet.set(pos, new_item);
                        notifyItemChanged(pos);
                    });
                fm.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, AddEditIngredientFragment.class, bundle)
                    .addToBackStack("EditIngredient")
                    .commit();

        };
    }

    /**
     * Set the view holder for the recyler view
     * @param parent the parent view group
     * @param viewType the type of view
     * @return the ViewHolder associate with the row layout
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_recipe_ingredient, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Bind the View Holder to the Adapter
     * @param holder the View Holder
     * @param position the position in the List
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDescriptionTextView().setText(localDataSet.get(position).getDescription());
        holder.getCategoryTextView().setText(localDataSet.get(position).getCategory());
        String amountUnit = localDataSet.get(position).getAmount() + " " +
                localDataSet.get(position).getUnit();
        holder.getAmountUnitTextView().setText(amountUnit);
        holder.bind(localDataSet.get(position), listener);
    }





    /**
     * Get the number of items
     * @return the number of items
     */
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Provide a reference to the Card View of each item
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView descriptionTextView;
        private final TextView categoryTextView;
        private final TextView amountUnitTextView;

        public ViewHolder(View view) {
            super(view);

            descriptionTextView = view.findViewById(R.id.ingredient_description_text_view);
            categoryTextView = view.findViewById(R.id.ingredient_category_text_view);
            amountUnitTextView = view.findViewById(R.id.ingredient_amount_unit_text_view);
        }

        public void bind(final Ingredient item, final OnItemLongClickListener listener) {
            itemView.setOnLongClickListener(v -> {
                listener.onClick(item);
                return true;
            });
        }

        /**
         * Get Description Text View
         * @return Description Text View
         */
        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        /**
         * Get Amount + Unit Text View
         * @return Amount + Unit Text View
         */
        public TextView getAmountUnitTextView() {
            return amountUnitTextView;
        }

        /**
         * Get Category Text View
         * @return Category Text View
         */
        public TextView getCategoryTextView() {
            return categoryTextView;
        }

    }
}