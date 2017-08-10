package com.example.chorryigas.bismillahtugasakhir.Model;

/**
 * Created by Choy on 8/6/2017.
 */

public class ModelBooking {
    public int id;
    public String id_user;
    public String id_guru;
    public String status;

    public ModelBooking(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
