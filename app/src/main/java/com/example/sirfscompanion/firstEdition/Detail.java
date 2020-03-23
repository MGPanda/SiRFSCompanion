package com.example.sirfscompanion.firstEdition;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sirfscompanion.R;
import com.example.sirfscompanion.control.MainActivity;
import com.example.sirfscompanion.control.MyDB;
import com.example.sirfscompanion.control.SwipeToDelete;
import com.example.sirfscompanion.instanciables.Char;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detail extends AppCompatActivity {
    private Char _c;
    private TextView _detailPV, _detailName, _detailRaceClass, _detailPE, _detailFUEVal, _detailDESVal, _detailPUNVal, _detailINTVal, _detailSABVal, _detailAGIVal, _detailVOLVal, _detailArmor, _detailMarmor;
    private EditText _goldQuantity;
    private CircleImageView _civ;
    private int _pos;
    private RecyclerAdapterInventory _ra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this._c = (Char) getIntent().getSerializableExtra("CHAR");
        this._pos = getIntent().getIntExtra("POSITION", 0);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(_c.getCharName());
        _civ = findViewById(R.id.detailImage);
        _detailName = findViewById(R.id.detailName);
        _detailRaceClass = findViewById(R.id.detailRaceClass);
        _detailFUEVal = findViewById(R.id.detailFUEVal);
        _detailDESVal = findViewById(R.id.detailDESVal);
        _detailPUNVal = findViewById(R.id.detailPUNVal);
        _detailINTVal = findViewById(R.id.detailINTVal);
        _detailSABVal = findViewById(R.id.detailSABVal);
        _detailAGIVal = findViewById(R.id.detailAGIVal);
        _detailVOLVal = findViewById(R.id.detailVOLVal);
        _detailArmor = findViewById(R.id.detailArmor);
        _detailMarmor = findViewById(R.id.detailMarmor);
        _goldQuantity = findViewById(R.id.goldQuantity);
        _goldQuantity.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                _c.setCharGold(Integer.parseInt(_goldQuantity.getText().toString()));
                MyDB.updateGold(_c.getCharId(), _c.getCharGold());
            }
            return false;
        });
        this._detailPV = findViewById(R.id.detailPV);
        this._detailPE = findViewById(R.id.detailPE);
        RecyclerView rv = findViewById(R.id.detailRecycler);
        _ra = new RecyclerAdapterInventory(this, _c);
        rv.setAdapter(_ra);
        rv.setLayoutManager(new LinearLayoutManager(this));
        updateDetail();
    }

    public void updateDetail() {
        Glide.with(this).load(_c.getCharImg()).asBitmap().into(_civ);
        _detailName.setText(_c.getCharName());
        _detailRaceClass.setText(String.format(Locale.getDefault(), "%s %s %d", _c.getCharRace(), _c.getCharClass(), _c.getCharLevel()));
        if (_c.getCharLevel() == 5) findViewById(R.id.detailLevelUp).setVisibility(View.GONE);
        _detailFUEVal.setText(String.valueOf(_c.getCharFue()));
        _detailDESVal.setText(String.valueOf(_c.getCharDes()));
        _detailPUNVal.setText(String.valueOf(_c.getCharPun()));
        _detailINTVal.setText(String.valueOf(_c.getCharInt()));
        _detailSABVal.setText(String.valueOf(_c.getCharSab()));
        _detailAGIVal.setText(String.valueOf(_c.getCharAgi()));
        _detailVOLVal.setText(String.valueOf(_c.getCharVol()));
        _detailPV.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPv(), _c.getCharMaxpv()));
        _detailPE.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPe(), _c.getCharMaxpe()));
        _detailArmor.setText(String.format(Locale.getDefault(), "Armadura: %d (evitará como mínimo %s puntos de daño)", _c.getCharArmor(), Math.floor(_c.getCharArmor() / 5)));
        _detailMarmor.setText(String.format(Locale.getDefault(), "Armadura mágica: %d (evitará como mínimo %s puntos de daño mágico)", _c.getCharMarmor(), Math.floor(_c.getCharMarmor() / 5)));
        _goldQuantity.setText(String.valueOf(_c.getCharGold()));
        MainActivity.get_ma().updateList(_c, _pos);
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
        } else if (view.equals(findViewById(R.id.detailLessPE))) {
            if (_c.getCharPe() != 0) {
                _c.setCharPe(_c.getCharPe() - 1);
                _detailPE.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPe(), _c.getCharMaxpe()));
                MyDB.updatePE(_c.getCharId(), _c.getCharPe());
            }
        } else if (view.equals(findViewById(R.id.subtractGold))) {
            if (_c.getCharGold() != 0) {
                _c.setCharGold(_c.getCharGold() - 1);
                _goldQuantity.setText(String.valueOf(_c.getCharGold()));
                MyDB.updateGold(_c.getCharId(), _c.getCharGold());
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
        } else if (view.equals(findViewById(R.id.detailMorePE))) {
            if (_c.getCharPe() < _c.getCharMaxpe()) {
                _c.setCharPe(_c.getCharPe() + 1);
                _detailPE.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPe(), _c.getCharMaxpe()));
                MyDB.updatePE(_c.getCharId(), _c.getCharPe());
            }
        } else if (view.equals(findViewById(R.id.addGold))) {
            _c.setCharGold(_c.getCharGold() + 1);
            _goldQuantity.setText(String.valueOf(_c.getCharGold()));
            MyDB.updateGold(_c.getCharId(), _c.getCharGold());
        }
    }

    public void levelUp(View view) {
        if (_c.getCharLevel() < 5) {
            final Dialog d = new Dialog(this);
            d.setContentView(R.layout.levelupstats);
            _c.setCharLevel(_c.getCharLevel() + 1);
            ((TextView) d.findViewById(R.id.luLevel)).setText(String.format(Locale.getDefault(), "¡Nivel %d!", _c.getCharLevel()));
            (d.findViewById(R.id.luFUE)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _c.setCharFue(_c.getCharFue() + 1);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            (d.findViewById(R.id.luDES)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _c.setCharDes(_c.getCharDes() + 1);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            (d.findViewById(R.id.luPUN)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _c.setCharPun(_c.getCharPun() + 1);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            (d.findViewById(R.id.luINT)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _c.setCharInt(_c.getCharInt() + 1);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            (d.findViewById(R.id.luSAB)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _c.setCharSab(_c.getCharSab() + 1);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            (d.findViewById(R.id.luAGI)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _c.setCharAgi(_c.getCharAgi() + 1);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            if (_c.getCharRace().equals(getResources().getStringArray(R.array.races1e)[3]) || _c.getCharLevel() == 2 || ((_c.getCharLevel() == 3) && !_c.getCharBonus().contains("VOL2")) || ((_c.getCharLevel() == 4) && !_c.getCharBonus().contains("VOL3")) || ((_c.getCharLevel() == 5) && !_c.getCharBonus().contains("VOL4"))) {
                (d.findViewById(R.id.luVOL)).setVisibility(View.VISIBLE);
            }
            (d.findViewById(R.id.luVOL)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_c.getCharLevel() == 2) _c.setCharBonus(_c.getCharBonus() + " VOL2");
                    else if (_c.getCharLevel() == 3) _c.setCharBonus(_c.getCharBonus() + " VOL3");
                    else if (_c.getCharLevel() == 4) _c.setCharBonus(_c.getCharBonus() + " VOL4");
                    _c.setCharVol(_c.getCharVol() + 1);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            if ((_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10])))
                ((Button) d.findViewById(R.id.luPV)).setText("+8 PV");
            (d.findViewById(R.id.luPV)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10]))
                        _c.setCharMaxpv(_c.getCharMaxpv() + 8);
                    else _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[6]) && _c.getCharSkills().contains(" 5"))
                ((Button) d.findViewById(R.id.luPE)).setText("+8 PE");
            (d.findViewById(R.id.luPE)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[6]) && _c.getCharSkills().contains(" 5"))
                        _c.setCharMaxpe(_c.getCharMaxpe() + 8);
                    else _c.setCharMaxpe(_c.getCharMaxpe() + 5);
                    MyDB.updateChar(_c);
                    updateDetail();
                    d.cancel();
                }
            });
            d.show();
            if (_c.getCharRace().equals(getResources().getStringArray(R.array.races1e)[2]) && (_c.getCharFue() >= 3 || _c.getCharDes() >= 3 || _c.getCharPun() >= 3 || _c.getCharInt() >= 3 || _c.getCharSab() >= 3 ||
                    _c.getCharAgi() >= 3 || _c.getCharVol() >= 3) && !_c.getCharBonus().contains("WECRIT")) {
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                _c.setCharBonus(_c.getCharBonus() + " WECRIT");
            }
            if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[11]) && _c.getCharSkills().contains(" 3")) {
                _c.setCharArmor(_c.getCharArmor() + 3);
            }
            if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[2])) {
                barbDialog();
            }
            chooseSkill();
            MyDB.updateChar(_c);
            updateDetail();
        }
    }

    public void barbDialog() {
        final Dialog barbD = new Dialog(this);
        barbD.setContentView(R.layout.levelupstats);
        ((TextView) barbD.findViewById(R.id.luLevel)).setText("Elige sabiamente, poderoso Bárbaro.");
        (barbD.findViewById(R.id.luFUE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _c.setCharFue(_c.getCharFue() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                barbD.cancel();
            }
        });
        (barbD.findViewById(R.id.luDES)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luPUN)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luINT)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luSAB)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luAGI)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luVOL)).setVisibility(View.GONE);
        ((Button) barbD.findViewById(R.id.luPV)).setText("+3 PV");
        (barbD.findViewById(R.id.luPV)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _c.setCharPv(_c.getCharPv() + 3);
                MyDB.updateChar(_c);
                updateDetail();
                barbD.cancel();
            }
        });
        (barbD.findViewById(R.id.luPE)).setVisibility(View.GONE);
        barbD.show();
    }

    public void chooseSkill() {
        final Dialog skillD = new Dialog(this);
        skillD.setContentView(R.layout.chooseskill);
        String[] classes = getResources().getStringArray(R.array.classes1e);
        int myClass = 0;
        for (int i = 0; i < classes.length; i++) {
            if (_c.getCharClass().equals(classes[i])) myClass = i;
        }
        String[] titles = getResources().getStringArray(R.array.classesSkills1e)[myClass].split("XNEWX");
        String[] desc = getResources().getStringArray(R.array.classesSkillsDesc1e)[myClass].split("XNEWX");
        ((TextView) skillD.findViewById(R.id.luSkillTitle)).setText(String.format(Locale.getDefault(), "Elige una habilidad de nivel %d.", _c.getCharLevel()));
        ((TextView) skillD.findViewById(R.id.luSkillT1)).setText(titles[(_c.getCharLevel() - 1) * 3]);
        ((TextView) skillD.findViewById(R.id.luSkillD1)).setText(Html.fromHtml(desc[(_c.getCharLevel() - 1) * 3]));
        ((TextView) skillD.findViewById(R.id.luSkillT2)).setText(titles[((_c.getCharLevel() - 1) * 3) + 1]);
        ((TextView) skillD.findViewById(R.id.luSkillD2)).setText(Html.fromHtml(desc[((_c.getCharLevel() - 1) * 3) + 1]));
        ((TextView) skillD.findViewById(R.id.luSkillT3)).setText(titles[((_c.getCharLevel() - 1) * 3) + 2]);
        ((TextView) skillD.findViewById(R.id.luSkillD3)).setText(Html.fromHtml(desc[((_c.getCharLevel() - 1) * 3) + 2]));
        ((RadioButton) skillD.findViewById(R.id.luSkill1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[1]) && ((_c.getCharLevel() - 1) * 3) == 9) {
                    _c.setCharCritbonus(_c.getCharCritbonus() + 2);
                    _c.setCharCritdmgbonus(_c.getCharCritdmgbonus() + 100);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[8]) && ((_c.getCharLevel() - 1) * 3) == 9) {
                    _c.setCharVol(_c.getCharVol() + 1);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10]) && ((_c.getCharLevel() - 1) * 3) == 3) {
                    _c.setCharDes(_c.getCharDes() + 1);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10]) && ((_c.getCharLevel() - 1) * 3) == 12) {
                    _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                    _c.setCharVol(_c.getCharVol() + 3);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[11]) && ((_c.getCharLevel() - 1) * 3) == 3) {
                    _c.setCharVol(_c.getCharVol() + 2);
                    _c.setCharArmor(_c.getCharArmor() + 6);
                }
                _c.setCharSkills(_c.getCharSkills() + " " + (_c.getCharLevel() - 1) * 3);
                MyDB.updateChar(_c);
                updateDetail();
                skillD.cancel();
            }
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill2)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[2]) && (((_c.getCharLevel() - 1) * 3) + 1) == 4) {
                    _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[1]) && (((_c.getCharLevel() - 1) * 3) + 1) == 13) {
                    assassinSkill();
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[7]) && (((_c.getCharLevel() - 1) * 3) + 1) == 7) {
                    _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                    _c.setCharCritdmgbonus(_c.getCharCritdmgbonus() + 100);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[8]) && (((_c.getCharLevel() - 1) * 3) + 1) == 13) {
                    _c.setCharMaxpe(_c.getCharMaxpe() + 8);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10]) && (((_c.getCharLevel() - 1) * 3) + 1) == 10) {
                    _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[12]) && (((_c.getCharLevel() - 1) * 3) + 1) == 10) {
                    _c.setCharMaxpe(_c.getCharMaxpe() + 5);
                }
                _c.setCharSkills(_c.getCharSkills() + " " + (((_c.getCharLevel() - 1) * 3) + 1));
                MyDB.updateChar(_c);
                updateDetail();
                skillD.cancel();
            }
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill3)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[4]) && (((_c.getCharLevel() - 1) * 3) + 2) == 5) {
                    _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                    _c.setCharPun(_c.getCharPun() + 2);
                    _c.setCharMaxpe(_c.getCharMaxpe() + 5);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[6]) && (((_c.getCharLevel() - 1) * 3) + 2) == 5) {
                    _c.setCharMaxpe(_c.getCharMaxpe() + 3);
                } else if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[13]) && (((_c.getCharLevel() - 1) * 3) + 2) == 14) {
                    _c.setCharMaxpv(_c.getCharMaxpv() + 10);
                }
                _c.setCharSkills(_c.getCharSkills() + " " + (((_c.getCharLevel() - 1) * 3) + 2));
                MyDB.updateChar(_c);
                updateDetail();
                skillD.cancel();
            }
        });
        skillD.show();
    }

    public void assassinSkill() {
        final Dialog skillD = new Dialog(this);
        skillD.setContentView(R.layout.chooseskill);
        String[] classes = getResources().getStringArray(R.array.classes1e);
        int myClass = 0;
        for (int i = 0; i < classes.length; i++) {
            if (_c.getCharClass().equals(classes[i])) myClass = i;
        }
        String[] titles = getResources().getStringArray(R.array.classesSkills1e)[myClass].split("XNEWX");
        String[] desc = getResources().getStringArray(R.array.classesSkillsDesc1e)[myClass].split("XNEWX");
        int[] possible = new int[4];
        if (_c.getCharSkills().contains(" 3")) {
            possible[0] = 4;
            possible[1] = 5;
        } else if (_c.getCharSkills().contains(" 4")) {
            possible[0] = 3;
            possible[1] = 5;
        } else if (_c.getCharSkills().contains(" 5")) {
            possible[0] = 3;
            possible[1] = 4;
        }
        if (_c.getCharSkills().contains(" 6")) {
            possible[2] = 7;
            possible[3] = 8;
        } else if (_c.getCharSkills().contains(" 7")) {
            possible[2] = 6;
            possible[3] = 8;
        } else if (_c.getCharSkills().contains(" 8")) {
            possible[2] = 6;
            possible[3] = 7;
        }
        ((TextView) skillD.findViewById(R.id.luSkillTitle)).setText("Elige una habilidad de nivel 2 o 3.");
        ((TextView) skillD.findViewById(R.id.luSkillT1)).setText(titles[possible[0]]);
        ((TextView) skillD.findViewById(R.id.luSkillD1)).setText(Html.fromHtml(desc[possible[0]]));
        ((TextView) skillD.findViewById(R.id.luSkillT2)).setText(titles[possible[1]]);
        ((TextView) skillD.findViewById(R.id.luSkillD2)).setText(Html.fromHtml(desc[possible[1]]));
        ((TextView) skillD.findViewById(R.id.luSkillT3)).setText(titles[possible[2]]);
        ((TextView) skillD.findViewById(R.id.luSkillD3)).setText(Html.fromHtml(desc[possible[2]]));
        (skillD.findViewById(R.id.bonusSkill)).setVisibility(View.VISIBLE);
        ((TextView) skillD.findViewById(R.id.luSkillT4)).setText(titles[possible[3]]);
        ((TextView) skillD.findViewById(R.id.luSkillD4)).setText(Html.fromHtml(desc[possible[3]]));
        ((RadioButton) skillD.findViewById(R.id.luSkill1)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            _c.setCharSkills(_c.getCharSkills() + " " + possible[0]);
            updateDetail();
            skillD.cancel();
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill2)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            _c.setCharSkills(_c.getCharSkills() + " " + possible[1]);
            updateDetail();
            skillD.cancel();
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill3)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            _c.setCharSkills(_c.getCharSkills() + " " + possible[2]);
            updateDetail();
            skillD.cancel();
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill4)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            _c.setCharSkills(_c.getCharSkills() + " " + possible[3]);
            updateDetail();
            skillD.cancel();
        });
        skillD.show();
    }

    public void showQR(View view) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(_c.getCharName() + "\n" + _c.getCharAgi(), BarcodeFormat.QR_CODE, 800, 800);
            ImageView iv = new ImageView(this);
            AlertDialog ad = new AlertDialog.Builder(this).setView(iv).create();
            iv.setImageBitmap(bitmap);
            ad.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detailSetImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == 0) {
                try {
                    Uri selectedImage = data.getData();
                    Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    _c.setCharImg(baos.toByteArray());
                    updateDetail();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
    }

    public void addNewItem(View view) {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.additem);
        d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        d.findViewById(R.id.createItemButton).setOnClickListener(v -> {
            String name = ((TextView) (d.findViewById(R.id.createItemName))).getText().toString();
            String desc = ((TextView) (d.findViewById(R.id.createItemDesc))).getText().toString();
            String quantity = ((TextView) (d.findViewById(R.id.createItemQuant))).getText().toString();
            if (_c.getCharInventory().equals("")) {
                _c.setCharInventory(name + "XPARTX" + desc + "XPARTX" + quantity);
            } else
                _c.setCharInventory(_c.getCharInventory() + "XNEWX" + name + "XPARTX" + desc + "XPARTX" + quantity);
            _ra.addNew(name + "XPARTX" + desc + "XPARTX" + quantity);
            MyDB.updateInv(_c.getCharId(), _c.getCharInventory());
            d.dismiss();
        });
        d.show();
    }
}
