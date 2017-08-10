package com.example.chorryigas.bismillahtugasakhir.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.Model.ModelRating;
import com.example.chorryigas.bismillahtugasakhir.Model.Review;
import com.example.chorryigas.bismillahtugasakhir.R;

import java.util.ArrayList;
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

        public ViewHolder(View itemView){
            super(itemView);
            review_guru = (TextView) itemView.findViewById(R.id.reviewortu);
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
    }

    public int getItemCount(){
        return reviews.size();
    }
}
