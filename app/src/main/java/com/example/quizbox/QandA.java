package com.example.quizbox;

import android.content.Context;
import android.icu.text.Edits;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class QandA extends Fragment {

    private String mParam1;
    private String mParam2;
    TextView tv1,score,que;
    RadioButton op1,op2,op3;
    Button nextque,submit;
    Questions obj = new Questions();
    DatabaseReference m_database;
    public ArrayList<HashMap<String,String>> questionNos = new ArrayList<>();
    String type;

    HashMap<String,String> hashMap;
    public QandA() {
        // Required empty public constructor
    }

    int time = 300;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*//type=getArguments().getString("quiztype");
        Log.e("in fragment type--------",type);
        m_database = FirebaseDatabase.getInstance().getReference().child("Quiz_QueAns").child(type);

        m_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("count--------",dataSnapshot.getChildrenCount()+"");
              //  int rand = new Random().nextInt(3);
                for (DataSnapshot quiz : dataSnapshot.getChildren()) {
                   // Log.e("quizque", quiz.child("Question").getValue().toString());
                    String que=quiz.child("Question").getValue().toString();
                    Log.e("que",que);
                  //  hashMap.put("Question",que);
                    Log.e("quizque", hashMap.get("Question"));
                  //  hashMap.put("op1",quiz.child("option_A").getValue().toString());
                   // hashMap.put("op2",quiz.child("option_B").getValue().toString());
                   // hashMap.put("op3",quiz.child("option_C").getValue().toString());
                   // hashMap.put("answer",quiz.child("Answer").getValue().toString());
                    //que.setText(quiz.child("Question").getValue().toString());

                   // questionNos.add(hashMap);
                }

                Log.e("before ","call");
               // hashMap=new HashMap<String,String>();
                 //   Log.e("itr----------","");
                    //Questions value = dataSnapshot.getValue();

                 //   obj.setQue(dataSnapshot.child(itr.toString()).child("Question").getValue().toString());
                 //   Log.e("question value---------",obj.getQue());
*/

           /* }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg = (ViewGroup)inflater.inflate(R.layout.fragment_qand, container, false);
        tv1 = vg.findViewById(R.id.id_timer);
        score=vg.findViewById(R.id.id_score);
        que=vg.findViewById(R.id.id_ques);
        op1=vg.findViewById(R.id.id_option1);
        op2=vg.findViewById(R.id.id_option2);
        op3=vg.findViewById(R.id.id_option3);
        nextque=vg.findViewById(R.id.id_nextQue);
        submit=vg.findViewById(R.id.id_submitAns);

        new CountDownTimer(30000,1000){

            @Override
            public void onTick(long l) {
                tv1.setText("0:"+checkDigit(time));
                time--;
            }

            @Override
            public void onFinish() {
                tv1.setText("try again");
            }
        }.start();

        nextque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        return vg;


    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
