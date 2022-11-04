package com.cmput301f22t18.snackntrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * This class represent an ingredient storage
 * @author Duc Ho
 * @version 1.0.0
 */
public class Storage implements Serializable {
    private final ArrayList<Ingredient> storage; // Ingredient Storage
    // Comparators
    private final IngredientDescriptionComparator idc = new IngredientDescriptionComparator();
    private final IngredientLocationComparator ilc = new IngredientLocationComparator();
    private final IngredientCategoryComparator icc = new IngredientCategoryComparator();
    private final IngredientBBDComparator ibc = new IngredientBBDComparator();
    private final Dictionary<String, IngredientComparator> comparators =
            new Hashtable<String, IngredientComparator>() {{
                put("description", idc);
                put("location", ilc);
                put("category", icc);
                put("best before date", ibc);
            }};
    /**
     * Construct an empty storage
     */
    public Storage() {
        storage = new ArrayList<>();
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
     * Return a sorted array list of ingredients based on comparing attribute
     * @param key the attribute of ingredient class to compare
     * @return the sorted array list of the ingredients
     */
    public ArrayList<Ingredient> sort(String key) {
        ArrayList<Ingredient> newStorage = storage;
        Collections.sort(newStorage, comparators.get(key));
        return newStorage;
    }

    public ArrayList getStorageList(){
        return storage;
    }
}
