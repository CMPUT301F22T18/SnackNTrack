package com.cmput301f22t18.snackntrack;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.cmput301f22t18.snackntrack.models.Ingredient;
import com.cmput301f22t18.snackntrack.models.Recipe;
import com.cmput301f22t18.snackntrack.models.ShoppingList;
import com.cmput301f22t18.snackntrack.models.Storage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Unit tests for ShoppingList class
 * @author Charlotte Kalutycz
 * @version 1
 */
public class ShoppingListTest {
    /**
     * These are a ShoppingList object to be used for testing and a calendar object for creating dates for testing
     */
    private ShoppingList shoppingList;
    private Calendar calendar = Calendar.getInstance();

    /**
     * Runs before all other tests; creates a ShoppingList object with one ingredient
     */
    @Before
    public void createMockShoppingList() {
        shoppingList = new ShoppingList();
        // Ingredient consists of desc, location, unit, category, amount, BBD
        Ingredient ingredient = new Ingredient("Chicken Wings", "wings", "Meat", 1);
        shoppingList.addShoppingIngredient(ingredient);
    }

    /**
     * Checks whether the getShoppingList method successfully retrieves the shopping list arraylist
     */
    @Test
    public void testGetShoppingList() {
        assertEquals("Chicken Wings", shoppingList.getShoppingList().get(0).getDescription());
        assertEquals(1, shoppingList.getShoppingList().size());
    }

    /**
     * Checks whether the addShoppingIngredient method successfully adds an ingredient to the shopping list
     */
    @Test
    public void testAddShoppingIngredient() {
        Ingredient mockIngredient = new Ingredient("Milk", "Litres", "Dairy", 4);
        shoppingList.addShoppingIngredient(mockIngredient);
        assertEquals(2, shoppingList.getShoppingList().size());
        assertEquals(mockIngredient, shoppingList.getShoppingList().get(1));
    }

    /**
     * Checks whether the removeIngredient method successfully removes an existing ingredient
     * from the shopping list, and does nothing when the ingredient does not exist
     */
    @Test
    public void testRemoveIngredient() {
        int originalSize = shoppingList.getShoppingList().size();
        Ingredient mockIngredient = new Ingredient("Cabbage", "Heads", "Produce", 3);
        // Add new ingredient then delete said ingredient
        shoppingList.addShoppingIngredient(mockIngredient);
        shoppingList.removeIngredient(mockIngredient);
        assertEquals(originalSize, shoppingList.getShoppingList().size());
        Ingredient failIngredient = new Ingredient("Lettuce", "Heads", "Produce", 2);
        // Try to delete ingredient that does not exist within the shopping list
        shoppingList.removeIngredient(failIngredient);
        assertEquals(originalSize, shoppingList.getShoppingList().size());
    }

    /**
     * Checks whether the sort method successfully sorts the shopping list
     */
    @Test
    public void testSort() {
        // Empty shopping list for this test
        shoppingList = new ShoppingList();
        // Create three ingredients with A, B, and C descriptions
        Ingredient aIngredient = new Ingredient("Apples", "Units", "Produce", 2);
        Ingredient bIngredient = new Ingredient("Beef", "Grams", "Meat", 500);
        Ingredient cIngredient = new Ingredient("Cheese", "Grams", "Dairy", 1000);
        // Add to the shopping list in non-alphabetical order
        shoppingList.addShoppingIngredient(cIngredient);
        shoppingList.addShoppingIngredient(aIngredient);
        shoppingList.addShoppingIngredient(bIngredient);
        assertEquals("Cheese", shoppingList.getShoppingList().get(0).getDescription());
        // Sort by Description
        ArrayList<Ingredient> sortedList = shoppingList.sort("Description");
        assertEquals("Apples", sortedList.get(0).getDescription());

        // Empty shopping list for this test
        shoppingList = new ShoppingList();
        // Add the ingredients from before with their categories in non-alphabetical order
        shoppingList.addShoppingIngredient(aIngredient);
        shoppingList.addShoppingIngredient(cIngredient);
        shoppingList.addShoppingIngredient(bIngredient);
        assertEquals("Produce", shoppingList.getShoppingList().get(0).getCategory());
        // Sort by Category
        sortedList = shoppingList.sort("Category");
        assertEquals("Dairy", sortedList.get(0).getCategory());
    }

