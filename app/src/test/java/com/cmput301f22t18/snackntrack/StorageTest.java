package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        storage.addIngredient(ingredient);
    }

    /**
     * Adds ingredients to storage and checks if list is sorted by respective filters
     * Storage can sort ingredients by description, best before, location, category
     */
    @Test
    public void testSort() {
        ArrayList<Ingredient> list;
        // Add new Ingredient to storage (storage already has 1 ingredient)
        storage.addIngredient(new Ingredient("Carrots", "Fridge", "pieces", "Produce", 3, new Date(2024-1900, 11, 27)));

        // Sort by Description (order: Carrots, Spam)
        storage.sort("Description");
        list = storage.getStorageList();
        assertEquals("Carrots", list.get(0).getDescription()); // True if first ingredient's description is Carrots

        // Sort by Best Before date (Spam, Carrots)
        storage.sort("Best Before");
        list = storage.getStorageList();
        assertEquals("Spam", list.get(0).getDescription()); // True if first ingredient's description is Spam

        // Sort by Location (Carrots, Spam)
        storage.sort("Location");
        list = storage.getStorageList();
        assertEquals("Carrots", list.get(0).getDescription()); // True if first ingredient's description is Carrots

        // Sort by Category (Spam, Carrots)
        storage.sort("Category");
        list = storage.getStorageList();
        assertEquals("Spam", list.get(0).getDescription()); // True if first ingredient's description is Spam

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
        assertEquals(list.get(1), newIngredient); // True if second ingredient in storage is newIngredient
    }

    /**
     * Select ingredient object in storage, delete it, and check if successfully deleted from storage
     */
    @Test
    public void testDeleteIngredientByIngredient() {
        // Select first ingredient in storage
        Ingredient toDelete = (Ingredient) storage.getStorageList().get(0);
        storage.deleteIngredient(toDelete);
        ArrayList<Ingredient> list = storage.getStorageList(); // Get the ArrayList inside storage
        assertEquals(0, list.size()); // True if there are no items inside list
        assertNotEquals(toDelete, list.get(0)); // True if deleted ingredient != first element of list
    }

    /**
     * Test deleting ingredient by index of ingredient (rather than ingredient object)
     */
    @Test
    public void testDeleteIngredientByInt() {
        Ingredient toDelete = (Ingredient) storage.getStorageList().get(0);
        storage.deleteIngredient(0);
        ArrayList<Ingredient> list = storage.getStorageList(); // Get the ArrayList inside storage
        assertEquals(0, list.size()); // True if there are no items inside list
        assertNotEquals(toDelete, list.get(0)); // True if deleted ingredient != first element of list
    }

    /**
     * Get ArrayList from storage object and check if contents of list are correct
     */
    @Test
    public void testGetStorageList() {
        storage.addIngredient(new Ingredient("Potato", "Pantry", "pieces", "Produce", 3, new Date(2023-1900, 7, 27)));
        storage.sort("Best Before"); // Sort storage by Best Before date so Spam comes first ?
        ArrayList<Ingredient> list = storage.getStorageList();
        assertEquals(2, list.size()); // True if 2 items in list
        assertEquals("Spam", list.get(0).getDescription()); // True if "Spam" is description of 1st item in list
        assertEquals("Potato", list.get(1).getDescription()); // True if "Potato" is description of 2nd item in list
    }

    @Test
    public void testGetCount() {
        int initialCount = storage.getStorageList().size();
        assertEquals(initialCount, storage.getCount()); // True if 1 ingredient in storage
        Ingredient newIngredient = new Ingredient("Potato", "Pantry", "pieces", "Produce", 3, new Date(2023-1900, 7, 27));
        storage.addIngredient(newIngredient);
        assertEquals(initialCount + 1, storage.getCount()); // True if 2 ingredients in storage
        storage.deleteIngredient(newIngredient);
        assertEquals(initialCount, storage.getCount()); // True if 1 ingredient in storage
        storage.deleteIngredient(storage.getStorageList().get(0)); // Delete first ingredient from storage
        assertEquals(initialCount - 1, storage.getCount()); // True if no ingredients in storage
    }

    @Test
    public void testClearStorage() {
        storage.addIngredient(new Ingredient("Potato", "Pantry", "pieces", "Produce", 3, new Date(2023-1900, 7, 27)));
        assertEquals(2, storage.getCount());
        storage.clearStorage();
        assertEquals(0, storage.getCount());
    }

    @Test
    public void testSetIngredient() {
        int initialCount = storage.getCount();
        storage.setIngredient(0, new Ingredient("Potato", "Pantry", "pieces", "Produce", 3, new Date(2023-1900, 7, 27)));
        assertEquals(1, initialCount); // True if storage still has same amount of ingredients, i.e. 1
        assertEquals("Potato", storage.getStorageList().get(0).getDescription()); // True if first ingredient description is "Potato"
        assertNotEquals("Spam", storage.getStorageList().get(0).getDescription()); // True if first ingredient desc no longer "Spam"
    }
}
