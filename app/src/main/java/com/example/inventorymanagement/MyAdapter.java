package com.example.inventorymanagement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

    Context c;

    String uid;
    ArrayList<StockModel> models, filterList;

    CustomFilter filter;
    private MyDbHelper dbHelper;

    public MyAdapter(Context c, ArrayList<StockModel> stockModels) {

        this.c = c;
        this.models = stockModels;
        this.filterList = stockModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.item.setText(models.get(position).getProductName());
        holder.btc.setText(models.get(position).getBatchNumber());
        holder.exp.setText(models.get(position).getExpiryDate());
        holder.cprice.setText(models.get(position).getBuyingPrice());
        holder.qty.setText(models.get(position).getQuantity());
        holder.sp.setText(models.get(position).getSellingPrice());
        holder.cName.setText(models.get(position).getSupplierCompany());
        holder.code.setText(models.get(position).getBarcode());
        holder.cp.setText(models.get(position).getDateUpdated());
        holder.desc.setText(models.get(position).getDescription());
        holder.unit.setText(models.get(position).getUnit());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.gone.getVisibility() == View.VISIBLE) {
                    holder.gone.setVisibility(View.GONE);
                } else {
                    holder.gone.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(c, UpdateItem.class);
                i.putExtra("id", models.get(position).getId());
                c.startActivity(i);
            }
        });

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String item = models.get(position).getProductName();
                String id =models.get(position).getId();
                dbHelper=new MyDbHelper(v.getContext());

                AlertDialog.Builder alert = new AlertDialog.Builder(c);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete this item?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        dbHelper.deleteFromStock(id);
                        dialog.dismiss();
                        models.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, models.size());

                        // log
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
                        String date=format.format(calendar.getTime()) + " " + fm.format(calendar.getTime()) ;

                        String log=format.format(calendar.getTime()) + "\n["
                                + fm.format(calendar.getTime()) + "] "+item +"  Deleted from stock\n\n";

                        dbHelper.insertToLog(log,date);
                        Log.d("delete" +" id= "+id,log);


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
            filter = new CustomFilter(filterList, this);
        }
        return filter;
    }
}
