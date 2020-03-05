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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) || (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/
        _ma = this;
        _mydb = new MyDB(this);
        //_mydb.insertTest();
        _rv = findViewById(R.id.recyclerView);
        setAdapter();

    }

    public void addNew(View view) {
        Intent i = new Intent(this, CharacterCreation.class);
        startActivityForResult(i, 0);
    }

    public void darkMode(MenuItem mi) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
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
            _ra = new RecyclerAdapter(this);
            _rv.setAdapter(_ra);
            _rv.setLayoutManager(new LinearLayoutManager(this));
            ItemTouchHelper ith = new ItemTouchHelper(new SwipeToDelete(_ra));
            ith.attachToRecyclerView(_rv);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Char c = (Char) data.getSerializableExtra("CHAR");
            _ra.notifyItemInserted(_ra.addNew(c));
        }
    }
}
