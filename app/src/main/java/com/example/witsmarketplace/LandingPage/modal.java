package com.example.witsmarketplace.LandingPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.witsmarketplace.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class modal extends AppCompatActivity
{
    private static String name;
    private static String price;
    private static String description;
    private static ExtendedFloatingActionButton cartBtn;
    private static ExtendedFloatingActionButton faveBtn;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal);
        context = this;

        TextView name_text = findViewById(R.id.name);
        TextView price_text = findViewById(R.id.price);
        TextView desc_text = findViewById(R.id.desc);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        description = intent.getStringExtra("description");

        name_text.setText(name);
        price_text.setText(price);
        desc_text.setText(description);

        cartBtn = (ExtendedFloatingActionButton) findViewById(R.id.addCartBtn);
        faveBtn = (ExtendedFloatingActionButton) findViewById(R.id.addFaveBtn);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });

        faveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Added to Favorites",Toast.LENGTH_SHORT).show();
            }
        });

        ImageSlider imageSlider = findViewById(R.id.modal_image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://lamp.ms.wits.ac.za/home/s1671848/uploads/1624189162-IMG_20210620_133631202.jpg"));
        slideModels.add(new SlideModel("https://lamp.ms.wits.ac.za/home/s1671848/uploads/1624189776-IMG_20210620_134645615.jpg"));
        slideModels.add(new SlideModel("https://lamp.ms.wits.ac.za/home/s1671848/uploads/1624282297-IMG_20210621_152856992.jpg"));
        imageSlider.setImageList(slideModels, true);
    }
}