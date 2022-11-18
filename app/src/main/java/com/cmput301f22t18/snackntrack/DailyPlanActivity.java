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
import java.util.Date;

public class DailyPlanActivity extends AppCompatActivity {

    private MealPlan mealPlan;
    private DailyPlan dailyPlan;
    private RecipeListAdapter RecipeListAdapter;
    private StorageAdapter StorageAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private Button addIngredient;
    private Button addRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        Date date = (Date) this.getIntent().getExtras().get("Date");
        Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT).show();

        // Firebase, should get meal plan list from user not generate new
        mealPlan = new MealPlan();
        dailyPlan = new DailyPlan();
        //dailyPlan = mealPlan.getDailyPlanAtDay(date);
        insertTestRecipes(date);

        RecipeListAdapter = new RecipeListAdapter(this, dailyPlan.getDailyPlanRecipes(), null);
        recyclerView = this.findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setAdapter(RecipeListAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

        RecipeListAdapter = new RecipeListAdapter(this, dailyPlan.getDailyPlanRecipes(), null);
        recyclerView2 = this.findViewById(R.id.ingredient_list_recycler_view);
        recyclerView2.setAdapter(RecipeListAdapter);
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
}
