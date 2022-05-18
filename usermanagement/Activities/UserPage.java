package com.example.usermanagement.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.Adapter.UserListRecyclerAdapter;
import com.example.usermanagement.Helpers.DatabaseHelper;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserPage extends AppCompatActivity {
    Button logout,profile;
    List<User> mdata;
    SharedPreferences sp;
    DatabaseHelper db;
    String usernamefromlogin;
    TextView usr;
    Uri imguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        sp = getSharedPreferences("logged_in",MODE_PRIVATE);
        Intent intent=getIntent();

        usr = findViewById(R.id.usernamefield);
        usernamefromlogin = intent.getStringExtra("user_name");
        usr.setText(usernamefromlogin);

        mdata = new ArrayList<>();
        db = new DatabaseHelper(this);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfile();
            }
        });



        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchToList();
        UserListRecyclerAdapter rec= new UserListRecyclerAdapter(mdata,this);
        recyclerView.setAdapter(rec);
    }

    public void initImages (){
        User u1 = new User("https://images.generated.photos/1M5y2b6d0SU7RJ50S9pLdzB2yudegQ2xVxivkJIaIC4/rs:fit:512:512/Z3M6Ly9nZW5lcmF0/ZWQtcGhvdG9zLzA5/OTk3ODAuanBn.jpg","Josh","josh","Anything is possible", new Date(),"0","");
        mdata.add(u1);
        User u2 = new User("https://images.generated.photos/gIQs5znc6V_PNuLzVFIxll5VP3hvDoH3Jj9AUad8dSc/rs:fit:512:512/Z3M6Ly9nZW5lcmF0/ZWQtcGhvdG9zLzA5/OTg3ODQuanBn.jpg","John","josh","Anything is possible", new Date(),"0","");
        mdata.add(u2);
        User u3 = new User("https://images.generated.photos/0dav65T4Xfw38thT0tLX2l9MOMDY5nzP1XLYui3MTlk/rs:fit:512:512/Z3M6Ly9nZW5lcmF0/ZWQtcGhvdG9zLzA5/OTA4MzkuanBn.jpg","Smith","josh","Anything is possible", new Date(),"0","");
        mdata.add(u3);
        User u4 = new User("https://images.generated.photos/ww3Ht-Tf6Zh17fJYFQEJeNWcqwUMhVlAVykzQH0Uu90/rs:fit:512:512/Z3M6Ly9nZW5lcmF0/ZWQtcGhvdG9zL3Yy/XzAwMDIzOTYuanBn.jpg","Luke","josh","Anything is possible", new Date(),"0","");
        mdata.add(u4);
        User u5 = new User("https://images.generated.photos/UxbWyb3MSxphmS78dmVqoKEWJAJn4gBHPCrHohLLoZA/rs:fit:512:512/Z3M6Ly9nZW5lcmF0/ZWQtcGhvdG9zLzA5/OTk3NDQuanBn.jpg","Emiily","josh","Anything is possible", new Date(),"0","");
        mdata.add(u5);
        User u6 = new User("https://images.generated.photos/bUropt-LW9m6qMD1dWziL3dRWdcEQbajdNXkOHOIQd8/rs:fit:512:512/Z3M6Ly9nZW5lcmF0/ZWQtcGhvdG9zLzA3/NDQ5ODAuanBn.jpg","Erick","josh","Anything is possible", new Date(),"0","");
        mdata.add(u6);

        initRecycler();

    }
    public void initRecycler() {


    }
    public void logout(){
        Intent intent = new Intent(this,LoginActivity.class);
        sp.edit().putBoolean("isLogged",false).apply();
        startActivity(intent);
        finish();
    }
    public void toProfile(){
        Intent intent= new Intent(this, ProfileActivity.class);
        intent.putExtra("uname",usernamefromlogin);
        startActivity(intent);


    }
    public void fetchToList(){

        Cursor res= db.getDataForRecycler();
        if(res.getCount() ==0){
            initImages();
            return;
        }
        User us;
        while(res.moveToNext()){
            String img,fname,uname,bio;
            String views;
            Date dt;

            img = res.getString(0);
            fname = res.getString(1);
            uname = res.getString(2);
            bio = res.getString(3);
            views = res.getString(4);

            us = new User(img,fname,uname,bio,new Date(),views,"");
            mdata.add(us);
        }
        initImages();
    }
}
