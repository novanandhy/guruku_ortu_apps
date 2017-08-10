package com.example.chorryigas.bismillahtugasakhir;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelPengguna;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Awal_install extends AppCompatActivity {
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private SplashActivity splashActivity;

    private EditText Edit_alamat_email;
    private EditText Edit_password;
    private TextView registrasi;
    private Button button;
    private Context context;
    private RelativeLayout relativeLayout;

    String alamat_email, password;
    String TAG = "TAG_AwalInstallActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal_install);
        this.setTitle("Login");
        context = this;
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        //membuat objek dialog
        progressDialog = new ProgressDialog(context);
        splashActivity = new SplashActivity();

        Edit_alamat_email = (EditText) findViewById(R.id.Edit_alamat_email);
        Edit_password = (EditText) findViewById(R.id.Edit_password);

        registrasi = (TextView) findViewById(R.id.registrasi);
        button = (Button) findViewById(R.id.button_login);

        //membuat session login
        sessionManager = new SessionManager(context);
        if(sessionManager.isLoggedIn()){
            Intent intent = new Intent(Awal_install.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        //cek apakah terkoneksi atau tidak, jika tidak akan ada anjuran untuk mereload activity
        if(isOnline()){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alamat_email = Edit_alamat_email.getText().toString().trim();
                    password = Edit_password.getText().toString().trim();

                    //cek apakah form telah terisi semua atau belum
                    if(!alamat_email.isEmpty() && !password.isEmpty()){
                        Login_ortu(alamat_email, password);
                    } else{
                        Toast.makeText(context, "Mohon isi username dan password", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else{
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Tolong cek kembali koneksi", Snackbar.LENGTH_LONG)
                    .setAction("CEK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(getIntent());
                        }
                    });

            snackbar.show();
        }

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Awal_install.this, Registrasi.class);
                startActivity(intent);
            }
        });
    }

    private void Login_ortu(final String alamat_email, final String password){
        //Tag used to cancel the request
        String tag_string_req = "req_register";
        progressDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Server.LOGIN, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(!error){
                        String uid = jObj.getString("uid");
                        Log.d(TAG, "uid: " + uid);

                        //membuat session login
                        sessionManager.setLogin(true, uid);
                        Intent intent = new Intent(Awal_install.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        //error saat registrasi
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registrasi error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //mengirim parameter yang dibutuhkan untuk login
                Map<String, String> params = new HashMap<>();

                params.put("previllage", "1");
                params.put("username", alamat_email);
                params.put("password", password);

                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

//    public void login(){
//
//        if (!TextUtils.isEmpty(et_email.getText()) || !TextUtils.isEmpty(et_password.getText())) {
//            email = et_email.getText().toString();
//            password = et_password.getText().toString();
//
//            new processLogin().execute();
//        } else {
//            Toast.makeText(this, "Masukkan username & password.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void parseJson(String response) {
//        String hasil_login;
//        if (response.equals("network_error")) {
//            Toast.makeText(this, "Cek koneksi internet anda.", Toast.LENGTH_SHORT).show();
//        } else {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                hasil_login = jsonObject.getString("login");
//                JSONObject data = jsonObject.getJSONObject("data");
//                if (hasil_login.equals("berhasil")) {
//                    UserData.USERNAME = email;
//                    UserData.save(Awal_install.this);
//
//                    String email = data.getString(UserData.KEY_EMAIL);
//                    String nama_lengkap = data.getString(UserData.KEY_NAMA_LENGKAP);
//                    String no_telp = data.getString(UserData.KEY_NO_TELP);
//                    String latitude = data.getString(UserData.KEY_LATITUDE);
//                    String longitude = data.getString(UserData.KEY_LONGITUDE);
//                    String foto_profile = data.getString(UserData.KEY_PHOTO_PROFILE);
//                    String alamat = data.getString(UserData.KEY_ALAMAT);
//                    int id_user = Integer.valueOf(data.getString(UserData.KEY_ID_USER));
//
//                    UserData.login(Awal_install.this,id_user,email,alamat,latitude,longitude,nama_lengkap,foto_profile,no_telp);
//                    UserData.getIDuser(Awal_install.this);
//                    Intent intent = new Intent(Awal_install.this, HomeActivity.class);
//                    startActivity(intent);
//                    Awal_install.this.finish();
//                } else if (hasil_login.equals("gagal")) {
//                    Toast.makeText(this, "Kombinasi username & password salah.", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class processLogin extends AsyncTask<String, Void, String> {
//
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(Awal_install.this);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(true);
//            progressDialog.setTitle("Login");
//            progressDialog.setMessage("Tunggu Sebentar...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            toServer connect = new toServer();
//            String response;
//            try {
//                response = connect.run(URL_LOGIN);
//            } catch (IOException e) {
//                response = "network_error";
//                e.printStackTrace();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            progressDialog.dismiss();
//
//            parseJson(s);
//        }
//    }
//
//    class toServer {
//        OkHttpClient client = new OkHttpClient();
//        String run(String url) throws IOException {
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("username", email)
//                    .add("password", password)
//                    .build();
//
//            Request request = new Request.Builder()
//                    .post(requestBody)
//                    .url(url)
//                    .build();
//
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        }
//    }
//
//    private void buttonMasuk(){
//        button_login.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(Awal_install.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    private void buttonRegis(){

        registrasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Awal_install.this, Registrasi.class);
                startActivity(intent);
            }
        });
    }
}
