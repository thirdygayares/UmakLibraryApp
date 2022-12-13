package com.firetera.umaklibraryapp.Librarian;

import static android.content.ContentValues.TAG;

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

public class AddBooks extends AppCompatActivity {

    EditText Title, Author, Publisher, PublishedDate, Description, Category;
    Button Save;
    static String getTitle, getAuthor, getPublisher, getPublishedDate, getDescription, getCategory;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        firestore = FirebaseFirestore.getInstance();
        initxml();
        saveMethod();

    }
    private void saveMethod(){
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTitle = Title.getText().toString();
                getAuthor = Author.getText().toString();
                getPublisher = Publisher.getText().toString();
                getPublishedDate = PublishedDate.getText().toString();
                getDescription = Description.getText().toString();
                getCategory = Category.getText().toString();

                HashMap<String, Object> Save = new HashMap<String, Object>();
                Save.put("Title",getTitle);
                Save.put("Author",getAuthor);
                Save.put("Publisher" ,getPublisher);
                Save.put("Published Date" ,getPublishedDate);
                Save.put("Description" ,getDescription);
                Save.put("Category" ,getCategory);

                firestore.collection("Add Books").add(Save).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG","SUCCESS");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,e.toString());
                    }
                });



            }
        });
    }

    private void initxml() {
        Title = (EditText) findViewById(R.id.bookTitle);
        Author = (EditText) findViewById(R.id.bookAuthor);
        Publisher = (EditText) findViewById(R.id.bookPublisher);
        PublishedDate = (EditText) findViewById(R.id.publishedDate);
        Description = (EditText) findViewById(R.id.bookDescription);
        Category = (EditText) findViewById(R.id.bookCategory);
        Save = (Button) findViewById(R.id.saveButton);

    }
}