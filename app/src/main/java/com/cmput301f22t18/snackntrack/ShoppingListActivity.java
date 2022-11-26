package com.cmput301f22t18.snackntrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShoppingListActivity extends AppCompatActivity {
    private ShoppingList shoppingList;
    private ShoppingListAdapter shoppingListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingList = new ShoppingList();
        initShoppingList();

        shoppingListAdapter = new ShoppingListAdapter(shoppingList);
        recyclerView = this.findViewById(R.id.shopping_list_recycler_view);
        recyclerView.setAdapter(shoppingListAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));
    }

    private String TAG = "DEBUG";

    private void initShoppingList() {
        Ingredient carrots = new Ingredient("Carrots", "carrots", "Produce", 5);
        shoppingList.addShoppingIngredient(carrots);
        Ingredient bread = new Ingredient("Bread", "loaves", "Grains", 2);
        shoppingList.addShoppingIngredient(bread);
    }
}