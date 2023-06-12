package com.example.menu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodSectionDto implements Parcelable {
    private int id;
    private String name;

    public FoodSectionDto() {
    }

    public FoodSectionDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected FoodSectionDto(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<FoodSectionDto> CREATOR = new Creator<FoodSectionDto>() {
        @Override
        public FoodSectionDto createFromParcel(Parcel in) {
            return new FoodSectionDto(in);
        }

        @Override
        public FoodSectionDto[] newArray(int size) {
            return new FoodSectionDto[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
