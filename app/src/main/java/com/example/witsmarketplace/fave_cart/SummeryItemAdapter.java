package com.example.witsmarketplace.fave_cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.witsmarketplace.R;

import java.util.ArrayList;

public class SummeryItemAdapter extends ArrayAdapter<CartItem> {
    private Context mContext;
    private  int resource;

    public SummeryItemAdapter(Context context, int resource, ArrayList<CartItem> cartItems){
        super(context,resource,cartItems);
        this.mContext = context;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //fav item info
        String name =  getItem(position).getName();
        String price = getItem(position).getPrice();
        String image = getItem(position).getImage();

        CartItem cartItem = new CartItem(name,price,image);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource,parent,false);

        ImageView itemImage = (ImageView) convertView.findViewById(R.id.cart_image);
        TextView itemName =  (TextView) convertView.findViewById(R.id.cart_name);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.cart_item_price);

        itemName.setText(name);
        itemPrice.setText(price);

        Glide.with(mContext).load(image).into(itemImage);

        return convertView;
    }
}
