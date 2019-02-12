package com.afinal.group.myconnections;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TextInputLayout;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Database Name
    public static String DATABASE_NAME = "contact_database";
    // Current version of database
    private static final int DATABASE_VERSION = 1;
    // Name of table
    private static final String TABLE_CONTACT = "contact";
    // All fields used in database table
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String COMPANY = "company";
    private static final String NOTE = "note";

    public static String TAG = "my_tag";

    // Client Table Create Query in this string
    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE "
            + TABLE_CONTACT + "(" + KEY_FIRST_NAME
            + " TEXT," + KEY_LAST_NAME + " TEXT,"
            + EMAIL + " TEXT ,"
            + PHONE + " TEXT ,"
            + COMPANY + " TEXT, "
            + NOTE + " TEXT, PRIMARY KEY (" + KEY_FIRST_NAME + ", " + KEY_LAST_NAME + "));";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method is called by system if the database is accessed but not yet
     * created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT); // create client table
    }

    /**
     * This method is called when any modifications in database are done like
     * version is updated or database schema is changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + CREATE_TABLE_CONTACT); // drop table if exists
        onCreate(db);
    }

    /**
     * This method is used to add client detail in client Table
     */

    public long addContactDetail(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, contact.getFirstName());
        values.put(KEY_LAST_NAME, contact.getLastName());
        values.put(EMAIL, contact.getEmail());
        values.put(PHONE, contact.getPhoneNo());
        values.put(COMPANY, contact.getCompany());
        values.put(NOTE, contact.getNote());

        // insert row in client table

        long insert = db.insert(TABLE_CONTACT, null, values);
        return insert;
    }

    /**
     * This method is used to update particular client entry
     */
    public int updateEntry(Contact contact, String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();

       // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, contact.getFirstName());
        values.put(KEY_LAST_NAME, contact.getLastName());
        values.put(EMAIL, contact.getEmail());
        values.put(PHONE, contact.getPhoneNo());
        values.put(COMPANY, contact.getCompany());
        values.put(NOTE, contact.getNote());

         //update row in client table base on client.is value
        return db.update(TABLE_CONTACT, values, KEY_FIRST_NAME + " = ?" + " AND " + KEY_LAST_NAME + " = ?",
                new String[] { firstName, lastName });
    }

    /**
     * Used to delete particular client entry
     */
    public void deleteEntry(String firstName, String lastName) {
        // delete row in client table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, KEY_FIRST_NAME + " = ? AND " + KEY_LAST_NAME + " = ?",
                new String[] { String.valueOf(firstName), String.valueOf(lastName) });
    }

    /**
     * Used to get particular client details     *
     * @param firstName
     * @param lastName
     */
    public Contact getContact(String firstName, String lastName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACT + " WHERE "
                + KEY_FIRST_NAME + " = '" + firstName + "' AND " + KEY_LAST_NAME + " = '" + lastName + "'";
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();

            Contact contact = new Contact();
            contact.setFirstName(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
            contact.setLastName(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
            contact.setPhoneNo(c.getString(c.getColumnIndex(PHONE)));
            contact.setEmail(c.getString(c.getColumnIndex(EMAIL)));
            contact.setCompany(c.getString(c.getColumnIndex(COMPANY)));
            contact.setNote(c.getString(c.getColumnIndex(NOTE)));
            return contact;
        } else
            return null;
    }

    /**
     * Used to get detail of entire database and save in array list of data type
     * Clients
     */
    public List<Contact> getAllContactsList() {
        List<Contact> contactsArrayList = new ArrayList<Contact>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Contact contact = new Contact();
                contact.setFirstName(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
                contact.setLastName(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
                contact.setPhoneNo(c.getString(c.getColumnIndex(PHONE)));
                contact.setEmail(c.getString(c.getColumnIndex(EMAIL)));
                contact.setCompany(c.getString(c.getColumnIndex(COMPANY)));
                contact.setNote(c.getString(c.getColumnIndex(NOTE)));

                // adding to Clients list
                contactsArrayList.add(contact);
            } while (c.moveToNext());
        }
        return contactsArrayList;
    }

    public Boolean getContactnumber(String phoneNo) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACT + " WHERE "
                + PHONE + " = '" + phoneNo + "'";
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        return true;
    }

    public String checkEmailExist(String email){
        String fullName = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT first_name, last_name FROM " + TABLE_CONTACT + " WHERE "
                + EMAIL + " = '" + email + "'";
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            fullName = c.getString(c.getColumnIndex(KEY_FIRST_NAME)) + " " + c.getString(c.getColumnIndex(KEY_LAST_NAME));
        }
        return fullName;
    }
    public String checkPhoneExist(String phone){
        String fullName = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT first_name, last_name FROM " + TABLE_CONTACT + " WHERE "
                + PHONE + " = '" + phone + "'";
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            fullName = c.getString(c.getColumnIndex(KEY_FIRST_NAME)) + " " + c.getString(c.getColumnIndex(KEY_LAST_NAME));
        }
        return fullName;
    }
}
