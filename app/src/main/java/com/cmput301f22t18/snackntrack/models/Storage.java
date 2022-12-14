package com.cmput301f22t18.snackntrack.models;

import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientBBDComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientCategoryComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientDescriptionComparator;
import com.cmput301f22t18.snackntrack.controllers.comparators.ingredients.IngredientLocationComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * This class represent an ingredient storage
 * @author Duc Ho
 * @version 1.0.0
 */
public class Storage implements Serializable {
    private final ArrayList<Ingredient> storage; // Ingredient Storage
    private final ArrayList<String> ids;
    // Comparators
    private final IngredientDescriptionComparator idc = new IngredientDescriptionComparator();
    private final IngredientLocationComparator ilc = new IngredientLocationComparator();
    private final IngredientCategoryComparator icc = new IngredientCategoryComparator();
    private final IngredientBBDComparator ibc = new IngredientBBDComparator();
    private final Dictionary<String, IngredientComparator> comparators =
            new Hashtable<String, IngredientComparator>() {{
                put("Description", idc);
                put("Location", ilc);
                put("Category", icc);
                put("Best Before", ibc);
            }};
    /**
     * Construct an empty storage
     */
    public Storage() {
        storage = new ArrayList<>();
        ids = new ArrayList<>();
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
     * Return a sorted array list of ingredients based on comparing attribute
     * @param key the attribute of ingredient class to compare
     */
    public void sort(String key) {
        storage.sort(comparators.get(key));
    }

    public ArrayList<Ingredient> getStorageList() {
        return storage;
    }
}
