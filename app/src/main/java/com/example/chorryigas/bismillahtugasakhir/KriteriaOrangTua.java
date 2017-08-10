package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class KriteriaOrangTua extends AppCompatActivity {
    private EditText biaya_fuzzy,pengalaman_fuzzy,jarak_fuzzy;
    private TextView cari;

    private Context context;

    private String kelamin,jenjang,mapel,hari;
    private int biaya_param, pengalaman_param, jarak_param;
    private float biaya_min, biaya_mid, biaya_max;
    private int pengalaman_min, pengalaman_mid, pengalaman_max;
    private float jarak_min, jarak_mid, jarak_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_fuzzy_guru);

        context = this;

        getItemId();

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCari();
            }
        });
    }

    private void getItemId() {
        biaya_fuzzy = (EditText) findViewById(R.id.biaya_fuzzy);
        pengalaman_fuzzy = (EditText) findViewById(R.id.pengalaman_fuzzy);
        jarak_fuzzy = (EditText) findViewById(R.id.jarak_fuzzy);
        cari = (TextView) findViewById(R.id.cari_guru);
    }

    private void buttonCari(){
        getValue();
        getValueFromIntent();

        Intent intent = new Intent(KriteriaOrangTua.this, HasilCariGuru.class);

        intent.putExtra("biaya_param",biaya_param);
        intent.putExtra("pengalaman_param",pengalaman_param);
        intent.putExtra("jarak_param",jarak_param);
        intent.putExtra("kelamin",kelamin);
        intent.putExtra("jenjang",jenjang);
        intent.putExtra("mapel",mapel);
        intent.putExtra("hari",hari);
        intent.putExtra("biaya_min",biaya_min);
        intent.putExtra("biaya_mid",biaya_mid);
        intent.putExtra("biaya_max",biaya_max);
        intent.putExtra("pengalaman_min",pengalaman_min);
        intent.putExtra("pengalaman_mid",pengalaman_mid);
        intent.putExtra("pengalaman_max",pengalaman_max);
        intent.putExtra("jarak_min",jarak_min);
        intent.putExtra("jarak_mid",jarak_mid);
        intent.putExtra("jarak_max",jarak_max);

        startActivity(intent);
    }

    private void getValueFromIntent() {
        Intent intent = getIntent();

        biaya_param = intent.getExtras().getInt("biaya_param");
        pengalaman_param = intent.getExtras().getInt("pengalaman_param");
        jarak_param = intent.getExtras().getInt("jarak_param");

        kelamin = intent.getExtras().getString("kelamin");
        jenjang = intent.getExtras().getString("jenjang");
        mapel = intent.getExtras().getString("mapel");
        hari = intent.getExtras().getString("hari");
    }

    private void getValue() {
        biaya_mid = Float.parseFloat(biaya_fuzzy.getText().toString());
        pengalaman_mid = Integer.parseInt(pengalaman_fuzzy.getText().toString());
        jarak_mid = Float.parseFloat(jarak_fuzzy.getText().toString()) * 1000f;

        biaya_min = (float) (biaya_mid * 0.7);
        biaya_max = (float) (biaya_mid * 1.3);

        pengalaman_min = (int) (pengalaman_mid*0.7);
        pengalaman_max = (int) (pengalaman_mid*1.3);

        jarak_min = (float) (jarak_mid*0.7);
        jarak_max = (float) (jarak_mid*1.3);
    }
}
