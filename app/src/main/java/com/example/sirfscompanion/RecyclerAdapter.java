package com.example.sirfscompanion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context _c;
    private Cursor _cu;
    private ArrayList<Char> _al;
    public RecyclerAdapter(Context c, Cursor cu) {
        this._c = c;
        this._cu = cu;
        this._al = new ArrayList<Char>();
        do {
            _al.add(new Char(_cu));
        } while (_cu.moveToNext());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(_c);
        View v = li.inflate(R.layout.row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(_al.get(position));
    }

    @Override
    public int getItemCount() {
        return _al.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView _civ;
        private TextView _name, _date, _raceClass;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._civ = itemView.findViewById(R.id.imageView);
            this._name = itemView.findViewById(R.id.listCharName);
            this._date = itemView.findViewById(R.id.listDate);
            this._raceClass = itemView.findViewById(R.id.listRaceClass);
        }
        public void bind(final Char c) {
            this._civ.setImageBitmap(BitmapFactory.decodeByteArray(c.getCharImg(), 0, c.getCharImg().length));
            this._name.setText(c.getCharName());
            this._date.setText(c.getCharDate());
            this._raceClass.setText(String.format("%s %s %d", c.getCharRace(), c.getCharClass(), c.getCharLevel()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(_c, Detail.class);
                    i.putExtra("CHAR", c);
                    _c.startActivity(i);
                }
            });
        }
    }
}
/*
package com.example.finalexam;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context _c;
    private Cursor _cu;
    private ArrayList<TODO> _al;

    public RecyclerAdapter(Context c, Cursor cu) {
        this._c = c;
        this._cu = cu;
        this._al = new ArrayList<TODO>();
        do {
            _al.add(new TODO(_cu.getInt(0), _cu.getString(1), _cu.getString(2), _cu.getString(3)));
        } while (_cu.moveToNext());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(_c);
        View v = li.inflate(R.layout.row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(_al.get(position));
    }

    @Override
    public int getItemCount() {
        return _al.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _tv1, _tv2;
        private CheckBox _cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _tv1 = itemView.findViewById(R.id.taskName);
            _tv2 = itemView.findViewById(R.id.date);
            _cb = itemView.findViewById(R.id.checkBox);
        }

        public void bind(final TODO t) {
            _tv1.setText(t.getName());
            _tv2.setText(t.getDate());
            if (t.isComplete().equals("true")) {
                _cb.setChecked(true);
                _cb.setEnabled(false);
            } else _cb.setChecked(false);
            _cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MyDB.completeItem(t.get_id(), t.getName(), t.getDate(), "true");
                    MainActivity.getMa().setAdapter();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(_c, Detail.class);
                    i.putExtra("ID", t.get_id());
                    i.putExtra("NAME", t.getName());
                    i.putExtra("DATE", t.getDate());
                    i.putExtra("ISCOMPLETE", t.isComplete());
                    _c.startActivity(i);
                }
            });
        }
    }
}

 */