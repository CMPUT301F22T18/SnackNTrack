package com.cmput301f22t18.snackntrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This class is a custom adapter for the shopping list.
 *
 * @author Charlotte Kalutycz
 * @version 1.0.0
 * @see RecyclerView
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private ArrayList<Ingredient> localShoppingList;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param shoppingList - The user's ShoppingList
     * by RecyclerView.
     */
    public ShoppingListAdapter(ShoppingList shoppingList) {
        localShoppingList = shoppingList.getShoppingList();
    }

    /**
     * Set the view holder for the recyler view
     * @param parent - the parent view group
     * @param viewType - the type of view
     * @return the ViewHolder associated with the item layout
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Bind the View Holder to the Adapter
     * @param holder - the View Holder
     * @param position - the position in the List
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDescriptionTextView().setText(localShoppingList.get(position).getDescription());
        holder.getCategoryTextView().setText(localShoppingList.get(position).getCategory());
        String amountUnit = localShoppingList.get(position).getAmount() + " " +
                localShoppingList.get(position).getUnit();
        holder.getAmountUnitTextView().setText(amountUnit);

    }

    /**
     * Provide a reference to the Card View of each ingredient item
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView descriptionTextView;
        private final TextView categoryTextView;
        private final TextView amountUnitTextView;
        private final CheckBox ingredientCheckBox;

        public ViewHolder(View view) {
            super(view);

            descriptionTextView = view.findViewById(R.id.ingredient_description_text_view);
            categoryTextView = view.findViewById(R.id.ingredient_category_text_view);
            amountUnitTextView = view.findViewById(R.id.ingredient_amount_unit_text_view);
            ingredientCheckBox = view.findViewById(R.id.ingredient_check_box);
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

        /**
         * Get the ingredient Check Box View
         * @return ingredient CheckBox
         */
        public CheckBox getIngredientCheckBox() {return ingredientCheckBox;}

    }

    /**
     * Get the number of items
     * @return number of ingredients in the shopping list
     */
    @Override
    public int getItemCount() {
        return localShoppingList.size();
    }
}




