package com.cmput301f22t18.snackntrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanAdapter.ViewHolder> {

    private final ArrayList<Ingredient> ingredientList;
    private final DailyPlanAdapter.OnNoteListener myOnNoteListener;

    /**
     * This is the constructor for the class {@link DailyPlanAdapter}
     * @param ingredientList an ArrayList of DailyPlans
     * @param myOnNoteListener an OnNoteListener object
     * @since 1.0.0
     */
    public DailyPlanAdapter(ArrayList<Ingredient> ingredientList, DailyPlanAdapter.OnNoteListener myOnNoteListener) {
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
        private final TextView bestBeforeDateTextView;
        private final TextView amountTextView;
        private final TextView unitTextView;
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
            amountTextView = view.findViewById(R.id.ingredient_amount_text_view);
            unitTextView = view.findViewById(R.id.ingredient_unit_text_view);
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
                .inflate(R.layout.ingredient_detail_card, parent, false);

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
        holder.amountTextView.setText(ingredientList.get(position).getAmount());
        holder.unitTextView.setText(ingredientList.get(position).getUnit());
        Date bbf = ingredientList.
                get(position).
                getBestBeforeDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M d, y", Locale.CANADA);
        String dateText = "Best Before: " + simpleDateFormat.format(bbf);
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
