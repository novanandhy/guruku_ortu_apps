package com.example.chorryigas.bismillahtugasakhir.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.Model.Jadwal;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelJadwal;
import com.example.chorryigas.bismillahtugasakhir.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choy on 7/31/2017.
 */

public class RVAdapter_JadwalGuru extends RecyclerView.Adapter<RVAdapter_JadwalGuru.ViewHolder> {
    private List<ModelJadwal> dataJadwal;
    private Context context;

    public RVAdapter_JadwalGuru(Context context, List<ModelJadwal> inputData) {
        this.dataJadwal = inputData;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView hari_ajar;
        public TextView jam_mulai;
        public TextView jam_selesai;

        public ViewHolder(View v) {
            super(v);
            hari_ajar = (TextView) v.findViewById(R.id.hari_ajar);
            jam_mulai = (TextView) v.findViewById(R.id.jam_mulai);
            jam_selesai = (TextView) v.findViewById(R.id.jam_selesai);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jadwal_guru, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        RVAdapter_JadwalGuru.ViewHolder vh = new RVAdapter_JadwalGuru.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.hari_ajar.setText(dataJadwal.get(position).getHari());
        holder.jam_mulai.setText(dataJadwal.get(position).getJam_mulai());
        holder.jam_selesai.setText(dataJadwal.get(position).getJam_selesai());
    }

    @Override
    public int getItemCount() {
        return dataJadwal.size();
    }
}
