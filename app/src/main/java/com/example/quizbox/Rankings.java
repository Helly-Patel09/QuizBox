package com.example.quizbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Rankings extends AppCompatActivity {

    //global variable declaration
    String useremail;
    DatabaseReference m_getdata;
    List<SliceValue> pieData = new ArrayList<>();
    int CS_score,GK_score,Sports_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        //piechart ID declaration
        final PieChartView pieChartView = findViewById(R.id.chart);

        useremail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        m_getdata = FirebaseDatabase.getInstance().getReference().child("Users");

        //set value for current user in pie chart
        m_getdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot users : dataSnapshot.getChildren()) {

                    String user = (String) users.child("email").getValue();
                    if(user.equals(useremail))
                    {
                        //getting scores from user table
                        CS_score = Integer.parseInt((String) users.child("Computer Science").getValue());
//                        GK_score = (String) users.child("General Knowledge").getValue();
                        GK_score = Integer.parseInt((String) users.child("General Knowledge").getValue());
//                        Sports_score = (String) users.child("Sports").getValue();
                        Sports_score = Integer.parseInt((String) users.child("Sports").getValue());
                    }

                }
                   Log.e("points-------",GK_score+CS_score+Sports_score+"");

                //set values in pie chart as per category with color and score values
                int total =CS_score+GK_score+Sports_score;
                if(CS_score==0 && GK_score==0 && Sports_score==0)
                    pieData.add(new SliceValue(100, Color.RED).setLabel("Your all results are zero"));
                else if(CS_score==0 && GK_score==0)
                    pieData.add(new SliceValue(100, Color.MAGENTA).setLabel("Sports Score : "+Sports_score));
                else if(CS_score==0 && Sports_score==0)
                    pieData.add(new SliceValue(100, Color.RED).setLabel("General Knowledge Score : "+GK_score));
                else if(GK_score==0 && Sports_score==0)
                    pieData.add(new SliceValue(100, Color.GRAY).setLabel("Computer Science Score : "+CS_score));
                else if(CS_score==0)
                {
                    pieData.add(new SliceValue(GK_score*100/total, Color.RED).setLabel("General Knowledge Score : "+GK_score));
                    pieData.add(new SliceValue(Sports_score*100/total, Color.MAGENTA).setLabel("Sports Score : "+Sports_score));
                }
                else if(GK_score==0)
                {
                    pieData.add(new SliceValue(CS_score*100/total, Color.GRAY).setLabel("Computer Science Score : "+CS_score));
                    pieData.add(new SliceValue(Sports_score*100/total, Color.MAGENTA).setLabel("Sports Score : "+Sports_score));
                }
                else if(Sports_score==0)
                {
                    pieData.add(new SliceValue(CS_score*100/total, Color.GRAY).setLabel("Computer Science Score : "+CS_score));
                    pieData.add(new SliceValue(GK_score*100/total, Color.RED).setLabel("General Knowledge Score : "+GK_score));
                }
                else {
                    pieData.add(new SliceValue(CS_score * 100 / total, Color.GRAY).setLabel("Computer Science Score : "+CS_score));
                    pieData.add(new SliceValue(GK_score * 100 / total, Color.RED).setLabel("General Knowledge Score : "+GK_score));
                    pieData.add(new SliceValue(Sports_score * 100 / total, Color.MAGENTA).setLabel("Sports Score : "+Sports_score));
                }
                //set into piechart view
                PieChartData pieChartData = new PieChartData(pieData);
                pieChartData.setHasLabels(true);
                pieChartView.setPieChartData(pieChartData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
