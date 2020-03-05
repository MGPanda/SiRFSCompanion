package com.example.sirfscompanion.firstEdition;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sirfscompanion.R;
import com.example.sirfscompanion.control.MainActivity;
import com.example.sirfscompanion.control.MyDB;
import com.example.sirfscompanion.instanciables.Char;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CharacterCreation extends AppCompatActivity {
    private int[] raceStats, classStats;
    private TextView _FUE, _DES, _PUN, _INT, _SAB, _AGI, _VOL;
    private EditText _ccName, _PV, _PE;
    private CircleImageView _civ;
    private Random _r;
    private Spinner _ccRace, _ccClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);
        setResult(0);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(R.string.creacionPersonaje);
        _r = new Random();
        String[] names = getResources().getStringArray(R.array.nombres);
        String[] surname1 = getResources().getStringArray(R.array.apellidos);
        String[] surname2 = getResources().getStringArray(R.array.apellidos2);
        _ccName = findViewById(R.id.ccName);
        _ccName.setHint(names[_r.nextInt(names.length)] + " " + surname1[_r.nextInt(surname1.length)] + surname2[_r.nextInt(surname2.length)]);
        _civ = findViewById(R.id.ccImage);
        _FUE = findViewById(R.id.ccFUEVal);
        _DES = findViewById(R.id.ccDESVal);
        _PUN = findViewById(R.id.ccPUNVal);
        _INT = findViewById(R.id.ccINTVal);
        _SAB = findViewById(R.id.ccSABVal);
        _AGI = findViewById(R.id.ccAGIVal);
        _VOL = findViewById(R.id.ccVOLVal);
        _PV = findViewById(R.id.ccPV);
        _PE = findViewById(R.id.ccPE);
        raceStats = new int[9];
        classStats = new int[9];
        _ccRace = findViewById(R.id.ccRace);
        ArrayAdapter<CharSequence> racesAdapter = ArrayAdapter.createFromResource(this, R.array.races1e, R.layout.spinner);
        racesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _ccRace.setAdapter(racesAdapter);
        _ccRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] aux = getResources().getStringArray(R.array.racesStats1e);
                String aux2 = aux[position];
                aux = aux2.split(" ");
                for (int i = 0; i < aux.length; i++) {
                    raceStats[i] = Integer.parseInt(aux[i]);
                }
                _FUE.setText(String.valueOf(raceStats[0]));
                _DES.setText(String.valueOf(raceStats[1]));
                _PUN.setText(String.valueOf(raceStats[2]));
                _INT.setText(String.valueOf(raceStats[3]));
                _SAB.setText(String.valueOf(raceStats[4]));
                _AGI.setText(String.valueOf(raceStats[5]));
                _VOL.setText(String.valueOf(raceStats[6]));
                _PV.setText(null);
                _PE.setText(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        _ccClass = findViewById(R.id.ccClass);
        ArrayAdapter<CharSequence> classesAdapter = ArrayAdapter.createFromResource(this, R.array.classes1e, R.layout.spinner);
        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _ccClass.setAdapter(classesAdapter);
        _ccClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] classes = getResources().getStringArray(R.array.classes1e);
                if (parent.getItemAtPosition(position).toString().equals(classes[0]))
                    Glide.with(getApplicationContext()).load(R.drawable.alchemist).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[1]))
                    Glide.with(getApplicationContext()).load(R.drawable.assassin).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[2]))
                    Glide.with(getApplicationContext()).load(R.drawable.barbarian).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[3]))
                    Glide.with(getApplicationContext()).load(R.drawable.bard).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[4]))
                    Glide.with(getApplicationContext()).load(R.drawable.hunter).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[5]))
                    Glide.with(getApplicationContext()).load(R.drawable.druid).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[6]))
                    Glide.with(getApplicationContext()).load(R.drawable.arcane).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[7]))
                    Glide.with(getApplicationContext()).load(R.drawable.fire).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[8]))
                    Glide.with(getApplicationContext()).load(R.drawable.ice).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[9]))
                    Glide.with(getApplicationContext()).load(R.drawable.earth).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[10]))
                    Glide.with(getApplicationContext()).load(R.drawable.warrior).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[11]))
                    Glide.with(getApplicationContext()).load(R.drawable.monk).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[12]))
                    Glide.with(getApplicationContext()).load(R.drawable.necromancer).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[13]))
                    Glide.with(getApplicationContext()).load(R.drawable.paladin).asBitmap().into(_civ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        _ccRace.setSelection(_r.nextInt(_ccRace.getAdapter().getCount()));
        _ccClass.setSelection(_r.nextInt(_ccClass.getAdapter().getCount()));
        _PV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !_PV.getText().equals("")) {
                    if (Integer.parseInt(_PV.getText().toString()) > (20 + raceStats[7] + classStats[7])) {
                        _PV.setText(String.valueOf(20 + raceStats[7] + classStats[7]));
                    } else if (Integer.parseInt(_PV.getText().toString()) < 10) {
                        _PV.setText("10");
                    }
                }
            }
        });
        _PE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !_PE.getText().equals("")) {
                    if (Integer.parseInt(_PE.getText().toString()) > (20 + raceStats[8] + classStats[8])) {
                        _PE.setText(String.valueOf(20 + raceStats[8] + classStats[8]));
                    } else if (Integer.parseInt(_PE.getText().toString()) < 10) {
                        _PE.setText("10");
                    }
                }
            }
        });
    }

    public int roll(View view) {
        _r = new Random();
        int aux = 0;
        if (view.equals(findViewById(R.id.ccPVRoll))) {
            aux = _r.nextInt(20) + raceStats[7] + classStats[7];
            if (aux < 10) aux = 10;
            _PV.setText(String.valueOf(aux));
        } else {
            aux = _r.nextInt(20) + raceStats[8] + classStats[8];
            if (aux < 10) aux = 10;
            _PE.setText(String.valueOf(aux));
        }
        return aux;
    }

    public void max(View view) {
        if (view.equals(findViewById(R.id.ccPVMax))) {
            _PV.setText(String.valueOf(20 + raceStats[7] + classStats[7]));
        } else _PE.setText(String.valueOf(20 + raceStats[8] + classStats[8]));
    }

    public void ccSetImage(View view) {
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
                Uri selectedImage = data.getData();
                Glide.with(getApplicationContext()).load(selectedImage).asBitmap().into(_civ);
            }
    }

    public void createCharacter(View view) {
        String name = "";
        if (_ccName.getText().toString().equals("")) name = _ccName.getHint().toString();
        else name = _ccName.getText().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Bitmap img = ((BitmapDrawable) _civ.getDrawable()).getBitmap();
        int FUE = Integer.parseInt(_FUE.getText().toString());
        int DES = Integer.parseInt(_DES.getText().toString());
        int PUN = Integer.parseInt(_PUN.getText().toString());
        int INT = Integer.parseInt(_INT.getText().toString());
        int SAB = Integer.parseInt(_SAB.getText().toString());
        int AGI = Integer.parseInt(_AGI.getText().toString());
        int VOL = Integer.parseInt(_VOL.getText().toString());
        int PV;
        if (!_PV.getText().toString().equals("")) PV = Integer.parseInt(_PV.getText().toString());
        else PV = roll(findViewById(R.id.ccPVRoll));
        int PE;
        if (!_PE.getText().toString().equals("")) PE = Integer.parseInt(_PE.getText().toString());
        else PE = roll(findViewById(R.id.ccPERoll));
        int armor = 0;
        int marmor = 0;
        int critbonus = 0;
        int critdmgbonus = 0;
        int spellbonus = 0;
        String bonus = "";
        int gold = 100;
        Char c = new Char(name, img, date, 1, _ccRace.getSelectedItem().toString(), _ccClass.getSelectedItem().toString(), FUE, DES, PUN, INT, SAB, AGI, VOL, PV, PV, PE, PE, armor, marmor, critbonus, critdmgbonus,
                spellbonus, "", "", "", "0 1 2", bonus, gold, "");
        MyDB.createChar(c);
        Intent i = new Intent();
        i.putExtra("CHAR", c);
        setResult(1, i);
        finish();
    }
}

