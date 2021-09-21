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
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.Collection;
import java.util.Locale;

public class Itembox_Adapter extends RecyclerView.Adapter<Itembox_Adapter.Itembox_ViewHolder> implements Filterable {

    private ArrayList<ItemBox> itemsList;
    private ArrayList<ItemBox> CompleteItemsList;
    public static int n;

    private Activity mContext;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ItemBox> filteredItems = new ArrayList<>();

            if (charSequence.toString().isEmpty())
                filteredItems.addAll(CompleteItemsList);
            else{
                for (int i = 0; i < CompleteItemsList.size(); i++){
                    if (CompleteItemsList.get(i).getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredItems.add(CompleteItemsList.get(i));
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredItems;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            itemsList.clear();
            itemsList.addAll((Collection<? extends ItemBox>) filterResults.values);
        }
    };

    public static class Itembox_ViewHolder extends RecyclerView.ViewHolder{

        public ImageView itemImage;
        public TextView itemName;
        public TextView itemPrice;

        //      view holder for directly setting the items' details to be displayed
        public Itembox_ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (Itembox_Adapter.n == 1){
                itemImage = itemView.findViewById(R.id.img_item);
                itemName = itemView.findViewById(R.id.txt_itemname);
                itemPrice = itemView.findViewById(R.id.txt_price);
            }
            else if (Itembox_Adapter.n == 2){
                itemImage = itemView.findViewById(R.id.vm_img_item);
                itemName = itemView.findViewById(R.id.vm_itemname);
                itemPrice = itemView.findViewById(R.id.vm_price);
            }

        }
    }

    //  list of all items
    public Itembox_Adapter(ArrayList<ItemBox> itemsList, Activity mContext, int n){
        this.mContext = mContext;
        this.itemsList = itemsList;
        this.CompleteItemsList = new ArrayList<>(itemsList);
        Itembox_Adapter.n = n;
    }

    @NonNull
    @Override
    public Itembox_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (Itembox_Adapter.n == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itembox, parent, false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_more, parent, false);
        }
        return new Itembox_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Itembox_ViewHolder holder, int position) {
//      Set the view holders with details from the items list
        ItemBox currentItem = itemsList.get(position);

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