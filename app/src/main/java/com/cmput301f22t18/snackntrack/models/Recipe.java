package com.cmput301f22t18.snackntrack.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class creates a recipe object with the following attributes: title, prepTime, comments,
 * servings, category, recipeIngredients, photoURL
 *
 * @author Casper Nguyen
 */
public class Recipe implements Parcelable {
    private String title;
    private int prepTime;
    private String comments;
    private int servings;
    private String category;
    private ArrayList<Ingredient> recipeIngredients;
    private String photoURL;

    /**
     * This method is an empty constructor for the class Recipe
     */
    public Recipe() {}

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

    protected Recipe(Parcel in) {
        title = in.readString();
        prepTime = in.readInt();
        comments = in.readString();
        servings = in.readInt();
        category = in.readString();
        recipeIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        photoURL = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    /**
     * Setter for title attribute
     * @param title title for Recipe
     */
    public void setTitle(String title) {this.title = title;}

    /**
     * Setter for prepTime attribute
     * @param prepTime preparation time for Recipe
     */
    public void setPrepTime(int prepTime) {this.prepTime = prepTime;}

    /**
     * Setter for comments attribute
     * @param comments comments for Recipe
     */
    public void setComments(String comments) {this.comments = comments;}

    /**
     * Setter for servings attribute
     * @param servings number of servings for Recipe
     */
    public void setServings(int servings) {this.servings = servings;}

    /**
     * Setter for list of ingredients
     * @param recipeIngredients an ArrayList of Ingredient objects
     */
    public void setRecipeIngredients(ArrayList<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    /**
     * Setter for photo URL
     * @param photoURL the photoURL for the recipe
     */
    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    /**
     * Setter for category attribute
     * @param category category of the Recipe
     */
    public void setCategory(String category) {this.category = category;}

    /**
     * This method returns the title from the class Recipe
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method returns the preparation time from the class Recipe
     * @return prepTime (preparation time)
     */
    public int getPrepTime() {
        return this.prepTime;
    }

    /**
     * This method returns the comments from the class Recipe
     * @return comments
     */
    public String getComments() {
        return this.comments;
    }

    /**
     * This method returns the number of servings from the class Recipe
     * @return servings (the number of servings)
     */
    public int getServings() {
        return this.servings;
    }

    /**
     * This method returns the category from the class Recipe
     * @return category
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
     */
    public ArrayList<Ingredient> getRecipeIngredients() {
        return this.recipeIngredients;
    }

    /**
     * This method adds an ingredient to the list of ingredients
     * @param ingredient an Ingredient object
     */
    public void addIngredient(Ingredient ingredient) {
        this.recipeIngredients.add(ingredient);
    }

    /**
     * This method deletes an ingredient from the list of ingredients
     * @param ingredient an Ingredient object
     */
    public void deleteIngredient(Ingredient ingredient) {
        this.recipeIngredients.remove(ingredient);
    }

    /**
     * This method sets a specific ingredient to the list of ingredients
     * @param index index of ingredients list
     * @param ingredient ingredient to replace at specified index
     */
    public void setIngredient(int index, Ingredient ingredient) {
        this.recipeIngredients.set(index, ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(prepTime);
        dest.writeString(comments);
        dest.writeInt(servings);
        dest.writeString(category);
        dest.writeTypedList(recipeIngredients);
        dest.writeString(photoURL);
    }
}