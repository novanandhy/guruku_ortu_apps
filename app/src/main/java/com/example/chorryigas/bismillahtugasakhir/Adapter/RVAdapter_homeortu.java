package com.example.chorryigas.bismillahtugasakhir.Adapter;

/**
 * Created by Choy on 6/2/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelGuruHome;
import com.example.chorryigas.bismillahtugasakhir.ProfilGuru;
import com.example.chorryigas.bismillahtugasakhir.ProfilGuruHome;
import com.example.chorryigas.bismillahtugasakhir.R;
import com.example.chorryigas.bismillahtugasakhir.SplashActivity;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVAdapter_homeortu extends RecyclerView.Adapter<RVAdapter_homeortu.ViewHolder>{
    private ArrayList<ModelGuruHome> guru;
    private Context context;
    private SplashActivity splashActivity;
    private SessionManager sessionManager;

    private String TAG = "TAG AdapterHomeOrtu";



    public RVAdapter_homeortu(Context context, ArrayList<ModelGuruHome> inputData){
        guru = inputData;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView namaguru;
        public TextView pengalaman;
        public ImageView foto_guru;


        public ViewHolder(View v){
            super(v);
            namaguru = (TextView) v.findViewById(R.id.nama_guru);
            pengalaman = (TextView) v.findViewById(R.id.pengalaman_guru);
            foto_guru = (ImageView) v.findViewById(R.id.foto_guru);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_guru, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        RVAdapter_homeortu.ViewHolder vh = new RVAdapter_homeortu.ViewHolder(v);

        splashActivity = new SplashActivity();
        sessionManager = new SessionManager(context);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut

        holder.namaguru.setText(guru.get(position).getNama_guru());
        holder.pengalaman.setText(guru.get(position).getPengalaman());
        Picasso.with(context).invalidate(Server.URLpath + "upload/" + guru.get(position).getFoto());
        Picasso.with(context).load(Server.URLpath + "upload/" + guru.get(position).getFoto()).into(holder.foto_guru);

        holder.foto_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilGuruHome.class);
                intent.putExtra("nama_guru",guru.get(position).getNama_guru());
                intent.putExtra("alamat",guru.get(position).getAlamat());
                intent.putExtra("no_telp",guru.get(position).getNo_telp());
                intent.putExtra("kampus",guru.get(position).getKampus());
                intent.putExtra("jurusan",guru.get(position).getJurusan());
                intent.putExtra("foto",guru.get(position).getFoto());

                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return guru.size();
    }
}
