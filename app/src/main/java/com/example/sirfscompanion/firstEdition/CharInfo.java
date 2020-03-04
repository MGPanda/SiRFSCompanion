package com.example.sirfscompanion.firstEdition;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
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
            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
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
                tv1.setTypeface(Typeface.DEFAULT);
                tr.addView(tv1);
                TextView tv2 = new TextView(this);
                tv2.setText(aux[1]);
                tv2.setTypeface(Typeface.DEFAULT);
                tr.addView(tv2);
                tl.addView(tr);
                tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5f));
            }
        } else if (c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[4])) {
            TextView title = new TextView(this);
            layout.addView(title);
            title.setText(R.string.cazadorMascotas);
            title.setTextSize(18);
            title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            String[] mascotas = getResources().getStringArray(R.array.cazadorMascotas);
            for (int i = 0; i < mascotas.length/6; i++) {
                View v = LayoutInflater.from(this).inflate(R.layout.cazadormascotas, null);
                int pos = i*6;
                layout.addView(v);
                ((TextView) v.findViewById(R.id.cazador01)).setText(mascotas[pos]);
                ((TextView) v.findViewById(R.id.cazador02)).setText(Html.fromHtml(mascotas[pos+1]));
                ((TextView) v.findViewById(R.id.cazador03)).setText(String.format("%s | %s", mascotas[pos + 2].split(" ")[0], mascotas[pos + 2].split(" ")[1]));
                ((TextView) v.findViewById(R.id.cazador04)).setText(String.format("%s | %s", mascotas[pos + 2].split(" ")[2], mascotas[pos + 2].split(" ")[3]));
                ((TextView) v.findViewById(R.id.cazador05)).setText(String.format("%s | %s", mascotas[pos + 2].split(" ")[4], mascotas[pos + 2].split(" ")[5]));
                ((TextView) v.findViewById(R.id.cazador06)).setText(String.format("%s | %s", mascotas[pos + 2].split(" ")[6], mascotas[pos + 2].split(" ")[7]));
                ((TextView) v.findViewById(R.id.cazador07)).setText(String.format("%s | %s", mascotas[pos + 2].split(" ")[8], mascotas[pos + 2].split(" ")[9]));
                ((TextView) v.findViewById(R.id.cazador08)).setText(String.format("%s | %s", mascotas[pos + 2].split(" ")[10], mascotas[pos + 2].split(" ")[11]));
                ((TextView) v.findViewById(R.id.cazador09)).setText(String.format("%s | %s", mascotas[pos + 2].split(" ")[12], mascotas[pos + 2].split(" ")[13]));
                ((TextView) v.findViewById(R.id.cazador10)).setText(mascotas[pos+3].split(" ")[0]);
                ((TextView) v.findViewById(R.id.cazador11)).setText(String.format("%s | %s", mascotas[pos + 3].split(" ")[1], mascotas[pos + 3].split(" ")[2]));
                ((TextView) v.findViewById(R.id.cazador12)).setText(String.format("%s | %s", mascotas[pos + 3].split(" ")[3], mascotas[pos + 3].split(" ")[4]));
                ((TextView) v.findViewById(R.id.cazador13)).setText(String.format("%s | %s", mascotas[pos + 3].split(" ")[5], mascotas[pos + 3].split(" ")[6]));
                ((TextView) v.findViewById(R.id.cazador14)).setText(String.format("%s | %s", mascotas[pos + 3].split(" ")[7], mascotas[pos + 3].split(" ")[8]));
                ((TextView) v.findViewById(R.id.cazador15)).setText(Html.fromHtml(mascotas[pos+4].split("XNEWX")[0]));
                ((TextView) v.findViewById(R.id.cazador16)).setText(mascotas[pos+5].split("XNEWX")[0]);
                ((TextView) v.findViewById(R.id.cazador17)).setText(Html.fromHtml(mascotas[pos+4].split("XNEWX")[1]));
                ((TextView) v.findViewById(R.id.cazador18)).setText(mascotas[pos+5].split("XNEWX")[1]);
            }

        } else if (c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[5])) {
            TextView title = new TextView(this);
            layout.addView(title);
            title.setText(R.string.druidaTransformaciones);
            title.setTextSize(18);
            title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            String[] transformaciones = getResources().getStringArray(R.array.druidaTransformaciones);
            View v = LayoutInflater.from(this).inflate(R.layout.druidatransformaciones, null);
            layout.addView(v);
            ((TextView) v.findViewById(R.id.druida1)).setText(transformaciones[0]);
            ((TextView) v.findViewById(R.id.druida2)).setText(transformaciones[1]);
            ((TextView) v.findViewById(R.id.druida3)).setText(transformaciones[2]);
            ((TextView) v.findViewById(R.id.druida4)).setText(transformaciones[3]);
            ((TextView) v.findViewById(R.id.druida5)).setText(transformaciones[4]);
            ((TextView) v.findViewById(R.id.druida6)).setText(transformaciones[5]);
        }
    }
}
