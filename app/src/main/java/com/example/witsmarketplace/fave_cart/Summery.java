package com.example.witsmarketplace.fave_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.witsmarketplace.LandingPage.LandingPage;
import com.example.witsmarketplace.R;
import com.example.witsmarketplace.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Summery extends AppCompatActivity {

    ImageButton backbut;
    TextView changeMethod;
    TextView changeAdd;
    TextView itemNum;
    TextView Delivery;
    TextView price;
    TextView price2;
    String email;
    String webURL = "https://lamp.ms.wits.ac.za/home/s2172765/cart_items.php?ID=";
    ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summery);

        SharedPreference sharedPreference = new SharedPreference(this);
        email = sharedPreference.getSH("email");

        backbut = findViewById(R.id.sumback);
        changeMethod = findViewById(R.id.textView9);
        changeAdd = findViewById(R.id.textView11);
        itemNum = findViewById(R.id.textView3);
        Delivery = findViewById(R.id.textView8);
        price = findViewById(R.id.textView4);
        price2 = findViewById(R.id.textView6);
        getDataFromServer(email);

        itemNum.setText(String.valueOf(cartItems.size()));

        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summery.this, LandingPage.class);
                startActivity(intent);
            }
        });

        changeMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        changeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void parseData(JSONArray array) throws JSONException {

        Log.d("Cart Items",String.valueOf(array.getJSONObject(0)));

        String name="", price="", image="", iCount="", desc="";
        for (int i = 0; i < array.length(); i++) {

            //Creating the Request object
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);

                //Adding data to the request object
                name = json.getString("NAME");
                price = json.getString("PRICE");
                image = json.getString("PICTURE");
                desc = json.getString("DESCRIPTION");
                //iCount = json.getString("COUNT");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String[] imageURLs = image.split(",");
            String image_url = imageURLs[0];

            cartItems.add(new CartItem(name, price, image_url));
        }
        //Notifying the adapter that data has been added or changed
//        adapter.notifyDataSetChanged();

//        renderer();
//        cart_count = findViewById(R.id.cart_count);
//        cart_count.setText(cartItems.size()+" items");
    }

    private JsonArrayRequest getDataFromServer(String email) {
        //JsonArrayRequest of volley
        return new JsonArrayRequest(webURL + String.valueOf(email),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method to parse the json response
                        try {
                            parseData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If an error occurs that means end of the list has reached
                    }
                });
    }
}