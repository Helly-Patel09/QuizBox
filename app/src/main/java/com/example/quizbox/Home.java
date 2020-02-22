package com.example.quizbox;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //global variable declaration
    UserID uid = new UserID();
    private NavigationView navigationView;
    String name = uid.getUsername();
    FrameLayout fra;
    Bundle bundle=new Bundle();
    TextView nm,email;
    ImageButton ib1,ib2,ib3;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //defining varilable to ID resources
        Toolbar toolbar = findViewById(R.id.id_toolbarQandA);
        setSupportActionBar(toolbar);
        toolbar.setTitle("QuizBox..!!");
        fra=findViewById(R.id.frame2);

        Log.d("Home class","running");

        //navigation window layout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        nm=hView.findViewById(R.id.nav_bar_name);
        email=hView.findViewById(R.id.nav_bar_email);

        //Navigation window welcome message to entered user(email)
        nm.setText("Welcome ");
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

       //imagebutton onClick opens quiz for GK
        ib1 = findViewById(R.id.imageButton2);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Home.this,quesAndAns.class);
                intent.putExtra("quiztype","General Knowledge");
                startActivity(intent);
            }
        });

        //imagebutton onClick opens quiz for Sports
        ib2 = findViewById(R.id.imageButton3);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Home.this,quesAndAns.class);
                intent.putExtra("quiztype","Sports");
                startActivity(intent);
            }
        });

        //imagebutton onClick opens quiz for CS
        ib3 = findViewById(R.id.imageButton4);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Home.this,quesAndAns.class);
                intent.putExtra("quiztype","Computer Science");
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //logout event
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(Home.this,MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //onClick to navigation window features intent to respective page
        if (id == R.id.nav_Edit_profile) {
            Intent i = new Intent(Home.this, Edit_profile.class);
            startActivity(i);

        } else if (id == R.id.nav_results) {
            Intent i = new Intent(Home.this, Results.class);
            startActivity(i);

        } else if (id == R.id.nav_rankings) {
            Intent i = new Intent(Home.this, Rankings.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
