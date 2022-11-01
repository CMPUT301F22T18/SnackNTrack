package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import java.util.Date;

public class IngredientTest { //TODO: Ask if we need to check for setters

    /**
     * This variable stores the ingredient object used in testing
     */
    private Ingredient ingredient;

    /**
     * Before each test case, create a mock Ingredient object
     */
    @Before
    public void createMockIngredient() {
        ingredient = new Ingredient("Spam", "Pantry", "pieces", "Meat", 1, new Date(2022-1900, 12, 31));
    }

    /**
     * Check if description is consistent with the entered description
     */
    public void testGetDescription() {
        assertEquals("Spam", ingredient.getDescription());
    }

    /**
     * Check if location is consistent with the entered location
     */
    public void testGetLocation() {
        assertEquals("Pantry", ingredient.getLocation());
    }

    /**
     * Check if unit is consistent with the entered unit
     */
    public void testGetUnit() {
        assertEquals("pieces", ingredient.getUnit());
    }

    /**
     * Check if category is consistent with the entered category
     */
    public void testGetCategory() {
        assertEquals("Meat", ingredient.getCategory());
    }

    /**
     * Check if amount is consistent with the entered amount
     */
    public void testGetAmount() {
        assertEquals(1, ingredient.getAmount());
    }

    /**
     * Check if best before date is consistent with the entered date
     */
    public void testGetDate() {
        assertEquals(0, ingredient.getDate().compareTo(new Date(2022-1900, 12, 31)));
    }
}
