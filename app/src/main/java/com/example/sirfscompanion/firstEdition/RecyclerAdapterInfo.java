package com.example.sirfscompanion.firstEdition;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirfscompanion.R;
import com.example.sirfscompanion.control.MainActivity;
import com.example.sirfscompanion.instanciables.Char;

import java.util.ArrayList;

public class RecyclerAdapterInfo extends RecyclerView.Adapter<RecyclerAdapterInfo.ViewHolder> {
    private Context _c;
    private int _p;
    private ArrayList<String> _al;
    public RecyclerAdapterInfo(Context c, Char ch) {
        this._c = c;
        this._al = new ArrayList<>();
        String[] races = MainActivity.get_ma().getResources().getStringArray(R.array.races1e);
        for (int i = 0; i < races.length; i++) {
            if (races[i].equals(ch.getCharRace())) _p = i;
        }
        _al.add(races[_p]);
        _al.add(MainActivity.get_ma().getResources().getStringArray(R.array.racesDesc1e)[_p]);
        String[] racesBonus = MainActivity.get_ma().getResources().getStringArray(R.array.racesBonus1e)[_p].split("XNEWX");
        String[] racesBonusDesc = MainActivity.get_ma().getResources().getStringArray(R.array.racesBonusDesc1e)[_p].split("XNEWX");
        for (int i = 0; i < racesBonus.length; i++) {
            _al.add(racesBonus[i]);
            _al.add(racesBonusDesc[i]);
        }
        _al.add(MainActivity.get_ma().getResources().getStringArray(R.array.classes1e)[_p]);
        _al.add(MainActivity.get_ma().getResources().getStringArray(R.array.classesDesc1e)[_p]);
        String[] mySkills = ch.getCharSkills().split(" ");
        String[] skills = MainActivity.get_ma().getResources().getStringArray(R.array.classesSkills1e)[_p].split("XNEWX");
        String[] skillsDesc = MainActivity.get_ma().getResources().getStringArray(R.array.classesSkillsDesc1e)[_p].split("XNEWX");
        for (int i = 0; i < mySkills.length; i++) {
            _al.add(skills[Integer.parseInt(mySkills[i])]);
            _al.add(skillsDesc[Integer.parseInt(mySkills[i])]);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(_c);
        View v = li.inflate(R.layout.rowinfo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return _al.size()/2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _infoTitle, _infoDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._infoTitle = itemView.findViewById(R.id.infoTitle);
            this._infoDesc = itemView.findViewById(R.id.infoDesc);
        }
        public void bind(final int position) {
            this._infoTitle.setText(Html.fromHtml(_al.get(position*2)));
            this._infoDesc.setText(Html.fromHtml(_al.get((position*2)+1)));
        }
    }
}