package com.example.witsmarketplace.LandingPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.witsmarketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class SearchResults extends AppCompatActivity {

    private RequestQueue requestQueue;
    ArrayList<ItemBox> search_results = new ArrayList<ItemBox>();
    String searchURL = "https://lamp.ms.wits.ac.za/home/s2172765/Search.php?ID=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        requestQueue = Volley.newRequestQueue(this);


        TextView search_key = findViewById(R.id.search_key);

//      Set search key
        Bundle b = getIntent().getExtras();
        String search = b.getString("search");
        search_key.setText('"' + search + '"');

//      set search result items
//        Bundle args = getIntent().getBundleExtra("search_bundle");
//        ArrayList<ItemBox> list = (ArrayList<ItemBox>) args.getSerializable("search_result");

        getData(search);

        ImageView search_btn = findViewById(R.id.btn_search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });
    }

    void openSearch(){
        Intent intent = new Intent(this, Search.class);
//        intent.putExtra("search", search);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("search_results", search_results);
//        intent.putExtra("search_bundle", bundle);
        startActivity(intent);
    }

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

            search_results.add(new ItemBox(name, "R " + price, image_url, description,images));
        }
        //Notifying the adapter that data has been added or changed
        //adapter.notifyDataSetChanged();
        renderer(search_results);
    }

    private JsonArrayRequest getDataFromServer(String url, String requestCount) {

        //JsonArrayRequest of volley
        return new JsonArrayRequest(url + requestCount,
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

    private void getData(String key){
        requestQueue.add(getDataFromServer(searchURL, key));

    }

    private void renderEverything(){
//        getData(search);
    }

    private void renderer(ArrayList<ItemBox> list){
        //display the data in a recyclerview which allows us to scroll through
        RecyclerView recyclerView = findViewById(R.id.rv_searchResults);
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter adapter = new Itembox_Adapter(list, this, 2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}