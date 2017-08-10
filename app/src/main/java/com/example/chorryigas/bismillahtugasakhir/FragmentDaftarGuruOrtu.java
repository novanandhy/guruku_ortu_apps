package com.example.chorryigas.bismillahtugasakhir;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_ListGuruOrtu;
import com.example.chorryigas.bismillahtugasakhir.Model.Guru;

import java.util.ArrayList;

public class FragmentDaftarGuruOrtu extends Fragment {

    public static FragmentDaftarGuruOrtu newInstance(){
        FragmentDaftarGuruOrtu fragment = new FragmentDaftarGuruOrtu();
        return fragment;
    }

    public static FragmentDaftarGuruOrtu newInstance(String strArg) {
        FragmentDaftarGuruOrtu fragment = new FragmentDaftarGuruOrtu();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    private final String nama_guru[] = {
            "Chorry",
            "Elya",
            "Tyas",
            "Lutfi",
            "Tatik",
            "Lintang",
            "Febrina"
    };
    private final String pend_guru[] = {
            "D3",
            "D3",
            "D3",
            "D3",
            "D3",
            "S1",
            "S1"
    };
    private final String materi_guru[] = {
            "Semua Mata Pelajaran",
            "TK",
            "Matematika",
            "IPA",
            "IPA",
            "Matematika",
            "Matematika"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_daftar_guru_ortu,null,false);
        tampilGuru(view);
        return view;
    }

    private void tampilGuru(View view){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_daftarguru);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        ArrayList<Guru> gurus = prepareData();
        RVAdapter_ListGuruOrtu adapter = new RVAdapter_ListGuruOrtu(getActivity(),gurus);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Guru> prepareData(){
        ArrayList<Guru> gurus = new ArrayList<>();
        for(int i=0; i<nama_guru.length;i++){
            Guru guru = new Guru();
            guru.setNama_guru(nama_guru[i]);
            guru.setPend_guru(pend_guru[i]);
            guru.setMateri_guru(materi_guru[i]);
            gurus.add(guru);
        }
        return gurus;
    }
}

