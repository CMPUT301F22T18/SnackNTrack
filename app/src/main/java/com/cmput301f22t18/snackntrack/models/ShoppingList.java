package com.cmput301f22t18.snackntrack.models;

import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientCategoryComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientDescriptionComparator;

import java.io.Serializable;
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
    // Issue: ingredients not mutable, so will change ingredients in the Storage and MealPlan while calculating List
    public void calculateList(MealPlan mealPlan, Storage storage) {
        // Get a list of all ingredients in the user's MealPlan
        ArrayList<Ingredient> neededIngredients = calculateNeeded(mealPlan);
        // Get a list of all ingredients in the user's Storage (Needs to be a copy, so the original list is not altered)
        ArrayList<Ingredient> storageIngredients = new ArrayList<Ingredient>(storage.getStorageList());
        // Check if each needed ingredient needs to be shopped for
        for (int i = 0; i < neededIngredients.size(); i++) {
            // Check if the ingredient exists within the storage
            for (int j = 0; j < storageIngredients.size(); j++) {
                if (Objects.equals(neededIngredients.get(i).getDescription(), storageIngredients.get(j).getDescription())) {
                    // If it does, check if there is enough
                    if (storageIngredients.get(j).getAmount() >= neededIngredients.get(i).getAmount()) {
                        // Successful find; subtract amount from storageIngredients
                        storageIngredients.get(j).setAmount(storageIngredients.get(j).getAmount() - neededIngredients.get(i).getAmount());
                    }
                    else {
                        // Partially successful find; set amount in storage to zero, and add remainder to shopping list
                        neededIngredients.get(i).setAmount(neededIngredients.get(i).getAmount() - storageIngredients.get(j).getAmount());
                        storageIngredients.get(j).setAmount(0);
                        this.addShoppingIngredient(neededIngredients.get(i));
                    }
                }
                else {
                    // Unsuccessful find; add ingredient in its entirety to shopping list
                    this.addShoppingIngredient(neededIngredients.get(i));
                }
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
     * This method removes a successfully purchased ingredient from the shoppingList
     * @param ingredient - ingredient that was purchased
     */
    public void removeIngredient(Ingredient ingredient) {
        // Remove from the shoppingList
        shoppingList.remove(ingredient);
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
