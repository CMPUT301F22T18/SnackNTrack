package com.cmput301f22t18.snackntrack;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.cmput301f22t18.snackntrack.views.storage.AddEditIngredientFragment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanAdapter.ViewHolder> {

    private ArrayList<Ingredient> ingredientList;
    private Context context;
    private DailyPlanAdapter.OnNoteListener myOnNoteListener;

    /**
     * This is the constructor for the class {@link DailyPlanAdapter}
     * @param context provides the context
     * @param ingredientList an ArrayList of DailyPlans
     * @param myOnNoteListener an OnNoteListener object
     * @since 1.0.0
     */
    public DailyPlanAdapter(Context context, ArrayList<Ingredient> ingredientList, DailyPlanAdapter.OnNoteListener myOnNoteListener) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.myOnNoteListener = myOnNoteListener;
    }

    /**
     * This class provides a reference to the type of views that you are using. It is a custom {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
     * @since 1.0.0
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView descriptionTextView;
        private final TextView locationTextView;
        private final TextView categoryTextView;
        private final TextView amountUnitTextView;
        private final TextView bestBeforeDateTextView;
        DailyPlanAdapter.OnNoteListener onNoteListener;

        /**
         * This method is the constructor for the ViewHolder of RecipeListAdapter
         * @param view a particular item view
         * @param onNoteListener an OnNoteListener object
         * @since 1.0.0
         */
        public ViewHolder(@NonNull View view, DailyPlanAdapter.OnNoteListener onNoteListener) {
            super(view);
            descriptionTextView = view.findViewById(R.id.ingredient_description_text_view);
            locationTextView = view.findViewById(R.id.ingredient_location_text_view);
            categoryTextView = view.findViewById(R.id.ingredient_category_text_view);
            amountUnitTextView = view.findViewById(R.id.ingredient_amount_unit_text_view);
            bestBeforeDateTextView = view.findViewById(R.id.ingredient_best_before_date_text_view);
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
            this.onNoteListener.onIngredientNoteClick(getAbsoluteAdapterPosition());
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storage_row_item, parent, false);

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
        holder.descriptionTextView.setText(ingredientList.get(position).getDescription());
        holder.locationTextView.setText(ingredientList.get(position).getLocation());
        holder.categoryTextView.setText(ingredientList.get(position).getCategory());
        String amountUnit = ingredientList.get(position).getAmount() + " " +
                ingredientList.get(position).getUnit();
        holder.amountUnitTextView.setText(amountUnit);
        Date bbf = ingredientList.
                get(position).
                getBestBeforeDate();
        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = bbf.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String dateText = "Best Before: " + date.toString();
        holder.
                bestBeforeDateTextView.
                setText(dateText);

    }

    /**
     * This method returns the size of the list
     * @return the size of recipeArrayList
     * @since 1.0.0
     */
    @Override
    public int getItemCount() {
        return this.ingredientList.size();
    }

    public interface OnNoteListener {
        void onIngredientNoteClick(int position);
    }
}
