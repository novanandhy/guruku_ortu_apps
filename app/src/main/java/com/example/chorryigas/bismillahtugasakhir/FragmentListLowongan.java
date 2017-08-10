package com.example.chorryigas.bismillahtugasakhir;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chorryigas.bismillahtugasakhir.Adapter.RVAdapter_ListLowongan;
import com.example.chorryigas.bismillahtugasakhir.GlobalUse.Server;
import com.example.chorryigas.bismillahtugasakhir.Model.Jadwal;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowongan;
import com.example.chorryigas.bismillahtugasakhir.Model.ModelLowonganPribadi;
import com.example.chorryigas.bismillahtugasakhir.Util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FragmentListLowongan extends Fragment {

    private Context context;
    private RecyclerView rv_lowongan;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    private String TAG = "TAG_FragmentListLowongan";

    public static FragmentListLowongan newInstance(){
        FragmentListLowongan fragment = new FragmentListLowongan();
        return fragment;
    }

    public static FragmentListLowongan newInstance(String strArg) {
        FragmentListLowongan fragment = new FragmentListLowongan();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list_lowongan,null,false);
        context = getActivity().getApplicationContext();

        setData(view);
        return view;
    }

    private void setData(View view) {
        rv_lowongan = (RecyclerView) view.findViewById(R.id.list_lowongan);
        rv_lowongan.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rv_lowongan.setLayoutManager(layoutManager);

        RVAdapter_ListLowongan adapter = new RVAdapter_ListLowongan(context, SplashActivity.mLowongan);
        rv_lowongan.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        setData(view);
        super.onResume();
    }

    public class RVAdapter_ListLowongan extends RecyclerView.Adapter<RVAdapter_ListLowongan.ViewHolder> {
        private ArrayList<ModelLowonganPribadi> dataLowongan;
        private Context context;

        public RVAdapter_ListLowongan(Context context, ArrayList<ModelLowonganPribadi> inputData){
            this.dataLowongan = inputData;
            this.context = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView judul_lowongan;
            public TextView isi_lowongan;
            public TextView edit_lowongan;
            public TextView hapus_lowongan;
            public AlertDialog.Builder dialog;

            public ViewHolder (View v){
                super(v);
                judul_lowongan = (TextView)v.findViewById(R.id.judul_lowongan);
                isi_lowongan = (TextView) v.findViewById(R.id.isi_lowongan);
                edit_lowongan = (TextView) v.findViewById(R.id.edit_lowongan);
                hapus_lowongan = (TextView) v.findViewById(R.id.hapus_lowongan);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lowongan, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.judul_lowongan.setText(dataLowongan.get(position).getSubjek());
            holder.isi_lowongan.setText(dataLowongan.get(position).getDescription());
            holder.edit_lowongan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditLowongan.class);
                    Log.d("Guru","size : "+dataLowongan.get(position));
                    intent.putExtra("lowongan", dataLowongan.get(position));
                    startActivity(intent);
                }
            });
            holder.hapus_lowongan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hapusLowongan(dataLowongan.get(position).getId(), dataLowongan.get(position).getId_user(),position);
                }
            });
        }

        private void hapusLowongan(final String id, final String id_user, final int position) {
            //Tag used to cancel the request
            String tag_string_req = "req_register";

            //merequest JSON dengan URL yang telah disediakan dengan method POST
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Server.LOWONGAN_DELETE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "delete data " + response.toString());

                    try{
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        //cek apakah JSON memiliki indeks error yg true atau tidak
                        if(!error){
                            dataLowongan.remove(position);
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
                    params.put("id", id);
                    return params;
                }
            };

            //Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

        @Override
        public int getItemCount() {
            return dataLowongan.size();
        }
    }

}
