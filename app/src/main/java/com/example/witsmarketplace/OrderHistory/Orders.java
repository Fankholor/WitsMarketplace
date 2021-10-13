package com.example.witsmarketplace.OrderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.witsmarketplace.R;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {
    private static String date_str, street_name, surburb_name, city_name, country_name, items, order_no_str, total_str, email_str;
    private static String[] names;
    private static int[] prices;
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