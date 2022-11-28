package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddIngredientToMealPlan extends Fragment implements DailyPlanAdapter.OnNoteListener {
    private Storage storage;
    private DailyPlanAdapter dailyPlanAdapter;
    private RecyclerView recyclerView;
    Ingredient selectedIngredient;
    Date date;
    String id;
    ArrayList<DocumentReference> newIngredient;
    ArrayList<DocumentReference> documentReferencesIngredients = new ArrayList<>();;
    DocumentReference selectedIngredientReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_mealplan, container, false);
        Bundle dateBundle = getArguments();
        date = (Date) dateBundle.getSerializable("Date");
        id = (String) dateBundle.getSerializable("id");
        // should get recipe list from database
        storage = new Storage();

        dailyPlanAdapter = new DailyPlanAdapter(storage.getStorageList(), this);
        recyclerView = v.findViewById(R.id.meal_plan_add_recycler_view);
        recyclerView.setAdapter(dailyPlanAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this.getContext()));
        newIngredient = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert(user != null);
        String uid = user.getUid();
        CollectionReference cf = db.collection("storages")
                .document(uid).collection("ingredients");

        cf.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("AddIngred", "Listen failed.", error);
                return;
            }
            for (QueryDocumentSnapshot doc : value) {
                storage.addIngredient(doc.toObject(Ingredient.class));
                newIngredient.add(doc.getReference());
                Log.w("ref", newIngredient.get(0).toString(), error);
            }
            dailyPlanAdapter.notifyDataSetChanged();
        });



        return v;
    }

    private void insertTestStorage() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = formatter.parse("2022/11/03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "Fridge", "pack", "Bakery", 2, date);

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("Ace", "pieces", "C", 2));
        storage.addIngredient(bread);
    }


    @Override
    public void onIngredientNoteClick(int position) {
        Toast.makeText(this.getContext(), storage.getStorageList().get(position).getDescription(), Toast.LENGTH_SHORT).show();
        selectedIngredient = storage.getStorageList().get(position);
        selectedIngredientReference = newIngredient.get(position);
        Log.w("NEW INGRED", selectedIngredientReference.getPath());
        DailyPlanFragment dailyPlanFragment = new DailyPlanFragment();
        Bundle dateBundle = new Bundle();
        dateBundle.putSerializable("Date",date);
        dateBundle.putSerializable("Ingredient",selectedIngredient);
        dateBundle.putSerializable("id",id);
        dailyPlanFragment.setArguments(dateBundle);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DocumentReference cf = db.collection("mealPlans")
                .document(uid).collection("mealPlanList").document(id);

        cf.update("ingredients", FieldValue.arrayUnion(selectedIngredientReference))
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


        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, dailyPlanFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
