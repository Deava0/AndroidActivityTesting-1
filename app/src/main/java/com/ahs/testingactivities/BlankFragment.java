package com.ahs.testingactivities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class BlankFragment extends Fragment {
    EditText etISBN, etTitle, etAuthor, etQt, etPrice, etAbout;
    Button bUpdate,bDelete, bCheckISBN;
    ImageView ivbookPic;
    DBHelper book_prot_db;

    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {



        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =   inflater.inflate(R.layout.fragment_blank,container,false);
        book_prot_db = new DBHelper(getActivity());

        etISBN = (EditText) view.findViewById(R.id.etISBN);
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etAuthor = (EditText) view.findViewById(R.id.etAuthor);
        etQt = (EditText) view.findViewById(R.id.etQt);
        etPrice = (EditText) view.findViewById(R.id.etPrice);
        etAbout = (EditText) view.findViewById(R.id.etAbout);

        bUpdate = (Button) view.findViewById(R.id.bUpdate);
        bDelete = (Button) view.findViewById(R.id.bDelete);
        bCheckISBN = (Button) view.findViewById(R.id.bCheckISBN);

        ivbookPic = (ImageView) view.findViewById(R.id.ivbookPic);

        bCheckISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBooksByISBN(etISBN.getText().toString());
            }
        });

        return view;
    }

    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
    }

    public void  selectBooksByISBN(String isbn)throws SQLException {
        SQLiteDatabase book_prot_db = getActivity().getApplicationContext().openOrCreateDatabase("book_prot_db.db", Context.MODE_PRIVATE,null);
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn =?", new String[]{isbn});
        if (cursor.getCount() > 0) {

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                Toast.makeText(getActivity(), "Record Found", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(getActivity(), "No record found for ISBN="+isbn, Toast.LENGTH_SHORT).show();
        }
    }
}