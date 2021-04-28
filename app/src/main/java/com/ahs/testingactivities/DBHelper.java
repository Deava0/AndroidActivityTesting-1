package com.ahs.testingactivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.PipedWriter;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "book_prot_db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase book_prot_db) throws SQLException {
        book_prot_db.execSQL("create Table users(username Text primary key, password Text)");

        book_prot_db.execSQL("create Table books(isbn NUMERIC primary key, title TEXT, author TEXT, qt INTEGER, price DECIMAL,about TEXT, cover BLOB)");

    }
    @Override
    public void onOpen(SQLiteDatabase book_prot_db) {
        //book_prot_db.execSQL("drop Table if exists books");
        //book_prot_db.execSQL("create Table books(isbn NUMERIC primary key, title TEXT, author TEXT, qt INTEGER, price DECIMAL,about TEXT, cover BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase book_prot_db, int oldVersion, int newVersion) throws SQLException {
        book_prot_db.execSQL("drop Table if exists books");
    }

    public Boolean insertBook(String isbn, String title, String author, int qt, float price, String about, byte[] cover) throws SQLException{
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isbn", isbn);
        contentValues.put("title", title);
        contentValues.put("author", author);
        contentValues.put("qt", qt);
        contentValues.put("price", price);
        contentValues.put("about", about);
        contentValues.put("cover", cover);

        long result = book_prot_db.insert("books", null, contentValues);
        if (result == -1) {
            book_prot_db.close();
            return false;
        } else {
            book_prot_db.close();
            return true;
        }

    }


    public Boolean checkISBN(String isbn)throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn =?", new String[]{isbn});
        if (cursor.getCount() > 0) {
            book_prot_db.close();
            cursor.close();
            return true;
        } else {
            book_prot_db.close();
            cursor.close();
            return false;
        }
    }


    public Boolean insertUser(String username, String password) throws SQLException{
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = book_prot_db.insert("users", null, contentValues);
        if (result == -1) {
            book_prot_db.close();
            return false;
        } else {
            book_prot_db.close();
            return true;
        }
    }

    public Boolean checkusername(String userName)throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        Cursor cursor = book_prot_db.rawQuery("select * from users where username =?", new String[]{userName});
        if (cursor.getCount() > 0) {
            book_prot_db.close();
            cursor.close();
            return true;
        } else {
            book_prot_db.close();
            cursor.close();
            return false;
        }
    }

    public Boolean check_UN_PW(String UN, String PW) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        Cursor cursor = book_prot_db.rawQuery("select * from users where username =? and password =? ", new String[]{UN, PW});
        if (cursor.getCount() > 0) {
            book_prot_db.close();
            cursor.close();
            return true;
        } else {
            book_prot_db.close();
            cursor.close();
            return false;
        }
    }
}
