package com.example.witsmarketplace.LandingPage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.witsmarketplace.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Itembox_Adapter extends RecyclerView.Adapter<Itembox_Adapter.Itembox_ViewHolder> {

    private ArrayList<ItemBox> itemsList;

    private Activity mContext;
    public static class Itembox_ViewHolder extends RecyclerView.ViewHolder{

        public ImageView itemImage;
        public TextView itemName;
        public TextView itemPrice;

//      view holder for directly setting the items' details to be displayed
        public Itembox_ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img_item);
            itemName = itemView.findViewById(R.id.txt_itemname);
            itemPrice = itemView.findViewById(R.id.txt_price);
        }
    }

//  list of all items
    public Itembox_Adapter(ArrayList<ItemBox> itemsList, Activity mContext){
        this.mContext = mContext;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public Itembox_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itembox, parent, false);
        return new Itembox_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Itembox_ViewHolder holder, int position) {
//      Set the view holders with details from the items list
        ItemBox currentItem = itemsList.get(position);

        System.out.println(currentItem.getImage());
        System.out.println("Outside");

        Glide.with(mContext).load(currentItem.getImage()).into(holder.itemImage);

//        holder.itemImage.setImageDrawable(drawable);
        holder.itemName.setText(currentItem.getName());
        holder.itemPrice.setText(currentItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

}