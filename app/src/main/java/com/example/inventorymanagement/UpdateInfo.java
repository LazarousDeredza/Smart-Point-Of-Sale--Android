package com.example.inventorymanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class UpdateInfo extends AppCompatActivity {

    TextInputLayout name, storename, addr, ph1, ph2, more, email;
    EditText ename, estorename, eaddr, eph1, eph2, emore, eemail;
    private  MyDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        setTitle("Update Your Company Info");
        name = (TextInputLayout) findViewById(R.id.input_layout_name);
        storename = (TextInputLayout) findViewById(R.id.input_layout_store_name);

        addr = (TextInputLayout) findViewById(R.id.input_layout_addr);
        ph1 = (TextInputLayout) findViewById(R.id.input_layout_ph1);
        ph2 = (TextInputLayout) findViewById(R.id.input_layout_ph2);
        more = (TextInputLayout) findViewById(R.id.input_layout_more);
        email = (TextInputLayout) findViewById(R.id.input_layout_email);

        ename = (EditText) findViewById(R.id.input_name);
        estorename = (EditText) findViewById(R.id.input_store_name);

        eaddr = (EditText) findViewById(R.id.input_addr);
        eph1 = (EditText) findViewById(R.id.input_ph1);
        eph2 = (EditText) findViewById(R.id.input_ph2);
        emore = (EditText) findViewById(R.id.input_more);
        eemail = (EditText) findViewById(R.id.input_email);

        //initialise dphelper
        dbHelper=new MyDbHelper(this);


        CompanyModel companyModel=dbHelper.getCompany("1");
        ename.setText(companyModel.getOwnerName());
        estorename.setText(companyModel.getStoreName());
        eaddr.setText(companyModel.getAddress());
        eph1.setText(companyModel.getPhone());
        eph2.setText(companyModel.getWhatsapp());
        emore.setText(companyModel.getInfo());
        eemail.setText(companyModel.getEmail());


    }

    public void save(View view) {

        String sname = ""+ename.getText().toString();
        String sstorename = ""+estorename.getText().toString();

        String saddr = ""+eaddr.getText().toString();
        String sph1 = ""+eph1.getText().toString();
        String sph2 = ""+eph2.getText().toString();
        String smore = ""+emore.getText().toString();
        String semail = ""+eemail.getText().toString();
        if (validate()) {
            String DateCreated="";
            String DateUpdated="";

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
            DateCreated=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;


            CompanyModel n=new CompanyModel("",sname,sstorename,saddr,sph1,sph2,semail,dbHelper.getCompany("1").getDateCreated(),DateCreated,smore);

            //Save to database
            dbHelper.updateCompany(

                    "1",
                    n
            );

            Toast.makeText(this,"Company Updated Successfully",Toast.LENGTH_SHORT).show();



        }

    }

    public boolean validate() {
        int flag = 0;
        if (ename.getText().toString().trim().isEmpty()) {
            flag = 1;
            name.setError("Cannot be Empty");
        } else
            name.setErrorEnabled(false);
        if (estorename.getText().toString().trim().isEmpty()) {
            flag = 1;
            storename.setError("Cannot be Empty");
        } else
            storename.setErrorEnabled(false);

        if (eaddr.getText().toString().trim().isEmpty()) {
            flag = 1;
            addr.setError("Cannot be Empty");
        } else
            addr.setErrorEnabled(false);
        if (eph1.getText().toString().trim().isEmpty()) {
            flag = 1;
            ph1.setError("Cannot be Empty");
        } else
            ph1.setErrorEnabled(false);
        if (eph2.getText().toString().trim().isEmpty()) {
            flag = 1;
            ph2.setError("Cannot be Empty");
        } else
            ph2.setErrorEnabled(false);
        if (emore.getText().toString().trim().isEmpty()) {
            emore.setText("-");
        }
        if (eemail.getText().toString().trim().isEmpty()) {
            flag = 1;
            email.setError("Cannot be Empty");
        } else
            email.setErrorEnabled(false);
        if (flag == 1)
            return false;
        else
            return true;
    }



    public void logoutreg(View view) {
        signOut();
    }

    private void signOut() {

        // super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
                finish();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();

               /* Intent i = new Intent(Register.this, Login.class);
                finish();
                startActivity(i);

                */

    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}
