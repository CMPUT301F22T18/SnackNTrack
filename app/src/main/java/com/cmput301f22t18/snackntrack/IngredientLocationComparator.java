package com.cmput301f22t18.snackntrack;

/**
 * This comparator compares two Ingredients based on their Location
 */
public class IngredientLocationComparator extends IngredientComparator {
    @Override
    public int compare(Ingredient ingredient1, Ingredient ingredient2) {
        return ingredient1.getLocation().compareTo(ingredient2.getLocation());
    }
}