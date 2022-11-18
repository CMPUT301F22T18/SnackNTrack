package com.cmput301f22t18.snackntrack;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represent a meal plan for a single day
 * @author Areeba Fazal
 * @version 1.0.0
 */
public class DailyPlan {
    private final ArrayList<Ingredient> dailyPlanIngredients;
    private final ArrayList<Recipe> dailyPlanRecipes;
    private Date date;

    /**
     * This method is the constructor for the DailyPlan
     * @since 1.0.0
     */
    public DailyPlan() {
        this.dailyPlanIngredients = new ArrayList<Ingredient>();
        this.dailyPlanRecipes = new ArrayList<Recipe>();

    }

    /**
     * This method gets the full ingredients meal plan list from the dailyPlan
     * @return recipeList
     * @since 1.0.0
     */
    public ArrayList<Ingredient> getDailyPlanIngredients() {
        return this.dailyPlanIngredients;
    }

    /**
     * This method gets the full recipes meal plan list from the dailyPlan
     * @return recipeList
     * @since 1.0.0
     */
    public ArrayList<Recipe> getDailyPlanRecipes() {
        return this.dailyPlanRecipes;
    }

    /**
     * This method returns the total size of the dailyPlan
     * @return int
     * @since 1.0.0
     */
    public int getSize() {
        return this.dailyPlanIngredients.size() + this.dailyPlanRecipes.size();
    }


    /**
     * This method adds an ingredient into the dailyPLan
     * @param ingredient - a ingredient to be added
     * @since 1.0.0
     */
    public void addIngredient(Ingredient ingredient) {
        this.dailyPlanIngredients.add(ingredient);
    }

    /**
     * This method adds a recipe into the dailyPLan
     * @param recipe - a recipe to be added
     * @since 1.0.0
     */
    public void addRecipe(Recipe recipe) {
        this.dailyPlanRecipes.add(recipe);
    }

    /**
     * This method deletes a recipe/ingredient from the dailyPLan
     * @param ingredient - a recipe to be deleted
     * @since 1.0.0
     */
    public void deleteIngredient(Ingredient ingredient) {
        this.dailyPlanIngredients.remove(ingredient);
    }

    /**
     * This method deletes a recipe/ingredient from the dailyPLan
     * @param recipe - a recipe to be deleted
     * @since 1.0.0
     */
    public void deleteRecipe(Recipe recipe) {
        this.dailyPlanRecipes.remove(recipe);
    }

    /**
     * This method returns the day associated with this daily meal plan
     * @return day - date representing the day
     * @since 1.0.0
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method sets the day associated with this daily meal plan
     * @param date - date representing the day
     * @since 1.0.0
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
