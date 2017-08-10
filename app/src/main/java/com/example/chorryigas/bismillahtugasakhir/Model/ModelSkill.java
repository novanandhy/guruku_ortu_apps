package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Choy on 8/2/2017.
 */

public class ModelSkill implements Parcelable {
    public int id_skill;
    public String id_guru;
    public String jenjang;
    public String mapel;
    public String biaya;

    public ModelSkill(){}

    public ModelSkill(JSONObject jsonObject){
        try {
            this.id_skill = Integer.valueOf(jsonObject.getInt("id"));
            this.id_guru = jsonObject.getString("id_guru");
            this.jenjang = jsonObject.getString("jenjang");
            this.mapel = jsonObject.getString("mapel");
            this.biaya = jsonObject.getString("biaya");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected ModelSkill(Parcel in) {
        id_skill = in.readInt();
        id_guru = in.readString();
        jenjang = in.readString();
        mapel = in.readString();
        biaya = in.readString();
    }

    public static final Creator<ModelSkill> CREATOR = new Creator<ModelSkill>() {
        @Override
        public ModelSkill createFromParcel(Parcel in) {
            return new ModelSkill(in);
        }

        @Override
        public ModelSkill[] newArray(int size) {
            return new ModelSkill[size];
        }
    };

    public int getId_skill() {
        return id_skill;
    }

    public void setId_skill(int id_skill) {
        this.id_skill = id_skill;
    }

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getJenjang() {
        return jenjang;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_skill);
        dest.writeString(id_guru);
        dest.writeString(jenjang);
        dest.writeString(mapel);
        dest.writeString(biaya);
    }
}
