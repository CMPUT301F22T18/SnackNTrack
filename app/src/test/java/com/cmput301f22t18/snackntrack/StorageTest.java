package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

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
     * Instantiate new ingredient but do not add to storage, try to delete it (get assertion error)
     */
    @Test
    public void testDeleteNonexistentIngredient() {
        Ingredient notInStorage = new Ingredient("Chocolate", "Pantry", "pieces", "Candy", 1, new Date(2024-1900, 12, 31));
        assertThrows(IllegalArgumentException.class, () -> {
                storage.deleteIngredient(notInStorage);} );
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

}
