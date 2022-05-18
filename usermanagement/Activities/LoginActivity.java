package com.example.usermanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.Helpers.DatabaseHelper;
import com.example.usermanagement.R;

public class LoginActivity extends AppCompatActivity {
    EditText email, pwd;
    Button login_btn;
    SharedPreferences sp;
    TextView register_link;
    String user_name;
    ProgressBar pg;


    // Database Helper ***********//
    DatabaseHelper mydbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.lg_email);
        pwd = findViewById(R.id.lg_password);
        login_btn = findViewById(R.id.lg_btn);
        register_link = findViewById(R.id.link_to_reg);
        //intialize database helper class
        mydbh = new DatabaseHelper(this);
        //shared Preference ********
        sp = getSharedPreferences("logged_in",MODE_PRIVATE);

        if(sp.getBoolean("isLogged",false)){
            toUserPage();
        }
        pg = findViewById(R.id.progressBar2);
        pg.setVisibility(View.GONE);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(authenticate(email.getText().toString(),pwd.getText().toString())){
                            sp.edit().putBoolean("isLogged",true).apply();
                            toUserPage();
                        } else {
                            pg.setVisibility(View.GONE);
                            login_btn.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Incorrect Password or Email, try again", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                },3000);



            }
        });
        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterPage();
            }
        });

    }
    public boolean authenticate(String mail,String pwd){
        Cursor res = mydbh.getDataForLogin(mail);
        String st_email="",st_pwd="";

        if(res.getCount() ==0){
            return false;
        }
        while(res.moveToNext()){
            st_email = res.getString(0);
            st_pwd = res.getString(1);
        }


        if(st_email.equals(mail) && st_pwd.equals(pwd)){
            user_name = mail;
            return  true;
        }else {
            return false;
        }

    }

    public void toUserPage(){
        Intent toUserpage = new Intent(this, UserPage.class);
        toUserpage.putExtra("user_name",user_name);
        startActivity(toUserpage);
        finish();
    }
    public void toRegisterPage(){
        Intent toUserpage = new Intent(this, RegisterActivity.class);
        startActivity(toUserpage);
    }


}
