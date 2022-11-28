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

    /**
     * Interface used to return user's choice to clear
     */
    public interface CallBack {
        public void confirmed(boolean clear);
    }

    public CallBack callBack;

    /**
     * This is a constructor for the ClearShoppingListFragment class
     * @param callBack call back to shopping list fragment
     */
    public ClearShoppingListFragment(CallBack callBack) {
        this.callBack = callBack;
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
                callBack.confirmed(true);
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.confirmed(false);
                dismiss();
            }
        });

        builder.setView(v);
        return builder.create();
    }
}
