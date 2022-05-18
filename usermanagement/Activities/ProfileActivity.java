package com.example.usermanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usermanagement.Helpers.DatabaseHelper;
import com.example.usermanagement.R;

public class ProfileActivity extends AppCompatActivity {
    ImageView img;
    TextView pr_fname, pr_uname, pr_phone;
    DatabaseHelper mydbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mydbh = new DatabaseHelper(this);
        Intent intent =getIntent();

        img = findViewById(R.id.profile_img);
        pr_fname = findViewById(R.id.pr_fname);
        pr_uname = findViewById(R.id.pr_username);
        pr_phone = findViewById(R.id.pr_pnum);
        String x =intent.getStringExtra("uname");
        getData(x);
    }

    public void getData(String filter){
        Cursor res = mydbh.getDataForProfile(filter);

        while(res.moveToNext()){
            pr_fname.setText(res.getString(0));
            pr_uname.setText(res.getString(1));
            pr_phone.setText(res.getString(2));

        }

    }
}
