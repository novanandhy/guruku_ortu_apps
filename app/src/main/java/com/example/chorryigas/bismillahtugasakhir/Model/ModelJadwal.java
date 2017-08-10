package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Choy on 8/2/2017.
 */

public class ModelJadwal implements Parcelable {
    public int id_jadwal;
    public String id_guru;
    public String hari;
    public String jam_mulai;
    public String jam_selesai;

    public ModelJadwal(){}

    public ModelJadwal(JSONObject jsonObject){
        try {
            this.id_jadwal = Integer.valueOf(jsonObject.getInt("id"));
            this.hari = jsonObject.getString("hari");
            this.id_guru = jsonObject.getString("id_guru");
            this.jam_mulai = jsonObject.getString("jam_mulai");
            this.jam_selesai = jsonObject.getString("jam_selesai");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected ModelJadwal(Parcel in) {
        id_jadwal = in.readInt();
        id_guru = in.readString();
        hari = in.readString();
        jam_mulai = in.readString();
        jam_selesai = in.readString();
    }

    public static final Creator<ModelJadwal> CREATOR = new Creator<ModelJadwal>() {
        @Override
        public ModelJadwal createFromParcel(Parcel in) {
            return new ModelJadwal(in);
        }

        @Override
        public ModelJadwal[] newArray(int size) {
            return new ModelJadwal[size];
        }
    };

    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_jadwal);
        dest.writeString(id_guru);
        dest.writeString(hari);
        dest.writeString(jam_mulai);
        dest.writeString(jam_selesai);
    }
}
