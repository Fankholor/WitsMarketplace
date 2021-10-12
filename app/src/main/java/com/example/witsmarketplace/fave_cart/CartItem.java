package com.example.witsmarketplace.fave_cart;

public class CartItem {
    private final String email,name, price, image,productID;

    //  constructor fetching all data required on an itemBox
    public CartItem(String email,String name, String price, String image,String productID) {
        this.email = email;
        this.name = name;
        this.price = price;
        this.image = image;
        this.productID = productID;
//        this.count = count;
//        this.quantity = quantity;
    }

    //  getters for all required data

    public String getEmail() {return email; }
    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String getImage(){
        return image;
    }
    public String getProductID(){return productID;}
//    public String getCount(){ return count; }
//    public  String getQuantity(){return quantity;}
}
