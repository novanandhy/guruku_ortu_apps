package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.squareup.picasso.Picasso;

public class ProfilGuruHome extends AppCompatActivity {
    private Context context;

    private ImageView foto_profil;
    private TextView nama_guru, alamat_guru, telp_guru, kampus, jurusan;

    private String nama, alamat, telp, kmps, jrusan, foto;
    private String TAG = "TAG ProfilGuruHome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guru_home);
        context = this;

        nama_guru = (TextView) findViewById(R.id.nama_guru);
        alamat_guru = (TextView) findViewById(R.id.alamat_guru);
        telp_guru = (TextView) findViewById(R.id.telp_guru);
        kampus = (TextView) findViewById(R.id.kampus_guru);
        jurusan = (TextView) findViewById(R.id.jurusan_guru);
        foto_profil = (ImageView) findViewById(R.id.foto_guru);

        ambilDataIntent();
        setData();
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

        nama = intent.getExtras().getString("nama_guru");
        alamat = intent.getExtras().getString("alamat");
        telp = intent.getExtras().getString("no_telp");
        kmps = intent.getExtras().getString("kampus");
        jrusan = intent.getExtras().getString("jurusan");
        foto = intent.getExtras().getString("foto");
    }
}
