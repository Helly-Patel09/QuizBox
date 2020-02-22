package com.example.quizbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_profile extends AppCompatActivity {

    //global variable declaration
    TextView edit_name,edit_email;
    EditText edit_phone,edit_pass;
    Button edit_profile;
    String useremail,userID;
    DatabaseReference m_getdata;
    String name,email,pass,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //declaring IDs to resource layout
        edit_name=findViewById(R.id.edit_Name);
        edit_email=findViewById(R.id.edit_email);
        edit_pass=findViewById(R.id.edit_pass);
        edit_phone=findViewById(R.id.edit_phone);
        edit_profile=findViewById(R.id.edit_btn);


        //useremail and userID for current user
        useremail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userID=FirebaseAuth.getInstance().getCurrentUser().getUid();

        //get data from table for current user
        m_getdata = FirebaseDatabase.getInstance().getReference().child("Users");
        m_getdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot users : dataSnapshot.getChildren()) {

                    String user = (String) users.child("email").getValue();
                    if (user.equals(useremail)) {
                            name=users.child("name").getValue().toString();
                            email=users.child("email").getValue().toString();
                            pass=users.child("pass").getValue().toString();
                            phone=users.child("contact").getValue().toString();
                    }
                }
                //displaying value into text field
                edit_name.setText(name);
                edit_email.setText(email);
                edit_pass.setText(pass);
                edit_phone.setText(phone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        //onCLick event for edit profile button
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validation for any change in password and phone number
                if(edit_pass.getText().toString().length()<=7){
                    Toast.makeText(getApplicationContext(), "New password can't be smaller than 7 characters", Toast.LENGTH_SHORT).show();
                    edit_pass.setError("New Password too small");
                }
                else if (edit_phone.getText().toString().length() != 10){
                    Toast.makeText(getApplicationContext(), "Phone number can't be smaller than 10 characters", Toast.LENGTH_SHORT).show();
                    edit_phone.setError("New phone number too small");
                }
                else {
                    //updating entered values into database for current user
                    m_getdata = FirebaseDatabase.getInstance().getReference().child("Users");
                    m_getdata.child(userID).child("pass").setValue(edit_pass.getText().toString());
                    m_getdata.child(userID).child("contact").setValue(edit_phone.getText().toString());

                    //intent to home page after edit
                    Intent i = new Intent(Edit_profile.this, Home.class);
                    startActivity(i);
                }
            }
        });


    }
}
