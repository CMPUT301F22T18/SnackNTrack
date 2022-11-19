package com.cmput301f22t18.snackntrack;

import com.cmput301f22t18.snackntrack.models.Ingredient;

/**
 * This comparator compares two Ingredients based on their Category
 */
public class IngredientCategoryComparator extends IngredientComparator {
    @Override
    public int compare(Ingredient ingredient1, Ingredient ingredient2) {
        return ingredient1.getCategory().compareTo(ingredient2.getCategory());
    }
}
