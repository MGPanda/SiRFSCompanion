package com.example.sirfscompanion.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirfscompanion.R;
import com.example.sirfscompanion.instanciables.Char;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerAdapterInventory extends RecyclerView.Adapter<RecyclerAdapterInventory.ViewHolder> {
    private Context _c;
    private ArrayList<String> _al;
    private Char _ch;

    public RecyclerAdapterInventory(Context c, Char ch) {
        this._c = c;
        this._ch = ch;
        this._al = new ArrayList<>();
        String[] inv = ch.getCharInventory().split("XNEWX");
        if (!inv[0].equals("")) {
            _al.addAll(Arrays.asList(inv));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(_c);
        View v = li.inflate(R.layout.rowinventory, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return _al.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _itemName, _itemType, _itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._itemName = itemView.findViewById(R.id.itemName);
            this._itemType = itemView.findViewById(R.id.itemType);
            this._itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }

        public void bind() {
            String[] item = _al.get(getAdapterPosition()).split("XPARTX");
            this._itemName.setText(item[0]);
            this._itemType.setText(item[1]);
            this._itemQuantity.setText(item[2]);
            this.itemView.findViewById(R.id.itemSub).setOnClickListener(v -> {
                if (item[2].equals("1")) {
                    _al.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                } else {
                    _al.set(getAdapterPosition(), item[0] + "XPARTX" + item[1] + "XPARTX" + (Integer.parseInt(item[2]) - 1));
                    notifyItemChanged(getAdapterPosition());
                }
                String ninv = "";
                for (int i = 0; i < _al.size(); i++) {
                    if (i != 0) ninv += "XNEWX";
                    ninv += _al.get(i);
                }
                _ch.setCharInventory(ninv);
                MyDB.updateInv(_ch.getCharId(), ninv);
            });
            this.itemView.findViewById(R.id.itemAdd).setOnClickListener(v -> {
                _al.set(getAdapterPosition(), item[0] + "XPARTX" + item[1] + "XPARTX" + (Integer.parseInt(item[2]) + 1));
                notifyItemChanged(getAdapterPosition());
                String ninv = "";
                for (int i = 0; i < _al.size(); i++) {
                    if (i != 0) ninv += "XNEWX";
                    ninv += _al.get(i);
                }
                _ch.setCharInventory(ninv);
                MyDB.updateInv(_ch.getCharId(), ninv);
            });
        }
    }

    public void addNew(String s) {
        _al.add(s);
        notifyItemInserted(getItemCount());
    }
}