package com.example.chorryigas.bismillahtugasakhir;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    private boolean status;
    public static final int FRAGMENT_LIST_LOWONGAN = R.id.action_lowongan;
    public static final int FRAGMENT_LIST_SEARCH = R.id.action_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        openHome();
                        break;
                    case R.id.action_search:
                        openSearch();
                        break;
                    case R.id.action_teacher:
                        openTeacher();
                        break;
                    case R.id.action_lowongan:
                        openLowongan();
                        break;
                    case R.id.action_setting:
                        openSetting();
                        break;
                }

                return true;
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra("fragments")){
            switch (intent.getIntExtra("fragments",-1)){
                case FRAGMENT_LIST_LOWONGAN:
                    openLowongan();
                    break;
                case FRAGMENT_LIST_SEARCH:
                    openSearch();
                    break;
            }
        }
        else {
            status = intent.getBooleanExtra("intent_status", false);

            if (status == true) {
                openLowongan();
            }


            fragment = FragmentHomeOrtu.newInstance();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            //Bottom Navigation
        }
    }

    private void openSetting() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentPengaturan.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void openLowongan() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentListLowongan.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void openTeacher() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentDaftarGuruOrtu.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void openSearch() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentCariGuru.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void openHome() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = FragmentHomeOrtu.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


}
