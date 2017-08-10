package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Choy on 8/9/2017.
 */

public class ModelLowonganPribadi implements Parcelable {
    private String id;
    private String id_user;
    private String subjek;
    private String description;

    protected ModelLowonganPribadi(Parcel in) {
        id = in.readString();
        id_user = in.readString();
        subjek = in.readString();
        description = in.readString();
    }

    public ModelLowonganPribadi(){

    }

    public static final Creator<ModelLowonganPribadi> CREATOR = new Creator<ModelLowonganPribadi>() {
        @Override
        public ModelLowonganPribadi createFromParcel(Parcel in) {
            return new ModelLowonganPribadi(in);
        }

        @Override
        public ModelLowonganPribadi[] newArray(int size) {
            return new ModelLowonganPribadi[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(id_user);
        dest.writeString(subjek);
        dest.writeString(description);
    }
}
