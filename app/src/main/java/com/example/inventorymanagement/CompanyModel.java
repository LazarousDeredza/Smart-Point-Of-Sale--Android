package com.example.inventorymanagement;

public class CompanyModel {
    private String id;
    private String OwnerName;
    private String StoreName;
    private String Address;
    private String Phone;
    private String Whatsapp;
    private String Email;
    private String DateCreated;
    private String DateUpadted;
    private String info;


    public CompanyModel() {
    }

    public CompanyModel(String id, String ownerName, String storeName, String address, String phone, String whatsapp, String email, String dateCreated, String dateUpadted, String info) {
        this.id = id;
        OwnerName = ownerName;
        StoreName = storeName;
        Address = address;
        Phone = phone;
        Whatsapp = whatsapp;
        Email = email;
        DateCreated = dateCreated;
        DateUpadted = dateUpadted;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
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

    public String getWhatsapp() {
        return Whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        Whatsapp = whatsapp;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
