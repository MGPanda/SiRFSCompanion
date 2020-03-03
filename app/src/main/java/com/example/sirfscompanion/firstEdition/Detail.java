package com.example.sirfscompanion.firstEdition;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sirfscompanion.control.MainActivity;
import com.example.sirfscompanion.R;
import com.example.sirfscompanion.instanciables.Char;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detail extends AppCompatActivity {
    private Char _c;
    private CircleImageView _civ;
    private TextView _detailName, _detailRaceClass, _detailFUEVal, _detailDESVal, _detailPUNVal, _detailINTVal, _detailSABVal, _detailAGIVal, _detailVOLVal, _detailPV, _detailPE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this._c = (Char) getIntent().getSerializableExtra("CHAR");
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(_c.getCharName());
        this._civ = findViewById(R.id.detailImage);
        this._detailName = findViewById(R.id.detailName);
        this._detailRaceClass = findViewById(R.id.detailRaceClass);
        this._detailFUEVal = findViewById(R.id.detailFUEVal);
        this._detailDESVal = findViewById(R.id.detailDESVal);
        this._detailPUNVal = findViewById(R.id.detailPUNVal);
        this._detailINTVal = findViewById(R.id.detailINTVal);
        this._detailSABVal = findViewById(R.id.detailSABVal);
        this._detailAGIVal = findViewById(R.id.detailAGIVal);
        this._detailVOLVal = findViewById(R.id.detailVOLVal);
        this._detailPV = findViewById(R.id.detailPV);
        this._detailPE = findViewById(R.id.detailPE);
        _civ.setImageBitmap(BitmapFactory.decodeByteArray(_c.getCharImg(), 0, _c.getCharImg().length));
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
}
