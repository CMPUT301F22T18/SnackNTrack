package com.cmput301f22t18.snackntrack.models;
public class User {
    String name;
    String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
