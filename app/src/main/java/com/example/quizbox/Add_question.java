package com.example.quizbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_question extends AppCompatActivity {
    //global variable declaration
    Button submit_entry;
    EditText que,opA,opB,opC,Ans;
    Spinner sp;
    String quiz_type;

    private FirebaseAuth mAuth;
    private FirebaseDatabase fdb;
    private DatabaseReference myref;
    private FirebaseUser user;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);


        //declaring IDs to layout
        que=findViewById(R.id.id_enterQues);
        opA=findViewById(R.id.id_option1);
        opB=findViewById(R.id.id_option2);
        opC=findViewById(R.id.id_option3);
        Ans=findViewById(R.id.id_correctAns);
        submit_entry = findViewById(R.id.id_submitEntry);
        sp=findViewById(R.id.id_spinner1);

        //spinner to select to which TYPE category the admin wants to add a new question
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quiz_type= (String) sp.getSelectedItem();
                Log.e("quiz type-----------",quiz_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //submit question entered
        submit_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Question added to dataset",Toast.LENGTH_SHORT).show();

                mAuth = FirebaseAuth.getInstance();
                fdb=FirebaseDatabase.getInstance();
                myref=fdb.getReference();
                String userID = myref.push().getKey();

                //setting all fields into question table
                myref.child("Quiz_QueAns").child(quiz_type).child(userID).child("Question").setValue(que.getText().toString());
                myref.child("Quiz_QueAns").child(quiz_type).child(userID).child("option_A").setValue(opA.getText().toString());
                myref.child("Quiz_QueAns").child(quiz_type).child(userID).child("option_B").setValue(opB.getText().toString());
                myref.child("Quiz_QueAns").child(quiz_type).child(userID).child("option_C").setValue(opC.getText().toString());
                myref.child("Quiz_QueAns").child(quiz_type).child(userID).child("Answer").setValue(Ans.getText().toString());

               Log.d("success", "Question Added to Dataset ");

                //intent to the admin page
                Intent in = new Intent(Add_question.this,AdminPage.class);
                startActivity(in);
            }
        });

    }
}
