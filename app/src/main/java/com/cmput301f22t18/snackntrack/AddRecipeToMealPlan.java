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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class AddRecipeToMealPlan extends Fragment implements RecipeListAdapter.OnNoteListener{
    private RecipeList recipeList;
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private ListView listView;
    ArrayList<String> recipeNames;
    ArrayList<Recipe> list;
    Recipe selectedRecipe;
    Date date;
    String id;
    String uid;

    ArrayList<DocumentReference> newRecipe;
    ArrayList<DocumentReference> documentReferencesRecipe;
    DocumentReference selectedRecipeReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_mealplan, container, false);
        Bundle dateBundle = getArguments();
        date = (Date) dateBundle.getSerializable("Date");
        id = (String) dateBundle.getSerializable("id");
        // should get recipe list from database
        recipeList = new RecipeList();
        //insertTestRecipes();
        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipeList.getRecipeList(), this);
        recyclerView = v.findViewById(R.id.meal_plan_add_recycler_view);
        recyclerView.setAdapter(recipeListAdapter);

        newRecipe = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert(user != null);
        uid = user.getUid();
        CollectionReference cf = db.collection("recipeLists")
                .document(uid).collection("recipes");

        cf.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("AddRec", "Listen failed.", error);
                return;
            }
            for (QueryDocumentSnapshot doc : value) {
                recipeList.addRecipe(doc.toObject(Recipe.class));
                newRecipe.add(doc.getReference());
                Log.w("ref", newRecipe.get(0).toString(), error);
            }
            recipeListAdapter.notifyDataSetChanged();
        });
    return v;
    }

    private void insertTestRecipes() {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "pieces", "Wheat", 2);

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("A", "pieces", "C", 2));
        Recipe recipe = new Recipe("Salad", 10, "none", 2, "Dinner", in2, null);
        recipeList.addRecipe(recipe);
    }


    @Override
    public void onNoteClick(int position) {
        Toast.makeText(this.getContext(), recipeList.getRecipeList().get(position).getTitle(), Toast.LENGTH_SHORT).show();
        selectedRecipe = recipeList.getRecipeList().get(position);
        selectedRecipeReference = newRecipe.get(position);
        DailyPlanFragment dailyPlanFragment = new DailyPlanFragment();
        Bundle dateBundle = new Bundle();
        dateBundle.putSerializable("Date",date);
        dateBundle.putSerializable("Recipe",selectedRecipe);
        dateBundle.putSerializable("id",id);
        dailyPlanFragment.setArguments(dateBundle);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference df = db.collection("mealPlans")
                .document(uid).collection("mealPlanList").document(id);

//        df.set(selectedRecipe)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // These are a method which gets executed when the task is succeeded
//                        Log.d("Success", "Data has been added successfully!");
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // These are a method which gets executed if there’s any problem
//                        Log.d("error", "Data could not be added!" + e.toString());
//                    }
//                });

//        db.collection("mealPlans")
//                .document(uid).collection("mealPlanRecipes")
//                .add(selectedRecipe)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });



        DocumentReference cf = db.collection("mealPlans")
                .document(uid).collection("mealPlanList").document(id);

        cf.update("recipes", FieldValue.arrayUnion(selectedRecipeReference))
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
