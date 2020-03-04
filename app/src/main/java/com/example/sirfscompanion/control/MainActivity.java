package com.example.sirfscompanion.control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sirfscompanion.R;
import com.example.sirfscompanion.firstEdition.RecyclerAdapter;
import com.example.sirfscompanion.firstEdition.CharacterCreation;

public class MainActivity extends AppCompatActivity {
    private static MyDB _mydb;
    private static MainActivity _ma;
    private static RecyclerView _rv;

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
        _mydb = new MyDB(this);
        _mydb.insertTest();
        _rv = findViewById(R.id.recyclerView);
        _rv.setHasFixedSize(true);
        _rv.setItemViewCacheSize(20);
        _rv.setDrawingCacheEnabled(true);
        _rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        setAdapter();
    }

    public void addNew(View view) {
        Intent i = new Intent(this, CharacterCreation.class);
        startActivityForResult(i, 0);
    }

    public void darkMode(MenuItem mi) {
        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static MainActivity get_ma() {
        return _ma;
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
