package com.example.quizbox;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class quesAndAns extends AppCompatActivity {

    //global variable declaration
    String type;
    Bundle bundle=new Bundle();
    TextView tv1,score,que;
    RadioButton op1,op2,op3;
    Button nextque,submit;
    int time = 300;
    final ArrayList<Questions> list = new ArrayList<>();
    DatabaseReference m_database;
    String currrentanswer;
    int totalScore = 0,questio_count=1;
    String username=FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private DatabaseReference m_writeData;
    int quizno = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_and_ans);

        //assigning resources IDs to layout
        Toolbar qanda_toolbar = findViewById(R.id.id_toolbarQandA);
        setSupportActionBar(qanda_toolbar);
        tv1 = findViewById(R.id.id_timer);
        score=findViewById(R.id.id_score_1);
        que=findViewById(R.id.id_ques);
        op1=findViewById(R.id.id_rb1);
        op2=findViewById(R.id.id_rb2);
        op3=findViewById(R.id.id_rb3);
        nextque=findViewById(R.id.id_nextQue_gen);
        submit=findViewById(R.id.id_submitAns_gen);


        setSupportActionBar(qanda_toolbar);
       // qanda_toolbar.setTitle("QuizBox: All the Best...!!");

        //get type on intent for quizType
        type=getIntent().getStringExtra("quiztype");

        Log.e("type------",type);

        m_database = FirebaseDatabase.getInstance().getReference().child("Quiz_QueAns").child(type);
        m_writeData = FirebaseDatabase.getInstance().getReference().child("Users");

        Log.e("before ","call");

        //getting dataset for all questions and stroing it in arraylist
        m_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("count-----",dataSnapshot.getChildrenCount()+"");
                for(DataSnapshot quiz : dataSnapshot.getChildren())
                {
                    String que = (String) quiz.child("Question").getValue();
                    String op1 = (String) quiz.child("option_A").getValue();
                    String op2 = (String) quiz.child("option_B").getValue();
                    String op3 = (String) quiz.child("option_C").getValue();
                    String ans = (String) quiz.child("Answer").getValue();

                    Questions obj1 = new Questions("1",que,op1,op2,op3,ans);
                           list.add(obj1);
                   Log.e("que-----",que);
                }
                //calling setQuestion
                setQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Timer for quiz
        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long l) {
                tv1.setText(l/1000+"");
                time--;
            }

            @Override
            public void onFinish() {
                //if timer ends, auto-submit method called
                submit.callOnClick();
            }
        }.start();

        //next question called on NEXT button click event
        nextque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("abc","clicked "+ currrentanswer + op2.isChecked());

                //condition to check correct answer from firebase and update score
                if(op1.isChecked() && currrentanswer.equals("A"))
                {
                    totalScore += 1;
                    op1.setChecked(false);

                }
                else if(op2.isChecked() && currrentanswer.equals("B"))
                {
                    totalScore += 1;
                    op2.setChecked(false);


                }
                else if(op3.isChecked() && currrentanswer.equals("C"))
                {
                    totalScore += 1;
                    op3.setChecked(false);


                }

                //question limit set to 10 per quiz
                if(questio_count>=10)
                {
                    submit.callOnClick();
                }

                questio_count++;
                setQuestion();
            }
        });

        //onClick event for submit quiz
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_writeData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot users : dataSnapshot.getChildren())
                        {
                            //update current quiz score in database in USer table
                            String user = (String) users.child("email").getValue();
                            if(user.equals(username))
                            {
                                String userID = (String) users.child("userID").getValue();
                                Log.e("user-----------",username);
                                m_writeData.child(userID).child(type).setValue(totalScore+"");
                                quizno++;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //intent back to home page
                Intent i = new Intent(quesAndAns.this,Home.class);
                startActivity(i);
            }
        });
    }


    public void setQuestion()
    {
        //random select any question object from arraylist
        Random r = new Random();
        int x = r.nextInt(5);
        Questions questions = list.get(x);
        //setters for setting text
        que.setText(questions.getQue());
        op1.setText(questions.getOp1());
        op2.setText(questions.getOp2());
        op3.setText(questions.getOp3());
        currrentanswer = questions.getAns();
        Log.e("current ans-------",currrentanswer);
        //removing entry from list once displayed....not to show redundant question in one quiz
        list.remove(questions);
        score.setText("Score : "+totalScore+"");
       }

}
