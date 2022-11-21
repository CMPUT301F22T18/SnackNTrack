package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DailyPlanActivity extends AppCompatActivity implements RecipeListAdapter.OnNoteListener, StorageAdapter.OnItemLongClickListener {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        Date date = (Date) this.getIntent().getExtras().get("Date");
        Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT).show();

        // Firebase, should get meal plan list based on date, from user not generate new
        mealPlan = new MealPlan();
        dailyPlan = new DailyPlan();
        //dailyPlan = mealPlan.getDailyPlanAtDay(date);
        insertTestRecipes(date);

        recipeListAdapter = new RecipeListAdapter(this, dailyPlan.getDailyPlanRecipes(), this);
        recyclerView = this.findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setAdapter(recipeListAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

        storage = new Storage();
        insertTestStorage();
        unit_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        location_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_locations_array)));
        category_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));

        // DOES NOT WORK. error from storage adapter
        storageAdapter = new StorageAdapter(storage, getSupportFragmentManager(), this,
                unit_list, location_list, category_list);
        recyclerView2 = this.findViewById(R.id.ingredient_list_recycler_view);
        recyclerView2.setAdapter(storageAdapter);
        recyclerView2.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

        addIngredient = findViewById(R.id.add_ingredient_button);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MealPlanActivity.class);
                myIntent.putExtra("Date",date);
                startActivity(myIntent);
            }

        });

        addRecipe = findViewById(R.id.add_recipe_button);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AddEditMealPlanActivity.class);
                myIntent.putExtra("Date",date);
                startActivity(myIntent);
            }

        });

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
        Toast.makeText(getApplicationContext(), dailyPlan.getDailyPlanRecipes().get(position).getTitle(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(Ingredient item) {
        Toast.makeText(getApplicationContext(), item.getCategory(), Toast.LENGTH_SHORT).show();
    }
}
