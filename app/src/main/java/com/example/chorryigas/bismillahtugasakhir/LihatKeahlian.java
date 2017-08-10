package com.example.chorryigas.bismillahtugasakhir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_KeahlianGuru;
import com.example.chorryigas.bismillahtugasakhir.Model.Keahlian;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelSkill;

import java.util.ArrayList;
import java.util.List;

public class LihatKeahlian extends AppCompatActivity {

    private List<ModelSkill> skills;

    private final String jenjang_murid[] = {
            "SD",
            "SD",
            "SMP",
            "SMP",
            "SMP",
            "SMA",
            "SMA"
    };
    private final String harga_temuan[] = {
            "50.000",
            "40.000",
            "65.000",
            "70.000",
            "30.000",
            "10.000",
            "39.000"
    };
    private final String mata_pelajaran[] = {
            "Semua Mata Pelajaran",
            "TK",
            "Matematika",
            "IPA",
            "IPA",
            "Matematika",
            "Matematika"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_keahlian);
        skills = getIntent().getParcelableArrayListExtra("skill");
        tampilKeahlian();
    }

    private void tampilKeahlian(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list_keahlian);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm  = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        RVAdapter_KeahlianGuru adapter = new RVAdapter_KeahlianGuru(this, skills);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Keahlian> prepareData(){
        ArrayList<Keahlian> keahlians = new ArrayList<>();
        for(int i=0; i<jenjang_murid.length;i++){
            Keahlian keahlian = new Keahlian();
            keahlian.setJenjang_murid(jenjang_murid[i]);
            keahlian.setMata_pelajaran(mata_pelajaran[i]);
            keahlian.setHarga_temuan(harga_temuan[i]);
            keahlians.add(keahlian);
        }
        return keahlians;
    }
}
