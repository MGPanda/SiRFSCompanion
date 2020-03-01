package com.example.sirfscompanion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class CharacterCreation extends AppCompatActivity {
    private int[] raceStats, classStats;
    private TextView FUE, DES, PUN, INT, SAB, AGI, VOL;
    private EditText PV, PE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(R.string.creacionPersonaje);
        if (MainActivity.isDarkMode()) getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES); else getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
                FUE.setText(String.valueOf(raceStats[0] + classStats[0]));
                DES.setText(String.valueOf(raceStats[1] + classStats[1]));
                PUN.setText(String.valueOf(raceStats[2] + classStats[2]));
                INT.setText(String.valueOf(raceStats[3] + classStats[3]));
                SAB.setText(String.valueOf(raceStats[4] + classStats[4]));
                AGI.setText(String.valueOf(raceStats[5] + classStats[5]));
                VOL.setText(String.valueOf(raceStats[6] + classStats[6]));
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
                String[] aux = getResources().getStringArray(R.array.classesStats1e);
                String aux2 = aux[position];
                aux = aux2.split(" ");
                for (int i = 0; i < aux.length; i++) {
                    classStats[i] = Integer.parseInt(aux[i]);
                }
                FUE.setText(String.valueOf(raceStats[0] + classStats[0]));
                DES.setText(String.valueOf(raceStats[1] + classStats[1]));
                PUN.setText(String.valueOf(raceStats[2] + classStats[2]));
                INT.setText(String.valueOf(raceStats[3] + classStats[3]));
                SAB.setText(String.valueOf(raceStats[4] + classStats[4]));
                AGI.setText(String.valueOf(raceStats[5] + classStats[5]));
                VOL.setText(String.valueOf(raceStats[6] + classStats[6]));
                PV.setText(null);
                PE.setText(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        Random r = new Random();
        int aux = 0;
        if (view.equals(findViewById(R.id.ccPVRoll))) {
            aux = r.nextInt(20) + raceStats[7] + classStats[7];
            if (aux < 10) aux = 10;
            PV.setText(String.valueOf(aux));
        } else {
            aux = r.nextInt(20) + raceStats[8] + classStats[8];
            if (aux < 10) aux = 10;
            PE.setText(String.valueOf(aux));
        }
    }

    public void max(View view) {
        if (view.equals(findViewById(R.id.ccPVMax))) {
            PV.setText(String.valueOf(20 + raceStats[7] + classStats[7]));
        }
        else PE.setText(String.valueOf(20 + raceStats[8] + classStats[8]));
    }
}

