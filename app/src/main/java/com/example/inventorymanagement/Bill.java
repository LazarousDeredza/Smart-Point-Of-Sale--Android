package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class Bill extends AppCompatActivity {

    TextView ph1, ph2, storename, storeaddr, transid, date,  totamt, name;
    TextView medname[], unit[], batch[], qty[],  exp[], amt[];
    String ids[];


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

        totamt = (TextView) findViewById(R.id.totamt);
        name = (TextView) findViewById(R.id.name);


        dbHelper=new MyDbHelper(this);

        medname = new TextView[8];
        unit = new TextView[8];
        batch = new TextView[8];
        qty = new TextView[8];
       // rate = new TextView[8];
        exp = new TextView[8];
        amt = new TextView[8];

        ids = new String[] { "medname", "unit", "batch", "qty",  "exp", "amt" };
        for (int j = 1; j <= 8; j++) {
            medname[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[0] + j, "id", getPackageName()));
            unit[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[1] + j, "id", getPackageName()));
            batch[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[2] + j, "id", getPackageName()));
            qty[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[3] + j, "id", getPackageName()));
           // rate[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[4] + j, "id", getPackageName()));
            exp[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[4] + j, "id", getPackageName()));
            amt[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[5] + j, "id", getPackageName()));
        }



        CompanyModel companyModel=dbHelper.getCompany("1");

                name.setText(companyModel.getOwnerName());
                storename.setText(companyModel.getStoreName());

                storeaddr.setText(companyModel.getAddress());
                ph1.setText("Ph: " + companyModel.getPhone());
                ph2.setText("Ph: " + companyModel.getWhatsapp());



                Intent intent = getIntent();
                String id = intent.getStringExtra("key");
                BillModel bill=dbHelper.getBill(id);


                //Log.e("billDate","Date = "+bill.getDateBilled());

        String DateCreated="";

        Calendar r = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat m = new SimpleDateFormat("HH:mm");
        DateCreated=f.format(r.getTime()) + " " + m.format(r.getTime()) ;




                totamt.setText(bill.getTotalAmount());
                date.setText(DateCreated);
                transid.setText(bill.getId());
                String items =bill.getItems();
                StringTokenizer st = new StringTokenizer(items, "®");
                int k = -1;
                while (st.hasMoreTokens()) {
                    k++;
                    // Toast.makeText(Bill.this,""+k , Toast.LENGTH_SHORT).show();
                    StringTokenizer sti = new StringTokenizer(st.nextToken(), "©");
                    while (sti.hasMoreTokens()) {
                        medname[k].setText(sti.nextToken());
                        unit[k].setText( sti.nextToken());
                        batch[k].setText(sti.nextToken());
                        qty[k].setText(sti.nextToken());
                        //rate[k].setText(sti.nextToken());
                        exp[k].setText(sti.nextToken());
                        amt[k].setText(sti.nextToken());
                    }
                }

    }

}
