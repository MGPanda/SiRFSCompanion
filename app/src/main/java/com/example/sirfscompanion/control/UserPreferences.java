package com.example.sirfscompanion.control;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class UserPreferences {
    private SharedPreferences _sp;
    public UserPreferences(Context c) {
        _sp = c.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }
    public void setNightMode(Boolean b) {
        SharedPreferences.Editor editor = _sp.edit();
        if (b) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        editor.putBoolean("NightMode",b);
        editor.apply();
    }
    public boolean getNightMode() {
        boolean b = _sp.getBoolean("NightMode", false);
        return b;
    }
}
