package com.cmput301f22t18.snackntrack;

/**
 * This comparator compares two Ingredients based on their Description
 */
public class IngredientDescriptionComparator extends IngredientComparator {
    @Override
    public int compare(Ingredient ingredient1, Ingredient ingredient2) {
        return ingredient1.getDescription().compareTo(ingredient2.getDescription());
    }
}
