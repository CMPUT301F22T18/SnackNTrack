package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.controllers.StorageAdapter;
import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.RecipeList;
import com.cmput301f22t18.snackntrack.models.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddIngredientToMealPlan extends Fragment implements StorageAdapter.OnItemLongClickListener {
    private Storage storage;
    private StorageAdapter storageAdapter;
    private RecyclerView recyclerView;
    Ingredient selectedIngredient;
    private ArrayList<String> unit_list, location_list, category_list;
    Date date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_mealplan, container, false);
        Bundle dateBundle = getArguments();
        date = (Date) dateBundle.getSerializable("Date");
        // should get recipe list from database
        storage = new Storage();

        unit_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.units_array)));
        location_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_locations_array)));
        category_list = new ArrayList<>
                (Arrays.asList(getResources().getStringArray(R.array.ingredient_categories_array)));

        // DOES NOT WORK. error from storage adapter
        FragmentManager Manager = getParentFragmentManager();
        storageAdapter = new StorageAdapter(storage, Manager, this,
                unit_list, location_list, category_list);
        recyclerView = v.findViewById(R.id.meal_plan_add_recycler_view);
        recyclerView.setAdapter(storageAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this.getContext()));
        insertTestStorage();

        return v;
    }

    private void insertTestStorage() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = formatter.parse("2022/11/03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
        Ingredient bread = new Ingredient("Bread", "Fridge", "pack", "Bakery", 2, date);

        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
        in2.add(new Ingredient("Ace", "pieces", "C", 2));
        storage.addIngredient(bread);
    }

    @Override
    public void onClick(Ingredient item) {
        Toast.makeText(this.getContext(), item.getDescription(), Toast.LENGTH_SHORT).show();
        selectedIngredient = item;
        DailyPlanActivity dailyPlanActivity = new DailyPlanActivity();
        Bundle dateBundle = new Bundle();
        dateBundle.putSerializable("Date",date);
        dateBundle.putSerializable("Ingredient",selectedIngredient);
        dailyPlanActivity.setArguments(dateBundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, dailyPlanActivity);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
