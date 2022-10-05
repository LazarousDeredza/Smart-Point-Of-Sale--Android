package com.example.inventorymanagement;

public class StockModel {
    private String id,productName, batchNumber, quantity,
            expiryDate,supplierCompany,buyingPrice,sellingPrice,
            barcode,unit,description ,dateAdded,dateUpdated;

    public StockModel() {
    }

    public StockModel(String id, String productName, String batchNumber, String quantity,
                      String expiryDate, String supplierCompany, String buyingPrice,
                      String sellingPrice, String barcode, String unit, String description,
                      String dateAdded, String dateUpdated) {
        this.id = id;
        this.productName = productName;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.supplierCompany = supplierCompany;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.barcode = barcode;
        this.unit = unit;
        this.description = description;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSupplierCompany() {
        return supplierCompany;
    }

    public void setSupplierCompany(String supplierCompany) {
        this.supplierCompany = supplierCompany;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Model{" +
                "productName='" + productName + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", quantity='" + quantity + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", supplierCompany='" + supplierCompany + '\'' +
                ", buyingPrice='" + buyingPrice + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", barcode='" + barcode + '\'' +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
