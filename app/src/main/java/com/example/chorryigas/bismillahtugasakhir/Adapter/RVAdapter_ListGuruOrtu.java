package com.example.chorryigas.bismillahtugasakhir.Adapter;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.Model.Guru;
import com.example.chorryigas.bismillahtugasakhir.ProfilGuru;
import com.example.chorryigas.bismillahtugasakhir.R;
import com.example.chorryigas.bismillahtugasakhir.Rating_guru_ortu;

import java.util.ArrayList;
/**
 * Created by Choy on 6/7/2017.
 */

public class RVAdapter_ListGuruOrtu extends RecyclerView.Adapter<RVAdapter_ListGuruOrtu.ViewHolder> {
    private ArrayList<Guru> gurus;
    private Context context;

    public RVAdapter_ListGuruOrtu(Context context, ArrayList<Guru> gurus){
        this.context = context;
        this.gurus = gurus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nama_guru;
        public TextView pend_guru;
        public TextView materi_guru;
        public TextView beri_review;
        public ImageView foto_guru;
        public TextView status_booking;
        public TextView cancel_action;

        public ViewHolder(View itemView){
            super(itemView);

            nama_guru = (TextView)itemView.findViewById(R.id.nama_guru);
            pend_guru = (TextView) itemView.findViewById(R.id.pend_guru);
            materi_guru = (TextView) itemView.findViewById(R.id.materi_guru);
            beri_review = (TextView) itemView.findViewById(R.id.beri_review);
            foto_guru = (ImageView) itemView.findViewById(R.id.foto_guru);
            status_booking = (TextView) itemView.findViewById(R.id.status_booking);
            cancel_action = (TextView) itemView.findViewById(R.id.cancel_action);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.list_guruortu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter_ListGuruOrtu.ViewHolder holder, final int position){
        holder.nama_guru.setText(gurus.get(position).getNama_guru());
        holder.pend_guru.setText(gurus.get(position).getPend_guru());
        holder.materi_guru.setText(gurus.get(position).getMateri_guru());
        holder.beri_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Rating_guru_ortu.class);
                intent.putExtra("guru",gurus.get(position));
                context.startActivity(intent);
            }
        });

        holder.foto_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilGuru.class);
                intent.putExtra("guru", gurus.get(position));
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount(){
        return gurus.size();
    }
}
