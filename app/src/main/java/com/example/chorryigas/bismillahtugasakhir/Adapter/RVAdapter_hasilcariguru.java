package com.example.chorryigas.bismillahtugasakhir.Adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelGuruHome;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelPencarian;
import com.example.chorryigas.bismillahtugasakhir.ProfilGuruHome;
import com.example.chorryigas.bismillahtugasakhir.R;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Choy on 6/7/2017.
 */

public class RVAdapter_hasilcariguru extends RecyclerView.Adapter<RVAdapter_hasilcariguru.ViewHolder> {
    private ArrayList<ModelPencarian> gurus;
    private Context context;
    private SessionManager sessionManager;
    private String TAG = "TAG_RVAdapter_HasilCariGuru";

    public RVAdapter_hasilcariguru(Context context, ArrayList<ModelPencarian> gurus){
        this.context = context;
        this.gurus = gurus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nama_guru;
        public TextView pend_guru;
        public ImageView imageView;
        public View parent;
        public TextView pengalaman, jarak, biaya;
        public CardView cv;
        public RatingBar ratingBar;

        public ViewHolder(View itemView){
            super(itemView);
            parent = itemView;
            nama_guru = (TextView)itemView.findViewById(R.id.nama_guru);
            pend_guru = (TextView) itemView.findViewById(R.id.pend_guru);
            imageView = (ImageView) itemView.findViewById(R.id.foto_guru);
            pengalaman = (TextView) itemView.findViewById(R.id.peng_guru);
            jarak = (TextView) itemView.findViewById(R.id.jarak_guru);
            biaya = (TextView) itemView.findViewById(R.id.biaya_guru);
            cv = (CardView) itemView.findViewById(R.id.cv_main);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_guru);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.list_daftar_guru_ortu, parent, false);
        sessionManager = new SessionManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.nama_guru.setText(gurus.get(position).getNama());
        holder.pend_guru.setText(gurus.get(position).getJenjang());
        holder.pengalaman.setText(gurus.get(position).getPengalaman());
        holder.jarak.setText(String.valueOf(gurus.get(position).getJarak()));
        holder.biaya.setText(gurus.get(position).getBiaya());
        holder.ratingBar.setRating(gurus.get(position).getRating());

        Picasso.with(context).invalidate(Server.URLpath+"upload/"+gurus.get(position).getFoto());
        Picasso.with(context).load(Server.URLpath+"upload/"+gurus.get(position).getFoto()).into(holder.imageView);

        final ModelGuruHome modelGuruHome = new ModelGuruHome();

        modelGuruHome.setId_guru(gurus.get(position).getId_guru());
        modelGuruHome.setAlamat(gurus.get(position).getAlamat());
        modelGuruHome.setJarak(gurus.get(position).getJarak());
        modelGuruHome.setPengalaman(Integer.parseInt(gurus.get(position).getPengalaman()));
        modelGuruHome.setFoto(gurus.get(position).getFoto());
        modelGuruHome.setJurusan(gurus.get(position).getJurusan());
        modelGuruHome.setKampus(gurus.get(position).getKampus());
        modelGuruHome.setNama_guru(gurus.get(position).getNama());
        modelGuruHome.setNo_telp(gurus.get(position).getNo_telp());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilGuruHome.class);
                intent.putExtra("guru", modelGuruHome);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    public int getItemCount(){
        return gurus.size();
    }
}
