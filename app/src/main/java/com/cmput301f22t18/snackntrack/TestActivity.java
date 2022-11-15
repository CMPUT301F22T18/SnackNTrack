package com.cmput301f22t18.snackntrack;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cmput301f22t18.snackntrack.databinding.ActivityTestBinding;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding binding;
    RecipeList recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Ingredient> in1 = new ArrayList<>();
        in1.add(new Ingredient("Bread", "pieces", "Wheat", 2));
        recipeList.addRecipe(new Recipe("Sandwich", 5, "none", 1, "Lunch", in1, null));

        ArrayList<Ingredient> in2 = new ArrayList<>();
        in2.add(new Ingredient("A", "pieces", "C", 2));
        recipeList.addRecipe(new Recipe("Soup", 10, "none", 2, "Dinner", in2, null));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, RecipeListFragment.newInstance(recipeList)).commit();
    }

}