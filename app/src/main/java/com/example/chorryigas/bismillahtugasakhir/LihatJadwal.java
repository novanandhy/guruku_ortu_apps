package com.example.chorryigas.bismillahtugasakhir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_JadwalGuru;
import com.example.chorryigas.bismillahtugasakhir.Model.Jadwal;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelJadwal;

import java.util.ArrayList;
import java.util.List;

public class LihatJadwal extends AppCompatActivity {
    private final String hari_ajar[] = {
            "SD",
            "SD",
            "SMP",
            "SMP",
            "SMP",
            "SMA",
            "SMA"
    };
    private final String jam_mulai[] = {
            "50.000",
            "40.000",
            "65.000",
            "70.000",
            "30.000",
            "10.000",
            "39.000"
    };
    private final String jam_selesai[] = {
            "Semua Mata Pelajaran",
            "TK",
            "Matematika",
            "IPA",
            "IPA",
            "Matematika",
            "Matematika"
    };

    private List<ModelJadwal> jadwals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_jadwal);
        jadwals = getIntent().getParcelableArrayListExtra("jadwal");
        tampilJadwal();
    }

    private void tampilJadwal(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list_jadwal);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm  = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        RVAdapter_JadwalGuru adapter = new RVAdapter_JadwalGuru(this,jadwals );
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Jadwal> prepareData(){
        ArrayList<Jadwal> jadwals = new ArrayList<>();
        for(int i=0; i<hari_ajar.length;i++){
            Jadwal jadwal = new Jadwal();
            jadwal.setHari_ajar(hari_ajar[i]);
            jadwal.setJam_mulai(jam_mulai[i]);
            jadwal.setJam_selesai(jam_selesai[i]);
            jadwals.add(jadwal);
        }
        return jadwals;
    }
}
