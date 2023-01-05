package com.firetera.umaklibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firetera.umaklibraryapp.Model.AddFavoritesModel;
import com.firetera.umaklibraryapp.Model.AddShelvesModel;
import com.firetera.umaklibraryapp.extension.MyCollege;
import com.firetera.umaklibraryapp.extension.MyCourse;
import com.firetera.umaklibraryapp.extension.MyEmail;
import com.firetera.umaklibraryapp.extension.MyName;
import com.firetera.umaklibraryapp.extension.MySection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.sql.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;

public class BookDetails extends AppCompatActivity {

    private static final String TAG = "TAG";
    //call xml
    TextView txt_bookTitle, txt_author, txt_rating, txt_read, txt_fav, txt_loc, txt_publisher, txt_publishedDate, txt_description,txt_available;
    Button btn_addbooks,btn_borrow,btn_addtoshelves;
    ImageView imageses;
    ImageButton img_btn_saved;

    //initiate firebase
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    //call Homepage class
    Homepage homepage;

    //static
    static String code = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        //initate xml
        initXml();

        //inititiate personal
        initPersona();

        //initiate homepage
        homepage = new Homepage();

        //setUpData for book details
        setUpDataBook();

        //borrow book
        borrowButonMethod();

        //add to shelves click
        addToShelves();

        //save click
        saveMethod();

    }

    private void addToShelves() {
        btn_addtoshelves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AddShelvesModel addShelvesModel = new AddShelvesModel(firebaseAuth.getUid(), homepage.ID, homepage.BookImage, txt_bookTitle.getText().toString(), txt_author.getText().toString());

                firestore.collection("SHELVES").add(addShelvesModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(BookDetails.this, "Add To Shelves Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void saveMethod() {
        img_btn_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AddFavoritesModel addFavoritesModel = new AddFavoritesModel(firebaseAuth.getUid(), homepage.ID, homepage.BookImage, txt_bookTitle.getText().toString(), txt_author.getText().toString());

                firestore.collection("FAVORITES").add(addFavoritesModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(BookDetails.this, "Add To Favorites", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initPersona() {
       MyName myName = new MyName(BookDetails.this);
        MySection mySection = new MySection(BookDetails.this);
       MyCollege myCollege = new MyCollege(BookDetails.this);
       MyCourse myCourse = new MyCourse(BookDetails.this);
       MyEmail myEmail = new MyEmail(BookDetails.this);
    }

    private void borrowButonMethod() {
        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FieldValue date = FieldValue.serverTimestamp();
                // getting the system date

                Date date = new Date();

                // getting the object of the Timestamp class
                Timestamp ts = new Timestamp(date.getTime());

                code = ts.toString() + "borrow";
                String booksid = homepage.ID;
                String image = homepage.BookImage;
                String title = txt_bookTitle.getText().toString();
                String borrowerId = firebaseAuth.getUid();
                String borrowerName = MyName.getName();
                String borrowerCollege = MyCollege.getName();
                String borrowerCourse = MyCourse.getName();
                String borrowerSection = MySection.getName();
                String borrowerEmail = MyEmail.getName();

                HashMap<String, Object> uploadBorrowRaw = new HashMap<>();
                uploadBorrowRaw.put("QR_CODE", code);
                uploadBorrowRaw.put("BOOK_ID", booksid);
                uploadBorrowRaw.put("TITLE", title);
                uploadBorrowRaw.put("BORROWER_ID", borrowerId);
                uploadBorrowRaw.put("BORROWER_NAME", borrowerName);
                uploadBorrowRaw.put("COLLEGE", borrowerCollege);
                uploadBorrowRaw.put("COURSE", borrowerCourse);
                uploadBorrowRaw.put("SECTION", borrowerSection);
                uploadBorrowRaw.put("EMAIL", borrowerEmail);
                uploadBorrowRaw.put("IMAGE", image);


                firestore.collection("BORROW_WAITING_AREA").document(code).set(uploadBorrowRaw)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Intent intent = new Intent(BookDetails.this, Borrow.class);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
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
                                txt_loc.setText(document.get("bookdetails.LOCATION").toString());

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
        img_btn_saved = findViewById(R.id.img_btn_saved);
    }

}