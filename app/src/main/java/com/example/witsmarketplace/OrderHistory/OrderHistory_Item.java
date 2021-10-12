package com.example.witsmarketplace.OrderHistory;

public class OrderHistory_Item implements java.io.Serializable {
    private final String total, date, address, items;

    //  constructor fetching all data required on an itemBox
    public OrderHistory_Item(String total, String date, String address, String items) {
        this.total = total;
        this.date = date;
        this.address = address;
        this.items = items;

    }

    //  getters for all required data
    public String getTotal(){
        return total;
    }
    public String getDate(){
        return date;
    }
    public String getAddress(){
        return address;
    }
    public String getItems(){ return items; }
}
