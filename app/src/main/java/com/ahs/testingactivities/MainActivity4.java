package com.ahs.testingactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity {

    EditText etISBN, etTitle, etAuthor, etQt, etPrice, etAbout;
    Button bUpdate,bDelete, bCheckISBN;
    ImageView ivbookPic;
    DBHelper book_prot_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        book_prot_db = new DBHelper(this);

        etISBN = (EditText) findViewById(R.id.etISBN);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etQt = (EditText) findViewById(R.id.etQt);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etAbout = (EditText) findViewById(R.id.etAbout);

        bUpdate = (Button) findViewById(R.id.bUpdate);
        bDelete = (Button) findViewById(R.id.bDelete);
        bCheckISBN = (Button) findViewById(R.id.bCheckISBN);

        ivbookPic = (ImageView) findViewById(R.id.ivbookPic);

        bCheckISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBooksByISBN(etISBN.getText().toString());
            }
        });



    }

    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
    }

    public void  selectBooksByISBN(String isbn)throws SQLException {
        SQLiteDatabase book_prot_db = getApplicationContext().openOrCreateDatabase("book_prot_db.db", Context.MODE_PRIVATE,null);
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn =?", new String[]{isbn});
        if (cursor.getCount() > 0) {

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                Toast.makeText(this, "Record Found", Toast.LENGTH_SHORT).show();

                etTitle.setText(cursor.getString(1));
                etAuthor.setText(cursor.getString(2));
                etQt.setText(cursor.getString(3));
                etPrice.setText(cursor.getString(4));
                etAbout.setText(cursor.getString(5));
                setImageViewWithByteArray(ivbookPic, cursor.getBlob(6));
            }


            book_prot_db.close();
            cursor.close();
        } else {
            book_prot_db.close();
            cursor.close();
            Toast.makeText(this, "No record found for ISBN="+isbn, Toast.LENGTH_SHORT).show();
        }
    }

}