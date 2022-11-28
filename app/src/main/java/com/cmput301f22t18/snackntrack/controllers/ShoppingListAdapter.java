package com.cmput301f22t18.snackntrack.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.Ingredient;

import com.cmput301f22t18.snackntrack.models.ShoppingList;
import com.cmput301f22t18.snackntrack.models.Storage;

import java.util.ArrayList;

/**
 * This class is a custom adapter for the shopping list.
 *
 * @author Charlotte Kalutycz
 * @version 1.0.0
 * @see RecyclerView
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private ShoppingList localShoppingList;
    private Storage localStorage;
    private ArrayList<Ingredient> checkedIngredients;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param shoppingList - The user's ShoppingList
     * by RecyclerView.
     */
    public ShoppingListAdapter(ShoppingList shoppingList) {
        localShoppingList = shoppingList;
        checkedIngredients = new ArrayList<Ingredient>();
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
        int viewPosition = holder.getAbsoluteAdapterPosition();
        holder.getDescriptionTextView().setText(localShoppingList.getShoppingList().get(viewPosition).getDescription());
        holder.getCategoryTextView().setText(localShoppingList.getShoppingList().get(viewPosition).getCategory());
        String amountUnit = localShoppingList.getShoppingList().get(viewPosition).getAmount() + " " +
                localShoppingList.getShoppingList().get(viewPosition).getUnit();
        holder.getAmountUnitTextView().setText(amountUnit);
        holder.getIngredientCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // If box is checked, add ingredient to checked list
                    checkedIngredients.add(localShoppingList.getShoppingList().get(viewPosition));
                }
                else {
                    // If unchecked, remove from the checked list
                    checkedIngredients.remove(localShoppingList.getShoppingList().get(viewPosition));
                }
            }
        });
    }

    /**
     * Gets the array list of checked ingredients
     * @return checkedIngredients array list
     */
    public ArrayList<Ingredient> getCheckedIngredients() {
        return checkedIngredients;
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
         * Get Ingredient Check Box
         * @return Ingredient Check Box
         */
        public CheckBox getIngredientCheckBox() {
            return ingredientCheckBox;
        }
    }

    /**
     * Get the number of items
     * @return number of ingredients in the shopping list
     */
    @Override
    public int getItemCount() {
        return localShoppingList.getShoppingList().size();
    }
}




