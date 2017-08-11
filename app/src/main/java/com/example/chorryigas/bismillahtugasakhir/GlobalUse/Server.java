package com.example.chorryigas.bismillahtugasakhir.GlobalUse;

/**
 * Created by Choy on 8/7/2017.
 */

public class Server {
    public static String URL = "http://websitegratis.esy.es/";
    public static String URLpath = URL+"guruku_api/";

    //untuk mendaftarkan segala pengguna [user+guru]
//param: name,username,previllage,password
    public static String REGISTER = URLpath + "register.php";

    //untuk masuk ke dalam aplikasi [user+guru]
//param: username,password,previllage
    public static String LOGIN = URLpath+"login.php";

    //untuk memperbarui informasi user [user]
//param: id_user,nama,alamat,no_telp,email,lat,lng,foto
    public static String USER_UPDATE = URLpath+"pengguna_update.php";

    //untuk mengambil data pengguna [user]
//param: id_user
    public static String USER_SELECT = URLpath+"pengguna_select.php";

    //untuk memperbarui informasi guru [guru]
//param: id_guru,nama,alamat,no_telp,kelamin,email,pengalaman,deskripsi,lat,lng,foto,usia,ipk,kampus
    public static String GURU_UPDATE = URLpath+"guru_update.php";

    //untuk mengambil data guru [guru]
//param: id_guru
    public static String GURU_SELECT = URLpath+"guru_select.php";

    //membuat lowongan pencarian guru les [user]
//param: id_user,subjek,description
    public static String LOWONGAN_CREATE = URLpath+"lowongan_create.php";

    //memperbarui informasi tentang lowongan [user]
//param: id,id_user,subjek,description
    public static String LOWONGAN_UPDATE = URLpath+"lowongan_update.php";

    //menghapus lowongan jika tidak terpakai lagi [user]
//param: id, id_user
    public static String LOWONGAN_DELETE = URLpath+"lowongan_delete.php";

    //mengambil semua informasi lowongan yang ada [guru]
//param:
    public static String LOWONGAN_GET_ALL = URLpath+"lowongan_get_all.php";

    //mengambil semua informasi lowongan yang telah dibuat [user]
//param: id_user
    public static String LOWONGAN_GET_BY = URLpath+"lowongan_get_by.php";

    //memberikan rating dan review kepada pengajar [user]
//param: id_user, id_guru, rating, review
    public static String RATING_CREATE = URLpath+"rating_create.php";

    //memperbarui rating dan review kepada pengajar [user]
//param: id_user, id_guru, rating, review
    public static String RATING_UPDATE = URLpath+"rating_update.php";

    //mengambil informasi rating guru [user+guru]
//param: id_guru
    public static String RATING_GET = URLpath+"rating_get.php";

    //mengambil detail review dan rating guru [guru+user]
//param: id_user
    public static String RATING_GET_REVIEW = URLpath+"rating_get_review.php";

    //membuat skill mengajar guru [guru]
//param: id_guru,jenjang,mapel,biaya
    public static String SKILL_CREATE = URLpath+"skill_create.php";

    //memperbarui informasi skill mengajar guru [guru]
//param: id_guru,jenjang,mapel,biaya,id
    public static String SKILL_UPDATE = URLpath+"skill_update.php";

    //menghapus skill guru [guru]
//param: id_guru,id
    public static String SKILL_DELETE = URLpath+"skill_delete.php";

    //mengambil informasi skill guru [guru+user]
//param: id_guru
    public static String SKILL_GET_BY = URLpath+"skill_get_by.php";

    //membuat jadwal pelajaran [guru]
//param: id_guru,hari,jam_mulai
    public static String JADWAL_CREATE = URLpath+"jadwal_create.php";

    //mengambil jadwal sesuai dengan guru [guru+user]
//param: id_guru
    public static String JADWAL_GET_BY = URLpath+"jadwal_get_by.php";

    //memperbarui jadwal guru [guru]
//param: id,id_guru,hari,jam_mulai,jam_selesai
    public static String JADWAL_UPDATE = URLpath+"jadwal_update.php";

    //menghapus jadwal [guru]
//param: id,id_guru
    public static String JADWAL_DELETE = URLpath+"jadwal_delete.php";

    //memesan guru [user]
//param: id_user, id_guru
    public static String BOOKING_CREATE = URLpath+"booking_create.php";

    //mengkonfirmasi pesanan guru [guru]
//param: id_user, id_guru
    public static String BOOKING_UPDATE = URLpath+"booking_update.php";

    //membatalkan pesanan [user]
//param: id_user, id_guru
    public static String BOOKING_CANCEL = URLpath+"booking_cancel.php";

    //melihat daftar pesanan. untuk user akan mellihat daftar guru yang dipesan. untuk guru akan menampilkan daftar user yang memesan guru [user+guru]
//param: id_user,previllage (previllage = 1 untuk guru, previllage = 0 untuk user)
    public static String BOOKING_GET = URLpath+"booking_get.php";

    //mengambil lowongan [guru]
//param: id_guru, id_lowongan
    public static String TRANSAKSI_LOWONGAN_CREATE = URLpath+"transaksi_lowongan_create.php";

    //mengkonfirmasi lowongan [user]
//param: id_guru,id_lowongan
    public static String TRANSAKSI_LOWONGAN_UPDATE = URLpath+"transaksi_lowongan_update.php";

    //melihat daftar lowongan yang telah diambil [guru]
//param: id_guru
    public static String TRANSAKSI_LOWONGAN_GET = URLpath+"transaksi_lowongan_get.php";

    //membatalkan transaksi lowongan [guru]
//param: id_guru,id_lowongan
    public static String TRANSAKSI_LOWONGAN_CANCEL = URLpath+"transaksi_lowongan_cancel.php";

    //mencari fuzzy
    public static String FUZZY_URL = URLpath+"FuzzySearch.php";

    //mencari guru dengan rating tertinggi dalam radius 20km
    public static String GURU_GET_ALL = URLpath+"guru_get_all.php";
}
