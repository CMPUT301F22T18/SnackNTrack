package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Unit tests for Storage class
 * @author Mark Maligalig
 * @version 1
 */
public class StorageTest {
    /**
     * This variable stores storage object that will be used in testing.
     */
    private Storage storage;

    /**
     * Runs before all test cases; creates a mock Storage object with 1 Ingredient
     */
    @Before
    public void createMockStorage() {
        storage = new Storage();
        // Ingredient consists of desc, location, unit, category, amount, best before date
        Ingredient ingredient = new Ingredient("Spam", "Pantry", "pieces", "Meat", 1, new Date(2022-1900, 12, 31));
        storage.add(ingredient);

    }

    /**
     * Adds ingredients to storage and checks if list is sorted by respective filters
     * Storage can sort ingredients by description, best before, location, category
     */
    @Test
    public void testSort() {
        //Ingredient newIngredient = new Ingredient("Potato", "Pantry", "pieces", "Produce", 3, new Date(2023-1900, 7, 27));
        //storage.add(newIngredient);
        // incomplete
    }

    /**
     * Add an ingredient to storage and check if successfully in storage
     */
    @Test
    public void testAddIngredient() {
        // Add a new Ingredient to storage (note that storage already has 1 Ingredient)
        Ingredient newIngredient = new Ingredient("Potato", "Pantry", "pieces", "Produce", 3, new Date(2023-1900, 7, 27));
        storage.addIngredient(newIngredient);
        ArrayList<Ingredient> list = storage.getStorageList(); // Get the ArrayList inside storage
        assertEquals(2, list.size()); // True if there are 2 items inside list
        assertEquals(list[1], newIngredient); // True if second ingredient in storage is newIngredient
    }

    /**
     * Select ingredient in storage, delete it, and check if successfully deleted from storage
     */
    @Test
    public void testDeleteIngredient() {
        // Select first ingredient in storage
        Ingredient toDelete = (Ingredient) storage.getStorageList().get(0);
        storage.deleteIngredient(toDelete);
        ArrayList<Ingredient> list = storage.getStorageList(); // Get the ArrayList inside storage
        assertEquals(0, list.size()); // True if there are no items inside list
        assertFalse(toDelete, list[0]); // True if deleted ingredient != first element of list
    }

    /**
     * Get ArrayList from storage object and check if contents of list are correct
     */
    @Test
    public void testGetStorageList() {
        //incomplete
    }

    /**
     * Need to write tests for Storage:
     * testSort
     * testAddIngredient
     * testDeleteIngredient
     * testGetStorageList
     */
}
