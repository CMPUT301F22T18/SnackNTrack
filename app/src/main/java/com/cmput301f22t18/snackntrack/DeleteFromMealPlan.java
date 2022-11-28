package com.cmput301f22t18.snackntrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * This class represent the a DeleteFromMealPlan dialog fragment, which deletes items from meal plans
 * @author Areeba Fazal
 */
public class DeleteFromMealPlan extends DialogFragment {

    /**
     * This method is called when the dialog is created
     * @author Areeba Fazal
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getArguments() != null;

        String ref = (String) getArguments().getSerializable("Reference");
        String docId = (String) getArguments().getSerializable("id");
        String type = (String) getArguments().getSerializable("type");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_delete_mealplan, null);

        builder.setView(view)

                // when ok is pressed, delete item
                .setPositiveButton(R.string.OK, (dialog, id) -> {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    DocumentReference tempDf = db.document(ref);
                    DocumentReference cf = db.collection("mealPlans")
                            .document(uid).collection("mealPlanList").document(docId);

                    // Find item by its id, and remove it
                    cf.update(type, FieldValue.arrayRemove(tempDf))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // These are a method which gets executed when the task is succeeded
                                    Log.d("Success", "Data has been added successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
                                    Log.d("error", "Data could not be added!" + e.toString());
                                }
                            });

                })

                // if cancel is pressed, do nothing
                .setNegativeButton(R.string.cancel, (dialog, id) ->
                        Objects.requireNonNull(DeleteFromMealPlan.this.getDialog()).cancel());
        return builder.create();
    }
}
