package com.ahs.testingactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    EditText etUN, etPW, etConfirmPW;
    Button bRegister;
    DBHelper book_prot_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        etUN = (EditText) findViewById(R.id.etUN);
        etPW = (EditText) findViewById(R.id.etPW);
        etConfirmPW = (EditText) findViewById(R.id.etConfirmPW);

        bRegister = (Button) findViewById(R.id.bEnter);

        book_prot_db = new DBHelper(this);

        bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String un = etUN.getText().toString();
                String pw = etPW.getText().toString();
                String cPW = etConfirmPW.getText().toString();

                if (un.equals("") || pw.equals("") || cPW.equals("")) {
                    Toast.makeText(MainActivity3.this, "Fill all fields before clicking Register", Toast.LENGTH_SHORT).show();
                } else {
                    if (pw.equals(cPW)) {
                        Boolean userExist = book_prot_db.checkusername(un);
                        if (userExist == false) {
                            Boolean regResult = book_prot_db.insertUser(un, pw);
                            if (regResult == true) {
                                Toast.makeText(MainActivity3.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity3.this, "Registration Unsuccessful \n UN=" + un + "\n pw=" + pw, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity3.this, "User already registered \n Please Log in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity3.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
}