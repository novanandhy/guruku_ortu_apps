package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Choy on 8/2/2017.
 */

public class ModelLowongan implements Parcelable{
    public int id_lowongan;
    public String id_user;
    public String subjek;
    public String deskripsi;
    public String nama;

    public ModelLowongan(){}

    protected ModelLowongan(Parcel in) {
        id_lowongan = in.readInt();
        id_user = in.readString();
        subjek = in.readString();
        deskripsi = in.readString();
        nama = in.readString();
    }

    public static final Creator<ModelLowongan> CREATOR = new Creator<ModelLowongan>() {
        @Override
        public ModelLowongan createFromParcel(Parcel in) {
            return new ModelLowongan(in);
        }

        @Override
        public ModelLowongan[] newArray(int size) {
            return new ModelLowongan[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId_lowongan() {
        return id_lowongan;
    }

    public void setId_lowongan(int id_lowongan) {
        this.id_lowongan = id_lowongan;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getSubjek() {
        return subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_lowongan);
        dest.writeString(id_user);
        dest.writeString(subjek);
        dest.writeString(deskripsi);
        dest.writeString(nama);
    }
}
