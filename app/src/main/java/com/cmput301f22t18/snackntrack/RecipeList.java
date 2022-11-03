package com.cmput301f22t18.snackntrack;

import java.util.ArrayList;

/**
 * This class keeps track of all recipes using a recipeList {@link ArrayList}
 *
 * @author SCWinter259
 * @version 1.0.0
 * @see Recipe
 */
public class RecipeList {
    private final ArrayList<Recipe> recipeList;

    /**
     * This method is the constructor for the RecipeList class
     * @since 1.0.0
     */
    public RecipeList() {
        this.recipeList = new ArrayList<>();
    }

    /**
     * This method gets the recipe list from the RecipeList class
     * @return recipeList
     * @since 1.0.0
     */
    public ArrayList<Recipe> getRecipeList() {
        return this.recipeList;
    }

    /**
     * This method sorts the recipeList in the order specified by sortBy
     * @param sortBy - an order indicator
     * @since 1.0.0
     */
    public void sort(String sortBy) {
        // TODO: the sorting order is not so clear, so this method is yet to be implemented
    }

    /**
     * This method adds a recipe into the recipeList
     * @param newRecipe - a recipe to be added
     * @since 1.0.0
     */
    public void addRecipe(Recipe newRecipe) {
        this.recipeList.add(newRecipe);
    }

    /**
     * This method deletes a recipe from the recipeList
     * @param recipe - a recipe to be deleted
     * @since 1.0.0
     */
    public void deleteRecipe(Recipe recipe) {
        this.recipeList.remove(recipe);
    }
}
