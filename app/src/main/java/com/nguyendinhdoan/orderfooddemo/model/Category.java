package com.nguyendinhdoan.orderfooddemo.model;

import com.google.firebase.database.PropertyName;

public class Category {

    private String image;
    private String name;

    public Category(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public Category() {
    }

    @PropertyName("image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
