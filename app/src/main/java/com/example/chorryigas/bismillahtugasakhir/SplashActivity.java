package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelGuruHome;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowonganPribadi;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelPengguna;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private static Context context;
    private ConstraintLayout constraintLayout;

    public static ArrayList<ModelPengguna> mPengguna;
    public static ArrayList<ModelLowonganPribadi> mLowongan;
    public static ArrayList<ModelGuruHome> mGuruHome;

    public String ID_USER;
    private static String TAG = "TAG_SplashScreen";
    private boolean intent_status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        sessionManager = new SessionManager(context);

        mPengguna = new ArrayList<>();
        mLowongan = new ArrayList<>();
        mGuruHome = new ArrayList<>();

        //cek apakah terkoneksi atau tidak
        if(isOnline()){
            //cek apakah user pernah login atau belum
            if(sessionManager.isLoggedIn()){
                ID_USER = sessionManager.getKeyId();
                //masuk ke halaman home jika telah mengambil semua data
                GetAllData(ID_USER);
            }
            //jika user belum pernah login, masuk ke halaman login
            else{
                Intent intent = new Intent(SplashActivity.this, Awal_install.class);
                startActivity(intent);
                finish();
            }
        }
        //jika tidak konek ke internet, maka muncul pemberitahuan
        else{
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "Tolong cek kembali koneksi", Snackbar.LENGTH_LONG)
                    .setAction("CEK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //reload activity
                            finish();
                            startActivity(getIntent());
                        }
                    });
            snackbar.show();
        }
    }

    public void GetAllData(String ID_USER){

        //mengambil semua nilai JSON dan disimpan pada array list
        GetDataUser(ID_USER);

    }

    public interface GetLowonganInterface{
        void onLowonganRequestSuccess(String message);
        void onLowonganFailure(VolleyError e);
        void onLowonganRequestFailure(JSONException e);
    }

    public void GetLowonganUser(final String id_user, final boolean flag_from, final boolean intent, @Nullable final GetLowonganInterface callback) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.LOWONGAN_GET_BY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get data lowongan Response: " + response.toString());

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    //cek apakah JSON memiliki indeks error yg true atau tidak
                    if(!error){
                        //menangkap indeks array JSON "user"
                        JSONArray list = jObj.getJSONArray("user");
                        mLowongan.clear();
                        if(list!=null && list.length()>0) {
                            //mengambil setiap data di setiap indeks JSON
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject details = list.getJSONObject(i);

                                ModelLowonganPribadi mLo = new ModelLowonganPribadi();
                                mLo.setId_user(id_user);
                                mLo.setId(details.getString("id"));
                                mLo.setSubjek(details.getString("subjek"));
                                mLo.setDescription(details.getString("description"));


                                mLowongan.add(mLo);
                            }
                        } else mLowongan = new ArrayList<>();

                        if(!flag_from) {
                            if (!intent) {
                                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(context, HomeActivity.class);
                                intent.putExtra("intent_status", intent_status);
                                startActivity(intent);
                                finish();
                            }
                        } else if(callback!=null){
                            callback.onLowonganRequestSuccess("Success");
                        }

                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
                if(callback!=null)
                    callback.onLowonganFailure(error);
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void GetDataUser(final String id_user){
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.USER_SELECT, new Response.Listener<String>() {
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
                        mPengguna.clear();

                        //mengambil setiap data di setiap indeks JSON
                        for(int i=0; i<list.length(); i++){
                            JSONObject details = list.getJSONObject(i);
                            if (details.getInt("dist") < 20){
                                ModelGuruHome mGuru = new ModelGuruHome();
                                mGuru.setId_guru(details.getString("id_guru"));
                                mGuru.setNama_guru(details.getString("nama"));
                                mGuru.setFoto(details.getString("foto"));
                                mGuru.setNo_telp(details.getString("no_telp"));
                                mGuru.setKampus(details.getString("kampus"));
                                mGuru.setJurusan(details.getString("jurusan"));
                                mGuru.setAlamat(details.getString("alamat"));
                                mGuru.setPengalaman(details.getInt("pengalaman"));

                                mGuruHome.add(mGuru);
                            }
                        }


                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void GetAllGuru(final String lat, final String lng) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.USER_SELECT, new Response.Listener<String>() {
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
                        mPengguna.clear();

                        //mengambil setiap data di setiap indeks JSON
                        for(int i=0; i<list.length(); i++){
                            JSONObject details = list.getJSONObject(i);

                            ModelPengguna mPeng = new ModelPengguna();
                            mPeng.setId_user("id_user");
                            mPeng.setNama(details.getString("nama"));
                            mPeng.setAlamat(details.getString("alamat"));
                            mPeng.setNo_telp(details.getString("no_telp"));
                            mPeng.setEmail(details.getString("email"));
                            mPeng.setFoto(details.getString("foto"));
                            mPeng.setLat(details.getString("lat"));
                            mPeng.setLng(details.getString("lng"));
                            Log.d(TAG, "get into response");


                            mPengguna.add(mPeng);
                        }
                        GetLowonganUser(ID_USER,false, intent_status, null);
                        GetAllGuru(mPengguna.get(0).getLat(),mPengguna.get(0).getLng());


                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("lat", lat);
                params.put("long", lng);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public boolean isOnline(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
