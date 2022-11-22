package com.firetera.umaklibraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    TextView email;
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        /to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        email = (TextView) findViewById(R.id.email);
        Logout = findViewById(R.id.logout);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

//        Button buttonShelves = findViewById(R.id.button_shelves);
//        Button buttonBorrow = findViewById(R.id.button_borrow);
//
//        buttonShelves.setOnClickListener(new view.OnClickListener(){
//            @override
//            public void onClick(View v) {
//                Toast.makeText(context:MainActivity.this, text "SHEALVES", Toast.LENGHT_SHORT).show()
//            }
//        });
//        switchEnableButtom.setOnCheckedChangeListener(){
//            @override
//            public void onClick(View v) {
//                Toast.makeText(context:MainActivity.this, text "BORROW", Toast.LENGHT_SHORT).show()
//            }
//
//        }


    }
}