package com.cmput301f22t18.snackntrack.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Label implements Serializable {
    private String name;
    private String color;
    public Label() {

    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