    /**
     * Checks whether the calculateNeeded method correctly calculates the list of needed
     * ingredients based off a given MealPlan
     */
    @Test
    public void testCalculateNeeded() {
        // Create a new empty MealPlan
        MealPlan mealPlan = new MealPlan();
        // Zero ingredients should be added the neededIngredients array list
        ArrayList<Ingredient> needed = shoppingList.calculateNeeded(mealPlan);
        assertEquals(0, needed.size());

        // Add one empty DailyPlan to the MealPlan
        DailyPlan dailyPlan1 = new DailyPlan();
        mealPlan.addDailyPlan(dailyPlan1);
        // Zero ingredients should be added to neededIngredients
        needed = shoppingList.calculateNeeded(mealPlan);
        assertEquals(0, needed.size());

        // Add one DailyPlan to the MealPlan with one Ingredient
        mealPlan = new MealPlan();
        dailyPlan1 = new DailyPlan();
        dailyPlan1.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        mealPlan.addDailyPlan(dailyPlan1);
        // calculateNeeded should add this one ingredient to the list
        needed = shoppingList.calculateNeeded(mealPlan);
        assertEquals(1, needed.size());
        assertEquals("Bananas", needed.get(0).getDescription());

        // Add another DailyPlan to the MealPlan, this time with one Recipe with 3 Ingredients
        ArrayList<Ingredient> ingredientList1 = new ArrayList<Ingredient>();
        ingredientList1.add(new Ingredient("Bread", "Slices", "Grains", 2));
        ingredientList1.add(new Ingredient("Peanut Butter", "Tablespoons", "Condiments", 2));
        ingredientList1.add(new Ingredient("Jam", "Tablespoons", "Condiments", 1));
        Recipe sandwich = new Recipe("PB&J",
                5, "1. Spread Peanut Butter on Bread. 2. Spread Jam on Bread. 3. Enjoy.", 1,
                "lunch", ingredientList1,
                "https://unsplash.com/photos/rQX9eVpSFz8");
        DailyPlan dailyPlan2 = new DailyPlan();
        dailyPlan2.addRecipe(sandwich);
        mealPlan.addDailyPlan(dailyPlan2);
        // calculateNeeded should add 4 Ingredients to the list (one from dp1 and 3 from the Recipe in dp2)
        needed = shoppingList.calculateNeeded(mealPlan);
        assertEquals(4, needed.size());
        assertEquals("Bread", needed.get(1).getDescription());

        // Add one more DailyPlan to the MealPlan, this time with one Ingredient and one Recipe with 2 Ingredients
        ArrayList<Ingredient> ingredientList2 = new ArrayList<Ingredient>();
        ingredientList2.add(new Ingredient("Tortilla", "Units", "Grains", 2));
        ingredientList2.add(new Ingredient("Cheese", "Grams", "Grains", 20));
        Recipe quesadilla = new Recipe("Cheese Quesadilla",
                5, "1. Add cheese to tortillas. 2. Bake in the oven. 3. Enjoy.", 1,
                "lunch", ingredientList2,
                "https://unsplash.com/photos/rQX9eVpSFz8");
        DailyPlan dailyPlan3 = new DailyPlan();
        dailyPlan3.addRecipe(quesadilla);
        dailyPlan3.addIngredient(new Ingredient("Cinnamon", "Grams", "Seasoning", 50));
        mealPlan.addDailyPlan(dailyPlan3);
        // calculateNeeded should add 7 Ingredients to the list (one from dp1, 3 from dp2, and 3 from dp3)
        needed = shoppingList.calculateNeeded(mealPlan);
        assertEquals(7, needed.size());
        assertEquals("Cinnamon", needed.get(4).getDescription());
    }

