package com.example.witsmarketplace.LandingPage;

import androidx.recyclerview.widget.RecyclerView;

public class ItemBox {
    private final String name , price, image, description;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

//  constructor fetching all data required on an itemBox
    public ItemBox(String name, String price, String image, String description) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
    }

//  getters for all required data
    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String getImage(){
        return image;
    }
    public String getDescription(){
        return description;
    }
}
