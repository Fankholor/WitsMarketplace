package com.example.witsmarketplace.fave_cart;

public class CartItem {
    private final String name, price, image, count, desc,quantity;

    //  constructor fetching all data required on an itemBox
    public CartItem(String name, String price, String image, String count,String desc,String quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.count = count;
        this.desc  = desc;
        this.quantity = quantity;
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
    public String getCount(){
        return count;
    }
    public String getDesc() {return desc;}
    public  String getQuantity(){return quantity;}
}
