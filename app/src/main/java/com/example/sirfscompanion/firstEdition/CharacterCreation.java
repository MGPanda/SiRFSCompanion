package com.example.sirfscompanion.firstEdition;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sirfscompanion.control.MainActivity;
import com.example.sirfscompanion.R;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CharacterCreation extends AppCompatActivity {
    private int[] raceStats, classStats;
    private TextView FUE, DES, PUN, INT, SAB, AGI, VOL;
    private EditText PV, PE;
    private CircleImageView _civ;
    private Random _r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(R.string.creacionPersonaje);
        _r = new Random();
        _civ = findViewById(R.id.ccImage);
        FUE = findViewById(R.id.ccFUEVal);
        DES = findViewById(R.id.ccDESVal);
        PUN = findViewById(R.id.ccPUNVal);
        INT = findViewById(R.id.ccINTVal);
        SAB = findViewById(R.id.ccSABVal);
        AGI = findViewById(R.id.ccAGIVal);
        VOL = findViewById(R.id.ccVOLVal);
        PV = findViewById(R.id.ccPV);
        PE = findViewById(R.id.ccPE);
        raceStats = new int[9];
        classStats = new int[9];
        Spinner ccRace = findViewById(R.id.ccRace);
        ArrayAdapter<CharSequence> racesAdapter = ArrayAdapter.createFromResource(this, R.array.races1e, R.layout.spinner);
        racesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ccRace.setAdapter(racesAdapter);
        ccRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] aux = getResources().getStringArray(R.array.racesStats1e);
                String aux2 = aux[position];
                aux = aux2.split(" ");
                for (int i = 0; i < aux.length; i++) {
                    raceStats[i] = Integer.parseInt(aux[i]);
                }
                FUE.setText(String.valueOf(raceStats[0]));
                DES.setText(String.valueOf(raceStats[1]));
                PUN.setText(String.valueOf(raceStats[2]));
                INT.setText(String.valueOf(raceStats[3]));
                SAB.setText(String.valueOf(raceStats[4]));
                AGI.setText(String.valueOf(raceStats[5]));
                VOL.setText(String.valueOf(raceStats[6]));
                PV.setText(null);
                PE.setText(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner ccClass = findViewById(R.id.ccClass);
        ArrayAdapter<CharSequence> classesAdapter = ArrayAdapter.createFromResource(this, R.array.classes1e, R.layout.spinner);
        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ccClass.setAdapter(classesAdapter);
        ccClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] classes = getResources().getStringArray(R.array.classes1e);
                if (parent.getItemAtPosition(position).toString().equals(classes[0])) Glide.with(getApplicationContext()).load(R.drawable.alchemist).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[1])) Glide.with(getApplicationContext()).load(R.drawable.assassin).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[2])) Glide.with(getApplicationContext()).load(R.drawable.barbarian).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[3])) Glide.with(getApplicationContext()).load(R.drawable.bard).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[4])) Glide.with(getApplicationContext()).load(R.drawable.hunter).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[5])) Glide.with(getApplicationContext()).load(R.drawable.druid).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[6])) Glide.with(getApplicationContext()).load(R.drawable.arcane).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[7])) Glide.with(getApplicationContext()).load(R.drawable.fire).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[8])) Glide.with(getApplicationContext()).load(R.drawable.ice).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[9])) Glide.with(getApplicationContext()).load(R.drawable.earth).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[10])) Glide.with(getApplicationContext()).load(R.drawable.warrior).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[11])) Glide.with(getApplicationContext()).load(R.drawable.monk).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[12])) Glide.with(getApplicationContext()).load(R.drawable.necromancer).asBitmap().into(_civ);
                else if (parent.getItemAtPosition(position).toString().equals(classes[13])) Glide.with(getApplicationContext()).load(R.drawable.paladin).asBitmap().into(_civ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ccRace.setSelection(_r.nextInt(ccRace.getAdapter().getCount()));
        ccClass.setSelection(_r.nextInt(ccClass.getAdapter().getCount()));
        PV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !PV.getText().equals("")) {
                    if (Integer.parseInt(PV.getText().toString()) > (20 + raceStats[7] + classStats[7])) {
                        PV.setText(String.valueOf(20 + raceStats[7] + classStats[7]));
                    } else if (Integer.parseInt(PV.getText().toString()) < 10) {
                        PV.setText("10");
                    }
                }
            }
        });
        PE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !PE.getText().equals("")) {
                    if (Integer.parseInt(PE.getText().toString()) > (20 + raceStats[8] + classStats[8])) {
                        PE.setText(String.valueOf(20 + raceStats[8] + classStats[8]));
                    } else if (Integer.parseInt(PE.getText().toString()) < 10) {
                        PE.setText("10");
                    }
                }
            }
        });
    }

    public void roll(View view) {
        _r = new Random();
        int aux = 0;
        if (view.equals(findViewById(R.id.ccPVRoll))) {
            aux = _r.nextInt(20) + raceStats[7] + classStats[7];
            if (aux < 10) aux = 10;
            PV.setText(String.valueOf(aux));
        } else {
            aux = _r.nextInt(20) + raceStats[8] + classStats[8];
            if (aux < 10) aux = 10;
            PE.setText(String.valueOf(aux));
        }
    }

    public void max(View view) {
        if (view.equals(findViewById(R.id.ccPVMax))) {
            PV.setText(String.valueOf(20 + raceStats[7] + classStats[7]));
        } else PE.setText(String.valueOf(20 + raceStats[8] + classStats[8]));
    }

    public void ccSetImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        i.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 0:
                    Uri selectedImage = data.getData();
                    Glide.with(getApplicationContext()).load(selectedImage).asBitmap().into(_civ);
                    break;
            }
    }
}

