package com.ahs.testingactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Drawer_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    public String UserName="Should be a username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawer = findViewById(R.id.drawer_layout);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_Cont, new BlankFragment2()).commit();
       /* NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.NavHeader_UN);
        navUsername.setText(UserName);*/
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_addBook: {

                getSupportFragmentManager().beginTransaction().replace(R.id.frag_Cont, new BlankFragment2()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_ModifyBook: {

                getSupportFragmentManager().beginTransaction().replace(R.id.frag_Cont, new BlankFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_BookList: {

                getSupportFragmentManager().beginTransaction().replace(R.id.frag_Cont, new BlankFragment3()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
    }
}