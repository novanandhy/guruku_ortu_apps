package com.example.chorryigas.bismillahtugasakhir.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.EditLowongan;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.HasilCariGuru;
import com.example.chorryigas.bismillahtugasakhir.HomeActivity;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowongan;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowonganPribadi;
import com.example.chorryigas.bismillahtugasakhir.ProfilGuru;
import com.example.chorryigas.bismillahtugasakhir.R;
import com.example.chorryigas.bismillahtugasakhir.SplashActivity;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Choy on 8/8/2017.
 */

public class RVAdapter_ListLowongan extends RecyclerView.Adapter<RVAdapter_ListLowongan.ViewHolder> {
    private ArrayList<ModelLowonganPribadi> dataLowongan;
    private Context context;

    public RVAdapter_ListLowongan(Context context, ArrayList<ModelLowonganPribadi> inputData){
        this.dataLowongan = inputData;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView judul_lowongan;
        public TextView isi_lowongan;
        public TextView edit_lowongan;
        public TextView hapus_lowongan;
        public AlertDialog.Builder dialog;

        public ViewHolder (View v){
            super(v);
            judul_lowongan = (TextView)v.findViewById(R.id.judul_lowongan);
            isi_lowongan = (TextView) v.findViewById(R.id.isi_lowongan);
            edit_lowongan = (TextView) v.findViewById(R.id.edit_lowongan);
            hapus_lowongan = (TextView) v.findViewById(R.id.hapus_lowongan);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lowongan, parent, false);
        RVAdapter_ListLowongan.ViewHolder vh = new RVAdapter_ListLowongan.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RVAdapter_ListLowongan.ViewHolder holder, final int position) {
        holder.judul_lowongan.setText(dataLowongan.get(position).getSubjek());
        holder.isi_lowongan.setText(dataLowongan.get(position).getDescription());
        holder.edit_lowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditLowongan.class);
                Log.d("Guru","size : "+dataLowongan.get(position));
                intent.putExtra("lowongan", dataLowongan.get(position));
                intent.putExtra("position", position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.hapus_lowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusLowongan(dataLowongan.get(position).getId(), dataLowongan.get(position).getId_user(),position);
            }
        });
    }

    private void hapusLowongan(final String id, final String id_user, final int position) {
        Log.d(TAG, "masuk ubahdata: ");
        //Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST, Server.LOWONGAN_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "masuk response");
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                SplashActivity.mLowongan.remove(position);

                                notifyDataSetChanged();
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
                params.put("id",id);
                params.put("id_user",id_user);
                Log.d(TAG, "getParams: "+params);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public int getItemCount() {
        return dataLowongan.size();
    }
}
