package com.ahs.testingactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ahs.testingactivities.Drawer_Activity;

public class MainActivity extends AppCompatActivity {
    EditText etUN, etPW;
    //Button bEnter;

    DBHelper book_prot_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUN = (EditText) findViewById(R.id.etUN_login);
        etPW = (EditText) findViewById(R.id.etPW_login);
       // bEnter = (Button) findViewById(R.id.bEnter);
        book_prot_db = new DBHelper(this);


    }

    public void Login(View v) {
            String un = etUN.getText().toString();
            String pw = etPW.getText().toString();
            if (un.equals("") || pw.equals("")) {
                Toast.makeText(MainActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
            } else {
                Boolean result = book_prot_db.check_UN_PW(un, pw);
                if (result == true) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                  /*  Drawer_Activity da = new Drawer_Activity();
                    da.UserName=un;
                    */

                   Intent intent = new Intent(MainActivity.this, Drawer_Activity.class);
                   startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
    }

    public void Register(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        startActivity(intent);
    }

}