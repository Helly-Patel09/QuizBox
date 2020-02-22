package com.example.quizbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //global variable declaration
    EditText m_useremail, m_userpsw;
    Button btn_login;
    TextView btn_reg;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fdb;
    private DatabaseReference myref;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //assigning resources IDs to layout
        m_useremail = findViewById(R.id.txt_useremail);
            m_userpsw = findViewById(R.id.txt_userpsw);
            btn_login = findViewById(R.id.btn_login);
            btn_reg = findViewById(R.id.btn_register);

            //onClick event for Login button
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Login validations
                 if (!m_useremail.getText().toString().trim().matches(getString(R.string.email_regx)) || m_useremail.getText() == null) {
                        Toast.makeText(MainActivity.this, "Enter valid Email Id!", Toast.LENGTH_LONG).show();
                    }
                    else if (m_userpsw.getText().toString().length() < 6) {
                        Toast.makeText(MainActivity.this, "Password too small! Must be 6 letters long", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //successfull login attempt, user check from firebase
                        mAuth = FirebaseAuth.getInstance();
                        Log.e("email,psw: ", m_useremail.getText().toString() + m_userpsw.getText().toString());
                        mAuth.signInWithEmailAndPassword(m_useremail.getText().toString().trim(), m_userpsw.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = mAuth.getCurrentUser();

                                    Toast.makeText(MainActivity.this, "Authentication Successful!", Toast.LENGTH_LONG).show();
                                    fdb = FirebaseDatabase.getInstance();
                                    myref = fdb.getReference();
                                }
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String userID = currentUser.getUid();
                                Intent intent1;

                                //if admin login, intent to Admin page
                                if(userID.equals("G3KaGaszezYooDSEiX7ZWJglFMC3"))
                                    intent1 = new Intent(MainActivity.this, AdminPage.class);
                                else
                                    //if user login, intent to user homepage
                                    intent1 = new Intent(MainActivity.this, Home.class);
                                startActivity(intent1);
                                finish();
                            }
                        });
                    }
                }

            });

            //if user not register, intent to register page
            btn_reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, Register.class);
                    startActivity(i);
                }
            });
    }
    @Override
    public void onStart() {
        super.onStart();

        //if user session already logged in, start from home page, until logged out
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }
    }
}

