package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerLog extends AppCompatActivity {

    String uid;

    TextView t;

    MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log);
        Intent i = getIntent();
        String k = i.getStringExtra("k");
        t = (TextView) findViewById(R.id.textView);

        dbHelper=new MyDbHelper(this);


        Modelcustomer modelcustomer=dbHelper.getCustomer(k);

                t.setText(modelcustomer.getLog());

    }
}
