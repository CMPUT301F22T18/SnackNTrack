package com.cmput301f22t18.snackntrack.models;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent an ingredient storage
 * @author Duc Ho
 * @version 1.0.0
 */
public class Storage implements Serializable {
    private final ArrayList<Ingredient> storage; // Ingredient Storage
    private final ArrayList<String> ids;
    private String option;
    // Comparators
    /**
     * Construct an empty storage
     */
    public Storage() {
        storage = new ArrayList<>();
        ids = new ArrayList<>();
        option = "default";
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void addId(String id) {
        this.ids.add(id);
    }

    /**
     * Add an ingredient to the storage
     * @param ingredient the ingredient to be added
     */
    public void addIngredient(Ingredient ingredient) {
        storage.add(ingredient);
    }

    /**
     * Return the number of ingredients in the storage
     * @return the number of ingredients currently in the storage
     */
    public int getCount() {
        return storage.size();
    }

    /**
     * Delete an ingredient from the storage
     * @param ingredient the ingredient to be deleted
     */
    public void deleteIngredient(Ingredient ingredient) {
        storage.remove(ingredient);
    }

    /**
     * Delete an ingredient at a specified index from the storage
     * @param index the index of the ingredient
     */
    public void deleteIngredient(int index) {
        storage.remove(index);
    }

    /**
     * Set the ingredient at a specified index to be a specified ingredient
     * @param index the index of the old ingredient
     * @param ingredient the new ingredient
     */
    public void setIngredient(int index, Ingredient ingredient) {
        storage.set(index, ingredient);
    }

    /**
     * Clear the storage
     */
    public void clearStorage() {
        storage.clear();
    }

    /**
     * Return the storage ingredient list
     * @return the ingredient list
     */
    public ArrayList<Ingredient> getStorageList() {
        return storage;
    }

    public String getOption() {
        return option;
    }

    public ArrayList<Ingredient> getStorage() {
        return storage;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
