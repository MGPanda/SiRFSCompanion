package com.example.sirfscompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static MyDB _mydb;
    private static MainActivity _ma;
    private static int _nightMode;
    private static RecyclerView _rv;
    private boolean isRotate;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _ma = this;
        _nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        _mydb = new MyDB(this);
        _mydb.insertTest();
        _rv = findViewById(R.id.recyclerView);
        setAdapter();
    }

    public void addNew(View view) {
        Intent i = new Intent(this, CharacterCreation.class);
        startActivityForResult(i, 0);
    }
    public void darkMode(MenuItem mi) {
        if (_nightMode == Configuration.UI_MODE_NIGHT_NO) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mi.setIcon(getResources().getDrawable(R.drawable.ic_lightbulb_outline_white_24dp));
            _nightMode = Configuration.UI_MODE_NIGHT_YES;
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            mi.setIcon(getResources().getDrawable(R.drawable.ic_lightbulb_outline_black_24dp));
            _nightMode = Configuration.UI_MODE_NIGHT_NO;
        }
    }
    public static MainActivity get_ma() {
        return _ma;
    }
    public static boolean isDarkMode() {
        if (_nightMode == Configuration.UI_MODE_NIGHT_NO) {
            return false;
        } else return true;
    }
    public void setAdapter() {
        Cursor c = MyDB.selectAll();
        if (c != null) {
            RecyclerAdapter ra = new RecyclerAdapter(this, c);
            _rv.setAdapter(ra);
            _rv.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
