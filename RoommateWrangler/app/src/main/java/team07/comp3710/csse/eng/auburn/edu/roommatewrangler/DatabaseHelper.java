package team07.comp3710.csse.eng.auburn.edu.roommatewrangler;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Margaret Caufield on 4/19/2015.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "roommateManager";

    // Table Names
    private static final String TABLE_BILLS = "bills";
    private static final String TABLE_ROOMMATES = "roommate";
    private static final String TABLE_GROCERIES = "groceries";

    // Common column names
    private static final String KEY_ID = "id";

    // Bills Table - column names
    private static final String KEY_BILL_TYPE = "bill_type";
    private static final String KEY_ROOMMATE_ID = "roommate_id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_PAID = "paid";

    // Groceries Table - column names
    private static final String KEY_GROCERY_TYPE = "grocery_type";

    // NOTE_TAGS Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_NUMBER = "number";

    // Table Create Statements
    // Bill table create statement
    private static final String CREATE_TABLE_BILLS = "CREATE TABLE "
            + TABLE_BILLS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BILL_TYPE
            + " TEXT," + KEY_AMOUNT + " REAL," + KEY_ROOMMATE_ID
            + " INTEGER," + KEY_PAID + " INTEGER" + ")";

    // Groceries table create statement
    private static final String CREATE_TABLE_GROCERIES = "CREATE TABLE " + TABLE_GROCERIES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_GROCERY_TYPE + " TEXT,"
            + KEY_ROOMMATE_ID + " INTEGER" + ")";

    // Roommate table create statement
    private static final String CREATE_TABLE_ROOMMATES = "CREATE TABLE "
            + TABLE_ROOMMATES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NUMBER + " INTEGER," + KEY_NAME + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_BILLS);
        db.execSQL(CREATE_TABLE_GROCERIES);
        db.execSQL(CREATE_TABLE_ROOMMATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMMATES);

        // create new tables
        onCreate(db);
    }

    //Create
    public long createRoommate(Roommate roommie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, roommie.getName());
        values.put(KEY_NUMBER, roommie.getPhoneNumber());

        // insert row
        long roommate_id = db.insert(TABLE_ROOMMATES, null, values);

        return roommate_id;
    }

    public long createBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BILL_TYPE, bill.getBillType());
        values.put(KEY_AMOUNT, bill.getAmount());
        values.put(KEY_ROOMMATE_ID, bill.getRoommateNumber());
        if (bill.isPaid()) {
            values.put(KEY_PAID, 1);
        }
        else
        {
            values.put(KEY_PAID, 0);
        }

        long bill_id = db.insert(TABLE_BILLS, null, values);

        return bill_id;
    }

    public long createGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GROCERY_TYPE, grocery.getType());
        values.put(KEY_ROOMMATE_ID, grocery.getRoommate());

        long grocery_id = db.insert(TABLE_GROCERIES, null, values);

        return grocery_id;
    }

    //get
    public ArrayList<Roommate> getAllRoommates()
    {
        ArrayList<Roommate> toRet = new ArrayList<Roommate>();
        String selectQuery = "SELECT  * FROM " + TABLE_ROOMMATES + " ORDER BY " + KEY_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Roommate roommie = new Roommate();
                roommie.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                roommie.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                roommie.setPhoneNumber(c.getLong(c.getColumnIndex(KEY_NUMBER)));

                // adding to roommate list
                toRet.add(roommie);
            } while (c.moveToNext());
        }

        return toRet;
    }

    public ArrayList<Bill> getAllBills()
    {
        ArrayList<Bill> toRet = new ArrayList<Bill>();
        String selectQuery = "SELECT  * FROM " + TABLE_BILLS + " ORDER BY " + KEY_PAID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setAmount(c.getDouble((c.getColumnIndex(KEY_AMOUNT))));
                bill.setBillType((c.getString(c.getColumnIndex(KEY_BILL_TYPE))));
                bill.setRoommateNumber(c.getInt(c.getColumnIndex(KEY_ROOMMATE_ID)));
                bill.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                if (c.getInt((c.getColumnIndex(KEY_AMOUNT))) != 0)
                {
                    bill.markBillPaid();
                }

                // adding to bill list
                toRet.add(bill);
            } while (c.moveToNext());
        }

        return toRet;
    }

    public ArrayList<Bill> getAllPaidBills()
    {
        ArrayList<Bill> toRet = new ArrayList<Bill>();
        String selectQuery = "SELECT  * FROM " + TABLE_BILLS + " WHERE " + KEY_PAID + " = 1";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setAmount(c.getDouble((c.getColumnIndex(KEY_AMOUNT))));
                bill.setBillType((c.getString(c.getColumnIndex(KEY_BILL_TYPE))));
                bill.setRoommateNumber(c.getInt(c.getColumnIndex(KEY_ROOMMATE_ID)));
                bill.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                // adding to bill list
                toRet.add(bill);
            } while (c.moveToNext());
        }

        return toRet;
    }

    public ArrayList<Bill> getAllUnpaidBills()
    {
        ArrayList<Bill> toRet = new ArrayList<Bill>();
        String selectQuery = "SELECT  * FROM " + TABLE_BILLS + " WHERE " + KEY_PAID + " = 0";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setAmount(c.getDouble((c.getColumnIndex(KEY_AMOUNT))));
                bill.setBillType((c.getString(c.getColumnIndex(KEY_BILL_TYPE))));
                bill.setRoommateNumber(c.getInt(c.getColumnIndex(KEY_ROOMMATE_ID)));
                bill.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                // adding to bill list
                toRet.add(bill);
            } while (c.moveToNext());
        }

        return toRet;
    }

    public ArrayList<Grocery> getAllGroceries()
    {
        ArrayList<Grocery> toRet = new ArrayList<Grocery>();
        String selectQuery = "SELECT  * FROM " + TABLE_GROCERIES + " ORDER BY " + KEY_ROOMMATE_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                grocery.setType((c.getString(c.getColumnIndex(KEY_GROCERY_TYPE))));
                grocery.setRoommate(c.getInt(c.getColumnIndex(KEY_ROOMMATE_ID)));

                // adding to grocery list
                toRet.add(grocery);
            } while (c.moveToNext());
        }

        return toRet;
    }

    public int updateBill(Bill bill)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, bill.getAmount());
        if (bill.isPaid()) {
            values.put(KEY_PAID, 1);
        }
        else
        {
            values.put(KEY_PAID, 0);
        }
        values.put(KEY_BILL_TYPE, bill.getBillType());
        values.put(KEY_ROOMMATE_ID, bill.getRoommateNumber());

        // updating row
        return db.update(TABLE_BILLS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(bill.getId()) });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}