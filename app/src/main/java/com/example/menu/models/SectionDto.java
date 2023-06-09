package com.example.menu.models;

public class SectionDto {
    private int id;
    private String name;

    public SectionDto() {
    }

    public SectionDto(int id, String name) {
        this.id = id;
        this.name = name;
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
}
