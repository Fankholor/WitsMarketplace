package com.example.witsmarketplace.fave_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.witsmarketplace.LandingPage.LandingPage;
import com.example.witsmarketplace.R;
import com.example.witsmarketplace.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Summery extends AppCompatActivity {

    ImageButton backbut;
    TextView changeMethod;
    TextView changeAdd;
    TextView itemNum;
    TextView Delivery;
    TextView price;
    private RequestQueue requestQueue;
    TextView price2;
    String email;
    String webURL = "https://lamp.ms.wits.ac.za/home/s2172765/cart_items.php?ID=";
    ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summery);

        requestQueue = Volley.newRequestQueue(this);
        SharedPreference sharedPreference = new SharedPreference(this);
        renderItems(sharedPreference.getSH("email"));
        email = sharedPreference.getSH("email");

        backbut = findViewById(R.id.sumback);
        changeMethod = findViewById(R.id.textView9);
        changeAdd = findViewById(R.id.textView11);
        itemNum = findViewById(R.id.textView3);
        Delivery = findViewById(R.id.textView8);
        price = findViewById(R.id.textView6);
        getDataFromServer(email);

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

    private void AsignValues(){

        itemNum.setText(String.valueOf(cartItems.size()) + "  Items");

    }

    private void parseData(JSONArray array) throws JSONException {

        Log.d("Cart Items",String.valueOf(array.getJSONObject(0)));

        String name="", price="", image="";
        Log.d("Size",String.valueOf(array.length()));
        for (int i = 0; i < array.length(); i++) {

            JSONObject json = null;

            try {
                json = array.getJSONObject(i);

                //Adding data to the request object
                name = json.getString("NAME");
                price = json.getString("PRICE");
                image = json.getString("PICTURE");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String[] imageURLs = image.split(",");
            String image_url = imageURLs[0];

            cartItems.add(new CartItem(name, price, image_url));
        }
        AsignValues();
        renderer();
    }

    private JsonArrayRequest getDataFromServer(String email) {

        return new JsonArrayRequest(webURL + String.valueOf(email),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

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

    private void getData(String email){

        requestQueue.add(getDataFromServer(email));
    }

    private void renderItems(String email){

        getData(email);
    }

    private void renderer(){
        //ListView adapter for the wishlist items
        ListView cartList  = findViewById(R.id.SumList);
        SummeryItemAdapter IT = new SummeryItemAdapter(Summery.this, R.layout.sum_item, cartItems);
        cartList.setAdapter(IT);
    }
}