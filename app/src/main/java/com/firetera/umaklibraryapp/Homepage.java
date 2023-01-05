package com.firetera.umaklibraryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firetera.umaklibraryapp.Adapter.Adapter1;
import com.firetera.umaklibraryapp.Adapter.Adapter2;
import com.firetera.umaklibraryapp.Adapter.MyInterface;
import com.firetera.umaklibraryapp.Model.Model1;
import com.firetera.umaklibraryapp.extension.MyCollege;
import com.firetera.umaklibraryapp.extension.MyCourse;
import com.firetera.umaklibraryapp.extension.MyEmail;
import com.firetera.umaklibraryapp.extension.MyName;
import com.firetera.umaklibraryapp.extension.MySection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Homepage extends Fragment implements MyInterface {

    //ArrayListModel
    ArrayList<Model1> LibrayLogsModel = new ArrayList<>();
    ArrayList<Model1> CategoryModel = new ArrayList<>();
    ArrayList<Model1> YouMightLikeModel = new ArrayList<>();
    ArrayList<Model1> CourseRelatedModel = new ArrayList<>();
    ArrayList<Model1> PopularModel = new ArrayList<>();

    //Adapter initialization
    Adapter1 LibraryAdapter,CategoryAdapter;
    Adapter2 YouMightLikeAdapter,CourseRelatedAdapter,PopularAdapter;

    View view;
    ImageView menu;
    EditText input_search;
    RecyclerView recycler_library_logs, recycler_category,recycler_you_might_like,recycler_course_related,recycler_popular;

    //initiate firebase
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //static
    static String ID = "", BookName = "" , BookImage = "";

    //static to get what is the log activity
    static String activityLog="";
    static String code = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_homepage, container, false);

        //initialization of xml
        initXml();

        //Adapter
         LibraryAdapter = new Adapter1(getContext(), LibrayLogsModel , this, "librarylogs");
         CategoryAdapter = new Adapter1(getContext(), CategoryModel , this, "category");

         YouMightLikeAdapter = new Adapter2(getContext(), YouMightLikeModel , this, "youmightlike");
        CourseRelatedAdapter = new Adapter2(getContext(), CourseRelatedModel , this, "course");
        PopularAdapter = new Adapter2(getContext(), PopularModel , this, "popular");

        //populate data in RecyclerView
        libraryLogsMethod();
        CategoryMethod();
        YouMightLikeMethod();
        CourseRelatedMethod();
        PopularMethod();
        initPersona();
        return view;
    }

    private void initPersona() {
        MyName myName = new MyName(getContext());
        MySection mySection = new MySection(getContext());
        MyCollege myCollege = new MyCollege(getContext());
        MyCourse myCourse = new MyCourse(getContext());
        MyEmail myEmail = new MyEmail(getContext());
    }


    private void PopularMethod() {

        firestore.collection("BOOKS").limit(30)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                PopularModel.add(new Model1(document.getId(),document.get("bookdetails.TITLE").toString(), document.get("image_URL").toString()));
                                PopularAdapter.notifyItemInserted(PopularModel.size());
                            }
                        }else{
                            Log.d("TAG","Errorgettingdocuments:",task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                    }
                });

        recycler_popular.setAdapter(PopularAdapter);
        recycler_popular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void CourseRelatedMethod() {


        firestore.collection("BOOKS").limit(30)
                .whereEqualTo("category", "Computer Science")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                CourseRelatedModel.add(new Model1(document.getId(),document.get("bookdetails.TITLE").toString(), document.get("image_URL").toString()));
                                CourseRelatedAdapter.notifyItemInserted(CourseRelatedModel.size());
                            }
                        }else{
                            Log.d("TAG","Errorgettingdocuments:",task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                    }
                });

        recycler_course_related.setAdapter(CourseRelatedAdapter);
        recycler_course_related.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void YouMightLikeMethod() {

        firestore.collection("BOOKS")
                .orderBy("bookdetails.CREATED_ON", Query.Direction.DESCENDING)
                .limit(30)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                YouMightLikeModel.add(new Model1(document.getId(),document.get("bookdetails.TITLE").toString(), document.get("image_URL").toString()));
                                YouMightLikeAdapter.notifyItemInserted(YouMightLikeModel.size());
                            }
                        }else{
                            Log.d("TAG","Errorgettingdocuments:",task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                    }
                });

        recycler_you_might_like.setAdapter(YouMightLikeAdapter);
        recycler_you_might_like.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    //Library Logs Method
    private void libraryLogsMethod() {
        //data of library logs
        ArrayList<String> Title = new ArrayList<>();
        ArrayList<Integer> Image = new ArrayList<>();

        Title.add("Reading or Research");
        Title.add("SDI");
        Title.add("Borrowing");
        Title.add("Collaboration");
        Title.add("Clearance or Visitor");

        Image.add(R.drawable.logs_reading);
        Image.add(R.drawable.logs_sdi);
        Image.add(R.drawable.logs_borrowing);
        Image.add(R.drawable.logs_collaboration);
        Image.add(R.drawable.logs_clearance);

        for(int i=0; i<Title.size(); i++){
            LibrayLogsModel.add(new Model1(Title.get(i),Image.get(i)));
        }

        LibraryAdapter.notifyItemInserted(Title.size());

        recycler_library_logs.setAdapter(LibraryAdapter);
        recycler_library_logs.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
    }


    private void CategoryMethod() {
        //data of library logs
        ArrayList<String> Title = new ArrayList<>();
        ArrayList<Integer> Image = new ArrayList<>();

        Title.add("Fiction");
        Title.add("Mystery");
        Title.add("Novel");
        Title.add("Science Fiction");
        Title.add("Fantasy");
        Title.add("Narrative");
        Title.add("Non-Fiction");
        Title.add("Historical-Fiction");
        Title.add("Children's Literature");
        Title.add("Computer Science");
        Title.add("Romance Novel");
        Title.add("Thriller");
        Title.add("Fantasy Fiction");
        Title.add("Horror Fiction");
        Title.add("Literary Fiction");
        Title.add("Poetry");
        Title.add("Memoir");
        Title.add("Self Help Book");
        Title.add("Autobiography");
        Title.add("Short Story");

        Image.add(R.drawable.category_fiction);
        Image.add(R.drawable.category_mystery);
        Image.add(R.drawable.category_novel);
        Image.add(R.drawable.category_sciencefiction);
        Image.add(R.drawable.category_fantasy);
        Image.add(R.drawable.category_narrative);
        Image.add(R.drawable.category_nonfiction);
        Image.add(R.drawable.category_history);
        Image.add(R.drawable.category_children);
        Image.add(R.drawable.category_computerscience);
        Image.add(R.drawable.category_romance);
        Image.add(R.drawable.category_thriller);
        Image.add(R.drawable.category_fantasyfiction);
        Image.add(R.drawable.category_horror);
        Image.add(R.drawable.category_literary);
        Image.add(R.drawable.category_poem);
        Image.add(R.drawable.category_memoir);
        Image.add(R.drawable.category_selfhelp);
        Image.add(R.drawable.category_autobiography);
        Image.add(R.drawable.category_shorthistory);


        for(int i=0; i<Title.size(); i++){
            CategoryModel.add(new Model1(Title.get(i),Image.get(i)));
        }

        CategoryAdapter.notifyItemInserted(Title.size());

        recycler_category.setAdapter(CategoryAdapter);
        recycler_category.setLayoutManager(new GridLayoutManager(getContext() , 2, GridLayoutManager.HORIZONTAL, false));

    }

    private void initXml() {
        menu = view.findViewById(R.id.menu);
        input_search = view.findViewById(R.id.input_search);
        recycler_library_logs = view.findViewById(R.id.recycler_library_logs);
        recycler_category = view.findViewById(R.id.recycler_category);
        recycler_you_might_like = view.findViewById(R.id.recycler_you_might_like);
        recycler_course_related = view.findViewById(R.id.recycler_course_related);
        recycler_popular = view.findViewById(R.id.recycler_popular);

    }

    @Override
    public void onItemClick(int pos, String onclick) {
        Intent intent;
        switch (onclick){
            case "librarylogs":
                activityLog = LibrayLogsModel.get(pos).getTitle();
                Date date = new Date();

                // getting the object of the Timestamp class
                Timestamp ts = new Timestamp(date.getTime());

                code = ts + "logs";


                String borrowerId = firebaseAuth.getUid();
                String borrowerName = MyName.getName();
                String borrowerCollege = MyCollege.getName();
                String borrowerCourse = MyCourse.getName();
                String borrowerSection = MySection.getName();
                String borrowerEmail = MyEmail.getName();

                HashMap<String, Object> uploadBorrowRaw = new HashMap<>();
                uploadBorrowRaw.put("QR_CODE", code);
                uploadBorrowRaw.put("ACTIVITY", activityLog);
                uploadBorrowRaw.put("BORROWER_ID", borrowerId);
                uploadBorrowRaw.put("BORROWER_NAME", borrowerName);
                uploadBorrowRaw.put("COLLEGE", borrowerCollege);
                uploadBorrowRaw.put("COURSE", borrowerCourse);
                uploadBorrowRaw.put("SECTION", borrowerSection);
                uploadBorrowRaw.put("EMAIL", borrowerEmail);

                firestore.collection("LOGS_WAITING_AREA")
                        .document(code)
                        .set(uploadBorrowRaw)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(getContext(), ActivityLogs.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;



            case "category":
                Toast.makeText(getContext(), "category", Toast.LENGTH_SHORT).show();
                break;

            case "youmightlike":
                ID = YouMightLikeModel.get(pos).getId();
                BookName = YouMightLikeModel.get(pos).getTitle();
                BookImage = YouMightLikeModel.get(pos).getImagestring();
                intent = new Intent(getContext(), BookDetails.class);
                startActivity(intent);
                break;

            case "course":
                ID = CourseRelatedModel.get(pos).getId();
                BookName = CourseRelatedModel.get(pos).getTitle();
                BookImage = CourseRelatedModel.get(pos).getImagestring();
                intent = new Intent(getContext(), BookDetails.class);
                startActivity(intent);
                break;

            case "popular":
                ID = PopularModel.get(pos).getId();
                BookName = PopularModel.get(pos).getTitle();
                BookImage = PopularModel.get(pos).getImagestring();
                intent = new Intent(getContext(), BookDetails.class);
                startActivity(intent);
                break;
        }
    }
}