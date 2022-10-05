package com.example.inventorymanagement;

public class Constants {

    //db name
    public static  final String DB_NAME="MY_RECORDS_DB";

    //db version
    public static  final int DB_VERSION=1;

    //Table Name for Company

    public static  final String Company="company";


    public  static final String C_ID="ID";
    public  static final String C_OWNER="OWRNER";
    public  static final String C_STORENAME="STORENAME";
    public  static final String C_PHONE="PHONE";
    public  static final String C_WHATSAPP="WHATSAPP";
    public  static final String C_ADDRESS="ADDRESS";
    public  static final String C_EMAIL="EMAIL";
    public  static final String C_ADDED="DATEADDED";
    public  static final String C_UPDATED="DATEUPDATED";


    //Create table query
    public static final String CREATE_TABLE="CREATE TABLE "+Company+" ("
            +C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +C_OWNER+" TEXT,"
            +C_STORENAME +" TEXT,"
            +C_PHONE+" TEXT,"
            +C_WHATSAPP +" TEXT,"
            +C_ADDRESS+" TEXT,"
            +C_EMAIL +" TEXT,"
            +C_ADDED+" TEXT,"
            +C_UPDATED +" TEXT"
            +" )";




    //Table Users

    public static  final String Users="users";



    public  static final String FIRSTNAME="FIRSTNAME";
    public  static final String LASTNAME="LASTNAME";
    public  static final String USERLEVEL="USERLEVEL";
    public  static final String USERNAME="USERNAME";
    public  static final String PASSWORD="PASSWORD";

    //Create table query
    public static final String CREATE_TABLE2="CREATE TABLE "+Users+" ("
            +C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +FIRSTNAME+" TEXT,"
            +LASTNAME +" TEXT,"
            +USERNAME+" TEXT,"
            +PASSWORD +" TEXT,"
            +C_PHONE+" TEXT,"
            +USERLEVEL +" TEXT,"
            +C_ADDRESS+" TEXT,"
            +C_EMAIL +" TEXT,"
            +C_ADDED+" TEXT,"
            +C_UPDATED +" TEXT"
            +" )";



    //Table Stock

    public static  final String Stock="stock";



    public  static final String productName = "productName";
    public  static final String batchNo = "batch";
    public  static final String quantity = "quantity";
    public  static final  String expDate = "expirey";
    public  static final  String sellingPrice = "sellingPrice";
    public  static final  String supplier = "supplier";
    public  static final  String barcode = "barcode";
    public  static final  String costPrice = "costPrice";
    public  static final  String desc = "description";
    public  static final String unit = "unit";



    //Create table query
    public static final String CREATE_TABLE3="CREATE TABLE "+Stock+" ("
            +C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +productName+" TEXT,"
            +batchNo +" TEXT,"
            +quantity+" TEXT,"
            +expDate +" TEXT,"
            +sellingPrice+" TEXT,"
            +supplier +" TEXT,"
            +barcode+" TEXT,"
            +costPrice +" TEXT,"
            +desc +" TEXT,"
            + unit +" TEXT,"
            +C_ADDED+" TEXT,"
            +C_UPDATED +" TEXT"
            +" )";





    //Table Stock

    public static  final String Log="log";

    //Create table query
    public static final String CREATE_TABLE4="CREATE TABLE "+Log+" ("
            +C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +desc +" TEXT,"
            +C_ADDED+" TEXT"
            +" )";



    //Table Bill

    public static  final String Bill="bill";

    public  static final String items = "items";
    public  static final String totalAmount = "totalAmount";

    //Create table query
    public static final String CREATE_TABLE5="CREATE TABLE "+Bill+" ("
            +C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +items+" TEXT,"
            +totalAmount +" TEXT,"
            +C_ADDED+" TEXT"
            +" )";

//Table Summary

    public static  final String Summary="summary";

    public  static final String count="count";
    public  static final String amount="amount";
    public  static final String  month="month";
    public  static final String  monthAmount="monthAmount";
    public  static final String  monthCount="monthCount";
    public  static final String  trans="trans";

    //Create table query
    public static final String CREATE_TABLE6="CREATE TABLE "+Summary+" ("
            +C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +items+" TEXT,"
            +count +" TEXT,"
            +C_ADDED +" TEXT,"
            +amount+" TEXT,"
            +month+" TEXT,"
            +monthAmount +" TEXT,"
            +monthCount+" TEXT,"
            +trans+" TEXT"
            +" )";

//Table Customer

    public static  final String Customer="customer";

    public  static final String  log="log";
    public  static final String  name="name";

    //Create table query
    public static final String CREATE_TABLE7="CREATE TABLE "+Customer+" ("
            +C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +name+" TEXT,"
            +C_PHONE +" TEXT,"
            +amount+" TEXT,"
            +C_ADDED+" TEXT,"
            +log +" TEXT,"
            +C_ADDRESS+" TEXT"
            +" )";


}
