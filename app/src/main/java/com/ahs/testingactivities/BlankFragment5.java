package com.ahs.testingactivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BlankFragment5 extends Fragment {

    Button bInsert, bCheckPrice, bCheckISBN;
    DBHelper book_prot_db;
    EditText etISBN, etCustomerName, etLocation, etPhone, etQt, etPrice, etAbout;

    BlankFragment5() {
        // Required empty public constructor
    }

    public static BlankFragment5 newInstance(String param1, String param2) {
        BlankFragment5 fragment = new BlankFragment5();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank5, container, false);
        book_prot_db = new DBHelper(getActivity());

        etISBN = (EditText) view.findViewById(R.id.etISBN);
        etCustomerName = (EditText) view.findViewById(R.id.etCustomerName);
        etLocation = (EditText) view.findViewById(R.id.etLocation);
        etQt = (EditText) view.findViewById(R.id.etQt);
        etPrice = (EditText) view.findViewById(R.id.etPrice);
        etPhone = (EditText) view.findViewById(R.id.etPhone);

        etAbout = (EditText) view.findViewById(R.id.etAbout);

        bInsert = (Button) view.findViewById(R.id.bInsert);
        bInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ISBN = etISBN.getText().toString();
                String CustomerName = etCustomerName.getText().toString();
                String Location = etLocation.getText().toString();
                String Phone = etPhone.getText().toString();
                int Qt = Integer.parseInt(etQt.getText().toString());
                float Price = Float.parseFloat(etPrice.getText().toString());


                long yourmilliseconds = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date resultdate = new Date(yourmilliseconds);
                String date = sdf.format(resultdate);
                System.out.println(sdf.format(resultdate));


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirmation");
                builder.setMessage("Confirm Order Creation\n" + etAbout.getText().toString());
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (ISBN.equals("") || CustomerName.equals("") || Location.equals("") || Qt == 0 || Price == 0 || Phone.equals("")) {
                                    Toast.makeText(getActivity(), "Fill all fields before clicking insert", Toast.LENGTH_SHORT).show();
                                } else {

                                    Boolean insertResult = book_prot_db.insertOrder(ISBN, CustomerName, Location, Phone, date, Qt, Price);
                                    if (insertResult == true) {
                                        Toast.makeText(getActivity(), "New Order Has been created", Toast.LENGTH_SHORT).show();
                                        ClearET();
                                    } else {
                                        Toast.makeText(getActivity(), "Error, the order has not been created", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        bCheckPrice = (Button) view.findViewById(R.id.bCheckPrice);
        bCheckPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ISBN = etISBN.getText().toString();
                int Qt = Integer.parseInt(etQt.getText().toString());
                getPriceFromQt(ISBN, Qt);
            }
        });
        bCheckISBN = (Button) view.findViewById(R.id.bCheckISBN);
        bCheckISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etAbout.setText(selectBooksByISBN(etISBN.getText().toString()));
            }
        });
        return view;
    }

    public void ClearET() {
        etISBN.setText(null);
        etCustomerName.setText(null);
        etLocation.setText(null);
        etQt.setText(null);
        etPrice.setText(null);
        etPhone.setText(null);
        etAbout.setText(null);
    }

    public String selectBooksByISBN(String isbn) throws SQLException {
        SQLiteDatabase book_prot_db = getActivity().getApplicationContext().openOrCreateDatabase("book_prot_db.db", Context.MODE_PRIVATE, null);
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn= ?", new String[]{isbn});
        String RequestedBook = null;
        if (cursor.getCount() > 0) {

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                Toast.makeText(getActivity(), "Record Found", Toast.LENGTH_SHORT).show();

                RequestedBook = "Title=" + cursor.getString(1) +
                        "\nAuthor=" + cursor.getString(2) +
                        "\nAvailable Qt.=" + cursor.getString(3) + " nos" +
                        "\nUnit Price=" + cursor.getString(4) + " OMR";
                System.out.println(RequestedBook);
                return RequestedBook;

            }


            cursor.close();
        } else {


            cursor.close();
            Toast.makeText(getActivity(), "No record found for ISBN=" + isbn, Toast.LENGTH_SHORT).show();
            return RequestedBook;
        }
        return RequestedBook;

    }

    public void getPriceFromQt(String isbn, int Qt) throws SQLException {
        SQLiteDatabase book_prot_db = getActivity().getApplicationContext().openOrCreateDatabase("book_prot_db.db", Context.MODE_PRIVATE, null);
        Cursor cursor = book_prot_db.rawQuery("select * from books where isbn =?", new String[]{isbn});
        float FinalPrice = 0.0f;
        if (cursor.getCount() > 0) {

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                Toast.makeText(getActivity(), "Record Found", Toast.LENGTH_SHORT).show();

                FinalPrice = Qt * cursor.getFloat(4);

                etPrice.setText(String.valueOf(FinalPrice));

            }


            cursor.close();
        } else {

            cursor.close();
            Toast.makeText(getActivity(), "No record found for ISBN=" + isbn, Toast.LENGTH_SHORT).show();
        }
    }


}