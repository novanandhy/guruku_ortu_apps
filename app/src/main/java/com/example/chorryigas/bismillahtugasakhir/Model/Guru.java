package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Choy on 6/5/2017.
 */

public class Guru implements Parcelable {
    private String nama_guru;
    private String pend_guru;
    private String materi_guru;

    protected Guru(Parcel in) {
        nama_guru = in.readString();
        pend_guru = in.readString();
        materi_guru = in.readString();
    }

    public Guru(){

    }

    public static final Creator<Guru> CREATOR = new Creator<Guru>() {
        @Override
        public Guru createFromParcel(Parcel in) {
            return new Guru(in);
        }

        @Override
        public Guru[] newArray(int size) {
            return new Guru[size];
        }
    };

    public String getNama_guru(){
        return nama_guru;
    }
    public void setNama_guru(String nama_guru){
        this.nama_guru = nama_guru;
    }

    public String getPend_guru(){
        return pend_guru;
    }
    public void setPend_guru (String pend_guru){
        this.pend_guru = pend_guru;
    }

    public String getMateri_guru(){
        return materi_guru;
    }
    public void setMateri_guru(String materi_guru){
        this.materi_guru = materi_guru;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama_guru);
        dest.writeString(pend_guru);
        dest.writeString(materi_guru);
    }
}
