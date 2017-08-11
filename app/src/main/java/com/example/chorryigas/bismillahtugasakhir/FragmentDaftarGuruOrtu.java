package com.example.chorryigas.bismillahtugasakhir;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_ListGuruOrtu;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_daftar_guru_ortu,null,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Guru dipesan");

        tampilGuru(view);
        return view;
    }

    private void tampilGuru(View view){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_daftarguru);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        RVAdapter_ListGuruOrtu adapter = new RVAdapter_ListGuruOrtu(getActivity(),SplashActivity.mBooking);
        recyclerView.setAdapter(adapter);
    }

}

