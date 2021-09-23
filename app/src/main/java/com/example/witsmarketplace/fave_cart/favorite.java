package com.example.witsmarketplace.fave_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.witsmarketplace.LandingPage.LandingPage;
import com.example.witsmarketplace.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class favorite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        //        Bottom Navigation
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navListener);
    }

    //    Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent intent = null;
                    if (item.getItemId() == R.id.nav_cart){
                        intent = new Intent(getApplicationContext(), cart.class);
                        startActivity(intent);
                    }
                    else if (item.getItemId() == R.id.nav_home) {
                        intent = new Intent(getApplicationContext(), LandingPage.class);
                        startActivity(intent);
                    }
                    return true;
                }
            };
}