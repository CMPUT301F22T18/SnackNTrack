package com.cmput301f22t18.snackntrack.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.Label;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder>{
    private final ArrayList localDataSet;
    private int checkPosition = -1;
    private final Context context;
    private final String labelType;

    public LabelAdapter(ArrayList data, Context context, String labelType) {
        localDataSet = data;
        this.context = context;
        this.labelType = labelType;
    }
    /**
     * Set the view holder for the recyler view
     * @param parent the parent view group
     * @param viewType the type of view
     * @return the ViewHolder associate with the row layout
     */
    @NonNull
    @Override
    public LabelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.label_card, parent, false);
        return new LabelAdapter.ViewHolder(view);
    }

    /**
     * Bind the View Holder to the Adapter
     * @param holder the View Holder
     * @param position the position in the List
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull LabelAdapter.ViewHolder holder, int position) {

        if (labelType.equals("unit"))
            holder.getLabelTextView().setText((String)localDataSet.get(position));
        else
            holder.getLabelTextView().setText(((Label)localDataSet.get(position)).getName());

        holder.getUnitRadioButton().setChecked((position == checkPosition));

        int selectedStroke = ResourcesCompat.getColor(
                context.getResources(), R.color.red_900, null);
        int normalStroke = ResourcesCompat.getColor(
                context.getResources(), R.color.gray_400, null);
        ((MaterialCardView)holder
                .getView())
                .setStrokeColor((position == checkPosition) ? selectedStroke : normalStroke);

        if (!labelType.equals("unit")) {
            Drawable unwrappedDrawable = ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.custom_label, null);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            Label currentLabel = (Label) localDataSet.get(position);
            wrappedDrawable.setTint(Color.parseColor(currentLabel.getColor()));
            holder.getLabelTextView().setPadding(16, 8, 16, 8);
            holder.getLabelTextView().setBackground(wrappedDrawable);
            if (labelType.equals("location")) {
                int white = ResourcesCompat.getColor(
                        context.getResources(), R.color.white, null);
                holder.getLabelTextView().setTextColor(white);
            }
        }
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
        private final TextView labelTextView;
        public ViewHolder(View view) {
            super(view);
            unitRadioButton = view.findViewById(R.id.unit_name_radio_button);
            labelTextView = view.findViewById(R.id.label_text_view);

        }

        public RadioButton getUnitRadioButton() {
            return unitRadioButton;
        }

        public TextView getLabelTextView() {
            return labelTextView;
        }

        public View getView() {
            return this.itemView;
        }
    }
}
