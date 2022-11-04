package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Unit tests for RecipeList class
 * @author Purnapushkala Hariharan
 * @version 1
 */
public class RecipeListTest{
    private RecipeList recipeList;
    /**
     * Runs before all test cases; creates a mock RecipeList object with 1 Recipe
     */
    @Before
    public void createMockRecipeList() {
        recipeList = new RecipeList();
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        recipeList.addRecipe(mockRecipe());
    }
    public Recipe mockRecipe() {
        Calendar c = Calendar.getInstance();
        c.set(2024, 11, 25);
        Recipe recipe = new Recipe(
                "Fried Rice",
                2, "I don't like peas in it", 2,
                "breakfast", new ArrayList<Ingredient>(),
                "https://unsplash.com/photos/rQX9eVpSFz8");
        // Recipe List consists of arraylist of recipes which is a array list of ingredients
        recipe.addIngredient(new Ingredient(
                "Carrots", "Fridge", "pieces", "Produce",
                3, c.getTime()));
        return recipe;
    }

    /**
     * Get ArrayList from RecipeList object and check if contents of list are correct.
     */
    @Test
    public void testGetRecipeList() {
        assertEquals(1, recipeList.getSize()); // True if 2 items in list
        assertEquals("Fried Rice", recipeList.getRecipeList().get(0).getTitle());
        // True if "Spam" is description of 1st item in list
    }

    /**
     * Add an recipe to recipeList and check if successfully in recipeList
     */
    @Test
    public void testAddRecipe() {
        assertEquals(1, recipeList.getRecipeList().size());
        Recipe recipe = new Recipe("Pancakes",
                2,
                "I don't like it too fried",
                2,
                "breakfast", new ArrayList<Ingredient>(),
                "https://unsplash.com/photos/gf9777gaYjs");
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        recipe.addIngredient(
                new Ingredient("Potatoes", "Fridge",
                        "pieces", "Produce", 3,
                        new Date(2024-1900, 11, 27)));
        recipeList.addRecipe(recipe);
        assertEquals(2, recipeList.getRecipeList().size());
        assertTrue(recipeList.getRecipeList().contains(recipe));
    }

    /**
     * Delete an recipe to recipeList if present and check if successfully in recipeList
     */
    @Test
    public void testDeleteRecipe(){
        Recipe recipe = new Recipe("Toast",
                2,
                "I don't like it too fried",
                2,
                "lunch", new ArrayList<Ingredient>(),
                "https://unsplash.com/photos/gf9777gaYjs");
   
        // Recipe List consists of arraylist of recipes which is a array list of ingredients

        recipe.addIngredient(
                new Ingredient("Bread", "Pantry", "pieces",
                        "Bakery", 2, new Date(2024-1900, 11, 27)));
        recipeList.addRecipe(recipe);
        Recipe recipe1 = new Recipe("Sandwich",
                2,
                "I don't like it too fried",
                2,
                "lunch", new ArrayList<Ingredient>(),
                "https://unsplash.com/photos/U0PiIS4Uvkc");
        recipe1.addIngredient(new Ingredient(
                "Bread", "Pantry",
                "pieces", "Bakery", 2,
                new Date(2024-1900, 11, 27)));
        recipeList.deleteRecipe(recipe1);
        assertEquals(recipeList.getSize(), 2);
        recipeList.deleteRecipe(recipe);// Existing recipe
        assertEquals(recipeList.getSize(), 1);
    }

    @Test
    public void testSort() {
        // TODO: sort Recipes based on title, category, number of servings, preparation time
    }

}