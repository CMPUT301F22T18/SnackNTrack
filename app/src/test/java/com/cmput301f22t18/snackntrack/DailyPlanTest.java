package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Unit tests for DailyPlan class
 * @author Charlotte Kalutycz
 * @version 1
 */
public class DailyPlanTest {

    /**
     * These are a DailyPlan object to be used for testing and a calendar object for creating dates for testing
     */
    private DailyPlan dailyPlan;
    private Calendar calendar = Calendar.getInstance();

    /**
     * Runs before all other tests; creates a DailyPlan object with one ingredient and one recipe
     */
    @Before
    public void createMockDailyPlan() {
        dailyPlan = new DailyPlan();
        // Recipe consists of title, prep time, comments, servings, category, ingredients, and photoURL
        Recipe recipe = new Recipe("Soup", 5, "Heat up soup", 2, "dinner", new ArrayList<Ingredient>(), "photoURL");
        // Ingredient consists of desc, location, unit, category, amount, BBD
        Ingredient ingredient = new Ingredient("Chicken Wings", "Fridge", "wings", "Meat", 1, calendar.getTime());
        dailyPlan.addRecipe(recipe);
        dailyPlan.addIngredient(ingredient);
    }

    /**
     * Checks whether the getDailyPlanRecipes correctly fetches the recipe list from the DailyPlan
     */
    @Test
    public void testGetDailyPlanRecipes() {
        // There is already 1 recipe in the DailyPlan
        assertEquals(1, dailyPlan.getDailyPlanRecipes().size());
        // The recipe in the DailyPlan has the title "Soup"
        assertEquals("Soup", dailyPlan.getDailyPlanRecipes().get(0).getTitle());
    }

    /**
     * Checks whether the getDailyPlanIngredients correctly fetches the ingredient list from the DailyPlan
     */
    @Test
    public void testGetDailyPlanIngredients() {
        // There is already 1 ingredient in the DailyPlan
        assertEquals(1, dailyPlan.getDailyPlanIngredients().size());
        // The recipe in the DailyPlan has the description "Chicken Wings"
        assertEquals("Chicken Wings", dailyPlan.getDailyPlanIngredients().get(0).getDescription());
    }

    /**
     * Adds a recipe to the DailyPlan and checks whether it was successfully added
     */
    @Test
    public void testAddRecipe() {
        int currentSize = dailyPlan.getDailyPlanRecipes().size();
        Recipe recipe = new Recipe("Pasta", 5, "Great for a busy night", 2, "dinner", new ArrayList<Ingredient>(), "photoURL");
        // Adds the new recipe to the arraylist<recipe> inside of the DailyPlan
        dailyPlan.addRecipe(recipe);
        // This arraylist should now have one more recipe
        assertEquals(currentSize + 1, dailyPlan.getDailyPlanRecipes().size());
    }

    /**
     * Adds an ingredient to the DailyPlan and checks whether it was successfully added
     */
    @Test
    public void testAddIngredient() {
        int currentSize = dailyPlan.getDailyPlanIngredients().size();
        Ingredient ingredient = new Ingredient("Bananas", "Pantry", "bunches", "Produce", 1, calendar.getTime());
        // Adds the new ingredient to the arraylist<ingredient> inside of the DailyPlan
        dailyPlan.addIngredient(ingredient);
        // This arraylist should now have one more ingredient
        assertEquals(currentSize + 1, dailyPlan.getDailyPlanIngredients().size());
    }

    /**
     * Deletes a recipe from the DailyPlan, and checks whether it was successfully removed
     */
    @Test
    public void testDeleteRecipe() {
        int currentSize = dailyPlan.getDailyPlanRecipes().size();
        Recipe toast = new Recipe("Toast", 5, "Toast bread and enjoy", 2, "breakfast", new ArrayList<Ingredient>(), "photoURL");
        dailyPlan.addRecipe(toast);
        // Adds new recipe, which should increase the size of the arraylist<recipe> by 1
        assertEquals(currentSize + 1, dailyPlan.getDailyPlanRecipes().size());
        dailyPlan.deleteRecipe(toast);
        // After deleting this recipe, the arraylist<recipe> should be back to its original size
        assertEquals(currentSize, dailyPlan.getDailyPlanRecipes().size());
    }

    /**
     * Deletes an ingredient from the DailyPlan, and checks whether it was successfully removed
     */
    @Test
    public void testDeleteIngredient() {
        int currentSize = dailyPlan.getDailyPlanIngredients().size();
        Ingredient ingredient = new Ingredient("Grapes", "Pantry", "grapes", "Produce", 1, calendar.getTime());
        dailyPlan.addIngredient(ingredient);
        // Adds new ingredient, which should increase the size of the arraylist<ingredient> by 1
        assertEquals(currentSize + 1, dailyPlan.getDailyPlanIngredients().size());
        dailyPlan.deleteIngredient(ingredient);
        // After deleting this ingredient, the arraylist<ingredient> should be back to its original size
        assertEquals(currentSize, dailyPlan.getDailyPlanIngredients().size());
    }

    /**
     * Checks whether the getSize() method updates correctly when both recipes and ingredients are added and deleted
     */
    @Test
    public void testGetSize() {
        int currentSize = dailyPlan.getSize();
        Ingredient ingredient = new Ingredient("Flour", "Pantry", "cups", "Baking", 1, calendar.getTime());
        Recipe recipe = new Recipe("Salad", 5, "Chop veggies then mix", 2, "lunch", new ArrayList<Ingredient>(), "photoURL");
        dailyPlan.addIngredient(ingredient);
        // After adding an ingredient, the size of the DailyPlan should have increased by 1
        assertEquals(currentSize + 1, dailyPlan.getSize());
        dailyPlan.addRecipe(recipe);
        // After adding a recipe, the size of the DailyPlan should have increased by 1
        assertEquals(currentSize + 2, dailyPlan.getSize());
        dailyPlan.deleteIngredient(ingredient);
        // After deleting an ingredient, the size of the DailyPlan should decrease by 1
        assertEquals(currentSize + 1, dailyPlan.getSize());
        dailyPlan.deleteRecipe(recipe);
        // After deleting a recipe, the size of the DailyPlan should decrease by 1
        assertEquals(currentSize, dailyPlan.getSize());

        DailyPlan emptyPlan = new DailyPlan();
        // An empty DailyPlan should by default have a size of 0
        assertEquals(0, emptyPlan.getSize());
    }

    /**
     * Sets the date of the DailyPlan, and checks whether it was correctly set
     */
    @Test
    public void testSetDate() {
        // Gets a date object from the Calendar
        Date date = calendar.getTime();
        dailyPlan.setDate(date);
        // DailyPlan's date attribute should now be equal to this date
        assertEquals(date, dailyPlan.getDate());
    }

    /**
     * Gets the date of the DailyPlan, and checks whether it was correctly fetched
     */
    @Test
    public void testGetDate() {
        // Increases the date by one
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        dailyPlan.setDate(date);
        // getDate() should then return this new date
        assertEquals(date, dailyPlan.getDate());
    }
}
