package com.example.quizbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Results extends AppCompatActivity {

    DatabaseReference getdata;
    ArrayList<String> array_score=new ArrayList<>();
    ArrayList<String> array_name=new ArrayList<>();
    static Map<String, Integer> map = new HashMap<>();
    private RecyclerView recyclerView;
    HashMap<String, Integer> temp;
    Adapter adapter;
    ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, names);
        recyclerView.setAdapter(adapter);

        getdata= FirebaseDatabase.getInstance().getReference().child("Users");
        getdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    String id = users.child("userID").getValue().toString();
                    String name = users.child("name").getValue().toString();
                    int GK_score = Integer.parseInt(users.child("General Knowledge").getValue().toString());
                    int sport_score = Integer.parseInt(users.child("Sports").getValue().toString());
                    int cs_score = Integer.parseInt(users.child("Computer Science").getValue().toString());
                    array_score.add((GK_score + sport_score + cs_score) + "");
                    array_name.add(name);
                    map.put(name, (GK_score + sport_score + cs_score));
                    Log.e("id--------------", id + "----" + name + "---------" + (GK_score + sport_score + cs_score));
                }
               /* int temp;
                for (int i = 0; i < array_score.size(); i++) {

                    for (int j = 1; j < array_score.size(); j++) {
                        if (Integer.parseInt(array_score.get(j-1)) > Integer.parseInt(array_score.get(j))) {
                            temp = Integer.parseInt(array_score.get(j-1));
                            Integer.parseInt(array_score.get(j-1)) = Integer.parseInt(array_score.get(j));
                            Integer.parseInt(array_score.get(j)) = temp;
                        }
                    }*/
                List<Map.Entry<String, Integer> > list =new LinkedList<Map.Entry<String, Integer> >(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
                    public int compare(Map.Entry<String, Integer> o1,
                                       Map.Entry<String, Integer> o2)
                    {
                        return (o1.getValue()).compareTo(o2.getValue());
                    }
                });
                temp = new LinkedHashMap<String, Integer>();
                for (Map.Entry<String, Integer> aa : list) {
                    temp.put(aa.getKey(), aa.getValue());
                }
                for (Map.Entry<String, Integer> en : temp.entrySet()) {
                    System.out.println("Key = " + en.getKey() +
                            ", Value = " + en.getValue());
                    names.add(en.getKey());
                }
                adapter = new Adapter(Results.this, names);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            });



    }
}
