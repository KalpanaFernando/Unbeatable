package com.example.usermanagement.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME= "user_info";
    public static final String imgUri = "imgUri";
    public static final String fname= "full_name";
    public static final String usrname= "user_name";
    public static final String phone= "phone";
    public static final String email= "email";
    public static final String password = "password";
    public static final String sex = "sex";
    public static final String date = "Date";
    public static final String views = "views";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_NAME +
                " ( "
                    + imgUri + " varchar(50) ,"
                    + fname + " varchar(50) ,"
                    + usrname + " varchar(50) ,"
                    + phone + " varchar(50) ,"
                    + email + " varchar(50) ,"
                    + password + " varchar(50) ,"
                    + sex + " varchar(50) ,"
                    + date + " varchar(50) ,"
                    + views + " varchar(50) "
                    + " ) ";
        db.execSQL(createUserTable);
    }


    //called when the database needs to upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }
    }

    public boolean insert(String imgPath, String fullname, String username, String mail, String ph_num, String pwd, String gender, String joinedDate, String views ){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("imgUri",imgPath);
        contentValues.put("full_name",fullname);
        contentValues.put("user_name",username);
        contentValues.put("email",mail);
        contentValues.put("phone",ph_num);
        contentValues.put("password",pwd);
        contentValues.put("sex",gender);
        contentValues.put("date",joinedDate);
        contentValues.put("views",views);


        long result= db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db= getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
    public Cursor getDataForRecycler(){
        SQLiteDatabase db = getWritableDatabase();
        String filter = imgUri + " , " +  fname + " , " + usrname + " , " + email + " , " +views;

        Cursor res = db.rawQuery("SELECT "+ filter + " From "+ TABLE_NAME,null);

        return res;
    }
    public Cursor getDataForLogin(String mail){
        SQLiteDatabase db = getWritableDatabase();
        String filter = email + " , " + password;
        String condition = "'"+ mail +"'" + " = " + email;
        Cursor res = db.rawQuery("SELECT "+ filter + " FROM " + TABLE_NAME + " WHERE " + condition, null );
        return res;
    }
    public Cursor getDataForDetails(String filter){
        SQLiteDatabase db = getWritableDatabase();
        String filterer = fname + " , " +usrname + " , "+ email + " , " + phone + " , " +  date + " , " + views;
        String condition = "'"+ filter +"'" + " = " + usrname;
        Cursor res = db.rawQuery("SELECT "+ filterer + " FROM " + TABLE_NAME + " WHERE " + condition, null );
        return res;
    }
    public Cursor getDataForProfile(String filter){
        SQLiteDatabase db = getWritableDatabase();
        String filterer = fname + " , " +usrname + " , " + phone;
        String condition = "'"+ filter +"'" + " = " + usrname;
        Cursor res = db.rawQuery("SELECT "+ filterer + " FROM " + TABLE_NAME + " WHERE " + condition, null );

        return res;

    }

    public void remove(String email){
        SQLiteDatabase db = getWritableDatabase();
        String condition = "'" + email + "'"+ " = "+ this.email;
        db.rawQuery("DELETE FROM "+TABLE_NAME+" WHERE "+ condition,null);

    }
}
