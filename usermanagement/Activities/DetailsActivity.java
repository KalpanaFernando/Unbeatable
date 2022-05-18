package com.example.usermanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.Helpers.DatabaseHelper;
import com.example.usermanagement.R;

public class DetailsActivity extends AppCompatActivity {
    String user_name;
    DatabaseHelper mydbh;
    ImageView cover_img,detail_image;
    TextView details_fname,details_uname,details_email,details_phnum,details_date,details_views;
    Animation anim1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mydbh = new DatabaseHelper(this);
        cover_img = findViewById(R.id.details_cimg);
        detail_image = findViewById(R.id.detail_image);
        details_fname = findViewById(R.id.details_fname);
        details_uname = findViewById(R.id.details_uname);
        details_email = findViewById(R.id.details_email);
        details_phnum = findViewById(R.id.details_phnum);
        details_date = findViewById(R.id.details_date);
        details_views = findViewById(R.id.details_views);
        Intent intent= getIntent();
        user_name = intent.getStringExtra("username");
        bringDataFromDB(user_name);
        cover_img.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));
        detail_image.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));
        details_fname.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));
        details_uname.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));
        details_email.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));
        details_phnum.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));
        details_date.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));
        details_views.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_to_down));




    }

    public void bringDataFromDB(String filter){

            Cursor res = mydbh.getDataForDetails(filter);

            while(res.moveToNext()){
                details_fname.setText(res.getString(0));
                details_uname.setText(res.getString(1));
                details_email.setText(res.getString(2));
                details_phnum.setText(res.getString(3));
                details_date.setText(res.getString(4));
                details_views.setText(res.getString(5));
            }


    }
}
