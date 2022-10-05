package com.example.inventorymanagement;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    Button btnClearDB,   Register,size;
    private  MyDbHelper dbHelper;
    ArrayList<CompanyModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        btnClearDB=findViewById(R.id.btnClearDB);
        Register=findViewById(R.id.Register);
        size=findViewById(R.id.size);
        list=new ArrayList<>();


        //initialise dphelper
        dbHelper=new MyDbHelper(this);

        btnClearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.Clear(getApplicationContext());
            }
        });



    list=dbHelper.getAllCOMPANY();
    if(list.size()<1){

        Toast.makeText(Splash.this, "No Company Registered ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Splash.this,Register.class));
        finish();
    }else{
        startActivity(new Intent(Splash.this,ChooseUserType.class));
        finish();
    }






    }
}