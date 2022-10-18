package com.example.inventorymanagement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapterCustomer extends RecyclerView.Adapter<MyViewHolderCustomer> implements Filterable {

    Context ct;

    String uid;
    ArrayList<Modelcustomer> models, filterList;
    String level="";
    CustomFilterCustomer filter;
    MyDbHelper dbHelper;




    public MyAdapterCustomer(Context ct, ArrayList<Modelcustomer> models) {

        this.ct = ct;
        this.models = models;
        this.filterList = models;
    }

    @Override
    public MyViewHolderCustomer onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ct).inflate(R.layout.customercard, parent, false);
        dbHelper = new MyDbHelper(ct);
        return new MyViewHolderCustomer(v);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolderCustomer holder, @SuppressLint("RecyclerView") final int position) {

        holder.item.setText(models.get(position).getEiname());
        holder.date.setText(models.get(position).getEdate());
        holder.money.setText(models.get(position).getEmoney());
        holder.addr.setText(models.get(position).getEaddr());
        holder.phone.setText(models.get(position).getEphoneNo());


        level=dbHelper.getLogged("1").getLevel();

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
                if(level.equals("user")){
                    Toast.makeText(ct,"UnAuthorized",Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(ct, UpdateCustomer.class);
                    i.putExtra("Keys", models.get(position).getKey());
                    ct.startActivity(i);
                }
            }
        });
        holder.log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ct, CustomerLog.class);
                i.putExtra("k", models.get(position).getKey());
                ct.startActivity(i);

            }
        });
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level.equals("user")) {
                    Toast.makeText(ct, "UnAuthorized", Toast.LENGTH_SHORT).show();
                } else {
                    final String item = models.get(position).getEiname();
                    String id = models.get(position).getKey();


                    AlertDialog.Builder alert = new AlertDialog.Builder(ct);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete this customer?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dbHelper.deleteCustomer(id);

                            // dataSnapshot.getRef().removeValue();
                            dialog.dismiss();
                            models.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, models.size());

                            // log

                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat fm = new SimpleDateFormat("HH:mm");

                            String DateCreated = format.format(calendar.getTime()) + " " + fm.format(calendar.getTime());
                            String log = format.format(calendar.getTime()) + "\n["
                                    + fm.format(calendar.getTime()) + "] " + item
                                    + " Customer Deleted\n";
                            long id2 = dbHelper.insertToLog(
                                    "" + log,
                                    "" + DateCreated
                            );


                            Toast.makeText(ct, item + " has been removed succesfully.", Toast.LENGTH_SHORT).show();
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
            }

        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String item = models.get(position).getEiname();
                String id = models.get(position).getKey();

                final AlertDialog.Builder alert = new AlertDialog.Builder(ct);
                final EditText edittext = new EditText(ct);
                edittext.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});

                Modelcustomer modelcu = dbHelper.getCustomer(id);

                edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                        | InputType.TYPE_NUMBER_FLAG_SIGNED);
                edittext.setMaxLines(1);
                edittext.setHint("Enter amount");
                // alert.setMessage("Enter amount :");
                // alert.setTitle("Enter amount :");

                alert.setView(edittext);

                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        // What ever you want to do with the value
                        final Editable value = edittext.getText();
                        if (!value.toString().trim().isEmpty()) {
                            BigDecimal a = new BigDecimal(value.toString());
                            BigDecimal due = BigDecimal.valueOf(Double.parseDouble(modelcu.getEmoney())).add(a);


                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(c.getTime());


                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");


                            dbHelper.updateCustomer(modelcu.getKey(), due.toString(), formattedDate, modelcu.getLog() + "[" + format.format(calendar.getTime())
                                    + "] (" + value.toString() + ") Balance:" + due.toString() + "\n");


                            // log


                            SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
                            String Created = format.format(calendar.getTime()) + " " + fm.format(calendar.getTime());

                            String loo = format.format(calendar.getTime()) + "\n["
                                    + fm.format(calendar.getTime()) + "] "
                                    + modelcu.getEiname()
                                    + " Customer Balance update ("
                                    + value.toString() + ")\n";
                            long id2 = dbHelper.insertToLog(
                                    "" + loo,
                                    "" + Created
                            );



                           MainActivity.reloadCustomer();

                           notifyDataSetChanged();
                            Toast.makeText(ct, " Update of $"+value+" recorded successfully.", Toast.LENGTH_SHORT).show();


                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.dismiss();
                    }
                });
                AlertDialog b = alert.create();
                b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                b.show();


            }
        });
    }

    @Override
    public Filter getFilter() {

        if (filter == null) {
            filter = new CustomFilterCustomer(filterList, this);
        }
        return filter;
    }
}
