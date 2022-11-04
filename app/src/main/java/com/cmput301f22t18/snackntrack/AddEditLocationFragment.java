package com.cmput301f22t18.snackntrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class AddEditLocationFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getArguments() != null;
        String type = getArguments().getString("type");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_edit_location, null);
        EditText locationEditText = view.findViewById(R.id.location_edit_text);
        TextView titleTextView = view.findViewById(R.id.add_item_title);
        String title = titleTextView.getText().toString() + ' ' + type;
        titleTextView.setText(title);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.OK, (dialog, id) -> {
                    Bundle result = new Bundle();
                    result.putString("new_item", locationEditText.getText().toString());
                    requireActivity().getSupportFragmentManager().setFragmentResult(
                            type, result);
                    requireActivity().getSupportFragmentManager().popBackStackImmediate();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) ->
                        Objects.requireNonNull(AddEditLocationFragment.this.getDialog()).cancel());
        return builder.create();
    }
}