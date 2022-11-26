package com.cmput301f22t18.snackntrack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

/**
 * This class keeps track of ingredients the user needs to purchase for the next week using a shoppingList {@link ArrayList}
 *
 * @author Charlotte Kalutycz
 * @version 1.0.0
 */
public class ShoppingList {

    private final ArrayList<Ingredient> shoppingList;
    // Comparators
    private final IngredientDescriptionComparator idc = new IngredientDescriptionComparator();
    private final IngredientCategoryComparator icc = new IngredientCategoryComparator();
    private final Dictionary<String, IngredientComparator> comparators =
            new Hashtable<String, IngredientComparator>() {{
                put("Description", idc);
                put("Category", icc);
            }};

    /**
     * This method is the constructor for the ShoppingList class
     */
    public ShoppingList() {
        shoppingList = new ArrayList<Ingredient>();
    }

    /**
     * This method supplies the ShoppingList ArrayList
     * @return shoppingList
     */
    public ArrayList<Ingredient> getShoppingList() {
        return shoppingList;
    }

    /**
     * This method adds an ingredient to the shoppingList
     * @param ingredient - ingredient to be added
     */
    public void addShoppingIngredient(Ingredient ingredient) {
        shoppingList.add(ingredient);
    }

    /**
     * This method calculates the ingredients the user needs to shop for
     * @param mealPlan - the user's MealPlan
     * @param storage - the user's Storage
     */
    public void calculateList(MealPlan mealPlan, Storage storage) {
        // Get a list of all ingredients in the user's MealPlan
        ArrayList<Ingredient> neededIngredients = calculateNeeded(mealPlan);
        // Check which of those ingredients is not in the storage
        for (int i = 0; i < neededIngredients.size(); i++) {
            // Check if the ingredient exists within the storage
            if () {
                // If it does, check if there is enough
            }
            else {
                this.addShoppingIngredient(neededIngredients.get(i));
            }
        }
    }

    /**
     * This method return an ArrayList of all ingredients the user will need for the next 7 days
     * @param mealPlan - the user's MealPlan
     * @return neededIngredients
     */
    private ArrayList<Ingredient> calculateNeeded(MealPlan mealPlan) {
        Calendar calendar = Calendar.getInstance();
        ArrayList<Ingredient> neededIngredients = new ArrayList<Ingredient>();
        // These variables will be used for keeping track of current values while iterating through the MealPlan
        Date currentDate;
        int ingredientAmount;
        int recipeAmount;
        DailyPlan currentDailyPlan;
        Recipe currentRecipe;

        // Goes through the next 7 days of the MealPlan including the current date
        for (int i = 0; i < 7; i++) {
            // Gets the current date, then the DailyPlan at this date
            currentDate = calendar.getTime();
            currentDailyPlan = mealPlan.getDailyPlanAtDay(currentDate);
            // Before preceding, make sure that the DailyPlan exists
            if (!(Objects.isNull(currentDailyPlan))) {
                // Find the number of ingredients and recipes in the DailyPlan
                ingredientAmount = currentDailyPlan.getDailyPlanIngredients().size();
                recipeAmount = currentDailyPlan.getDailyPlanRecipes().size();
                // Add all ingredients in the DailyPlan to the neededIngredients ArrayList
                for (int j = 0; j < ingredientAmount; j++) {
                    neededIngredients.add(currentDailyPlan.getDailyPlanIngredients().get(j));
                }
                // Check each Recipe in the DailyPlan
                for (int k = 0; k < recipeAmount; k++) {
                    // Get the kth recipe and check how many ingredients it has
                    currentRecipe = currentDailyPlan.getDailyPlanRecipes().get(k);
                    ingredientAmount = currentRecipe.getRecipeIngredients().size();
                    // Add all ingredients in the Recipe to the neededIngredients ArrayList
                    for (int l = 0; l < ingredientAmount; l++) {
                        neededIngredients.add(currentRecipe.getRecipeIngredients().get(l));
                    }
                }
            }
            // Increments calendar to the next day
            calendar.add(Calendar.DATE, 5);
        }
        return neededIngredients;
    }

    /**
     * This method adds a successfully purchased ingredient to storage and removes it from the shoppingList
     * @param ingredient - ingredient that was purchased
     */
    public void purchased(Ingredient ingredient, Storage storage) {
        // Remove from the shoppingList
        shoppingList.remove(ingredient);
        // Add the ingredient to the Storage
        storage.addIngredient(ingredient);
    }

    /**
     * This method sorts the ShoppingList based on the key
     * @return sortedShoppingList
     */
    public ArrayList<Ingredient> sort(String key) {
        ArrayList<Ingredient> sortedShoppingList = shoppingList;
        Collections.sort(sortedShoppingList, comparators.get(key));
        return sortedShoppingList;
    }
}
