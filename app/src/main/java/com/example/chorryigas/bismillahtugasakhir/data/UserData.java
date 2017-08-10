package com.example.chorryigas.bismillahtugasakhir.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Choy on 7/26/2017.
 */

public class UserData {
    public static String USERNAME;
    public static String PREFNAME = "userLogin";
    public static String KEY_ID_USER = "id_user";
    public static String KEY_EMAIL = "email";
    public static String KEY_ALAMAT = "alamat";
    public static String KEY_NAMA_LENGKAP = "nama_lengkap";
    public static String KEY_LONGITUDE = "longitude";
    public static String KEY_LATITUDE = "latitude";
    public static String KEY_PHOTO_PROFILE = "foto_profil";
    public static String KEY_NO_TELP = "no_telp";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences myPref = context.getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        return ((myPref!=null) && (myPref.getString("username", null) != null));
    }

    public static void save(Context context) {
        SharedPreferences myPref = context.getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor myPrefEdit = myPref.edit();
        try {
            myPrefEdit.putString("username", USERNAME);
            myPrefEdit.apply();
        } catch (Exception e) {
            Log.e("User-Data", e.getMessage());
        }
    }

    public static void login(Context context, int id_user, String email, String alamat, String latitude,
                             String longitude, String nama_lengkap, String foto_profil, String no_telp){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID_USER,id_user);
        editor.putString(KEY_ALAMAT,alamat);
        editor.putString(KEY_LATITUDE,latitude);
        editor.putString(KEY_LONGITUDE,longitude);
        editor.putString(KEY_NAMA_LENGKAP,nama_lengkap);
        editor.putString(KEY_PHOTO_PROFILE,foto_profil);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_NO_TELP,no_telp);
        editor.apply();
    }


    public static int getIDuser (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID_USER,0);
    }

    public static void load(Context context) {
        SharedPreferences myPref = context.getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        try {
            USERNAME = myPref.getString("username", null);
        } catch (Exception e) {
            Log.e("User-Data", e.getMessage());
        }
    }

    public static void remove(Context context) {
        SharedPreferences myPref = context.getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor myPrefEdit = myPref.edit();
        myPrefEdit.clear();
        myPrefEdit.apply();
    }
}
