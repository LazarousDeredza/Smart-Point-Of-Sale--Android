package com.example.inventorymanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class UpdateItem extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout productLayout, batchLayout, quantityLayout,
            unitLayout, expireyLayout, sellingPriceLayout,
            supplierLayout, barcodeLayout, costPriceLayout, descriptionLayout;
    EditText productText, batchText, quantityText, expireyText, costPriceText, supplierText,
            descriptionText, barcodeText, sellingPriceText, unitText;
    private int mYear, mMonth, mDay;

    private  MyDbHelper dbHelper;
    private StockModel model;



    String s;

    String dateAdded,dateUpdated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        setTitle("Update Item");
        s = getIntent().getStringExtra("id");
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

        model=new StockModel();

        //initialise dBhelper
        dbHelper=new MyDbHelper(this);

        StockModel t=dbHelper.getStocksById(s);


        productText.setText(t.getProductName());
        batchText.setText(t.getBatchNumber());
        quantityText.setText(t.getQuantity());
        expireyText.setText(t.getExpiryDate());
        costPriceText.setText(t.getBuyingPrice());
        supplierText.setText(t.getSupplierCompany());
        descriptionText.setText(t.getDescription());
        barcodeText.setText(t.getBarcode());
        sellingPriceText.setText(t.getSellingPrice());
        unitText.setText(t.getUnit());
        dateAdded=t.getDateAdded();
        dateUpdated=t.getDateUpdated();


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.del_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.del) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateItem.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete this item?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dbHelper.deleteFromStock(s);
                            dialog.dismiss();


                            // log
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat fm = new SimpleDateFormat("HH:mm");


                            String log="";



                            ArrayList<LogModel> logs=dbHelper.getLogs();

                            String l=dbHelper.getLog(String.valueOf(logs.size()-1));
                            String date = l.substring(0, 10);



                            String DCreated=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;
                            if (format.format(calendar.getTime()).equals(date)) {
                                l = l.substring(11);
                                log = format.format(calendar.getTime()) + "\n["
                                        + fm.format(calendar.getTime()) + "] "+item +"  Deleted from stock\n"+l;
                                long id2 = dbHelper.insertToLog(
                                        "" + log,
                                        "" + DCreated);
                            }else{
                                log=format.format(calendar.getTime()) + "\n["
                                        + fm.format(calendar.getTime()) + "] "+item +"  Deleted from stock\n\n"+l;
                                long id2 = dbHelper.insertToLog(
                                        "" + log,
                                        "" + DCreated);
                            }







                            // dbHelper.insertToLog(log,date);
                            Log.d("delete" +" id= "+id,log);


                            Toast.makeText(UpdateItem.this, item + " has been removed succesfully.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            finish();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateInfo(View view) {
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

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
            dateUpdated=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;

        dbHelper.updateStock(s,productName,batchNo,quantity,expDate,
                supplier,costPrice,sellingPrice,barcode,unit,desc,
                dateAdded,dateUpdated);

            String log="";


            ArrayList<LogModel> logs=dbHelper.getLogs();

            String l=dbHelper.getLog(String.valueOf(logs.size()-1));
            String date = l.substring(0, 10);



            String DCreated=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;
            if (format.format(calendar.getTime()).equals(date)) {
                l = l.substring(11);
                log = format.format(calendar.getTime()) + "\n["
                        + fm.format(calendar.getTime()) + "] "+ productName + " Was Updated \n"+l;
                long id2 = dbHelper.insertToLog(
                        "" + log,
                        "" + DCreated);
            }else{
                log=format.format(calendar.getTime()) + "\n["
                        + fm.format(calendar.getTime()) + "] "+ productName + " Was Updated \n\n"+l;
                long id2 = dbHelper.insertToLog(
                        "" + log,
                        "" + DCreated);
            }










            Toast.makeText(this,"Item Updated Successfully",Toast.LENGTH_SHORT).show();

            finish();
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

}