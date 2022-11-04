package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
    public RecipeList createMockRecipeList() {
        RecipeList recipeList = new RecipeList();
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        recipeList.addRecipe(mockRecipe());
        return recipeList;
    }
    public Recipe mockRecipe() {
        Recipe recipe = new Recipe();
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        recipe.addIngredient(new Ingredient("Carrots", "Fridge", "pieces", "Produce", 3, new Date(2024-1900, 11, 27)));
        recipe.setRecipe("Fried Rice",2,2,"breakfast","I don't like peas in it", recipe.getRecipeIngredients()); // How to test fro photo
        return recipe;
    }

    /**
     * Get ArrayList from RecipeList object and check if contents of list are correct.
     */
    @Test
    public void testGetRecipeList() {
        recipeList.sort("Best Before"); // Sort storage by Best Before date so Spam comes first ?
        ArrayList<Recipe> list = recipeList.getRecipeList();
        assertEquals(1, list.size()); // True if 2 items in list
        assertEquals("Carrot", list.get(0).getTitle()); // True if "Spam" is description of 1st item in list
    }

    /**
     * Add an recipe to recipeList and check if successfully in recipeList
     */
    @Test
    void testAddRecipe() {
        RecipeList recipeList = createMockRecipeList();
        assertEquals(1, recipeList.getRecipeList().size());
        Recipe recipe = new Recipe();
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        recipe.addIngredient(new Ingredient("Potatoes", "Fridge", "pieces", "Produce", 3, new Date(2024-1900, 11, 27)));
        recipe.setRecipe("Pancakes",2,2,"breakfast","I don't like it too fried", recipe.getRecipeIngredients()); // H
        recipeList.addRecipe(recipe);
        assertEquals(2, recipeList.getRecipeList().size());
        assertTrue(recipeList.getRecipeList().contains(recipe));
    }

    /**
     * Delete an recipe to recipeList if present and check if successfully in recipeList
     */
    @Test
    public void testDeleteRecipe(){
        RecipeList recipeList = createMockRecipeList();
        Recipe recipe = new Recipe();
        // Recipe List consists of arraylist of recipes which is a array list of ingrediants
        recipe.addIngredient(new Ingredient("Bread", "Pantry", "pieces", "Bread", 2, new Date(2024-1900, 11, 27)));
        recipe.setRecipe("Toast",2,2,"lunch","I don't like it too fried", recipe.getRecipeIngredients()); // H
        recipeList.addRecipe(recipe);
        Recipe recipe1 = new Recipe();
        recipe1.addIngredient(new Ingredient("Bread", "Pantry", "pieces", "Bread", 2, new Date(2024-1900, 11, 27)));
        recipe1.setRecipe("Sandwich",2,2,"lunch","I don't like it too fried", recipe.getRecipeIngredients());// Non existent recipe in the recipe lis
        assertThrows( IllegalArgumentException.class, ()->{recipeList.deleteRecipe(recipe1);}); // What is the error?
        recipeList.deleteRecipe(recipe);// Existing recipe

    }

    @Test
    public void testSort() {
        // TODO after halfway checkpoint
    }

}