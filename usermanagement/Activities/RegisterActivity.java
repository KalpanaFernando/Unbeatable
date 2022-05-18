package com.example.usermanagement.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.Helpers.DatabaseHelper;
import com.example.usermanagement.R;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    SQLiteDatabase mydb ;
    Cursor rs;
    // from activity class

    ImageView img;
    EditText full_name;
    EditText user_name;
    EditText email;
    EditText phone;
    EditText pwd;
    EditText first_pwd;
    Button btn;
    TextView toLogin;
    String user;

    // end of activity imports

    String imgUri;
    static int PReqCode = 1;
    static int REQUESCODE= 1;
    Uri selectedimg;
    DatabaseHelper mydbh;
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind activity imports with their respective elements
        img = findViewById(R.id.reg_user_img);
        full_name = findViewById(R.id.reg_fname);
        user_name = findViewById(R.id.regName);
        email = findViewById(R.id.regEmail);
        phone = findViewById(R.id.reg_phone);
        first_pwd = findViewById(R.id.regPwd);
        pwd = findViewById(R.id.regPwdCnfrm);
        btn = findViewById(R.id.regBtn);
        toLogin = findViewById(R.id.link_to_login);
        pg = findViewById(R.id.progressBar);
        pg.setVisibility(View.GONE);

        // ********************************************************** end of Binding//


        //create database with helper class, creates a db user.db
        mydbh = new DatabaseHelper(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pg.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String st_username=user_name.getText().toString();
                        String st_fullname= full_name.getText().toString();
                        String st_email=email.getText().toString();
                        String st_phone= phone.getText().toString();
                        String st_password=pwd.getText().toString();
                        String gender = "M";
                        user= st_username;
                        int date= new Date().getMonth();
                        String st_date = date + "";
                        // validate User input
                        if(validate()){

                            pushToDatabase("",st_fullname,st_username,st_email,st_phone,st_password,gender,st_date,"65");
                            sendMessage();
                        } else {
                            pg.setVisibility(View.GONE);
                            btn.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this, "Please fill the form and also make sure you entered matching passwords", Toast.LENGTH_LONG).show();

                            return;
                        }
                    }
                },3000);



            }
        });


        // image view on click lsitener to change profile photo
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT > 22){
                    checkAndRequestForPermission();
                }else {
                    openGallery();
                }
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginPage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            //successful image Upload
            // lets save the image reference on Uri
            selectedimg= data.getData();
            img.setImageURI(selectedimg);
        }
    }
    // user defined functions
    private void openGallery() {
        //TODO: open gallery and wait for user to pick an image
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivity.this,"please accept for required permission",Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else {
            openGallery();
        }
    }



    public void sendMessage(){
        Intent intent= new Intent(this,UserPage.class);
        intent.putExtra("user_name",user);

        startActivity(intent);
        finish();
    }
    public void toLoginPage(){
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void pushToDatabase(String imgPath, String fullname, String username, String mail, String ph_num, String pwd, String gender, String joinedDate, String views ){

        boolean result = mydbh.insert(imgPath,fullname,username,mail,ph_num,pwd,gender,joinedDate,views);
        if(result){
            Toast.makeText(this, "Data successfully inserted to sqlite database", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Data insertion Failed", Toast.LENGTH_LONG).show();
        }
    }
    public boolean validate(){
        if(full_name.getText() == null && user_name.getText() == null && email.getText() == null
                && phone.getText()== null && first_pwd.getText() == null && pwd.getText() == null
                &&(first_pwd.getText() == pwd.getText())){
            return false;
        } else if(full_name == null && user_name == null && email == null
                && phone == null && first_pwd == null && pwd == null
                &&(first_pwd != pwd) ){
            return false;
        } else if(full_name.getText().toString().matches("") && user_name.getText().toString().matches("") && email.getText().toString().matches("")
                && phone.getText().toString().matches("") && first_pwd.getText().toString().matches("") && pwd.getText().toString().matches("")
                && (first_pwd.getText().toString().matches(pwd.getText().toString()))){
            return false;
        }
        else {
            String p1= first_pwd.getText().toString();
            String p2 = pwd.getText().toString();
            if(p1.equals(p2)){
                return true;
            }else {
                return false;
            }

        }
    }
}
