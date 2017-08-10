package com.example.chorryigas.bismillahtugasakhir.Adapter;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelPencarian;
import com.example.chorryigas.bismillahtugasakhir.ProfilGuru;
import com.example.chorryigas.bismillahtugasakhir.R;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        public TextView ambil_guru;
        public TextView lihat_profil;
        public ImageView imageView;
        public TextView pengalaman, jarak, biaya;
        public RatingBar rating;

        public ViewHolder(View itemView){
            super(itemView);

            nama_guru = (TextView)itemView.findViewById(R.id.nama_guru);
            pend_guru = (TextView) itemView.findViewById(R.id.pend_guru);
            imageView = (ImageView) itemView.findViewById(R.id.foto_guru);
            pengalaman = (TextView) itemView.findViewById(R.id.peng_guru);
            jarak = (TextView) itemView.findViewById(R.id.jarak_guru);
            biaya = (TextView) itemView.findViewById(R.id.biaya_guru);
            rating = (RatingBar) itemView.findViewById(R.id.rating_guru);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.list_daftar_guru_ortu, parent, false);

        sessionManager = new SessionManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter_hasilcariguru.ViewHolder holder, final int position){
        holder.nama_guru.setText(gurus.get(position).getNama());
        holder.pend_guru.setText(gurus.get(position).getJenjang());
        holder.pengalaman.setText(gurus.get(position).getPengalaman());
        holder.jarak.setText(String.valueOf(gurus.get(position).getJarak()));
        holder.biaya.setText(gurus.get(position).getBiaya());
        holder.rating.setRating(gurus.get(position).getRating());

        holder.lihat_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilGuru.class);
                intent.putExtra("guru", (Parcelable) gurus.get(position));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        Picasso.with(context).invalidate(Server.URLpath+"upload/"+gurus.get(position).getFoto());
        Picasso.with(context).load(Server.URLpath+"upload/"+gurus.get(position).getFoto()).into(holder.imageView);

        holder.ambil_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilGuru(sessionManager.getKeyId(),gurus.get(position).getId_guru());
            }
        });
    }

    private void ambilGuru(final String id_user, final String id_guru) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST, Server.BOOKING_CREATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "masuk response");
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                Toast.makeText(context, "Guru berhasil di pesan", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(context,"Update gagal",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "ora kenek", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Koneksi bermasalah",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user",""+id_user);
                params.put("id_guru",""+id_guru);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public int getItemCount(){
        return gurus.size();
    }
}
