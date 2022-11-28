package com.cmput301f22t18.snackntrack.controllers;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents the Storage Adapter for the Storage Activity Recyler View
 */
public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> {
    private final ArrayList<Ingredient> localDataSet;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param storage The Storage containing the data to be populated
     * by RecyclerView.
     */
    public StorageAdapter(Storage storage) {
        localDataSet = storage.getStorageList();
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
                .inflate(R.layout.ingredient_detail_card, parent, false);

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
        holder.getLocationTextView().setText(localDataSet.get(position).getLocation());
        holder.getCategoryTextView().setText(localDataSet.get(position).getCategory());
        holder.getAmountTextView().setText(String.format(Locale.CANADA, "%d",
                localDataSet.get(position).getAmount()));
        holder.getUnitTextView().setText(localDataSet.get(position).getUnit());
        Date bbf = localDataSet.
                get(position).
                getBestBeforeDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, y", Locale.CANADA);
        String dateText = "Best Before: " + simpleDateFormat.format(bbf);
        holder.
                getBestBeforeDateTextView().
                setText(dateText);
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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView descriptionTextView;
        private final TextView locationTextView;
        private final TextView categoryTextView;
        private final TextView amountTextView;
        private final TextView unitTextView;
        private final TextView bestBeforeDateTextView;

        public ViewHolder(View view) {
            super(view);

            descriptionTextView = view.findViewById(R.id.ingredient_description_text_view);
            locationTextView = view.findViewById(R.id.ingredient_location_text_view);
            categoryTextView = view.findViewById(R.id.ingredient_category_text_view);
            amountTextView = view.findViewById(R.id.ingredient_amount_text_view);
            unitTextView = view.findViewById(R.id.ingredient_unit_text_view);
            bestBeforeDateTextView = view.findViewById(R.id.ingredient_best_before_date_text_view);
        }


        /**
         * Get Description Text View
         * @return Description Text View
         */
        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        /**
         * Get Location Text View
         * @return Location Text View
         */
        public TextView getLocationTextView() {
            return locationTextView;
        }

        /**
         * Get Amount Text View
         * @return Amount Text View
         */
        public TextView getAmountTextView() {
            return amountTextView;
        }

        /**
         * Get Amount Text View
         * @return Amount Text View
         */
        public TextView getUnitTextView() {
            return unitTextView;
        }

        /**
         * Get Category Text View
         * @return Category Text View
         */
        public TextView getCategoryTextView() {
            return categoryTextView;
        }

        /**
         * Get Best Before Date Text View
         * @return Best Before Date Text View
         */
        public TextView getBestBeforeDateTextView() {
            return bestBeforeDateTextView;
        }
    }
}