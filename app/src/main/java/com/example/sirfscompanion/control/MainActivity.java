package com.example.sirfscompanion.control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sirfscompanion.R;
import com.example.sirfscompanion.instanciables.Char;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {
    private static MyDB _mydb;
    private static MainActivity _ma;
    private static RecyclerView _rv;
    private RecyclerAdapter _ra;
    private UserPreferences _up;
    private FloatingActionsMenu _fam;

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
        _fam = findViewById(R.id.selectEdition);
        setupFam();
        setAdapter();

    }

    public void setupFam() {
        FloatingActionButton firstEdition = new FloatingActionButton(this);
        firstEdition.setTitle(getString(R.string.firstEdition));
        firstEdition.setIcon(R.drawable.first_edition_icon);
        firstEdition.setSize(FloatingActionButton.SIZE_MINI);
        firstEdition.setColorNormal(R.color.colorPrimary);
        firstEdition.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CharacterCreation.class);
            i.putExtra("EDITION", 1);
            startActivityForResult(i, 0);
            _fam.collapse();
        });
        _fam.addButton(firstEdition);
        FloatingActionButton secondEdition = new FloatingActionButton(this);
        secondEdition.setTitle(getString(R.string.secondEdition));
        secondEdition.setIcon(R.drawable.second_edition_icon);
        secondEdition.setSize(FloatingActionButton.SIZE_MINI);
        secondEdition.setColorNormal(R.color.colorPrimary);
        secondEdition.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CharacterCreation.class);
            i.putExtra("EDITION", 2);
            startActivityForResult(i, 0);
            _fam.collapse();
        });
        _fam.addButton(secondEdition);
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
