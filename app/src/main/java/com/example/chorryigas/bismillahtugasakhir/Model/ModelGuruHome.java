package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Choy on 8/9/2017.
 */

public class ModelGuruHome implements Parcelable {
    public String id_guru;
    public String foto;
    public String nama_guru;
    public String alamat;
    public String no_telp;
    public String kampus;
    public String jurusan;
    public int pengalaman;
    public String jarak;

    public ModelGuruHome(){}
    
    public ModelGuruHome(Parcel in) {
        id_guru = in.readString();
        foto = in.readString();
        nama_guru = in.readString();
        alamat = in.readString();
        no_telp = in.readString();
        kampus = in.readString();
        jurusan = in.readString();
        pengalaman = in.readInt();
        jarak = in.readString();
    }

    public static final Creator<ModelGuruHome> CREATOR = new Creator<ModelGuruHome>() {
        @Override
        public ModelGuruHome createFromParcel(Parcel in) {
            return new ModelGuruHome(in);
        }

        @Override
        public ModelGuruHome[] newArray(int size) {
            return new ModelGuruHome[size];
        }
    };

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

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_guru);
        dest.writeString(foto);
        dest.writeString(nama_guru);
        dest.writeString(alamat);
        dest.writeString(no_telp);
        dest.writeString(kampus);
        dest.writeString(jurusan);
        dest.writeInt(pengalaman);
        dest.writeString(jarak);
    }
}
