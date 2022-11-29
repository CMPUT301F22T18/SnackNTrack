package com.cmput301f22t18.snackntrack.views.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.fragment.app.DialogFragment;

import com.cmput301f22t18.snackntrack.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DeleteIngredientConfirmationDialog extends DialogFragment {

    public DeleteIngredientConfirmationDialog() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(this.getDialog()).getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View view = inflater.inflate(
                R.layout.dialog_delete_ingredient, container, false);
        Objects.requireNonNull(getDialog()).requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.findViewById(R.id.delete_an_ingredient_confirm_button)
                .setOnClickListener(v -> {
                    if (getArguments()!=null) {
                        String id = getArguments().getString("id");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        if (user!=null) {
                            String uid = user.getUid();
                            db.collection("storages").document(uid)
                                    .collection("ingredients").document(id)
                                    .delete();
                            dismiss();
                            Intent intent = new Intent();
                            intent.putExtra("action", "delete");
                            intent.putExtra("id", id);
                            requireActivity().setResult(Activity.RESULT_OK, intent);
                            requireActivity().finish();

                        }

                    }

                });
        view.findViewById(R.id.delete_an_ingredient_cancel_button)
                .setOnClickListener(v -> dismiss());
        return view;
    }

    public static String TAG = "DeleteIngredientConfirmationDialog";
}
