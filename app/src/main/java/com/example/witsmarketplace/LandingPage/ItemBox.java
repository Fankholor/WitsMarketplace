package com.example.witsmarketplace.LandingPage;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ItemBox implements java.io.Serializable{
    private final String productID,name , price, image, description;
    private final ArrayList<String> imageUrls;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

//  constructor fetching all data required on an itemBox
    public ItemBox(String productID,String name, String price, String image, String description,ArrayList<String> imageUrls) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.imageUrls = imageUrls;
    }

//  getters for all required data
    public String getProductID(){return productID;}
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
    public ArrayList<String> getImageUrls(){
        return imageUrls;
    }
}
