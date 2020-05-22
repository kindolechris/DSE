package com.dsetanzania.dse.storage;

public class DbContract {

    ////////////////////////////////////////////////////////
    public static final int SYNC_STATUS_OK = 0;
    public static final int SYNC_STATUS_FAILED = 1;
    public static final String USER_TABLE = "user";
    public static final String DATABASE_NAME = "dse_db";
    public static final String firstname = "firstname";
    public static final String lastname = "lastname";
    public static final String gender = "gender";
    public static final String tradername = "tradername";
    public static final String phonenumber = "phonenumber";
    public static final String university = "university";
    public static final String yearOfStudy = "yearOfStudy" ;
    public static final String coursename = "coursename";
    public static final String email = "email";
    public static final String bonds = "bonds";
    public static final String stock = "stock";
    public static final String virtualmoney = "virtualmoney";
    public static final String role = "role";
    public static final String SYNC_STATUS = "syncstatus";
    //public static final String UI_SYNCHRONIZE_DATA = "com.dsetanzania.dse.UI_SYNCHRONIZE_SQLITE";


    ///////////////////////////////////////////////////////////
    public static final String SHARE_TRANSACTION_TABLE = "sharestransaction";
    public static final String sharesAmout = "sharesAmount";
    public static final String sharetransactiondate = "transactiondate";
    public static final String elapsetime = "elapsetime";
    public static final String boardShareName = "boardShareName";
    public static final String sharePrice = "price";
    public static final String transactiontype = "transactiontype";
    public static final String transactionstatus = "transactionstatus";


    //////////////////////////////////////////////////////////
    public static final String BOND_TRANSACTION_TABLE = "bondstransaction";
    public static final String bondnumber = "bondnumber";
    public static final String bondstatus = "bondstatus";
    public static final String bondtransactiondate = "transactiondate";
    public static final String bondunit = "bondprice";

    /////////////////////////////////////////////////////////
    public static final String BOND_HOLDINGS_TABLE = "bondsHoldings";
    public static final String bonddatecreated = "datecreated";
    public static final String boarbonddname = "boarbonddname";
    public static final String boardbondid = "boardbondid";
    public static final String bonduserid = "bonduserid";
    public static final String bondamount = "bondamount";


    //////////////////////////////////////////////////////////
    public static final String SHARE_HOLDINGS_TABLE = "shareHoldings";
    public static final String sharedatecreated = "sharedatecreated";
    public static final String boarsharename = "boarsharename";
    public static final String boardshareid = "boardshareid";
    public static final String shareuserid = "shareuserid";
    public static final String shareamount = "shareamount";
}
