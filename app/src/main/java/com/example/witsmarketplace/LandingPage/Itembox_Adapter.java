package com.example.witsmarketplace.LandingPage;

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
    public Itembox_Adapter(ArrayList<ItemBox> itemsList){
        this.itemsList = itemsList;
    }

//    public static Drawable loadImage(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            System.out.println("success");
//            return Drawable.createFromStream(is, "src name");
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//    }

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
//        String[] imageURLs = currentItem.getImage().split(",");
//
//        String url = imageURLs[0];
        System.out.println(currentItem.getImage());
        System.out.println("Outside");
//        MyRunnable r = new MyRunnable("https://lamp.ms.wits.ac.za/home/s2172765/electronics/phone1.jpeg");
        MyRunnable r = new MyRunnable(currentItem.getImage());
        Thread thread = new Thread(r);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Drawable drawable = r.getDrawable();
//        holder.itemImage.setImageDrawable(drawable);
        holder.itemName.setText(currentItem.getName());
        holder.itemPrice.setText(currentItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

}
//New Thread to run image network operations
class MyRunnable implements Runnable {
    public String url;
    public Drawable drawable;
    public MyRunnable(String url) {
        this.url = url;
        System.out.println("Inside");
    }

    public void loadUrl(Drawable drawable){
        this.drawable = drawable;
    }
    public Drawable getDrawable(){
        return this.drawable;
    }

    public void run() {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            loadUrl(Drawable.createFromStream(is, "src name"));
            System.out.println("success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}