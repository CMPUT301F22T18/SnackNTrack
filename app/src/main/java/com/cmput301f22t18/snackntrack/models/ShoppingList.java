package com.cmput301f22t18.snackntrack.models;

import com.cmput301f22t18.snackntrack.DailyPlan;
import com.cmput301f22t18.snackntrack.MealPlan;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientCategoryComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientDescriptionComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.sql.Timestamp;

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
     * This method removes an ingredient from the shoppingList if it exists within the list
     * @param ingredient - ingredient that was purchased
     */
    public void removeIngredient(Ingredient ingredient) {
        // Remove from the shoppingList
        if (shoppingList.contains(ingredient)) {
            shoppingList.remove(ingredient);
        }
    }

    /**
     * This method sorts the ShoppingList based on the key
     * @return sortedShoppingList
     */
    public ArrayList<Ingredient> sort(String key) {
        if (key != "Default") {
            ArrayList<Ingredient> sortedShoppingList = shoppingList;
            Collections.sort(sortedShoppingList, comparators.get(key));
            return sortedShoppingList;
        }
        else {
            return shoppingList;
        }
    }

    /**
     * This method calculates the ingredients the user needs to shop for
     * @param mealPlan - the user's MealPlan
     * @param storage - the user's Storage
     */
    public void calculateList(MealPlan mealPlan, Storage storage) {
        // Clear current shoppingList
        this.shoppingList.clear();
        // Get a list of all ingredients in the user's MealPlan
        ArrayList<Ingredient> neededIngredients = this.calculateNeeded(mealPlan);
        // Get a list of all ingredients in the user's Storage
        ArrayList<Ingredient> storageIngredients = storage.getStorageList();
        // Check if each needed ingredient needs to be shopped for
        boolean shouldBeAdded;
        for (int i = 0; i < neededIngredients.size(); i++) {
            shouldBeAdded = true;
            // Check if the ingredient exists within the storage
            for (int j = 0; j < storageIngredients.size(); j++) {
                if (neededIngredients.get(i).getDescription() == storageIngredients.get(j).getDescription()) {
                    // If it does, check if there is enough
                    if (storageIngredients.get(j).getAmount() >= neededIngredients.get(i).getAmount()) {
                        // Successful find; subtract amount from storageIngredients
                        storageIngredients.get(j).setAmount(storageIngredients.get(j).getAmount() - neededIngredients.get(i).getAmount());
                        shouldBeAdded = false;
                    } else if (storageIngredients.get(j).getAmount() != 0) {
                        // Partially successful find; set amount in storage to zero, and add remainder to shopping list
                        neededIngredients.get(i).setAmount(neededIngredients.get(i).getAmount() - storageIngredients.get(j).getAmount());
                        storageIngredients.get(j).setAmount(0);
                        this.addShoppingIngredient(neededIngredients.get(i));
                        shouldBeAdded = false;
                    }
                }
            }
            if (shouldBeAdded) {
                // Unsuccessful find; add ingredient to shopping list
                this.addShoppingIngredient(neededIngredients.get(i));
            }
        }
    }

    /**
     * This method return an ArrayList of all ingredients the user will need for the next 7 days
     * @param mealPlan - the user's MealPlan
     * @return neededIngredients
     */
    public ArrayList<Ingredient> calculateNeeded(MealPlan mealPlan) {
        ArrayList<Ingredient> neededIngredients = new ArrayList<Ingredient>();
        // These variables will be used for keeping track of current values while iterating through the MealPlan
        int ingredientAmount;
        int recipeAmount;
        DailyPlan currentDailyPlan;
        Recipe currentRecipe;

        // Goes through DailyPlans within the MealPlan
        for (int i = 0; i < mealPlan.getSize(); i++) {
            currentDailyPlan = mealPlan.getDailyPlan().get(i);
            // Before preceding, make sure that the DailyPlan exists
            if (!(Objects.isNull(currentDailyPlan))) {
                // Find the number of ingredients and recipes in the DailyPlan
                ingredientAmount = currentDailyPlan.getDailyPlanIngredients().size();
                recipeAmount = currentDailyPlan.getDailyPlanRecipes().size();
                // Add all ingredients in the DailyPlan to the neededIngredients ArrayList
                for (int j = 0; j < ingredientAmount; j++) {
                    if (currentDailyPlan.getDailyPlanIngredients().get(j) != null && (currentDailyPlan.getDailyPlanIngredients().get(j).getDescription() != null && currentDailyPlan.getDailyPlanIngredients().get(j).getDescription() != null)) {
                        if (currentDailyPlan.getDailyPlanIngredients().get(j).getAmount() > 0) {
                            neededIngredients.add(currentDailyPlan.getDailyPlanIngredients().get(j));
                        }
                    }
                }
                // Check each Recipe in the DailyPlan
                for (int k = 0; k < recipeAmount; k++) {
                    // Get the kth recipe and check how many ingredients it has
                    currentRecipe = currentDailyPlan.getDailyPlanRecipes().get(k);
                    if (currentRecipe.getRecipeIngredients() != null) {
                        ingredientAmount = currentRecipe.getRecipeIngredients().size();
                        // Add all ingredients in the Recipe to the neededIngredients ArrayList
                        for (int l = 0; l < ingredientAmount; l++) {
                            if (currentRecipe.getRecipeIngredients().get(l) != null && (currentRecipe.getRecipeIngredients().get(l).getDescription() != null && currentRecipe.getRecipeIngredients().get(l).getCategory() != null)) {
                                if (currentRecipe.getRecipeIngredients().get(l).getAmount() > 0) {
                                    neededIngredients.add(currentRecipe.getRecipeIngredients().get(l));
                                }
                            }
                        }
                    }
                }
            }
        }
        return neededIngredients;
    }
}
