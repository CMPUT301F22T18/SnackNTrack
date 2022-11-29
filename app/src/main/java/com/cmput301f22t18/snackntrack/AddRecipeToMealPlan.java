package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

/**
 * This class represent the a AddRecipeToMealPlan, which adds a recipe to the meal plan
 * @author Areeba Fazal
 */
public class AddRecipeToMealPlan extends Fragment implements RecipeListAdapter.OnNoteListener{
    private RecipeList recipeList;
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private ListView listView;
    private ArrayList<String> recipeNames;
    private ArrayList<Recipe> list;
    private Recipe selectedRecipe;
    private Date date;
    private String id;
    private String uid;

    private ArrayList<DocumentReference> newRecipe;
    private ArrayList<DocumentReference> documentReferencesRecipe;
    private DocumentReference selectedRecipeReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_mealplan, container, false);

        // get arguments from DailyPlanAdapter
        Bundle dateBundle = getArguments();
        date = (Date) dateBundle.getSerializable("Date");
        id = (String) dateBundle.getSerializable("id");

        // set recipe list recycler view
        recipeList = new RecipeList();
        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipeList.getRecipeList(), this);
        recyclerView = v.findViewById(R.id.meal_plan_add_recycler_view);
        recyclerView.setAdapter(recipeListAdapter);

        // get user's recipes from firebase
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

    /**
     * This is called when the recipe list is clicked
     * Adds the clicked recipe to the daily plan, then switches back to DailyPlanFragment
     * @param position - the index of the recipe that was clicked
     * @author Areeba Fazal
     */
    @Override
    public void onNoteClick(int position) {
        selectedRecipe = recipeList.getRecipeList().get(position);
        selectedRecipeReference = newRecipe.get(position);
        DailyPlanFragment dailyPlanFragment = new DailyPlanFragment();

        // Create bundle
        Bundle dateBundle = new Bundle();
        dateBundle.putSerializable("Date",date);
        dateBundle.putParcelable("Recipe",selectedRecipe);
        dateBundle.putSerializable("id",id);
        dailyPlanFragment.setArguments(dateBundle);

        // Save the selected recipe in a separate place in Firebase
        // to ensure original recipe is not effected
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference df = db.collection("mealPlans")
                .document(uid).collection("mealPlanRecipes").document();

        df.set(selectedRecipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d("SET RECIPE AT: ", df.getPath());

                        DocumentReference tempDf = db.document(df.getPath());
                        selectedRecipeReference = tempDf;

                        // Once new recipe is created, add it back to fireBase for this DailyPlan
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d("error", "Data could not be added!" + e.toString());
                    }
                });

        // Switch back to DailyPlanFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, dailyPlanFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
