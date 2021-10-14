package com.example.witsmarketplace.OrderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witsmarketplace.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Orders extends AppCompatActivity {
    private static String date_str, street_name, surburb_name, city_name, country_name, items, order_no_str, total_str, email_str;
    private static String[] names;
    private static int[] prices;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        TextView street = findViewById(R.id.addressStreet);
        TextView surburb = findViewById(R.id.addressSurburb);
        TextView city = findViewById(R.id.addressCity);
        TextView country = findViewById(R.id.addressCountry);
        TextView date = findViewById(R.id.date_id);
        TextView order_no = findViewById(R.id.orderNo);
        TextView total = findViewById(R.id.totalPrice);
        TextView email = findViewById(R.id.email);
        LinearLayout pdf = findViewById(R.id.pdf);

        Intent intent = getIntent();
        street_name = intent.getStringExtra("street");
        surburb_name = intent.getStringExtra("surburb");
        city_name = intent.getStringExtra("city");
        country_name = intent.getStringExtra("country");
        date_str = intent.getStringExtra("date");
        order_no_str = intent.getStringExtra("order_no");
        total_str = intent.getStringExtra("total");
        email_str = intent.getStringExtra("user");
        names = intent.getStringArrayExtra("names");
        prices = intent.getIntArrayExtra("prices");

        street.setText(street_name);
        surburb.setText(surburb_name);
        city.setText(city_name);
        country.setText(country_name);
        date.setText(date_str);
        order_no.setText(order_no_str);
        total.setText(total_str);
        email.setText(email_str);

        renderer();

        ImageView downloadPDF = findViewById(R.id.downloadPDF);
        downloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Invoice Downloaded", Toast.LENGTH_SHORT).show();
                System.out.println("W: " + pdf.getWidth() + " H: " + pdf.getHeight());
                int width = pdf.getWidth(), height = pdf.getHeight();
                bitmap = loadBitmap(pdf, width, height);
                createPDF();
            }
        });
    }

    private Bitmap loadBitmap(View pdf, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        pdf.draw(canvas);

        return bitmap;
    }

    private void createPDF() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHeight = (int) height, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/page.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }////////////////////

        // close the document
        document.close();
        Toast.makeText(this, "successfully pdf created", Toast.LENGTH_SHORT).show();

        openPdf();

    }

    private void openPdf() {
        File file = new File("/sdcard/page.pdf");
        
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No Application for pdf view", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void renderer(){
        //display the data in a recyclerview which allows us to scroll through
        RecyclerView recyclerView = findViewById(R.id.rv_orderList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter adapter = new OrdersAdapter(names, prices);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}