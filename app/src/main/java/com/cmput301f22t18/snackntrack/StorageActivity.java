package com.cmput301f22t18.snackntrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class StorageActivity extends AppCompatActivity {
    private Storage storage;
    private StorageAdapter storageAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        storage = new Storage();
        initStorage();
        storageAdapter = new StorageAdapter(storage);
        recyclerView = this.findViewById(R.id.storage_recycler_view);
        recyclerView.setAdapter(storageAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));
    }

    private void initStorage() {
        Date today = new Date();
        Ingredient i1 = new Ingredient("Salt",
                "Counter-top","shaker", "Spice", 2, today);
        Ingredient i2 = new Ingredient("Pepper",
                "Cupboard","shaker", "Spice", 1, today);
        Ingredient i3 = new Ingredient("Sugar",
                "Pantry","shaker", "Spice", 3, today);

        storage.addIngredient(i1);
        storage.addIngredient(i2);
        storage.addIngredient(i3);
    }
}