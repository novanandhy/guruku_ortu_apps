package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FragmentCariGuru extends Fragment {
    private Spinner spinner_biaya, spinner_pengalaman, spinner_jarak;
    private TextView next;

    private Context context;

    private String TAG = "TAG_FragmentCariGuru";
    private String biaya,pengalaman,jarak;
    private int biaya_param, pengalaman_param, jarak_param;


    public static FragmentCariGuru newInstance(){
        FragmentCariGuru fragment = new FragmentCariGuru();
        return fragment;
    }

    public static FragmentCariGuru newInstance(String strArg) {
        FragmentCariGuru fragment = new FragmentCariGuru();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_kriteria_orang_tua,null,false);
        context = getActivity().getApplicationContext();

        getItemId(view);
        setSpinnerBiaya();
        setSpinnerPengalaman();
        setSpinnerJarak();

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CariSelanjutnya();
            }
        });
        return view;
    }

    private void setSpinnerJarak() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.fuzzy_jarak, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_jarak.setAdapter(adapter);
        spinner_jarak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil nilai jenjang
                jarak = (String) parent.getItemAtPosition(position);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerPengalaman() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.fuzzy_pengalaman, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pengalaman.setAdapter(adapter);
        spinner_pengalaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil nilai jenjang
                pengalaman = (String) parent.getItemAtPosition(position);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerBiaya() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.fuzzy_biaya, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_biaya.setAdapter(adapter);
        spinner_biaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil nilai jenjang
                biaya = (String) parent.getItemAtPosition(position);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getItemId(View view) {
        next = (TextView) view.findViewById(R.id.next);
        spinner_biaya = (Spinner) view.findViewById(R.id.spinner_biaya);
        spinner_jarak = (Spinner) view.findViewById(R.id.spinner_jarak);
        spinner_pengalaman = (Spinner) view.findViewById(R.id.spinner_pengalaman);
    }

    public void CariSelanjutnya(){
        getValue();
        Intent intent = new Intent(getActivity(), Cari_guru.class);
        intent.putExtra("biaya_param", biaya_param);
        intent.putExtra("pengalaman_param", pengalaman_param);
        intent.putExtra("jarak_param", jarak_param);
        startActivity(intent);
    }

    private void getValue() {
        if (biaya.contains("Murah")){
            biaya_param = 0;
        }else if (biaya.contains("Sedang")){
            biaya_param = 1;
        }else if (biaya.contains("Mahal")){
            biaya_param = 2;
        }

        if (pengalaman.contains("Sedikit")){
            pengalaman_param = 0;
        }else if (pengalaman.contains("Sedang")){
            pengalaman_param = 1;
        }else if (pengalaman.contains("Banyak")){
            pengalaman_param = 2;
        }

        if (jarak.contains("Dekat")){
            jarak_param = 0;
        }else if (jarak.contains("Sedang")){
            jarak_param = 1;
        }else if (jarak.contains("Jauh")){
            jarak_param = 2;
        }
    }
}
