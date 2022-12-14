package com.example.inventorymanagement;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.ss.usermodel.Font;

import org.apache.poi.ss.usermodel.IndexedColors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView navhead, navsub,txtUsername,hometext;

    public static String companyEmail, storeName,uuid, Ulevel,userID= "";

    View headerView;
    public static int flag = 0;
    SharedPreferences sharedPref;
    public static FloatingActionButton fab;

    private ActionBar actionBar;
    static FragmentManager fragmentManager;

    private  MyDbHelper dbHelper;
    private ArrayList<CompanyModel> list;


    private ArrayList<Modelcustomer> customerlist;
    private ArrayList<StockModel> stocklist;
    private ArrayList<Modeltrans>  billlist;

String username,userlevel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");




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


         fragmentManager = getSupportFragmentManager();
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
        userID=bundle.getString("id");
       // hometext =findViewById(R.id.hometext);
      //  hometext.setText(storeName);




    }

    @Override
    protected void onResume() {
        super.onResume();



                navhead = (TextView) headerView.findViewById(R.id.navhead);
                navsub = (TextView) headerView.findViewById(R.id.navsub);
                txtUsername= (TextView) headerView.findViewById(R.id.txtUsername);

           //     hometext =findViewById(R.id.hometext);
           //     hometext.setText(storeName);

                navhead.setText(storeName);
                navsub.setText(Ulevel);
                txtUsername.setText(uuid);



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
            if(Ulevel.equals("user")){
                Toast.makeText(MainActivity.this,"UnAuthorized",Toast.LENGTH_SHORT).show();
            }else{

            Intent i = new Intent(this, UpdateInfo.class);
            startActivity(i);
                }
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
                i.putExtra("userLevel",Ulevel);
                finish();
                startActivity(i);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inventory) {
          //  Toast.makeText(MainActivity.this,"items",Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new items()).commit();


        } else if (id == R.id.nav_home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new home()).commit();
        } else if (id == R.id.nav_transaction) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new transactions()).commit();
        } else if (id == R.id.nav_customers) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new customers()).commit();
        } else if (id == R.id.nav_summary) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new summary()).commit();
        }
        else if (id == R.id.nav_users) {
            if(Ulevel.equals("user")){
                Toast.makeText(MainActivity.this,"UnAuthorized",Toast.LENGTH_SHORT).show();
            }else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new users()).commit();
            }
        }
        else if (id == R.id.nav_expo) {
           /* FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new summary()).commit();*/


           /* if(Ulevel.equals("user")){
                Toast.makeText(MainActivity.this,"UnAuthorized",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Expo Clicked", Toast.LENGTH_SHORT).show();
            }*/

            Export();

        }
        else if (id == R.id.nav_user_update) {
            Intent i = new Intent(this, Newuser.class);
            i.putExtra("id", userID);
            i.putExtra("userLevel",Ulevel);
            i.putExtra("task","update");

            startActivity(i);

        }

        else if (id == R.id.nav_dev) {
            Intent i = new Intent(this, Programmer.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {
          Intent i = new Intent(this, About.class);
            startActivity(i);
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static void reloadCustomer() {
        FragmentManager fragmentManager1 = fragmentManager;
        fragmentManager1.beginTransaction().replace(R.id.content_frame, new customers()).commit();
    }

    public void homebtn(View view) {
        int id = view.getId();
        if (R.id.btnnewt == id) {
            Intent i = new Intent(this, Trans.class);
            startActivity(i);

        } else if (R.id.btncust == id) {
           FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new customers()).commit();

        } else if (R.id.btntrans == id) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new transactions()).commit();

        } else if (R.id.btninventory == id) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new items()).commit();

        }
    }

    public void logout(View view) {
        signOut();
    }

    public void updatei(View view) {
        if(Ulevel.equals("user")){
            Toast.makeText(MainActivity.this,"UnAuthorized",Toast.LENGTH_SHORT).show();
        }else{
         Intent i = new Intent(this, UpdateInfo.class);
        startActivity(i);
        }


    }




    private void Export() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            } else {
                importData();
            }
        } else {
            importData();
        }
    }

    private void importData() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date=format.format(calendar.getTime());







        billlist=dbHelper.getBills();
        ArrayList<StockModel> stockList=dbHelper.getAllStocks();
        ArrayList<Modelcustomer> customers=dbHelper.getAllCUSTOMERS();









        // File filePath = new File(Environment.getExternalStorageDirectory() + "/Demo.xls");
        Workbook wb = new HSSFWorkbook();


        Cell cell = null;


        // WorksheetS

        Sheet salesSheet,stockSheet ,customerSheet= null;


        if(billlist.size()>0){
            //Sales worksheet
            salesSheet = wb.createSheet("Sales");


            //Now column and row
            Row salesHeaderRow = salesSheet.createRow(0);
            // Row row = salesSheet.createRow(0);


            cell = salesHeaderRow.createCell(0);
            cell.setCellValue("Invoice ID");


            cell = salesHeaderRow.createCell(1);
            cell.setCellValue("Product");


            cell = salesHeaderRow.createCell(2);
            cell.setCellValue("Quantity");

            cell = salesHeaderRow.createCell(3);
            cell.setCellValue("Unit Price");

            cell = salesHeaderRow.createCell(4);
            cell.setCellValue("Total Amount");

            cell = salesHeaderRow.createCell(5);
            cell.setCellValue("Date");

            cell = salesHeaderRow.createCell(6);
            cell.setCellValue("Cashier");

            //column width
            salesSheet.setColumnWidth(0, (15 * 200));
            salesSheet.setColumnWidth(1, (30 * 200));
            salesSheet.setColumnWidth(2, (15 * 200));
            salesSheet.setColumnWidth(3, (15 * 200));
            salesSheet.setColumnWidth(4, (15 * 200));
            salesSheet.setColumnWidth(5, (20 * 200));
            salesSheet.setColumnWidth(6, (25 * 200));

            int y=0;

            for (int i = 0; i < billlist.size(); i++) {
                Modeltrans bill=billlist.get(i);


                String items =bill.getGitems();
                StringTokenizer st = new StringTokenizer(items, "??");
                int k = -1;
                while (st.hasMoreTokens()) {
                    k++;
                    y++;
                    // Toast.makeText(Bill.this,""+k , Toast.LENGTH_SHORT).show();
                    StringTokenizer sti = new StringTokenizer(st.nextToken(), "??");
                    while (sti.hasMoreTokens()) {
                        String billdate=bill.getGdate().substring(0,2)+"/"+bill.getGdate().substring(2,4)+"/"+bill.getGdate().substring(4,8);
                        int id = Integer.parseInt(bill.getGtranid());
                        String cashier=bill.getBiller();

                        String product=sti.nextToken();

                        String unitSize=sti.nextToken();


                        String productId=sti.nextToken();
                        int quantity= Integer.parseInt(sti.nextToken());

                        double price= Double.parseDouble(sti.nextToken());


                        double totAmount= Double.parseDouble(sti.nextToken());




                        Row row1 = salesSheet.createRow(y );

                        cell = row1.createCell(0);
                        cell.setCellValue(id);

                        cell = row1.createCell(1);
                        cell.setCellValue(product +" "+unitSize);
                        //  cell.setCellStyle(cellStyle);

                        cell = row1.createCell(2);
                        cell.setCellValue(quantity);

                        cell = row1.createCell(3);
                        cell.setCellValue(price);
                        //  cell.setCellStyle(cellStyle);

                        cell = row1.createCell(4);
                        cell.setCellValue(totAmount);


                        cell = row1.createCell(5);
                        cell.setCellValue(billdate);
                        //  cell.setCellStyle(cellStyle);

                        cell = row1.createCell(6);
                        cell.setCellValue(cashier);



                        salesSheet.setColumnWidth(0, (15 * 200));
                        salesSheet.setColumnWidth(1, (30 * 200));
                        salesSheet.setColumnWidth(2, (15 * 200));
                        salesSheet.setColumnWidth(3, (15 * 200));
                        salesSheet.setColumnWidth(4, (15 * 200));
                        salesSheet.setColumnWidth(5, (20 * 200));
                        salesSheet.setColumnWidth(6, (25 * 200));


                    }
                }



          /*  Row row1 = salesSheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(billlist.get(i).getKey());

            cell = row1.createCell(1);
            cell.setCellValue((billlist.get(i).getGitems()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(billlist.get(i).getGtotamt());

            cell = row1.createCell(3);
            cell.setCellValue((billlist.get(i).getGdate()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(4);
            cell.setCellValue(billlist.get(i).getBiller());


            salesSheet.setColumnWidth(0, (20 * 200));
            salesSheet.setColumnWidth(1, (50 * 200));
            salesSheet.setColumnWidth(2, (20 * 200));
            salesSheet.setColumnWidth(3, (30 * 200));
            salesSheet.setColumnWidth(4, (30 * 200));*/

            }
        }





       if(stockList.size()>0){
           //Inventory Sheet


           stockSheet = wb.createSheet("Inventory");

           //Now column and row
           Row stockHeaderRow = stockSheet.createRow(0);



           cell = stockHeaderRow.createCell(0);
           cell.setCellValue("ID");


           cell = stockHeaderRow.createCell(1);
           cell.setCellValue("Product");


           cell = stockHeaderRow.createCell(2);
           cell.setCellValue("Batch Number");

           cell = stockHeaderRow.createCell(3);
           cell.setCellValue("Quantity");

           cell = stockHeaderRow.createCell(4);
           cell.setCellValue("Expiry Date");

           cell = stockHeaderRow.createCell(5);
           cell.setCellValue("Supplier");

           cell = stockHeaderRow.createCell(6);
           cell.setCellValue("Cost Price");

           cell = stockHeaderRow.createCell(7);
           cell.setCellValue("Selling Price");

           cell = stockHeaderRow.createCell(8);
           cell.setCellValue("Barcode");

           cell = stockHeaderRow.createCell(9);
           cell.setCellValue("Unit Size");

           cell = stockHeaderRow.createCell(10);
           cell.setCellValue("Description");

           cell = stockHeaderRow.createCell(11);
           cell.setCellValue("Date Added");

           cell = stockHeaderRow.createCell(12);
           cell.setCellValue("Date Updated");



           //column width
           stockSheet.setColumnWidth(0, (10 * 200));
           stockSheet.setColumnWidth(1, (30 * 200));
           stockSheet.setColumnWidth(2, (15 * 200));
           stockSheet.setColumnWidth(3, (10 * 200));
           stockSheet.setColumnWidth(4, (15 * 200));
           stockSheet.setColumnWidth(5, (20 * 200));
           stockSheet.setColumnWidth(6, (20 * 200));
           stockSheet.setColumnWidth(7, (20 * 200));
           stockSheet.setColumnWidth(8, (20 * 200));
           stockSheet.setColumnWidth(9, (10 * 200));
           stockSheet.setColumnWidth(10, (25 * 200));
           stockSheet.setColumnWidth(11, (20 * 200));
           stockSheet.setColumnWidth(12, (20 * 200));




           for(int i=0; i<stockList.size();i++) {


               Row row1 = stockSheet.createRow(i + 1);

               cell = row1.createCell(0);
               cell.setCellValue(Integer.parseInt(stockList.get(i).getId()));

               cell = row1.createCell(1);
               cell.setCellValue((stockList.get(i).getProductName()));
               //  cell.setCellStyle(cellStyle);

               cell = row1.createCell(2);
               cell.setCellValue(Integer.parseInt(stockList.get(i).getBatchNumber()));

               cell = row1.createCell(3);
               cell.setCellValue(Integer.parseInt(stockList.get(i).getQuantity()));
               //  cell.setCellStyle(cellStyle);

               cell = row1.createCell(4);
               cell.setCellValue(stockList.get(i).getExpiryDate());

               cell = row1.createCell(5);
               cell.setCellValue(stockList.get(i).getSupplierCompany());

               cell = row1.createCell(6);
               cell.setCellValue(Double.parseDouble(stockList.get(i).getBuyingPrice()));

               cell = row1.createCell(7);
               cell.setCellValue(Double.parseDouble(stockList.get(i).getSellingPrice()));
               //  cell.setCellStyle(cellStyle);

               cell = row1.createCell(8);
               cell.setCellValue(stockList.get(i).getBarcode());

               cell = row1.createCell(9);
               cell.setCellValue((stockList.get(i).getUnit()));
               //  cell.setCellStyle(cellStyle);

               cell = row1.createCell(10);
               cell.setCellValue(stockList.get(i).getDescription());

               cell = row1.createCell(11);
               cell.setCellValue(stockList.get(i).getDateAdded());

               cell = row1.createCell(12);
               cell.setCellValue((stockList.get(i).getDateUpdated()));
               //  cell.setCellStyle(cellStyle);




               //column width
               stockSheet.setColumnWidth(0, (10 * 200));
               stockSheet.setColumnWidth(1, (30 * 200));
               stockSheet.setColumnWidth(2, (15 * 200));
               stockSheet.setColumnWidth(3, (10 * 200));
               stockSheet.setColumnWidth(4, (15 * 200));
               stockSheet.setColumnWidth(5, (20 * 200));
               stockSheet.setColumnWidth(6, (20 * 200));
               stockSheet.setColumnWidth(7, (20 * 200));
               stockSheet.setColumnWidth(8, (20 * 200));
               stockSheet.setColumnWidth(9, (10 * 200));
               stockSheet.setColumnWidth(10, (25 * 200));
               stockSheet.setColumnWidth(11, (15 * 200));
               stockSheet.setColumnWidth(12, (15 * 200));

           }
       }






       if(customers.size()>0)
       {
           //Regular Customers Sheet

           customerSheet = wb.createSheet("Regular Customers");

           //Now column and row
           Row cutomerHeaderRow = customerSheet.createRow(0);



           cell = cutomerHeaderRow.createCell(0);
           cell.setCellValue("ID");


           cell = cutomerHeaderRow.createCell(1);
           cell.setCellValue("Name");


           cell = cutomerHeaderRow.createCell(2);
           cell.setCellValue("Phone Number");

           cell = cutomerHeaderRow.createCell(3);
           cell.setCellValue("Balance ");

           cell = cutomerHeaderRow.createCell(4);
           cell.setCellValue("Last Balance Update");

           cell = cutomerHeaderRow.createCell(5);
           cell.setCellValue("Address");

           //column width
           customerSheet.setColumnWidth(0, (10 * 200));
           customerSheet.setColumnWidth(1, (20 * 200));
           customerSheet.setColumnWidth(2, (20 * 200));
           customerSheet.setColumnWidth(3, (10 * 200));
           customerSheet.setColumnWidth(4, (15 * 200));
           customerSheet.setColumnWidth(5, (25 * 200));



           for(int i=0; i<customers.size();i++) {


               Row row1 = customerSheet.createRow(i + 1);

               cell = row1.createCell(0);
               cell.setCellValue(Integer.parseInt(customers.get(i).getKey()));

               cell = row1.createCell(1);
               cell.setCellValue((customers.get(i).getEiname()));
               //  cell.setCellStyle(cellStyle);

               cell = row1.createCell(2);
               cell.setCellValue(customers.get(i).getEphoneNo());

               cell = row1.createCell(3);
               cell.setCellValue(Double.parseDouble(customers.get(i).getEmoney()));
               //  cell.setCellStyle(cellStyle);

               cell = row1.createCell(4);
               cell.setCellValue(customers.get(i).getEdate());

               cell = row1.createCell(5);
               cell.setCellValue(customers.get(i).getEaddr());





               //column width
               customerSheet.setColumnWidth(0, (10 * 200));
               customerSheet.setColumnWidth(1, (20 * 200));
               customerSheet.setColumnWidth(2, (20 * 200));
               customerSheet.setColumnWidth(3, (10 * 200));
               customerSheet.setColumnWidth(4, (15 * 200));
               customerSheet.setColumnWidth(5, (25 * 200));


           }

       }




        String folderName = storeName+" Database Backup Excel";
        String fileName = "Backup " + date+ ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(getApplicationContext(), "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

}
