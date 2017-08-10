package com.example.chorryigas.bismillahtugasakhir;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_JadwalGuru;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelJadwal;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LihatJadwal extends AppCompatActivity {

    private List<ModelJadwal> jadwals;
    private String id_guru;
    private String TAG = "TAG_LihatJadwal";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_jadwal);
        jadwals = new ArrayList<>();
        progressDialog = new ProgressDialog(this);


        id_guru = getIntent().getExtras().getString("id_user");
        getJadwal(id_guru);
        tampilJadwal(jadwals);
    }

    private void getJadwal(final String id_guru) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        progressDialog.setCancelable(false);
        progressDialog.setMessage("jadwal...");
        progressDialog.show();

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.JADWAL_GET_BY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get daftar guru Response: " + response.toString());

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    progressDialog.dismiss();
                    //cek apakah JSON memiliki indeks error yg true atau tidak
                    if(!error){
                        //menangkap indeks array JSON "user"
                        JSONArray list = jObj.getJSONArray("user");

                        //mengambil setiap data di setiap indeks JSON
                        for(int i=0; i<list.length(); i++){
                            JSONObject details = list.getJSONObject(i);

                            ModelJadwal mJadwal = new ModelJadwal();

                            mJadwal.setId_jadwal(details.getInt("id"));
                            mJadwal.setId_guru(details.getString("id_guru"));
                            mJadwal.setHari(details.getString("hari"));
                            mJadwal.setJam_mulai(details.getString("jam_mulai"));
                            mJadwal.setJam_selesai(details.getString("jam_selesai"));

                            jadwals.add(mJadwal);
                        }

                        tampilJadwal(jadwals);

                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(LihatJadwal.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("id_guru", id_guru);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void tampilJadwal(List<ModelJadwal> jadwals){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list_jadwal);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm  = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        RVAdapter_JadwalGuru adapter = new RVAdapter_JadwalGuru(this, jadwals);
        recyclerView.setAdapter(adapter);
    }
}
