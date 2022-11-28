package com.cmput301f22t18.snackntrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MealPlanActivity extends Fragment {

    private CalendarView cal;
    private MealPlan mealplan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_meal_plan, container, false);

        cal = v.findViewById(R.id.calendarView);

        // when a date on calender is pressed
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                // get date pressed
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
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

                //firebase, get meal plan for user, then get daily plan based on date
                Timestamp myTimestamp = new Timestamp(date);
                Log.w("myTIME: ", myTimestamp.toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert(user != null);
                String uid = user.getUid();
                ArrayList<String> dailyPlanId = new ArrayList<>();
                Log.d("TRY", "THIS IS A TEST");

                CollectionReference cf = db.collection("mealPlans")
                        .document(uid).collection("mealPlanList");

                cf.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("Storage", "Listen failed.", error);
                        return;
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        Log.w("DATE: ", doc.get("date").toString(), error);
                        if (myTimestamp.getSeconds() == ((Timestamp) doc.get("date")).getSeconds()){
                            Log.w("FOUND: ", doc.get("date").toString(), error);
                            dailyPlanId.add(doc.getId());
                            Log.w("ID: ", dailyPlanId.get(0), error);

                            Log.w("ID2: ", dailyPlanId.get(0));
                            dateBundle.putSerializable("id",dailyPlanId.get(0));
                            dailyPlanActivity.setArguments(dateBundle);

                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container_main, dailyPlanActivity);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                    }
                });


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


