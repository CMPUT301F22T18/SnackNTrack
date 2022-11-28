package com.cmput301f22t18.snackntrack.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AppUser {
    ArrayList<String> units;
    ArrayList<Label> locations;
    ArrayList<Label> categories;

    public AppUser() {
        units = new ArrayList<>();
        locations = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public ArrayList<String> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<String> units) {
        this.units = units;
    }

    public ArrayList<Label> getCategories() {
        return categories;
    }

    public ArrayList<Label> getLocations() {
        return locations;
    }

    public void setCategories(ArrayList<Label> categories) {
        this.categories = categories;
    }

    public void setLocations(ArrayList<Label> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public String toString() {
        return "AppUser{" +
                "units=" + units +
                ", locations=" + locations +
                ", categories=" + categories +
                '}';
    }
}
