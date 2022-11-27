package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddIngredientToMealPlan extends Fragment implements DailyPlanAdapter.OnNoteListener {
    private Storage storage;
    private DailyPlanAdapter dailyPlanAdapter;
    private RecyclerView recyclerView;
    Ingredient selectedIngredient;
    Date date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_mealplan, container, false);
        Bundle dateBundle = getArguments();
        date = (Date) dateBundle.getSerializable("Date");
        // should get recipe list from database
        storage = new Storage();
        insertTestStorage();

        dailyPlanAdapter = new DailyPlanAdapter(this.getContext(), storage.getStorageList(), this);
        recyclerView = v.findViewById(R.id.meal_plan_add_recycler_view);
        recyclerView.setAdapter(dailyPlanAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this.getContext()));

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
        DailyPlanActivity dailyPlanActivity = new DailyPlanActivity();
        Bundle dateBundle = new Bundle();
        dateBundle.putSerializable("Date",date);
        dateBundle.putSerializable("Ingredient",selectedIngredient);
        dailyPlanActivity.setArguments(dateBundle);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert(user != null);
        String uid = user.getUid();

        CollectionReference cf = db.collection("mealPlans")
                .document(uid).collection("mealPlanList");

        cf.document().set(selectedIngredient)
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
        transaction.replace(R.id.fragment_container_main, dailyPlanActivity);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
