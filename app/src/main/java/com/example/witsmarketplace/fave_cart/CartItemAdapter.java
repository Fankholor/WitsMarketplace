package com.example.witsmarketplace.fave_cart;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.witsmarketplace.LandingPage.LandingPage;
import com.example.witsmarketplace.Login.ServerCommunicator;
import com.example.witsmarketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartItemAdapter extends ArrayAdapter<CartItem> {

    private Context mContext;
    private int resource;

    String removeUrl = "https://lamp.ms.wits.ac.za/home/s2172765/app_rm_item_cart.php"; // remove cart item


    public CartItemAdapter(Context context, int resource, ArrayList<CartItem> cartItems) {
        super(context, resource, cartItems);
        this.mContext = context;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //cart item info
        String email = getItem(position).getEmail();
        String name = getItem(position).getName();
        String price = getItem(position).getPrice();
        String image = getItem(position).getImage();
        String productID = getItem(position).getProductID();
//        String count = getItem(position).getCount();
//        String quantity = getItem(position).getQuantity();


        //create cart object with info
        CartItem cartItem = new CartItem(email, name, price, image, productID);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        ImageView itemImage = (ImageView) convertView.findViewById(R.id.cart_image);
        TextView itemName = (TextView) convertView.findViewById(R.id.cart_name);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.cart_item_price);
        TextView itemQuantity = (TextView) convertView.findViewById(R.id.incre_decr_btn);
        Button cartRemove = (Button) convertView.findViewById(R.id.btn_remove);

        itemName.setText(name);
        itemPrice.setText(price);
        itemQuantity.setText("1");
        Glide.with(mContext).load(image).into(itemImage);

        cartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tell the server to remove this cart item entry
                Log.d("Cart Item clicked", "Cart item clicked: Email =" + cartItem.getEmail() + "    ProductID = " + cartItem.getProductID());
                removeItem(cartItem.getEmail(), cartItem.getProductID());
            }
        });

        return convertView;
    }

    public void removeItem(String email, String productID) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("PRODUCT_ID", productID);
        new ServerCommunicator(removeUrl, contentValues) {
            @Override
            protected void onPreExecute() {
            }

            @Override

            protected void onPostExecute(String output) {

                if (output.equals("1")) {

                    Toast.makeText(mContext, "Item removed from cart", Toast.LENGTH_LONG).show();
                    mContext.startActivity(new Intent(mContext,cart.class));
                } else {
                    Toast.makeText(mContext, output, Toast.LENGTH_LONG).show();
                }

            }
        }.execute();

    }
}
