package com.example.witsmarketplace.LandingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.witsmarketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LandingPage extends AppCompatActivity implements RecyclerView.OnScrollChangeListener{

    String webURL = "https://lamp.ms.wits.ac.za/home/s2172765/product.php?ID="; // 1 = computer/electronics >>> 3 = books >>> 6 = clothing >>> 8 = health/hygiene >>> 10 = sports
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private BottomSheetDialog bottomSheetDialog;
    private static Context mContext;

    ArrayList<ItemBox> books_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> computers_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> clothes_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> sports_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> health_list = new ArrayList<ItemBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        mContext = this;

        requestQueue = Volley.newRequestQueue(this);
        renderCategories();    //render all categories with their items

        EditText searchTxt = (EditText) findViewById(R.id.txt_search);
        ImageButton searchBtn = (ImageButton) findViewById(R.id.btn_search);
        ImageButton cat =(ImageButton)findViewById(R.id.btn_categories);//Categories draw-bar button

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                searchTxt.clearFocus();
                hideKeyboard(view);
            }
        });
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

//  Pop up menu for the categories draw-bar
    public void showPopup(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.categories_menu, popupMenu.getMenu());
        popupMenu.show();
    }

//  Parsing data from database and adding it to an arraylist (for easy access)
    private void parseData(JSONArray array, int count) {
        String name="", price="", image="", description="";
        for (int i = 0; i < array.length(); i++) {

            //Creating the Request object
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);

                //Adding data to the request object
                name = json.getString("NAME");
                price = json.getString("PRICE");
                image = json.getString("PICTURE");
                description = json.getString("DESCRIPTION");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the request object to the list
            String[] imageURLs = image.split(",");
            ArrayList<String> images = new ArrayList<>();
            images.addAll(Arrays.asList(imageURLs));

            String image_url = imageURLs[0];

            if (count == 1) computers_list.add(new ItemBox(name, "R " + price, image_url, description, images));
            else if (count == 3) books_list.add(new ItemBox(name, "R " + price, image_url, description, images));
            else if (count == 6) clothes_list.add(new ItemBox(name, "R " + price, image_url, description, images));
            else if (count == 8) health_list.add(new ItemBox(name, "R " + price, image_url, description, images));
            else if (count == 10) sports_list.add(new ItemBox(name, "R " + price, image_url, description, images));
        }
        //Notifying the adapter that data has been added or changed
//        adapter.notifyDataSetChanged();
    }

//  Fetching the data from the database as a JSON array
    private JsonArrayRequest getDataFromServer(int requestCount) {

        //JsonArrayRequest of volley
        return new JsonArrayRequest(webURL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method to parse the json response
                        parseData(response, requestCount);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If an error occurs that means end of the list has reached
                    }
                });
    }

    private void getData(int count){

        requestQueue.add(getDataFromServer(count));
    }

    private void renderCategories(){
        // 1 = computer/electronics >>> 3 = books >>> 6 = clothing >>> 8 = health/hygiene >>> 10 = sports
        int[] arr = {1, 3, 6, 8, 10};

        for (int i : arr){
            //get data from database
            getData(i);

            //display the data in a recyclerview according to each category
            if (i == 1) renderer(R.id.rv_computers, computers_list);
            else if (i == 3) renderer(R.id.rv_books, books_list);
            else if (i == 6) renderer(R.id.rv_clothes, clothes_list);
            else if (i == 8) renderer(R.id.rv_health, health_list);
            else if (i == 10) renderer(R.id.rv_sports, sports_list);
        }
    }

    private void renderer(int rv, ArrayList<ItemBox> list){
        //display the data in a recyclerview which allows us to scroll through
        recyclerView = findViewById(rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapter = new Itembox_Adapter(list, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (Objects.requireNonNull(recyclerView.getAdapter()).getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();

            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //if Scrolled to the end then fetch more data
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getData again
            renderCategories();
        }
    }

}