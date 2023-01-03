package com.firetera.umaklibraryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.firetera.umaklibraryapp.Adapter.Adapter1;
import com.firetera.umaklibraryapp.Adapter.Adapter2;
import com.firetera.umaklibraryapp.Adapter.MyInterface;
import com.firetera.umaklibraryapp.Model.Model1;

import java.util.ArrayList;

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

        return view;
    }

    private void PopularMethod() {
        //dummy data for you might like section
        ArrayList<String> Title = new ArrayList<>();
        ArrayList<String> Image = new ArrayList<>();

        Title.add("She said Three");
        Title.add("Noli Me Tangere");
        Title.add("Python for beginner");

        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2Fshesaid.png?alt=media&token=476e5b73-a260-4e5f-aada-d4ad430e2f69");
        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2Fnoli.png?alt=media&token=5af5f93d-71e3-4c77-a52d-43784487bc80");
        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2Fpythonforbeginners.png?alt=media&token=5997a312-2421-40f8-92fd-e67bf76c5565");

        for(int i=0;i<Title.size(); i++){
            PopularModel.add(new Model1(Title.get(i), Image.get(i)));
        }

        PopularAdapter.notifyItemInserted(Title.size());

        recycler_popular.setAdapter(PopularAdapter);
        recycler_popular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void CourseRelatedMethod() {
        //dummy data for course related section
        ArrayList<String> Title = new ArrayList<>();
        ArrayList<String> Image = new ArrayList<>();

        Title.add("The Python Book");
        Title.add("Python for beginner");
        Title.add("Fundamentals of Computer Programming");

        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2Fpythonbook.png?alt=media&token=b6c1bb29-0ebc-4307-8b5f-03bd623c44d6");
        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2Fpythonforbeginners.png?alt=media&token=5997a312-2421-40f8-92fd-e67bf76c5565");
        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2FComputerprogrammingbook.png?alt=media&token=11188061-2cc5-4aad-87ae-08e8a2c63520");

        for(int i=0;i<Title.size(); i++){
            CourseRelatedModel.add(new Model1(Title.get(i), Image.get(i)));
        }

        CourseRelatedAdapter.notifyItemInserted(Title.size());

        recycler_course_related.setAdapter(CourseRelatedAdapter);
        recycler_course_related.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void YouMightLikeMethod() {
        //dummy data for you might like section
        ArrayList<String> Title = new ArrayList<>();
        ArrayList<String> Image = new ArrayList<>();

        Title.add("Fundamentals of Computer Programming");
        Title.add("Python for beginners");
        Title.add("Web Programming");

        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2FComputerprogrammingbook.png?alt=media&token=11188061-2cc5-4aad-87ae-08e8a2c63520");
        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2Fpythonforbeginners.png?alt=media&token=5997a312-2421-40f8-92fd-e67bf76c5565");
        Image.add("https://firebasestorage.googleapis.com/v0/b/umak-library-app.appspot.com/o/Book_cover%2Fwebprogrammingbooks.png?alt=media&token=279f2997-30b5-4c9f-a492-ca14ef8498ea");

        for(int i=0;i<Title.size(); i++){
            YouMightLikeModel.add(new Model1(Title.get(i), Image.get(i)));
        }

        CourseRelatedAdapter.notifyItemInserted(Title.size());

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

        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);

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

        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);
        Image.add(R.drawable.placeholder);

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

    }
}