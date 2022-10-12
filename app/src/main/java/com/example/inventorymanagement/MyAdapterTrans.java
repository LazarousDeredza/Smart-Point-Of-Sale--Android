package com.example.inventorymanagement;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class MyAdapterTrans extends RecyclerView.Adapter<MyViewHolderTrans> implements Filterable {

    Context c;

    String uid;
    ArrayList<Modeltrans> models, filterList;

    MyDbHelper dbHelper;
    CustomFilterTrans filter;

    BillModel billModel=new BillModel();

    public MyAdapterTrans(Context c, ArrayList<Modeltrans> models) {

        this.c = c;
        this.models = models;
        this.filterList = models;
    }

    @Override
    public MyViewHolderTrans onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.transcard, parent, false);
        return new MyViewHolderTrans(v);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolderTrans holder, @SuppressLint("RecyclerView") final int position) {

       // holder.cp.setText(models.get(position).getDateUpdated());
        holder.trans.setText(models.get(position).getGtranid());
        holder.date.setText(models.get(position).getGdate());
        holder.money.setText(models.get(position).getGtotamt());
        /*holder.custname.setText(models.get(position).getGpt());*/


        dbHelper=new MyDbHelper(c.getApplicationContext());

        billModel=dbHelper.getBill(models.get(position).getKey());



        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Bill.class);
                i.putExtra("key", models.get(position).getKey());
                c.startActivity(i);

            }
        });

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String item = models.get(position).getGtranid();

                        AlertDialog.Builder alert = new AlertDialog.Builder(c);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Are you sure to delete this transaction?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                              /*  dataSnapshot.getRef().removeValue();*/


                                dbHelper.deleteBill(models.get(position).getKey());


                                dialog.dismiss();
                                models.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, models.size());

                                // log



                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
                               String log= format.format(calendar.getTime()) + "\n["
                                                    + fm.format(calendar.getTime()) + "] " + item
                                                    + " Transaction Deleted\n\n" ;

                                String DCreated=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;

                                long id2=dbHelper.insertToLog(
                                        ""+log,
                                        ""+DCreated);

                                Toast.makeText(c, item + " has been removed succesfully.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alert.show();
                    }


                });


    }

    @Override
    public Filter getFilter() {

        if (filter == null) {
            filter = new CustomFilterTrans(filterList, this);
        }
        return filter;
    }
}

