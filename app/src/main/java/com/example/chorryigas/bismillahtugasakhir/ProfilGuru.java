package com.example.chorryigas.bismillahtugasakhir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelGuruHome;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;
import com.squareup.picasso.Picasso;

public class ProfilGuru extends Activity {

    private Context context;
    private SessionManager sessionManager;

    private ImageView foto_profil;
    private TextView nama_guru, alamat_guru, telp_guru, kampus, jurusan;
    private ImageButton keahlian,jadwal,rating;

    private ProgressDialog progressDialog;

    private String nama, alamat, telp, kmps, jrusan, foto, id_guru;
    private String TAG = "TAG ProfilGuru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guru);
        context = this;

        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);

        nama_guru = (TextView) findViewById(R.id.nama_guru);
        alamat_guru = (TextView) findViewById(R.id.alamat_guru);
        telp_guru = (TextView) findViewById(R.id.telp_guru);
        kampus = (TextView) findViewById(R.id.kampus_guru);
        jurusan = (TextView) findViewById(R.id.jurusan_guru);
        foto_profil = (ImageView) findViewById(R.id.foto_guru);
        keahlian = (ImageButton) findViewById(R.id.lihat_keahlian);
        jadwal = (ImageButton) findViewById(R.id.lihat_jadwal);
        rating = (ImageButton) findViewById(R.id.beri_review);

        ambilDataIntent();
        setData();
        lihatKeahlian(id_guru);
        lihatJadwal(id_guru);
        lihatReview(id_guru);
    }


    private void lihatKeahlian(final String id_guru) {
        keahlian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilGuru.this, LihatKeahlian.class);
                intent.putExtra("id_user", id_guru);
                startActivity(intent);
            }
        });
    }

    private void lihatJadwal(final String id_guru) {
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilGuru.this, LihatJadwal.class);
                intent.putExtra("id_user", id_guru);
                startActivity(intent);
            }
        });
    }

    private void lihatReview(final String id_guru) {
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilGuru.this, LihatRating.class);
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
        ModelGuruHome guru = new ModelGuruHome();
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
}
