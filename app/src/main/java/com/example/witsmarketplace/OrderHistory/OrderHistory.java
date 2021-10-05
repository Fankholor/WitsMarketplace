package com.example.witsmarketplace.OrderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.witsmarketplace.LandingPage.ItemBox;
import com.example.witsmarketplace.LandingPage.Itembox_Adapter;
import com.example.witsmarketplace.R;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
    }

    private void renderer(ArrayList<ItemBox> list){
        //display the data in a recyclerview which allows us to scroll through
        RecyclerView recyclerView = findViewById(R.id.rv_order_history);
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter adapter = new Itembox_Adapter(list, this, 5);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}