package com.example.leon.mylibrary.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by leon on 9/05/2015.
 */
public class BookReviewDb extends SQLiteOpenHelper {

    private SQLiteDatabase bookReviewDb;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bookReviewDB.db";

    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_REVIEW = "userreview";

    public BookReviewDb(Context context,
                        CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    //creates db
    @Override
    public void onCreate(SQLiteDatabase db) {
        bookReviewDb = db;
    }


    //reruns on create
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    //table handlers
    public void addBookTable(String TABLE_BOOK){
        String CREATE_BOOK_TABLE = "CREATE TABLE " +
                TABLE_BOOK + "("
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_REVIEW + " TEXT"
                + ")";
        bookReviewDb.execSQL(CREATE_BOOK_TABLE);
    }

    public boolean hasBookTable(String TABLE_BOOK){
        String query = "Select * FROM " + TABLE_BOOK;

        bookReviewDb = this.getWritableDatabase();

        try{
            bookReviewDb.rawQuery(query, null);
            return true;
        }catch (SQLiteException e){
            return false;
        }


    }


    //book review handlers
    public void addBookReview(String TABLE_BOOK, String username, String review) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_REVIEW, review);

        bookReviewDb = this.getWritableDatabase();

        bookReviewDb.insert(TABLE_BOOK, null, values);
        bookReviewDb.close();

    }

    public String findUserReview(String TABLE_BOOK, String username) {
        String query = "Select * FROM " + TABLE_BOOK + " WHERE " + COLUMN_USERNAME + " =  \"" + username + "\"";

        bookReviewDb = this.getWritableDatabase();

        Cursor cursor = bookReviewDb.rawQuery(query, null);

        String review = null;

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            review = cursor.getString(1);
            cursor.close();
        }

        bookReviewDb.close();

        return review;
    }

    public ArrayList<String> findAllReviews(String TABLE_BOOK) {
        String query = "Select * FROM " + TABLE_BOOK;

        bookReviewDb = this.getWritableDatabase();

        Cursor cursor = bookReviewDb.rawQuery(query, null);

        ArrayList reviews = new ArrayList();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                String review = cursor.getString(1);
                reviews.add(review);
                cursor.moveToNext();
            }
        }

        bookReviewDb.close();

        return reviews;
    }



}
