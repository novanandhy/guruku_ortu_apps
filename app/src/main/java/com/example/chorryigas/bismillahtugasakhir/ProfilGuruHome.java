package com.example.chorryigas.bismillahtugasakhir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelGuruHome;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilGuruHome extends Activity {
    private Context context;
    private SessionManager sessionManager;

    private ImageView foto_profil;
    private TextView nama_guru, alamat_guru, telp_guru, kampus, jurusan;
    private ImageButton keahlian,jadwal,rating;
    private Button pesan;

    private ProgressDialog progressDialog;
    private SplashActivity splashActivity;

    ModelGuruHome guru = new ModelGuruHome();
    private String nama, alamat, telp, kmps, jrusan, foto, id_guru;
    private String TAG = "TAG ProfilGuruHome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guru_home);
        context = this;

        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        splashActivity = new SplashActivity();

        nama_guru = (TextView) findViewById(R.id.nama_guru);
        alamat_guru = (TextView) findViewById(R.id.alamat_guru);
        telp_guru = (TextView) findViewById(R.id.telp_guru);
        kampus = (TextView) findViewById(R.id.kampus_guru);
        jurusan = (TextView) findViewById(R.id.jurusan_guru);
        foto_profil = (ImageView) findViewById(R.id.foto_guru);
        keahlian = (ImageButton) findViewById(R.id.lihat_keahlian);
        jadwal = (ImageButton) findViewById(R.id.lihat_jadwal);
        rating = (ImageButton) findViewById(R.id.beri_review);
        pesan = (Button) findViewById(R.id.btn_pesan);

        ambilDataIntent();
        setData();
        lihatKeahlian(id_guru);
        lihatJadwal(id_guru);
        lihatReview(id_guru);
        ambilGuru();
    }

    private void ambilGuru() {
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingGuru(sessionManager.getKeyId(),id_guru);
            }
        });
    }


    private void lihatKeahlian(final String id_guru) {
        keahlian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilGuruHome.this, LihatKeahlian.class);
                intent.putExtra("id_user", id_guru);
                startActivity(intent);
            }
        });
    }

    private void lihatJadwal(final String id_guru) {
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilGuruHome.this, LihatJadwal.class);
                intent.putExtra("id_user", id_guru);
                startActivity(intent);
            }
        });
    }

    private void lihatReview(final String id_guru) {
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilGuruHome.this, LihatRating.class);
                intent.putExtra("id_user", id_guru);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        nama_guru.setText(nama);
        telp_guru.setText(telp);
        alamat_guru.setText(alamat);
        kampus.setText(kmps);
        jurusan.setText(jrusan);
        Picasso.with(context).load(Server.URLpath+"upload/"+foto).into(foto_profil);
    }

    private void ambilDataIntent() {
        Intent intent = getIntent();
        guru = intent.getExtras().getParcelable("guru");

        nama = guru.getNama_guru();
        alamat = guru.getAlamat();
        telp = guru.getNo_telp();
        kmps = guru.getKampus();
        jrusan = guru.getJurusan();
        foto = guru.getFoto();
        id_guru = guru.getId_guru();
    }

    private void bookingGuru(final String id_user, final String id_guru) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        progressDialog.setCancelable(false);
        progressDialog.setMessage("rating...");
        progressDialog.show();

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.BOOKING_CREATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get daftar guru Response: " + response.toString());

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    progressDialog.dismiss();
                    //cek apakah JSON memiliki indeks error yg true atau tidak
                    if(!error){
                        splashActivity.GetBooking(sessionManager.getKeyId(),"0");
                        Toast.makeText(context, "Berhasil memesan guru", Toast.LENGTH_SHORT).show();

                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(ProfilGuruHome.this, errorMsg, Toast.LENGTH_SHORT).show();
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
                params.put("id_user", id_user);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
