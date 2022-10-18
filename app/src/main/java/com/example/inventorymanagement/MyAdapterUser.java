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

public class MyAdapterUser extends RecyclerView.Adapter<MyViewHolderUser> implements Filterable {

    Context c;

    String uid;
    ArrayList<UserModel> models, filterList;

    CustomFilterUser filter;

    MyDbHelper dbHelper;

    public MyAdapterUser(Context c, ArrayList<UserModel> models) {

        this.c = c;
        this.models = models;
        this.filterList = models;
    }

    @Override
    public MyViewHolderUser onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.usercard, parent, false);
        dbHelper = new MyDbHelper(c);
        return new MyViewHolderUser(v);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolderUser holder, @SuppressLint("RecyclerView") final int position) {

        holder.item.setText(models.get(position).getFirstName()+" "+models.get(position).getLastName());
        holder.date.setText(models.get(position).getDateCreated());
        holder.level.setText(models.get(position).getUserLevel());
        holder.addr.setText(models.get(position).getAddress());
        holder.phone.setText(models.get(position).getPhone());

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

                Intent i = new Intent(c, Newuser.class);
                i.putExtra("id", models.get(position).getId());
                i.putExtra("userLevel","admin");
                i.putExtra("task","update");

                c.startActivity(i);
            }
        });
        holder.log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(c, UserLog.class);
                i.putExtra("k", models.get(position).getId());
                c.startActivity(i);

            }
        });
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String item = models.get(position).getFirstName()+" "+models.get(position).getLastName();
                String id = models.get(position).getId();


                AlertDialog.Builder alert = new AlertDialog.Builder(c);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete this customer?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dbHelper.deleteUser(id);

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
                                + " User Deleted\n";
                        long id2 = dbHelper.insertToLog(
                                "" + log,
                                "" + DateCreated
                        );


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
       /* holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String item = models.get(position).getFirstName()+" "+models.get(position).getLastName();
                String id = models.get(position).getId();

                final AlertDialog.Builder alert = new AlertDialog.Builder(c);
                final EditText edittext = new EditText(c);
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
                    @SuppressLint("NotifyDataSetChanged")
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


                            notifyDataSetChanged();
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
        });*/
    }

    @Override
    public Filter getFilter() {

        if (filter == null) {
            filter = new CustomFilterUser(filterList, this);
        }
        return filter;
    }
}
