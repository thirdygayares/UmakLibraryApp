package com.firetera.umaklibraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    Button login;
    EditText inputEmail, inputPassword;
    TextView newAccount,errorLogin;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        initxml();
        newAccountMethod();
        login();

    }

    private void login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           ;
                            errorLogin.setVisibility(View.VISIBLE);
                            errorLogin.setText(e.getMessage());
                        }
                    });

                } else {

                    errorLogin.setVisibility(View.VISIBLE);
                    errorLogin.setText("All Fields are Required");
//                    alertDialog.dismiss();
//                    Toast.makeText(Login.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
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

    private void initxml() {
        errorLogin = (TextView) findViewById(R.id.errorLogin);
        login = (Button) findViewById(R.id.login);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        newAccount = (TextView) findViewById(R.id.newAccount);

    }
}