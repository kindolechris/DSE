package com.dsetanzania.dse.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "create table "+DbContract.TABLE_NAME+
            "(id integer," +DbContract.firstname+" text," +DbContract.lastname+
            " text,"+DbContract.gender+" text," +DbContract.tradername+ " text," +DbContract.phonenumber+
            " text,"+DbContract.university+" text," +DbContract.yearOfStudy+
            " text,"+DbContract.coursename+" text," +DbContract.email+
            " text,"+DbContract.bonds+" integer," +DbContract.stock+
            " integer,"+DbContract.virtualmoney+" double,"+DbContract.role+
            " text," +DbContract.SYNC_STATUS+
            " integer);";

    private static final String DROP_TABLE = "drop table if exists "+DbContract.TABLE_NAME;

    public DbHelper(Context context){
        super(context,DbContract.DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void saveTolocalDatabase(int id,int stock, int bonds, String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename,String phonenumber, String role, double virtualmoney, String gender,int sync_status,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.firstname,firstname);
        contentValues.put(DbContract.lastname,lastname);
        contentValues.put(DbContract.tradername,tradername);
        contentValues.put(DbContract.email,email);
        contentValues.put(DbContract.yearOfStudy,yearOfStudy);
        contentValues.put(DbContract.university,university);
        contentValues.put(DbContract.coursename,coursename);
        contentValues.put(DbContract.phonenumber,phonenumber);
        contentValues.put(DbContract.role,role);
        contentValues.put(DbContract.virtualmoney,virtualmoney);
        contentValues.put(DbContract.stock,stock);
        contentValues.put(DbContract.bonds,bonds);
        contentValues.put(DbContract.gender,gender);
        contentValues.put(DbContract.SYNC_STATUS,sync_status);
        database.insert(DbContract.TABLE_NAME,null,contentValues);
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.firstname,DbContract.tradername,DbContract.bonds,DbContract.stock,DbContract.virtualmoney,DbContract.SYNC_STATUS};
        return (database.query(DbContract.TABLE_NAME,projection,null,null,null,null,null));
    }

    public  boolean updateLocakDatabase(String id,int stock, int bonds, String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename,String phonenumber, String role, double virtualmoney, String gender,int sync_status,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.firstname,firstname);
        contentValues.put(DbContract.lastname,lastname);
        contentValues.put(DbContract.tradername,tradername);
        contentValues.put(DbContract.email,email);
        contentValues.put(DbContract.yearOfStudy,yearOfStudy);
        contentValues.put(DbContract.university,university);
        contentValues.put(DbContract.coursename,coursename);
        contentValues.put(DbContract.phonenumber,phonenumber);
        contentValues.put(DbContract.role,role);
        contentValues.put(DbContract.virtualmoney,virtualmoney);
        contentValues.put(DbContract.stock,stock);
        contentValues.put(DbContract.bonds,bonds);
        contentValues.put(DbContract.gender,gender);
        contentValues.put(DbContract.SYNC_STATUS,sync_status);
        String[] selection_arg = {id};
        database.update(DbContract.TABLE_NAME,contentValues,"id=? ",selection_arg);
        return true;
    }

    public void droptable(SQLiteDatabase db){
        db.execSQL(DROP_TABLE);
    }
}
