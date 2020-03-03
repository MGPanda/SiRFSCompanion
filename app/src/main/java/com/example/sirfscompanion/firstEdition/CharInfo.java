package com.example.sirfscompanion.firstEdition;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sirfscompanion.R;
import com.example.sirfscompanion.control.MainActivity;
import com.example.sirfscompanion.instanciables.Char;

public class CharInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_info);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(R.string.infoChar);
        Char c = (Char) getIntent().getSerializableExtra("CHAR");
        RecyclerView rv = findViewById(R.id.recyclerViewInfo);
        RecyclerAdapterInfo rai = new RecyclerAdapterInfo(this, c);
        rv.setAdapter(rai);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ViewGroup layout = findViewById(R.id.infoLayout);
        if (c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[0])) {
            TextView title = new TextView(this);
            layout.addView(title);
            title.setText(R.string.listaPociones);
            title.setTextSize(18);
            title.setTypeface(null, Typeface.BOLD);
            String[] pociones = getResources().getStringArray(R.array.alquimistaPociones);
            TableLayout tl = new TableLayout(this);
            layout.addView(tl);
            tl.getLayoutParams().width= LinearLayout.LayoutParams.MATCH_PARENT;
            tl.getLayoutParams().height= LinearLayout.LayoutParams.WRAP_CONTENT;
            for (int i = 0; i < pociones.length; i++) {
                String[] aux = pociones[i].split("XNEWX");
                TableRow tr = new TableRow(this);
                TextView tv1 = new TextView(this);
                tv1.setText(aux[0]);
                tr.addView(tv1);
                TextView tv2 = new TextView(this);
                tv2.setText(aux[1]);
                tr.addView(tv2);
                tl.addView(tr);
                tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5f));
            }
        } else if (c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[4])) {

        }
    }
}
