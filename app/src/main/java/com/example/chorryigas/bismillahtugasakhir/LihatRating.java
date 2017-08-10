package com.example.chorryigas.bismillahtugasakhir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_RatingGuru;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelRating;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelRatingGuru;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LihatRating extends AppCompatActivity {

    private ArrayList<ModelRating> rating;

    private RatingBar ratingGuru;
    private TextView ratingGuruText;

    private ProgressDialog progressDialog;

    private String id_guru;
    private String TAG = "TAG_LihatRating";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_rating);

        ratingGuru = (RatingBar) findViewById(R.id.rating_guru);
        ratingGuruText = (TextView) findViewById(R.id.nilai_rating_guru);
        progressDialog = new ProgressDialog(this);
        rating = new ArrayList<>();

        id_guru = getIntent().getExtras().getString("id_user");
        Log.d(TAG, "onCreate: "+id_guru);

        getData(id_guru);
    }

    private void getData(String id_guru) {
        getRating(id_guru);
        getRatingReview(id_guru);
    }

    private void getRating(final String id_guru) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        progressDialog.setCancelable(false);
        progressDialog.setMessage("rating...");
        progressDialog.show();

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.RATING_GET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get daftar guru Response: " + response.toString());

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    progressDialog.dismiss();
                    //cek apakah JSON memiliki indeks error yg true atau tidak
                    if(!error){
                        //menangkap indeks array JSON "user"
                        JSONArray list = jObj.getJSONArray("user");

                        //mengambil setiap data di setiap indeks JSON
                        ModelRatingGuru mRating = new ModelRatingGuru();
                        for(int i=0; i<list.length(); i++){
                            JSONObject details = list.getJSONObject(i);

                            mRating.setRating(Float.valueOf(details.getString("rating")));
                        }

                        ratingGuru.setRating(mRating.getRating());
                        ratingGuruText.setText(String.valueOf(mRating.getRating()));

                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(LihatRating.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("id_guru", id_guru);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void getRatingReview(final String id_guru){
        //Tag used to cancel the request
        String tag_string_req = "req_register";

        progressDialog.setCancelable(false);
        progressDialog.setMessage("rating...");
        progressDialog.show();

        //merequest JSON dengan URL yang telah disediakan dengan method POST
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Server.RATING_GET_REVIEW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get daftar guru Response: " + response.toString());

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    progressDialog.dismiss();
                    //cek apakah JSON memiliki indeks error yg true atau tidak
                    if(!error){
                        //menangkap indeks array JSON "user"
                        JSONArray list = jObj.getJSONArray("user");

                        //mengambil setiap data di setiap indeks JSON
                        for(int i=0; i<list.length(); i++){
                            JSONObject details = list.getJSONObject(i);

                            ModelRating mRating = new ModelRating();

                            mRating.setId_user(details.getString("id_user"));
                            mRating.setRating(Float.valueOf(details.getString("rating")));
                            mRating.setReview(details.getString("review"));
                            mRating.setNama(details.getString("nama"));
                            mRating.setFoto(details.getString("foto"));

                            rating.add(mRating);
                        }

                        tampilRating(rating);

                    } else {
                        //terjadi kesalahan saat mengambil JSON. Misal data pada db tidak ada
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(LihatRating.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();;
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //jika tidak ada respon dari URL (tidak ada internet
                Log.e(TAG, "Registration Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //memasukkan parameter untuk merequest JSON
                Map<String, String> params = new HashMap<>();
                params.put("id_guru", id_guru);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tambahrating,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.tambah_rating:
                Intent tambahRating = new Intent(LihatRating.this, Rating_guru_ortu.class );
                tambahRating.putExtra("id_guru", id_guru);
                startActivityForResult(tambahRating,10);
        }
        return true;
    }

    private void tampilRating(ArrayList<ModelRating> rating){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_reviewortu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm  = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        RVAdapter_RatingGuru adapter = new RVAdapter_RatingGuru(this, rating);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK){
            getData(id_guru);
        }
    }
}
