package com.example.inventorymanagement;



import static com.example.inventorymanagement.MainActivity.fab;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class home extends Fragment {
    View v;
    ImageView i;
    Animation rotate;

    String s;

    String uid;



    TextView hometext;

    public static String companyEmail, storeName= "";

    private  MyDbHelper dbHelper;
    private ArrayList<CompanyModel> list;





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
        dbHelper=new MyDbHelper(getContext());
        list=new ArrayList<>();


        list=dbHelper.getAllCOMPANY();

        storeName=list.get(0).getStoreName();
        companyEmail=list.get(0).getEmail();

        TextView tv = (TextView) v.findViewById(R.id.hometext);
        tv.setText(storeName);




        fab.hide();
    }

}
