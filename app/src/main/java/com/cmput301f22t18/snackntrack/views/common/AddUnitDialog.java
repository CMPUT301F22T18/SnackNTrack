package com.cmput301f22t18.snackntrack.views.common;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.cmput301f22t18.snackntrack.R;
import com.cmput301f22t18.snackntrack.models.AppUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AddUnitDialog extends DialogFragment {
    TextInputEditText unitEditText;
    String oldUnit;
    public AddUnitDialog() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceBundle) {
        Bundle args = getArguments();
        Objects.requireNonNull(this.getDialog()).getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View view = inflater.inflate(
                R.layout.dialog_add_unit, container, false);
        unitEditText = view.findViewById(R.id.unit_edit_text);
        if (args!=null && args.getString("unit") != null) {
            unitEditText.setText(args.getString("unit"));
            oldUnit = args.getString("unit");
        }
        view.findViewById(R.id.add_a_unit_confirm_button).setOnClickListener(
                v->{
                    String unitText = "";

                    if (unitEditText.getText()!=null)
                        unitText = unitEditText.getText().toString();
                    if (unitText.equals("")) {
                        Toast.makeText(getContext(), "Insufficient input",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String uid = user.getUid();
                        if (oldUnit == null)
                            db.collection("users").document(uid)
                                    .update("units",
                                            FieldValue.arrayUnion(unitText))
                                    .addOnSuccessListener(task->
                                            Log.i("INFO", "Added units successfully"));
                        else {
                            String finalUnitText = unitText;
                            db.collection("users")
                                    .document(uid).get().addOnCompleteListener(
                                            task -> {
                                                AppUser appUser = task
                                                        .getResult().toObject(AppUser.class);
                                                if (appUser != null) {
                                                    appUser.getUnits().set(
                                                            appUser.getUnits().indexOf(oldUnit),
                                                            finalUnitText
                                                    );
                                                    db.collection("users")
                                                            .document(uid).update(
                                                                    "units",
                                                                    appUser.getUnits()
                                                            );
                                                }
                                            }
                                    );
                        }
                        dismiss();
                    }
                }
        );
        view.findViewById(R.id.add_a_unit_cancel_button).setOnClickListener(
                v->dismiss()
        );
        return view;
    }
    public static String TAG = "AddUnitDialog";

}
