package com.ahs.testingactivities;

import android.content.Intent;
import android.graphics.Bitmap;
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

public class BlankFragment2 extends Fragment implements View.OnClickListener {
    ImageView ivbookPic1;

    EditText etISBN, etTitle, etAuthor, etQt, etPrice, etAbout;
    Button bInsert;

    DBHelper book_prot_db;

    public BlankFragment2() {
        // Required empty public constructor
    }

    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();
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
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);
        ivbookPic1 = (ImageView) view.findViewById(R.id.ivbookPic);

        etISBN = (EditText) view.findViewById(R.id.etISBN);
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etAuthor = (EditText) view.findViewById(R.id.etAuthor);
        etQt = (EditText) view.findViewById(R.id.etQt);
        etPrice = (EditText) view.findViewById(R.id.etPrice);
        etAbout = (EditText) view.findViewById(R.id.etAbout);

        book_prot_db = new DBHelper(getActivity());

        bInsert = (Button) view.findViewById(R.id.bInsert);
        ivbookPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Choose a Book Cover", Toast.LENGTH_SHORT).show();
                //get image from gallery
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });
        bInsert.setOnClickListener(new View.OnClickListener() {
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
                    Bitmap bitmap = ((BitmapDrawable) ivbookPic1.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] cover = baos.toByteArray();


                    if (ISBN.equals("") || Title.equals("") || Author.equals("") || Qt == 0 || Price == 0 || About.equals("")) {
                        Toast.makeText(getActivity(), "Fill all fields before clicking insert", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean bookExist = book_prot_db.checkISBN(ISBN);
                        if (bookExist == false) {
                            Boolean insertResult = book_prot_db.insertBook(ISBN, Title, Author, Qt, Price, About, cover);
                            if (insertResult == true) {
                                Toast.makeText(getActivity(), "[" + ISBN + "]" + Title + "has been added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Error, the book has not been added", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Book already registered \n Please add another", Toast.LENGTH_SHORT).show();
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
                    ivbookPic1.setImageURI(selectedImage);
                }

                break;
        }
    }

    @Override
    public void onClick(View v) {


    }
}