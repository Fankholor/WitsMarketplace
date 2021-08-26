package com.example.witsmarketplace.LandingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.witsmarketplace.MainActivity;
import com.example.witsmarketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.util.ArrayList;

public class LandingPage extends AppCompatActivity implements RecyclerView.OnScrollChangeListener{

    String webURL = "https://lamp.ms.wits.ac.za/home/s2172765/product.php?ID=";
    private RecyclerView recyclerView;
    ArrayList<ItemBox> itemBoxes = new ArrayList<ItemBox>();
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        requestQueue = Volley.newRequestQueue(this);

        getData();                  //get data from database

//      display the data in a recyclerview which allows us to scroll through
        recyclerView = findViewById(R.id.rv_books);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapter = new Itembox_Adapter(itemBoxes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//      Categories draw-bar button
        ImageButton cat =(ImageButton)findViewById(R.id.btn_categories);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

//  Pop up menu for the categories draw-bar
    public void showPopup(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.categories_menu, popupMenu.getMenu());
        popupMenu.show();
    }

//  Parsing data from database and adding it to an arraylist (for easy access)
    private void parseData(JSONArray array) {
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
            itemBoxes.add(new ItemBox(name, "R " + price, image, description));
        }
        //Notifying the adapter that data has been added or changed
//        adapter.notifyDataSetChanged();
    }

//  Fetching the data from the database as a JSON array
    private JsonArrayRequest getDataFromServer(int requestCount) {

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(webURL + String.valueOf(1),
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
        //Returning the request
        return jsonArrayRequest;
    }

    private void getData(){
        requestQueue.add(getDataFromServer(1));
    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //if Scrolled to the end then fetch more data
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getData again
            getData();
        }
    }

}