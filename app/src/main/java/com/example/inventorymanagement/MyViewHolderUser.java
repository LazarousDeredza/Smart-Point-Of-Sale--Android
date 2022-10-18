package com.example.inventorymanagement;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderUser extends RecyclerView.ViewHolder {

    TextView item, date, level, addr, phone;
    ImageView editBtn, delbtn;
    Button update, log;
    LinearLayout gone;
    CardView cv;

    public MyViewHolderUser(View itemView) {
        super(itemView);

        cv = (CardView) itemView.findViewById(R.id.cv);
        gone = (LinearLayout) itemView.findViewById(R.id.gone_layout);

        item = (TextView) itemView.findViewById(R.id.name);
        date = (TextView) itemView.findViewById(R.id.date);
        level = (TextView) itemView.findViewById(R.id.level);
        addr = (TextView) itemView.findViewById(R.id.addr);
        phone = (TextView) itemView.findViewById(R.id.phone);

        editBtn = (ImageView) itemView.findViewById(R.id.editbutton);
        delbtn = (ImageView) itemView.findViewById(R.id.deletebutton);
        update = (Button) itemView.findViewById(R.id.updatebtn);
        log = (Button) itemView.findViewById(R.id.logbtn);
    }
}
