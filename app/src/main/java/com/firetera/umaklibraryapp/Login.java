package com.firetera.umaklibraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.umaklibraryapp.Database.DatabaseHelper;
import com.firetera.umaklibraryapp.Model.AccountModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    //call xml file
    Button login;
    TextInputEditText inputEmail, inputPassword;
    TextView newAccount,errorLogin;
    ProgressBar progressBar;

    //initiate direbase
    FirebaseAuth firebaseAuth  = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    //call sqlite
    DatabaseHelper databaseHelper;
    String type = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initiate xml
        initxml();

        //initiate sqlite
        databaseHelper = new DatabaseHelper(Login.this);



        //newAccountMethod();

        //login Method
        login();

    }

    private void login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show progressbar
                progressBar.setVisibility(View.VISIBLE);


                String emaiL, passworD;
                emaiL = String.valueOf(inputEmail.getText());
                passworD = String.valueOf(inputPassword.getText());

                if (emaiL.isEmpty()) {
                    inputEmail.setError("Email is Required");
                    return;
                }

                if (passworD.isEmpty()) {
                    inputPassword.setError("Password is Required");
                    return;
                }

                if (!emaiL.equals("") && !passworD.equals("")) {

                    firebaseAuth.signInWithEmailAndPassword(emaiL, passworD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            //save data to sqlite
                            firestore.collection("Account").document(firebaseAuth.getUid())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                try {
                                                    type = documentSnapshot.get("type").toString();

                                                    firestore.collection("borrowers").document(firebaseAuth.getUid())
                                                            .get()
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    if (documentSnapshot.exists()) {
                                                                        try {
                                                                            AccountModel accountModel = new AccountModel(firebaseAuth.getUid(), documentSnapshot.get("Name").toString(), documentSnapshot.get("Section").toString(), documentSnapshot.get("Course").toString(), documentSnapshot.get("College").toString(), documentSnapshot.get("Gender").toString(), documentSnapshot.get("Number").toString(),documentSnapshot.get("UmakEmail").toString());
                                                                            Boolean addAccount = databaseHelper.addAccount(accountModel);
                                                                            if (addAccount == true) {
                                                                                Intent intent = new Intent(Login.this, MainActivity.class);
                                                                                startActivity(intent);
                                                                            } else {
                                                                                Toast.makeText(Login.this, "Something Error", Toast.LENGTH_LONG).show();
                                                                            }
                                                                        } catch (Exception e) {
                                                                            Log.d("TAG", e.getMessage());
                                                                        }
                                                                        progressBar.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                                    progressBar.setVisibility(View.GONE);
                                                                }
                                                            });

                                                }catch (Exception e){
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(Login.this, "Something Error", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    });


                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            errorLogin.setVisibility(View.VISIBLE);
                            errorLogin.setText(e.getMessage());
                        }
                    });

                } else {

                    errorLogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    errorLogin.setText("All Fields are Required");
                }

            }
        });
    }

    private void newAccountMethod() {
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, NewAccount.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void initxml() {
        errorLogin = (TextView) findViewById(R.id.errorLogin);
        login = (Button) findViewById(R.id.login);
        inputEmail =  findViewById(R.id.inputEmail);
        inputPassword =  findViewById(R.id.inputPassword);
        newAccount =  findViewById(R.id.newAccount);
        progressBar  = findViewById(R.id.progressbar);
    }
}