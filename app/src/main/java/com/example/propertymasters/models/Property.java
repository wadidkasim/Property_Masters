package com.example.propertymasters.models;

import java.util.List;

public class Property {

    private int propertyID;
    private String propertyTypeName;
    private String location;
    private int price;
    String name;
    private String description;
    private String imageURL;
    private boolean isApproved;
    private boolean isAvailable;

    public Property(int propertyID, String propertyTypeName, String location, int price,String name, String description, boolean isApproved, String imageURL, boolean isAvailable) {
        this.propertyID = propertyID;
        this.propertyTypeName = propertyTypeName;
        this.location = location;
        this.price = price;
        this.name = name;
        this.description = description;
        this.isApproved = isApproved;
        this.imageURL = imageURL;
        this.isAvailable = isAvailable;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public String getName(){
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
