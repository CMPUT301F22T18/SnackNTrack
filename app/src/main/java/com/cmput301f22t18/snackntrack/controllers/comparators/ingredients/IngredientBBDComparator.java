package com.cmput301f22t18.snackntrack.controllers.comparators.ingredients;

import com.cmput301f22t18.snackntrack.models.Ingredient;

/**
 * This comparator compares two Ingredients based on their Best Before Date
 */
public class IngredientBBDComparator extends IngredientComparator {
    @Override
    public int compare(Ingredient ingredient1, Ingredient ingredient2) {
        return ingredient1.getBestBeforeDate().compareTo(ingredient2.getBestBeforeDate());
    }
}
