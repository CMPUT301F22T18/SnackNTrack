package com.cmput301f22t18.snackntrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.getTextView().setText(localDataSet.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Provide a reference to the Frame Layout of each row
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.ingredient_description_text_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
