package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MealPlanActivity extends Fragment {

    private CalendarView cal;
    private int date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_meal_plan, container, false);

        cal = v.findViewById(R.id.calendarView);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    String temp = String.valueOf(year) +"/" +String.valueOf(month+1) +"/"+String.valueOf(dayOfMonth);
                    date = formatter.parse(temp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT).show();
                DailyPlanActivity dailyPlanActivity = new DailyPlanActivity();
                Bundle dateBundle = new Bundle();
                dateBundle.putSerializable("Date",date);
                dailyPlanActivity.setArguments(dateBundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, dailyPlanActivity);
                transaction.addToBackStack(null);
                transaction.commit();

            }

        });
        return v;
    }

}






//    private MealPlan mealPlan;
//    private MealPlanAdapter mealPlanAdapter;
//    private RecyclerView recyclerView;
//    private FloatingActionButton fab;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_meal_plan);
//
//
//        mealPlan = new MealPlan();
//        insertTestRecipes();
//        mealPlanAdapter = new MealPlanAdapter(this, mealPlan.getDailyPlan(), null);
//        recyclerView = this.findViewById(R.id.recipe_list_recycler_view);
//        recyclerView.setAdapter(mealPlanAdapter);
//        recyclerView.setLayoutManager(
//                new LinearLayoutManager(getApplicationContext()));
//
//        fab = findViewById(R.id.add_meal_plan_fab);
//        fab.show();
//        fab.setOnClickListener(view -> {
//            if (savedInstanceState == null) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("mealPlan", (Serializable) mealPlan);
//
//                recyclerView.setVisibility(View.GONE);
//                getSupportFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
//                        .add(R.id.fragment_container_view, AddRecipeFragment.class, bundle)
//                        .addToBackStack("AddMealPlan")
//                        .commit();
//
//            }
//        });
//
//
//    }
//
//    private void insertTestRecipes() {
//        ArrayList<Ingredient> in1 = new ArrayList<Ingredient>();
//        Ingredient bread = new Ingredient("Bread", "pieces", "Wheat", 2);
//        DailyPlan dailyPlan = new DailyPlan();
//
//
//        ArrayList<Ingredient> in2 = new ArrayList<Ingredient>();
//        in2.add(new Ingredient("A", "pieces", "C", 2));
//        Recipe recipe = new Recipe("Soup", 10, "none", 2, "Dinner", in2, null);
//        dailyPlan.addRecipe(recipe);
//        dailyPlan.addIngredient(bread);
//        mealPlan.addDailyPlan(dailyPlan);
//
//    }


