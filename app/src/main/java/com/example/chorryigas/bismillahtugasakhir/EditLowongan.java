package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowongan;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowonganPribadi;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditLowongan extends AppCompatActivity {
    private SessionManager sessionManager;
    private Context context;

    private String id_user;
    private int id,position;
    ModelLowonganPribadi lowongan;
    TextView ubah_data;
    EditText et_judulLowongan;
    EditText et_isiLowongan;
    String subjek;
    String deskripsi;

    private String TAG = "TAG_EditLowongan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lowongan);
        context = this;

        sessionManager = new SessionManager(context);

        lowongan = getIntent().getParcelableExtra("lowongan");
        position = getIntent().getExtras().getInt("position");

        et_isiLowongan = (EditText) findViewById(R.id.isi_lowongan);
        et_judulLowongan = (EditText) findViewById(R.id.judul_lowongan);
        ubah_data = (Button) findViewById(R.id.ubah_lowongan);
        id_user = sessionManager.getKeyId();
        id = Integer.valueOf(lowongan.getId());
        et_isiLowongan.setText(lowongan.getDescription());
        et_judulLowongan.setText(lowongan.getSubjek());

        ubah_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjek = et_judulLowongan.getText().toString();
                deskripsi = et_isiLowongan.getText().toString();
                ubahData(id,id_user,subjek,deskripsi);
            }
        });



    }

    private void ubahData(final int id, final String id_user, final String subjek, final String deskripsi) {
        Log.d(TAG, "masuk ubahdata: ");
        //Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST, Server.LOWONGAN_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "masuk response");
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                SplashActivity.mLowongan.get(position).setSubjek(subjek);
                                SplashActivity.mLowongan.get(position).setDescription(deskripsi);

                                Toast.makeText(EditLowongan.this,"Update berhasil",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else Toast.makeText(EditLowongan.this,"Update gagal",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "ora kenek", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditLowongan.this,"Koneksi bermasalah",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",""+id);
                params.put("id_user",id_user);
                params.put("subjek",subjek);
                params.put("description",deskripsi);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
