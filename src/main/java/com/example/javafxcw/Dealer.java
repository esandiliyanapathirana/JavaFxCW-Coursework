package com.example.javafxcw;

public class Dealer {
    private String id;
    private String name;
    private String phone;
    private String location;

    public Dealer(String id, String name, String phone, String location) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getLocation() { return location; }

    public String toFileLine() {
        return id + "|" + name + "|" + phone + "|" + location;
    }

    public String toDisplay() {
        return id + " | " + name + " | " + phone + " | " + location;
    }
}