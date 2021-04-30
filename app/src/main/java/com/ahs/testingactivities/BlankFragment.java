package com.ahs.testingactivities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.ParseException;

public class BlankFragment extends Fragment {
    EditText etISBN, etTitle, etAuthor, etQt, etPrice, etAbout;
    Button bUpdate, bDelete, bCheckISBN;
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

    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
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

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        book_prot_db = new DBHelper(getActivity());

        etISBN = (EditText) view.findViewById(R.id.etISBN);
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etAuthor = (EditText) view.findViewById(R.id.etAuthor);
        etQt = (EditText) view.findViewById(R.id.etQt);
        etPrice = (EditText) view.findViewById(R.id.etPrice);
        etAbout = (EditText) view.findViewById(R.id.etAbout);

        ivbookPic = (ImageView) view.findViewById(R.id.ivbookPic);

        bCheckISBN = (Button) view.findViewById(R.id.bCheckISBN);
        bCheckISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBooksByISBN(etISBN.getText().toString());
            }
        });
        bDelete = (Button) view.findViewById(R.id.bDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ISBN = etISBN.getText().toString();
                    String Title = etTitle.getText().toString();

                    if (ISBN.equals("")) {
                        Toast.makeText(getActivity(), "Fill all fields before clicking delete", Toast.LENGTH_SHORT).show();
                    } else {

                        Boolean updateResult = book_prot_db.deleteBook(ISBN);
                        if (updateResult == true) {
                            Toast.makeText(getActivity(), "[" + ISBN + "]" + Title + "has been deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Error, the book has not been deleted", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println(ex);
                }
            }
        });
        bUpdate = (Button) view.findViewById(R.id.bUpdate);
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ISBN = etISBN.getText().toString();
                    String Title = etTitle.getText().toString();
                    String Author = etAuthor.getText().toString();
                    int Qt = Integer.parseInt(etQt.getText().toString());
                    float Price = getFloatFrom(etPrice);
                    String About = etAbout.getText().toString();

                    //bitmap->img->byte
                    Bitmap bitmap = ((BitmapDrawable) ivbookPic.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 4, baos);//quality ???
                    byte[] cover = baos.toByteArray();


                    if (ISBN.equals("") || Title.equals("") || Author.equals("") || Qt == 0 || Price == 0 || About.equals("")) {
                        Toast.makeText(getActivity(), "Fill all fields before clicking update", Toast.LENGTH_SHORT).show();
                    } else {

                        Boolean updateResult = book_prot_db.updateBook(ISBN, Title, Author, Qt, Price, About, cover);
                        if (updateResult == true) {
                            Toast.makeText(getActivity(), "[" + ISBN + "]" + Title + "has been updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Error, the book has not been updated", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println(ex);
                }
            }
        });

        return view;
    }

    float getFloatFrom(EditText txt) {
        try {
            return NumberFormat.getInstance().parse(txt.getText().toString()).floatValue();
        } catch (ParseException e) {
            return 0.0f;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    ivbookPic.setImageURI(selectedImage);
                }

                break;
        }
    }

    public void selectBooksByISBN(String isbn) throws SQLException {
        SQLiteDatabase book_prot_db = getActivity().getApplicationContext().openOrCreateDatabase("book_prot_db.db", Context.MODE_PRIVATE, null);
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn =?", new String[]{isbn});
        if (cursor.getCount() > 0) {

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                Toast.makeText(getActivity(), "Record Found", Toast.LENGTH_SHORT).show();

                etTitle.setText(cursor.getString(1));
                etAuthor.setText(cursor.getString(2));
                etQt.setText(cursor.getString(3));
                etPrice.setText(cursor.getString(4));
                etAbout.setText(cursor.getString(5));
                setImageViewWithByteArray(ivbookPic, cursor.getBlob(6));

            }


            cursor.close();
        } else {

            cursor.close();
            Toast.makeText(getActivity(), "No record found for ISBN=" + isbn, Toast.LENGTH_SHORT).show();
        }
    }
}