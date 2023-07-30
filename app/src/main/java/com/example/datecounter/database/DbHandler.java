package com.example.datecounter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.datecounter.model.EventModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DbHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "MY_DATABASE";
    private static final String TABLE_NAME = "EVENTS";


    //Column names
    private static final String TITLE = "TITLE";
    private static final String DATE = "DATE";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table_create_query = "CREATE TABLE " + TABLE_NAME +
                " (" +
                TITLE + " TEXT," +
                DATE + " TEXT," +
                "PRIMARY KEY"+"("+ TITLE+ "," + DATE + ")"+
                ");";
        sqLiteDatabase.execSQL(table_create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String table_drop_query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(table_drop_query);
        onCreate(sqLiteDatabase);
    }

    public void insertData(EventModel eventModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, eventModel.getEventTitle());
        contentValues.put(DATE,eventModel.getEventDate());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public EventModel[] retrieveData(){
        List<EventModel> events = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * " +" FROM " + TABLE_NAME + " ORDER BY " + DATE ;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                EventModel eventModel =  new EventModel();
                eventModel.setEventTitle(cursor.getString(0));
                eventModel.setEventDate((cursor.getString(1)));

                events.add(eventModel);
            } while (cursor.moveToNext());
        }

        EventModel[] arrayEvents = new EventModel[events.size()];
        events.toArray(arrayEvents);
        return arrayEvents;
    }
    public int removeData(EventModel eventModel) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] whereArgs = new String[]{eventModel.getEventTitle(), eventModel.getEventDate()};

        return sqLiteDatabase.delete(TABLE_NAME,"TITLE=? and DATE=?", whereArgs);


    }
}
