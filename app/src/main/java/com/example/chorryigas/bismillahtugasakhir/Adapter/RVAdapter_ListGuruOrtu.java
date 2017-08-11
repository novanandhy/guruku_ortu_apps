package com.example.chorryigas.bismillahtugasakhir.Adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelBooking;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelGuruHome;
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

import static android.content.ContentValues.TAG;

/**
 * Created by Choy on 6/7/2017.
 */

public class RVAdapter_ListGuruOrtu extends RecyclerView.Adapter<RVAdapter_ListGuruOrtu.ViewHolder> {
    private ArrayList<ModelBooking> gurus;
    private Context context;
    private SessionManager sessionManager;

    public RVAdapter_ListGuruOrtu(Context context, ArrayList<ModelBooking> gurus){
        this.context = context;
        this.gurus = gurus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nama_guru;
        public TextView pend_guru;
        public ImageView foto_guru;
        public TextView status_booking;
        public TextView cancel_action;
        public RatingBar ratingBar;

        public ViewHolder(View itemView){
            super(itemView);

            nama_guru = (TextView)itemView.findViewById(R.id.nama_guru);
            pend_guru = (TextView) itemView.findViewById(R.id.pend_guru);
            foto_guru = (ImageView) itemView.findViewById(R.id.foto_guru);
            status_booking = (TextView) itemView.findViewById(R.id.status_booking);
            cancel_action = (TextView) itemView.findViewById(R.id.cancel_action);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_guru);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.list_guruortu, parent, false);

        sessionManager = new SessionManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter_ListGuruOrtu.ViewHolder holder, final int position){
        holder.nama_guru.setText(gurus.get(position).getNama());
        holder.pend_guru.setText(gurus.get(position).getPendidikan());
        holder.ratingBar.setRating(gurus.get(position).getRating());

        if (gurus.get(position).getStatus().contains("0")){
            holder.status_booking.setText("Belum Terkonfirmasi");
            holder.status_booking.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }else {
            holder.status_booking.setText("Terkonfirmasi");
            holder.status_booking.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }

        Picasso.with(context).invalidate(Server.URLpath+"upload/"+gurus.get(position).getFoto());
        Picasso.with(context).load(Server.URLpath+"upload/"+gurus.get(position).getFoto()).into(holder.foto_guru);

        final ModelGuruHome modelGuruHome = new ModelGuruHome();

        modelGuruHome.setId_guru(gurus.get(position).getId_guru());
        modelGuruHome.setAlamat(gurus.get(position).getAlamat());
        modelGuruHome.setFoto(gurus.get(position).getFoto());
        modelGuruHome.setJurusan(gurus.get(position).getJurusan());
        modelGuruHome.setKampus(gurus.get(position).getKampus());
        modelGuruHome.setNama_guru(gurus.get(position).getNama());
        modelGuruHome.setNo_telp(gurus.get(position).getNo_telp());

        holder.foto_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilGuru.class);
                intent.putExtra("guru", modelGuruHome);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBooking(sessionManager.getKeyId(),gurus.get(position).getId_guru(),position);
            }
        });


    }

    private void cancelBooking(final String id_user, final String id_guru, final int position) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.BOOKING_CANCEL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get data booking Response: " + response.toString());

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    //cek apakah JSON memiliki indeks error yg true atau tidak
                    if(!error){

                        gurus.remove(position);

                        notifyDataSetChanged();

                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context,"gagal membatalkan pesanan", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                params.put("id_guru", id_guru);
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
