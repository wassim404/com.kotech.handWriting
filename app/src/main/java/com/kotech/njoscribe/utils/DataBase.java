package com.kotech.njoscribe.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


public class DataBase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "NJO_DATABASE";
    // Contacts table name
    private static final String TABLE_NOTES = "AllTextNote";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_BODY = "body";
    // CRUD operations
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_TITLE, KEY_DATE, KEY_BODY};

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create contacts table
        String CREATE_TABLE_NOTES = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "title TEXT, " +
                "date DATETIME, " +
                "body TEXT )";

        // create contacts table
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older contacts table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        // create fresh contacts table
        this.onCreate(db);

    }

    public long addTextnote(Textnote textnote) {
        Log.d("addTextnote", textnote.toString());

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_NAME, "");
        values.put(KEY_TITLE, textnote.getTitle());
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        values.put(KEY_DATE, mydate);
        values.put(KEY_BODY, textnote.getBody());


        long i = db.insert(TABLE_NOTES, null, values);
        Log.i("id", i + "");
        // 4. close
        db.close();
        return i;
    }

    public ArrayList<Textnote> getAlltextnote() {
        ArrayList<Textnote> textnotes = new ArrayList<Textnote>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NOTES + " ORDER BY date DESC";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Textnote textnote = null;
        if (cursor.moveToFirst()) {
            do {
                textnote = new Textnote();
                textnote.setId(Integer.parseInt(cursor.getString(0)));
                textnote.setName(cursor.getString(1));
                textnote.setTitle(cursor.getString(2));
                textnote.setDate(cursor.getString(3));
                textnote.setBody(cursor.getString(4));


                textnotes.add(textnote);
            } while (cursor.moveToNext());
        }

        Log.d("getAlltextnote()", textnotes.toString());

        return textnotes;
    }

    public Textnote getOneTextNote(int id) {


        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor =
                db.query(TABLE_NOTES, COLUMNS, " id = ?",
                        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Textnote textnote = new Textnote();
        textnote.setId(cursor.getInt(0));
        textnote.setName(cursor.getString(1));
        textnote.setTitle(cursor.getString(2));
        textnote.setDate(cursor.getString(3));
        textnote.setBody(cursor.getString(4));

        return textnote;
    }


    public int updateTextnote(Textnote textnote) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ""); // get nom
        values.put(KEY_TITLE, textnote.getTitle());
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        values.put(KEY_DATE, mydate);
        values.put(KEY_BODY, textnote.getBody());


        // 3. updating row
        int i = db.update(TABLE_NOTES, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(textnote.getId())}); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteTextnote(int id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_NOTES,
                KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        // 3. close
        db.close();


    }

    public boolean checkDataBase(Context context) {

        SQLiteDatabase checkDB = null;
        boolean IfExist = false;
        try {

            File database = context.getDatabasePath(DATABASE_NAME);

            if (database.exists()) {

                Log.i("Database", "Found");

                String myPath = database.getAbsolutePath();

                Log.i("Database Path", myPath);

                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
                IfExist = true;
            } else {

                // Database does not exist so copy it from assets here
                Log.i("Database", "Not Found");
                IfExist = false;

            }

        } catch (SQLiteException e) {

            Log.i("Database", "Not Found");
            IfExist = false;

        } finally {

            if (checkDB != null) {

                checkDB.close();

            }
        }
        return IfExist;
    }
}