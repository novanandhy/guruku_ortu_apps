package com.example.chorryigas.bismillahtugasakhir.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelRating;
import com.example.chorryigas.bismillahtugasakhir.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Choy on 7/31/2017.
 */

public class RVAdapter_RatingGuru extends RecyclerView.Adapter<RVAdapter_RatingGuru.ViewHolder> {
    private List<ModelRating> reviews;
    private Context context;

    public RVAdapter_RatingGuru(Context context, List<ModelRating> reviews){
        this.context = context;
        this.reviews = reviews;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView review_guru;
        public TextView nama_guru;
        public ImageView imageView;
        public RatingBar ratingBar;

        public ViewHolder(View itemView){
            super(itemView);
            review_guru = (TextView) itemView.findViewById(R.id.reviewortu);
            nama_guru = (TextView) itemView.findViewById(R.id.namaortu);
            imageView = (ImageView) itemView.findViewById(R.id.foto_ortu);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_review);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.list_rating_guru, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter_RatingGuru.ViewHolder holder, int position){
        holder.review_guru.setText(reviews.get(position).getReview());
        holder.nama_guru.setText(reviews.get(position).getNama());
        holder.ratingBar.setRating(reviews.get(position).getRating());

        Picasso.with(context).invalidate(Server.URLpath + "upload/" + reviews.get(position).getFoto());
        Picasso.with(context).load(Server.URLpath + "upload/" + reviews.get(position).getFoto()).into(holder.imageView);
    }

    public int getItemCount(){
        return reviews.size();
    }
}
