package com.example.ivansv.softomatetest.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ivansv.softomatetest.controller.DBHelper;
import com.example.ivansv.softomatetest.R;
import com.example.ivansv.softomatetest.model.TextLanguage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<TextLanguage> textLanguages = new ArrayList<>();
    private Fragment listFragment = new ListFragment();
    private Fragment textFragment = new TextFragment();
    public static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContainer, textFragment)
                .commit();
        dbHelper = new DBHelper(this);
        if (textLanguages.size() == 0) {
            Cursor cursor = dbHelper.getReadableDatabase().query(DBHelper.DATABASE_TABLE, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
                    int languageIndex = cursor.getColumnIndex(DBHelper.KEY_LANGUAGE);
                    do {
                        TextLanguage textLanguage = new TextLanguage();
                        textLanguage.setText(cursor.getString(textIndex));
                        textLanguage.setLanguage(cursor.getString(languageIndex));
                        textLanguages.add(textLanguage);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            dbHelper.close();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, textFragment)
                    .commit();
        } else if (id == R.id.restore) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, listFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
