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

public class CartItemAdapter extends ArrayAdapter<CartItem> {

    private Context mContext;
    private  int resource;

    public CartItemAdapter(Context context, int resource, ArrayList<CartItem> cartItems){
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
//        String count = getItem(position).getCount();
//        String quantity = getItem(position).getQuantity();

        //create fav object with info
        CartItem cartItem = new CartItem(name,price,image);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource,parent,false);

        ImageView itemImage = (ImageView) convertView.findViewById(R.id.cart_image);
        TextView itemName =  (TextView) convertView.findViewById(R.id.cart_name);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.cart_item_price);
        TextView itemQuantity =  (TextView) convertView.findViewById(R.id.incre_decr_btn);
        TextView instock = (TextView) convertView.findViewById(R.id.cart_instock);
        Button cartRemove = (Button) convertView.findViewById(R.id.btn_remove);

        itemName.setText(name);
        itemPrice.setText(price);
        itemQuantity.setText("1");
        //instock.setText(count);
        //if(Integer.parseInt(count)>0)instock.setText("In stock: "+count);
        Glide.with(mContext).load(image).into(itemImage);

        cartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tell the server to remove this cart item entry

            }
        });

        return convertView;
    }
}
