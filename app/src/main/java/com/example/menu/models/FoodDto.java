package com.example.menu.models;

public class FoodDto {
    private int id;
    private String name;
    private String sectionName;
    private int sectionId;
    private double price;
    private String description;
    private String photo;

    public FoodDto() {
    }

    public FoodDto(int id, String name, String sectionName, double price, String description, String photo) {
        this.id = id;
        this.name = name;
        this.sectionName = sectionName;
        this.price = price;
        this.description = description;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
