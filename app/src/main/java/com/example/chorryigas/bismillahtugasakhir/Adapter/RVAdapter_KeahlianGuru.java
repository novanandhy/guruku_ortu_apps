package com.example.chorryigas.bismillahtugasakhir.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.Model.Keahlian;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelSkill;
import com.example.chorryigas.bismillahtugasakhir.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choy on 7/31/2017.
 */

public class RVAdapter_KeahlianGuru extends RecyclerView.Adapter<RVAdapter_KeahlianGuru.ViewHolder> {
    private List<ModelSkill> dataKeahlian;
    private Context context;

    public RVAdapter_KeahlianGuru(Context context, List<ModelSkill> inputData) {
        this.dataKeahlian = inputData;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_keahlianguru, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        RVAdapter_KeahlianGuru.ViewHolder vh = new RVAdapter_KeahlianGuru.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.jenjang_pendidikan.setText(dataKeahlian.get(position).getJenjang());
        holder.mata_pelajaran.setText(dataKeahlian.get(position).getMapel());
        holder.harga_temuan.setText(dataKeahlian.get(position).getBiaya());
    }

    @Override
    public int getItemCount() {
        return dataKeahlian.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView jenjang_pendidikan;
        public TextView mata_pelajaran;
        public TextView harga_temuan;

        public ViewHolder(View v) {
            super(v);
            jenjang_pendidikan = (TextView) v.findViewById(R.id.jenjang_murid);
            mata_pelajaran = (TextView) v.findViewById(R.id.matpel_guru);
            harga_temuan = (TextView) v.findViewById(R.id.harga_temuan);
        }
    }
}
