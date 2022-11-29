package com.cmput301f22t18.snackntrack.controllers;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.AppUser;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Label;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents the Storage Adapter for the Storage Activity Recyler View
 */
public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> {
    private final ArrayList<Ingredient> localDataSet;
    private final Context context;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param storage The Storage containing the data to be populated
     * by RecyclerView.
     */
    public StorageAdapter(Context context, Storage storage) {
        localDataSet = storage.getStorageList();
        this.context = context;
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
        Ingredient ingredient = localDataSet.get(position);
        String locationText = ingredient.getLocation();
        String categoryText = ingredient.getCategory();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid).get().addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            AppUser appUser = task.getResult().toObject(AppUser.class);
                            if (appUser != null) {
                                if (locationText != null) {
                                    Label desiredLocation = appUser.getLocations().stream()
                                            .filter(location -> locationText.equals(location.getName()))
                                            .findAny()
                                            .orElse(null);
                                    if (desiredLocation != null) {
                                        Drawable unwrappedDrawable = ResourcesCompat.getDrawable(
                                                context.getResources(),
                                                R.drawable.custom_label, null);
                                        if (unwrappedDrawable != null) {
                                            Drawable wrappedDrawable = DrawableCompat
                                                    .wrap(unwrappedDrawable);
                                            wrappedDrawable.setTint(Color
                                                    .parseColor(desiredLocation.getColor()));
                                            holder.getLocationTextView()
                                                    .setBackground(wrappedDrawable);
                                            holder.getLocationTextView()
                                                    .setVisibility(View.VISIBLE);
                                            holder.getLocationTextView().setText(locationText);
                                        }
                                    }
                                }
                                Label desiredCategory = appUser.getCategories().stream()
                                        .filter(category -> categoryText.equals(category.getName()))
                                        .findAny()
                                        .orElse(null);

                                if (desiredCategory != null) {
                                    Drawable unwrappedDrawable = ResourcesCompat
                                            .getDrawable(context.getResources(),
                                            R.drawable.custom_label, null);
                                    if (unwrappedDrawable != null) {
                                        Drawable wrappedDrawable = DrawableCompat
                                                .wrap(unwrappedDrawable);
                                        wrappedDrawable.setTint(Color
                                                .parseColor(desiredCategory.getColor()));
                                        holder.getCategoryTextView().setBackground(wrappedDrawable);
                                        holder.getCategoryTextView().setTextColor(
                                                ResourcesCompat.getColor(context.getResources(),
                                                        R.color.black, null)
                                        );
                                        holder.getCategoryTextView().setVisibility(View.VISIBLE);
                                        holder.getCategoryTextView().setText(categoryText);
                                    }
                                }
                            }

                        }
                    }
            );
        }
        holder.getDescriptionTextView().setText(ingredient.getDescription());
        holder.getAmountTextView().setText(String.format(Locale.CANADA, "%d",
                localDataSet.get(position).getAmount()));
        holder.getUnitTextView().setText(ingredient.getUnit());
        Date bbf = localDataSet.
                get(position).
                getBestBeforeDate();
        if (bbf != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, y", Locale.CANADA);
            String dateText = "Best Before: " + simpleDateFormat.format(bbf);
            holder.
                    getBestBeforeDateTextView().
                    setText(dateText);
        }
        else {
            holder.getBestBeforeDateTextView().setTextColor(
                    ResourcesCompat.getColor(context.getResources(),
                            R.color.red_500, null)
            );
        }

        if (locationText == null || bbf == null) {
            MaterialCardView materialCardView = (MaterialCardView) holder.getView();
            materialCardView.setStrokeColor(ResourcesCompat.getColor(context.getResources(),
                    R.color.red_500, null));
        }
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

        public View getView() {
            return this.itemView;
        }
    }
}