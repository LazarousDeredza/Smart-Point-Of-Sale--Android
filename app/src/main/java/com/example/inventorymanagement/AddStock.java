package com.example.inventorymanagement;
import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddStock extends AppCompatActivity implements View.OnClickListener {


    TextInputLayout productLayout, batchLayout, quantityLayout,
            unitLayout, expireyLayout, sellingPriceLayout,
            supplierLayout, barcodeLayout, costPriceLayout, descriptionLayout;
    EditText productText, batchText, quantityText, expireyText, costPriceText, supplierText,
            descriptionText, barcodeText, sellingPriceText, unitText;

    private  MyDbHelper dbHelper;



    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstock);

        setTitle("Add New Stock");

        productLayout = (TextInputLayout) findViewById(R.id.productLayout);
        batchLayout = (TextInputLayout) findViewById(R.id.batchLayout);
        quantityLayout = (TextInputLayout) findViewById(R.id.quantityLayout);
        unitLayout = (TextInputLayout) findViewById(R.id.unitLayout);
        expireyLayout = (TextInputLayout) findViewById(R.id.expireyLayout);
        sellingPriceLayout = (TextInputLayout) findViewById(R.id.sellingPriceLayout);
        supplierLayout = (TextInputLayout) findViewById(R.id.supplierLayout);
        barcodeLayout = (TextInputLayout) findViewById(R.id.barcodeLayout);
        costPriceLayout = (TextInputLayout) findViewById(R.id.costPriceLayout);
        descriptionLayout = (TextInputLayout) findViewById(R.id.descriptionLayout);


        productText = (EditText) findViewById(R.id.productText);
        batchText = (EditText) findViewById(R.id.batchText);
        quantityText = (EditText) findViewById(R.id.quantityText);
        expireyText = (EditText) findViewById(R.id.expireyText);
        costPriceText = (EditText) findViewById(R.id.costPriceText);
        supplierText = (EditText) findViewById(R.id.supplierText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);
        barcodeText = (EditText) findViewById(R.id.barcodeText);
        sellingPriceText = (EditText) findViewById(R.id.sellingPriceText);
        unitText = (EditText) findViewById(R.id.unitText);


        sellingPriceText.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(2) });
        costPriceText.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(2) });

        expireyText.setInputType(InputType.TYPE_NULL);
        expireyText.setFocusable(false);
        expireyText.setOnClickListener(this);


        //initialise dBhelper
        dbHelper=new MyDbHelper(this);



    }

    public void addInfo(View v) {
        String productName = productText.getText().toString();
        String batchNo = batchText.getText().toString();
        String quantity = quantityText.getText().toString();
        String expDate = expireyText.getText().toString();
        String sellingPrice = sellingPriceText.getText().toString();
        String supplier = supplierText.getText().toString();
        String barcode = barcodeText.getText().toString();
        String costPrice = costPriceText.getText().toString();
        String desc = descriptionText.getText().toString();
        String unit = unitText.getText().toString();




        if (validate()) {
            String DateCreated="";
            String DateUpdated="";


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fm = new SimpleDateFormat("HH:mm");

            String log="";
            String DCreated=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;

            ArrayList<LogModel> logs=dbHelper.getLogs();

            if(logs.size()>0) {

                String l = dbHelper.getLog(String.valueOf(logs.size() - 1));
                String date = l.substring(0, 10);

                if (format.format(calendar.getTime()).equals(date)) {
                    l = l.substring(11);

                    log = "["+ fm.format(calendar.getTime()) + "] " + quantity + " x " + unit + " of " + productName + " Added to stock\n" ;

                    long id2 = dbHelper.insertToLog(
                            "" + log,
                            "" + DCreated);
                } else {

                    log = format.format(calendar.getTime()) + "\n["
                            + fm.format(calendar.getTime()) + "] " + quantity + " x " + unit + " of " + productName + " Added to stock\n\n" ;


                    long id2 = dbHelper.insertToLog(
                            "" + log,
                            "" + DCreated);
                }

            }else{
                log = format.format(calendar.getTime()) + "\n["
                        + fm.format(calendar.getTime()) + "] " + quantity + " x " + unit + " of " + productName + " Added to stock\n" ;

                long id2 = dbHelper.insertToLog(
                        "" + log,
                        "" + DCreated);
            }













            //Save to database
            long id=dbHelper.insertToStock(
                    ""+productName,
                    ""+batchNo,
                    ""+quantity,
                    ""+expDate,
                    ""+supplier,
                    ""+costPrice,
                    ""+sellingPrice,
                    ""+barcode,
                    ""+unit,
                    ""+desc,
                    ""+DateCreated,
                    ""+DateCreated);

            long id2=dbHelper.insertToLog(
                    ""+log,
                    ""+DateCreated
            );

            Toast.makeText(this,"Stock Added Successfully",Toast.LENGTH_SHORT).show();
            m_clear();

        }



    }

    public boolean validate() {
        int flag = 0;
        if (productText.getText().toString().trim().isEmpty()) {
            flag = 1;
            productLayout.setError("Cannot be Empty");
        } else
            productLayout.setErrorEnabled(false);
        if (quantityText.getText().toString().trim().isEmpty()) {
            flag = 1;
            quantityLayout.setError("Cannot be Empty");
        } else
            quantityLayout.setErrorEnabled(false);
        if (barcodeText.getText().toString().trim().isEmpty()) {
            flag = 1;
            barcodeLayout.setError("Cannot be Empty");
        } else
            barcodeLayout.setErrorEnabled(false);
        if (expireyText.getText().toString().trim().isEmpty()) {
            flag = 1;
            expireyLayout.setError("Cannot be Empty");
        } else
            expireyLayout.setErrorEnabled(false);
        if (supplierText.getText().toString().trim().isEmpty()) {
            flag = 1;
            supplierLayout.setError("Cannot be Empty");
        } else
            supplierLayout.setErrorEnabled(false);
        if (unitText.getText().toString().trim().isEmpty()) {
            flag = 1;
            unitLayout.setError("Cannot be Empty");
        } else
            unitLayout.setErrorEnabled(false);
        if (costPriceText.getText().toString().trim().isEmpty()) {
            flag = 1;
            costPriceLayout.setError("Cannot be Empty");
        } else
            costPriceLayout.setErrorEnabled(false);
        if (sellingPriceText.getText().toString().trim().isEmpty()) {
            flag = 1;
            sellingPriceLayout.setError("Cannot be Empty");
        } else
            sellingPriceLayout.setErrorEnabled(false);

        if (batchText.getText().toString().trim().isEmpty()) {
            flag = 1;
            batchLayout.setError("Cannot be Empty");
        } else
            batchLayout.setErrorEnabled(false);


        if (flag == 1)
            return false;
        else
            return true;
    }

    public void clear(View v) {
        m_clear();
    }

    public void m_clear() {
        productText.setText(null);
        batchText.setText(null);
        quantityText.setText(null);
        expireyText.setText(null);
        costPriceText.setText(null);
        supplierText.setText(null);
        descriptionText.setText(null);
        barcodeText.setText(null);
        sellingPriceText.setText(null);
        unitText.setText(null);
    }

    @Override
    public void onClick(View v) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
                expireyText.setText(format.format(calendar.getTime()));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
}
