package com.cmput301f22t18.snackntrack;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represent the entire meal plan, comprised of multiple days
 * @author Areeba Fazal
 */
public class MealPlan {

    private final ArrayList<DailyPlan> dailyPlanList;

    /**
     * This method is the constructor for the MealPlan
     */
    public MealPlan() {
        this.dailyPlanList = new ArrayList<>();
    }

    /**
     * This method gets the full meal plan list from the dailyPlan
     * @return recipeList
     */
    public ArrayList<DailyPlan> getDailyPlan() {
        return this.dailyPlanList;
    }

    /**
     * This method returns the size of the dailyPlan
     * @return int
     */
    public int getSize() {
        return this.dailyPlanList.size();
    }

    /**
     * This method adds a recipe/ingredient into the dailyPLan
     * @param newDailyPlan - a recipe/ingredient to be added
     */
    public void addDailyPlan(DailyPlan newDailyPlan) {
        this.dailyPlanList.add(newDailyPlan);
    }

    /**
     * This method deletes a recipe/ingredient from the dailyPLan
     * @param newDailyPlan - a recipe to be deleted
     */
    public void deleteDailyPlan(DailyPlan newDailyPlan) {
        this.dailyPlanList.remove(newDailyPlan);
    }

    /**
     * This method returns the dailyPlan for a specified day
     * @param newDate - int representing the day
     * @return dailyPlan
     */
    public DailyPlan getDailyPlanAtDay(Date newDate) {
        for (int planIndex = 0; planIndex < dailyPlanList.size(); planIndex++) {
            if (dailyPlanList.get(planIndex).getDate().compareTo(newDate) == 0) {
                return dailyPlanList.get(planIndex);
            }
        }
        return null;

    }


}
