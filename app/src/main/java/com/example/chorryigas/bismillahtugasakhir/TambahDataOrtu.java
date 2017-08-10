package com.example.chorryigas.bismillahtugasakhir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TambahDataOrtu extends AppCompatActivity {
    private Geocoder geocoder;
    private List<Address> addressList;
    private ProgressDialog progressDialog;
    private Context context;
    private SessionManager sessionManager;

    private TextView buka_map;
    private Button simpan;
    private Button button_upload;
    private EditText edit_email, edit_nama, edit_telp, edit_alamat;
    private CircleImageView image;
    private Bitmap img;

    private static final int SELECT_PICTURE = 100;
    private String email, nama, telp, alamat, image64;
    private String TAG = "TAG TambahDataOrtu";
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_ortu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        button_upload = (Button) findViewById(R.id.upload_foto);
        buka_map = (TextView) findViewById(R.id.open_map);
        simpan = (Button) findViewById(R.id.simpan_data);

        sessionManager = new SessionManager(context);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat alamat");
        progressDialog.setTitle("Loading");
        geocoder = new Geocoder(this, Locale.getDefault());
        edit_alamat = (EditText) findViewById(R.id.alamat_rumah);

        image = (CircleImageView) findViewById(R.id.foto_profil);
        edit_email = (EditText) findViewById(R.id.alamat_email);
        edit_nama = (EditText) findViewById(R.id.nama_lengkap);
        edit_telp = (EditText) findViewById(R.id.no_telp);

        setValueForm();
        buttonOpenImage();
        buttonMap();
        uploadData();
    }

    private void setValueForm(){
        edit_email.setText(SplashActivity.mPengguna.get(0).getEmail());
        edit_nama.setText(SplashActivity.mPengguna.get(0).getNama());
        edit_telp.setText(SplashActivity.mPengguna.get(0).getNo_telp());
        edit_alamat.setText(SplashActivity.mPengguna.get(0).getAlamat());
        Picasso.with(context).load(SplashActivity.mPengguna.get(0).getFoto()).into(image);
    }
    private void uploadData(){
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mendapatkan nilai dari form
                getValueForm();

                if (latitude == 0 || longitude == 0 || alamat.isEmpty() || nama.isEmpty()
                        || telp.isEmpty() || email.isEmpty() || image64.isEmpty()){
                    Toast.makeText(context, "Harap isi semua form", Toast.LENGTH_SHORT).show();
                }else{
                    //Edit profil guru
                    EditPengguna(sessionManager.getKeyId(), nama, alamat, telp, email, latitude, longitude, image64);
                }
            }
        });
    }

    private void EditPengguna(final String id_pengguna, final String nama, final String alamat,
                              final String telp, final String email, final Double latitude,
                              final Double longitude, final String image){

        progressDialog.show();
        final String foto_path;

        //mendapatkan url foto
        foto_path = SplashActivity.mPengguna.get(0).getFoto();


        //Tag used to cancel request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.USER_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        //mengganti semua nilai data pengguna
                        SplashActivity.mPengguna.clear();

                        ModelPengguna mPeng = new ModelPengguna();

                        mPeng.setNama(nama);
                        mPeng.setEmail(email);
                        mPeng.setNo_telp(telp);
                        mPeng.setAlamat(alamat);
                        mPeng.setLat(String.valueOf(latitude));
                        mPeng.setLng(String.valueOf(longitude));
                        mPeng.setFoto(foto_path);

                        SplashActivity.mPengguna.add(mPeng);

                        progressDialog.dismiss();
                        finish();

                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        progressDialog.dismiss();
                        Log.d(TAG, "error Message: " + errorMsg);
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
            @Override
            protected Map<String, String> getParams(){
                //posting paramas to register url
                Map<String, String> params = new HashMap<>();

                params.put("id_user", id_pengguna);
                params.put("nama", nama);
                params.put("alamat", alamat);
                params.put("no_telp", telp);
                params.put("email", email);
                params.put("lat",String.valueOf(latitude));
                params.put("lng",String.valueOf(longitude));
                params.put("foto", image);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void getValueForm(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray = null;

        //mengambil nilai email
        email = edit_email.getText().toString();
        //mengambil nilai nama
        nama = edit_nama.getText().toString();
        //mengambil nilai telfon
        telp = edit_telp.getText().toString();
        //mengambil nilai alamat
        alamat = edit_alamat.getText().toString();

        //jika alamat kosong, maka akan diisi dengan yang sudah ada
        if(latitude == null && longitude == null){
            //mengambil nilai latitude
            latitude = Double.valueOf(SplashActivity.mPengguna.get(0).getLat());
            //mengambil nilai longitude
            longitude = Double.valueOf(SplashActivity.mPengguna.get(0).getLng());
        }

        //cek jika pengguna tidak memilih gambar
        if(img == null){
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG,40,stream);
            byteArray = stream.toByteArray();
        } else{
            //change bitmap to byte array
            img.compress(Bitmap.CompressFormat.PNG,40,stream);
            byteArray = stream.toByteArray();
        }

        //mengambil nilai foto
        image64 = getStringImage(byteArray);

    }

    private void buttonOpenImage(){
        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSource();
            }
        });
    }

    private void openImageSource(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih gambar"), SELECT_PICTURE);
    }

    public String getStringImage(byte[] bmp){
        String encodedImage = Base64.encodeToString(bmp, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context){
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo = Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    private void buttonMap(){

        buka_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(TambahDataOrtu.this, MapsActivity.class);
                startActivityForResult(intent,207);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 207) {
            LatLng latLng = data.getParcelableExtra("latlng");
            latitude = latLng.latitude;
            longitude = latLng.longitude;
            progressDialog.show();
            try {
                addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (addressList.size() > 0) {
                    String address = addressList.get(0).getAddressLine(0);
                    String district = addressList.get(0).getAddressLine(1);
                    String regency = addressList.get(0).getAddressLine(2);
                    String country = addressList.get(0).getAddressLine(3);
                    String completeAddress = address + " " + district + " " + regency + " " + country;
                    edit_alamat.setText(completeAddress);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(TambahDataOrtu.this, "Gagal menambahkan alamat, coba lagi", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }

        //mengambil gambar dari galeri hp
        try{
            //when image is picked
            if(resultCode == RESULT_OK){
                if(requestCode == SELECT_PICTURE){
                    //get the url from data
                    Uri selectedImageUri = data.getData();
                    if(null != selectedImageUri){
                        img = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        image.setImageBitmap(img);
                        img = scaleDownBitmap(img, 90, context);
                    }
                }
            } else{
                Toast.makeText(this, "Anda belum memilih gambar", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_LONG).show();
        }
    }
}
