package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Unit tests for MealPlan class
 * @author Charlotte Kalutycz
 * @version 1
 */
public class MealPlanTest {
    /**
     * These are a MealPlan object to be used for testing and a Calendar to be used for creating test dates
     */
    private MealPlan mealPlan;
    private Calendar calendar = Calendar.getInstance();

    /**
     * Runs before all other tests; creates a MealPlan object with one DailyPlan
     */
    @Before
    public void createMockMealPlan() {
        DailyPlan dailyPlan = new DailyPlan();
        dailyPlan.setDate(calendar.getTime());
        mealPlan = new MealPlan();
        mealPlan.addDailyPlan(dailyPlan);
    }

    /**
     * Checks whether the getDailyPlan method successfully retrieves the list of DailyPlans from the MealPlan
     */
    @Test
    public void testGetDailyPlan() {
        // There is already 1 DailyPlan in the MealPlan
        assertEquals(1, mealPlan.getDailyPlan().size());
        // The DailyPlan in the MealPlan has the current date from the Calendar
        assertEquals(calendar.getTime(), mealPlan.getDailyPlan().get(0).getDate());
    }

    /**
     * Checks whether the getSize method correctly retrieves the number of DailyPlans in the MealPlan
     */
    @Test
    public void testGetSize() {
        // The size of the MealPlan should equal the size of the DailyPlan arraylist
        assertEquals(mealPlan.getDailyPlan().size(), mealPlan.getSize());
    }

    /**
     * Adds a DailyPlan to the DailyPlanList in MealPlan, and checks if it was successfully added
     */
    @Test
    public void testAddDailyPlan() {
        int currentSize = mealPlan.getSize();
        DailyPlan dailyPlan = new DailyPlan();
        // Adds a new DailyPlan to the MealPlan
        mealPlan.addDailyPlan(dailyPlan);
        // The MealPlan should now have one more DailyPlan
        assertEquals(currentSize + 1, mealPlan.getSize());
    }

    /**
     * Adds then deletes a DailyPlan from the MealPlan, and checks if it was successfully removed
     */
    @Test
    public void testDeleteDailyPlan() {
        int currentSize = mealPlan.getSize();
        DailyPlan dailyPlan = new DailyPlan();
        mealPlan.addDailyPlan(dailyPlan);
        // Adds the new DailyPlan, which should increase the size of the MealPlan by 1
        assertEquals(currentSize + 1, mealPlan.getSize());
        mealPlan.deleteDailyPlan(dailyPlan);
        // Deletes the new DailyPlan, which should decrease the size of the MealPlan by 1
        assertEquals(currentSize, mealPlan.getSize());
    }

    /**
     * Attempts to retrieve a DailyPlan at a specific date
     */
    @Test
    public void testGetDailyPlanAtDay() {
        // Changes the calendar date by 5 days
        calendar.add(Calendar.DATE, 5);
        Date newDate = calendar.getTime();
        // Creates a new DailyPlan at this new date
        DailyPlan testPlan = new DailyPlan();
        testPlan.setDate(calendar.getTime());
        // Adds the testPlan to the MealPlan
        mealPlan.addDailyPlan(testPlan);
        // getDailyPlanAtDay should return testPlan when given the new date variable as a parameter
        assertEquals(testPlan, mealPlan.getDailyPlanAtDay(newDate));
    }

}
