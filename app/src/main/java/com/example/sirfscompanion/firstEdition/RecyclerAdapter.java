package com.example.sirfscompanion.firstEdition;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sirfscompanion.R;
import com.example.sirfscompanion.control.MainActivity;
import com.example.sirfscompanion.control.MyDB;
import com.example.sirfscompanion.instanciables.Char;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context _c;
    private ArrayList<Char> _al;
    public RecyclerAdapter(Context c) {
        this._c = c;
        this._al = new ArrayList<>();
        try {
            Cursor _cu = MyDB.selectAll();
            do {
                _al.add(new Char(_cu));
            } while (_cu.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            Glide.with(_c).load(c.getCharImg()).asBitmap().into(_civ);
            this._name.setText(c.getCharName());
            this._date.setText(c.getCharDate());
            this._raceClass.setText(String.format("%s %s %d", c.getCharRace(), c.getCharClass(), c.getCharLevel()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(_c, Detail.class);
                    i.putExtra("CHAR", c);
                    i.putExtra("POSITION", getAdapterPosition());
                    MainActivity.get_ma().startActivityForResult(i, 0);
                }
            });
        }
    }

    public void deleteItem(RecyclerView.ViewHolder vh, final int position) {
        Char ch = _al.get(position);
        Snackbar s = Snackbar.make(vh.itemView, "¿Seguro que quieres eliminar a " + ch.getCharName() + "?", Snackbar.LENGTH_SHORT).setAction("Sí", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDB.delete(_al.get(position).getCharId());
                _al.remove(position);
                notifyItemRemoved(position);
            }
        });
        s.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                notifyItemChanged(position);
            }
        });
        s.show();
    }

    public int addNew(Char c) {
        _al.add(0, c);
        return 0;
    }

    public void updateChar(Char c, int position) {
        MyDB.updateChar(c);
        _al.set(position, c);
    }
}