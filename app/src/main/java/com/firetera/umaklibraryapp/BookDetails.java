package com.firetera.umaklibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {

    private static final String TAG = "TAG";
    //call xml
    TextView txt_bookTitle, txt_author, txt_rating, txt_read, txt_fav, txt_loc, txt_publisher, txt_publishedDate, txt_description,txt_available;
    Button btn_addbooks,btn_borrow,btn_addtoshelves;
    ImageView imageses;

    //initiate firebase
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    //call Homepage class
    Homepage homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        //initate xml
        initXml();

        //initiate homepage
        homepage = new Homepage();

        //setUpData for book details
        setUpDataBook();



    }

    private void setUpDataBook() {
        //set Name and Image
        Picasso.get().load(homepage.BookImage).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).resize(720,1080).centerCrop().into(imageses);
        txt_bookTitle.setText(homepage.BookName);


        firestore.collection("BOOKS").document(homepage.ID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document =task.getResult();
                            if(document.exists()){
                                txt_author.setText(document.get("author").toString());
                                txt_publishedDate.setText(document.get("bookdetails.PUBLISHED_DATE").toString());
                                txt_publisher.setText(document.get("bookdetails.PUBLISHER").toString());
                                txt_rating.setText(document.get("bookdetails.RATING_TOTAL").toString() + " ("  + document.get("bookdetails.RATING_AVERAGE").toString() + ")");
                                txt_description.setText(document.get("bookdetails.DESCRIPTION").toString());
                            }else{
                                Log.d(TAG,"Nosuchdocument");
                            }
                        }else{
                            Log.d(TAG,"getfailedwith",task.getException());
                        }
                    }
                });
    }


    private void initXml() {
        txt_bookTitle = findViewById(R.id.txt_bookTitle);
        txt_author = findViewById(R.id.txt_author);
        txt_rating = findViewById(R.id.txt_rating);
        txt_read = findViewById(R.id.txt_read);
        txt_fav = findViewById(R.id.txt_fav);
        txt_loc = findViewById(R.id.txt_loc);
        txt_publisher = findViewById(R.id.txt_publisher);
        txt_publishedDate = findViewById(R.id.txt_publishedDate);
        txt_description = findViewById(R.id.txt_description);
        imageses = findViewById(R.id.images);
        txt_available  = findViewById(R.id.txt_available);
        btn_borrow  = findViewById(R.id.btn_borrow);
        btn_addtoshelves  = findViewById(R.id.btn_addtoshelves);
    }

}