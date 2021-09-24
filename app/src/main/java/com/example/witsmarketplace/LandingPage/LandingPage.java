package com.example.witsmarketplace.LandingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.witsmarketplace.MainActivity;
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
import com.example.witsmarketplace.ViewMore.ViewMore;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("ALL")
public class LandingPage extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {

    String webURL = "https://lamp.ms.wits.ac.za/home/s2172765/product.php?ID="; // 1 = computer/electronics >>> 3 = books >>> 6 = clothing >>> 8 = health/hygiene >>> 10 = sports
    String searchURL = "https://lamp.ms.wits.ac.za/home/s2172765/Search.php?ID=";
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    ArrayList<ItemBox> books_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> computers_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> clothes_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> sports_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> health_list = new ArrayList<ItemBox>();
    ArrayList<ItemBox> search_results = new ArrayList<ItemBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        requestQueue = Volley.newRequestQueue(this);

        renderCategories();         //render all categories with their items

//      Categories draw-bar button
        ImageButton cat = (ImageButton) findViewById(R.id.btn_categories);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        Button health_vm = (Button) findViewById(R.id.vm_health);
        health_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewMore(8, health_list);
            }
        });

        Button sports_vm = (Button) findViewById(R.id.vm_sports);
        sports_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewMore(10, sports_list);
            }
        });

//      Search Button
        EditText search_text = findViewById(R.id.txt_search);
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });

        System.out.println(books_list);
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

    //  Open View More page
    void openViewMore(int code, ArrayList<ItemBox> list){
        Intent intent = new Intent(this, ViewMore.class);
        intent.putExtra("depart_code", code);

        Bundle b = new Bundle();
        b.putSerializable("list", list);
        intent.putExtra("bundle", b);
        startActivity(intent);
    }

    void openSearch(){
        Intent intent = new Intent(this, Search.class);
//        intent.putExtra("search", search);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("search_results", search_results);
//        intent.putExtra("search_bundle", bundle);
        startActivity(intent);
    }

    //  Parsing data from database and adding it to an arraylist (for easy access)
    private void parseData(JSONArray array, String count) {
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

            if (count.equals("1")) computers_list.add(new ItemBox(name, "R " + price, image_url, description));
            else if (count.equals("3")) books_list.add(new ItemBox(name, "R " + price, image_url, description));
            else if (count.equals("6")) clothes_list.add(new ItemBox(name, "R " + price, image_url, description));
            else if (count.equals("8")) health_list.add(new ItemBox(name, "R " + price, image_url, description));
            else if (count.equals("10")) sports_list.add(new ItemBox(name, "R " + price, image_url, description));
            else search_results.add(new ItemBox(name, "R " + price, image_url, description));

        }
        //Notifying the adapter that data has been added or changed
        //adapter.notifyDataSetChanged();
    }

    //  Fetching the data from the database as a JSON array
    private JsonArrayRequest getDataFromServer(String url, String requestCount) {

        //JsonArrayRequest of volley
        return new JsonArrayRequest(url + String.valueOf(requestCount),
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
        requestQueue.add(getDataFromServer(webURL, String.valueOf(count)));
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
            else renderer(R.id.rv_sports, sports_list);
        }
    }

    private void renderer(int rv, ArrayList<ItemBox> list){
        //display the data in a recyclerview which allows us to scroll through
        recyclerView = findViewById(rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapter = new Itembox_Adapter(list, this, 1);
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