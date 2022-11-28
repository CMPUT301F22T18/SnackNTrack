package com.cmput301f22t18.snackntrack.models;

import java.util.ArrayList;

public class AppUser {
    ArrayList<String> units;

    public AppUser() {
        units = new ArrayList<>();
    }

    public ArrayList<String> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<String> units) {
        this.units = units;
    }
}
