package com.example.inventorymanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Newuser extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout firstnameLayout,lastnameLayout, userLayout, phoneLayout,
            userlevelLayout,emailLayout,passLayout, confirmpassLayout, addrLayout;
    EditText firstnameText,lastnameText,txtUsername,
            phoneText,emailText, passText, pass1Text, addrText;

    RadioButton radioAdmin,radioUser;
    Button btn_add;
    MyDbHelper dbHelper;
    String level,task="";
    String id;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);

        dbHelper = new MyDbHelper(this);

        Bundle bundle= getIntent().getExtras();
        level=bundle.getString("userLevel");
        task=bundle.getString("task");



        firstnameLayout = (TextInputLayout) findViewById(R.id.firstnameLayout);
        lastnameLayout = (TextInputLayout) findViewById(R.id.lastnameLayout);
        userLayout = (TextInputLayout) findViewById(R.id.userLayout);
        phoneLayout = (TextInputLayout) findViewById(R.id.phoneLayout);
        userlevelLayout = (TextInputLayout) findViewById(R.id.userlevelLayout);
        emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);
        passLayout = (TextInputLayout) findViewById(R.id.passLayout);
        confirmpassLayout = (TextInputLayout) findViewById(R.id.confirmpassLayout);
        addrLayout = (TextInputLayout) findViewById(R.id.addrLayout);



        firstnameText = (EditText) findViewById(R.id.firstnameText);
        lastnameText = (EditText) findViewById(R.id.lastnameText);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        phoneText = (EditText) findViewById(R.id.phoneText);
        emailText = (EditText) findViewById(R.id.emailText);
        passText = (EditText) findViewById(R.id.passText);
        pass1Text = (EditText) findViewById(R.id.pass1Text);
        addrText = (EditText) findViewById(R.id.addrText);

        btn_add=findViewById(R.id.btn_add);

        radioAdmin=findViewById(R.id.radioAdmin);
        radioUser=findViewById(R.id.radioUser);

        if(level.equals("user")){
            userlevelLayout.setVisibility(View.GONE);
        }else{
            userlevelLayout.setVisibility(View.VISIBLE);
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
       // edate.setText(format.format(c.getTime()));


        if(Objects.equals(task, "register")){
            setTitle("Register User");
            btn_add.setText("SAVE");

        }else{
            setTitle("Update User");
            btn_add.setText("UPDATE");
            id= bundle.getString("id");
            fillData(id);
        }


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_add.getText().equals("SAVE")){
                    if(level.equals("user")){
                        addInfo("user");
                    }else{
                        if(!radioAdmin.isChecked()&&!radioUser.isChecked()){
                            Toast.makeText(Newuser.this, "Please select user level", Toast.LENGTH_SHORT).show();
                        }else if(radioAdmin.isChecked()){
                            addInfo("admin");
                        }else if(radioUser.isChecked()){
                            addInfo("user");
                        }
                    }
                }else{
                    if(level.equals("user")){
                        updateInfo("user",id);
                    }else {
                        if(!radioAdmin.isChecked()&&!radioUser.isChecked()) {
                        Toast.makeText(Newuser.this, "Please select user level", Toast.LENGTH_SHORT).show();
                         }else
                        {
                            if(radioAdmin.isChecked()){
                                updateInfo("admin",id);
                            }else if(radioUser.isChecked()){
                                updateInfo("user",id);
                            }
                        }
                    }



                }
            }
        });


    }

    private void updateInfo(String level, String id) {


        String firstname = firstnameText.getText().toString();
        String lastname= lastnameText.getText().toString();
        String username = txtUsername.getText().toString();
        String phone = phoneText.getText().toString();
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();
        String addr = addrText.getText().toString();

        if (validate()) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String log = "[" + format.format(calendar.getTime()) + " ] "+firstname +" " +lastname+ " User Updated. "+"\n";

            String  DateCreated=format.format(calendar.getTime());

            UserModel user= dbHelper.getUser(id);
            String lo=user.getLog()+log;

            dbHelper.updateUserLog(id,lo);

           dbHelper.updateUser(
                    id,
                    firstname,
                    lastname,
                    username,
                    pass,
                    phone,
                    level,
                    addr,
                    email,
                    ""+DateCreated);



            long id2 = dbHelper.insertToLog(
                    "" + log,
                    "" + DateCreated
            );


            Toast.makeText(this, "User Profile Updated", Toast.LENGTH_SHORT).

                    show();

        } else {
            Toast.makeText(this, "Sorry.. Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillData(String id) {
        Log.e("id =................",id);
        UserModel user=dbHelper.getUser(id);

        firstnameText.setText(user.getFirstName());
        lastnameText.setText(user.getLastName());
        txtUsername.setText(user.getUserName());
        phoneText.setText(user.getPhone());
        emailText.setText(user.getEmail());
        passText.setText(user.getPassword());
        pass1Text.setText(user.getPassword());
        addrText.setText(user.getAddress());

        if(user.getUserLevel().equals("user")){
            radioUser.setChecked(true);
        }else if(user.getUserLevel().equals("admin")){
            radioAdmin.setChecked(true);
        }

    }

    public void addInfo(String level) {


        String firstname = firstnameText.getText().toString();
        String lastname= lastnameText.getText().toString();
        String username = txtUsername.getText().toString();
        String phone = phoneText.getText().toString();
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();
        String addr = addrText.getText().toString();

        if (validate()) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String log = "[" + format.format(calendar.getTime()) + " ] "+firstname +" " +lastname+ " User Added. "+"\n";

            String  DateCreated=format.format(calendar.getTime());

            long id=dbHelper.insertToUsers(
                    firstname,
                    lastname,
                    username,
                    pass,
                    phone,
                    level,
                    addr,
                    email,
                    ""+DateCreated,
                    ""+DateCreated,
                    "Account Created at "+DateCreated+"\n");



            long id2 = dbHelper.insertToLog(
                    "" + log,
                    "" + DateCreated
            );


            Toast.makeText(this, "User is saved", Toast.LENGTH_SHORT).

                    show();

            m_clear();
        } else {
            Toast.makeText(this, "Sorry.. Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validate() {

        int flag = 0;
        if (firstnameText.getText().toString().trim().isEmpty()) {
            flag = 1;
            firstnameLayout.setError("Cannot be Empty");
        } else
            firstnameLayout.setErrorEnabled(false);

        if (lastnameText.getText().toString().trim().isEmpty()) {
            flag = 1;
            lastnameLayout.setError("Cannot be Empty");
        } else
            lastnameLayout.setErrorEnabled(false);
        if (txtUsername.getText().toString().trim().isEmpty()) {
            flag = 1;
            userLayout.setError("Cannot be Empty");
        } else
            userLayout.setErrorEnabled(false);
        if (phoneText.getText().toString().trim().isEmpty()) {
            flag = 1;
            phoneLayout.setError("Cannot be Empty");
        } else
            phoneLayout.setErrorEnabled(false);
        if (emailText.getText().toString().trim().isEmpty()) {
            flag = 1;
            emailLayout.setError("Cannot be Empty");
        } else
            emailLayout.setErrorEnabled(false);
        if (passText.getText().toString().trim().isEmpty()) {
            flag = 1;
            passLayout.setError("Cannot be Empty");
        } else
            passLayout.setErrorEnabled(false);
        if (pass1Text.getText().toString().trim().isEmpty()) {
            flag = 1;
            confirmpassLayout.setError("Cannot be Empty");
        } else
            confirmpassLayout.setErrorEnabled(false);
       /* if (addrText.getText().toString().trim().isEmpty()) {
            flag = 1;
            addrLayout.setError("Cannot be Empty");
        } else
            addrLayout.setErrorEnabled(false);*/

        if (!(passText.getText().toString().trim().equals(pass1Text.getText().toString().trim()))) {
            flag = 1;
            confirmpassLayout.setError("Password Mismatch");
        } else
            confirmpassLayout.setErrorEnabled(false);

        if (flag == 1)
            return false;
        else
            return true;
    }

    public void clear(View v) {
        m_clear();
    }

    public void m_clear() {

        firstnameText.setText("");
        lastnameText.setText("");
        txtUsername.setText("");
        phoneText.setText("");
        emailText.setText("");
        passText.setText("");
        pass1Text.setText("");
        addrText.setText("");
        radioUser.setChecked(false);
        radioAdmin.setChecked(false);
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
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
             //   edate.setText(format.format(calendar.getTime()));

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
}