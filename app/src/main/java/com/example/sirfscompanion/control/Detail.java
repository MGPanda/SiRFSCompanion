package com.example.sirfscompanion.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sirfscompanion.R;
import com.example.sirfscompanion.instanciables.Char;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detail extends AppCompatActivity {
    private Char _c;
    private TextView _detailPV, _detailName, _detailRaceClass, _detailPE, _detailFUEVal, _detailDESVal, _detailPUNVal, _detailINTVal, _detailSABVal, _detailAGIVal, _detailVOLVal, _detailENCVal, _detailArmor, _detailMarmor;
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
        if (_c.getCharEdition().equals("1"))
            ((com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.floatingActionButton2)).setImageResource(R.drawable.first_edition_icon);
        else if (_c.getCharEdition().equals("2"))
            ((com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.floatingActionButton2)).setImageResource(R.drawable.second_edition_icon);
        _civ = findViewById(R.id.detailImage);
        _detailName = findViewById(R.id.detailName);
        _detailRaceClass = findViewById(R.id.detailRaceClass);
        if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[2])) {
            findViewById(R.id.detailArmorLL1).setVisibility(View.GONE);
            findViewById(R.id.detailArmorLL2).setVisibility(View.GONE);
        }
        _detailFUEVal = findViewById(R.id.detailFUEVal);
        _detailDESVal = findViewById(R.id.detailDESVal);
        _detailPUNVal = findViewById(R.id.detailPUNVal);
        _detailINTVal = findViewById(R.id.detailINTVal);
        _detailSABVal = findViewById(R.id.detailSABVal);
        _detailAGIVal = findViewById(R.id.detailAGIVal);
        _detailVOLVal = findViewById(R.id.detailVOLVal);
        _detailENCVal = findViewById(R.id.detailENCVal);
        if (_c.getCharEdition().equals("1"))
            findViewById(R.id.detailEncLayout).setVisibility(View.GONE);
        else ((TextView) findViewById(R.id.detailPUN)).setText("PRE");
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

    public void updateWeapons() {
        MyDB.updateWeapons(_c.getCharId(), _c.getCharWeapons());
        String weaponsAux = _c.getCharWeapons();
        weaponsAux = weaponsAux.replace("FUE", _detailFUEVal.getText().toString());
        weaponsAux = weaponsAux.replace("DES", _detailDESVal.getText().toString());
        weaponsAux = weaponsAux.replace("PUN", _detailPUNVal.getText().toString());
        weaponsAux = weaponsAux.replace("PRE", _detailPUNVal.getText().toString());
        weaponsAux = weaponsAux.replace("INT", _detailINTVal.getText().toString());
        weaponsAux = weaponsAux.replace("SAB", _detailSABVal.getText().toString());
        weaponsAux = weaponsAux.replace("AGI", _detailAGIVal.getText().toString());
        weaponsAux = weaponsAux.replace("VOL", _detailVOLVal.getText().toString());
        weaponsAux = weaponsAux.replace("ENC", _detailENCVal.getText().toString());
        String[] myWeapons = weaponsAux.split("XNEWX");
        ((TextView) findViewById(R.id.weap0name)).setText(myWeapons[0].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.weap0desc)).setText(myWeapons[0].split("XPARTX")[1]);
        ((TextView) findViewById(R.id.weap1name)).setText(myWeapons[1].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.weap1desc)).setText(myWeapons[1].split("XPARTX")[1]);
        ((TextView) findViewById(R.id.weap2name)).setText(myWeapons[2].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.weap2desc)).setText(myWeapons[2].split("XPARTX")[1]);
        ((TextView) findViewById(R.id.weap3name)).setText(myWeapons[3].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.weap3desc)).setText(myWeapons[3].split("XPARTX")[1]);
    }

    public void updateEquipment() {
        MyDB.updateEquipment(_c.getCharId(), _c.getCharEquip());
        String[] equipment = _c.getCharEquip().split("XNEWX");
        ((TextView) findViewById(R.id.equip0name)).setText(equipment[0].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.equip0desc)).setText(equipment[0].split("XPARTX")[1]);
        ((TextView) findViewById(R.id.equip1name)).setText(equipment[1].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.equip1desc)).setText(equipment[1].split("XPARTX")[1]);
        ((TextView) findViewById(R.id.equip2name)).setText(equipment[2].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.equip2desc)).setText(equipment[2].split("XPARTX")[1]);
        ((TextView) findViewById(R.id.equip3name)).setText(equipment[3].split("XPARTX")[0]);
        ((TextView) findViewById(R.id.equip3desc)).setText(equipment[3].split("XPARTX")[1]);
        String[] armor0 = ((TextView) findViewById(R.id.equip0desc)).getText().toString().split(" / ");
        String[] armor1 = ((TextView) findViewById(R.id.equip1desc)).getText().toString().split(" / ");
        String[] armor2 = ((TextView) findViewById(R.id.equip2desc)).getText().toString().split(" / ");
        String[] armor3 = ((TextView) findViewById(R.id.equip3desc)).getText().toString().split(" / ");
        int shieldVal = 0, shieldValM = 0;
        String[] weapons = _c.getCharWeapons().split("XNEWX");
        for (int i = 0; i < 2; i++) {
            try {
                if (weapons[i].split("XPARTX")[0].toUpperCase().contains("ESCUDO") || weapons[i].split("XPARTX")[0].toUpperCase().contains("RODELA")) {
                    if (weapons[i].split("XPARTX")[0].toUpperCase().contains("MÁGICO") || weapons[i].split("XPARTX")[0].toUpperCase().contains("MAGIA") || weapons[i].split("XPARTX")[0].toUpperCase().contains("MÁGICA")) {
                        shieldValM += Integer.parseInt(weapons[i].split("XPARTX")[1]);
                    } else shieldVal += Integer.parseInt(weapons[i].split("XPARTX")[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (_c.getCharEdition().equals("2") && _c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[9]) && _c.getCharSkills().contains(" 4")) {
            shieldVal *= 2;
            shieldValM *= 2;
        }
        int finalArmor = _c.getCharArmor() + Integer.parseInt(armor0[0]) + Integer.parseInt(armor1[0]) + Integer.parseInt(armor2[0]) + Integer.parseInt(armor3[0]) + shieldVal;
        int finalMarmor = _c.getCharMarmor() + Integer.parseInt(armor0[1]) + Integer.parseInt(armor1[1]) + Integer.parseInt(armor2[1]) + Integer.parseInt(armor3[1]) + shieldValM;
        int finalValue = (int) Math.floor(finalArmor / 5);
        int finalValueM = (int) Math.floor(finalMarmor / 5);
        if (_c.getCharEdition().equals("2") && _c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[9]) && _c.getCharSkills().contains(" 12")) {
            finalValue += 3;
            finalValueM += 3;
        }
        if (_c.getCharEdition().equals("1")) {
            _detailArmor.setText(String.format(Locale.getDefault(), "Armadura: %d", finalArmor));
            if (finalValue != 0)
                _detailArmor.setText(String.format("%s (evitará como mínimo %s punto/s de daño)", _detailArmor.getText(), finalValue));
            _detailMarmor.setText(String.format(Locale.getDefault(), "Armadura mágica: %d", finalMarmor));
            if (finalValueM != 0)
                _detailMarmor.setText(String.format("%s (evitará como mínimo %s punto/s de daño mágico)", _detailMarmor.getText(), finalValueM));
        } else {
            _detailArmor.setText(String.format(Locale.getDefault(), "Armadura: %d", finalArmor));
            if (finalValue != 0)
                _detailArmor.setText(String.format("%s (evitará %s punto/s de daño)", _detailArmor.getText(), finalValue));
            _detailMarmor.setText(String.format(Locale.getDefault(), "Armadura mágica: %d", finalMarmor));
            if (finalValueM != 0)
                _detailMarmor.setText(String.format("%s (evitará %s punto/s de daño mágico)", _detailMarmor.getText(), finalValueM));
        }
    }

    public void updateDetail() {
        Glide.with(this).load(_c.getCharImg()).asBitmap().into(_civ);
        _detailName.setText(_c.getCharName());
        _detailRaceClass.setText(String.format(Locale.getDefault(), "%s %s %d", _c.getCharRace(), _c.getCharClass(), _c.getCharLevel()));
        if (_c.getCharLevel() == 5) findViewById(R.id.detailLevelUp).setVisibility(View.GONE);
        _detailFUEVal.setText(String.valueOf(_c.getCharFue()));
        _detailDESVal.setText(String.valueOf(_c.getCharDes()));
        String[] weapons = _c.getCharWeapons().split("XNEWX");
        if (_c.getCharEdition().equals("2") && _c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[9]) && _c.getCharSkills().contains(" 3") && ((weapons[0].substring(0, 1).equals("-") && !weapons[1].substring(0, 1).equals("-")) || (!weapons[0].substring(0, 1).equals("-") && weapons[1].substring(0, 1).equals("-")))) {
            _detailDESVal.setText(String.valueOf(_c.getCharDes() + _c.getCharLevel()));
        } else if (_c.getCharEdition().equals("2") && _c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[3]) && _c.getCharSkills().contains(" 3") && !_c.getCharSkills().contains(" 12") && ((!weapons[0].substring(0, 1).equals("-") && !weapons[1].substring(0, 1).equals("-")))) {
            _detailFUEVal.setText(String.valueOf(_c.getCharFue() + 2));
            _detailDESVal.setText(String.valueOf(_c.getCharDes() + 1));
        }
        _detailPUNVal.setText(String.valueOf(_c.getCharPun()));
        _detailINTVal.setText(String.valueOf(_c.getCharInt()));
        _detailSABVal.setText(String.valueOf(_c.getCharSab()));
        _detailAGIVal.setText(String.valueOf(_c.getCharAgi()));
        _detailVOLVal.setText(String.valueOf(_c.getCharVol()));
        _detailENCVal.setText(String.valueOf(_c.getCharEnc()));
        _detailPV.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPv(), _c.getCharMaxpv()));
        _detailPE.setText(String.format(Locale.getDefault(), "%d / %d", _c.getCharPe(), _c.getCharMaxpe()));
        int myCritbonus = _c.getCharCritbonus();
        if (_c.getCharEdition().equals("2") && _c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[3]) && _c.getCharSkills().contains(" 4") && ((weapons[0].substring(0, 1).equals("-") && !weapons[1].substring(0, 1).equals("-")) || (!weapons[0].substring(0, 1).equals("-") && weapons[1].substring(0, 1).equals("-"))))
            myCritbonus += 1;
        ((TextView) findViewById(R.id.detailCritbonus)).setText(String.format(Locale.getDefault(), "Bonif. crítico: %d (críticos con %d natural o superior)", _c.getCharCritbonus(), 20 - myCritbonus));
        ((TextView) findViewById(R.id.detailCritdmgbonus)).setText(String.format(Locale.getDefault(), "Mult. crítico: %d", _c.getCharCritdmgbonus()));
        if (_c.getCharEdition().equals("1")) {
            ((TextView) findViewById(R.id.detailSpellbonus)).setText(String.format(Locale.getDefault(), "Bonif. hechizo: %d", _c.getCharSpellbonus()));
            ((TextView) findViewById(R.id.detailSpellredbonus)).setText(String.format(Locale.getDefault(), "Bonif. hechizo crítico: %d (críticos con %d natural o superior)", _c.getCharSpellredbonus(), 20 - _c.getCharSpellredbonus()));
        } else {
            ((TextView) findViewById(R.id.detailSpellbonus)).setText(String.format(Locale.getDefault(), "Bonif. poder: %d", _c.getCharSpellbonus()));
            ((TextView) findViewById(R.id.detailSpellredbonus)).setText(String.format(Locale.getDefault(), "Reduc. poder: %d", _c.getCharSpellredbonus()));
        }
        updateWeapons();
        updateEquipment();
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
        if (_c.getCharEdition().equals("1")) levelUp1e();
        else if (_c.getCharEdition().equals("2")) levelUp2e();
    }

    public void levelUp1e() {
        if (_c.getCharLevel() < 5) {
            final Dialog d = new Dialog(this);
            d.setContentView(R.layout.levelupstats);
            _c.setCharLevel(_c.getCharLevel() + 1);
            ((TextView) d.findViewById(R.id.luLevel)).setText(String.format(Locale.getDefault(), "¡Nivel %d!", _c.getCharLevel()));
            (d.findViewById(R.id.luFUE)).setOnClickListener(v -> {
                _c.setCharFue(_c.getCharFue() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luDES)).setOnClickListener(v -> {
                _c.setCharDes(_c.getCharDes() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luPUN)).setOnClickListener(v -> {
                _c.setCharPun(_c.getCharPun() + 1);
                if (_c.getCharPun() % 5 == 0) _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luINT)).setOnClickListener(v -> {
                _c.setCharInt(_c.getCharInt() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luSAB)).setOnClickListener(v -> {
                _c.setCharSab(_c.getCharSab() + 1);
                if (_c.getCharSab() % 5 == 0)
                    _c.setCharSpellredbonus(_c.getCharSpellredbonus() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luAGI)).setOnClickListener(v -> {
                _c.setCharAgi(_c.getCharAgi() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            if (_c.getCharRace().equals(getResources().getStringArray(R.array.races1e)[3]) || _c.getCharLevel() == 2 || ((_c.getCharLevel() == 3) && !_c.getCharBonus().contains("VOL2")) || ((_c.getCharLevel() == 4) && !_c.getCharBonus().contains("VOL3")) || ((_c.getCharLevel() == 5) && !_c.getCharBonus().contains("VOL4"))) {
                (d.findViewById(R.id.luVOL)).setVisibility(View.VISIBLE);
            } else (d.findViewById(R.id.luVOL)).setVisibility(View.GONE);
            (d.findViewById(R.id.luVOL)).setOnClickListener(v -> {
                if (_c.getCharEdition().equals("1")) {
                    if (_c.getCharLevel() == 2) _c.setCharBonus(_c.getCharBonus() + " VOL2");
                    else if (_c.getCharLevel() == 3) _c.setCharBonus(_c.getCharBonus() + " VOL3");
                    else if (_c.getCharLevel() == 4) _c.setCharBonus(_c.getCharBonus() + " VOL4");
                }
                _c.setCharVol(_c.getCharVol() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            d.findViewById(R.id.luENC).setVisibility(View.GONE);
            if ((_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10])))
                ((Button) d.findViewById(R.id.luPV)).setText("+8 PV");
            (d.findViewById(R.id.luPV)).setOnClickListener(v -> {
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10]))
                    _c.setCharMaxpv(_c.getCharMaxpv() + 8);
                else _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[6]) && _c.getCharSkills().contains(" 5"))
                ((Button) d.findViewById(R.id.luPE)).setText("+8 PE");
            (d.findViewById(R.id.luPE)).setOnClickListener(v -> {
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[6]) && _c.getCharSkills().contains(" 5"))
                    _c.setCharMaxpe(_c.getCharMaxpe() + 8);
                else _c.setCharMaxpe(_c.getCharMaxpe() + 5);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
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

    public void levelUp2e() {
        if (_c.getCharLevel() < 5) {
            final Dialog d = new Dialog(this);
            d.setContentView(R.layout.levelupstats);
            ((Button) d.findViewById(R.id.luPUN)).setText("+1 PRE");
            _c.setCharLevel(_c.getCharLevel() + 1);
            ((TextView) d.findViewById(R.id.luLevel)).setText(String.format(Locale.getDefault(), "¡Nivel %d!", _c.getCharLevel()));
            (d.findViewById(R.id.luFUE)).setOnClickListener(v -> {
                _c.setCharFue(_c.getCharFue() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luDES)).setOnClickListener(v -> {
                _c.setCharDes(_c.getCharDes() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luPUN)).setOnClickListener(v -> {
                _c.setCharPun(_c.getCharPun() + 1);
                if (_c.getCharPun() % 3 == 0) _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luINT)).setOnClickListener(v -> {
                _c.setCharInt(_c.getCharInt() + 1);
                if (_c.getCharInt() % 3 == 0) _c.setCharSpellbonus(_c.getCharSpellbonus() + 1);
                if ((_c.getCharInt() % 5 == 0) && (_c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[0])))
                    _c.setCharSpellredbonus(_c.getCharSpellredbonus() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luSAB)).setOnClickListener(v -> {
                _c.setCharSab(_c.getCharSab() + 1);
                if (_c.getCharSab() % 3 == 0)
                    _c.setCharSpellredbonus(_c.getCharSpellredbonus() + 1);
                if ((_c.getCharSab() % 5 == 0) && (_c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[0])))
                    _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luAGI)).setOnClickListener(v -> {
                _c.setCharAgi(_c.getCharAgi() + 1);
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[2]) && _c.getCharBonus().contains("ASCRIT")) {
                    if (_c.getCharAgi() % 5 == 0)
                        _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                }
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luVOL)).setOnClickListener(v -> {
                _c.setCharVol(_c.getCharVol() + 1);
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[12]))
                    _c.setCharArmor(_c.getCharArmor() + 2);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luENC)).setOnClickListener(v -> {
                _c.setCharEnc(_c.getCharEnc() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luPV)).setOnClickListener(v -> {
                /*if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes1e)[10]))
                    _c.setCharMaxpv(_c.getCharMaxpv() + 8);
                else*/
                _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            (d.findViewById(R.id.luPE)).setOnClickListener(v -> {
                _c.setCharMaxpe(_c.getCharMaxpe() + 5);
                if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[11]))
                    _c.setCharMaxpv(_c.getCharMaxpv() + 2);
                MyDB.updateChar(_c);
                updateDetail();
                d.cancel();
            });
            d.show();
            if (_c.getCharRace().equals(getResources().getStringArray(R.array.races2e)[2]) && (_c.getCharFue() >= 3 || _c.getCharDes() >= 3 || _c.getCharPun() >= 3 || _c.getCharInt() >= 3 || _c.getCharSab() >= 3 ||
                    _c.getCharAgi() >= 3 || _c.getCharVol() >= 3) && !_c.getCharBonus().contains("WECRIT")) {
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                _c.setCharBonus(_c.getCharBonus() + " WECRIT");
            }
            if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[3]) && ((_c.getCharLevel() == 3) || (_c.getCharLevel() == 5)))
                barbDialog();
            if (_c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[10]) && _c.getCharSkills().contains(" 3"))
                _c.setCharArmor(_c.getCharArmor() + 3);
            chooseSkill();
            MyDB.updateChar(_c);
            updateDetail();
        }
    }

    public void barbDialog() {
        final Dialog barbD = new Dialog(this);
        barbD.setContentView(R.layout.levelupstats);
        ((TextView) barbD.findViewById(R.id.luLevel)).setText("Elige sabiamente, poderoso Bárbaro.");
        (barbD.findViewById(R.id.luFUE)).setOnClickListener(v -> {
            _c.setCharFue(_c.getCharFue() + 1);
            MyDB.updateChar(_c);
            updateDetail();
            barbD.cancel();
        });
        if (_c.getCharEdition().equals("1")) {
            (barbD.findViewById(R.id.luVOL)).setVisibility(View.GONE);
            ((Button) barbD.findViewById(R.id.luPV)).setText("+3 PV");
            (barbD.findViewById(R.id.luPV)).setOnClickListener(v -> {
                _c.setCharPv(_c.getCharPv() + 3);
                MyDB.updateChar(_c);
                updateDetail();
                barbD.cancel();
            });
        } else {
            (barbD.findViewById(R.id.luVOL)).setOnClickListener(v -> {
                _c.setCharVol(_c.getCharVol() + 1);
                MyDB.updateChar(_c);
                updateDetail();
                barbD.cancel();
            });
            (barbD.findViewById(R.id.luPV)).setVisibility(View.GONE);
        }
        (barbD.findViewById(R.id.luDES)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luPUN)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luINT)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luSAB)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luAGI)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luENC)).setVisibility(View.GONE);
        (barbD.findViewById(R.id.luPE)).setVisibility(View.GONE);
        barbD.show();
    }

    public void chooseSkill() {
        if (_c.getCharEdition().equals("1")) chooseSkill1e();
        else if (_c.getCharEdition().equals("2")) chooseSkill2e();
    }

    public void chooseSkill1e() {
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
        ((RadioButton) skillD.findViewById(R.id.luSkill1)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            String[] myArray = getResources().getStringArray(R.array.classes1e);
            if (_c.getCharClass().equals(myArray[1]) && ((_c.getCharLevel() - 1) * 3) == 9) {
                _c.setCharCritbonus(_c.getCharCritbonus() + 2);
                _c.setCharCritdmgbonus(_c.getCharCritdmgbonus() + 100);
            } else if (_c.getCharClass().equals(myArray[8]) && ((_c.getCharLevel() - 1) * 3) == 9) {
                _c.setCharVol(_c.getCharVol() + 1);
            } else if (_c.getCharClass().equals(myArray[10]) && ((_c.getCharLevel() - 1) * 3) == 3) {
                _c.setCharDes(_c.getCharDes() + 1);
            } else if (_c.getCharClass().equals(myArray[10]) && ((_c.getCharLevel() - 1) * 3) == 12) {
                _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                _c.setCharVol(_c.getCharVol() + 3);
            } else if (_c.getCharClass().equals(myArray[11]) && ((_c.getCharLevel() - 1) * 3) == 3) {
                _c.setCharVol(_c.getCharVol() + 2);
                _c.setCharArmor(_c.getCharArmor() + 6);
            }
            _c.setCharSkills(_c.getCharSkills() + " " + (_c.getCharLevel() - 1) * 3);
            MyDB.updateChar(_c);
            updateDetail();
            skillD.cancel();
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill2)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            String[] myArray = getResources().getStringArray(R.array.classes1e);
            if (_c.getCharClass().equals(myArray[2]) && (((_c.getCharLevel() - 1) * 3) + 1) == 4) {
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
            } else if (_c.getCharClass().equals(myArray[1]) && (((_c.getCharLevel() - 1) * 3) + 1) == 13) {
                assassinSkill();
            } else if (_c.getCharClass().equals(myArray[7]) && (((_c.getCharLevel() - 1) * 3) + 1) == 7) {
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                _c.setCharCritdmgbonus(_c.getCharCritdmgbonus() + 100);
            } else if (_c.getCharClass().equals(myArray[8]) && (((_c.getCharLevel() - 1) * 3) + 1) == 13) {
                _c.setCharMaxpe(_c.getCharMaxpe() + 8);
            } else if (_c.getCharClass().equals(myArray[10]) && (((_c.getCharLevel() - 1) * 3) + 1) == 10) {
                _c.setCharMaxpv(_c.getCharMaxpv() + 5);
            } else if (_c.getCharClass().equals(myArray[12]) && (((_c.getCharLevel() - 1) * 3) + 1) == 10) {
                _c.setCharMaxpe(_c.getCharMaxpe() + 5);
            }
            _c.setCharSkills(_c.getCharSkills() + " " + (((_c.getCharLevel() - 1) * 3) + 1));
            MyDB.updateChar(_c);
            updateDetail();
            skillD.cancel();
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill3)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            String[] myArray = getResources().getStringArray(R.array.classes1e);
            if (_c.getCharClass().equals(myArray[4]) && (((_c.getCharLevel() - 1) * 3) + 2) == 5) {
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                _c.setCharPun(_c.getCharPun() + 2);
                _c.setCharMaxpe(_c.getCharMaxpe() + 5);
            } else if (_c.getCharClass().equals(myArray[6]) && (((_c.getCharLevel() - 1) * 3) + 2) == 5) {
                _c.setCharMaxpe(_c.getCharMaxpe() + 3);
            } else if (_c.getCharClass().equals(myArray[13]) && (((_c.getCharLevel() - 1) * 3) + 2) == 14) {
                _c.setCharMaxpv(_c.getCharMaxpv() + 10);
            }
            _c.setCharSkills(_c.getCharSkills() + " " + (((_c.getCharLevel() - 1) * 3) + 2));
            MyDB.updateChar(_c);
            updateDetail();
            skillD.cancel();
        });
        skillD.show();
    }

    public void chooseSkill2e() {
        final Dialog skillD = new Dialog(this);
        skillD.setContentView(R.layout.chooseskill);
        String[] classes = getResources().getStringArray(R.array.classes2e);
        int myClass = 0;
        for (int i = 0; i < classes.length; i++) {
            if (_c.getCharClass().equals(classes[i])) myClass = i;
        }
        String[] titles = getResources().getStringArray(R.array.classesSkills2e)[myClass].split("XNEWX");
        String[] desc = getResources().getStringArray(R.array.classesSkillsDesc2e)[myClass].split("XNEWX");
        ((TextView) skillD.findViewById(R.id.luSkillTitle)).setText(String.format(Locale.getDefault(), "Elige una habilidad de nivel %d.", _c.getCharLevel()));
        ((TextView) skillD.findViewById(R.id.luSkillT1)).setText(titles[(_c.getCharLevel() - 1) * 3]);
        ((TextView) skillD.findViewById(R.id.luSkillD1)).setText(Html.fromHtml(desc[(_c.getCharLevel() - 1) * 3]));
        ((TextView) skillD.findViewById(R.id.luSkillT2)).setText(titles[((_c.getCharLevel() - 1) * 3) + 1]);
        ((TextView) skillD.findViewById(R.id.luSkillD2)).setText(Html.fromHtml(desc[((_c.getCharLevel() - 1) * 3) + 1]));
        ((TextView) skillD.findViewById(R.id.luSkillT3)).setText(titles[((_c.getCharLevel() - 1) * 3) + 2]);
        ((TextView) skillD.findViewById(R.id.luSkillD3)).setText(Html.fromHtml(desc[((_c.getCharLevel() - 1) * 3) + 2]));
        ((RadioButton) skillD.findViewById(R.id.luSkill1)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            String[] myArray = getResources().getStringArray(R.array.classes2e);
            if ((_c.getCharClass().equals(myArray[9])) && (((_c.getCharLevel() - 1) * 3) == 3))
                _c.setCharDes(_c.getCharDes() + 1);
            else if ((_c.getCharClass().equals(myArray[9])) && (((_c.getCharLevel() - 1) * 3) == 12)) {
                _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                _c.setCharVol(_c.getCharVol() + 3);
            } else if ((_c.getCharClass().equals(myArray[10])) && (((_c.getCharLevel() - 1) * 3) == 3)) {
                _c.setCharVol(_c.getCharVol() + 2);
                _c.setCharArmor(_c.getCharArmor() + 6);
            }
            _c.setCharSkills(_c.getCharSkills() + " " + (_c.getCharLevel() - 1) * 3);
            MyDB.updateChar(_c);
            updateDetail();
            skillD.cancel();
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill2)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            String[] myArray = getResources().getStringArray(R.array.classes2e);
            if ((_c.getCharClass().equals(myArray[2])) && (((_c.getCharLevel() - 1) * 3) + 1 == 13))
                assassinSkill();
            else if ((_c.getCharClass().equals(myArray[3])) && (((_c.getCharLevel() - 1) * 3) + 1 == 4))
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
            else if ((_c.getCharClass().equals(myArray[13])) && (((_c.getCharLevel() - 1) * 3) + 1 == 7)) {
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
                _c.setCharCritdmgbonus(_c.getCharCritdmgbonus() + 1);
            }
            _c.setCharSkills(_c.getCharSkills() + " " + (((_c.getCharLevel() - 1) * 3) + 1));
            MyDB.updateChar(_c);
            updateDetail();
            skillD.cancel();
        });
        ((RadioButton) skillD.findViewById(R.id.luSkill3)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            String[] myArray = getResources().getStringArray(R.array.classes2e);
            if (_c.getCharClass().equals(myArray[2]) && (((_c.getCharLevel() - 1) * 3) + 2) == 8) {
                _c.setCharCritbonus(_c.getCharCritbonus() + (int) Math.floor(_c.getCharAgi() / 5));
                _c.setCharBonus(_c.getCharBonus() + " ASCRIT");
            } else if (_c.getCharClass().equals(myArray[9]) && (((_c.getCharLevel() - 1) * 3) + 2) == 8) {
                _c.setCharMarmor(_c.getCharMarmor() + 5);
            } else if (_c.getCharClass().equals(myArray[13]) && (((_c.getCharLevel() - 1) * 3) + 2) == 11)
                _c.setCharCritbonus(_c.getCharCritbonus() + 1);
            _c.setCharSkills(_c.getCharSkills() + " " + (((_c.getCharLevel() - 1) * 3) + 2));
            MyDB.updateChar(_c);
            updateDetail();
            skillD.cancel();
        });
        skillD.show();
    }

    public void assassinSkill() {
        final Dialog skillD = new Dialog(this);
        skillD.setContentView(R.layout.chooseskill);
        String[] classes = null, titles = null, desc = null;
        if (_c.getCharEdition().equals("1"))
            classes = getResources().getStringArray(R.array.classes1e);
        else if (_c.getCharEdition().equals("2"))
            classes = getResources().getStringArray(R.array.classes2e);
        int myClass = 0;
        for (int i = 0; i < classes.length; i++) {
            if (_c.getCharClass().equals(classes[i])) myClass = i;
        }
        if (_c.getCharEdition().equals("1")) {
            titles = getResources().getStringArray(R.array.classesSkills1e)[myClass].split("XNEWX");
            desc = getResources().getStringArray(R.array.classesSkillsDesc1e)[myClass].split("XNEWX");
        } else if (_c.getCharEdition().equals("2")) {
            titles = getResources().getStringArray(R.array.classesSkills2e)[myClass].split("XNEWX");
            desc = getResources().getStringArray(R.array.classesSkillsDesc2e)[myClass].split("XNEWX");
        }
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

    public void addWeapon(View view) {
        int position = 0;
        if (view.equals(findViewById(R.id.weapon0add))) position = 0;
        else if (view.equals(findViewById(R.id.weapon1add))) position = 1;
        else if (view.equals(findViewById(R.id.weapon2add))) position = 2;
        else if (view.equals(findViewById(R.id.weapon3add))) position = 3;
        String[] myWeapons = _c.getCharWeapons().split("XNEWX");
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.addweapon);
        d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        d.show();
        Spinner s = d.findViewById(R.id.newWeaponSpinner);
        ArrayAdapter<CharSequence> weaponSpinner = ArrayAdapter.createFromResource(this, R.array.dados, R.layout.spinner);
        weaponSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(weaponSpinner);
        int finalPosition = position;
        d.findViewById(R.id.createWeaponButton).setOnClickListener(v -> {
            TextView myName = d.findViewById(R.id.createWeaponName);
            if (myName.getText().toString().equals("")) myName.setText(R.string.genericWeapon);
            TextView addDmg = d.findViewById(R.id.createWeaponDmg);
            TextView diceMult = d.findViewById(R.id.newWeaponMult);
            if (diceMult.getText().toString().equals("")) diceMult.setText("1");
            String name = myName.getText().toString();
            String dmg = "";
            if (!s.getSelectedItem().toString().equals("-"))
                dmg += diceMult.getText().toString() + s.getSelectedItem();
            if (!addDmg.getText().toString().equals("")) {
                if (!s.getSelectedItem().toString().equals("-")) dmg += " + ";
                dmg += addDmg.getText().toString();
            }
            if (dmg.equals("")) dmg = "-";
            myWeapons[finalPosition] = name + "XPARTX" + dmg;
            String newWeapon = "";
            for (int i = 0; i < myWeapons.length; i++) {
                if (i != 0) newWeapon += "XNEWX";
                newWeapon += myWeapons[i];
            }
            _c.setCharWeapons(newWeapon);
            if (_c.getCharEdition().equals("2") && _c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[9]) && _c.getCharSkills().contains(" 11") && (myName.getText().toString().toUpperCase().contains("ESCUDO") || myName.getText().toString().toUpperCase().contains("RODELA"))) {
                _c.setCharMaxpv(_c.getCharMaxpv() + 5);
                _c.setCharArmor(_c.getCharArmor() + 5);
                updateDetail();
            }
            updateDetail();
            d.dismiss();
        });

    }

    public void removeWeapon(View view) {
        int position = 0;
        if (view.equals(findViewById(R.id.weapon0remove))) position = 0;
        else if (view.equals(findViewById(R.id.weapon1remove))) position = 1;
        else if (view.equals(findViewById(R.id.weapon2remove))) position = 2;
        else if (view.equals(findViewById(R.id.weapon3remove))) position = 3;
        String[] myWeapons = _c.getCharWeapons().split("XNEWX");
        if (_c.getCharEdition().equals("2") && _c.getCharClass().equals(getResources().getStringArray(R.array.classes2e)[9]) && _c.getCharSkills().contains(" 11") && (myWeapons[position].split("XPARTX")[0].toUpperCase().contains("ESCUDO") || myWeapons[position].split("XPARTX")[0].toUpperCase().contains("RODELA"))) {
            _c.setCharMaxpv(_c.getCharMaxpv() - 5);
            _c.setCharArmor(_c.getCharArmor() - 5);
            updateDetail();
        }
        myWeapons[position] = "-XPARTX-";
        String newWeapon = "";
        for (int i = 0; i < myWeapons.length; i++) {
            if (i != 0) newWeapon += "XNEWX";
            newWeapon += myWeapons[i];
        }
        _c.setCharWeapons(newWeapon);
        updateDetail();
    }

    public void addEquipment(View view) {
        int position = 0;
        if (view.equals(findViewById(R.id.equip0add))) position = 0;
        else if (view.equals(findViewById(R.id.equip1add))) position = 1;
        else if (view.equals(findViewById(R.id.equip2add))) position = 2;
        else if (view.equals(findViewById(R.id.equip3add))) position = 3;
        String[] myEquipment = _c.getCharEquip().split("XNEWX");
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.addequipment);
        d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        d.show();
        int finalPosition = position;
        d.findViewById(R.id.createEquipmentButton).setOnClickListener(v -> {
            EditText name = d.findViewById(R.id.createEquipName);
            if (name.getText().toString().equals("")) name.setText(R.string.genericArmor);
            EditText armor = d.findViewById(R.id.createEquipValue);
            if (armor.getText().toString().equals("")) armor.setText("0");
            EditText marmor = d.findViewById(R.id.createEquipValueM);
            if (marmor.getText().toString().equals("")) marmor.setText("0");
            myEquipment[finalPosition] = name.getText().toString() + "XPARTX" + armor.getText().toString() + " / " + marmor.getText().toString();
            String newEquip = "";
            for (int i = 0; i < myEquipment.length; i++) {
                if (i != 0) newEquip += "XNEWX";
                newEquip += myEquipment[i];
            }
            _c.setCharEquip(newEquip);
            updateDetail();
            d.dismiss();
        });
    }

    public void removeEquipment(View view) {
        int position = 0;
        if (view.equals(findViewById(R.id.equip0remove))) position = 0;
        else if (view.equals(findViewById(R.id.equip1remove))) position = 1;
        else if (view.equals(findViewById(R.id.equip2remove))) position = 2;
        else if (view.equals(findViewById(R.id.equip3remove))) position = 3;
        String[] myEquipment = _c.getCharEquip().split("XNEWX");
        myEquipment[position] = "-XPARTX0 / 0";
        String newEquip = "";
        for (int i = 0; i < myEquipment.length; i++) {
            if (i != 0) newEquip += "XNEWX";
            newEquip += myEquipment[i];
        }
        _c.setCharEquip(newEquip);
        updateDetail();
    }
}
