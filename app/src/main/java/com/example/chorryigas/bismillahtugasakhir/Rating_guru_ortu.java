package com.example.chorryigas.bismillahtugasakhir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.Guru;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Rating_guru_ortu extends AppCompatActivity {
    RatingBar ratingbar;
    Button button;
    Guru guru;
    EditText et_komentar;
    String id_guru;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_guru_ortu);
        guru = getIntent().getParcelableExtra("guru");
        id_guru = getIntent().getStringExtra("id_guru");
        sessionManager = new SessionManager(this);
        addListenerOnButtonClick();
    }

    private void addListenerOnButtonClick() {
        ratingbar = (RatingBar)findViewById(R.id.rating_guru);
        button = (Button)findViewById(R.id.submit_reviewortu);
        et_komentar = (EditText) findViewById(R.id.komentar);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String rating = String.valueOf(ratingbar.getRating());
//                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                sendRating();
            }
        });
    }

    public void sendRating(){
        StringRequest request = new StringRequest(Request.Method.POST, Server.RATING_CREATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                Toast.makeText(Rating_guru_ortu.this,"Submit review berhasil",Toast.LENGTH_LONG).show();
                            }
                            else  Toast.makeText(Rating_guru_ortu.this,jsonObject.getString("error_msg"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Rating_guru_ortu.this,"Koneksi bermasalah",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user",sessionManager.getKeyId());
                params.put("id_guru",id_guru);
                params.put("rating",""+ratingbar.getRating());
                params.put("review",et_komentar.getText().toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }
}
