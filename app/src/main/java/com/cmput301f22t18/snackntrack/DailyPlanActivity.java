package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DailyPlanActivity extends Fragment implements RecipeListAdapter.OnNoteListener, StorageAdapter.OnItemLongClickListener {

    private MealPlan mealPlan;
    private DailyPlan dailyPlan;
    private RecipeListAdapter recipeListAdapter;
    private DailyPlanAdapter DailyPlanAdapter;
    private StorageAdapter storageAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private Button addIngredient;
    private Button addRecipe;
    private Storage storage;
    private ArrayList<String> unit_list, location_list, category_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_daily_plan, container, false);

        Bundle dateBundle = getArguments();
        Date date = (Date) dateBundle.getSerializable("Date");
        Toast.makeText(v.getContext(), date.toString(), Toast.LENGTH_SHORT).show();

        // Firebase, should get meal plan list based on date, from user not generate new
        mealPlan = new MealPlan();
        dailyPlan = new DailyPlan();
        //dailyPlan = mealPlan.getDailyPlanAtDay(date);
        insertTestRecipes(date);

        recipeListAdapter = new RecipeListAdapter(this.getContext(), dailyPlan.getDailyPlanRecipes(), this);
        recyclerView = v.findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setAdapter(recipeListAdapter);


//        storage = new Storage();
//        insertTestStorage();
//        unit_list = new ArrayList<>
//                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
//        location_list = new ArrayList<>
//                (Arrays.asList(getResources().getStringArray(R.array.ingredient_locations_array)));
//        category_list = new ArrayList<>
//                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));
//
//        // DOES NOT WORK. error from storage adapter
//        storageAdapter = new StorageAdapter(storage, getSupportFragmentManager(), this,
//                unit_list, location_list, category_list);
//        recyclerView2 = this.findViewById(R.id.ingredient_list_recycler_view);
//        recyclerView2.setAdapter(storageAdapter);
//        recyclerView2.setLayoutManager(
//                new LinearLayoutManager(getApplicationContext()));
//
        addIngredient = v.findViewById(R.id.add_ingredient_button);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MealPlanActivity.class);
                startActivity(myIntent);
            }

        });

        addRecipe = v.findViewById(R.id.add_recipe_button);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle dateBundle = new Bundle();
                dateBundle.putSerializable("Date",date);
                AddEditMealPlanActivity addEditMealPlanActivity = new AddEditMealPlanActivity();
                addEditMealPlanActivity.setArguments(dateBundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, addEditMealPlanActivity);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
        return v;
    }

    private void insertTestRecipes(Date date) {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "pieces", "Wheat", 2);

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("A", "pieces", "C", 2));
        Recipe recipe = new Recipe("Soup", 10, "none", 2, "Dinner", in2, null);
        dailyPlan.addRecipe(recipe);
        dailyPlan.addIngredient(bread);
        dailyPlan.setDate(date);
        mealPlan.addDailyPlan(dailyPlan);

    }

    private void insertTestStorage() {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "pieces", "Wheat", 2);

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("Ace", "pieces", "C", 2));
        storage.addIngredient(bread);
        storage.addIngredient(new Ingredient("Ace", "pieces", "C", 2));

    }

    @Override
    public void onNoteClick(int position) {
        Toast.makeText(this.getContext(), dailyPlan.getDailyPlanRecipes().get(position).getTitle(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(Ingredient item) {
        Toast.makeText(this.getContext(), item.getCategory(), Toast.LENGTH_SHORT).show();
    }
}
