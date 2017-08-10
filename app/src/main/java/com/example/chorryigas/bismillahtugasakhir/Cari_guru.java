package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Cari_guru extends AppCompatActivity {
    private TextView cari;
    private Spinner spinner_jenjang,spinner_matpel,spinner_hari, spinner_kelamin;

    private Context context;

    private String jenjang,matpel,hari,kelamin,kelamin_param;
    private int biaya_param, pengalaman_param, jarak_param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_guru);
        context = this;

        getItemId();
        setSpinnerJenjang();
        setSpinnerMapel();
        setSpinnerHari();
        setSpinnerKelamin();
        buttonCari();
    }

    private void setSpinnerKelamin() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.jenis_kelamin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_kelamin.setAdapter(adapter);
        spinner_kelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil nilai jenjang
                kelamin = (String) parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerHari() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.hari_les, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hari.setAdapter(adapter);
        spinner_hari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil nilai jenjang
                hari = (String) parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerMapel() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.matpel_murid, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_matpel.setAdapter(adapter);
        spinner_matpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil nilai jenjang
                matpel = (String) parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerJenjang() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.jenjang_murid, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_jenjang.setAdapter(adapter);
        spinner_jenjang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil nilai jenjang
                jenjang = (String) parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void getItemId() {
        cari = (TextView) findViewById(R.id.selanjutnya1);
        spinner_jenjang = (Spinner) findViewById(R.id.jenjang_murid);
        spinner_matpel = (Spinner) findViewById(R.id.matpel_murid);
        spinner_hari = (Spinner) findViewById(R.id.hari_les);
        spinner_kelamin = (Spinner) findViewById(R.id.jenis_kelamin);
    }

    private void buttonCari(){
        cari.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getValueFromIntent();

                if(!jenjang.contains("Pilih jenjang murid")
                        && !matpel.contains("Pilih mata pelajaran")
                        && !hari.contains("Pilih hari les")
                        && !kelamin.contains("Pilih Jenis Kelamin Guru")){
                    if (kelamin.contains("Laki-laki")){
                        kelamin_param = "L";
                    }else{
                        kelamin_param = "P";
                    }

                    Intent intent = new Intent(Cari_guru.this, KriteriaOrangTua.class);
                    intent.putExtra("biaya_param",biaya_param);
                    intent.putExtra("pengalaman_param",pengalaman_param);
                    intent.putExtra("jarak_param",jarak_param);
                    intent.putExtra("kelamin",kelamin_param);
                    intent.putExtra("jenjang",jenjang);
                    intent.putExtra("mapel",matpel);
                    intent.putExtra("hari",hari);
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "Mohon isi semua form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getValueFromIntent() {
        Intent intent = getIntent();
        biaya_param = intent.getExtras().getInt("biaya_param");
        pengalaman_param = intent.getExtras().getInt("pengalaman_param");
        jarak_param = intent.getExtras().getInt("jarak_param");
    }

}
