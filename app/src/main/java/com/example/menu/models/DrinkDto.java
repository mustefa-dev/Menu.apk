package com.example.menu.models;

public class DrinkDto {
    private int id;
    private String name;
    private String sectionName;
    private int sectionId;
    private double price;
    private String description;
    private String photo;

    public DrinkDto() {
    }

    public DrinkDto(int id, String name, String sectionName, int sectionId, double price, String description, String photo) {
        this.id = id;
        this.name = name;
        this.sectionName = sectionName;
        this.sectionId = sectionId;
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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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
