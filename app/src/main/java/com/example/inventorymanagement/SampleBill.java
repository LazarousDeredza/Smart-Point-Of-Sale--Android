package com.example.inventorymanagement;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Map;

public class SampleBill extends AppCompatActivity {

    TextView ph1, ph2, storename, storeaddr, transid,  date,  ptname,  totamt, name;


    MyDbHelper dbHelper;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.invoice);
        getSupportActionBar().hide();

        ph1 = (TextView) findViewById(R.id.ph1);
        ph2 = (TextView) findViewById(R.id.ph2);
        storename = (TextView) findViewById(R.id.storename);
        storeaddr = (TextView) findViewById(R.id.storeaddr);
        transid = (TextView) findViewById(R.id.transid);
        date = (TextView) findViewById(R.id.date);
        ptname = (TextView) findViewById(R.id.ptname);

        totamt = (TextView) findViewById(R.id.totamt);
        name = (TextView) findViewById(R.id.name);

        dbHelper=new MyDbHelper(this);

        CompanyModel companyModel=dbHelper.getCompany("1");



                name.setText(companyModel.getOwnerName());
                storename.setText(companyModel.getStoreName());
                storeaddr.setText(companyModel.getAddress());
                ph1.setText("Ph: " + companyModel.getPhone());
                ph2.setText("Ph: " + companyModel.getWhatsapp());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date.setText(LocalDate.now().toString());
        }else{
            date.setText("00-00-0000");
        }


        transid.setText("1");
        ptname.setText("Jon Doe");
    }

}