package com.example.inventorymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserLog extends AppCompatActivity {

    String uid;

    TextView t;

    MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_log);
        Intent i = getIntent();
        String k = i.getStringExtra("k");
        t = (TextView) findViewById(R.id.textView);

        dbHelper=new MyDbHelper(this);



        UserModel user=dbHelper.getUser(k);

        t.setText(user.getLog());

    }
}