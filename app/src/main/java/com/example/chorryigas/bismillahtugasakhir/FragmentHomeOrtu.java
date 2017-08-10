package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayoutManager;

import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_homeortu;

import java.util.ArrayList;


public class FragmentHomeOrtu extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private Context context;
    private String TAG = "TAG_FragmentHomeOrtu";
   // Button logout;

    public static FragmentHomeOrtu newInstance(){
        FragmentHomeOrtu fragment = new FragmentHomeOrtu();
        return fragment;
    }

    public static FragmentHomeOrtu newInstance(String strArg) {
        FragmentHomeOrtu fragment = new FragmentHomeOrtu();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_ortu,null,false);
        context = getActivity().getApplicationContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.grid_guru);
        recyclerView.setHasFixedSize(true);
        //Tampilan Grid View
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RVAdapter_homeortu(context,SplashActivity.mGuruHome);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
