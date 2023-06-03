package com.example.menu.models;

public class Item {
    private int id;
    private String name;
    private double price;
    private String photo;

    public Item(int id, String name, double price, String photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }
}