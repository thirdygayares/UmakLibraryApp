package com.firetera.umaklibraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class NewAccount extends AppCompatActivity {

    TextView errorEmail;
    Button CheckEmailbtn,SubmitAccount;
    EditText email,password,confirmPassword;
    CardView CheckEmail,CreatePassword;
    static String emailInput, passwordInput,confirmPasswordInput;
    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        initXml();
        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        checkEmailMethod();
        createPassword();
    }



    private void createPassword() {
        SubmitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 }
            });
    }

    private void checkEmailMethod() {
        CheckEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Click");

                emailInput = email.getText().toString();

                if(emailInput.equals("")){
                    email.setError("Required");
                }else{
                Log.d("TAG", "Input is" + emailInput);

                firestore.collection("borrowers")
                        .whereEqualTo("UmakEmail", emailInput)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document: task.getResult()) {
                                        Log.d("TAG", "SUCCESS " + document.get("College").toString());
                                        if(document.exists()){
                                            CheckEmail.setVisibility(View.GONE);
                                            CreatePassword.setVisibility(View.VISIBLE);
                                            errorEmail.setVisibility(View.GONE);

                                            SubmitAccount.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {



                                            passwordInput = password.getText().toString();
                                            confirmPasswordInput = confirmPassword.getText().toString();

                                            if(passwordInput.equalsIgnoreCase("")){
                                                password.setError("Required");
                                            }
                                            if(confirmPasswordInput.equalsIgnoreCase("")){
                                                confirmPassword.setError("Required");
                                            }

                                            if(!confirmPasswordInput.equals(passwordInput)){
                                                confirmPassword.setError("Password Does Not Match");
                                            }else{

                                                firebaseAuth.createUserWithEmailAndPassword(emailInput,confirmPasswordInput).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                    @Override
                                                    public void onSuccess(AuthResult authResult) {
                                                        Intent intent = new Intent(NewAccount.this, Login.class);
                                                        startActivity(intent);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Log.d("PASSWORD", e.toString());
                                                        if(e.toString().equals("com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]")){
                                                            Log.d("PASSWORD", "hey");
                                                            confirmPassword.setError("Password should be at least 6 characters");
                                                        }

                                                        if(e.toString().equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.")){
                                                            Log.d("PASSWORD", "hey2");
                                                            Toast.makeText(NewAccount.this, "Email address is already Exists", Toast.LENGTH_SHORT).show();
//                                                            Email.setError("Email address is already Exists");
                                                        }


                                                        if(e.toString().equals("com.google.firebase.FirebaseNetworkException: A network error (such as timeout, interrupted connection or unreachable host) has occurred.")){
                                                            Log.d("PASSWORD", "hey3");
                                                            Toast.makeText(NewAccount.this, "Check Your Internet", Toast.LENGTH_SHORT).show();
                                                        }

//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }

                                                }
                                            });


                                        }else if(!document.exists()){
                                            Log.d("TAG", "NOT SUCCESSFUL");
                                        }
                                    }

                                }else{
                                    Log.d("TAG", "NOT SUCCESSFUL");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "NOT SUCCESSFUL");
                            }
                        });

                errorEmail.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    private void initXml() {
        errorEmail = (TextView) findViewById(R.id.errorEmail);
        CheckEmailbtn = (Button) findViewById(R.id.CheckEmailbtn);
        SubmitAccount = (Button) findViewById(R.id.SubmitAccount);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        CheckEmail = (CardView) findViewById(R.id.CheckEmail);
        CreatePassword = (CardView) findViewById(R.id.CreatePassword);
    }
}