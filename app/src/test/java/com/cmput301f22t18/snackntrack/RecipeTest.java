package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

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
public class RecipeTest {
    private Recipe recipe;
    /**
     * Runs before all test cases; creates a mock RecipeList object with 1 Recipe
     */
    @Before
    public Recipe createMockRecipe() {
        Recipe recipe = new Recipe();
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        recipe.addIngredient(new Ingredient("Carrots", "Fridge", "pieces", "Produce", 3, new Date(2024-1900, 11, 27)));
        recipe.setRecipe("Fried Rice",2,2,"breakfast","I don't like peas in it", recipe.getRecipeIngredients()); // How to test fro photo
        return recipe;
    }
    /**
     * Add an ingredient to recipe and check if successfully in recipe
     */
    @Test
    void testAddIngrediants() {
        // Add a new Ingredient to storage (note that storage already has 1 Ingredient)
        Ingredient newIngredient = new Ingredient("Potato", "Pantry", "pieces", "Produce", 3, new Date(2023-1900, 7, 27));
        recipe.addIngredient(newIngredient);
        ArrayList<Ingredient> list = recipe.getRecipeIngredients(); // Get the ArrayList inside storage
        assertEquals(2, list.size()); // True if there are 2 items inside list
        assertEquals(list.get(1), newIngredient); // True if second ingredient in storage is newIngredient
    }

    /**
     * Get ArrayList from Recipe object and check if contents of list are correct
     */
    @Test
    public void testGetRecipeIngrediants() {
        Ingredient newIngredient = new Ingredient("Lettuce", "Pantry", "pieces", "Produce", 5, new Date(2023-1900, 7, 27));
        recipe.addIngredient(newIngredient);
        ArrayList<Ingredient> list = recipe.getRecipeIngredients(); // Get the ArrayList inside storage
        assertEquals(2, list.size()); // True if there are 2 items inside list
        assertEquals("Carrots", list.get(0).getDescription()); // True if Carrots is description of 1st item in list
        assertEquals("Lettuce", list.get(1).getDescription()); // True if Lettuce is the description of 2nd item in list
    }
    /**
     * Select ingredient in recipe, delete it, and check if successfully deleted from recipe
     */
    @Test
    void testDeleteIngrediant(){
        Ingredient delete = (Ingredient) recipe.getRecipeIngredients().get(0); ;
        recipe.deleteIngredient(delete);
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        ArrayList<Ingredient> list = recipe.getRecipeIngredients();
        assertEquals(0, list.size()); // True if there are no items inside list
        Ingredient notPresent = new Ingredient("Cream", "Diary", "gram", "Diary", 5, new Date(2024, 11, 31));
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.deleteIngredient(notPresent);} );

    }
    /**
     * Get the recipe object title and check if contents are correct
     */
    @Test
    public void testGetTitle() {
        assertEquals("Fried rice", recipe.getTitle());
    }
    /**
     * Get the recipe object prep time and check if contents are correct
     */
    @Test
    public void testGetPrepTime() {
        assertEquals(2, recipe.getPrepTime());
    }
    /**
     * Get the recipe object servings number and check if contents are correct
     */
    @Test
    public void testGetServings() {
        assertEquals(2, recipe.getServings());
    }
    /**
     * Get the recipe object comments and check if contents are correct
     */
    @Test
    public void testComments() {
        assertEquals("I don't like peas in it", recipe.getComments());
    }
    /**
     * Get the recipe object Category and check if contents are correct
     */
    @Test
    public void testGetCategory() {
        assertEquals("breakfast", recipe.getCategory());
    }
    /**
     * Get the recipe object title and check if contents are correct
     */
    @Test
    public void testGetPhoto() {
        assertEquals(photo, recipe.getPhoto());  // How to do??
    }


}