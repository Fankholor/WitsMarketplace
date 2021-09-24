package com.example.witsmarketplace.ViewMore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.witsmarketplace.LandingPage.ItemBox;
import com.example.witsmarketplace.LandingPage.Itembox_Adapter;
import com.example.witsmarketplace.LandingPage.LandingPage;
import com.example.witsmarketplace.LandingPage.Search;
import com.example.witsmarketplace.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ViewMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);

        TextView title = findViewById(R.id.vm_title);

//      Set title of the View More page
        Bundle b = getIntent().getExtras();
        int code = b.getInt("depart_code");
        title.setText(set_vm_title(code));

//      set list items
        Bundle args = getIntent().getBundleExtra("bundle");
        ArrayList<ItemBox> list = (ArrayList<ItemBox>) args.getSerializable("list");
        renderer(R.id.rv_viewMore, list);

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

    String set_vm_title(int code){
        String title = "";

        if (code == 3) title = "Books";
        else if (code == 1) title = "Computer & Electronics";
        else if (code == 6) title = "Clothes";
        else if (code == 8) title = "Health & Hygiene";
        else if (code == 10) title = "Sports & Training";

        return title;
    }

    private void renderer(int rv, ArrayList<ItemBox> list){
        //display the data in a recyclerview which allows us to scroll through
        RecyclerView recyclerView = findViewById(rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter adapter = new Itembox_Adapter(list, this, 2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

}