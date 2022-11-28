package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DailyPlanFragment extends Fragment implements RecipeListAdapter.OnNoteListener, DailyPlanAdapter.OnNoteListener {

    private MealPlan mealPlan;
    private DailyPlan dailyPlan;
    private RecipeListAdapter recipeListAdapter;
    private RecipeList recipeList;
    private DailyPlanAdapter dailyPlanAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private Button addIngredient;
    private Button addRecipe;
    private Button scaleButton;
    private EditText scaleText;
    private Storage storage;
    String temp;
    private String id;
    private ArrayList<DocumentReference> documentReferencesRecipies;
    private ArrayList<DocumentReference> documentReferencesIngredients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_daily_plan, container, false);

        Bundle dateBundle = getArguments();
        Date date = (Date) dateBundle.getSerializable("Date");
        id = (String) dateBundle.getSerializable("id");
        Log.w("ID: ", date.toString());

        // Firebase, should get meal plan list based on date, from user not generate new
        mealPlan = new MealPlan();
        dailyPlan = new DailyPlan();

        recipeListAdapter = new RecipeListAdapter(this.getContext(), dailyPlan.getDailyPlanRecipes(), this);
        recyclerView = v.findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setAdapter(recipeListAdapter);

        //gets list of ingredients (but from storage, not meal plans)
        storage = new Storage();
        //insertTestStorage();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<Recipe> recipeList = new ArrayList<>();

        documentReferencesRecipies = new ArrayList<>();
        documentReferencesIngredients = new ArrayList<>();
        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference dr = db
                    .collection("mealPlans")
                    .document(uid)
                    .collection("mealPlanList")
                    .document(id);
            dr.addSnapshotListener(
                    (value, error) -> {
                        if (error != null) {
                            Log.d("ERROR", error.getLocalizedMessage());
                        }
                        else if (value != null && value.getData() != null) {

                            //dailyPlan.setDate((Date)value.get("date"));
                            if (value.get("recipes") != null) {
                                ArrayList<DocumentReference> recipes =
                                        (ArrayList<DocumentReference>) value.get("recipes");
                                documentReferencesRecipies.addAll(recipes);

                                for (DocumentReference recipe : recipes) {

                                    Log.d("DEBUG", recipe.toString());
                                    recipe.addSnapshotListener((value2, error2) -> {
                                        if (error2 != null) {
                                            Log.d("ERROR", error2.getLocalizedMessage());
                                        }
                                        else if (value2 != null) {
                                            Recipe r = value2.toObject(Recipe.class);
                                            //Log.d("RECIPE", r.getTitle());
                                            int index = documentReferencesRecipies.indexOf(recipe);
                                            if (index != -1 && index < dailyPlan.getDailyPlanRecipes().size() &&
                                                    dailyPlan.getDailyPlanRecipes().get(index) != null) {
                                                dailyPlan.getDailyPlanRecipes().set(index, r);
                                            }
                                            else {
                                                //recipeList.add(r);
                                                dailyPlan.addRecipe(r);
                                            }
                                            recipeListAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                            if (value.get("ingredients") != null) {
                                ArrayList<DocumentReference> ingredients =
                                        (ArrayList<DocumentReference>) value.get("ingredients");
                                documentReferencesIngredients.addAll(ingredients);

                                for (DocumentReference ingredient : ingredients) {

                                    Log.d("DEBUG", ingredient.toString());
                                    ingredient.addSnapshotListener((value2, error2) -> {
                                        if (error2 != null) {
                                            Log.d("ERROR", error2.getLocalizedMessage());
                                        }
                                        else if (value2 != null) {
                                            Ingredient i = value2.toObject(Ingredient.class);
                                            //Log.d("RECIPE", i.getDescription());
                                            int index = documentReferencesIngredients.indexOf(ingredient);
                                            if (index != -1 && index < dailyPlan.getDailyPlanIngredients().size() &&
                                                    dailyPlan.getDailyPlanIngredients().get(index) != null) {
                                                dailyPlan.getDailyPlanIngredients().set(index, i);
                                            }
                                            else {
                                                //recipeList.add(r);
                                                dailyPlan.addIngredient(i);
                                            }
                                            dailyPlanAdapter.notifyDataSetChanged();
                                        }
                                    });

                                }
                            }
                        }
                    }
            );
        }

        //Toast.makeText(this.getContext(), storage.getStorageList().get(0).getDescription(), Toast.LENGTH_SHORT).show();
        dailyPlanAdapter = new DailyPlanAdapter(this.getContext(), dailyPlan.getDailyPlanIngredients(), this);
        recyclerView2 = v.findViewById(R.id.ingredient_list_recycler_view);
        recyclerView2.setAdapter(dailyPlanAdapter);
        recyclerView2.setLayoutManager(
                new LinearLayoutManager(this.getContext()));

        addIngredient = v.findViewById(R.id.add_ingredient_button);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle dateBundle = new Bundle();
                dateBundle.putSerializable("Date",date);
                dateBundle.putSerializable("id",id);
                AddIngredientToMealPlan addIngredientToMealPlan = new AddIngredientToMealPlan();
                addIngredientToMealPlan.setArguments(dateBundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, addIngredientToMealPlan);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });

        addRecipe = v.findViewById(R.id.add_recipe_button);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle dateBundle = new Bundle();
                dateBundle.putSerializable("Date",date);
                dateBundle.putSerializable("id",id);
                AddRecipeToMealPlan addRecipeToMealPlan = new AddRecipeToMealPlan();
                addRecipeToMealPlan.setArguments(dateBundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, addRecipeToMealPlan);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });

        scaleText = v.findViewById(R.id.scale_text);

        scaleButton = v.findViewById(R.id.scale_button);
        scaleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String temp = scaleText.getText().toString();
                if (temp.length()>0){
                    int newServings = Integer.parseInt(temp);
                    
                    for (int recipeIndex = 0; recipeIndex < dailyPlan.getDailyPlanRecipes().size(); recipeIndex++){
                        Log.d("Recipe",dailyPlan.getDailyPlanRecipes().get(recipeIndex).getTitle());
                        Log.d("idofRecipe",documentReferencesRecipies.get(recipeIndex).getPath());
                        Recipe recipe = dailyPlan.getDailyPlanRecipes().get(recipeIndex);
                        int currentServings = recipe.getServings();
                        ArrayList<Ingredient> recipeIngredients;
                        recipeIngredients = recipe.getRecipeIngredients();

                        for (int ingredientIndex = 0; ingredientIndex < recipeIngredients.size(); ingredientIndex++) {
                            Ingredient ingredient = recipeIngredients.get(ingredientIndex);
                            int currentAmount = ingredient.getAmount();
                            ingredient.setAmount((int) Math.ceil(currentAmount * (double)newServings / currentServings));

                            Log.d(ingredient.getDescription(), Integer.toString(ingredient.getAmount()));
                        }
                        recipe.setServings(newServings);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        DocumentReference df = db.document(documentReferencesRecipies.get(recipeIndex).getPath());
                        df.set(recipe);

                    }

                }
            }

        });
        return v;
    }

    private void insertTestRecipes(Date date) {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "Fridge","pack", "Bakery", 2, date);

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("cheese", "Fridge","pack", "Bakery", 2, date));
        Recipe recipe = new Recipe("Soup", 10, "none", 2, "Dinner", in2, null);
        Recipe recipe2 = new Recipe("Sandwich", 50, "none", 1, "Dinner", in2, null);
        dailyPlan.addRecipe(recipe);
        dailyPlan.addRecipe(recipe2);
        dailyPlan.addIngredient(bread);
        dailyPlan.setDate(date);
        mealPlan.addDailyPlan(dailyPlan);

    }

    private void insertTestStorage(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date =null;
        try {
            date = formatter.parse("2022/11/03");
        }catch (ParseException e) {
        e.printStackTrace();
        }
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "Fridge","pack", "Bakery", 2, date);
        Ingredient cheese = new Ingredient("Cheese", "Fridge","pack", "Bakery", 1, date);
        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("Ace", "pieces", "C", 2));
        storage.addIngredient(bread);
        storage.addIngredient(cheese);
        //storage.addIngredient(new Ingredient("Ace", "pieces", "C", 2));

    }

    @Override
    public void onNoteClick(int position) {
        Toast.makeText(this.getContext(), dailyPlan.getDailyPlanRecipes().get(position).getTitle(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Reference", documentReferencesRecipies.get(position).getPath());
        bundle.putSerializable("id",id);
        bundle.putSerializable("type","recipes");
        DeleteFromMealPlan fragment = new DeleteFromMealPlan();
        fragment.setArguments(bundle);
        fragment.show(getChildFragmentManager(), "Add City");

    }

    @Override
    public void onIngredientNoteClick(int position) {
        Toast.makeText(this.getContext(), dailyPlan.getDailyPlanIngredients().get(position).getDescription(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable("Reference", documentReferencesIngredients.get(position).getPath());
        bundle.putSerializable("id",id);
        bundle.putSerializable("type","ingredients");
        DeleteFromMealPlan fragment = new DeleteFromMealPlan();
        fragment.setArguments(bundle);
        fragment.show(getChildFragmentManager(), "Add City");

    }
}
