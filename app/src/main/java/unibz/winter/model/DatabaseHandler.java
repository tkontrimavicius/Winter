package unibz.winter.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jetzt on 12/10/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "msgsManager";
    private static final String TABLE_CONTACTS = "msg";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "msg";
    private static final String KEY_MORSE = "morseMsg";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_MORSE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new msg
    public void addMsg(Msg msg) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, msg.getMsg()); // Msg Name
        values.put(KEY_MORSE, msg.getMorseMsg()); // Contact Phone


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Msg getMsg(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_MORSE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Msg msg = new Msg(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return msg
        return msg;
    }

    // code to get all contacts in a list view
    public List<Msg> getAllMsgs() {
        List<Msg> msgList = new ArrayList<Msg>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Msg msg = new Msg();
                msg.setID(Integer.parseInt(cursor.getString(0)));
                msg.setMsg(cursor.getString(1));
                msg.setMorseMsg(cursor.getString(2));
                // Adding msg to list
                msgList.add(msg);
            } while (cursor.moveToNext());
        }

        // return contact list
        return msgList;
    }

    // code to update the single msg
    public int updateMsg(Msg msg) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, msg.getMsg());
        values.put(KEY_MORSE, msg.getMorseMsg());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(msg.getID()) });
    }

    // Deleting single msg
    public void deleteMsg(Msg msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(msg.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getMsgCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}