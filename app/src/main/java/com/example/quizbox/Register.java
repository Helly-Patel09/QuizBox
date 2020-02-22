package com.example.quizbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    //global variable declarations
    private static final String TAG = "Register User";
    EditText m_username,m_email,m_psw,m_phone,m_confpass;
    ImageButton m_reg;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fdb;
    private DatabaseReference myref;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String userId;
    String email_pattern = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //assigning resource layout IDS to variables
        m_username = findViewById(R.id.txt_username);
        m_email = findViewById(R.id.txt_email);
        m_psw = findViewById(R.id.txt_psw);
        m_reg = findViewById(R.id.btn_register);
        m_phone = findViewById(R.id.txt_phone);
        m_confpass = findViewById(R.id.id_confirmPass);

        //onClick event on register now Textview
        mAuth = FirebaseAuth.getInstance();
        m_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validations before successfull new user registration
                if (m_username.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter all fields to register", Toast.LENGTH_SHORT).show();
                    m_username.setError("Username can't be empty");
                }
                else if(m_email.getText().toString().length() == 0){
                    m_email.setError("Email address can't be empty");
                }
                else if (!m_email.getText().toString().matches(email_pattern)) {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    m_email.setError("email address invalid");
                }
                else if(m_psw.getText().toString().length() == 0){
                    m_psw.setError("Password can't be empty");
                }
                else if (m_psw.getText().toString().length() <=7){
                    Toast.makeText(getApplicationContext(), "Password can't be smaller than 7 characters", Toast.LENGTH_SHORT).show();
                    m_psw.setError("Password too small");
                }
                else if (m_confpass.getText().toString().length() == 0){
                    m_confpass.setError("Retype password can't be empty");
                }
                else if (!m_psw.getText().toString().equals(m_confpass.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                    m_confpass.setError("Passwords doesn't match");
                }
                else if ( m_phone.getText().toString().length() == 0){
                    m_phone.setError("Phone number can't be empty");
                }

                else if (m_phone.getText().toString().length()!=10){
                    Toast.makeText(getApplicationContext(), "Phone number is less than 10 character", Toast.LENGTH_SHORT).show();
                    m_phone.setError("Phone number too small");
                }
                else {
                    //on successful registration
                    mAuth.createUserWithEmailAndPassword(m_email.getText().toString(), m_psw.getText().toString())
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {                                    //creating and authenticating new user registration entry into Firebase
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        fdb = FirebaseDatabase.getInstance();
                                        myref = fdb.getReference();
                                        userId = user.getUid();
                                        Toast.makeText(getBaseContext(), "Userid: " + userId, Toast.LENGTH_LONG).show();

                                        //add user details into firebase database
                                        myref.child("Users").child(userId).child("name").setValue(m_username.getText().toString());
                                        myref.child("Users").child(userId).child("userID").setValue(userId);
                                        myref.child("Users").child(userId).child("email").setValue(m_email.getText().toString());
                                        myref.child("Users").child(userId).child("pass").setValue(m_psw.getText().toString());
                                        myref.child("Users").child(userId).child("contact").setValue(m_phone.getText().toString());
                                        myref.child("Users").child(userId).child("General Knowledge").setValue("0");
                                        myref.child("Users").child(userId).child("Sports").setValue("0");
                                        myref.child("Users").child(userId).child("Computer Science").setValue("0");

                                        Toast.makeText(getBaseContext(), m_username.getText().toString() + " added", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "signin:success " + user.getUid());

                                        //on successful entry, intent to Home page
                                        Intent intent = new Intent(Register.this, Home.class);
                                        startActivity(intent);

                                        Log.d(TAG, "Created " + user.getUid());
                                        Toast.makeText(getBaseContext(), "Created", Toast.LENGTH_LONG).show();
                                    } else {
                                        //error, user registration not done
                                        Log.w(TAG, "Not created", task.getException());
                                        Toast.makeText(getBaseContext(), "Not created", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                }
            }
        });

    }

}
