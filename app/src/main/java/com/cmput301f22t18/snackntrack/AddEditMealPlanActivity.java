package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class AddEditMealPlanActivity extends AppCompatActivity implements RecipeListAdapter.OnNoteListener{
    private RecipeList recipeList;
    private RecipeListAdapter recipeListAdapter;
    private DailyPlan dailyPlan;
    private DailyPlanAdapter DailyPlanAdapter;
    private RecyclerView recyclerView;
    private ListView listView;
    ArrayList<String> recipeNames;
    ArrayList<Recipe> list;
    String selectedRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mealplan);
        Date date = (Date) this.getIntent().getExtras().get("Date");

        // should get recipe list from database
        dailyPlan = new DailyPlan();
        recipeList = new RecipeList();
        insertTestRecipes();
        recipeListAdapter = new RecipeListAdapter(this, dailyPlan.getDailyPlanRecipes(), null);
        recyclerView = this.findViewById(R.id.recipe_list);
        recyclerView.setAdapter(recipeListAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

//        recipeList = new RecipeList();
//
//        // Firebase, get the arraylist of recipes for the user
//        listView = findViewById(R.id.recipe_listview);

//        list = recipeList.getRecipeList();
//        recipeNames = new ArrayList<>();
//
//        String temp;
//        for (int x = 0; x < recipeList.getSize(); x++){
//            temp = list.get(x).getTitle();
//            recipeNames.add(temp);
//        }
//
//        //recipeNames.add("pizza");
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_add_mealplan, R.id.textView,recipeNames);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedRecipe = (String) listView.getItemAtPosition(i);
//                Toast.makeText(getApplicationContext(), selectedRecipe + " has been added!", Toast.LENGTH_SHORT).show();
//
//                // Add the selected recipe to the daily plan at Date
//                Intent myIntent = new Intent(view.getContext(), DailyPlanActivity.class);
//                myIntent.putExtra("Recipe",date);
//                startActivity(myIntent);
//            }
//        }
//        );


    }

    private void insertTestRecipes() {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "pieces", "Wheat", 2);

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("A", "pieces", "C", 2));
        Recipe recipe = new Recipe("Soup", 10, "none", 2, "Dinner", in2, null);
        dailyPlan.addRecipe(recipe);
        dailyPlan.addIngredient(bread);
    }


    @Override
    public void onNoteClick(int position) {
        Toast.makeText(getApplicationContext(), dailyPlan.getDailyPlanRecipes().get(position).getTitle(), Toast.LENGTH_SHORT).show();
        dailyPlan.addRecipe(dailyPlan.getDailyPlanRecipes().get(position));
    }
}
