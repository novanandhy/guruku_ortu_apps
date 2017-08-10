package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Choy on 8/2/2017.
 */

public class ModelRating implements Parcelable {
    public String id_user;
    public Double rating;
    public String review;
    public String nama;
    public String foto;

    public ModelRating(){}

    public ModelRating(JSONObject jsonObject){
        try {
            this.id_user = jsonObject.getString("id_user");
            this.foto = jsonObject.getString("foto");
            this.review = jsonObject.getString("review");
            this.nama = jsonObject.getString("nama");
            this.rating = Double.valueOf(jsonObject.getDouble("rating"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected ModelRating(Parcel in) {
        id_user = in.readString();
        review = in.readString();
        nama = in.readString();
        foto = in.readString();
    }

    public static final Creator<ModelRating> CREATOR = new Creator<ModelRating>() {
        @Override
        public ModelRating createFromParcel(Parcel in) {
            return new ModelRating(in);
        }

        @Override
        public ModelRating[] newArray(int size) {
            return new ModelRating[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_user);
        dest.writeString(review);
        dest.writeString(nama);
        dest.writeString(foto);
    }
}
