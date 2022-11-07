package com.example.inventorymanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateCustomer extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout iName, iaddr, iphone, ibalance, idate;
    EditText eName, eaddr, ephone, ebalance, edate;
    private int mYear, mMonth, mDay;

    MyDbHelper dbHelper;



    String s;

    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer);

        setTitle("Update Customer");
        s = getIntent().getStringExtra("Keys");

        iName = (TextInputLayout) findViewById(R.id.nameLayout);
        iaddr = (TextInputLayout) findViewById(R.id.addrLayout);
        iphone = (TextInputLayout) findViewById(R.id.phoneLayout);
        ibalance = (TextInputLayout) findViewById(R.id.balanceLayout);
        idate = (TextInputLayout) findViewById(R.id.dateLayout);

        eName = (EditText) findViewById(R.id.nameText);
        eaddr = (EditText) findViewById(R.id.addrText);
        ephone = (EditText) findViewById(R.id.phoneText);
        ebalance = (EditText) findViewById(R.id.balanceText);
        edate = (EditText) findViewById(R.id.dateText);

        edate.setInputType(InputType.TYPE_NULL);
        edate.setFocusable(false);
        edate.setOnClickListener(this);

        dbHelper = new MyDbHelper(this);

        ebalance.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});


        Modelcustomer modelcustomer = dbHelper.getCustomer(s);

        eName.setText(modelcustomer.getEiname());
        eaddr.setText(modelcustomer.getEaddr());
        ephone.setText(modelcustomer.getEphoneNo());
        ebalance.setText(modelcustomer.getEmoney());
        edate.setText(modelcustomer.getEdate());


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
                edate.setText(format.format(calendar.getTime()));
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


            AlertDialog.Builder alert = new AlertDialog.Builder(UpdateCustomer.this);
            alert.setTitle("Alert!!");
            alert.setMessage("Are you sure to delete this customer?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dbHelper.deleteCustomer(s);
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
        String sName = eName.getText().toString();
        String saddr = eaddr.getText().toString();
        String sphone = ephone.getText().toString();
        final String sbalance = ebalance.getText().toString();
        String sdate = edate.getText().toString();

        if (validate()) {


            dbHelper.updateCustomer(s, sName, sphone, sdate, sbalance, saddr);


            Modelcustomer model = dbHelper.getCustomer(s);
            // do your stuff here with value
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String logg = model.getLog() + "[" + format.format(calendar.getTime()) + "] "
                    + "Account updated. Balance :" + sbalance + "\n";


            dbHelper.updateCustomer(s, logg);


            // log
            final String item = sName;

            Calendar u = Calendar.getInstance();
            SimpleDateFormat t = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat y = new SimpleDateFormat("HH:mm");

            String log=t.format(u.getTime()) + "\n["
                                + y.format(u.getTime()) + "] " + item + " Customer Updated\n";


           String DateCreated=t.format(u.getTime()) + " " + y.format(u.getTime()) ;




            ArrayList<LogModel> logs=dbHelper.getLogs();

            String l=dbHelper.getLog(String.valueOf(logs.size()-1));
            String date = l.substring(0, 10);
            SimpleDateFormat fm = new SimpleDateFormat("HH:mm");

            String log1="";
            String DCreated=t.format(calendar.getTime()) + " " + y.format(calendar.getTime()) ;
            if (t.format(calendar.getTime()).equals(date)) {
                l = l.substring(11);
                log1 = t.format(u.getTime()) + "\n["
                        + y.format(u.getTime()) + "] " + item + " Customer Updated\n"+l;
                long id2 = dbHelper.insertToLog(
                        "" + log1,
                        "" + DCreated);
            }else{
                log1=t.format(u.getTime()) + "\n["
                        + y.format(u.getTime()) + "] " + item + " Customer Updated\n\n"+l;
                long id2 = dbHelper.insertToLog(
                        "" + log1,
                        "" + DCreated);
            }














            finish();
        }
    }

    public boolean validate() {
        int flag = 0;
        if (eName.getText().toString().trim().isEmpty()) {
            flag = 1;
            iName.setError("Cannot be Empty");
        } else
            iName.setErrorEnabled(false);

        if (ephone.getText().toString().trim().isEmpty()) {
            flag = 1;
            iphone.setError("Cannot be Empty");
        } else
            iphone.setErrorEnabled(false);
        if (edate.getText().toString().trim().isEmpty()) {
            flag = 1;
            idate.setError("Cannot be Empty");
        } else
            idate.setErrorEnabled(false);
        if (ebalance.getText().toString().trim().isEmpty()) {
            flag = 1;
            ibalance.setError("Cannot be Empty");
        } else
            ibalance.setErrorEnabled(false);
        if (flag == 1)
            return false;
        else
            return true;
    }

    public void clear(View v) {
        m_clear();
    }

    public void m_clear() {
        eName.setText("");
        eaddr.setText("");
        ephone.setText("");
        ebalance.setText("");
        edate.setText("");
    }
}
