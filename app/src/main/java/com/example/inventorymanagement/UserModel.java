package com.example.inventorymanagement;

public class UserModel {

    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String Password;
    private String Address;
    private String Phone;
    private String userLevel;
    private String Email;
    private String DateCreated;
    private String DateUpadted;
    private String log;


    public UserModel() {
    }

    public UserModel(String id, String firstName, String lastName, String userName,
                     String password, String address, String phone, String userLevel,
                     String email, String dateCreated, String dateUpadted, String log) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        Password = password;
        Address = address;
        Phone = phone;
        this.userLevel = userLevel;
        Email = email;
        DateCreated = dateCreated;
        DateUpadted = dateUpadted;
        this.log = log;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getDateUpadted() {
        return DateUpadted;
    }

    public void setDateUpadted(String dateUpadted) {
        DateUpadted = dateUpadted;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
