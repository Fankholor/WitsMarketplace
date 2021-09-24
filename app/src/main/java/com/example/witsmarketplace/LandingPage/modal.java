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
    private static ArrayList<String> images = new ArrayList<>();
    private static ExtendedFloatingActionButton cartBtn;
    private static ExtendedFloatingActionButton faveBtn;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal);
        mContext = this;

        TextView name_text = findViewById(R.id.name);
        TextView price_text = findViewById(R.id.price);
        TextView desc_text = findViewById(R.id.desc);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        description = intent.getStringExtra("description");
        images = intent.getStringArrayListExtra("images_array");

        name_text.setText(name);
        price_text.setText(price);
        desc_text.setText(description);

        cartBtn = (ExtendedFloatingActionButton) findViewById(R.id.addCartBtn);
        faveBtn = (ExtendedFloatingActionButton) findViewById(R.id.addFaveBtn);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });

        faveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Added to Favorites",Toast.LENGTH_SHORT).show();
            }
        });

        ImageSlider imageSlider = findViewById(R.id.modal_image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        for(String image:images){
            slideModels.add(new SlideModel(image));
        }
        imageSlider.setImageList(slideModels, true);
    }
}