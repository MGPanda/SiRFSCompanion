package com.example.sirfscompanion.control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.example.sirfscompanion.instanciables.Char;

public class MainActivity extends AppCompatActivity {
    private static MyDB _mydb;
    private static MainActivity _ma;
    private static RecyclerView _rv;
    private RecyclerAdapter _ra;
    private UserPreferences _up;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _up = new UserPreferences(this);
        if (_up.getNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _ma = this;
        _mydb = new MyDB(this);
        //TODO _mydb.insertTest();
        _rv = findViewById(R.id.recyclerView);
        setAdapter();

    }

    public void addNew(View view) {
        Intent i = new Intent(this, CharacterCreation.class);
        startActivityForResult(i, 0);
    }

    public void darkMode(MenuItem mi) {
        if (!_up.getNightMode()) {
            _up.setNightMode(true);
        } else {
            _up.setNightMode(false);
        }
    }

    public static MainActivity get_ma() {
        return _ma;
    }

    public void setAdapter() {
        _ra = new RecyclerAdapter(this);
        _rv.setAdapter(_ra);
        _rv.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper ith = new ItemTouchHelper(new SwipeToDelete(_ra));
        ith.attachToRecyclerView(_rv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Char c = (Char) data.getSerializableExtra("CHAR");
            _ra.addNew(c);
            _ra.notifyItemInserted(0);
            _rv.smoothScrollToPosition(0);
        }
    }

    public void updateList(Char c, int position) {
        _ra.updateChar(c, position);
        _ra.notifyItemChanged(position);
    }
}
