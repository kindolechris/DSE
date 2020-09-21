package com.dsetanzania.dse.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigDecimal;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_USER = "create table "+DbContract.USER_TABLE +
            "(id text," +DbContract.firstname+" text," +DbContract.lastname+
            " text,"+DbContract.gender+" text," +DbContract.tradername+ " text," +DbContract.phonenumber+
            " text,"+DbContract.university+" text," +DbContract.yearOfStudy+
            " text,"+DbContract.coursename+" text," +DbContract.email+
            " text,"+DbContract.bonds+" integer," +DbContract.stock+
            " integer,"+DbContract.virtualmoney+" double,"+DbContract.portfolio_value+" integer,"+DbContract.role+
            " text," +DbContract.SYNC_STATUS+
            " integer);";

    private static final String CREATE_TABLE_SHARE_TRANSACTION = "create table "+DbContract.SHARE_TRANSACTION_TABLE +
            "(id text," +DbContract.sharesAmout+" text," +DbContract.sharetransactiondate +
            " text,"+ DbContract.elapsetime+" text,"+DbContract.boardShareName+" text," +DbContract.sharePrice+ " text," +DbContract.transactiontype+
            " text,"+DbContract.transactionstatus+" text)";

    private static final String CREATE_TABLE_BOND_TRANSACTION = "create table "+DbContract.BOND_TRANSACTION_TABLE +
            "(id text," +DbContract.auction_date+" text,"  +DbContract.bond_tenure+" text," +DbContract.coupon_rate+" text," +DbContract.bond_price +" text,"+DbContract.bondstatus +" text," +DbContract.bondtransactiondate+
            " text,"+DbContract.bondtransactionTimeAgo+ " text)";


    private static final String CREATE_TABLE_BOND_HOLDINGS = "create table "+DbContract.BOND_HOLDINGS_TABLE +
            "(id text," +DbContract.holding_auction_date +" text,"  +DbContract.holding_bond_tenure +" text," +DbContract.holding_coupon_rate +" text," +DbContract.boardbondid +
            " text,"+DbContract.bonduserid+" text," +DbContract.bond_holding_price+ " text," +DbContract.bonddatecreated +
            " text)";


    private static final String CREATE_TABLE_SHARE_HOLDINGS = "create table "+DbContract.SHARE_HOLDINGS_TABLE +
            "(id text," +DbContract.boarsharename+" text," +DbContract.boardshareid +
            " text,"+DbContract.shareuserid+" text," +DbContract.shareamount+ " text," +DbContract.sharedatecreated+
            " text)";

    private static final String CREATE_TABLE_LIVE_DATA = "create table "+DbContract.LIVE_MARKET__TABLE +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +DbContract.Change+" text," +DbContract.Board +
            " text,"+DbContract.Closee+" text," +DbContract.Company+ " text," +DbContract.High+
            " text,"+ DbContract.LastDealPrice+" text,"+ DbContract.LastTradedQuantity+" text,"+DbContract.openingPrice+" text,"+ DbContract.Low+" text,"+ DbContract.MarketCap+" text,"+ DbContract.Time+" text,"+ DbContract.Volume+
            " text)";

    private static final String DROP_TABLE_USER = "drop table if exists "+DbContract.USER_TABLE;
    private static final String DROP_TABLE_SHARE_TRANSACTION = "drop table if exists "+DbContract.SHARE_TRANSACTION_TABLE;
    private static final String DROP_TABLE_BOND_TRANSACTION = "drop table if exists "+DbContract.BOND_TRANSACTION_TABLE;
    private static final String DROP_TABLE_BOND_HOLDING = "drop table if exists "+DbContract.BOND_HOLDINGS_TABLE;
    private static final String DROP_TABLE_SHARE_HOLDING = "drop table if exists "+DbContract.SHARE_HOLDINGS_TABLE;
    private static final String DROP_TABLE_lIVE_DATA = "drop table if exists "+DbContract.LIVE_MARKET__TABLE;

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
       db.execSQL(CREATE_TABLE_LIVE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_SHARE_TRANSACTION);
        db.execSQL(DROP_TABLE_BOND_TRANSACTION);
        db.execSQL(DROP_TABLE_BOND_HOLDING);
        db.execSQL(DROP_TABLE_SHARE_HOLDING);
        db.execSQL(DROP_TABLE_lIVE_DATA);
        onCreate(db);
    }


    //////////////////////////////////////////////////////////////////
    public void saveUserTolocalDatabase(String id, int stock, int bonds, String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename, String phonenumber, String role, double virtualmoney, String gender, int sync_status,Integer portfolio_value, SQLiteDatabase database){
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
        contentValues.put(DbContract.portfolio_value,portfolio_value);
        contentValues.put(DbContract.stock,stock);
        contentValues.put(DbContract.bonds,bonds);
        contentValues.put(DbContract.gender,gender);
        contentValues.put(DbContract.SYNC_STATUS,sync_status);
        database.insert(DbContract.USER_TABLE,null,contentValues);
    }

    public Cursor readUserFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.firstname,DbContract.lastname,DbContract.tradername,DbContract.bonds,DbContract.stock,DbContract.virtualmoney,DbContract.university,DbContract.portfolio_value,DbContract.SYNC_STATUS};
        return (database.query(DbContract.USER_TABLE,projection,null,null,null,null,null));
    }

    public boolean updateUserLocalDatabase(String id, int stock, int bonds, String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename, String phonenumber, String role, double virtualmoney, String gender, int sync_status,Integer portfolio_value, SQLiteDatabase database){
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
        contentValues.put(DbContract.portfolio_value,portfolio_value);
        contentValues.put(DbContract.stock,stock);
        contentValues.put(DbContract.bonds,bonds);
        contentValues.put(DbContract.gender,gender);
        contentValues.put(DbContract.SYNC_STATUS,sync_status);
        String[] selection_arg = {id};
        database.update(DbContract.USER_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }


    ////////////////////////////////////////////////////////////////////
    public boolean saveShareTransactionTolocalDatabase(String id, String sharesAmount, String transactiondate, String elapsetime, String boardShareName, Integer price, String transactiontype, String transactionstatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.sharesAmout,sharesAmount);
        contentValues.put(DbContract.sharetransactiondate,transactiondate);
        contentValues.put(DbContract.elapsetime,elapsetime);
        contentValues.put(DbContract.boardShareName,boardShareName);
        contentValues.put(DbContract.sharePrice,price);
        contentValues.put(DbContract.transactiontype,transactiontype);
        contentValues.put(DbContract.transactionstatus,transactionstatus);
        database.insert(DbContract.SHARE_TRANSACTION_TABLE,null,contentValues);
        return true;
    }

    public boolean updateShareTransactionTolocalDatabase(String id, String sharesAmount, String transactiondate,String elapsetime, String boardShareName, String price, String transactiontype, String transactionstatus, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.sharesAmout,sharesAmount);
        contentValues.put(DbContract.sharetransactiondate,transactiondate);
        contentValues.put(DbContract.elapsetime,elapsetime);
        contentValues.put(DbContract.boardShareName,boardShareName);
        contentValues.put(DbContract.sharePrice,price);
        contentValues.put(DbContract.transactiontype,transactiontype);
        contentValues.put(DbContract.transactionstatus,transactionstatus);
        String[] selection_arg = {id};
        database.update(DbContract.SHARE_TRANSACTION_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }

    public Cursor readShareTransactionFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.sharesAmout,DbContract.sharetransactiondate,DbContract.elapsetime,DbContract.boardShareName,DbContract.sharePrice,DbContract.transactiontype,DbContract.transactionstatus};
        return (database.query(DbContract.SHARE_TRANSACTION_TABLE,projection,null,null,null,null,null));
    }



    //////////////////////////////////////////////////////////////////
    public boolean saveBondTransactionTolocalDatabase(String id, String auction_date, String status, String bond_tenure,String coupon_rate,String price, String bondtransactiondate,String bondtransactionTimeAgo, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.auction_date,auction_date);
        contentValues.put(DbContract.bond_tenure,bond_tenure);
        contentValues.put(DbContract.coupon_rate,coupon_rate);
        contentValues.put(DbContract.bond_price,price);
        contentValues.put(DbContract.bondstatus,status);
        contentValues.put(DbContract.bondtransactiondate,bondtransactiondate);
        contentValues.put(DbContract.bondtransactionTimeAgo,bondtransactionTimeAgo);
        database.insert(DbContract.BOND_TRANSACTION_TABLE,null,contentValues);
        return true;
    }

    public boolean updateBondTransactionTolocalDatabase(String id,String auction_date, String status, String bond_tenure,String coupon_rate,String bondtransactiondate,String bondtransactionTimeAgo, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.auction_date,auction_date);
        contentValues.put(DbContract.bond_tenure,bond_tenure);
        contentValues.put(DbContract.coupon_rate,coupon_rate);
        contentValues.put(DbContract.bondstatus,status);
        contentValues.put(DbContract.bondtransactiondate,bondtransactiondate);
        contentValues.put(DbContract.bondtransactionTimeAgo,bondtransactionTimeAgo);
        String[] selection_arg = {id};
        database.update(DbContract.BOND_TRANSACTION_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }

    public Cursor readBondTransactionFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.auction_date,DbContract.bond_tenure,DbContract.coupon_rate,DbContract.bondstatus,DbContract.bond_price,DbContract.bondtransactiondate,DbContract.bondtransactionTimeAgo};
        return (database.query(DbContract.BOND_TRANSACTION_TABLE,projection,null,null,null,null,null));
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    public boolean saveBondHoldingsTolocalDatabase(int id,String holding_auction_date,String holding_bond_tenure,String holding_coupon_rate, String boardbondid, String bonduserid, String bondamount,String datecreated,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.holding_auction_date,holding_auction_date);
        contentValues.put(DbContract.holding_bond_tenure,holding_bond_tenure);
        contentValues.put(DbContract.holding_coupon_rate,holding_coupon_rate);
        contentValues.put(DbContract.boardbondid,boardbondid);
        contentValues.put(DbContract.bonduserid,bonduserid);
        contentValues.put(DbContract.bond_holding_price,bondamount);
        contentValues.put(DbContract.bonddatecreated,datecreated);
        database.insert(DbContract.BOND_HOLDINGS_TABLE,null,contentValues);
        return true;
    }

    public boolean updateBondHoldingsTolocalDatabase(String id,String holding_auction_date,String holding_bond_tenure,String holding_coupon_rate, String boardbondid, String bonduserid, String bondamount, String bonddatecreated, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put(DbContract.holding_auction_date,holding_auction_date);
        contentValues.put(DbContract.holding_bond_tenure,holding_bond_tenure);
        contentValues.put(DbContract.holding_coupon_rate,holding_coupon_rate);
        contentValues.put(DbContract.boardbondid,boardbondid);
        contentValues.put(DbContract.bonduserid,bonduserid);
        contentValues.put(DbContract.bond_holding_price,bondamount);
        contentValues.put(DbContract.bonddatecreated, bonddatecreated);
        String[] selection_arg = {id};
        database.update(DbContract.BOND_HOLDINGS_TABLE,contentValues,"id=? ",selection_arg);
        return true;
    }

    public Cursor readBondHoldingsFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.holding_auction_date,DbContract.holding_bond_tenure,DbContract.holding_coupon_rate,DbContract.boardbondid,DbContract.bonduserid,DbContract.bond_holding_price,DbContract.bonddatecreated};
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


    public boolean saveLiveDataTolocalDatabase(String board, String change, String close, String company, String high, String lastDealPrice, String lastTradedQuantity, String low, String marketCap, String openingPrice, String time, String volume, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.Board,board);
        contentValues.put(DbContract.Change,change);
        contentValues.put(DbContract.Closee,close);
        contentValues.put(DbContract.Company,company);
        contentValues.put(DbContract.High,high);
        contentValues.put(DbContract.LastDealPrice,lastDealPrice);
        contentValues.put(DbContract.LastTradedQuantity,lastTradedQuantity);
        contentValues.put(DbContract.Low,low);
        contentValues.put(DbContract.MarketCap,marketCap);
        contentValues.put(DbContract.openingPrice,openingPrice);
        contentValues.put(DbContract.Time,time);
        contentValues.put(DbContract.Volume,volume);
        database.insert(DbContract.LIVE_MARKET__TABLE,null,contentValues);
        return true;
    }

    public Cursor readLiveDataFromLocalDatabase(SQLiteDatabase database){
        String [] projection = {"id",DbContract.Change,DbContract.Board,DbContract.Closee,DbContract.Company,DbContract.High,DbContract.LastDealPrice,DbContract.LastTradedQuantity,DbContract.openingPrice,DbContract.MarketCap,DbContract.Time,DbContract.Low,DbContract.Volume};
        return (database.query(DbContract.LIVE_MARKET__TABLE,projection,null,null,null,null,null));
    }
}
