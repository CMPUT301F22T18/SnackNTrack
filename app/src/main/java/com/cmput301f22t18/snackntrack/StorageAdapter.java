package com.cmput301f22t18.snackntrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> {
    private ArrayList<Ingredient> localDataSet;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param storage The Storage containing the data to be populated
     * by RecyclerView.
     */
    public StorageAdapter(Storage storage) {
        localDataSet = storage.getStorage();
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
                .inflate(R.layout.storage_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDescriptionTextView().setText(localDataSet.get(position).getDescription());
        holder.getLocationTextView().setText(localDataSet.get(position).getLocation());
        holder.getCategoryTextView().setText(localDataSet.get(position).getCategory());
        String amountUnit = localDataSet.get(position).getAmount() + " " +
                localDataSet.get(position).getUnit();
        holder.getAmountUnitTextView().setText(amountUnit);
        Date bbf = localDataSet.
                get(position).
                getBestBeforeDate();
        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = bbf.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String dateText = "Best Before: " + date.toString();
        holder.
                getBestBeforeDateTextView().
                setText(dateText);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Provide a reference to the Frame Layout of each row
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView descriptionTextView;
        private final TextView locationTextView;
        private final TextView categoryTextView;
        private final TextView amountUnitTextView;
        private final TextView bestBeforeDateTextView;

        public ViewHolder(View view) {
            super(view);

            descriptionTextView = view.findViewById(R.id.ingredient_description_text_view);
            locationTextView = view.findViewById(R.id.ingredient_location_text_view);
            categoryTextView = view.findViewById(R.id.ingredient_category_text_view);
            amountUnitTextView = view.findViewById(R.id.ingredient_amount_unit_text_view);
            bestBeforeDateTextView = view.findViewById(R.id.ingredient_best_before_date_text_view);
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public TextView getLocationTextView() {
            return locationTextView;
        }

        public TextView getAmountUnitTextView() {
            return amountUnitTextView;
        }

        public TextView getCategoryTextView() {
            return categoryTextView;
        }

        public TextView getBestBeforeDateTextView() {
            return bestBeforeDateTextView;
        }
    }
}
