package com.example.chorryigas.bismillahtugasakhir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_hasilcariguru;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.Guru;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowongan;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowonganPribadi;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelPencarian;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelPengguna;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HasilCariGuru extends AppCompatActivity {
    private TextView pasang_iklan;
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private EditText lowongan_guru, judul_lowongan;

    private SplashActivity splashActivity;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    private ArrayList<ModelPencarian> mPencarian;

    private String lowongan, jdl_lowongan;
    private String id_user;
    private String kelamin,jenjang,mapel,hari;
    private int biaya_param, pengalaman_param, jarak_param;
    private float biaya_min, biaya_mid, biaya_max;
    private int pengalaman_min, pengalaman_mid, pengalaman_max;
    private float jarak_min, jarak_mid, jarak_max;
    private String lat, lng;

    private String TAG = "TAG_HasilCariGuru";
    private String id;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_cari_guru);
        context = this;

        mPencarian = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        sessionManager = new SessionManager(context);
        splashActivity = new SplashActivity();

        getValueFromIntent();
        getLatLong();
        buttonPasang();

        FuzzySearch(
                biaya_min,biaya_mid,biaya_max,
                pengalaman_min,pengalaman_mid,pengalaman_max,
                jarak_min,jarak_mid,jarak_max,
                biaya_param,pengalaman_param,jarak_param,
                jenjang,mapel,hari,kelamin,
                lat,lng);
    }

    private void FuzzySearch(final float biaya_min, final float biaya_mid, final float biaya_max,
                             final int pengalaman_min, final int pengalaman_mid, final int pengalaman_max,
                             final float jarak_min, final float jarak_mid, final float jarak_max,
                             final int biaya_param, final int pengalaman_param, final int jarak_param,
                             final String jenjang, final String mapel, final String hari, final String kelamin,
                             final String lat, final String lng) {
        String tag_string_req = "req_register";

        progressDialog.setCancelable(false);
        progressDialog.setMessage("mencari guru...");
        progressDialog.show();

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.FUZZY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get data guru Response: " + response.toString());

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    //cek apakah JSON memiliki indeks error yg true atau tidak
                    if(!error){
                        //menangkap indeks array JSON "user"
                        JSONArray list = jObj.getJSONArray("user");
                        mPencarian.clear();

                        //mengambil setiap data di setiap indeks JSON
                        for(int i=0; i<list.length(); i++){
                            JSONObject details = list.getJSONObject(i);

                            ModelPencarian pen = new ModelPencarian();
                            pen.setId_guru(details.getString("id_guru"));
                            pen.setNama(details.getString("nama"));
                            pen.setFoto(details.getString("foto"));
                            pen.setPengalaman(details.getString("pengalaman"));
                            pen.setJenjang(details.getString("pendidikan"));
                            pen.setLat(details.getString("lat"));
                            pen.setLng(details.getString("lng"));
                            pen.setBiaya(details.getString("biaya"));
                            pen.setRating(Double.valueOf(details.getString("rating")));
                            pen.setJarak(details.getString("jarak"));


                            mPencarian.add(pen);
                        }

                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_hasilcariguru);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(layoutManager);

                        RVAdapter_hasilcariguru adapter = new RVAdapter_hasilcariguru(getApplicationContext(),mPencarian);
                        recyclerView.setAdapter(adapter);

                        progressDialog.dismiss();
                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                        progressDialog.dismiss();
                        Toast.makeText(HasilCariGuru.this, "Tidak ada guru", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                    progressDialog.dismiss();
                    Toast.makeText(HasilCariGuru.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(HasilCariGuru.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("harga_min", String.valueOf(biaya_min));
                params.put("harga_mid", String.valueOf(biaya_mid));
                params.put("harga_max", String.valueOf(biaya_max));

                params.put("pengalaman_min", String.valueOf(pengalaman_min));
                params.put("pengalaman_mid", String.valueOf(pengalaman_mid));
                params.put("pengalaman_max", String.valueOf(pengalaman_max));

                params.put("jarak_min", String.valueOf(jarak_min));
                params.put("jarak_mid", String.valueOf(jarak_mid));
                params.put("jarak_max", String.valueOf(jarak_max));

                params.put("param_harga", String.valueOf(biaya_param));
                params.put("param_pengalaman", String.valueOf(pengalaman_param));
                params.put("param_jarak", String.valueOf(jarak_param));

                params.put("jenjang",jenjang);
                params.put("mapel",mapel);
                params.put("hari",hari);
                params.put("kelamin",kelamin);

                params.put("latitude",lat);
                params.put("longitude",lng);

                Log.d(TAG, "getParams: "+params.toString());
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void getLatLong() {
        lat = SplashActivity.mPengguna.get(0).getLat();
        lng = SplashActivity.mPengguna.get(0).getLng();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragments",HomeActivity.FRAGMENT_LIST_SEARCH);
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

        biaya_min = intent.getExtras().getFloat("biaya_min");
        biaya_mid = intent.getExtras().getFloat("biaya_mid");
        biaya_max = intent.getExtras().getFloat("biaya_max");

        pengalaman_min = intent.getExtras().getInt("pengalaman_min");
        pengalaman_mid = intent.getExtras().getInt("pengalaman_mid");
        pengalaman_max = intent.getExtras().getInt("pengalaman_max");

        jarak_min = intent.getExtras().getFloat("jarak_min");
        jarak_mid = intent.getExtras().getFloat("jarak_mid");
        jarak_max = intent.getExtras().getFloat("jarak_max");
    }

    private void buttonPasang(){
        pasang_iklan = (TextView) findViewById(R.id.pasang_iklan);
        pasang_iklan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();
            }
        });
    }

    private void DialogForm(){
        dialog = new AlertDialog.Builder(HasilCariGuru.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_iklan,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Tulis Iklan Lowongan Untuk Guru");

        judul_lowongan = (EditText) dialogView.findViewById(R.id.judul_lowongan);
        lowongan_guru = (EditText)dialogView.findViewById(R.id.lowongan_guru);
        lowongan_guru.setText(null);
        dialog.setPositiveButton("PASANG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id_user = sessionManager.getKeyId();
                jdl_lowongan = judul_lowongan.getText().toString();
                lowongan = lowongan_guru.getText().toString();
                //dialog.dismiss();

                if(jdl_lowongan.isEmpty() || lowongan.isEmpty()){
                    Toast.makeText(context, "Mohon isi semua form", Toast.LENGTH_SHORT).show();
                } else{
                    inputLowongan(id_user, jdl_lowongan, lowongan);
                }
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void inputLowongan(final String id_user, final String jdl_lowongan, final String lowongan) {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Menulis lowongan...");
        progressDialog.show();
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.LOWONGAN_CREATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get lowongan response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        splashActivity.GetLowonganUser(sessionManager.getKeyId(), true, true, new SplashActivity.GetLowonganInterface() {
                            @Override
                            public void onLowonganRequestSuccess(String message) {
                                progressDialog.dismiss();
                                Toast.makeText(HasilCariGuru.this,"Pasang iklan sukses",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(HasilCariGuru.this,HomeActivity.class);
                                intent.putExtra("fragments",HomeActivity.FRAGMENT_LIST_LOWONGAN);
                                startActivity(intent);
                            }

                            @Override
                            public void onLowonganFailure(VolleyError e) {
                                progressDialog.dismiss();
                                Toast.makeText(HasilCariGuru.this,"Request failed",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onLowonganRequestFailure(JSONException e) {
                                Toast.makeText(HasilCariGuru.this,"Failed parsing failed",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });

                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Log.d(TAG, "error Message: " + errorMsg);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onCatch: "+e.getMessage());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Registration Error: " + error.getMessage());
                progressDialog.dismiss();
                }
        }){
            protected Map<String, String> getParams(){
                //Posting params to registe url

                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                params.put("subjek", jdl_lowongan);
                params.put("description", lowongan);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public class RVAdapter_hasilcariguru extends RecyclerView.Adapter<RVAdapter_hasilcariguru.ViewHolder> {
        private ArrayList<ModelPencarian> gurus;
        private Context context;
        private SessionManager sessionManager;
        private String TAG = "TAG_RVAdapter_HasilCariGuru";

        public RVAdapter_hasilcariguru(Context context, ArrayList<ModelPencarian> gurus){
            this.context = context;
            this.gurus = gurus;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView nama_guru;
            public TextView pend_guru;
            public TextView ambil_guru;
            public TextView lihat_profil;
            public ImageView imageView;
            public View parent;
            public TextView pengalaman, jarak, biaya;

            public ViewHolder(View itemView){
                super(itemView);
                parent = itemView;
                nama_guru = (TextView)itemView.findViewById(R.id.nama_guru);
                pend_guru = (TextView) itemView.findViewById(R.id.pend_guru);
                lihat_profil = (TextView) itemView.findViewById(R.id.lihat_profil_guru);
                imageView = (ImageView) itemView.findViewById(R.id.foto_guru);
                pengalaman = (TextView) itemView.findViewById(R.id.peng_guru);
                jarak = (TextView) itemView.findViewById(R.id.jarak_guru);
                biaya = (TextView) itemView.findViewById(R.id.biaya_guru);
                ambil_guru = (TextView) itemView.findViewById(R.id.ambil_guru);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder (ViewGroup parent, int i){
            View view = LayoutInflater.from(context).inflate(R.layout.list_daftar_guru_ortu, parent, false);
            sessionManager = new SessionManager(context);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position){
            holder.nama_guru.setText(gurus.get(position).getNama());
            holder.pend_guru.setText(gurus.get(position).getJenjang());
            holder.pengalaman.setText(gurus.get(position).getPengalaman());
            holder.jarak.setText(String.valueOf(gurus.get(position).getJarak()));
            holder.biaya.setText(gurus.get(position).getBiaya());

            holder.lihat_profil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfilGuru.class);
                    intent.putExtra("id_guru", gurus.get(position).getId_guru());
                    startActivity(intent);
                }
            });

            Picasso.with(context).invalidate(Server.URLpath+"upload/"+gurus.get(position).getFoto());
            Picasso.with(context).load(Server.URLpath+"upload/"+gurus.get(position).getFoto()).into(holder.imageView);

            holder.ambil_guru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ambilGuru(sessionManager.getKeyId(),gurus.get(position).getId_guru());
                }
            });
        }

        private void ambilGuru(final String id_user, final String id_guru) {
            //Tag used to cancel the request
            String tag_string_req = "req_register";
            StringRequest strReq = new StringRequest(Request.Method.POST, Server.BOOKING_CREATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d(TAG, "masuk response");
                                JSONObject jsonObject = new JSONObject(response);
                                if(!jsonObject.getBoolean("error")){
                                    Toast.makeText(context, "Guru berhasil di pesan", Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(context,"Update gagal",Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "ora kenek", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,"Koneksi bermasalah",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id_user",""+id_user);
                    params.put("id_guru",""+id_guru);
                    return params;
                }
            };

            //Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

        public int getItemCount(){
            return gurus.size();
        }
    }
}
