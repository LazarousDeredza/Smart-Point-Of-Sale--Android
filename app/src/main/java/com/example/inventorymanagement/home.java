package com.example.inventorymanagement;



import static com.example.inventorymanagement.MainActivity.fab;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.util.Date;

public class home extends Fragment {
    View v;
    ImageView i;
    Animation rotate;

    String s;

    String uid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home, container, false);
        getActivity().setTitle("Home");


        MainActivity.flag = 0;
        return v;





    }

    @Override
    public void onResume() {
        super.onResume();
        // SharedPreferences sharedPref =
        // getActivity().getPreferences(Context.MODE_PRIVATE);
        // String defaultValue = "Medical Store";
        // String sname = sharedPref.getString("sname", defaultValue);


                TextView tv = (TextView) v.findViewById(R.id.hometext);
                tv.setText("Store Name");



        fab.hide();
    }

}
