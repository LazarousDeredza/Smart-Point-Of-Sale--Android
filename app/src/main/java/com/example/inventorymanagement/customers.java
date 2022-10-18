package com.example.inventorymanagement;

import static android.content.Intent.getIntent;
import static com.example.inventorymanagement.MainActivity.fab;

import android.content.Intent;
import android.os.Bundle;
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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class customers extends Fragment implements SearchView.OnQueryTextListener {
    View v;
    String level;

    MyAdapterCustomer adapter;
    private RecyclerView rv;
    ArrayList<Modelcustomer> models = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    MyDbHelper dbHelper;
    boolean order = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.customers, container, false);
        getActivity().setTitle("Customers");
        MainActivity.flag = 1;
        setHasOptionsMenu(true);

        // INITIALIZE RV
        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        // layoutManager.setReverseLayout(true);
        // layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);


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
                    // Snackbar.make(view, "Customer Fab", Snackbar.LENGTH_LONG)
                    // .setAction("Action", null).show();
                    if(level.equals("user")){
                        Toast.makeText(getContext(),"UnAuthorized",Toast.LENGTH_SHORT).show();
                    }else {

                        Intent i = new Intent(getContext(), Newcustomer.class);
                        startActivity(i);
                    }
                }
            });




        fab.show();
        // ADAPTER
        adapter = new MyAdapterCustomer(getContext(), retrieve());
        rv.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {

        menu.findItem(R.id.action_logout).setVisible(false);
        menu.findItem(R.id.action_updateinfo).setVisible(false);
        menu.findItem(R.id.order_ename).setVisible(false);
        menu.findItem(R.id.order_qty).setVisible(false);
        menu.findItem(R.id.order_exp).setVisible(false);
        // menu.findItem(R.id.order_transdate).setVisible(false);
        menu.findItem(R.id.order_due).setVisible(true);
        inflater.inflate(R.menu.search_bar, menu);
        final MenuItem searchitem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchitem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                menu.findItem(R.id.order_due).setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                menu.findItem(R.id.order_due).setVisible(false);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.order_due) {
            if (!item.isChecked()) {
                if (models.size() > 0) {
                    Collections.sort(models, new Comparator<Modelcustomer>() {
                        @Override
                        public int compare(Modelcustomer p1, Modelcustomer p2) {
                            return Double.compare(Double.parseDouble(p2.getEmoney()),
                                    Double.parseDouble(p1.getEmoney()));
                        }
                    });
                }
                order = true;
                item.setChecked(true);
                adapter.notifyDataSetChanged();
            } else {
                if (models.size() > 0) {
                    Collections.sort(models, new Comparator<Modelcustomer>() {
                        @Override
                        public int compare(final Modelcustomer object1, final Modelcustomer object2) {
                            return object1.getEiname().compareTo(object2.getEiname());
                        }
                    });
                }
                order = false;
                item.setChecked(false);
                adapter.notifyDataSetChanged();
            }
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
    private void fetchData() {


        models.clear();
        models=dbHelper.getAllCUSTOMERS();

        if (order) {
            if (models.size() > 0) {
                Collections.sort(models, new Comparator<Modelcustomer>() {
                    @Override
                    public int compare(Modelcustomer p1, Modelcustomer p2) {
                        return Double.compare(Double.parseDouble(p2.getEmoney()), Double.parseDouble(p1.getEmoney()));
                    }
                });
            }
        } else {
            if (models.size() > 0) {
                Collections.sort(models, new Comparator<Modelcustomer>() {
                    @Override
                    public int compare(final Modelcustomer object1, final Modelcustomer object2) {
                        return object1.getEiname().compareTo(object2.getEiname());
                    }
                });
            }
        }
       // adapter.notifyDataSetChanged();
    }

    // READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Modelcustomer> retrieve() {

                fetchData();


                /*if (models.size() == 1) {
                    models.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.notifyDataSetChanged();
                }*/

        return models;
    }

}
