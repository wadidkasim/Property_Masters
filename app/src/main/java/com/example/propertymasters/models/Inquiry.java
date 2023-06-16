package com.example.propertymasters.models;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Inquiry {

    private int inquiryID;
    private String message;
    private int propertyId;
    private Timestamp timestamp;
    private String email;
    private String phone;
    private String status;
    private int userId;
    private String name;
    private String description;
    private String imageUrl;
    private String location;
    private int price;
    private String propertyType;

    public Inquiry(int inquiryID, String message, int propertyId, Timestamp timestamp, String email, String phone, String status, int userId, String name, String description, String imageUrl, String location, int price, String propertyType) {
        this.inquiryID = inquiryID;
        this.message = message;
        this.propertyId = propertyId;
        this.timestamp = timestamp;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.price = price;
        this.propertyType = propertyType;
    }

    public int getInquiryID() {
        return inquiryID;
    }

    public String getMessage() {
        return message;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public String getPropertyType() {
        return propertyType;
    }
}
