package com.cmput301f22t18.snackntrack;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * A cooking ingredient, with a description, a counting unit, a category, an amount,
 * a location and a best before date
 * @author Duc Ho
 * @version 1
 * @since 1
 */
public class Ingredient {
    private String description, location, unit, category;
    private int amount;
    private Date bestBeforeDate;

    public Ingredient(String description, String location,
                      String unit, String category,
                      int amount, Date bbf) {
        this.description = description;
        this.location = location;
        this.unit = unit;
        this.category = category;
        this.amount = amount;
        this.bestBeforeDate = bbf;
    }

    public Ingredient(String description, String unit, String category, int amount) {
        this.description = description;
        this.unit = unit;
        this.category = category;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Date getBestBeforeDate() {
        return bestBeforeDate;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NonNull
    @Override
    public String toString() {
        return "Ingredient: " + '\n' +
                "Description: " + description + '\n' +
                "Location: " + location + '\n' +
                "Unit: " + unit + '\n' +
                "Category: " + category + '\n' +
                "Amount: " + amount +
                "Best Before: " + bestBeforeDate;
    }
}
