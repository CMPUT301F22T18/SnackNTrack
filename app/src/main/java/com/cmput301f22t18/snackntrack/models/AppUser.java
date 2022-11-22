package com.cmput301f22t18.snackntrack.models;
public class AppUser {
    String name;
    String email;

    public AppUser() {
        name = "";
        email = "";
    }

    public AppUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
