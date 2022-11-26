package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;

import java.util.ArrayList;
import java.util.Date;

public class AddEditMealPlanActivity extends Fragment implements RecipeListAdapter.OnNoteListener{
    private RecipeList recipeList;
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private ListView listView;
    ArrayList<String> recipeNames;
    ArrayList<Recipe> list;
    String selectedRecipe;
    Date date;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_mealplan, container, false);
        Bundle dateBundle = getArguments();
        date = (Date) dateBundle.getSerializable("Date");
        // should get recipe list from database
        recipeList = new RecipeList();
        insertTestRecipes();
        recipeListAdapter = new RecipeListAdapter(this.getContext(), recipeList.getRecipeList(), this);
        recyclerView = v.findViewById(R.id.recipe_list);
        recyclerView.setAdapter(recipeListAdapter);
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
        DailyPlanActivity dailyPlanActivity = new DailyPlanActivity();
        Bundle dateBundle = new Bundle();
        dateBundle.putSerializable("Date",date);
        dailyPlanActivity.setArguments(dateBundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, dailyPlanActivity);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
