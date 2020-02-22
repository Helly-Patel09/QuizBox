package com.example.quizbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdminPage extends AppCompatActivity {

    //global variable declaration
    Button btn1;
    TextView user_no,GK_no,CS_no,Sports_no;
    DatabaseReference user_data,category_data;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Toolbar home_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(home_toolbar);

        //resource IDs allocation to layout
        user_no=findViewById(R.id.no_users);
        GK_no=findViewById(R.id.no_GK);
        CS_no=findViewById(R.id.no_CS);
        Sports_no=findViewById(R.id.no_Sports);

        //get number of users count to display in admin page
        user_data= FirebaseDatabase.getInstance().getReference().child("Users");
        user_data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("children----------",dataSnapshot.getChildrenCount()+"");
                user_no.setText("Number of Users : "+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //get question count for each quiz category
        category_data=FirebaseDatabase.getInstance().getReference().child("Quiz_QueAns");
        category_data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GK_no.setText("Total no of Questions in General Knowledge : "+dataSnapshot.child("General Knowledge").getChildrenCount());
                CS_no.setText("Total no of Questions in Computer Science : "+dataSnapshot.child("Computer Science").getChildrenCount());
                Sports_no.setText("Total no of Questions in Sports : "+dataSnapshot.child("Sports").getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Add question access to admin
        btn1 = findViewById(R.id.id_addQues);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1 = new Intent(AdminPage.this,Add_question.class);
                startActivity(int1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_page, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //admin signout
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(AdminPage.this,MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}