package com.example.sirfscompanion.firstEdition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sirfscompanion.R;
import com.example.sirfscompanion.control.MyDB;
import com.example.sirfscompanion.instanciables.Char;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detail extends AppCompatActivity {
    private Char _c;
    private TextView _detailPV;
    private TextView _detailPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this._c = (Char) getIntent().getSerializableExtra("CHAR");
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(_c.getCharName());
        CircleImageView _civ = findViewById(R.id.detailImage);
        TextView _detailName = findViewById(R.id.detailName);
        TextView _detailRaceClass = findViewById(R.id.detailRaceClass);
        TextView _detailFUEVal = findViewById(R.id.detailFUEVal);
        TextView _detailDESVal = findViewById(R.id.detailDESVal);
        TextView _detailPUNVal = findViewById(R.id.detailPUNVal);
        TextView _detailINTVal = findViewById(R.id.detailINTVal);
        TextView _detailSABVal = findViewById(R.id.detailSABVal);
        TextView _detailAGIVal = findViewById(R.id.detailAGIVal);
        TextView _detailVOLVal = findViewById(R.id.detailVOLVal);
        this._detailPV = findViewById(R.id.detailPV);
        this._detailPE = findViewById(R.id.detailPE);
        Glide.with(this).load(_c.getCharImg()).asBitmap().into(_civ);
        _detailName.setText(_c.getCharName());
        _detailRaceClass.setText(String.format(Locale.getDefault(), "%s %s %d", _c.getCharRace(), _c.getCharClass(), _c.getCharLevel()));
        _detailFUEVal.setText(String.valueOf(_c.getCharFue()));
        _detailDESVal.setText(String.valueOf(_c.getCharDes()));
        _detailPUNVal.setText(String.valueOf(_c.getCharPun()));
        _detailINTVal.setText(String.valueOf(_c.getCharInt()));
        _detailSABVal.setText(String.valueOf(_c.getCharSab()));
        _detailAGIVal.setText(String.valueOf(_c.getCharAgi()));
        _detailVOLVal.setText(String.valueOf(_c.getCharVol()));
        _detailPV.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPv(), _c.getCharMaxpv()));
        _detailPE.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPe(), _c.getCharMaxpe()));
    }

    public void detailInfo(View view) {
        Intent i = new Intent(this, CharInfo.class);
        i.putExtra("CHAR", _c);
        startActivity(i);
    }

    public void subtract(View view) {
        if (view.equals(findViewById(R.id.detailLessPV))) {
            if (_c.getCharPv() != 0) {
                _c.setCharPv(_c.getCharPv() - 1);
                _detailPV.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPv(), _c.getCharMaxpv()));
                MyDB.updatePV(_c.getCharId(), _c.getCharPv());
            }
        } else {
            if (_c.getCharPe() != 0) {
                _c.setCharPe(_c.getCharPe() - 1);
                _detailPE.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPe(), _c.getCharMaxpe()));
                MyDB.updatePE(_c.getCharId(), _c.getCharPe());
            }
        }
    }

    public void add(View view) {
        if (view.equals(findViewById(R.id.detailMorePV))) {
            if (_c.getCharPv() < _c.getCharMaxpv()) {
                _c.setCharPv(_c.getCharPv() + 1);
                _detailPV.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPv(), _c.getCharMaxpv()));
                MyDB.updatePV(_c.getCharId(), _c.getCharPv());
            }
        } else {
            if (_c.getCharPe() < _c.getCharMaxpe()) {
                _c.setCharPe(_c.getCharPe() + 1);
                _detailPE.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPe(), _c.getCharMaxpe()));
                MyDB.updatePE(_c.getCharId(), _c.getCharPe());
            }
        }
    }
}
