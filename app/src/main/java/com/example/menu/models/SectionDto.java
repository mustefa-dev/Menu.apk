package com.example.menu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SectionDto implements Parcelable {
    private int id;
    private String name;

    public SectionDto() {
    }

    public SectionDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected SectionDto(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<SectionDto> CREATOR = new Creator<SectionDto>() {
        @Override
        public SectionDto createFromParcel(Parcel in) {
            return new SectionDto(in);
        }

        @Override
        public SectionDto[] newArray(int size) {
            return new SectionDto[size];
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
