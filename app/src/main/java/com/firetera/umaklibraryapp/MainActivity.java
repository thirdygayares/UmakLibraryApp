package com.firetera.umaklibraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button buttonShelves = findViewById(R.id.button_shelves);
        Button buttonBorrow = findViewById(R.id.button_borrow);

        buttonShelves.setOnClickListener(new view.OnClickListener(){
            @override
            public void onClick(View v) {
                Toast.makeText(context:MainActivity.this, text "SHEALVES", Toast.LENGHT_SHORT).show()
            }
        });
        switchEnableButtom.setOnCheckedChangeListener(){
            @override
            public void onClick(View v) {
                Toast.makeText(context:MainActivity.this, text "BORROW", Toast.LENGHT_SHORT).show()
            }

        }


    }
}