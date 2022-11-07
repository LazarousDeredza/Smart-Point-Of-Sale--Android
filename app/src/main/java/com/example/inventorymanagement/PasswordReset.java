package com.example.inventorymanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class PasswordReset extends AppCompatActivity {

    Button b,sub;

    MyDbHelper dbHelper;

    TextInputLayout  userLayout,emailLayout;
    EditText txtUsername,emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        setTitle("Password Recovery");

        setContentView(R.layout.activity_password_reset);

        userLayout = (TextInputLayout) findViewById(R.id.userLayout);
        emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);


        txtUsername = (EditText) findViewById(R.id.txtUsername);
        emailText = (EditText) findViewById(R.id.emailText);


        dbHelper=new MyDbHelper(this);
        b=findViewById(R.id.btn_clear);
        sub=findViewById(R.id.btn_add);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PasswordReset.this, Login.class);
                i.putExtra("userLevel","User");
                finish();
                startActivity(i);
            }
        });


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = txtUsername.getText().toString();
                String email = emailText.getText().toString();

                if (validate()) {
                    ArrayList<UserModel> userModel=dbHelper.resetPassword(username,email);

                    if (userModel.size()>0){
                        Toast.makeText(PasswordReset.this, "Your Password is : "+userModel.get(0).getPassword(), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(PasswordReset.this, "Account not Found", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PasswordReset.this, Login.class);
        i.putExtra("userLevel","User");
        finish();
        startActivity(i);

    }

    private boolean validate() {

        int flag = 0;

        if (txtUsername.getText().toString().trim().isEmpty()) {
            flag = 1;
            userLayout.setError("Cannot be Empty");
        } else
            userLayout.setErrorEnabled(false);

        if (emailText.getText().toString().trim().isEmpty()) {
            flag = 1;
            emailLayout.setError("Cannot be Empty");
        } else
            emailLayout.setErrorEnabled(false);


        if (flag == 1)
            return false;
        else
            return true;
    }
}