package com.cmput301f22t18.snackntrack;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class creates a recipe object with the following attributes:
 * title {@link String}
 * prepTime {@link int}
 * comments {@link String}
 * servings {@link int}
 * category {@link String}
 * recipeIngredients {@link ArrayList}
 *
 * @author SCWinter259
 * @version 1.0.0
 * @see Ingredient
 */
public class Recipe implements Serializable {
    private String title;
    private int prepTime;
    private String comments;
    private int servings;
    private String category;
    private ArrayList<Ingredient> recipeIngredients;
    private String photoURL;

    /**
     * This method is a constructor for the class Recipe
     * @since 1.0.0
     */
    public Recipe() {
        this.recipeIngredients = new ArrayList<Ingredient>();
    }

    /**
     * This method is a constructor for the class Recipe
     * @param title title for Recipe
     * @param prepTime preparation time for Recipe
     * @param comments comments for Recipe
     * @param servings number of servings for Recipe
     * @param category category of Recipe
     * @param recipeIngredients list of ingredients for recipe
     * @param photoURL photo URL for Recipe
     */
    public Recipe(String title, int prepTime,
                  String comments, int servings, String category,
                  ArrayList<Ingredient> recipeIngredients, String photoURL) {
        this.title = title;
        this.prepTime = prepTime;
        this.comments = comments;
        this.servings = servings;
        this.category = category;
        this.recipeIngredients = recipeIngredients;

        this.photoURL = photoURL;
    }

    /**
     * Setter for title attribute
     * @param title title for Recipe
     * @since 1.0.1
     */
    public void setTitle(String title) {this.title = title;}


    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    /**
     * Setter for list of ingredients
     * @param recipeIngredients
     */
    public void setRecipeIngredients(ArrayList<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Setter for photo URL
     * @param photoURL
     */
    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    /**
     * Setter for category attribute
     * @param category category of the Recipe
     * @since 1.0.1
     */


    public void setCategory(String category) {this.category = category;}

    /**
     * This method returns the title from the class Recipe
     * @return title
     * @since 1.0.0
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method returns the preparation time from the class Recipe
     * @return prepTime
     * @since 1.0.0
     */
    public int getPrepTime() {
        return this.prepTime;
    }

    /**
     * This method returns the comments from the class Recipe
     * @return comments
     * @since 1.0.0
     */
    public String getComments() {
        return this.comments;
    }

    /**
     * This method returns the number of servings from the class Recipe
     * @return servings
     * @since 1.0.0
     */
    public int getServings() {
        return this.servings;
    }

    /**
     * This method returns the category from the class Recipe
     * @return category
     * @since 1.0.0
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * This method returns the photoURL from the class Recipe
     * @return photoURL
     */
    public String getPhotoURL() {
        return photoURL;
    }

    /**
     * This method returns the ingredients from the class Recipe
     * @return recipeIngredients
     * @since 1.0.0
     */
    public ArrayList<Ingredient> getRecipeIngredients() {
        return this.recipeIngredients;
    }

    /**
     * This method adds an ingredient to the list of ingredients
     * @param ingredient {@link Ingredient}
     * @since 1.0.0
     */
    public void addIngredient(Ingredient ingredient) {
        this.recipeIngredients.add(ingredient);
    }

    /**
     * This method deletes an ingredient from the list of ingredients
     * @param ingredient
     * @since 1.0.0
     */
    public void deleteIngredient(Ingredient ingredient) {
        this.recipeIngredients.remove(ingredient);
    }
}
