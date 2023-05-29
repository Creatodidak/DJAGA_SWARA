package id.creatodidak.djaga_swara.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Models.SprintListOffline;
import id.creatodidak.djaga_swara.API.Models.TpsListOffline;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "djagaswara.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery3 = "CREATE TABLE IF NOT EXISTS sprinlist (id INTEGER PRIMARY KEY AUTOINCREMENT, nomor TEXT, judul TEXT, kode TEXT, tahun TEXT, penerbit TEXT, tandatangan TEXT, status TEXT, dasar TEXT, namaops TEXT, perintah TEXT, tanggal TEXT, tanggalmulai TEXT, tanggalberakhir TEXT, type TEXT, created_at TEXT, updated_at TEXT)";
        db.execSQL(createTableQuery3);

        String createTableQuery4 = "CREATE TABLE IF NOT EXISTS sprindetail (id INTEGER PRIMARY KEY AUTOINCREMENT, latitude TEXT, longitude TEXT, id_prov TEXT, id_sprin TEXT, id_kab TEXT, id_kec TEXT, id_des TEXT, id_tps TEXT, nomor_tps TEXT, ketua_kpps TEXT, hp_kpps TEXT, dpt_sementara TEXT, dpt_tetap TEXT, dpt_final TEXT, keterangan TEXT, status TEXT, lokasikotaksuara TEXT, created_at TEXT, updated_at TEXT, nama_des TEXT, nama_kec TEXT, nama_kab TEXT)";
        db.execSQL(createTableQuery4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery3 = "DROP TABLE IF EXISTS sprinlist";
        db.execSQL(dropTableQuery3);

        String dropTableQuery4 = "DROP TABLE IF EXISTS sprindetail";
        db.execSQL(dropTableQuery4);

        onCreate(db);
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Hapus data dari tabel sprinlist
        db.delete("sprinlist", null, null);
        // Set ulang ID di tabel sprinlist
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='sprinlist'");

        db.close();
    }

    public void resetTables2() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Hapus data dari tabel sprindetail
        db.delete("sprindetail", null, null);
        // Set ulang ID di tabel sprindetail
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='sprindetail'");

        db.close();
    }

    // Fungsi untuk menyisipkan data ke dalam tabel sprinlist
    public void insertSprinListData(String nomor, String judul, String kode, String tahun, String penerbit, String tandatangan, String status, String dasar, String namaops, String perintah, String tanggal, String tanggalmulai, String tanggalberakhir, String type, String created_at, String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nomor", nomor);
        values.put("judul", judul);
        values.put("kode", kode);
        values.put("tahun", tahun);
        values.put("penerbit", penerbit);
        values.put("tandatangan", tandatangan);
        values.put("status", status);
        values.put("dasar", dasar);
        values.put("namaops", namaops);
        values.put("perintah", perintah);
        values.put("tanggal", tanggal);
        values.put("tanggalmulai", tanggalmulai);
        values.put("tanggalberakhir", tanggalberakhir);
        values.put("type", type);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        db.insert("sprinlist", null, values);
        db.close();
    }

    // Fungsi untuk menyisipkan data ke dalam tabel sprindetail
    public void insertSprinDetailData(int id, String latitude, String longitude, String id_prov, String id_sprin, String id_kab, String id_kec, String id_des, String id_tps, String nomor_tps, String ketua_kpps, String hp_kpps, String dpt_sementara, String dpt_tetap, String dpt_final, String keterangan, String status, String lokasikotaksuara, String created_at, String updated_at, String nama_des, String nama_kec, String nama_kab) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("id_prov", id_prov);
        values.put("id_sprin", id_sprin);
        values.put("id_kab", id_kab);
        values.put("id_kec", id_kec);
        values.put("id_des", id_des);
        values.put("id_tps", id_tps);
        values.put("nomor_tps", nomor_tps);
        values.put("ketua_kpps", ketua_kpps);
        values.put("hp_kpps", hp_kpps);
        values.put("dpt_sementara", dpt_sementara);
        values.put("dpt_tetap", dpt_tetap);
        values.put("dpt_final", dpt_final);
        values.put("keterangan", keterangan);
        values.put("status", status);
        values.put("lokasikotaksuara", lokasikotaksuara);
        values.put("nama_des", nama_des);
        values.put("nama_kec", nama_kec);
        values.put("nama_kab", nama_kab);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);


        db.insert("sprindetail", null, values);
        db.close();
    }

    public List<SprintListOffline> getAllSprintList() {
        List<SprintListOffline> sprintList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM sprinlist";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Ambil data dari cursor
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String nomor = cursor.getString(cursor.getColumnIndex("nomor"));
                @SuppressLint("Range") String judul = cursor.getString(cursor.getColumnIndex("judul"));
                @SuppressLint("Range") String kode = cursor.getString(cursor.getColumnIndex("kode"));
                @SuppressLint("Range") String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                @SuppressLint("Range") String penerbit = cursor.getString(cursor.getColumnIndex("penerbit"));
                @SuppressLint("Range") String tandatangan = cursor.getString(cursor.getColumnIndex("tandatangan"));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
                @SuppressLint("Range") String dasar = cursor.getString(cursor.getColumnIndex("dasar"));
                @SuppressLint("Range") String namaops = cursor.getString(cursor.getColumnIndex("namaops"));
                @SuppressLint("Range") String perintah = cursor.getString(cursor.getColumnIndex("perintah"));
                @SuppressLint("Range") String tanggal = cursor.getString(cursor.getColumnIndex("tanggal"));
                @SuppressLint("Range") String tanggalmulai = cursor.getString(cursor.getColumnIndex("tanggalmulai"));
                @SuppressLint("Range") String tanggalberakhir = cursor.getString(cursor.getColumnIndex("tanggalberakhir"));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("type"));
                @SuppressLint("Range") String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                @SuppressLint("Range") String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                // Buat objek SprintListOffline dan tambahkan ke daftar sprintList
                SprintListOffline sprint = new SprintListOffline(id, nomor, judul, kode, tahun, penerbit, tandatangan, status, dasar, namaops, perintah, tanggal, tanggalmulai, tanggalberakhir, type, created_at, updated_at);
                sprintList.add(sprint);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sprintList;
    }

    @SuppressLint("Range")
    public List<TpsListOffline> getSprinDetailData() {
        List<TpsListOffline> tpsListOffline = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id", "latitude", "longitude", "id_prov", "id_sprin", "id_kab", "id_kec", "id_des", "id_tps", "nomor_tps", "ketua_kpps", "hp_kpps", "dpt_sementara", "dpt_tetap", "dpt_final", "keterangan", "status", "lokasikotaksuara", "created_at", "updated_at", "nama_des", "nama_kec", "nama_kab"
        };

        Cursor cursor = db.query("sprindetail", columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                TpsListOffline tps = new TpsListOffline();
                tps.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tps.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                tps.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                tps.setIdProv(cursor.getString(cursor.getColumnIndex("id_prov")));
                tps.setIdSprin(cursor.getString(cursor.getColumnIndex("id_sprin")));
                tps.setIdKab(cursor.getString(cursor.getColumnIndex("id_kab")));
                tps.setIdKec(cursor.getString(cursor.getColumnIndex("id_kec")));
                tps.setIdDes(cursor.getString(cursor.getColumnIndex("id_des")));
                tps.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
                tps.setNomorTps(cursor.getString(cursor.getColumnIndex("nomor_tps")));
                tps.setKetuaKpps(cursor.getString(cursor.getColumnIndex("ketua_kpps")));
                tps.setHpKpps(cursor.getString(cursor.getColumnIndex("hp_kpps")));
                tps.setDptSementara(cursor.getString(cursor.getColumnIndex("dpt_sementara")));
                tps.setDptTetap(cursor.getString(cursor.getColumnIndex("dpt_tetap")));
                tps.setDptFinal(cursor.getString(cursor.getColumnIndex("dpt_final")));
                tps.setKeterangan(cursor.getString(cursor.getColumnIndex("keterangan")));
                tps.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                tps.setLokasiKotakSuara(cursor.getString(cursor.getColumnIndex("lokasikotaksuara")));
                tps.setNamaDes(cursor.getString(cursor.getColumnIndex("nama_des")));
                tps.setNamaKec(cursor.getString(cursor.getColumnIndex("nama_kec")));
                tps.setNamaKab(cursor.getString(cursor.getColumnIndex("nama_kab")));
                tps.setCreatedAt(cursor.getString(cursor.getColumnIndex("created_at")));
                tps.setUpdatedAt(cursor.getString(cursor.getColumnIndex("updated_at")));

                tpsListOffline.add(tps);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tpsListOffline;
    }

    @SuppressLint("Range")
    public TpsListOffline getSprindetail(String id_tps) {
        TpsListOffline tps = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id", "latitude", "longitude", "id_prov", "id_sprin", "id_kab", "id_kec", "id_des", "id_tps", "nomor_tps", "ketua_kpps", "hp_kpps", "dpt_sementara", "dpt_tetap", "dpt_final", "keterangan", "status", "lokasikotaksuara", "created_at", "updated_at", "nama_des", "nama_kec", "nama_kab"};

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("sprindetail", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            tps = new TpsListOffline();
            tps.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tps.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
            tps.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
            tps.setIdProv(cursor.getString(cursor.getColumnIndex("id_prov")));
            tps.setIdSprin(cursor.getString(cursor.getColumnIndex("id_sprin")));
            tps.setIdKab(cursor.getString(cursor.getColumnIndex("id_kab")));
            tps.setIdKec(cursor.getString(cursor.getColumnIndex("id_kec")));
            tps.setIdDes(cursor.getString(cursor.getColumnIndex("id_des")));
            tps.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            tps.setNomorTps(cursor.getString(cursor.getColumnIndex("nomor_tps")));
            tps.setKetuaKpps(cursor.getString(cursor.getColumnIndex("ketua_kpps")));
            tps.setHpKpps(cursor.getString(cursor.getColumnIndex("hp_kpps")));
            tps.setDptSementara(cursor.getString(cursor.getColumnIndex("dpt_sementara")));
            tps.setDptTetap(cursor.getString(cursor.getColumnIndex("dpt_tetap")));
            tps.setDptFinal(cursor.getString(cursor.getColumnIndex("dpt_final")));
            tps.setKeterangan(cursor.getString(cursor.getColumnIndex("keterangan")));
            tps.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            tps.setLokasiKotakSuara(cursor.getString(cursor.getColumnIndex("lokasikotaksuara")));
            tps.setNamaDes(cursor.getString(cursor.getColumnIndex("nama_des")));
            tps.setNamaKec(cursor.getString(cursor.getColumnIndex("nama_kec")));
            tps.setNamaKab(cursor.getString(cursor.getColumnIndex("nama_kab")));
            tps.setCreatedAt(cursor.getString(cursor.getColumnIndex("created_at")));
            tps.setUpdatedAt(cursor.getString(cursor.getColumnIndex("updated_at")));
        }

        cursor.close();
        db.close();

        return tps;
    }



}