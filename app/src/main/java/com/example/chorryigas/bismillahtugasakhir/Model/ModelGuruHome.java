package com.example.chorryigas.bismillahtugasakhir.Model;

import android.widget.ImageView;

/**
 * Created by Choy on 8/9/2017.
 */

public class ModelGuruHome {
    public String id_guru;
    public String foto;
    public String nama_guru;
    public String alamat;
    public String no_telp;
    public String kampus;
    public String jurusan;
    public int pengalaman;


    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getKampus() {
        return kampus;
    }

    public void setKampus(String kampus) {
        this.kampus = kampus;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getNama_guru() {
        return nama_guru;
    }

    public void setNama_guru(String nama_guru) {
        this.nama_guru = nama_guru;
    }

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(int pengalaman) {
        this.pengalaman = pengalaman;
    }
}
