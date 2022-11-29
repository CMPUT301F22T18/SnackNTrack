package com.cmput301f22t18.snackntrack.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * A cooking ingredient, with a description, a counting unit, a category, an amount,
 * a location and a best before date
 * @author Duc Ho
 * @version 1
 * @since 1
 */
public class Ingredient implements Parcelable {
    private String description, location, unit, category;
    private int amount;
    private Timestamp bestBeforeDate;


    public Ingredient() {}

    /**
     * Main constructor when all information is available
     * @param description the description
     * @param location the location where the ingredient is stored
     * @param unit the measurable unit of the ingredient
     * @param category the category of the ingredients
     * @param amount the amount of the ingredients (in unit)
     * @param bbf the best before date of the ingredient
     */
    public Ingredient(String description, String location,
                      String unit, String category,
                      int amount, Date bbf) {
        this.description = description;
        this.location = location;
        this.unit = unit;
        this.category = category;
        this.amount = amount;
        this.bestBeforeDate = new Timestamp(bbf);
    }

    public Ingredient(String description, String location,
                      String unit, String category,
                      int amount, Timestamp bbf) {
        this.description = description;
        this.location = location;
        this.unit = unit;
        this.category = category;
        this.amount = amount;
        this.bestBeforeDate = bbf;
    }

    public Ingredient(Map<String, Object> map) {
        assert map != null;
        this.description = (String) map.get("description");
        this.unit = (String) map.get("unit");
        this.category = (String) map.get("category");
        Object amount = map.getOrDefault("amount", 0);
        if (amount != null) this.amount = ((Long)amount).intValue();
        this.location = (String) map.getOrDefault("location", null);
        this.bestBeforeDate = (Timestamp) map.getOrDefault("bestBeforeDate", null);

    }

    /**
     * Constructor for no location and best before date
     * @param description the description of the ingredient
     * @param unit the measurable unit of the ingredient
     * @param category the category of the ingredient
     * @param amount the amount of the ingredients (in unit)
     */
    public Ingredient(String description, String unit, String category, int amount) {
        this.description = description;
        this.unit = unit;
        this.category = category;
        this.amount = amount;
    }

    protected Ingredient(Parcel in) {
        description = in.readString();
        location = in.readString();
        unit = in.readString();
        category = in.readString();
        amount = in.readInt();
        bestBeforeDate = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    /**
     * Get the description of the ingredient
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the location of the ingredient
     * @return the location
     */
    public String getLocation() {
        if (location == null) return null;
        return location;
    }

    /**
     * Get the Best Before Date of the ingredient
     * @return the best before date
     */
    public Date getBestBeforeDateDate() {
        if (bestBeforeDate == null) return null;
        return bestBeforeDate.toDate();
    }

    public Timestamp getBestBeforeDate () {
        return bestBeforeDate;
    }

    /**
     * Get the amount (in unit) of the ingredient
     * @return the amount (in unit) of the ingredient
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Get the category of the ingredient
     * @return the category of the ingredient
     */
    public String getCategory() {
        return category;
    }

    /**
     * Get the measurable unit of the ingredient
     * @return the unit of the ingredient
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set the location of the ingredient
     * @param location the location of the ingredient
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Set the description of the ingredient
     * @param description the description of the ingredient
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the amount of the ingredient
     * @param amount the amount of the ingredient
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Set the unit of the ingredient
     * @param unit the measurable unit of the ingredient
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Set the best before date of the ingredient
     * @param bestBeforeDate the best before date of the ingredient
     */
    public void setBestBeforeDateDate(Date bestBeforeDate) {
        if (bestBeforeDate != null)
            this.bestBeforeDate = new Timestamp(bestBeforeDate);
        else this.bestBeforeDate = null;
    }

    public void setBestBeforeDate(Timestamp t) {
        this.bestBeforeDate = t;
    }

    /**
     * Set the category of the ingredient
     * @param category the category of the ingredient
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Print the Ingredient information in a legible format
     * @return the string contains the ingredient information
     */
    @NonNull
    @Override
    public String toString() {
        return "Ingredient: " + '\n' +
                "Description: " + description + '\n' +
                "Location: " + location + '\n' +
                "Unit: " + unit + '\n' +
                "Category: " + category + '\n' +
                "Amount: " + amount + '\n' +
                "Best Before: " + bestBeforeDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(unit);
        dest.writeString(category);
        dest.writeInt(amount);
        dest.writeParcelable(bestBeforeDate, flags);
    }
}
