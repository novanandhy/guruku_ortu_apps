package com.example.chorryigas.bismillahtugasakhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;

import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_RatingGuru;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelRating;
import com.example.chorryigas.bismillahtugasakhir.Model.Review;
import com.example.chorryigas.bismillahtugasakhir.Util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class LihatRating extends AppCompatActivity {

    private List<ModelRating> rating;
    private RatingBar ratingGuru;
    private SessionManager sessionManager;
    private String id_guru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_rating);
        this.rating = getIntent().getParcelableArrayListExtra("rating");
        this.ratingGuru = (RatingBar) findViewById(R.id.rating_guru);
        id_guru = getIntent().getStringExtra("id_guru");
        sessionManager = new SessionManager(this);
        if(rating.size() <=0)
            ratingGuru.setRating(5);
//        else ratingGuru.setRating();
        tampilRating();
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
                startActivity(tambahRating);
        }
        return true;
    }

    private void tampilRating(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_reviewortu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm  = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        RVAdapter_RatingGuru adapter = new RVAdapter_RatingGuru(this, rating);
        recyclerView.setAdapter(adapter);
    }
}
