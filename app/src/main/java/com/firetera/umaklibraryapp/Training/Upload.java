package com.firetera.umaklibraryapp.Training;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firetera.umaklibraryapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Upload extends AppCompatActivity {

    EditText book, Author;
    Button save;
    static String getBook, getAuthor;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        firestore = FirebaseFirestore.getInstance();
        initXml();
        saveMethod();

    }

    private void saveMethod() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   //firebase
                getBook = book.getText().toString();
                getAuthor = Author.getText().toString();


                HashMap<String, Object> save = new HashMap<String,Object>();
                save.put("Book",getBook);
                save.put("Author",getAuthor);


                firestore.collection("bookstesting").add(save).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "Success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.toString());
                    }
                });

            }
        });

    }

    private void initXml() {
        book = (EditText) findViewById(R.id.book);
        Author = (EditText) findViewById(R.id.Author);
        save = (Button) findViewById(R.id.save);

    }
}