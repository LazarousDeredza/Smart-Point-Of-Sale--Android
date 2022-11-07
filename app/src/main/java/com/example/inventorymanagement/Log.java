package com.example.inventorymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Log extends AppCompatActivity {

    MyDbHelper dbHelper;
    ArrayList<LogModel> logs=new ArrayList<>();
    TextView textView;

    String log="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        dbHelper=new MyDbHelper(this);
        textView=findViewById(R.id.textView);

        logs=dbHelper.getLogs();

        for (int i =0;i<logs.size();i++){
            log=log+logs.get(i).getLog();
        }

        if(log.isEmpty()){
            textView.setText(" Nothing to show");
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        }else{
        textView.setText(log);
        }

    }
}