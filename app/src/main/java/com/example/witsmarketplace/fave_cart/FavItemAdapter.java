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

public class FavItemAdapter extends ArrayAdapter<FavItem> {

    private Context mContext;
    private  int resource;

    public FavItemAdapter(Context context, int resource, ArrayList<FavItem> favItems){
        super(context,resource,favItems);
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
        String count = getItem(position).getCount();
        String desc = getItem(position).getDesc();

        //create fav object with info
        FavItem favItem = new FavItem(name,price,image,count,desc);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource,parent,false);

        ImageView itemImage = (ImageView) convertView.findViewById(R.id.fav_itemImg);
        TextView itemName =  (TextView) convertView.findViewById(R.id.fv_itemname);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.fv_price);
        TextView itemDesc =  (TextView) convertView.findViewById(R.id.fav_itemDesc);
        TextView instock = (TextView) convertView.findViewById(R.id.instock);
        Button cart = (Button) convertView.findViewById(R.id.fv_cart);

        itemName.setText(name);
        itemPrice.setText(price);
        //if(Integer.parseInt(count)>0)instock.setText("In stock");
        Glide.with(mContext).load(image).into(itemImage);

        return convertView;
    }
}
