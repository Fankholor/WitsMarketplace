package com.example.witsmarketplace.fave_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.witsmarketplace.R;
import com.example.witsmarketplace.SharedPreference;

public class Address extends AppCompatActivity {
    public static final String Prefs = "sharedprefs ";
    public static SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        EditText Street_Name = findViewById(R.id.Street);
        EditText City_Name = findViewById(R.id.City);
        EditText Suburb_Name = findViewById(R.id.Suburb);
        EditText Country_Name = findViewById(R.id.Country);
        Button Continue = findViewById(R.id.Continue);
        Button Save = findViewById(R.id.Save);

        sharedPreference = new SharedPreference(this);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreference.setSH("Street", Street_Name.getText().toString().trim());
                sharedPreference.setSH("City", City_Name.getText().toString().trim());
                sharedPreference.setSH("Suburb", Suburb_Name.getText().toString().trim());
                sharedPreference.setSH("Country", Country_Name.getText().toString().trim());


                System.out.println(sharedPreference.getSH("Street"));
                System.out.println(sharedPreference.getSH("City"));
                System.out.println(sharedPreference.getSH("Suburb"));
                System.out.println(sharedPreference.getSH("Country"));

            }
        });

    }
}
