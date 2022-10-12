package com.example.inventorymanagement;

import static com.example.inventorymanagement.MainActivity.fab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class transactions extends Fragment implements SearchView.OnQueryTextListener {
    View v;
    String uid;

    MyAdapterTrans adapter;
    private RecyclerView rv;
    ArrayList<Modeltrans> models = new ArrayList<>();
    ArrayList<Modeltrans> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    MyDbHelper dbHelper;

    // boolean trandate=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.transaction, container, false);
        getActivity().setTitle("Transactions");
        MainActivity.flag = 1;

        setHasOptionsMenu(true);

        dbHelper=new MyDbHelper(getContext());

        list=new ArrayList<>();

        // progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);

        // INITIALIZE RV
        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);
        rv.setVisibility(View.GONE);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Trans.class);
                startActivity(i);
            }
        });
        fab.show();
        // ADAPTER
        adapter = new MyAdapterTrans(getContext(), retrieve());
        rv.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {

        // menu.findItem(R.id.action_logout).setVisible(false);
        // menu.findItem(R.id.action_updateinfo).setVisible(false);
        menu.findItem(R.id.order_ename).setVisible(false);
        menu.findItem(R.id.order_qty).setVisible(false);
        menu.findItem(R.id.order_exp).setVisible(false);
        menu.findItem(R.id.order_due).setVisible(false);
        // menu.findItem(R.id.order_transdate).setVisible(true);
        inflater.inflate(R.menu.search_bar, menu);
        final MenuItem searchitem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchitem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // menu.findItem(R.id.order_transdate).setVisible(true);
                menu.findItem(R.id.action_logout).setVisible(true);
                menu.findItem(R.id.action_updateinfo).setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // menu.findItem(R.id.order_transdate).setVisible(false);
                menu.findItem(R.id.action_logout).setVisible(false);
                menu.findItem(R.id.action_updateinfo).setVisible(false);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.getFilter().filter(newText);
        return false;
    }

    // IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData() {
        models.clear();
     //  models=dbHelper.getBills();



        list= dbHelper.getBills();


        Log.d("list length =",String.valueOf(list.size()));
        for (int i=0 ; i < list.size();i++ ) {
            Modeltrans model = new Modeltrans(
                    list.get(i).getGdate(),
                    list.get(i).getGitems(),
                    list.get(i).getGtotamt(),
                    list.get(i).getGtranid(),
                    list.get(i).getKey());
            System.out.println(list.get(i).getGdate());
            models.add(model);
        }

        String TransDate=models.get(0).getGdate();
        System.out.println(" Date added  =========" +TransDate);


        System.out.println("model length = "+models.size());
        System.out.println(models);



        // progressBar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
       // adapter.notifyDataSetChanged();
    }

    // READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Modeltrans> retrieve() {

     fetchData();


        return models;
    }

}
