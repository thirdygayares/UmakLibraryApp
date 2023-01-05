package com.firetera.umaklibraryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.umaklibraryapp.Database.DatabaseHelper;
import com.firetera.umaklibraryapp.extension.CodeCollege;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends Fragment {

    View view;

    ImageView profile_image;
    TextView txt_fullname,txt_college_section,txt_email,txt_count_read,txt_count_logs;
    LinearLayout btn_favorites,btn_history,btn_leaderboards, btn_book_read,btn_library_logs, btn_information,btn_developer,btn_suggestion,btn_like,btn_help,btn_logout;


    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;



    //Databasehelper
    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_profile, container, false);

        //initialization of xml file
        initXml();

        //setup firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //initiate databasehelper
        databaseHelper = new DatabaseHelper(getContext());

        //process dialog Method
        processDialogMethod();

        //manipylate profile image, fullname, college, section and email
        detailsMethod();

        //clicking favorites
        favoritesMethod();

        //clciking History
        historyMethod();

        //clicking counting read and logs
        countingMethod();

        //Intent button of btn_information, btn_developer,btn_suggestion,btn_like,btn_help
        intentMethod();

        logoutMehod();



        return view;

    }

    private void historyMethod(){
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), History.class);
                startActivity(intent);
            }
        });
    }

    private void favoritesMethod() {
        btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Favorites.class);
                startActivity(intent);
            }
        });
    }

    private void processDialogMethod() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);
    }

    private void logoutMehod() {



        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Logout");
                progressDialog.show();


                Boolean deleteAccountLocal = databaseHelper.logout(firebaseAuth.getUid());
                if(deleteAccountLocal == true){
                    firebaseAuth.signOut();
                    Intent intent = new Intent(getContext(),Login.class);
                    startActivity(intent);
                }

               progressDialog.hide();

            }
        });

    }


    private void intentMethod() {
    }

    private void countingMethod() {
    }

    private void detailsMethod() {
        //progressDialog.setTitle("Fetching Data");
        //progressDialog.show();

        Cursor getAccount = databaseHelper.getValue();
        try {
            if(getAccount.getCount() > 0){
                while(getAccount.moveToNext()){
                    CodeCollege codeCollege = new CodeCollege(getAccount.getString(4));
                    txt_fullname.setText(getAccount.getString(0));
                    txt_college_section.setText(codeCollege.getCollege() + " " + getAccount.getString(2));
                    txt_email.setText(getAccount.getString(7));
                }
            }else{
                Toast.makeText(getContext(), "walang laman", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.d("TAG", e.getMessage());
        }



    }

    private void initXml() {
        profile_image = view.findViewById(R.id.profile_image);
        txt_fullname = view.findViewById(R.id.txt_fullname);
        txt_college_section = view.findViewById(R.id.txt_college_section);
        txt_email = view.findViewById(R.id.txt_email);
        txt_count_read = view.findViewById(R.id.txt_count_read);
        txt_count_logs = view.findViewById(R.id.txt_count_logs);
        btn_book_read = view.findViewById(R.id.btn_book_read);
        btn_library_logs = view.findViewById(R.id.btn_library_logs);
        btn_information = view.findViewById(R.id.btn_information);
        btn_developer = view.findViewById(R.id.btn_developer);
        btn_suggestion = view.findViewById(R.id.btn_suggestion);
        btn_like = view.findViewById(R.id.btn_like);
        btn_help = view.findViewById(R.id.btn_help);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_history  = view.findViewById(R.id.btn_history);
        btn_favorites = view.findViewById(R.id.btn_favorites);

    }
}