package com.cmput301f22t18.snackntrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Storage;
import com.cmput301f22t18.snackntrack.models.MealPlan;

import android.os.Bundle;

import com.cmput301f22t18.snackntrack.models.ShoppingList;

public class ShoppingListActivity extends AppCompatActivity {
    private ShoppingList shoppingList;
    private ShoppingListAdapter shoppingListAdapter;
    private Storage storage;
    private MealPlan mealPlan;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        storage = new Storage();

        mealPlan = new MealPlan();

        shoppingList = new ShoppingList();
        initShoppingList();

        shoppingListAdapter = new ShoppingListAdapter(shoppingList, storage);
        recyclerView = this.findViewById(R.id.shopping_list_recycler_view);
        recyclerView.setAdapter(shoppingListAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));
    }

    private void initShoppingList() {
        Ingredient carrots = new Ingredient("Carrots", "carrots", "Produce", 5);
        shoppingList.addShoppingIngredient(carrots);
        Ingredient bread = new Ingredient("Bread", "loaves", "Grains", 2);
        shoppingList.addShoppingIngredient(bread);
        shoppingList.calculateList(mealPlan, storage);
    }
}