package com.cmput301f22t18.snackntrack.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.cmput301f22t18.snackntrack.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder>{
    private final ArrayList<String> localDataSet;
    private int checkPosition = -1;
    private final Context context;

    public UnitAdapter(ArrayList<String> data, Context context) {
        localDataSet = data;
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
    public UnitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unit_card, parent, false);
        return new UnitAdapter.ViewHolder(view);
    }

    /**
     * Bind the View Holder to the Adapter
     * @param holder the View Holder
     * @param position the position in the List
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull UnitAdapter.ViewHolder holder, int position) {
        holder.getUnitRadioButton().setText(localDataSet.get(position));
        holder.getUnitRadioButton().setChecked((position == checkPosition));

        int selectedStroke = ResourcesCompat.getColor(
                context.getResources(), R.color.red_900, null);
        int normalStroke = ResourcesCompat.getColor(
                context.getResources(), R.color.gray_400, null);
        ((MaterialCardView)holder
                .getView())
                .setStrokeColor((position == checkPosition) ? selectedStroke : normalStroke);
        holder.getUnitRadioButton().setOnClickListener(v->{
            checkPosition = holder.getAbsoluteAdapterPosition();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public int getCheckPosition() {
        return this.checkPosition;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton unitRadioButton;
        public ViewHolder(View view) {
            super(view);
            unitRadioButton = view.findViewById(R.id.unit_name_radio_button);

        }

        public RadioButton getUnitRadioButton() {
            return unitRadioButton;
        }

        public View getView() {
            return this.itemView;
        }
    }
}
