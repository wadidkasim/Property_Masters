package com.example.propertymasters.models;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private String profilePicture;
    private int roleId;
    private String roleName;

    public User(int userId, String userName, String email, String phone, String profilePicture, int roleId, String roleName) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
