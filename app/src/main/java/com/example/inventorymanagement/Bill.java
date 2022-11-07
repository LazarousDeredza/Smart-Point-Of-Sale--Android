package com.example.inventorymanagement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;


import org.apache.poi.ss.usermodel.Table;
import org.apache.poi.ss.usermodel.TableStyleInfo;
import org.apache.poi.ss.util.CellReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class Bill extends AppCompatActivity {

    TextView ph1, ph2, storename, storeaddr, transid, date, totamt, name, biller;
    TextView medname[], unit[], batch[], qty[], exp[], amt[];
    String ids[];
    String id = "";

    Button btnPrint;
    MyDbHelper dbHelper;


    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.invoice);
        getSupportActionBar().hide();

        ph1 = (TextView) findViewById(R.id.ph1);
        ph2 = (TextView) findViewById(R.id.ph2);
        storename = (TextView) findViewById(R.id.storename);
        storeaddr = (TextView) findViewById(R.id.storeaddr);
        transid = (TextView) findViewById(R.id.transid);
        date = (TextView) findViewById(R.id.date);

        totamt = (TextView) findViewById(R.id.totamt);
        name = (TextView) findViewById(R.id.name);

        biller = findViewById(R.id.ptname);
        btnPrint = findViewById(R.id.btnPrint);

        dbHelper = new MyDbHelper(this);

        medname = new TextView[8];
        unit = new TextView[8];
        batch = new TextView[8];
        qty = new TextView[8];
        // rate = new TextView[8];
        exp = new TextView[8];
        amt = new TextView[8];

        ids = new String[]{"medname", "unit", "batch", "qty", "exp", "amt"};
        for (int j = 1; j <= 8; j++) {
            medname[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[0] + j, "id", getPackageName()));
            unit[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[1] + j, "id", getPackageName()));
            batch[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[2] + j, "id", getPackageName()));
            qty[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[3] + j, "id", getPackageName()));
            // rate[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[4] + j, "id", getPackageName()));
            exp[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[4] + j, "id", getPackageName()));
            amt[j - 1] = (TextView) findViewById(getResources().getIdentifier(ids[5] + j, "id", getPackageName()));
        }


        CompanyModel companyModel = dbHelper.getCompany("1");

        name.setText(companyModel.getOwnerName());
        storename.setText(companyModel.getStoreName());

        storeaddr.setText(companyModel.getAddress());
        ph1.setText("Ph: " + companyModel.getPhone());
        ph2.setText("Ph: " + companyModel.getWhatsapp());


        Intent intent = getIntent();
        id = intent.getStringExtra("key");
        BillModel bill = dbHelper.getBill(id);


        //Log.e("billDate","Date = "+bill.getDateBilled());

        String DateCreated = "";

        Calendar r = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat m = new SimpleDateFormat("HH:mm");
        DateCreated = f.format(r.getTime()) + " " + m.format(r.getTime());


        totamt.setText(bill.getTotalAmount());
        date.setText(bill.getDateBilled().substring(0, 2) + "/" + bill.getDateBilled().substring(2, 4) + "/" + bill.getDateBilled().substring(4, 8));
        transid.setText(bill.getId());
        biller.setText(bill.getBiller());

        String items = bill.getItems();
        StringTokenizer st = new StringTokenizer(items, "®");
        int k = -1;
        while (st.hasMoreTokens()) {
            k++;
            // Toast.makeText(Bill.this,""+k , Toast.LENGTH_SHORT).show();
            StringTokenizer sti = new StringTokenizer(st.nextToken(), "©");
            while (sti.hasMoreTokens()) {
                medname[k].setText(sti.nextToken());
                unit[k].setText(sti.nextToken());
                batch[k].setText(sti.nextToken());
                qty[k].setText(sti.nextToken());
                //rate[k].setText(sti.nextToken());
                exp[k].setText(sti.nextToken());
                amt[k].setText(sti.nextToken());
            }
        }


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        btnPrint.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Toast.makeText(Bill.this, "Error: Printer not Configured", Toast.LENGTH_SHORT).show();
                                createPDF(Common.getAppPath(Bill.this) + "Invoice " + id + " .pdf");
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();


    }

    private void createPDF(String path) {

        if (new File(path).exists())
            new File(path).delete();
        try {


            Document document = new Document();

            //Save
            PdfWriter.getInstance(document, new FileOutputStream(path));

            //Open document to write

            document.open();


            BillModel bill = dbHelper.getBill(id);


            //Log.e("billDate","Date = "+bill.getDateBilled());

            String DateCreated = "";

            Calendar r = Calendar.getInstance();
            SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat m = new SimpleDateFormat("HH:mm");
            DateCreated = f.format(r.getTime()) + " " + m.format(r.getTime());


            String totAmount = bill.getTotalAmount();
            String date = bill.getDateBilled().substring(0, 2) + "/" + bill.getDateBilled().substring(2, 4) + "/" + bill.getDateBilled().substring(4, 8);
            String transID = bill.getId();
            String biller = bill.getBiller();


            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Aronate Book keepers");
            document.addLanguage("English");
            document.addCreator("Lazarous Deredza ");

            //Font Setting

            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;


            //Custom Font

            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            Font titleFont2 = new Font(fontName, 30.0f, Font.NORMAL, BaseColor.BLACK);

            Font titleFont = new Font(fontName, 36.0f, Font.NORMAL, BaseColor.BLACK);

            addNewItem(document, "Invoice Details", Element.ALIGN_CENTER, titleFont);

            Font invoiceNumber = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            addNewItem(document, "Invoice Number :", Element.ALIGN_LEFT, invoiceNumber);


            Font invoiceNumberValue = new Font(fontName, valueFontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "#" + transID, Element.ALIGN_LEFT, invoiceNumberValue);


            addLineSeperator(document);


            addNewItem(document, "Date", Element.ALIGN_LEFT, invoiceNumber);
            addNewItem(document, date, Element.ALIGN_LEFT, invoiceNumberValue);


            addLineSeperator(document);


            addNewItem(document, "Teller", Element.ALIGN_LEFT, invoiceNumber);
            addNewItem(document, biller, Element.ALIGN_LEFT, invoiceNumberValue);

            addLineSeperator(document);


            addLinespace(document);

            addNewItem(document, "Product Details", Element.ALIGN_CENTER, titleFont);

            addLineSeperator(document);


            //Product Items


            //Headers

            addTittles(document, "Product", "Quantity", "Price", titleFont);
            addLineSeperator(document);
            addLineSeperator(document);
            addLinespace(document);


            String items = bill.getItems();
            StringTokenizer st = new StringTokenizer(items, "®");
            int k = -1;
            while (st.hasMoreTokens()) {
                k++;
                // Toast.makeText(Bill.this,""+k , Toast.LENGTH_SHORT).show();
                StringTokenizer sti = new StringTokenizer(st.nextToken(), "©");
                while (sti.hasMoreTokens()) {
                    String product = sti.nextToken();
                    String unit = sti.nextToken();
                    String batch = sti.nextToken();
                    String quantity = sti.nextToken();
                    String expirey = sti.nextToken();
                    String amount = sti.nextToken();


                    addProduct(document, product + " " + unit, "x" + quantity, amount, titleFont2, invoiceNumberValue);


                    addLinespace(document);
                    addLinespace(document);


                }
            }


            addLineSeperator(document);
            addLinespace(document);


            addTotal(document, "Total", "$ " + totAmount, titleFont, invoiceNumberValue);


            document.close();
            System.out.println("Dire.....>> = " + path);


            printReceipt();


            //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

    }

    private void printReceipt() {

        PrintManager printManager=(PrintManager) getSystemService(Context.PRINT_SERVICE);
       try {


           PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(Bill.this, Common.getAppPath(Bill.this) + "Invoice " + id + " .pdf");
           printManager.print("Invoice", printDocumentAdapter, new PrintAttributes.Builder().build());
       }catch (Exception e){
          Log.e("Error printing: ",e.getMessage());
           Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
       }

    }

    private void addTotal(Document document, String total, String totalPrice, Font titleFont, Font valueFont) throws DocumentException {
        Chunk textLeft = new Chunk(total, titleFont);

        Chunk textRight = new Chunk(totalPrice, valueFont);

        Paragraph paragraph = new Paragraph(textLeft);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(textRight);
        document.add(paragraph);

    }

    private void addProduct(Document document, String product, String quantity, String price, Font titleFont, Font valueFont) throws DocumentException {

        Chunk textLeft = new Chunk(product, titleFont);
        Chunk textCenter = new Chunk(quantity, valueFont);
        Chunk textRight = new Chunk(price, valueFont);

        Paragraph paragraph = new Paragraph(textLeft);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(textCenter);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(textRight);

        document.add(paragraph);


    }

    private void addTittles(Document document, String product_name, String quantity, String price, Font titleFont) throws DocumentException {
        Chunk textLeft = new Chunk(product_name, titleFont);
        Chunk textCenter = new Chunk(quantity, titleFont);
        Chunk textRight = new Chunk(price, titleFont);

        Paragraph paragraph = new Paragraph(textLeft);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(textCenter);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(textRight);

        document.add(paragraph);

    }

    private void addLineSeperator(Document document) throws DocumentException {

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
        addLinespace(document);
        document.add(new Chunk(lineSeparator));
        addLinespace(document);


    }

    private void addLinespace(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {

        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);


    }


}
