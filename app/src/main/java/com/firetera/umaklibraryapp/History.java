package com.firetera.umaklibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firetera.umaklibraryapp.Adapter.FavoritesAdapter;
import com.firetera.umaklibraryapp.Adapter.MyInterface;
import com.firetera.umaklibraryapp.Model.FavoriteModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class History extends AppCompatActivity implements MyInterface {

    private static final String TAG = "TAG";
    //Arraylist Model
    ArrayList<FavoriteModel> favoriteModels;

    //adapter
    FavoritesAdapter booksAdapter;

    //call recycler view
    RecyclerView recycler_books;
    TextView btn_loadmore;
    ProgressBar progressbar, progressbarcursor;
    NestedScrollView section_content;

    View view;

    // variable for our shimmer frame layout
    private ShimmerFrameLayout shimmerFrameLayout;

    //call and inituate firebase
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DocumentSnapshot lastResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        //initiate xml
        initXml();

        //initiate model
        favoriteModels = new ArrayList<>();

        //initiate adapter
        booksAdapter = new FavoritesAdapter(History.this, favoriteModels, this, "history" );

        //populate data in books
        setUpBooks();


        //count all books method
        countAllBooks();


    }

    private void countAllBooks() {

        btn_loadmore.setVisibility(View.GONE);
 //       CollectionReference collection = firestore.collection("BOOKS");
//        Query query = collection.whereEqualTo("archive", false);
//        AggregateQuery countQuery = query.count();
//        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                AggregateQuerySnapshot snapshot = task.getResult();
//                Log.d(TAG, "Count: " + snapshot.getCount());
//            } else {
//                Log.d(TAG, "Count failed: ", task.getException());
//            }
//        });

    }


    private void setUpBooks() {
        // on below line we are
        // starting our shimmer layout.
        shimmerFrameLayout.startShimmer();

        CollectionReference bookRef = firestore.collection("BORROW");

        //get Last Snapshot

        bookRef.limit(20)
                .whereEqualTo("BORROWER_ID",  firebaseAuth.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            favoriteModels.add(new FavoriteModel(documentSnapshot.getId(),documentSnapshot.get("TITLE").toString(), documentSnapshot.get("IMAGE").toString()));
                            //Log.d(TAG, "DATA " + documentSnapshot.getId() + " = > " + documentSnapshot.getData())
                        }

                        //lastResult = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                        booksAdapter.notifyItemInserted(favoriteModels.size());
                        section_content.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(History.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                });

//        btn_loadmore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressbarcursor.setVisibility(View.VISIBLE);
//                Query query;
//                if (lastResult == null) {
//                    query = bookRef
//                            .whereEqualTo("BORROWERID",  firebaseAuth.getUid())
//                            .limit(5);
//                } else {
//                    query = bookRef
//                            .whereEqualTo("BORROWERID",  firebaseAuth.getUid())
//                            .startAfter(lastResult)
//                            .limit(5);
//                }
//
//                query.get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            String data = "";
//                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                                    //Log.d(TAG, documentSnapshot.get("image_URL").toString());
//                                    favoriteModels.add(new FavoriteModel(documentSnapshot.get("BOOKID").toString(),documentSnapshot.get("NAME").toString(), documentSnapshot.get("IMAGE_URL").toString(), documentSnapshot.get("AUTHOR").toString()));
//                                    data = documentSnapshot.get("image_URL").toString();
//                                }
//
//                                if (queryDocumentSnapshots.size() > 0) {
//                                    Log.d(TAG, data);
//                                    booksAdapter.notifyDataSetChanged();
//                                    lastResult = queryDocumentSnapshots.getDocuments()
//                                            .get(queryDocumentSnapshots.size() - 1);
//                                }
//                                if(queryDocumentSnapshots.size() == 0){
//                                    btn_loadmore.setVisibility(View.INVISIBLE);
//                                }
//
//                                progressbarcursor.setVisibility(View.GONE);
//
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressbarcursor.setVisibility(View.GONE);
//                                Toast.makeText(Favorites.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });

        //booksAdapter.notifyItemInserted(Title.size());

        recycler_books.setAdapter(booksAdapter);
        recycler_books.setLayoutManager(new LinearLayoutManager(History.this));
    }

    private void initXml() {
        recycler_books = findViewById(R.id.recycler_books);
        btn_loadmore = findViewById(R.id.btn_loadmore);
        progressbar = findViewById(R.id.progressbar);
        progressbarcursor = findViewById(R.id.progressbar_cursor);
        section_content = findViewById(R.id.section_content);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
    }

    @Override
    public void onItemClick(int pos, String onclick) {

        Homepage homepage = new Homepage();
        homepage.ID = favoriteModels.get(pos).getId();
        homepage.BookName = favoriteModels.get(pos).getTitle();
        homepage.BookImage = favoriteModels.get(pos).getImage();
        Intent intent = new Intent(History.this, BookDetails.class);
        startActivity(intent);
    }
}