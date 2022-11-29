package com.cmput301f22t18.snackntrack.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.Label;
import com.cmput301f22t18.snackntrack.views.common.AddUnitDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder>{
    private final ArrayList localDataSet;
    private int checkPosition = -1;
    private final Context context;
    private final String labelType;
    private final FragmentManager fragmentManager;

    public LabelAdapter(ArrayList data, Context context, String labelType,
        FragmentManager fragmentManager) {
        localDataSet = data;
        this.context = context;
        this.labelType = labelType;
        this.fragmentManager = fragmentManager;
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
            if (unwrappedDrawable != null) {
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                Label currentLabel = (Label) localDataSet.get(position);
                wrappedDrawable.setTint(Color.parseColor(currentLabel.getColor()));
                int background = Color.parseColor(currentLabel.getColor());
                int foreground = ResourcesCompat.getColor(context.getResources(),
                        R.color.black, null);
                double contrast = ColorUtils.calculateContrast(foreground, background);
                if (contrast < 6.0f)
                    foreground = ResourcesCompat.getColor(context.getResources(),
                            R.color.white, null);
                holder.getLabelTextView().setTextColor(foreground);
                holder.getLabelTextView().setPadding(16, 8, 16, 8);
                holder.getLabelTextView().setBackground(wrappedDrawable);
            }
        }
        holder.getUnitRadioButton().setOnClickListener(v->{
            checkPosition = holder.getAbsoluteAdapterPosition();
            notifyDataSetChanged();
        });


        if (labelType.equals("unit")) {
            ImageButton editButton = holder.getEditLabelButton();
            editButton.setOnClickListener(v->openAddUnitDialog(
                    (String)localDataSet.get(position)
            ));
            ImageButton deleteButton = holder.getDeleteLabelButton();
            deleteButton.setOnClickListener(v->deleteUnit(
                    (String)localDataSet.get(position)
            ));

        }
    }

    private void deleteUnit(String unit) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid)
                    .update("units", FieldValue.arrayRemove(unit));
        }

    }

    private void openAddUnitDialog(String unit) {
        Bundle args = new Bundle();
        args.putString("unit", unit);
        AddUnitDialog addUnitDialog = new AddUnitDialog();
        addUnitDialog.setArguments(args);
        addUnitDialog.show(fragmentManager, AddUnitDialog.TAG);
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
        private final ImageButton editLabelButton, deleteLabelButton;
        public ViewHolder(View view) {
            super(view);
            unitRadioButton = view.findViewById(R.id.unit_name_radio_button);
            labelTextView = view.findViewById(R.id.label_text_view);
            editLabelButton = view.findViewById(R.id.edit_unit_button);
            deleteLabelButton = view.findViewById(R.id.delete_unit_button);

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

        public ImageButton getEditLabelButton() {
            return editLabelButton;
        }

        public ImageButton getDeleteLabelButton() {
            return deleteLabelButton;
        }
    }
}
