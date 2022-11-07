package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.inventorymanagement.MainActivity.fab;

public class summary extends Fragment {
    View v;
    Button sample, log;

    String s;

    TextView t;
    String uid;

    MyDbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.summary, container, false);
        getActivity().setTitle("Summary");
        MainActivity.flag = 1;
        sample = (Button) v.findViewById(R.id.sample);
      //  log = (Button) v.findViewById(R.id.log);

      /*  mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        du = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        du.keepSynced(true);*/

        t = (TextView) v.findViewById(R.id.title);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.hide();

        dbHelper=new MyDbHelper(getContext());
        sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SampleBill.class);
                startActivity(i);
            }
        });
       /* log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Log.class);
                startActivity(i);
            }
        });*/

        CompanyModel companyModel=dbHelper.getCompany("1");

      t.setText(companyModel.getStoreName());

      SummaryModel summaryModel=dbHelper.getSummary("1");

      if(summaryModel.getAmount() != null){
          android.util.Log.e("status ","Not Empty");
          Calendar c = Calendar.getInstance();
          SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
          SimpleDateFormat fm = new SimpleDateFormat("MM");

          String date = (String) summaryModel.getDate();
          String month = (String) summaryModel.getMonth();

          if (!format.format(c.getTime()).equals(date)) {


             // du.child("date").setValue(format.format(c.getTime()));
            //  du.child("count").setValue("0");
            //  du.child("amt").setValue("0.0");
              dbHelper.UpdateSummaryDateCountAndAmount(format.format(c.getTime()), "0" + "", "0.0" );

              android.util.Log.e("status ","!format.format(c.getTime()).equals(date)");


              ((TextView) (v.findViewById(R.id.todayamt))).setText("0.0");
              ((TextView) (v.findViewById(R.id.todaycount))).setText("0");


          }
          if (!fm.format(c.getTime()).equals(month)) {
             // du.child("month").setValue(fm.format(c.getTime()));
            //  du.child("monthcount").setValue("0");
            //  du.child("monthamt").setValue("0.0");


              dbHelper.UpdateSummaryMonthMonthCountAndMonthAmount(fm.format(c.getTime()),  "0",  "0.0");

              android.util.Log.e("status ","!fm.format(c.getTime()).equals(month)");

              ((TextView) (v.findViewById(R.id.mcount))).setText("0");
              ((TextView) (v.findViewById(R.id.mamt))).setText("0.0");


          }

              android.util.Log.e("status ","Case Else");

              ((TextView) (v.findViewById(R.id.todayamt))).setText(summaryModel.getAmount());
              ((TextView) (v.findViewById(R.id.todaycount))).setText(summaryModel.getCount());
              ((TextView) (v.findViewById(R.id.mcount))).setText(summaryModel.getMonthCount());
              ((TextView) (v.findViewById(R.id.mamt))).setText(summaryModel.getMonthAmount());


      }


     /*   du.child("Summary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("amt")) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
                    SimpleDateFormat fm = new SimpleDateFormat("MM");
                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                    };
                    Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                    String date = (String) dataSnapshot.child("date").getValue();
                    String month = (String) dataSnapshot.child("month").getValue();
                    if (!format.format(c.getTime()).equals(date)) {
                        du.child("date").setValue(format.format(c.getTime()));
                        du.child("count").setValue("0");
                        du.child("amt").setValue("0.0");
                        ((TextView) (v.findViewById(R.id.todayamt))).setText("0.0");
                        ((TextView) (v.findViewById(R.id.todaycount))).setText("0");
                    } else if (!fm.format(c.getTime()).equals(month)) {
                        du.child("month").setValue(fm.format(c.getTime()));
                        du.child("monthcount").setValue("0");
                        du.child("monthamt").setValue("0.0");
                        ((TextView) (v.findViewById(R.id.mcount))).setText("0");
                        ((TextView) (v.findViewById(R.id.mamt))).setText("0.0");
                    } else {
                        ((TextView) (v.findViewById(R.id.todayamt))).setText(map.get("amt"));
                        ((TextView) (v.findViewById(R.id.todaycount))).setText(map.get("count"));
                        ((TextView) (v.findViewById(R.id.mcount))).setText(map.get("monthcount"));
                        ((TextView) (v.findViewById(R.id.mamt))).setText(map.get("monthamt"));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });*/

    }
}
