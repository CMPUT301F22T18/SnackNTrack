package com.cmput301f22t18.snackntrack.views.shoppingList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cmput301f22t18.snackntrack.R;

/**
 * A simple {@link DialogFragment} subclass.
 *
 * This class displays a confirmation to clear the shopping list
 * @author Charlotte Kalutycz
 */
public class ClearShoppingListFragment extends DialogFragment {
    Boolean clear;

    public ClearShoppingListFragment() {
        // Empty constructor for ClearShoppingListFragment
    }

    public ClearShoppingListFragment(Boolean clear) {
        // Empty constructor for ClearShoppingListFragment
        this.clear = clear;
    }

    /**
     * Creates a new ClearShoppingListFragment dialog
     * @param savedInstanceState -
     * @return builder.create() - new DialogFragment
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_clear_shopping_list, null);
        ImageButton confirmButton = v.findViewById(R.id.clear_confirm_button);
        ImageButton cancelButton = v.findViewById(R.id.clear_cancel_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear = Boolean.TRUE;
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(v);
        return builder.create();
    }


    public static String TAG = "ClearShoppingListFragment";
}
