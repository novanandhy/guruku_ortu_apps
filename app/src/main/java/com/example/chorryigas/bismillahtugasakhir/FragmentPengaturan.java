package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chorryigas.bismillahtugasakhir.data.UserData;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentPengaturan extends Fragment{
    private TextView nama_ortu,email_ortu;
    private TextView edit_data;
    private TextView logout;
    private CircleImageView image;

    private Context context;

    public static FragmentPengaturan newInstance(){
        FragmentPengaturan fragment = new FragmentPengaturan();
        return fragment;
    }

    public static FragmentPengaturan newInstance(String strArg) {
        FragmentPengaturan fragment = new FragmentPengaturan();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pengaturan,null,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Pengaturan");

        edit_data = (TextView)view.findViewById(R.id.edit_data);
        logout = (TextView) view.findViewById(R.id.logout);
        nama_ortu = (TextView) view.findViewById(R.id.nama_ortu);
        email_ortu = (TextView) view.findViewById(R.id.alamat_email);
        image = (CircleImageView) view.findViewById(R.id.foto_profil);

        context = getActivity().getApplicationContext();

        setValue();
        buttonEdit();
        buttonLogout();
        return view;
    }

    private void buttonLogout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.remove(getContext());
                Intent intent = new Intent(getActivity(), Awal_install.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    private void buttonEdit(){
        edit_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), TambahDataOrtu.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        setValue();
        super.onResume();
    }

    private void setValue(){
        nama_ortu.setText(SplashActivity.mPengguna.get(0).getNama());
        email_ortu.setText(SplashActivity.mPengguna.get(0).getEmail());

        Picasso.with(context).invalidate(SplashActivity.mPengguna.get(0).getFoto());
        Picasso.with(context).load(SplashActivity.mPengguna.get(0).getFoto()).into(image);
    }
}
