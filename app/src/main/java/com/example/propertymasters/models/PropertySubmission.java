package com.example.propertymasters.models;

public class PropertySubmission {
    private int submissionID;
    private String status;
    private String reply;
    private int propertyID;
    private String propertyType;
    private String location;
    private String imageUrl;
    private String name;
    private String description;
    private int price;
    private boolean isApproved;
    private boolean isAvailable;
    private int userID;

    private String phone;
    private String email;

    public PropertySubmission(int submissionID, String status,String reply, int propertyID, String propertyType, String location, String imageUrl, String name, String description, int price, boolean isApproved, boolean isAvailable, int userID, String phone, String email) {
        this.submissionID = submissionID;
        this.status = status;
        this.reply = reply;
        this.propertyID = propertyID;
        this.propertyType = propertyType;
        this.location = location;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isApproved = isApproved;
        this.isAvailable = isAvailable;
        this.userID = userID;
        this.phone = phone;
        this.email = email;
    }


    public int getSubmissionID() {
        return submissionID;
    }

    public String getStatus() {
        return status;
    }

    public String getReply() {
        return reply;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getUserID() {
        return userID;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
