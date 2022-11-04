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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the Storage Adapter for the Storage Activity Recyler View
 */
public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> {
    private ArrayList<Ingredient> localDataSet;
    private final OnItemLongClickListener listener;


    public interface OnItemLongClickListener {
        void onClick(Ingredient item);
    }
    /**
     * Initialize the dataset of the Adapter.
     *
     * @param storage The Storage containing the data to be populated
     * by RecyclerView.
     */
    public StorageAdapter(Storage storage, FragmentManager fm,
                          LifecycleOwner owner,
                          ArrayList<String> unit_list, ArrayList<String> location_list,
                          ArrayList<String> category_list) {
        localDataSet = storage.getStorageList();
        listener = item -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", (Serializable) item);
            int pos = localDataSet.indexOf(item);
            bundle.putInt("position", pos);
            bundle.putSerializable("storage", (Serializable) storage);
            bundle.putStringArrayList("units", unit_list);
            bundle.putStringArrayList("locations", location_list);
            bundle.putStringArrayList("categories", category_list);
            bundle.putString("function", "Edit");
            fm.setFragmentResultListener("editIngredient", owner,
                    (requestKey, result) -> {
                        Ingredient new_item = (Ingredient) result.getSerializable("new_item");
                        storage.setIngredient(pos, new_item);
                        localDataSet.set(pos, new_item);
                        notifyItemChanged(pos);
                    });
            fm.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, AddEditIngredientFragment.class, bundle)
                    .addToBackStack("AddIngredient")
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
                .inflate(R.layout.storage_row_item, parent, false);

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
         * Get Location Text View
         * @return Location Text View
         */
        public TextView getLocationTextView() {
            return locationTextView;
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
         * Get Best Before Date Text View
         * @return Best Before Date Text View
         */
        public TextView getBestBeforeDateTextView() {
            return bestBeforeDateTextView;
        }
    }
}
