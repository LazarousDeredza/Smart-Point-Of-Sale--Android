package com.example.inventorymanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView navhead, navsub,txtUsername;

    public static String companyEmail, storeName,uuid, Ulevel= "";

    View headerView;
    public static int flag = 0;
    SharedPreferences sharedPref;
    public static FloatingActionButton fab;

    private ActionBar actionBar;


    private  MyDbHelper dbHelper;
    private ArrayList<CompanyModel> list;


String username,userlevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new home()).commit();

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        username=null;
        userlevel=null;

        dbHelper=new MyDbHelper(this);
        list=new ArrayList<>();


        list=dbHelper.getAllCOMPANY();

        storeName=list.get(0).getStoreName();
        companyEmail=list.get(0).getEmail();


        Bundle bundle =getIntent().getExtras();
        assert bundle != null;
        uuid=bundle.getString("userName");
        Ulevel=bundle.getString("userLevel");

    }

    @Override
    protected void onResume() {
        super.onResume();



                navhead = (TextView) headerView.findViewById(R.id.navhead);
                navsub = (TextView) headerView.findViewById(R.id.navsub);
                txtUsername= (TextView) headerView.findViewById(R.id.txtUsername);


                navhead.setText(storeName);
                navsub.setText(companyEmail);
                txtUsername.setText(uuid);


                if(Ulevel.equals("admin")){
                    Toast.makeText(this, "Logged is as "+Ulevel, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Logged is as "+Ulevel, Toast.LENGTH_SHORT).show();
                }




    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (flag == 0) {
            // super.onBackPressed();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new home()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.order_ename).setVisible(false);
        menu.findItem(R.id.order_qty).setVisible(false);
        menu.findItem(R.id.order_exp).setVisible(false);
        menu.findItem(R.id.order_due).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_updateinfo) {
           // Intent i = new Intent(this, UpdateInfo.class);
           // startActivity(i);
            return true;
        }
        if (id == R.id.action_logout) {
            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {

                Intent i = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(i);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inventory) {
            Toast.makeText(MainActivity.this,"items",Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new items()).commit();
        } else if (id == R.id.nav_home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new home()).commit();
        } else if (id == R.id.nav_transaction) {
           /* FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new transactions()).commit();*/
        } else if (id == R.id.nav_customers) {
          /*  FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new customers()).commit();*/
        } else if (id == R.id.nav_summary) {
           /* FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new summary()).commit();*/
        } else if (id == R.id.nav_dev) {
           /* Intent i = new Intent(this, Developer.class);
            startActivity(i);*/
        } else if (id == R.id.nav_about) {
         /*   Intent i = new Intent(this, About.class);
            startActivity(i);*/
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void homebtn(View view) {
        int id = view.getId();
        if (R.id.btnnewt == id) {
            Intent i = new Intent(this, Trans.class);
            startActivity(i);

        } else if (R.id.btncust == id) {
         /*   FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new customers()).commit();*/

        } else if (R.id.btntrans == id) {
         /*   FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new transactions()).commit();*/

        } else if (R.id.btninventory == id) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new items()).commit();

        }
    }

    public void logout(View view) {
        signOut();
    }

    public void updatei(View view) {
       /* Intent i = new Intent(this, UpdateInfo.class);
        startActivity(i);*/
    }
}
