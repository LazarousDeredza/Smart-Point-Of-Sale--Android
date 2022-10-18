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
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

;

public class items extends Fragment implements SearchView.OnQueryTextListener {
    View v;
    String level;

    MyAdapter adapter;
    private RecyclerView rv;
    ArrayList<StockModel> models = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    boolean menuqty = false, menuexp = false;
    MenuItem orderN, orderQ, orderE;
    ArrayList<StockModel> list;


   MyDbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.m_items, container, false);
        getActivity().setTitle("Stock List");
        MainActivity.flag = 1;
        setHasOptionsMenu(true);

        // INITIALIZE RV
        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        list=new ArrayList<>();


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbHelper=new MyDbHelper(getContext());
        level=dbHelper.getLogged("1").getLevel();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(level.equals("user")){
                    Toast.makeText(getContext(),"UnAuthorized",Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(getContext(), AddStock.class);
                    startActivity(i);
                }
            }
        });
        fab.show();

        adapter = new MyAdapter(getContext(), retrieve());


        rv.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {

        menu.findItem(R.id.action_logout).setVisible(false);
        menu.findItem(R.id.action_updateinfo).setVisible(false);
        menu.findItem(R.id.order_due).setVisible(false);
        menu.findItem(R.id.order_ename).setVisible(true);
        menu.findItem(R.id.order_exp).setVisible(true);
        menu.findItem(R.id.order_qty).setVisible(true);
        inflater.inflate(R.menu.search_bar, menu);
        final MenuItem searchitem = menu.findItem(R.id.search);
        orderQ = menu.findItem(R.id.order_qty);
        orderE = menu.findItem(R.id.order_exp);
        orderN = menu.findItem(R.id.order_ename);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchitem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                menu.findItem(R.id.order_ename).setVisible(true);
                menu.findItem(R.id.order_exp).setVisible(true);
                menu.findItem(R.id.order_qty).setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                menu.findItem(R.id.order_ename).setVisible(false);
                menu.findItem(R.id.order_exp).setVisible(false);
                menu.findItem(R.id.order_qty).setVisible(false);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.order_exp) {

            if (models.size() > 0) {
                Collections.sort(models, new Comparator<StockModel>() {
                    DateFormat f = new SimpleDateFormat("MM-yyyy");

                    @Override
                    public int compare(final StockModel object1, final StockModel object2) {
                        try {
                            return f.parse(object1.getExpiryDate()).compareTo(f.parse(object2.getExpiryDate()));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
            }
            menuqty = false;
            menuexp = true;
            orderE.setChecked(true);
            orderN.setChecked(false);
            orderQ.setChecked(false);
            adapter.notifyDataSetChanged();
            return true;
        }

        if (id == R.id.order_qty) {

            if (models.size() > 0) {
                Collections.sort(models, new Comparator<StockModel>() {
                    @Override
                    public int compare(StockModel p1, StockModel p2) {
                        return Integer.parseInt(p1.getQuantity()) - Integer.parseInt(p2.getQuantity());
                    }
                });
            }
            menuqty = true;
            menuexp = false;
            orderE.setChecked(false);
            orderN.setChecked(false);
            orderQ.setChecked(true);
            adapter.notifyDataSetChanged();
            return true;
        }

        if (id == R.id.order_ename) {

            if (models.size() > 0) {
                Collections.sort(models, new Comparator<StockModel>() {
                    @Override
                    public int compare(final StockModel object1, final StockModel object2) {
                        return object1.getProductName().compareTo(object2.getProductName());
                    }
                });
            }
            menuqty = false;
            menuexp = false;
            orderE.setChecked(false);
            orderN.setChecked(true);
            orderQ.setChecked(false);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    private void fetchData( ) {
        models.clear();

        list= dbHelper.getAllStocks();


        Log.d("list length =",String.valueOf(list.size()));
        for (int i=0 ; i < list.size();i++ ) {
            StockModel stockModel = new StockModel(list.get(i).getId(),list.get(i).getProductName(),
                    list.get(i).getBatchNumber(),list.get(i).getQuantity(),list.get(i).getExpiryDate(),
                    list.get(i).getSupplierCompany(),list.get(i).getBuyingPrice(),
                    list.get(i).getSellingPrice(),list.get(i).getBarcode(),
                    list.get(i).getUnit(),list.get(i).getDescription(),list.get(i).getDateAdded(),
                    list.get(i).getDateUpdated());

            System.out.println(list.get(i).getDateUpdated());
            models.add(stockModel);
        }

     //   String TransDate=models.get(0).getDateUpdated();
       // System.out.println(" Date added  =========" +TransDate);





        System.out.println("model length = "+models.size());
        System.out.println(models);

        if (menuqty) {
            if (models.size() > 0) {
                Collections.sort(models, new Comparator<StockModel>() {
                    @Override
                    public int compare(StockModel p1, StockModel p2) {
                        return Integer.parseInt(p1.getQuantity()) - Integer.parseInt(p2.getQuantity());
                    }
                });
            }
        } else if (menuexp) {
            if (models.size() > 0) {
                Collections.sort(models, new Comparator<StockModel>() {
                    DateFormat f = new SimpleDateFormat("MM-yyyy");

                    @Override
                    public int compare(final StockModel object1, final StockModel object2) {
                        try {
                            return f.parse(object1.getExpiryDate()).compareTo(f.parse(object2.getExpiryDate()));
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
            }
        } else {
            if (models.size() > 0) {
                Collections.sort(models, new Comparator<StockModel>() {
                    @Override
                    public int compare(final StockModel object1, final StockModel object2) {
                        return object1.getProductName().compareTo(object2.getProductName());
                    }
                });
            }
        }
       // adapter.notifyDataSetChanged();
    }

    // READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<StockModel> retrieve()  {

                fetchData();

//                if (models.size() == 1) {
//                    models.clear();
//                    adapter.notifyDataSetChanged();
//                } else {
//                    adapter.notifyDataSetChanged();
//                }



        return models;
    }
}
