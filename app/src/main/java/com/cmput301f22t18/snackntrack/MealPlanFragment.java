package com.cmput301f22t18.snackntrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealPlanFragment extends Fragment {

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
                DailyPlanFragment dailyPlanFragment = new DailyPlanFragment();
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


                CollectionReference cf = db.collection("mealPlans")
                        .document(uid).collection("mealPlanList");

                cf.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("Storage", "Listen failed.", error);
                        return;
                    }
                    //Find daily plan that matches date pressed
                    for (QueryDocumentSnapshot doc : value) {
                        Log.w("DATE: ", doc.get("date").toString(), error);
                        if (myTimestamp.getSeconds() == ((Timestamp) doc.get("date")).getSeconds()){
                            Log.w("FOUND: ", doc.get("date").toString(), error);
                            dailyPlanId.add(doc.getId());
                            Log.w("ID: ", dailyPlanId.get(0), error);

                            // get id of that daily plan
                            dateBundle.putSerializable("id",dailyPlanId.get(0));
                            dailyPlanFragment.setArguments(dateBundle);

                            // send to daily plan activity
                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container_main, dailyPlanFragment);
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
