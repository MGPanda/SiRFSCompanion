package com.example.sirfscompanion.instanciables;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Char implements Serializable {
    private int CHAR_ID;
    private String CHAR_NAME;
    private byte[] CHAR_IMG;
    private String CHAR_CRDATE;
    private int CHAR_LEVEL;
    private String CHAR_RACE;
    private String CHAR_CLASS;
    private int CHAR_FUE;
    private int CHAR_DES;
    private int CHAR_PUN;
    private int CHAR_INT;
    private int CHAR_SAB;
    private int CHAR_AGI;
    private int CHAR_VOL;
    private int CHAR_PV;
    private int CHAR_MAXPV;
    private int CHAR_PE;
    private int CHAR_MAXPE;
    private int CHAR_ARMOR;
    private int CHAR_MARMOR;
    private int CHAR_CRITBONUS;
    private int CHAR_CRITDMGBONUS;
    private int CHAR_SPELLBONUS;
    private String CHAR_WEAPONS;
    private String CHAR_EQUIP;
    private String CHAR_RELICS;
    private String CHAR_SKILLS;
    private String CHAR_BONUS;
    private int CHAR_GOLD;
    private String CHAR_INVENTORY;

    public Char(Cursor cu) {
        this.CHAR_ID = cu.getInt(0);
        this.CHAR_NAME = cu.getString(1);
        this.CHAR_IMG = cu.getBlob(2);
        this.CHAR_CRDATE = cu.getString(3);
        this.CHAR_LEVEL = cu.getInt(4);
        this.CHAR_RACE = cu.getString(5);
        this.CHAR_CLASS = cu.getString(6);
        this.CHAR_FUE = cu.getInt(7);
        this.CHAR_DES = cu.getInt(8);
        this.CHAR_PUN = cu.getInt(9);
        this.CHAR_INT = cu.getInt(10);
        this.CHAR_SAB = cu.getInt(11);
        this.CHAR_AGI = cu.getInt(12);
        this.CHAR_VOL = cu.getInt(13);
        this.CHAR_PV = cu.getInt(14);
        this.CHAR_MAXPV = cu.getInt(15);
        this.CHAR_PE = cu.getInt(16);
        this.CHAR_MAXPE = cu.getInt(17);
        this.CHAR_ARMOR = cu.getInt(18);
        this.CHAR_MARMOR = cu.getInt(19);
        this.CHAR_CRITBONUS = cu.getInt(20);
        this.CHAR_CRITDMGBONUS = cu.getInt(21);
        this.CHAR_SPELLBONUS = cu.getInt(22);
        this.CHAR_WEAPONS = cu.getString(23);
        this.CHAR_EQUIP = cu.getString(24);
        this.CHAR_RELICS = cu.getString(25);
        this.CHAR_SKILLS = cu.getString(26);
        this.CHAR_BONUS = cu.getString(27);
        this.CHAR_GOLD = cu.getInt(28);
        this.CHAR_INVENTORY = cu.getString(29);
    }

    public Char(int id, String name, Bitmap img, String date, int level, String race, String mClass, int fue, int des, int pun, int mInt, int sab, int agi, int vol, int pv, int maxpv, int pe, int maxpe, int armor, int marmor, int critbonus,
                int critdmgbonus, int spellbonus, String weapons, String equip, String relics, String skills, String bonus, int gold, String inventory) {
        this.CHAR_ID = id;
        this.CHAR_NAME = name;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        this.CHAR_IMG = baos.toByteArray();
        this.CHAR_CRDATE = date;
        this.CHAR_LEVEL = level;
        this.CHAR_RACE = race;
        this.CHAR_CLASS = mClass;
        this.CHAR_FUE = fue;
        this.CHAR_DES = des;
        this.CHAR_PUN = pun;
        this.CHAR_INT = mInt;
        this.CHAR_SAB = sab;
        this.CHAR_AGI = agi;
        this.CHAR_VOL = vol;
        this.CHAR_PV = pv;
        this.CHAR_MAXPV = maxpv;
        this.CHAR_PE = pe;
        this.CHAR_MAXPE = maxpe;
        this.CHAR_ARMOR = armor;
        this.CHAR_MARMOR = marmor;
        this.CHAR_CRITBONUS = critbonus;
        this.CHAR_CRITDMGBONUS = critdmgbonus;
        this.CHAR_SPELLBONUS = spellbonus;
        this.CHAR_WEAPONS = weapons;
        this.CHAR_EQUIP = equip;
        this.CHAR_RELICS = relics;
        this.CHAR_SKILLS = skills;
        this.CHAR_BONUS = bonus;
        this.CHAR_GOLD = gold;
        this.CHAR_INVENTORY = inventory;
    }

    public int getCharId() {
        return CHAR_ID;
    }

    public void setCharId(int charId) {
        CHAR_ID = charId;
    }

    public String getCharName() {
        return CHAR_NAME;
    }

    public void setCharName(String charName) {
        CHAR_NAME = charName;
    }

    public byte[] getCharImg() {
        return CHAR_IMG;
    }

    public void setCharImg(byte[] charImg) {
        CHAR_IMG = charImg;
    }

    public String getCharDate() {
        return CHAR_CRDATE;
    }

    public void setCharDate(String charDate) {
        CHAR_CRDATE = charDate;
    }

    public int getCharLevel() {
        return CHAR_LEVEL;
    }

    public void setCharLevel(int charLevel) {
        CHAR_LEVEL = charLevel;
    }

    public String getCharRace() {
        return CHAR_RACE;
    }

    public void setCharRace(String charRace) {
        CHAR_RACE = charRace;
    }

    public String getCharClass() {
        return CHAR_CLASS;
    }

    public void setCharClass(String charClass) {
        CHAR_CLASS = charClass;
    }

    public int getCharFue() {
        return CHAR_FUE;
    }

    public void setCharFue(int charFue) {
        CHAR_FUE = charFue;
    }

    public int getCharDes() {
        return CHAR_DES;
    }

    public void setCharDes(int charDes) {
        CHAR_DES = charDes;
    }

    public int getCharPun() {
        return CHAR_PUN;
    }

    public void setCharPun(int charPun) {
        CHAR_PUN = charPun;
    }

    public int getCharInt() {
        return CHAR_INT;
    }

    public void setCharInt(int charInt) {
        CHAR_INT = charInt;
    }

    public int getCharSab() {
        return CHAR_SAB;
    }

    public void setCharSab(int charSab) {
        CHAR_SAB = charSab;
    }

    public int getCharAgi() {
        return CHAR_AGI;
    }

    public void setCharAgi(int charAgi) {
        CHAR_AGI = charAgi;
    }

    public int getCharVol() {
        return CHAR_VOL;
    }

    public void setCharVol(int charVol) {
        CHAR_VOL = charVol;
    }

    public int getCharPv() {
        return CHAR_PV;
    }

    public void setCharPv(int charPv) {
        CHAR_PV = charPv;
    }

    public int getCharMaxpv() {
        return CHAR_MAXPV;
    }

    public void setCharMaxpv(int charMaxpv) {
        CHAR_MAXPV = charMaxpv;
    }

    public int getCharPe() {
        return CHAR_PE;
    }

    public void setCharPe(int charPe) {
        CHAR_PE = charPe;
    }

    public int getCharMaxpe() {
        return CHAR_MAXPE;
    }

    public void setCharMaxpe(int charMaxpe) {
        CHAR_MAXPE = charMaxpe;
    }

    public int getCharArmor() {
        return CHAR_ARMOR;
    }

    public void setCharArmor(int charArmor) {
        CHAR_ARMOR = charArmor;
    }

    public int getCharMarmor() {
        return CHAR_MARMOR;
    }

    public void setCharMarmor(int charMarmor) {
        CHAR_MARMOR = charMarmor;
    }

    public int getCharCritbonus() {
        return CHAR_CRITBONUS;
    }

    public void setCharCritbonus(int charCritbonus) {
        CHAR_CRITBONUS = charCritbonus;
    }

    public int getCharCritdmgbonus() {
        return CHAR_CRITDMGBONUS;
    }

    public void setCharCritdmgbonus(int charCritdmgbonus) {
        CHAR_CRITDMGBONUS = charCritdmgbonus;
    }

    public int getCharSpellbonus() {
        return CHAR_SPELLBONUS;
    }

    public void setCharSpellbonus(int charSpellbonus) {
        CHAR_SPELLBONUS = charSpellbonus;
    }

    public String getCharWeapons() {
        return CHAR_WEAPONS;
    }

    public void setCharWeapons(String charWeapons) {
        CHAR_WEAPONS = charWeapons;
    }

    public String getCharEquip() {
        return CHAR_EQUIP;
    }

    public void setCharEquip(String charEquip) {
        CHAR_EQUIP = charEquip;
    }

    public String getCharRelics() {
        return CHAR_RELICS;
    }

    public void setCharRelics(String charRelics) {
        CHAR_RELICS = charRelics;
    }

    public String getCharSkills() {
        return CHAR_SKILLS;
    }

    public void setCharSkills(String charSkills) {
        CHAR_SKILLS = charSkills;
    }

    public String getCharBonus() {
        return CHAR_BONUS;
    }

    public void setCharBonus(String charBonus) {
        CHAR_BONUS = charBonus;
    }

    public int getCharGold() {
        return CHAR_GOLD;
    }

    public void setCharGold(int charGold) {
        CHAR_GOLD = charGold;
    }

    public String getCharInventory() {
        return CHAR_INVENTORY;
    }

    public void setCharInventory(String charInventory) {
        CHAR_INVENTORY = charInventory;
    }
}
