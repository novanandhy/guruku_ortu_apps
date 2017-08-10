package com.example.chorryigas.bismillahtugasakhir.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choy on 8/2/2017.
 */

public class ModelGuru implements Parcelable{
    public String id_guru;
    public String nama;
    public String alamat;
    public String no_telp;
    public String kelamin;
    public String email;
    public String pendidikan;
    public int pengalaman;
    public String deskripsi;
    public String lat;
    public String lng;
    public String foto;
    public String jenjang_guru;
    public String jurusan;
    public String kampus;
    public double ipk;
    public String usia;
    private List<ModelSkill> skill;
    private List<ModelRating> rating;
    private List<ModelJadwal> jadwal;

    public ModelGuru(){}


    public ModelGuru(String id_guru, JSONObject jsonObject){
        this.id_guru = id_guru;
        try {
            this.nama = jsonObject.getString("nama");
            this.alamat = jsonObject.getString("alamat");
            this.no_telp = jsonObject.getString("no_telp");
            this.kelamin = jsonObject.getString("kelamin");
            this.pendidikan = jsonObject.getString("pendidikan");
            this.pengalaman = Integer.valueOf(jsonObject.getInt("pengalaman"));
            this.deskripsi = jsonObject.getString("deskripsi");
            this.foto = jsonObject.getString("foto");
            this.lat = jsonObject.getString("lat");
            this.lng = jsonObject.getString("lng");
            this.usia = jsonObject.getString("usia");
            this.ipk = Double.valueOf(jsonObject.getDouble("ipk"));
            this.kampus = jsonObject.getString("kampus");
            this.jurusan = jsonObject.getString("jurusan");

            JSONArray jsonArraySkill = jsonObject.getJSONArray("skill");
            this.skill = new ArrayList<>();
            if(jsonArraySkill!=null && jsonArraySkill.length()>0){
                for(int i=0;i<jsonArraySkill.length();i++)
                    this.skill.add(new ModelSkill(jsonArraySkill.getJSONObject(i)));
            }
            this.jadwal = new ArrayList<>();
            JSONArray jsonArrayJadwal = jsonObject.getJSONArray("jadwal");
            if(jsonArrayJadwal!=null && jsonArrayJadwal.length()>0){
                for(int i=0;i<jsonArrayJadwal.length();i++)
                    this.jadwal.add(new ModelJadwal(jsonArrayJadwal.getJSONObject(i)));
            }
            this.rating = new ArrayList<>();
            JSONArray jsonArrayRating = jsonObject.getJSONArray("rating");
            if(jsonArrayRating!=null && jsonArrayRating.length()>0){
                for (int i=0;i<jsonArrayRating.length();i++)
                    this.rating.add(new ModelRating(jsonArrayRating.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<ModelSkill> getSkill() {
        return skill;
    }

    public void setSkill(List<ModelSkill> skill) {
        this.skill = skill;
    }

    public List<ModelRating> getRating() {
        return rating;
    }

    public void setRating(List<ModelRating> rating) {
        this.rating = rating;
    }

    public List<ModelJadwal> getJadwal() {
        return jadwal;
    }

    public void setJadwal(List<ModelJadwal> jadwal) {
        this.jadwal = jadwal;
    }

    protected ModelGuru(Parcel in) {
        id_guru = in.readString();
        nama = in.readString();
        alamat = in.readString();
        no_telp = in.readString();
        kelamin = in.readString();
        email = in.readString();
        pendidikan = in.readString();
        pengalaman = in.readInt();
        deskripsi = in.readString();
        lat = in.readString();
        lng = in.readString();
        foto = in.readString();
        jenjang_guru = in.readString();
        jurusan = in.readString();
        kampus = in.readString();
        ipk = in.readDouble();
        usia = in.readString();
        skill = in.createTypedArrayList(ModelSkill.CREATOR);
        rating = in.createTypedArrayList(ModelRating.CREATOR);
        jadwal = in.createTypedArrayList(ModelJadwal.CREATOR);
    }

    public static final Creator<ModelGuru> CREATOR = new Creator<ModelGuru>() {
        @Override
        public ModelGuru createFromParcel(Parcel in) {
            return new ModelGuru(in);
        }

        @Override
        public ModelGuru[] newArray(int size) {
            return new ModelGuru[size];
        }
    };

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

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

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public int getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(int pengalaman) {
        this.pengalaman = pengalaman;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJenjang_guru() {
        return jenjang_guru;
    }

    public void setJenjang_guru(String jenjang_guru) {
        this.jenjang_guru = jenjang_guru;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKampus() {
        return kampus;
    }

    public void setKampus(String kampus) {
        this.kampus = kampus;
    }

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_guru);
        dest.writeString(nama);
        dest.writeString(alamat);
        dest.writeString(no_telp);
        dest.writeString(kelamin);
        dest.writeString(email);
        dest.writeString(pendidikan);
        dest.writeInt(pengalaman);
        dest.writeString(deskripsi);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(foto);
        dest.writeString(jenjang_guru);
        dest.writeString(jurusan);
        dest.writeString(kampus);
        dest.writeDouble(ipk);
        dest.writeString(usia);
        dest.writeTypedList(skill);
        dest.writeTypedList(rating);
        dest.writeTypedList(jadwal);
    }
}
