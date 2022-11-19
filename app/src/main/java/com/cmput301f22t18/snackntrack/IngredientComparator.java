package com.cmput301f22t18.snackntrack;

import com.cmput301f22t18.snackntrack.models.Ingredient;

import java.util.Comparator;

/**
 * This abstract class represents a comparator for Ingredient objects
 */
public abstract class IngredientComparator implements  Comparator<Ingredient>{
    public abstract int compare(Ingredient ingredient1, Ingredient ingredient2);
}

