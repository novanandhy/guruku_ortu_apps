package com.example.chorryigas.bismillahtugasakhir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registrasi_ortu extends AppCompatActivity {
    Button buttonRegis;
    EditText et_email;
    EditText et_password;
    EditText et_nama;
    EditText et_telp;

    String URL_LOGIN = "http://10.252.135.22/guruprivat/register.php";
    String email, password, nama_lengkap, no_telp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_ortu);
        buttonRegis = (Button) findViewById(R.id.simpan_data);
        et_email = (EditText) findViewById(R.id.alamat_email);
        et_password = (EditText) findViewById(R.id.password);
        et_nama = (EditText) findViewById(R.id.nama_lengkap);
        et_telp = (EditText) findViewById(R.id.no_telp);

        buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registrasi();
            }
        });
        buttonRegistrasi();
    }

    public void registrasi(){
        if (!TextUtils.isEmpty(et_email.getText()) || !TextUtils.isEmpty(et_password.getText()) || !TextUtils.isEmpty(et_nama.getText()) || !TextUtils.isEmpty(et_telp.getText())){
            email = et_email.getText().toString();
            password = et_password.getText().toString();
            nama_lengkap = et_nama.getText().toString();
            no_telp = et_telp.getText().toString();

            new prosesRegister().execute();
        } else {
            Toast.makeText(this, "Masukkan data registrasi.", Toast.LENGTH_SHORT).show();
        }
    }

    class prosesRegister extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Registrasi_ortu.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.setTitle("Registrasi");
            progressDialog.setMessage("Tunggu Sebentar...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            toServer connect = new toServer();
            String response;
            try{
                response = connect.run(URL_LOGIN);
            } catch (IOException e){
                response = "network_error";
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            parseJson(s);
        }
    }

    public void parseJson(String response){
        String hasil_register;
        if(response.equals("network_error")){
            Toast.makeText(this, "Cek koneksi internet anda.", Toast.LENGTH_LONG).show();
        } else{
            try{
                JSONObject jsonObject = new JSONObject(response);
                hasil_register = jsonObject.getString("registrasi");

                if(hasil_register.equals("berhasil")){
                    Toast.makeText(this, "Registrasi berhasil.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registrasi_ortu.this, Awal_install.class);
                    startActivity(intent);
                    Registrasi_ortu.this.finish();
                } else if (hasil_register.equals("gagal")){
                    Toast.makeText(this, "Registrasi gagal, coba lagi.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class toServer{
        OkHttpClient client = new OkHttpClient();
        String run(String url) throws IOException {
            RequestBody requestBody = new FormBody.Builder()
                    .add("alamat_email", email)
                    .add("password", password)
                    .add("nama_lengkap", nama_lengkap)
                    .add("no_telp", no_telp)
                    .build();

            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
    private void buttonRegistrasi(){
        buttonRegis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Registrasi_ortu.this, Awal_install.class);
                startActivity(intent);
            }
        });
    }
}
