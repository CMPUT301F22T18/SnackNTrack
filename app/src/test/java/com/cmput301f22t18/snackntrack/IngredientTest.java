package com.cmput301f22t18.snackntrack;

import static org.junit.Assert.assertEquals;

import com.cmput301f22t18.snackntrack.models.Ingredient;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Unit tests for Ingredient class
 * @author Mark Maligalig
 * @version 1
 */
public class IngredientTest {

    /**
     * This variable stores the ingredient object used in testing
     */
    private Ingredient ingredient;

    /**
     * Before each test case, create a mock Ingredient object
     */
    @Before
    public void createMockIngredient() {
        ingredient = new Ingredient("Spam", "Pantry", "cans", "Meat", 1, new Date(2022-1900, 12, 31));
    }

    /**
     * Check if description is consistent with the entered description
     */
    @Test
    public void testGetDescription() {
        assertEquals("Spam", ingredient.getDescription());
    }

    /**
     * Check if location is consistent with the entered location
     */
    @Test
    public void testGetLocation() {
        assertEquals("Pantry", ingredient.getLocation());
    }

    /**
     * Check if unit is consistent with the entered unit
     */
    @Test
    public void testGetUnit() {
        assertEquals("cans", ingredient.getUnit());
    }

    /**
     * Check if category is consistent with the entered category
     */
    @Test
    public void testGetCategory() {
        assertEquals("Meat", ingredient.getCategory());
    }

    /**
     * Check if amount is consistent with the entered amount
     */
    @Test
    public void testGetAmount() {
        assertEquals(1, ingredient.getAmount());
    }

    /**
     * Check if best before date is consistent with the entered date
     */
    @Test
    public void testGetBestBeforeDate() {
        assertEquals(0, ingredient.getBestBeforeDate().compareTo(new Date(2022-1900, 12, 31)));
    }

    /**
     * Check if description is consistent with the changed description
     */
    @Test
    public void testSetDescription() {
        String newDescription = "Potato";
        ingredient.setDescription(newDescription);
        assertEquals(newDescription, ingredient.getDescription());
    }

    /**
     * Check if location is consistent with the changed location
     */
    @Test
    public void testSetLocation() {
        String newLocation = "Fridge";
        ingredient.setLocation(newLocation);
        assertEquals(newLocation, ingredient.getLocation());
    }

    /**
     * Check if unit is consistent with the changed unit
     */
    @Test
    public void testSetUnit() {
        String newUnit = "Potato";
        ingredient.setUnit(newUnit);
        assertEquals(newUnit, ingredient.getUnit());
    }

    /**
     * Check if category is consistent with the changed category
     */
    @Test
    public void testSetCategory() {
        String newCategory = "Meat";
        ingredient.setCategory(newCategory);
        assertEquals(newCategory, ingredient.getCategory());
    }

    /**
     * Check if amount is consistent with the changed amount
     */
    @Test
    public void testSetAmount() {
        int newAmount = 2;
        ingredient.setAmount(newAmount);
        assertEquals(newAmount, ingredient.getAmount());
    }

    /**
     * Check if best before date is consistent with the changed date
     */
    @Test
    public void testSetBestBeforeDate() {
        Date newBestBeforeDate = new Date(2022-1900, 11, 30);
        ingredient.setBestBeforeDate(newBestBeforeDate);
        assertEquals(0, ingredient.getBestBeforeDate().compareTo(newBestBeforeDate));
    }
}
