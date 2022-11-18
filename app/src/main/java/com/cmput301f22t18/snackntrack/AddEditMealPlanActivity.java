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

public class AddEditMealPlanActivity extends AppCompatActivity {
    private RecipeList recipeList;
    private RecipeListAdapter recipeListAdapter;
    private ListView listView;
    ArrayList<String> recipeNames;
    ArrayList<Recipe> list;
    String selectedRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mealplan);
        Date date = (Date) this.getIntent().getExtras().get("Date");

        recipeList = new RecipeList();

        // Firebase, get the arraylist of recipes for the user
        listView = findViewById(R.id.recipe_listview);
        insertTestRecipes();
        list = recipeList.getRecipeList();
        recipeNames = new ArrayList<>();

        String temp;
        for (int x = 0; x < recipeList.getSize(); x++){
            temp = list.get(x).getTitle();
            recipeNames.add(temp);
        }

        //recipeNames.add("pizza");

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_add_mealplan, R.id.textView,recipeNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRecipe = (String) listView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), selectedRecipe + " has been added!", Toast.LENGTH_SHORT).show();

                // Add the selected recipe to the daily plan at Date
                Intent myIntent = new Intent(view.getContext(), DailyPlanActivity.class);
                myIntent.putExtra("Recipe",date);
                startActivity(myIntent);
            }
        }
        );





    }

    private void insertTestRecipes() {
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        in1.add(new Ingredient("Bread", "pieces", "Wheat", 2));
        recipeList.addRecipe(new Recipe("Sandwich", 5, "none", 1, "Lunch", in1, null));

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("A", "pieces", "C", 2));
        recipeList.addRecipe(new Recipe("Soup", 10, "none", 2, "Dinner", in2, null));

    }





}
