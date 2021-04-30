package com.ahs.testingactivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "book_prot_db.db", null, 1);
    }

    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
    }

    @Override
    public void onCreate(SQLiteDatabase book_prot_db) throws SQLException {

        try {
            book_prot_db.execSQL("create Table users(username Text primary key, password Text)");

            book_prot_db.execSQL("create Table books(isbn NUMERIC primary key, title TEXT, author TEXT, qt INTEGER, price DECIMAL,about TEXT, cover BLOB)");

            book_prot_db.execSQL("create Table orders(order_id INTEGER primary key AUTOINCREMENT NOT NULL, ISBN NUMERIC , customer_name TEXT, location TEXT, phone TEXT,qt INTEGER,price DECIMAL, date DATE, FOREIGN KEY(ISBN) REFERENCES books(isbn))");

        } catch (Exception ex) {
            System.out.println("\n\n\n\n\n\n\n\n\n" + ex.toString());
        }
    }

    @Override
    public void onOpen(SQLiteDatabase book_prot_db) {
       /* book_prot_db.execSQL("drop Table if exists books");
        book_prot_db.execSQL("drop Table if exists orders");

        book_prot_db.execSQL("create Table books(isbn NUMERIC primary key, title TEXT, author TEXT, qt INTEGER, price DECIMAL,about TEXT, cover BLOB)");

        book_prot_db.execSQL("create Table orders(order_id INTEGER primary key AUTOINCREMENT NOT NULL, ISBN NUMERIC , customer_name TEXT, location TEXT, phone TEXT,qt INTEGER,price DECIMAL, date DATE, FOREIGN KEY(ISBN) REFERENCES books(isbn))");
        */

    }

    @Override
    public void onUpgrade(SQLiteDatabase book_prot_db, int oldVersion, int newVersion) throws SQLException {
        book_prot_db.execSQL("drop Table if exists books");
    }

    public ArrayList<HashMap<String, String>> GetBooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> bookList = new ArrayList<>();
        String query = "select * from books where isbn";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<HashMap<String, String>> bookListPic = new ArrayList<>();
        while (cursor.moveToNext()) {
            HashMap<String, String> book = new HashMap<>();
            book.put("isbn", cursor.getString(cursor.getColumnIndex("isbn")));
            book.put("title", cursor.getString(cursor.getColumnIndex("title")));
            book.put("author", cursor.getString(cursor.getColumnIndex("author")));
            book.put("qt", cursor.getString(cursor.getColumnIndex("qt")));
            book.put("price", cursor.getString(cursor.getColumnIndex("price")));
            bookList.add(book);

        }
        return bookList;
    }

    //        book_prot_db.execSQL("create Table orders(order_id NUMERIC primary key AUTOINCREMENT NOT NULL, ISBN TEXT , customer_name TEXT, location TEXT, phone TEXT,qt INTEGER,price DECIMAL, date DATE, FOREIGN KEY(ISBN) REFERENCES books(isbn))");
    public ArrayList<HashMap<String, String>> GetOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> orderList = new ArrayList<>();
        String query = "select orders.order_id,orders.ISBN,orders.customer_name,orders.location,orders.phone,orders.qt,orders.price,orders.date,books.title,books.author from orders, books where orders.ISBN = books.isbn";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> order = new HashMap<>();
            order.put("order_id", cursor.getString(cursor.getColumnIndex("order_id")));
            order.put("ISBN", cursor.getString(cursor.getColumnIndex("ISBN")));
            order.put("customer_name", cursor.getString(cursor.getColumnIndex("customer_name")));
            order.put("location", cursor.getString(cursor.getColumnIndex("location")));
            order.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
            order.put("qt", cursor.getString(cursor.getColumnIndex("qt")));
            order.put("price", cursor.getString(cursor.getColumnIndex("price")));
            order.put("date", cursor.getString(cursor.getColumnIndex("date")));
            order.put("title", cursor.getString(cursor.getColumnIndex("title")));
            order.put("author", cursor.getString(cursor.getColumnIndex("author")));
            orderList.add(order);
            System.out.println("\n\n\n\n\nasad\n\n\n\n" + cursor.getString(cursor.getColumnIndex("price")));

        }
        return orderList;
    }

    public Boolean insertOrder(String ISBN, String customer_name, String location, String phone, String date, int qt, float price) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ISBN", ISBN);
        contentValues.put("customer_name", customer_name);
        contentValues.put("location", location);
        contentValues.put("phone", phone);
        contentValues.put("qt", qt);
        contentValues.put("date", date);
        contentValues.put("price", price);


        long result = book_prot_db.insert("orders", null, contentValues);
        if (result == -1) {

            return false;
        } else {

            updateBookQT(ISBN, qt);
            return true;

        }

    }

    public Boolean insertBook(String isbn, String title, String author, int qt, float price, String about, byte[] cover) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isbn", isbn);
        contentValues.put("title", title);
        contentValues.put("author", author);
        contentValues.put("qt", qt);
        contentValues.put("price", price);
        contentValues.put("about", about);
        contentValues.put("cover", cover);
        updateBookQT(isbn, qt);
        long result = book_prot_db.insert("books", null, contentValues);
        if (result == -1) {

            return false;
        } else {

            return true;
        }

    }

    public Boolean updateBookQT(String isbn, int qt) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int OldQT = 0;
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn =?", new String[]{isbn});
        if (cursor.getCount() > 0) {

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                OldQT = cursor.getInt(3);
            }
        } else {

            cursor.close();
        }
        qt = OldQT - qt;
        contentValues.put("qt", qt);
        long result = book_prot_db.update("books", contentValues, "isbn=?", new String[]{isbn});
        if (result == -1) {

            return false;
        } else {

            return true;
        }
    }


    public Boolean updateBook(String isbn, String title, String author, int qt, float price, String about, byte[] cover) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("isbn", isbn);
        contentValues.put("title", title);
        contentValues.put("author", author);
        contentValues.put("qt", qt);
        contentValues.put("price", price);
        contentValues.put("about", about);
        contentValues.put("cover", cover);

        long result = book_prot_db.update("books", contentValues, "isbn=?", new String[]{isbn});
        if (result == -1) {

            return false;
        } else {

            return true;
        }

    }

    public Boolean deleteBook(String isbn) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();


        long result = book_prot_db.delete("books", "isbn=?", new String[]{isbn});
        if (result == -1) {

            return false;
        } else {

            return true;
        }

    }


    public Boolean checkISBN(String isbn) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn =?", new String[]{isbn});
        if (cursor.getCount() > 0) {

            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }


    public Boolean insertUser(String username, String password) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = book_prot_db.insert("users", null, contentValues);
        if (result == -1) {

            return false;
        } else {

            return true;
        }
    }

    public Boolean checkusername(String userName) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        Cursor cursor = book_prot_db.rawQuery("select * from users where username =?", new String[]{userName});
        if (cursor.getCount() > 0) {

            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }

    public Boolean check_UN_PW(String UN, String PW) throws SQLException {
        SQLiteDatabase book_prot_db = this.getWritableDatabase();
        Cursor cursor = book_prot_db.rawQuery("select * from users where username =? and password =? ", new String[]{UN, PW});
        if (cursor.getCount() > 0) {

            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }
}
