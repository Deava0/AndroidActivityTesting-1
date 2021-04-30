package com.ahs.testingactivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.ParseException;

public class MainActivity2 extends AppCompatActivity {
    ImageView ivbookPic1;

    EditText etISBN, etTitle, etAuthor, etQt, etPrice, etAbout;
    Button bInsert;

    DBHelper book_prot_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ivbookPic1 = (ImageView) findViewById(R.id.ivbookPic);

        etISBN = (EditText) findViewById(R.id.etISBN);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etQt = (EditText) findViewById(R.id.etQt);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etAbout = (EditText) findViewById(R.id.etAbout);

        book_prot_db = new DBHelper(this);
        bInsert = (Button) findViewById(R.id.bInsert);
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
                        Toast.makeText(MainActivity2.this, "Fill all fields before clicking insert", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean bookExist = book_prot_db.checkISBN(ISBN);
                        if (bookExist == false) {
                            Boolean insertResult = book_prot_db.insertBook(ISBN, Title, Author, Qt, Price, About, cover);
                            if (insertResult == true) {
                                Toast.makeText(MainActivity2.this, "[" + ISBN + "]" + Title + "has been added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity2.this, "Error, the book has not been added", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(MainActivity2.this, "Book already registered \n Please add another", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception ex) {
                    Toast.makeText(MainActivity2.this, ex.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println(ex);
                }

            }
        });


        ivbookPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Choose a Books' Cover", Toast.LENGTH_LONG).show();
                //get image from gallery
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);

            }
        });


    }

    float getFloatFrom(EditText txt) {
        try {
            return NumberFormat.getInstance().parse(txt.getText().toString()).floatValue();
        } catch (ParseException e) {
            return 0.0f;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    ivbookPic1.setImageURI(selectedImage);
                }

                break;
        }
    }

}



