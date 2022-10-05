package com.example.inventorymanagement;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView item, btc, qty, cprice, exp, sp, cName, code, cp, desc, unit;
    ImageView editBtn, delbtn;
    LinearLayout gone;
    CardView cv;

    public MyViewHolder(View itemView) {
        super(itemView);

        cv = (CardView) itemView.findViewById(R.id.cv);
        gone = (LinearLayout) itemView.findViewById(R.id.gone_layout);

        item = (TextView) itemView.findViewById(R.id.itemName);
        btc = (TextView) itemView.findViewById(R.id.batchText);
        qty = (TextView) itemView.findViewById(R.id.qtyText);
        cprice = (TextView) itemView.findViewById(R.id.cprice);
        exp = (TextView) itemView.findViewById(R.id.expText);
        sp = (TextView) itemView.findViewById(R.id.spText);
        cName = (TextView) itemView.findViewById(R.id.cNameText);
        cp = (TextView) itemView.findViewById(R.id.cpText);
        code = (TextView) itemView.findViewById(R.id.codeText);
        desc = (TextView) itemView.findViewById(R.id.descText);
        unit = (TextView) itemView.findViewById(R.id.unitText);

        editBtn = (ImageView) itemView.findViewById(R.id.editbtn);
        delbtn = (ImageView) itemView.findViewById(R.id.delbtn);
    }
}
