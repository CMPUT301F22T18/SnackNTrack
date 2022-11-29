package com.cmput301f22t18.snackntrack.models;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

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

    public void initializeNewLabels() {
        String[] newUnits = {
                "Kilograms", "Grams",
                "Pounds", "Litres",
                "Ounces", "Gallons",
                "Shakers", "Packs",
                "Boxes", "Dozens"
        };
        units = new ArrayList<>(Arrays.asList(newUnits));
        Label[] newLocations = {
                new Label("Fridge", "#2AB2DD"),
                new Label("Counter-top", "#2C9692"),
                new Label("Cupboard", "#E15050"),
                new Label("Pantry", "#693286"),
        };
        locations = new ArrayList<>(Arrays.asList(newLocations));
        Label[] newCategories = {
                new Label("Meat", "#FFA47D"),
                new Label("Dairy", "#FFF492"),
                new Label("Vegetable", "#A1FF92"),
                new Label("Starch", "#FBE732"),
                new Label("Protein", "#D993B9"),
                new Label("Snack", "#93D99E")
        };
        categories = new ArrayList<>(Arrays.asList(newCategories));
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
