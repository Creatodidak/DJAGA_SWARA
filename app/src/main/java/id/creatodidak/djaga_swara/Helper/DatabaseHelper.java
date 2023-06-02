package id.creatodidak.djaga_swara.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Models.DataCalon;
import id.creatodidak.djaga_swara.API.Models.Draft.CekTps;
import id.creatodidak.djaga_swara.API.Models.Draft.DraftDpt;
import id.creatodidak.djaga_swara.API.Models.Draft.FormC1;
import id.creatodidak.djaga_swara.API.Models.Draft.Lappam;
import id.creatodidak.djaga_swara.API.Models.Draft.Lapserah;
import id.creatodidak.djaga_swara.API.Models.Draft.Lapwal;
import id.creatodidak.djaga_swara.API.Models.Draft.Lokasi;
import id.creatodidak.djaga_swara.API.Models.Multi.Bupati;
import id.creatodidak.djaga_swara.API.Models.Multi.DPDRI;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDKab;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDProv;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRRI;
import id.creatodidak.djaga_swara.API.Models.Multi.Gubernur;
import id.creatodidak.djaga_swara.API.Models.Multi.Kades;
import id.creatodidak.djaga_swara.API.Models.Multi.Presiden;
import id.creatodidak.djaga_swara.API.Models.Multi.SuaraData;
import id.creatodidak.djaga_swara.API.Models.SprintListOffline;
import id.creatodidak.djaga_swara.API.Models.TpsActivity;
import id.creatodidak.djaga_swara.API.Models.TpsList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "djagaswara.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private Handler handler;
    private Runnable runnable;


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery3 = "CREATE TABLE IF NOT EXISTS sprinlist (id INTEGER PRIMARY KEY AUTOINCREMENT, nomor TEXT, judul TEXT, kode TEXT, tahun TEXT, penerbit TEXT, tandatangan TEXT, status TEXT, dasar TEXT, namaops TEXT, perintah TEXT, tanggal TEXT, tanggalmulai TEXT, tanggalberakhir TEXT, type TEXT, created_at TEXT, updated_at TEXT)";
        db.execSQL(createTableQuery3);

        String createTableQuery4 = "CREATE TABLE IF NOT EXISTS sprindetail (id INTEGER PRIMARY KEY AUTOINCREMENT, latitude TEXT, longitude TEXT, id_prov TEXT, id_sprin TEXT, id_kab TEXT, id_kec TEXT, id_des TEXT, id_tps TEXT, nomor_tps TEXT, ketua_kpps TEXT, hp_kpps TEXT, dpt_sementara TEXT, dpt_tetap TEXT, dpt_final TEXT, keterangan TEXT, status TEXT, lokasikotaksuara TEXT, created_at TEXT, updated_at TEXT, nama_des TEXT, nama_kec TEXT, nama_kab TEXT)";
        db.execSQL(createTableQuery4);

        String createTableQuery1 = "CREATE TABLE IF NOT EXISTS tpsactivity (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, lokasi TEXT, cektps TEXT, dpt TEXT, lappam TEXT, laphasil TEXT, formc1 TEXT, lapwal TEXT, lapserah TEXT)";
        db.execSQL(createTableQuery1);

        String createLokasi = "CREATE TABLE IF NOT EXISTS lokasi (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, latitude TEXT NOT NULL, longitude TEXT NOT NULL, status TEXT, created_at TEXT)";
        db.execSQL(createLokasi);

        String createCekTps = "CREATE TABLE IF NOT EXISTS cektps (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, foto TEXT NOT NULL, situasi TEXT NOT NULL, prediksi TEXT NOT NULL, status TEXT, created_at TEXT)";
        db.execSQL(createCekTps);

        String createFormc1 = "CREATE TABLE IF NOT EXISTS formc1 (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, foto TEXT NOT NULL, situasi TEXT NOT NULL, prediksi TEXT NOT NULL, status TEXT, created_at TEXT)";
        db.execSQL(createFormc1);

        String createLappam = "CREATE TABLE IF NOT EXISTS lappam (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, foto TEXT NOT NULL, situasi TEXT NOT NULL, prediksi TEXT NOT NULL, status TEXT, created_at TEXT)";
        db.execSQL(createLappam);

        String createLapwal = "CREATE TABLE IF NOT EXISTS lapwal (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, foto TEXT NOT NULL, situasi TEXT NOT NULL, prediksi TEXT NOT NULL, status TEXT, created_at TEXT)";
        db.execSQL(createLapwal);

        String createLapserah = "CREATE TABLE IF NOT EXISTS lapserah (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, foto TEXT NOT NULL, situasi TEXT NOT NULL, prediksi TEXT NOT NULL, status TEXT, created_at TEXT)";
        db.execSQL(createLapserah);

        String createdDraftdpt = "CREATE TABLE IF NOT EXISTS draftdpt (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, dpt_final TEXT NOT NULL, keterangan TEXT NOT NULL, status TEXT NOT NULL)";
        db.execSQL(createdDraftdpt);

        db.execSQL("CREATE TABLE IF NOT EXISTS presiden (id INTEGER, id_calon TEXT, no_urut TEXT, capres TEXT, cawapres TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS dprri (id INTEGER, id_calon TEXT, id_dapil TEXT, id_partai TEXT, nomorurut TEXT, nama TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS dpdri (id INTEGER, id_calon TEXT, id_prov TEXT, nomorurut TEXT, nama TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS dprdprov (id INTEGER, id_calon TEXT, id_dapil TEXT, id_partai TEXT, nomorurut TEXT, nama TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS dprdkab (id INTEGER, id_calon TEXT, id_dapil TEXT, id_partai TEXT, nomorurut TEXT, nama TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS gubernur (id INTEGER, id_prov TEXT, id_calon TEXT, no_urut TEXT, cagub TEXT, cawagub TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS bupati (id INTEGER, id_kab TEXT, id_calon TEXT, no_urut TEXT, cabup TEXT, cawabup TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS kades (id INTEGER, id_des TEXT, id_calon TEXT, no_urut TEXT, cakades TEXT, tahun TEXT, periode TEXT, created_at TEXT, updated_at TEXT, id_tps TEXT, suara TEXT, status TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS suaratidaksah (id INTEGER, id_tps TEXT, type TEXT, jumlah TEXT, status TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS sprinlist");
        db.execSQL("DROP TABLE IF EXISTS sprindetail");
        db.execSQL("DROP TABLE IF EXISTS tpsactivity");
        db.execSQL("DROP TABLE IF EXISTS lokasi");
        db.execSQL("DROP TABLE IF EXISTS cektps");
        db.execSQL("DROP TABLE IF EXISTS formc1");
        db.execSQL("DROP TABLE IF EXISTS lappam");
        db.execSQL("DROP TABLE IF EXISTS lapwal");
        db.execSQL("DROP TABLE IF EXISTS lapserah");
        db.execSQL("DROP TABLE IF EXISTS draftdpt");
        db.execSQL("DROP TABLE IF EXISTS presiden");
        db.execSQL("DROP TABLE IF EXISTS dprri");
        db.execSQL("DROP TABLE IF EXISTS dpdri");
        db.execSQL("DROP TABLE IF EXISTS dprdprov");
        db.execSQL("DROP TABLE IF EXISTS dprdkab");
        db.execSQL("DROP TABLE IF EXISTS gubernur");
        db.execSQL("DROP TABLE IF EXISTS bupati");
        db.execSQL("DROP TABLE IF EXISTS kades");
        db.execSQL("DROP TABLE IF EXISTS suaratidaksah");

        // Recreate the tables
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

    public void resetTpsActivityTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tpsactivity");
        db.execSQL("VACUUM");
    }

    public void resetCekTpsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM cektps");
        db.execSQL("VACUUM");
    }

    public void resetFormc1Table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM formc1");
        db.execSQL("VACUUM");
    }

    public void resetLappamTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM lappam");
        db.execSQL("VACUUM");
    }

    public void resetLapwalTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM lapwal");
        db.execSQL("VACUUM");
    }

    public void resetLapserahTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM lapserah");
        db.execSQL("VACUUM");
    }

    public void resetDraftdpt() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM draftdpt");
        db.execSQL("VACUUM");
    }

    public void resetPresiden() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM presiden");
        db.execSQL("VACUUM");
    }

    public void resetDPRRI() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM dprri");
        db.execSQL("VACUUM");
    }

    public void resetDPDRI() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM dpdri");
        db.execSQL("VACUUM");
    }

    public void resetDPRDProv() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM dprdprov");
        db.execSQL("VACUUM");
    }

    public void resetDPRDKab() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM dprdkab");
        db.execSQL("VACUUM");
    }

    public void resetGubernur() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM gubernur");
        db.execSQL("VACUUM");
    }

    public void resetBupati() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM bupati");
        db.execSQL("VACUUM");
    }

    public void resetKades() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM kades");
        db.execSQL("VACUUM");
    }


    public void newtpsactivity(String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_tps", id_tps);
        values.put("lokasi", "NO");
        values.put("cektps", "NO");
        values.put("dpt", "NO");
        values.put("lappam", "NO");
        values.put("laphasil", "NO");
        values.put("formc1", "NO");
        values.put("lapwal", "NO");
        values.put("lapserah", "NO");
        db.insert("tpsactivity", null, values);
        db.close();
    }

    public boolean updateTpsActivity(String id_tps, String kolom, String isi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(kolom, isi);

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        int rowsAffected = db.update("tpsactivity", values, selection, selectionArgs);
        db.close();

        return rowsAffected > 0;
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
    public List<TpsList> getSprinDetailData() {
        List<TpsList> tpsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id", "latitude", "longitude", "id_prov", "id_sprin", "id_kab", "id_kec", "id_des", "id_tps", "nomor_tps", "ketua_kpps", "hp_kpps", "dpt_sementara", "dpt_tetap", "dpt_final", "keterangan", "status", "lokasikotaksuara", "created_at", "updated_at", "nama_des", "nama_kec", "nama_kab"
        };

        Cursor cursor = db.query("sprindetail", columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                TpsList tps = new TpsList();
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

                tpsList.add(tps);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tpsList;
    }

    @SuppressLint("Range")
    public TpsActivity getTpsActivity(String id_tps){
        TpsActivity tpsActivity = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id_tps", "lokasi", "cektps", "dpt", "lappam", "laphasil", "formc1", "lapwal", "lapserah"};

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("tpsactivity", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            tpsActivity = new TpsActivity();

            tpsActivity.setTpsId(cursor.getString(cursor.getColumnIndex("id_tps")));
            tpsActivity.setLokasi(cursor.getString(cursor.getColumnIndex("lokasi")));
            tpsActivity.setCekTps(cursor.getString(cursor.getColumnIndex("cektps")));
            tpsActivity.setDpt(cursor.getString(cursor.getColumnIndex("dpt")));
            tpsActivity.setLappam(cursor.getString(cursor.getColumnIndex("lappam")));
            tpsActivity.setLaphasil(cursor.getString(cursor.getColumnIndex("laphasil")));
            tpsActivity.setFormc1(cursor.getString(cursor.getColumnIndex("formc1")));
            tpsActivity.setLapwal(cursor.getString(cursor.getColumnIndex("lapwal")));
            tpsActivity.setLapserah(cursor.getString(cursor.getColumnIndex("lapserah")));
        }
        cursor.close();
        db.close();

        return tpsActivity;
    }

    @SuppressLint("Range")
    public TpsList getSprindetail(String id_tps) {
        TpsList tps = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id", "latitude", "longitude", "id_prov", "id_sprin", "id_kab", "id_kec", "id_des", "id_tps", "nomor_tps", "ketua_kpps", "hp_kpps", "dpt_sementara", "dpt_tetap", "dpt_final", "keterangan", "status", "lokasikotaksuara", "created_at", "updated_at", "nama_des", "nama_kec", "nama_kab"};

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("sprindetail", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            tps = new TpsList();
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

    @SuppressLint("Range")
    public TpsList getDpt(String id_tps) {
        TpsList tps = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"dpt_sementara", "dpt_tetap", "dpt_final", "keterangan"};

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("sprindetail", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            tps = new TpsList();
            tps.setDptSementara(cursor.getString(cursor.getColumnIndex("dpt_sementara")));
            tps.setDptTetap(cursor.getString(cursor.getColumnIndex("dpt_tetap")));
            tps.setDptFinal(cursor.getString(cursor.getColumnIndex("dpt_final")));
            tps.setKeterangan(cursor.getString(cursor.getColumnIndex("keterangan")));
        }

        cursor.close();
        db.close();

        return tps;
    }

    public boolean updateDpt(String id_tps, String dptf, String ket) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("dpt_final", dptf);
        values.put("keterangan", ket);

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        int rowsAffected = db.update("sprindetail", values, selection, selectionArgs);
        db.close();

        return rowsAffected > 0;
    }

    public boolean addLokasi(String idTps, String latitude, String longitude, String status, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idTps);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("status", status);
        values.put("created_at", created_at);
        long result = db.insert("lokasi", null, values);
        db.close();
        return result != -1;
    }

    public boolean addCekTps(String idTps, String foto, String situasi, String prediksi, String status, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idTps);
        values.put("foto", foto);
        values.put("situasi", situasi);
        values.put("prediksi", prediksi);
        values.put("status", status);
        values.put("created_at", created_at);
        long result = db.insert("cektps", null, values);
        db.close();
        return result != -1;
    }

    public boolean addFormc1(String idTps, String foto, String situasi, String prediksi, String status, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idTps);
        values.put("foto", foto);
        values.put("situasi", situasi);
        values.put("prediksi", prediksi);
        values.put("status", status);
        values.put("created_at", created_at);
        long result = db.insert("formc1", null, values);
        db.close();
        return result != -1;
    }

    public boolean addLappam(String idTps, String foto, String situasi, String prediksi, String status, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idTps);
        values.put("foto", foto);
        values.put("situasi", situasi);
        values.put("prediksi", prediksi);
        values.put("status", status);
        values.put("created_at", created_at);
        long result = db.insert("lappam", null, values);
        db.close();
        return result != -1;
    }

    public boolean addLapwal(String idTps, String foto, String situasi, String prediksi, String status, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idTps);
        values.put("foto", foto);
        values.put("situasi", situasi);
        values.put("prediksi", prediksi);
        values.put("status", status);
        values.put("created_at", created_at);
        long result = db.insert("lapwal", null, values);
        db.close();
        return result != -1;
    }

    public boolean addLapserah(String idTps, String foto, String situasi, String prediksi, String status, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idTps);
        values.put("foto", foto);
        values.put("situasi", situasi);
        values.put("prediksi", prediksi);
        values.put("status", status);
        values.put("created_at", created_at);
        long result = db.insert("lapserah", null, values);
        db.close();
        return result != -1;
    }

    public boolean addDraftdpt(String idTps, String dptf, String keterangan, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idTps);
        values.put("dpt_final", dptf);
        values.put("keterangan", keterangan);
        values.put("status", status);
        long result = db.insert("draftdpt", null, values);
        db.close();
        return result != -1;
    }

    public List<String> getDraft(String tableName) {
        List<String> drafts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, null, "status = ?", new String[]{"LOCAL"}, null, null, null);
        if (cursor.moveToFirst()) {
            int columnCount = cursor.getColumnCount();
            do {
                StringBuilder draftBuilder = new StringBuilder();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    draftBuilder.append(columnName).append(": ").append(columnValue).append("\n");
                }
                drafts.add(draftBuilder.toString());
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return drafts;
    }

    public void startRepeatedNotification(final Context context) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int jlokasi = getDraft("lokasi").size();
                int jcektps = getDraft("cektps").size();
                int jlappam = getDraft("lappam").size();
                int jformc1 = getDraft("formc1").size();
                int jlapwal = getDraft("lapwal").size();
                int jlapserah = getDraft("lapserah").size();
                int jdraftdpt = getDraft("draftdpt").size();
                int presidenj = getpresiden("LOCAL").size();
                int bupatij = getbupati("LOCAL").size();
                int gubernurj = getgubernur("LOCAL").size();
                int dprrij = getdprri("LOCAL").size();
                int dpdrij = getdpdri("LOCAL").size();
                int kadesj = getkades("LOCAL").size();
                int dprdProvj = getdprdprov("LOCAL").size();
                int dprdKabj = getdprdkab("LOCAL").size();
                int totalJumlahData = jlokasi + jcektps + jlappam + jformc1 + jlapwal + jlapserah + jdraftdpt + presidenj + bupatij + gubernurj + dprrij + dpdrij + kadesj +dprdProvj + dprdKabj;
                
                if (totalJumlahData != 0) {
                    String notificationTitle = "PERIKSA DRAFT ANDA!";
                    String notificationMessage = "Terdapat " + totalJumlahData + " data yang belum dilaporkan.\nNotifikasi ini akan terus muncul setiap 15 Menit...";

                        NotificationHelper.showNotification(context, notificationTitle, notificationMessage);
                }

                handler.postDelayed(this, 15 * 60 * 1000); // Check every 1 minute
            }
        };

        handler.postDelayed(runnable, 10 * 1000); // Delay first check for 1 minute
    }

    public void stopRepeatedNotification() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }

    @SuppressLint("Range")
    public List<Lokasi> getAllLokasi() {
        List<Lokasi> lokasiList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("lokasi", null, "status = ?", new String[]{"LOCAL"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_tps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                Lokasi lokasi = new Lokasi(id, id_tps, latitude, longitude, status, created_at);
                lokasiList.add(lokasi);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lokasiList;
    }

    @SuppressLint("Range")
    public List<CekTps> getAllCekTps() {
        List<CekTps> cekTpsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("cektps", null, "status = ?", new String[]{"LOCAL"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_tps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String foto = cursor.getString(cursor.getColumnIndex("foto"));
                String situasi = cursor.getString(cursor.getColumnIndex("situasi"));
                String prediksi = cursor.getString(cursor.getColumnIndex("prediksi"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                CekTps cekTps = new CekTps(id, id_tps, foto, situasi, prediksi, status, created_at);
                cekTpsList.add(cekTps);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cekTpsList;
    }

    @SuppressLint("Range")
    public List<FormC1> getAllFormC1() {
        List<FormC1> formC1List = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("formc1", null, "status = ?", new String[]{"LOCAL"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_tps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String foto = cursor.getString(cursor.getColumnIndex("foto"));
                String situasi = cursor.getString(cursor.getColumnIndex("situasi"));
                String prediksi = cursor.getString(cursor.getColumnIndex("prediksi"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                FormC1 formC1 = new FormC1(id, id_tps, foto, situasi, prediksi, status, created_at);
                formC1List.add(formC1);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return formC1List;
    }

    @SuppressLint("Range")
    public List<Lappam> getAllLappam() {
        List<Lappam> lappamList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("lappam", null, "status = ?", new String[]{"LOCAL"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_tps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String foto = cursor.getString(cursor.getColumnIndex("foto"));
                String situasi = cursor.getString(cursor.getColumnIndex("situasi"));
                String prediksi = cursor.getString(cursor.getColumnIndex("prediksi"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                Lappam lappam = new Lappam(id, id_tps, foto, situasi, prediksi, status, created_at);
                lappamList.add(lappam);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lappamList;
    }

    @SuppressLint("Range")
    public List<Lapwal> getAllLapwal() {
        List<Lapwal> lapwalList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("lapwal", null, "status = ?", new String[]{"LOCAL"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_tps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String foto = cursor.getString(cursor.getColumnIndex("foto"));
                String situasi = cursor.getString(cursor.getColumnIndex("situasi"));
                String prediksi = cursor.getString(cursor.getColumnIndex("prediksi"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                Lapwal lapwal = new Lapwal(id, id_tps, foto, situasi, prediksi, status, created_at);
                lapwalList.add(lapwal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lapwalList;
    }

    @SuppressLint("Range")
    public List<Lapserah> getAllLapserah() {
        List<Lapserah> lapserahList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("lapserah", null, "status = ?", new String[]{"LOCAL"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_tps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String foto = cursor.getString(cursor.getColumnIndex("foto"));
                String situasi = cursor.getString(cursor.getColumnIndex("situasi"));
                String prediksi = cursor.getString(cursor.getColumnIndex("prediksi"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                Lapserah lapserah = new Lapserah(id, id_tps, foto, situasi, prediksi, status, created_at);
                lapserahList.add(lapserah);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lapserahList;
    }

    @SuppressLint("Range")
    public List<DraftDpt> getAllDraftDpt() {
        List<DraftDpt> draftDptList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("draftdpt", null, "status = ?", new String[]{"LOCAL"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_tps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String dpt_final = cursor.getString(cursor.getColumnIndex("dpt_final"));
                String keterangan = cursor.getString(cursor.getColumnIndex("keterangan"));
                String status = cursor.getString(cursor.getColumnIndex("status"));

                DraftDpt draftDpt = new DraftDpt(id_tps, dpt_final, keterangan, status);
                draftDpt.setId(id);
                draftDptList.add(draftDpt);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return draftDptList;
    }


    public boolean updateStatus(String tableName, String idTps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "SERVER");
        int rowsAffected = db.update(tableName, values, "id_tps=?", new String[]{idTps});
        db.close();
        return rowsAffected > 0;
    }

    public void insertPresiden(int id, String id_calon, String no_urut, String capres, String cawapres, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_calon", id_calon);
        values.put("no_urut", no_urut);
        values.put("capres", capres);
        values.put("cawapres", cawapres);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("presiden", null, values);
        db.close();
    }

    public void insertDPRRI(int id, String id_calon, String id_dapil, String id_partai, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_calon", id_calon);
        values.put("id_dapil", id_dapil);
        values.put("id_partai", id_partai);
        values.put("nomorurut", nomorurut);
        values.put("nama", nama);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("dprri", null, values);
        db.close();
    }

    public void insertDPDRI(int id, String id_calon, String id_prov, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_calon", id_calon);
        values.put("id_prov", id_prov);
        values.put("nomorurut", nomorurut);
        values.put("nama", nama);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("dpdri", null, values);
        db.close();
    }

    public void insertDPRDProv(int id, String id_calon, String id_dapil, String id_partai, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_calon", id_calon);
        values.put("id_dapil", id_dapil);
        values.put("id_partai", id_partai);
        values.put("nomorurut", nomorurut);
        values.put("nama", nama);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("dprdprov", null, values);
        db.close();
    }

    public void insertDPRDKab(int id, String id_calon, String id_dapil, String id_partai, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_calon", id_calon);
        values.put("id_dapil", id_dapil);
        values.put("id_partai", id_partai);
        values.put("nomorurut", nomorurut);
        values.put("nama", nama);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("dprdkab", null, values);
        db.close();
    }

    public void insertGubernur(int id, String id_prov, String id_calon, String no_urut, String cagub, String cawagub, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_prov", id_prov);
        values.put("id_calon", id_calon);
        values.put("no_urut", no_urut);
        values.put("cagub", cagub);
        values.put("cawagub", cawagub);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("gubernur", null, values);
        db.close();
    }

    public void insertBupati(int id, String id_kab, String id_calon, String no_urut, String cabup, String cawabup, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_kab", id_kab);
        values.put("id_calon", id_calon);
        values.put("no_urut", no_urut);
        values.put("cabup", cabup);
        values.put("cawabup", cawabup);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("bupati", null, values);
        db.close();
    }

    public void insertKades(int id, String id_des, String id_calon, String no_urut, String cakades, String tahun, String periode, String created_at, String updated_at, String id_tps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("id_des", id_des);
        values.put("id_calon", id_calon);
        values.put("no_urut", no_urut);
        values.put("cakades", cakades);
        values.put("tahun", tahun);
        values.put("periode", periode);
        values.put("created_at", created_at);
        values.put("updated_at", updated_at);
        values.put("id_tps", id_tps);
        db.insert("kades", null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<DataCalon.Presiden> getAllPresiden(String id_tps) {
        List<DataCalon.Presiden> presidenList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("presiden", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
                String capres = cursor.getString(cursor.getColumnIndex("capres"));
                String cawapres = cursor.getString(cursor.getColumnIndex("cawapres"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.Presiden presiden = new DataCalon.Presiden(id, id_calon, no_urut, capres, cawapres, tahun, periode, created_at, updated_at);
                presidenList.add(presiden);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return presidenList;
    }

    @SuppressLint("Range")
    public List<DataCalon.DPRRI> getAllDprri(String id_tps) {
        List<DataCalon.DPRRI> dprriList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dprri", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String id_dapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
                String id_partai = cursor.getString(cursor.getColumnIndex("id_partai"));
                String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.DPRRI dprri = new DataCalon.DPRRI(id, id_calon, id_dapil, id_partai, nomorurut, nama, tahun, periode, created_at, updated_at);
                dprriList.add(dprri);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dprriList;
    }

    @SuppressLint("Range")
    public List<DataCalon.DPDRI> getAllDpdri(String id_tps) {
        List<DataCalon.DPDRI> dpdriList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dpdri", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String id_prov = cursor.getString(cursor.getColumnIndex("id_prov"));
                String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.DPDRI dpdri = new DataCalon.DPDRI(id, id_calon, id_prov, nomorurut, nama, tahun, periode, created_at, updated_at);
                dpdriList.add(dpdri);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dpdriList;
    }

    @SuppressLint("Range")
    public List<DataCalon.DPRDProv> getAllDPRDprov(String id_tps) {
        List<DataCalon.DPRDProv> dprdprovList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dprdprov", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String id_dapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
                String id_partai = cursor.getString(cursor.getColumnIndex("id_partai"));
                String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.DPRDProv dprdprov = new DataCalon.DPRDProv(id, id_calon, id_dapil, id_partai, nomorurut, nama, tahun, periode, created_at, updated_at);
                dprdprovList.add(dprdprov);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dprdprovList;
    }

    @SuppressLint("Range")
    public List<DataCalon.DPRDKab> getAllDPRDkab(String id_tps) {
        List<DataCalon.DPRDKab> dprdkabList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dprdkab", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String id_dapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
                String id_partai = cursor.getString(cursor.getColumnIndex("id_partai"));
                String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.DPRDKab dprdkab = new DataCalon.DPRDKab(id, id_calon, id_dapil, id_partai, nomorurut, nama, tahun, periode, created_at, updated_at);
                dprdkabList.add(dprdkab);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dprdkabList;
    }

    @SuppressLint("Range")
    public List<DataCalon.Gubernur> getAllGubernur(String id_tps) {
        List<DataCalon.Gubernur> gubernurList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("gubernur", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_prov = cursor.getString(cursor.getColumnIndex("id_prov"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
                String cagub = cursor.getString(cursor.getColumnIndex("cagub"));
                String cawagub = cursor.getString(cursor.getColumnIndex("cawagub"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.Gubernur gubernur = new DataCalon.Gubernur(id, id_prov, id_calon, no_urut, cagub, cawagub, tahun, periode, created_at, updated_at);
                gubernurList.add(gubernur);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return gubernurList;
    }

    @SuppressLint("Range")
    public List<DataCalon.Bupati> getAllBupati(String id_tps) {
        List<DataCalon.Bupati> bupatiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("bupati", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_kab = cursor.getString(cursor.getColumnIndex("id_kab"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
                String cabup = cursor.getString(cursor.getColumnIndex("cabup"));
                String cawabup = cursor.getString(cursor.getColumnIndex("cawabup"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.Bupati bupati = new DataCalon.Bupati(id, id_kab, id_calon, no_urut, cabup, cawabup, tahun, periode, created_at, updated_at);
                bupatiList.add(bupati);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bupatiList;
    }

    @SuppressLint("Range")
    public List<DataCalon.Kades> getAllKades(String id_tps) {
        List<DataCalon.Kades> kadesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("kades", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String id_des = cursor.getString(cursor.getColumnIndex("id_des"));
                String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
                String cakades = cursor.getString(cursor.getColumnIndex("cakades"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));

                DataCalon.Kades kades = new DataCalon.Kades(id, id_des, id_calon, no_urut, cakades, tahun, periode, created_at, updated_at);
                kadesList.add(kades);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return kadesList;
    }

    public boolean isTableEmpty(String tableName, String idTps) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id"};

        String selection = "id_tps = ?";
        String[] selectionArgs = {idTps};

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);
        boolean isEmpty = (cursor.getCount() == 0);
        cursor.close();

        return isEmpty;
    }

    @SuppressLint("Range")
    public List<Presiden> getPresidenData(String id_tps) {
        List<Presiden> presidenList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("presiden", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
            String capres = cursor.getString(cursor.getColumnIndex("capres"));
            String cawapres = cursor.getString(cursor.getColumnIndex("cawapres"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            Presiden presiden = new Presiden(id, id_calon, no_urut, capres, cawapres, tahun, periode, created_at, updated_at, tps_id, suara, status);
            presidenList.add(presiden);
        }

        cursor.close();
        return presidenList;
    }

    @SuppressLint("Range")
    public List<DPRRI> getDPRRIdata(String id_tps) {
        List<DPRRI> dprriList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dprri", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String id_dapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
            String id_partai = cursor.getString(cursor.getColumnIndex("id_partai"));
            String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            DPRRI dprri = new DPRRI(id, id_calon, id_dapil, id_partai, nomorurut, nama, tahun, periode, created_at, updated_at, tps_id, suara, status);
            dprriList.add(dprri);
        }

        cursor.close();
        return dprriList;
    }

    @SuppressLint("Range")
    public List<DPDRI> getDPDRIdata(String id_tps) {
        List<DPDRI> dpdriList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dpdri", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String id_prov = cursor.getString(cursor.getColumnIndex("id_prov"));
            String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            DPDRI dpdri = new DPDRI(id, id_calon, id_prov, nomorurut, nama, tahun, periode, created_at, updated_at, tps_id, suara, status);
            dpdriList.add(dpdri);
        }

        cursor.close();
        return dpdriList;
    }

    @SuppressLint("Range")
    public List<DPRDProv> getDPRDProvData(String id_tps) {
        List<DPRDProv> dprdProvList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dprdprov", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String id_dapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
            String id_partai = cursor.getString(cursor.getColumnIndex("id_partai"));
            String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            DPRDProv dprdProv = new DPRDProv(id, id_calon, id_dapil, id_partai, nomorurut, nama, tahun, periode, created_at, updated_at, tps_id, suara, status);
            dprdProvList.add(dprdProv);
        }

        cursor.close();
        return dprdProvList;
    }

    @SuppressLint("Range")
    public List<DPRDKab> getDPRDKabData(String id_tps) {
        List<DPRDKab> dprdkabList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("dprdkab", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String id_dapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
            String id_partai = cursor.getString(cursor.getColumnIndex("id_partai"));
            String nomorurut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            DPRDKab dprdkab = new DPRDKab(id, id_calon, id_dapil, id_partai, nomorurut, nama, tahun, periode, created_at, updated_at, tps_id, suara, status);
            dprdkabList.add(dprdkab);
        }

        cursor.close();
        return dprdkabList;
    }

    @SuppressLint("Range")
    public List<Gubernur> getGubernurData(String id_tps) {
        List<Gubernur> gubernurList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("gubernur", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_prov = cursor.getString(cursor.getColumnIndex("id_prov"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
            String cagub = cursor.getString(cursor.getColumnIndex("cagub"));
            String cawagub = cursor.getString(cursor.getColumnIndex("cawagub"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            Gubernur gubernur = new Gubernur(id, id_prov, id_calon, no_urut, cagub, cawagub, tahun, periode, created_at, updated_at, tps_id, suara, status);
            gubernurList.add(gubernur);
        }

        cursor.close();
        return gubernurList;
    }

    @SuppressLint("Range")
    public List<Bupati> getBupatiData(String id_tps) {
        List<Bupati> bupatiList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("bupati", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_kab = cursor.getString(cursor.getColumnIndex("id_kab"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
            String cabup = cursor.getString(cursor.getColumnIndex("cabup"));
            String cawabup = cursor.getString(cursor.getColumnIndex("cawabup"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            Bupati bupati = new Bupati(id, id_kab, id_calon, no_urut, cabup, cawabup, tahun, periode, created_at, updated_at, tps_id, suara, status);
            bupatiList.add(bupati);
        }

        cursor.close();
        return bupatiList;
    }

    @SuppressLint("Range")
    public List<Kades> getKadesData(String id_tps) {
        List<Kades> kadesList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "id_tps = ?";
        String[] selectionArgs = {id_tps};

        Cursor cursor = db.query("kades", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String id_des = cursor.getString(cursor.getColumnIndex("id_des"));
            String id_calon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String no_urut = cursor.getString(cursor.getColumnIndex("no_urut"));
            String cakades = cursor.getString(cursor.getColumnIndex("cakades"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
            String tps_id = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            Kades kades = new Kades(id, id_des, id_calon, no_urut, cakades, tahun, periode, created_at, updated_at, tps_id, suara, status);
            kadesList.add(kades);
        }

        cursor.close();
        return kadesList;
    }

    public boolean updateSuara(String tableName, SuaraData suaraData, String status, String calonId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("suara", suaraData.getSuara());
        values.put("status", status);
        int rowsAffected = db.update(tableName, values, "id_tps=? AND id_calon=?", new String[]{suaraData.getTpsId(), calonId});
        db.close();
        return rowsAffected > 0;
    }


    public boolean savesuaratidaksah(String tpsId, int suaratidaksah, String type, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", tpsId);
        values.put("type", type);
        values.put("jumlah", suaratidaksah);
        values.put("status", status);

        try {
            db.insertOrThrow("suaratidaksah", null, values);
            db.close();
            return true;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("Range")
    public List<Presiden> getpresiden(String status) {
        List<Presiden> presidenList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("presiden", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToNext()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
                String noUrut = cursor.getString(cursor.getColumnIndex("no_urut"));
                String capres = cursor.getString(cursor.getColumnIndex("capres"));
                String cawapres = cursor.getString(cursor.getColumnIndex("cawapres"));
                String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
                String periode = cursor.getString(cursor.getColumnIndex("periode"));
                String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
                String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
                String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
                String suara = cursor.getString(cursor.getColumnIndex("suara"));
                String statuS = cursor.getString(cursor.getColumnIndex("status"));

                Presiden presiden = new Presiden(id, idCalon, noUrut, capres, cawapres, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
                presidenList.add(presiden);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return presidenList;
    }

    @SuppressLint("Range")
    public List<DPRRI> getdprri(String status) {
        List<DPRRI> dprriList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("dprri", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String idDapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
            String idPartai = cursor.getString(cursor.getColumnIndex("id_partai"));
            String nomorUrut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
            String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String statuS = cursor.getString(cursor.getColumnIndex("status"));

            DPRRI dprri = new DPRRI(id, idCalon, idDapil, idPartai, nomorUrut, nama, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
            dprriList.add(dprri);
        }

        cursor.close();

        return dprriList;
    }


    @SuppressLint("Range")
    public List<DPDRI> getdpdri(String status) {
        List<DPDRI> dpdriList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("dpdri", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String idProv = cursor.getString(cursor.getColumnIndex("id_prov"));
            String nomorUrut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
            String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String statuS = cursor.getString(cursor.getColumnIndex("status"));

            DPDRI dpdri = new DPDRI(id, idCalon, idProv, nomorUrut, nama, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
            dpdriList.add(dpdri);
        }

        cursor.close();

        return dpdriList;
    }

    @SuppressLint("Range")
    public List<DPRDProv> getdprdprov(String status) {
        List<DPRDProv> dprdProvList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("dprdprov", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String idDapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
            String idPartai = cursor.getString(cursor.getColumnIndex("id_partai"));
            String nomorUrut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
            String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String statuS = cursor.getString(cursor.getColumnIndex("status"));

            DPRDProv dprdProv = new DPRDProv(id, idCalon, idDapil, idPartai, nomorUrut, nama, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
            dprdProvList.add(dprdProv);
        }

        cursor.close();

        return dprdProvList;
    }

    @SuppressLint("Range")
    public List<DPRDKab> getdprdkab(String status) {
        List<DPRDKab> dprdKabList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("dprdkab", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String idDapil = cursor.getString(cursor.getColumnIndex("id_dapil"));
            String idPartai = cursor.getString(cursor.getColumnIndex("id_partai"));
            String nomorUrut = cursor.getString(cursor.getColumnIndex("nomorurut"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
            String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String statuS = cursor.getString(cursor.getColumnIndex("status"));

            DPRDKab dprdKab = new DPRDKab(id, idCalon, idDapil, idPartai, nomorUrut, nama, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
            dprdKabList.add(dprdKab);
        }

        cursor.close();

        return dprdKabList;
    }

    @SuppressLint("Range")
    public List<Gubernur> getgubernur(String status) {
        List<Gubernur> gubernurList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("gubernur", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String idProv = cursor.getString(cursor.getColumnIndex("id_prov"));
            String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String noUrut = cursor.getString(cursor.getColumnIndex("no_urut"));
            String cagub = cursor.getString(cursor.getColumnIndex("cagub"));
            String cawagub = cursor.getString(cursor.getColumnIndex("cawagub"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
            String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String statuS = cursor.getString(cursor.getColumnIndex("status"));

            Gubernur gubernur = new Gubernur(id, idProv, idCalon, noUrut, cagub, cawagub, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
            gubernurList.add(gubernur);
        }

        cursor.close();

        return gubernurList;
    }

    @SuppressLint("Range")
    public List<Bupati> getbupati(String status) {
        List<Bupati> bupatiList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("bupati", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String idKab = cursor.getString(cursor.getColumnIndex("id_kab"));
            String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String noUrut = cursor.getString(cursor.getColumnIndex("no_urut"));
            String cabup = cursor.getString(cursor.getColumnIndex("cabup"));
            String cawabup = cursor.getString(cursor.getColumnIndex("cawabup"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
            String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String statuS = cursor.getString(cursor.getColumnIndex("status"));

            Bupati bupati = new Bupati(id, idKab, idCalon, noUrut, cabup, cawabup, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
            bupatiList.add(bupati);
        }

        cursor.close();

        return bupatiList;
    }

    @SuppressLint("Range")
    public List<Kades> getkades(String status) {
        List<Kades> kadesList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status = ?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query("kades", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String idDes = cursor.getString(cursor.getColumnIndex("id_des"));
            String idCalon = cursor.getString(cursor.getColumnIndex("id_calon"));
            String noUrut = cursor.getString(cursor.getColumnIndex("no_urut"));
            String cakades = cursor.getString(cursor.getColumnIndex("cakades"));
            String tahun = cursor.getString(cursor.getColumnIndex("tahun"));
            String periode = cursor.getString(cursor.getColumnIndex("periode"));
            String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));
            String idTps = cursor.getString(cursor.getColumnIndex("id_tps"));
            String suara = cursor.getString(cursor.getColumnIndex("suara"));
            String statuS = cursor.getString(cursor.getColumnIndex("status"));

            Kades kades = new Kades(id, idDes, idCalon, noUrut, cakades, tahun, periode, createdAt, updatedAt, idTps, suara, statuS);
            kadesList.add(kades);
        }

        cursor.close();

        return kadesList;
    }

    public boolean ceksuara(String table, String idTps) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status IS NULL AND id_tps = ?";
        String[] selectionArgs = {idTps};

        Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null);

        boolean dataExists = false;
        if (cursor != null && cursor.getCount() > 0) {
            dataExists = true;
            cursor.close();
        }

        return dataExists;
    }

    public boolean ceksuara2(String[] tables, String idTps) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "status IS NULL AND id_tps = ?";
        String[] selectionArgs = {idTps};

        boolean dataExists = false;

        for (String table : tables) {
            Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                dataExists = true;
                cursor.close();
                break; // Break out of the loop if data is found in any table
            }
        }

        return dataExists;
    }




}