package com.dsetanzania.dse.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_USER = "create table "+DbContract.USER_TABLE +
            "(id integer," +DbContract.firstname+" text," +DbContract.lastname+
            " text,"+DbContract.gender+" text," +DbContract.tradername+ " text," +DbContract.phonenumber+
            " text,"+DbContract.university+" text," +DbContract.yearOfStudy+
            " text,"+DbContract.coursename+" text," +DbContract.email+
            " text,"+DbContract.bonds+" integer," +DbContract.stock+
            " integer,"+DbContract.virtualmoney+" double,"+DbContract.role+
            " text," +DbContract.SYNC_STATUS+
            " integer);";

    private static final String CREATE_TABLE_SHARE_TRANSACTION = "create table "+DbContract.SHARE_TRANSACTION_TABLE +
            "(id integer," +DbContract.sharesAmout+" text," +DbContract.sharetransactiondate +
            " text,"+DbContract.boardShareName+" text," +DbContract.sharePrice+ " text," +DbContract.transactiontype+
            " text,"+DbContract.transactionstatus+" text)";

    private static final String CREATE_TABLE_BOND_TRANSACTION = "create table "+DbContract.BOND_TRANSACTION_TABLE +
            "(id integer," +DbContract.bondnumber+" text,"+DbContract.bondunit +" text,"+DbContract.bondstatus +" text," +DbContract.bondtransactiondate+
            " text)";


    private static final String CREATE_TABLE_BOND_HOLDINGS = "create table "+DbContract.BOND_HOLDINGS_TABLE +
            "(id integer," +DbContract.boarbonddname+" text," +DbContract.boardbondid +
            " text,"+DbContract.bonduserid+" text," +DbContract.bondamount+ " text," +DbContract.bonddatecreated +
            " text)";


    private static final String CREATE_TABLE_SHARE_HOLDINGS = "create table "+DbContract.SHARE_HOLDINGS_TABLE +
            "(id integer," +DbContract.boarsharename+" text," +DbContract.boardshareid +
            " text,"+DbContract.shareuserid+" text," +DbContract.shareamount+ " text," +DbContract.sharedatecreated+
            " text)";


    private static final String DROP_TABLE_USER = "drop table if exists "+DbContract.USER_TABLE;
    private static final String DROP_TABLE_SHARE_TRANSACTION = "drop table if exists "+DbContract.SHARE_TRANSACTION_TABLE;
    private static final String DROP_TABLE_BOND_TRANSACTION = "drop table if exists "+DbContract.BOND_TRANSACTION_TABLE;
    private static final String DROP_TABLE_BOND_HOLDING = "drop table if exists "+DbContract.BOND_HOLDINGS_TABLE;
    private static final String DROP_TABLE_SHARE_HOLDING = "drop table if exists "+DbContract.SHARE_HOLDINGS_TABLE;

    public DbHelper(Context context){
        super(context,DbContract.DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE_USER);
       db.execSQL(CREATE_TABLE_SHARE_TRANSACTION);
       db.execSQL(CREATE_TABLE_BOND_TRANSACTION);
       db.execSQL(CREATE_TABLE_BOND_HOLDINGS);
       db.execSQL(CREATE_TABLE_SHARE_HOLDINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_SHARE_TRANSACTION);
        db.execSQL(DROP_TABLE_BOND_TRANSACTION);
        db.execSQL(DROP_TABLE_BOND_HOLDING);
        db.execSQL(DROP_TABLE_SHARE_HOLDING);
        onCreate(db);
    }


    //////////////////////////////////////////////////////////////////
    public void saveUserTolocalDatabase(int id, int stock, int bonds, String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename, String phonenumber, String role, double virtualmoney, String gender, int sync_status, SQLiteDatabase database){
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
        database.insert(DbContract.USER_TABLE,null,contentValues);
    }

    public Cursor readUserFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.firstname,DbContract.lastname,DbContract.tradername,DbContract.bonds,DbContract.stock,DbContract.virtualmoney,DbContract.university,DbContract.SYNC_STATUS};
        return (database.query(DbContract.USER_TABLE,projection,null,null,null,null,null));
    }

    public boolean updateUserLocalDatabase(String id, int stock, int bonds, String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename, String phonenumber, String role, double virtualmoney, String gender, int sync_status, SQLiteDatabase database){
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
        database.update(DbContract.USER_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }


    ////////////////////////////////////////////////////////////////////
    public boolean saveShareTransactionTolocalDatabase(int id, String sharesAmount, String transactiondate, String boardShareName, Integer price, String transactiontype, String transactionstatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.sharesAmout,sharesAmount);
        contentValues.put(DbContract.sharetransactiondate,transactiondate);
        contentValues.put(DbContract.boardShareName,boardShareName);
        contentValues.put(DbContract.sharePrice,price);
        contentValues.put(DbContract.transactiontype,transactiontype);
        contentValues.put(DbContract.transactionstatus,transactionstatus);
        database.insert(DbContract.SHARE_TRANSACTION_TABLE,null,contentValues);
        return true;
    }

    public boolean updateShareTransactionTolocalDatabase(String id, String sharesAmount, String transactiondate, String boardShareName, String price, String transactiontype, String transactionstatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.sharesAmout,sharesAmount);
        contentValues.put(DbContract.sharetransactiondate,transactiondate);
        contentValues.put(DbContract.boardShareName,boardShareName);
        contentValues.put(DbContract.sharePrice,price);
        contentValues.put(DbContract.transactiontype,transactiontype);
        contentValues.put(DbContract.transactionstatus,transactionstatus);
        String[] selection_arg = {id};
        database.update(DbContract.SHARE_TRANSACTION_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }

    public Cursor readShareTransactionFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.sharesAmout,DbContract.sharetransactiondate,DbContract.boardShareName,DbContract.sharePrice,DbContract.transactiontype,DbContract.transactionstatus};
        return (database.query(DbContract.SHARE_TRANSACTION_TABLE,projection,null,null,null,null,null));
    }


    //////////////////////////////////////////////////////////////////
    public boolean saveBondTransactionTolocalDatabase(int id,String bondnumber,String status, String bondunit,String bondtransactiondate,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.bondnumber,bondnumber);
        contentValues.put(DbContract.bondunit,bondunit);
        contentValues.put(DbContract.bondstatus,status);
        contentValues.put(DbContract.bondtransactiondate,bondtransactiondate);
        database.insert(DbContract.BOND_TRANSACTION_TABLE,null,contentValues);
        return true;
    }

    public boolean updateBondTransactionTolocalDatabase(String id,String status,String bondnumber, String bondprice,String bondtransactiondate, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.bondnumber,bondnumber);
        contentValues.put(DbContract.bondunit,bondprice);
        contentValues.put(DbContract.bondstatus,status);
        contentValues.put(DbContract.bondtransactiondate,bondtransactiondate);
        String[] selection_arg = {id};
        database.update(DbContract.BOND_TRANSACTION_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }

    public Cursor readBondTransactionFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.bondnumber,DbContract.bondunit,DbContract.bondstatus,DbContract.bondtransactiondate};
        return (database.query(DbContract.BOND_TRANSACTION_TABLE,projection,null,null,null,null,null));
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    public boolean saveBondHoldingsTolocalDatabase(int id,String boarbonddname, String boardbondid, String bonduserid, String bondamount,String datecreated,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.boarbonddname,boarbonddname);
        contentValues.put(DbContract.boardbondid,boardbondid);
        contentValues.put(DbContract.bonduserid,bonduserid);
        contentValues.put(DbContract.bondamount,bondamount);
        contentValues.put(DbContract.bonddatecreated,datecreated);
        database.insert(DbContract.BOND_HOLDINGS_TABLE,null,contentValues);
        return true;
    }

    public boolean updateBondHoldingsTolocalDatabase(String id, String boarbonddname, String boardbondid, String bonduserid, String bondamount, String bonddatecreated, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.boarbonddname,boarbonddname);
        contentValues.put(DbContract.boardbondid,boardbondid);
        contentValues.put(DbContract.bonduserid,bonduserid);
        contentValues.put(DbContract.bondamount,bondamount);
        contentValues.put(DbContract.bonddatecreated, bonddatecreated);
        String[] selection_arg = {id};
        database.update(DbContract.BOND_HOLDINGS_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }

    public Cursor readBondHoldingsFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.boarbonddname,DbContract.boardbondid,DbContract.bonduserid,DbContract.bondamount,DbContract.bonddatecreated};
        return (database.query(DbContract.BOND_HOLDINGS_TABLE,projection,null,null,null,null,null));
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    public boolean saveShareHoldingsTolocalDatabase(int id,String boarsharename, String boardshareid, String shareuserid, String shareamount,String sharedatecreated,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.boarsharename,boarsharename);
        contentValues.put(DbContract.boardshareid,boardshareid);
        contentValues.put(DbContract.shareuserid,shareuserid);
        contentValues.put(DbContract.shareamount,shareamount);
        contentValues.put(DbContract.sharedatecreated,sharedatecreated);
        database.insert(DbContract.SHARE_HOLDINGS_TABLE,null,contentValues);
        return true;
    }

    public boolean updateShareHoldingsTolocalDatabase(String id,String boarsharename, String boardshareid, String shareuserid, String shareamount,String sharedatecreated,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.boarsharename,boarsharename);
        contentValues.put(DbContract.boardshareid,boardshareid);
        contentValues.put(DbContract.shareuserid,shareuserid);
        contentValues.put(DbContract.shareamount,shareamount);
        contentValues.put(DbContract.sharedatecreated,sharedatecreated);
        String[] selection_arg = {id};
        database.update(DbContract.SHARE_HOLDINGS_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }


    public Cursor readShareHoldingsFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.boarsharename,DbContract.boardshareid,DbContract.shareuserid,DbContract.shareamount,DbContract.sharedatecreated};
        return (database.query(DbContract.SHARE_HOLDINGS_TABLE,projection,null,null,null,null,null));
    }
}
