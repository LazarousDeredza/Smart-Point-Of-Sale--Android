package com.example.inventorymanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Trans extends AppCompatActivity implements View.OnClickListener {

    View v;
    String uid;

    AutoCompleteTextView act, act2;
    AutoAdapter adapter;
    AutoCustAdapter autoCustAdapter;
    ListAdapter listadapter;
    ArrayList<StockModel> models = new ArrayList<>();
    TextInputLayout date;
    EditText edate, ept, edoc, eage, due;
    private int mYear, mMonth, mDay;
    ArrayList<ListModel> listmodel = new ArrayList<>();
    ArrayList<Modelcustomer> modelcustomers = new ArrayList<>();
    ListView lv;
    Button btn, custbtn;
    static Button gen;
    LinearLayout linearLayout;
    boolean set = false;
    static TextView totamt;
    static double totamount;

    MyDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trans);
        setTitle("Transaction");

        getSupportActionBar().hide();

        date = (TextInputLayout) findViewById(R.id.dateLayout);

        totamount = 0.0;

        edate = (EditText) findViewById(R.id.dateText);

        due = (EditText) findViewById(R.id.amtdue);
        totamt = (TextView) findViewById(R.id.totamt);

        edate.setInputType(InputType.TYPE_NULL);
        edate.setFocusable(false);
        edate.setOnClickListener(this);
        act = (AutoCompleteTextView) findViewById(R.id.act);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        edate.setText(format.format(c.getTime()));

        dbHelper = new MyDbHelper(this);


        btn = (Button) findViewById(R.id.btnadd);
        gen = (Button) findViewById(R.id.genbtn);
        custbtn = (Button) findViewById(R.id.regcust);
        linearLayout = (LinearLayout) findViewById(R.id.cust);

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    if (custValidate()) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(Trans.this);
                        alert.setTitle("Confirm");
                        alert.setMessage("Generate Bill?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Updating customerdetail
                                if (set) {
                                    updateCust(autoCustAdapter.detcust()[0], due.getText().toString(),
                                            autoCustAdapter.detcust()[1]);
                                }

                                int size = listmodel.size();
                                String gdate = edate.getText().toString().trim();


                                String[] itemlist = new String[size];
                                String items = "";

                                int itemcnt = 0;
                                for (int i = 0; i < size; i++) {
                                    String amt=String.valueOf((Double.valueOf(listmodel.get(i).getAmount())  / Double.valueOf(listmodel.get(i).getQty())));
                                    itemlist[i] = listmodel.get(i).getMed() + "©" + listmodel.get(i).getUnit() + "©"
                                            + listmodel.get(i).getBatch() + "©" + listmodel.get(i).getQty() + "©"
                                            + amt  + "©" + listmodel.get(i).getAmount();

                                    if (items.equals("")){
                                        items = items + itemlist[i];
                                    }
                                    else{
                                        items = items + "®" + itemlist[i];
                                    }

                                    itemcnt += Integer.parseInt(listmodel.get(i).getQty());

                                    // updating item quantity
                                   /* updateQty(listmodel.get(i).getKey(), listmodel.get(i).getQty(),
                                            listmodel.get(i).getOrgqty());*/
                                    updateQty(listmodel.get(i).getKey(), listmodel.get(i).getQty());
                                }

                                final String gtotamt = totamt.getText().toString();

                                // save to Database


                                String dateCreated=edate.getText().toString().replaceAll("-","").trim();



                              /*  Calendar cl = Calendar.getInstance();
                                SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat fn = new SimpleDateFormat("HH:mm");
                                dateCreated=ft.format(cl.getTime()) + " " + fn.format(cl.getTime()) ;*/

                                // Log.e("dateeee",dateCreated);

                                String biller=dbHelper.getUser(dbHelper.getLogged("1").getUuid()).getFirstName()+
                                        " "+dbHelper.getUser(dbHelper.getLogged("1").getUuid()).getLastName();
                                String k = String.valueOf(dbHelper.insertToBill(items, dateCreated, gtotamt,biller));

                               BillModel m=dbHelper.getBill(k);
                               // Log.e("billDetails","ID= "+m.getId()+" Date = "+m.getDateBilled()+" items= "+m.getItems()+" Tot = "+m.getTotalAmount());





                                final int icount = itemcnt;
                                final double amt = totamount;


                                SummaryModel model = dbHelper.getSummary("1");

                                Log.e("summary","Amount =" +model.getAmount()+" MonthAmount= "+model.getMonthAmount()+"" +
                                        " Month= "+model.getMonth()+" Month Count= "+model.getMonthCount()+" Count ="+ model.getCount()+" "+"" +
                                        " Trans ID= "+model.getTrans()+" Date = "+model.getDate());



                                if (model.getTrans() != null) {
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
                                    SimpleDateFormat fm = new SimpleDateFormat("MM");
                                    String date = (String) model.getDate();
                                    String month = (String) model.getMonth();
                                    int tcount = Integer
                                            .parseInt((String) model.getTrans()) + 1;
                                    int count = Integer
                                            .parseInt((String) model.getCount());
                                    int mcount = Integer
                                            .parseInt((String) model.getMonthCount());
                                    double value = Double
                                            .parseDouble((String) model.getAmount());
                                    double mvalue = Double
                                            .parseDouble((String) model.getMonthAmount());
                                    value = roundOff(value);
                                    mvalue = roundOff(mvalue);
                                    if (format.format(c.getTime()).equals(date)) {
                                        dbHelper.UpdateSummaryCountAndAmount((count + icount) + "", roundOff(value + amt) + "");

                                    } else {

                                        dbHelper.UpdateSummaryDateCountAndAmount(format.format(c.getTime()), icount + "", amt + "");
                                    }
                                    if (fm.format(c.getTime()).equals(month)) {

                                        dbHelper.UpdateSummaryMonthCountAndMonthAmount((mcount + icount) + "", roundOff(mvalue + amt) + "");
                                    } else {


                                        dbHelper.UpdateSummaryMonthMonthCountAndMonthAmount(fm.format(c.getTime()), icount + "", amt + "");
                                    }

                                    //TODO
                                    //dbi.child("Bill").child(k).child("gtranid").setValue(tcount + "");
                                    dbHelper.updateBillId(k,tcount + "");
                                    dbHelper.updateSummaryId(tcount + "", "1");


                                } else {


                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
                                    SimpleDateFormat fm = new SimpleDateFormat("MM");

                                    dbHelper.insertToSummary(format.format(c.getTime()), icount + "", amt + "",
                                            fm.format(c.getTime()), amt + "", icount + "", "1");


                                    dbHelper.updateBillId(k, "1");


                                }


                                // log

                                ArrayList<LogModel> logs=dbHelper.getLogs();

                                String l=dbHelper.getLog(String.valueOf(logs.size()-1));
                                String date = l.substring(0, 10);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat fm = new SimpleDateFormat("HH:mm");

                                String log="";
                                String DCreated=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;
                                if (format.format(calendar.getTime()).equals(date)) {
                                    l = l.substring(11);
                                    log = format.format(calendar.getTime()) + "\n["
                                            + fm.format(calendar.getTime())
                                            + "] Transaction. Amount:" + gtotamt + "\n" + l;
                                    long id2 = dbHelper.insertToLog(
                                            "" + log,
                                            "" + DCreated);
                                }else{
                                     log=format.format(calendar.getTime()) + "\n["
                                             + fm.format(calendar.getTime())
                                             + "] Transaction. Amount:" + gtotamt + "\n\n" + l;
                                    long id2 = dbHelper.insertToLog(
                                            "" + log,
                                            "" + DCreated);
                                }



/*
                                String log=format.format(calendar.getTime()) + "\n["
                                        + fm.format(calendar.getTime()) + "] Transaction. Amount: " + gtotamt + "\n";



                                    long id2=dbHelper.insertToLog(
                                        ""+log,
                                        ""+DCreated);*/




                                Toast.makeText(Trans.this, "Bill Saved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Bill.class);
                                i.putExtra("key", k);
                                startActivity(i);
                                finish();
                                // clear();
                            }
                        });
                        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alert.show();
                    } else {
                        Toast.makeText(Trans.this, "Regular Customer Details not properly entered", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(Trans.this, "Fill Details Properly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addbtn();
            }
        });

        custbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.setVisibility(View.GONE);
                    custbtn.setText("REGULAR CUSTOMER");
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    custbtn.setText("NOT REG. CUSTOMER");
                }

            }
        });


        models.clear();

        ArrayList<StockModel> list = dbHelper.getAllStocks();

        for (int i = 0; i < list.size(); i++) {
            StockModel stockModel = new StockModel(list.get(i).getId(), list.get(i).getProductName(),
                    list.get(i).getBatchNumber(), list.get(i).getQuantity(), list.get(i).getExpiryDate(),
                    list.get(i).getSupplierCompany(), list.get(i).getBuyingPrice(),
                    list.get(i).getSellingPrice(), list.get(i).getBarcode(),
                    list.get(i).getUnit(), list.get(i).getDescription(), list.get(i).getDateAdded(),
                    list.get(i).getDateUpdated());
            models.add(stockModel);
        }

        act.setThreshold(1);
        adapter = new AutoAdapter(Trans.this, R.layout.activity_trans, R.id.item_name, models);
        act.setAdapter(adapter);


        modelcustomers.clear();

        ArrayList<Modelcustomer> m = dbHelper.getAllCUSTOMERS();

        for (int i = 0; i < m.size(); i++) {
            Modelcustomer modelCustomer = new Modelcustomer(
                    m.get(i).getEiname(),
                    m.get(i).getEphoneNo(),
                    m.get(i).getEdate(),
                    m.get(i).getEmoney(),
                    m.get(i).getEaddr(),
                    m.get(i).getKey());
            modelcustomers.add(modelCustomer);
        }


        act2 = (AutoCompleteTextView) findViewById(R.id.actcust);
        act2.setThreshold(1);
        autoCustAdapter = new AutoCustAdapter(Trans.this, R.layout.activity_trans, R.id.item_name, modelcustomers);
        act2.setAdapter(autoCustAdapter);


        listmodel.clear();
        lv = (ListView) findViewById(R.id.list);
        listadapter = new ListAdapter(Trans.this, R.layout.list_items, listmodel);
        lv.setAdapter(listadapter);
        Utility.setListViewHeightBasedOnChildren(lv);
    }



    // Functions

    public boolean validate() {
        int flag = 0;
        if (edate.getText().toString().trim().isEmpty()) {
            flag = 1;
            date.setError("Cannot be Empty");
        } else
            date.setErrorEnabled(false);
        if (listmodel.size() == 0) {
            flag = 1;
        }
        if (flag == 1)
            return false;
        else
            return true;
    }

    public boolean custValidate() {
        if (!autoCustAdapter.detcust()[0].equals("") && !due.getText().toString().equals("")
                && autoCustAdapter.detcust()[2].equals(act2.getText().toString())) {
            set = true;
            return true;

        } else if (autoCustAdapter.detcust()[0].equals("") && due.getText().toString().equals("")
                && act2.getText().toString().equals("")) {
            set = false;
            return true;
        } else {
            return false;
        }
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

    protected void addbtn() {

        if (listmodel.size() < 8) {
            final String item = adapter.det()[0];
            final String batch = adapter.det()[1];
            final String expdate = adapter.det()[2];
            final String productid = adapter.det()[3];
            final String quanity = adapter.det()[4];
            final String unitSize = adapter.det()[5];
            final String amount = adapter.det()[7];
            final String barcode=adapter.det()[6];
            final String match = item + " (" +barcode + ") "+unitSize;



            if (!item.equals("")  && match.equals(act.getText().toString())) {

                LayoutInflater layoutInflater = LayoutInflater.from(Trans.this);
                View promptView = layoutInflater.inflate(R.layout.input_dia, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Trans.this);
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                alertDialogBuilder.setCancelable(false).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String qt = "";
                        qt = editText.getText().toString().trim();
                        if (!qt.equals("") && !qt.equals("0")) {

                            if (Integer.parseInt(qt) <= Integer.parseInt(quanity)) {

                                double d = (Double.valueOf(qt)  * Double.valueOf(amount));
                                d = roundOff(d);
                                String amount = String.valueOf(d);
                                totamount = totamount + Double.valueOf(amount);
                                totamount = roundOff(totamount);
                                ListModel list = new ListModel(item, qt, amount, batch, expdate, productid, quanity, unitSize);
                                Log.e("tag","item :"+list.getMed() +" batsh :"+list.getBatch()+"  Esp Date :"+list.getExpdate()+
                                        " ID : "+list.getKey()+" Quantity :"+list.getQty()+" Original quantity :"+list.getOrgqty()+" Unit sise :"+list.getUnit()+" Amount :"+list.getAmount());
                                listmodel.add(list);
                                listadapter.notifyDataSetChanged();
                                act.setText("");
                                totamt.setText(String.valueOf(totamount));
                                gen.setText("GENERATE BILL ($" + totamount + ")");

                                Utility.setListViewHeightBasedOnChildren(lv);

                                dialog.dismiss();

                            } else {

                                Toast.makeText(Trans.this, "Quantity is not in stock", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(Trans.this, "Enter valid quantity", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alert.show();

            } else {
                Toast.makeText(Trans.this, "Select Item", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Trans.this, "No further items can be added", Toast.LENGTH_SHORT).show();
        }

    }

    public void clear() {
        edate.setText("");
        ept.setText("");
        edoc.setText("");
        eage.setText("");
        totamt.setText("");
        totamount = 0.0;
        listmodel.clear();
        act2.setText("");
        due.setText("");
        listadapter.notifyDataSetChanged();
    }

    public void updateQty(String key, String qty) {
        int oqt = Integer.parseInt(dbHelper.getQuantity(key));


        int qt = Integer.parseInt(qty);
        oqt = oqt - qt;
        dbHelper.deductStock(key, String.valueOf(oqt));

        adapter.notifyDataSetChanged();
    }


 //TODO
    public void updateCust(String key, String amt, String orgamt) {

        double org = roundOff(Double.valueOf(orgamt));
        double am = roundOff(Double.valueOf(amt));
        double samt = totamount - am;
        org = org + samt;
        final String k = key;
        final double tot = totamount;
        final double balance = org;
        final double paid = am;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());




        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String PrevLog= dbHelper.getLog(key);
        String log ="[" + format.format(calendar.getTime())
                + "] Transaction. Total:" + tot + " Paid:" + paid + " Balance:" + balance + "\n";

        dbHelper.updateCustomer(key,String.valueOf(org),formattedDate,PrevLog.concat(log));


    }

    public double roundOff(double d) {
        d = Math.round(d * 100.0);
        d = d / 100.0;
        return d;
    }

}
