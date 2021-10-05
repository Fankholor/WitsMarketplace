package com.example.witsmarketplace.OrderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.witsmarketplace.LandingPage.ItemBox;
import com.example.witsmarketplace.LandingPage.Itembox_Adapter;
import com.example.witsmarketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderHistory extends AppCompatActivity {

    private RequestQueue requestQueue;
    ArrayList<ItemBox> order_history;
    String order_historyURL = "https://lamp.ms.wits.ac.za/home/s2172765/app_fetch_orderHistory.php?USER_EMAIL=";
    String order_NoURL = "https://lamp.ms.wits.ac.za/home/s2172765/app_fetch_orderNo.php?USER_EMAIL=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        requestQueue = Volley.newRequestQueue(this);
        getData();
    }
    private void parseData2(JSONArray array) {
        String products="", name="", price="", image="", description="";
        for (int i = 0; i < array.length(); i++) {

            //Creating the Request object
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
                String orderNo = json.getString("ORDER_NO");
                System.out.println(orderNo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the request object to the list
//            String[] imageURLs = image.split(",");
//            ArrayList<String> images = new ArrayList<>();
//            images.addAll(Arrays.asList(imageURLs));
//            String image_url = imageURLs[0];

//            order_history.add(new ItemBox(name, "R " + price, image_url, description,images));
        }
        //Notifying the adapter that data has been added or changed
        //adapter.notifyDataSetChanged();
//        renderer(order_history);
    }

    private void parseData(JSONArray array) {
        String products="", name="", price="", image="", description="";
        for (int i = 0; i < array.length(); i++) {

            //Creating the Request object
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);

                products = json.getString("PRODUCT_NAME");
                System.out.println(products);
                JSONArray arr = new JSONArray(products);
//
//                for (int j = 0; j < arr.length();j++){
//                    JSONObject obj = arr.getJSONObject(j);
//
//                    //Adding data to the request object
//                    name = obj.getString("NAME");
//                    price = obj.getString("PRICE");
//                    image = obj.getString("PICTURE");
//                    description = obj.getString("DESCRIPTION");
//
//                    System.out.println(name + " " + price);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the request object to the list
//            String[] imageURLs = image.split(",");
//            ArrayList<String> images = new ArrayList<>();
//            images.addAll(Arrays.asList(imageURLs));
//            String image_url = imageURLs[0];
//
//            order_history.add(new ItemBox(name, "R " + price, image_url, description,images));
        }
        //Notifying the adapter that data has been added or changed
        //adapter.notifyDataSetChanged();
//        renderer(order_history);
    }

    private JsonArrayRequest getDataFromServer(String url, String email) {

        //JsonArrayRequest of volley
        return new JsonArrayRequest(url + email,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method to parse the json response
                        parseData2(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If an error occurs that means end of the list has reached
                    }
                });
    }

    private JsonArrayRequest getDataFromServer2(String url, String email, String orderNo) {

        //JsonArrayRequest of volley
        return new JsonArrayRequest(url + email + "&ORDER_NO=" + orderNo,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method to parse the json response
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If an error occurs that means end of the list has reached
                    }
                });
    }

    private void getData(){
        requestQueue.add(getDataFromServer(order_NoURL, "makgakgacharles@gmail.com"));
        requestQueue.add(getDataFromServer2(order_historyURL, "makgakgacharles@gmail.com", "1070"));
    }




    private void renderer(ArrayList<ItemBox> list){
        //display the data in a recyclerview which allows us to scroll through
        RecyclerView recyclerView = findViewById(R.id.rv_order_history);
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter adapter = new Itembox_Adapter(list, this, 5);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

//    private  void
}