    /**
     * Checks whether the calculateList method correctly calculates the shopping list based off a
     * given MealPlan and a given Storage
     */
    @Test
    public void testCalculateList() {

        // Empty the shopping list
        shoppingList = new ShoppingList();

        // Create an empty Storage and an empty MealPlan
        Storage storage = new Storage();
        MealPlan mealPlan = new MealPlan();
        // shoppingList should not have any ingredients in it
        shoppingList.calculateList(mealPlan, storage);
        assertEquals(0, shoppingList.getShoppingList().size());

        // Add one DailyPlan with one Ingredient to the MealPlan (Storage still empty)
        DailyPlan dailyPlan1 = new DailyPlan();
        dailyPlan1.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        mealPlan.addDailyPlan(dailyPlan1);
        // ShoppingList should calculate that it needs this one ingredient
        shoppingList.calculateList(mealPlan, storage);
        assertEquals(1, shoppingList.getShoppingList().size());
        assertEquals("Bananas", shoppingList.getShoppingList().get(0).getDescription());

        // Add one Ingredient to the Storage (MealPlan is empty)
        mealPlan = new MealPlan();
        storage = new Storage();
        storage.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        // ShoppingList should calculate that it needs zero Ingredients
        shoppingList.calculateList(mealPlan, storage);
        assertEquals(0, shoppingList.getShoppingList().size());

        // Add the same Ingredient to the MealPlan and the Storage
        storage = new Storage();
        mealPlan = new MealPlan();
        dailyPlan1 = new DailyPlan();
        dailyPlan1.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        mealPlan.addDailyPlan(dailyPlan1);
        storage.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        // ShoppingList should calculate that it needs zero Ingredients
        shoppingList.calculateList(mealPlan, storage);
        assertEquals(0, shoppingList.getShoppingList().size());


        // Add different Ingredients to the MealPlan and the Storage
        storage = new Storage();
        mealPlan = new MealPlan();
        dailyPlan1 = new DailyPlan();
        dailyPlan1.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        mealPlan.addDailyPlan(dailyPlan1);
        storage.addIngredient(new Ingredient("Apples", "Units", "Produce", 2));
        // ShoppingList should calculate that it needs one Ingredient (the bananas)
        shoppingList.calculateList(mealPlan, storage);
        assertEquals(1, shoppingList.getShoppingList().size());
        assertEquals("Bananas", shoppingList.getShoppingList().get(0).getDescription());

        // Add 2 DailyPlans to the MealPlan with the same Ingredient, and add one copy of the Ingredient to the Storage
        storage = new Storage();
        mealPlan = new MealPlan();
        dailyPlan1 = new DailyPlan();
        dailyPlan1.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        DailyPlan dailyPlan2 = new DailyPlan();
        dailyPlan2.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        mealPlan.addDailyPlan(dailyPlan1);
        mealPlan.addDailyPlan(dailyPlan2);
        storage.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        // ShoppingList should calculate that it needs one of this ingredient
        shoppingList.calculateList(mealPlan, storage);
        assertEquals(1, shoppingList.getShoppingList().size());
        assertEquals("Bananas", shoppingList.getShoppingList().get(0).getDescription());

        // Add same ingredient to both Storage and the MealPlan, but in different amounts
        storage = new Storage();
        mealPlan = new MealPlan();
        dailyPlan1 = new DailyPlan();
        dailyPlan1.addIngredient(new Ingredient("Bananas", "Units", "Produce", 2));
        mealPlan.addDailyPlan(dailyPlan1);
        storage.addIngredient(new Ingredient("Bananas", "Units", "Produce", 1));
        // ShoppingList should calculate that it needs one Ingredient (1 banana)
        shoppingList.calculateList(mealPlan, storage);
        assertEquals(1, shoppingList.getShoppingList().size());
        assertEquals("Bananas", shoppingList.getShoppingList().get(0).getDescription());
        assertEquals(1, shoppingList.getShoppingList().get(0).getAmount());
    }

}
