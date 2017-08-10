package com.example.chorryigas.bismillahtugasakhir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.Guru;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelGuru;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelPencarian;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfilGuru extends AppCompatActivity {

    private ImageView lihat_keahlian;
    private ImageView lihat_jadwal;
    private ImageView lihat_rating;

    private String id_guru;
    private ModelGuru modelGuru;

    TextView text_namaGuru;
    TextView text_usiaGuru;
    TextView text_kelaminGuru;
    TextView text_alamatGuru;
    TextView text_pendGuru;
    TextView text_ipkGuru;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guru);

        id_guru = getIntent().getStringExtra("id_guru");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Memuat data guru");
        lihat_keahlian = (ImageView)findViewById(R.id.lihat_keahlian);
        lihat_jadwal = (ImageView)findViewById(R.id.lihat_jadwal);
        lihat_rating = (ImageView)findViewById(R.id.beri_review);
        text_namaGuru = (TextView) findViewById(R.id.nama_guru);
        text_alamatGuru = (TextView) findViewById(R.id.alamat_guru);
        text_ipkGuru = (TextView) findViewById(R.id.ipk_guru);
        text_pendGuru = (TextView) findViewById(R.id.pend_guru);
        text_usiaGuru = (TextView) findViewById(R.id.usia_guru);
        text_kelaminGuru = (TextView) findViewById(R.id.jenis_kelamin);
        buttonKeahian();
        buttonJadwal();
        buttonRating();

        initGuru();

    }

    private void initGuru() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Server.GURU_SELECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(!jsonResponse.getBoolean("error")){
                                modelGuru = new ModelGuru(id_guru,jsonResponse.getJSONObject("user"));
                                text_alamatGuru.setText(modelGuru.getAlamat());
                                text_ipkGuru.setText(""+modelGuru.getIpk());
                                text_namaGuru.setText(modelGuru.getNama());
                                text_pendGuru.setText(modelGuru.getPendidikan());
                                text_usiaGuru.setText(modelGuru.getUsia());
                                text_kelaminGuru.setText(modelGuru.getKelamin());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProfilGuru.this,"Parsing data failed",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ProfilGuru.this,"Koneksi bermasalah",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_guru",id_guru);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
    }


    private void buttonKeahian(){
        lihat_keahlian.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ProfilGuru.this, LihatKeahlian.class);
                intent.putParcelableArrayListExtra("skill", (ArrayList<? extends Parcelable>) modelGuru.getSkill());
                startActivity(intent);
            }
        });
    }

    private void buttonJadwal(){
        lihat_jadwal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ProfilGuru.this, LihatJadwal.class);
                intent.putParcelableArrayListExtra("jadwal", (ArrayList<? extends Parcelable>) modelGuru.getJadwal());
                startActivity(intent);
            }
        });
    }

    private void buttonRating(){
        lihat_rating.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ProfilGuru.this, LihatRating.class);
                intent.putParcelableArrayListExtra("rating", (ArrayList<? extends Parcelable>) modelGuru.getRating());
                intent.putExtra("id_guru",modelGuru.getId_guru());
                startActivity(intent);
            }
        });
    }
}
