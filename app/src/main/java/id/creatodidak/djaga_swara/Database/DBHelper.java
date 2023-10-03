package id.creatodidak.djaga_swara.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFormC1;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapCekTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapPamTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapSerahTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapWalTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListbupatiItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListdpdriItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListdprdkabItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListdprdprovItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListdprriItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListgubernurItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListkadesItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListpartaikabItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListpartainasItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListpartaiprovItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListpresidenItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListsuaraItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListsuarapartaiItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListtpsItem;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DATABASE.db";
    private static final int DATABASE_VERSION = 1;
    String TBTPS = "CREATE TABLE IF NOT EXISTS tps (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT NOT NULL, latitude TEXT NOT NULL, longitude TEXT NOT NULL, id_prov TEXT NOT NULL, periode TEXT NOT NULL, id_kab TEXT NOT NULL, id_kec TEXT NOT NULL, id_des TEXT NOT NULL, nomor_tps TEXT NOT NULL, personil TEXT NOT NULL, dpt_sementara TEXT NOT NULL, dpt_tetap TEXT NOT NULL, dpt_final TEXT NOT NULL, keterangan TEXT NOT NULL, status TEXT NOT NULL, lokasikotaksuara TEXT NOT NULL)";
    String TBLAPCEKTPS = "CREATE TABLE IF NOT EXISTS lapcektps (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, latitude TEXT NOT NULL, longitude TEXT NOT NULL, fakta TEXT, analisa TEXT, prediksi TEXT, rekomendasi TEXT, dokumentasi TEXT, local INTEGER DEFAULT 1)";

    String TBLAPPAMTPS = "CREATE TABLE IF NOT EXISTS lappamtps (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, fakta TEXT, analisa TEXT, prediksi TEXT, rekomendasi TEXT, dokumentasi TEXT, local INTEGER DEFAULT 1)";

    String TBFORMC1 = "CREATE TABLE IF NOT EXISTS formc1 (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, type TEXT, dokumentasi TEXT, local INTEGER DEFAULT 1)";

    String TBLAPWAL = "CREATE TABLE IF NOT EXISTS lapwal (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, fakta TEXT, analisa TEXT, prediksi TEXT, rekomendasi TEXT, dokumentasi TEXT, local INTEGER DEFAULT 1)";

    String TBLAPSERAH = "CREATE TABLE IF NOT EXISTS lapserah (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, fakta TEXT, analisa TEXT, prediksi TEXT, rekomendasi TEXT, dokumentasi TEXT, local INTEGER DEFAULT 1)";

    String TBSUARA = "CREATE TABLE IF NOT EXISTS datasuarasah (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, type TEXT, id_calon TEXT, suara TEXT, local INTEGER DEFAULT 1)";
    String TBSUARATIDAKSAH = "CREATE TABLE IF NOT EXISTS datasuaratidaksah (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, type TEXT, total TEXT, local INTEGER DEFAULT 1)";
    String TBSUARAPARTAI = "CREATE TABLE IF NOT EXISTS datasuarapartai (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, type TEXT, id_partai TEXT, suara TEXT, local INTEGER DEFAULT 1)";
    String TBLISTPRESIDEN = "CREATE TABLE IF NOT EXISTS daftarpresiden (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, no_urut TEXT, capres TEXT, cawapres TEXT, periode TEXT NOT NULL)";
    String TBLISTGUBERNUR = "CREATE TABLE IF NOT EXISTS daftargubernur (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, no_urut TEXT, cagub TEXT, cawagub TEXT, periode TEXT NOT NULL)";
    String TBLISTBUPATI = "CREATE TABLE IF NOT EXISTS daftarbupati (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, no_urut TEXT, cabup TEXT, cawabup TEXT, periode TEXT NOT NULL)";
    String TBLISTKADES = "CREATE TABLE IF NOT EXISTS daftarkades (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, no_urut TEXT, cakades TEXT, periode TEXT NOT NULL)";
    String TBLISTDPDRI = "CREATE TABLE IF NOT EXISTS daftardpdri (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, nomorurut TEXT, nama TEXT, periode TEXT NOT NULL)";
    String TBLISTDPRRI = "CREATE TABLE IF NOT EXISTS daftardprri (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, id_partai TEXT, nomorurut TEXT, nama TEXT, periode TEXT NOT NULL)";
    String TBLISTDPRDPROV = "CREATE TABLE IF NOT EXISTS daftardprdprov (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, id_partai TEXT, nomorurut TEXT, nama TEXT, periode TEXT NOT NULL)";
    String TBLISTDPRDKAB = "CREATE TABLE IF NOT EXISTS daftardprdkab (id INTEGER PRIMARY KEY AUTOINCREMENT, id_calon TEXT, id_partai TEXT, nomorurut TEXT, nama TEXT, periode TEXT NOT NULL)";
    String TBLISTPARTAINAS = "CREATE TABLE IF NOT EXISTS daftarpartainas (id INTEGER PRIMARY KEY AUTOINCREMENT, id_partai TEXT, nomorurut TEXT, nama TEXT, periode TEXT NOT NULL)";
    String TBLISTPARTAIPROV = "CREATE TABLE IF NOT EXISTS daftarpartaiprov (id INTEGER PRIMARY KEY AUTOINCREMENT, id_partai TEXT, nomorurut TEXT, nama TEXT, periode TEXT NOT NULL)";
    String TBLISTPARTAIKAB = "CREATE TABLE IF NOT EXISTS daftarpartaikab (id INTEGER PRIMARY KEY AUTOINCREMENT, id_partai TEXT, nomorurut TEXT, nama TEXT, periode TEXT NOT NULL)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TBTPS);
        db.execSQL(TBSUARA);
        db.execSQL(TBSUARATIDAKSAH);
        db.execSQL(TBSUARAPARTAI);
        db.execSQL(TBLISTPRESIDEN);
        db.execSQL(TBLISTGUBERNUR);
        db.execSQL(TBLISTBUPATI);
        db.execSQL(TBLISTKADES);
        db.execSQL(TBLISTDPDRI);
        db.execSQL(TBLISTDPRRI);
        db.execSQL(TBLISTDPRDPROV);
        db.execSQL(TBLISTDPRDKAB);
        db.execSQL(TBLISTPARTAINAS);
        db.execSQL(TBLISTPARTAIPROV);
        db.execSQL(TBLISTPARTAIKAB);
        db.execSQL(TBLAPCEKTPS);
        db.execSQL(TBLAPPAMTPS);
        db.execSQL(TBFORMC1);
        db.execSQL(TBLAPWAL);
        db.execSQL(TBLAPSERAH);

    }

    public void init() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(TBTPS);
        db.execSQL(TBSUARA);
        db.execSQL(TBSUARATIDAKSAH);
        db.execSQL(TBSUARAPARTAI);
        db.execSQL(TBLISTPRESIDEN);
        db.execSQL(TBLISTGUBERNUR);
        db.execSQL(TBLISTBUPATI);
        db.execSQL(TBLISTKADES);
        db.execSQL(TBLISTDPDRI);
        db.execSQL(TBLISTDPRRI);
        db.execSQL(TBLISTDPRDPROV);
        db.execSQL(TBLISTDPRDKAB);
        db.execSQL(TBLISTPARTAINAS);
        db.execSQL(TBLISTPARTAIPROV);
        db.execSQL(TBLISTPARTAIKAB);
        db.execSQL(TBLAPCEKTPS);
        db.execSQL(TBLAPPAMTPS);
        db.execSQL(TBFORMC1);
        db.execSQL(TBLAPWAL);
        db.execSQL(TBLAPSERAH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean saveTps(ListtpsItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", data.getIdTps());
        values.put("latitude", (Double) data.getLatitude());
        values.put("longitude", (Double) data.getLongitude());
        values.put("id_prov", data.getIdProv());
        values.put("periode", data.getPeriode());
        values.put("id_kab", data.getIdKab());
        values.put("id_kec", data.getIdKec());
        values.put("id_des", data.getIdDes());
        values.put("nomor_tps", data.getNomorTps());
        values.put("personil", data.getPersonil());
        values.put("dpt_sementara", data.getDptSementara());
        values.put("dpt_tetap", data.getDptTetap());
        values.put("dpt_final", data.getDptFinal());
        values.put("keterangan", data.getKeterangan());
        values.put("status", data.getStatus());
        values.put("lokasikotaksuara", data.getLokasikotaksuara());

        long result = db.insert("tps", null, values);
        return result != -1;
    }

    public boolean createSuara(ListsuaraItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", data.getIdTps());
        values.put("type", data.getType());
        values.put("id_calon", data.getIdCalon());
        values.put("suara", "");
        long result = db.insert("datasuarasah", null, values);
        return result != -1;
    }

    public boolean createSuaraPartai(ListsuarapartaiItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", data.getIdTps());
        values.put("type", data.getType());
        values.put("id_partai", data.getIdPartai());
        values.put("suara", "");
        long result = db.insert("datasuarapartai", null, values);
        return result != -1;
    }

    public boolean savePartaiNas(ListpartainasItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_partai", data.getIdPartai());
        values.put("nomorurut", data.getNomorurut());
        values.put("nama", data.getNama());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftarpartainas", null, values);
        return result != -1;
    }

    public boolean savePartaiProv(ListpartaiprovItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_partai", data.getIdPartai());
        values.put("nomorurut", data.getNomorurut());
        values.put("nama", data.getNama());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftarpartaiprov", null, values);
        return result != -1;
    }

    public boolean savePartaiKab(ListpartaikabItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_partai", data.getIdPartai());
        values.put("nomorurut", data.getNomorurut());
        values.put("nama", data.getNama());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftarpartaikab", null, values);
        return result != -1;
    }

    public boolean savePresiden(ListpresidenItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("no_urut", data.getNoUrut());
        values.put("capres", data.getCapres());
        values.put("cawapres", data.getCawapres());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftarpresiden", null, values);
        return result != -1;
    }

    public boolean saveGubernur(ListgubernurItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("no_urut", data.getNoUrut());
        values.put("cagub", data.getCagub());
        values.put("cawagub", data.getCawagub());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftargubernur", null, values);
        return result != -1;
    }

    public boolean saveBupati(ListbupatiItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("no_urut", data.getNoUrut());
        values.put("cabup", data.getCabup());
        values.put("cawabup", data.getCawabup());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftarbupati", null, values);
        return result != -1;
    }

    public boolean saveKades(ListkadesItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("no_urut", data.getNoUrut());
        values.put("cakades", data.getCakades());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftarkades", null, values);
        return result != -1;
    }

    public boolean saveDpdRi(ListdpdriItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("nomorurut", data.getNomorurut());
        values.put("nama", data.getNama());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftardpdri", null, values);
        return result != -1;
    }

    public boolean saveDprRi(ListdprriItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("id_partai", data.getIdPartai());
        values.put("nomorurut", data.getNomorurut());
        values.put("nama", data.getNama());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftardprri", null, values);
        return result != -1;
    }

    public boolean saveDprdProv(ListdprdprovItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("id_partai", data.getIdPartai());
        values.put("nomorurut", data.getNomorurut());
        values.put("nama", data.getNama());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftardprdprov", null, values);
        return result != -1;
    }

    public boolean saveDprdKab(ListdprdkabItem data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_calon", data.getIdCalon());
        values.put("id_partai", data.getIdPartai());
        values.put("nomorurut", data.getNomorurut());
        values.put("nama", data.getNama());
        values.put("periode", data.getPeriode());
        long result = db.insert("daftardprdkab", null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    public List<ListtpsItem> getTps(String periode) {
        SQLiteDatabase db = getReadableDatabase();
        List<ListtpsItem> data = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM tps WHERE periode = ?", new String[]{periode});
        if (cursor != null & cursor.moveToFirst()) {
            do {
                ListtpsItem item = new ListtpsItem();
                item.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
                item.setNomorTps(cursor.getString(cursor.getColumnIndex("nomor_tps")));
                data.add(item);
            } while (cursor.moveToNext());
            db.close();
        }
        return data;
    }

    @SuppressLint("Range")
    public ListtpsItem getRincianTpsById(String idtps) {
        SQLiteDatabase db = getReadableDatabase();
        ListtpsItem data = new ListtpsItem();
        Cursor cursor = db.rawQuery("SELECT * FROM tps WHERE id_tps = ?", new String[]{idtps});
        if (cursor != null && cursor.moveToFirst()) {
            data.setNomorTps(cursor.getString(cursor.getColumnIndex("nomor_tps")));
            cursor.close();
        }
        return data;
    }

    @SuppressLint("Range")
    public String cektugastps(String idtps, List<String> type){
        String status = "";
        List<String> liststatus = new ArrayList<>();
        
        liststatus.add(cekLapcektps(idtps));
        liststatus.add(cekLappamtps(idtps));
        liststatus.add(ceksuaradone(idtps));
        liststatus.add(cekFormC1(idtps, type));
        liststatus.add(cekLapwal(idtps));
        liststatus.add(cekLapserah(idtps));
        
        if(liststatus.contains("BELUM ADA")){
            status = "BELUM SELESAI";
        }else if(liststatus.contains("LOCAL")){
            status = "LOCAL";
        } else if (liststatus.contains("SERVER")) {
            status = "SELESAI";
        }
        return status;
    };

    @SuppressLint("Range")
    public String cekLapcektps(String idtps) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "";
        Cursor cursor = db.rawQuery("SELECT * FROM lapcektps WHERE id_tps = ?", new String[]{idtps});

        if (cursor != null && cursor.moveToFirst()) {
            boolean hasLocal = false;
            do {
                if (cursor.getInt(cursor.getColumnIndex("local")) == 1) {
                    hasLocal = true;
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();

            if (hasLocal) {
                status = "LOCAL";
            } else {
                status = "SERVER";
            }
        } else {
            status = "BELUM ADA";
        }

        return status;
    }

    @SuppressLint("Range")
    public String cekLappamtps(String idtps) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "";
        Cursor cursor = db.rawQuery("SELECT * FROM lappamtps WHERE id_tps = ?", new String[]{idtps});

        if (cursor != null && cursor.moveToFirst()) {
            boolean hasLocal = false;
            do {
                if (cursor.getInt(cursor.getColumnIndex("local")) == 1) {
                    hasLocal = true;
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (hasLocal) {
                status = "LOCAL";
            } else {
                status = "SERVER";
            }
        } else {
            status = "BELUM ADA";
        }

        return status;
    }
    
    @SuppressLint("Range")
    public String ceksuaradone(String idtps){
        String status = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM datasuarasah WHERE id_tps = ?", new String[]{idtps});
        if (cursor.moveToFirst()) {
            do {
                String suara = cursor.getString(cursor.getColumnIndex("suara"));
                if (suara == null || suara.isEmpty()) {
                    status = "BELUM ADA";
                    break;
                }else{
                    int local = cursor.getInt(cursor.getColumnIndex("local"));
                    if(local == 1){
                        status = "LOCAL";
                        break;
                    }else{
                        status = "SERVER";
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return status;
    }

    @SuppressLint("Range")
    public String ceksuarapertype(String idtps, String type){
        String status = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM datasuarasah WHERE id_tps = ? AND type = ?", new String[]{idtps, type.toLowerCase()});
        if (cursor.moveToFirst()) {
            do {
                String suara = cursor.getString(cursor.getColumnIndex("suara"));
                if (suara == null || suara.isEmpty()) {
                    status = "BELUM ADA";
                    break;
                }else{
                    int local = cursor.getInt(cursor.getColumnIndex("local"));
                    if(local == 1){
                        status = "LOCAL";
                        break;
                    }else{
                        status = "SERVER";
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return status;
    }

    @SuppressLint("Range")
    public String cekFormC1(String idtps, List<String> type) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "";

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < type.size(); i++) {
            placeholders.append("?");
            if (i < type.size() - 1) {
                placeholders.append(", ");
            }
        }

        String sql = "SELECT * FROM formc1 WHERE id_tps = ? AND type IN (" + placeholders.toString() + ")";
        Cursor cursor = db.rawQuery(sql, new String[]{idtps, String.join(",", type)});

        try {
            boolean hasLocal = false;

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    if (cursor.getInt(cursor.getColumnIndex("local")) == 1) {
                        hasLocal = true;
                        break;
                    }
                } while (cursor.moveToNext());
            }

            if (hasLocal) {
                status = "LOCAL";
            } else {
                status = "SERVER";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return status;
    }

    @SuppressLint("Range")
    public String cekFormC1Each(String idtps, String type) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "";

        Cursor cursor = db.rawQuery("SELECT * FROM formc1 WHERE id_tps = ? AND type = ?", new String[]{idtps, type});

        if (cursor != null && cursor.moveToFirst()) {
            boolean hasLocal = false;
            do {
                if (cursor.getInt(cursor.getColumnIndex("local")) == 1) {
                    hasLocal = true;
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (hasLocal) {
                status = "LOCAL";
            } else {
                status = "SERVER";
            }
        } else {
            status = "BELUM ADA";
        }

        return status;
    }

    @SuppressLint("Range")
    public String cekLapwal(String idtps) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "";
        Cursor cursor = db.rawQuery("SELECT * FROM lapwal WHERE id_tps = ?", new String[]{idtps});

        if (cursor != null && cursor.moveToFirst()) {
            boolean hasLocal = false;
            do {
                if (cursor.getInt(cursor.getColumnIndex("local")) == 1) {
                    hasLocal = true;
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (hasLocal) {
                status = "LOCAL";
            } else {
                status = "SERVER";
            }
        } else {
            status = "BELUM ADA";
        }

        return status;
    }

    @SuppressLint("Range")
    public String cekLapserah(String idtps) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "";
        Cursor cursor = db.rawQuery("SELECT * FROM lapserah WHERE id_tps = ?", new String[]{idtps});

        if (cursor != null && cursor.moveToFirst()) {
            boolean hasLocal = false;
            do {
                if (cursor.getInt(cursor.getColumnIndex("local")) == 1) {
                    hasLocal = true;
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            if (hasLocal) {
                status = "LOCAL";
            } else {
                status = "SERVER";
            }
        } else {
            status = "BELUM ADA";
        }

        return status;
    }

    public boolean saveLapCekTPS(String idtps, String fakta, String analisa, String prediksi, String rekomendasi, String latitude, String longitude, String foto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idtps);
        values.put("fakta", fakta);
        values.put("analisa", analisa);
        values.put("prediksi", prediksi);
        values.put("rekomendasi", rekomendasi);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("dokumentasi", foto);
        values.put("local", 1);
        long result = db.insert("lapcektps", null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    public MLapCekTPS getLapCekTPS(String idtps){
        MLapCekTPS data = new MLapCekTPS();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lapcektps WHERE id_tps = ?", new String[]{idtps});
        if (cursor != null && cursor.moveToFirst()) {
            data.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            data.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
            data.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
            data.setFakta(cursor.getString(cursor.getColumnIndex("fakta")));
            data.setAnalisa(cursor.getString(cursor.getColumnIndex("analisa")));
            data.setPrediksi(cursor.getString(cursor.getColumnIndex("prediksi")));
            data.setRekomendasi(cursor.getString(cursor.getColumnIndex("rekomendasi")));
            data.setDokumentasi(cursor.getString(cursor.getColumnIndex("dokumentasi")));
            data.setLocal(cursor.getInt(cursor.getColumnIndex("local")));
            cursor.close();
        }

        return data;
    }

    public boolean updStatusCekTPS(String idtps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ?";
        String[] whereArgs = {idtps};

        int rowsUpdated = db.update("lapcektps", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    public boolean saveLapPamTPS(String idtps, String fakta, String analisa, String prediksi, String rekomendasi, String dokumentasi){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idtps);
        values.put("fakta", fakta);
        values.put("analisa", analisa);
        values.put("prediksi", prediksi);
        values.put("rekomendasi", rekomendasi);
        values.put("dokumentasi", dokumentasi);
        values.put("local", 1);
        long result = db.insert("lappamtps", null, values);
        return result != -1;
    }

    @SuppressLint("RANGE")
    public MLapPamTPS getLapPAMTPS(String idtps) {
        MLapPamTPS data = new MLapPamTPS();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lappamtps WHERE id_tps = ?", new String[]{idtps});
        if (cursor != null && cursor.moveToFirst()) {
            data.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            data.setFakta(cursor.getString(cursor.getColumnIndex("fakta")));
            data.setAnalisa(cursor.getString(cursor.getColumnIndex("analisa")));
            data.setPrediksi(cursor.getString(cursor.getColumnIndex("prediksi")));
            data.setRekomendasi(cursor.getString(cursor.getColumnIndex("rekomendasi")));
            data.setDokumentasi(cursor.getString(cursor.getColumnIndex("dokumentasi")));
            data.setLocal(cursor.getInt(cursor.getColumnIndex("local")));
            cursor.close();
        }

        return data;
    }

    public boolean updStatusPamTPS(String idtps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ?";
        String[] whereArgs = {idtps};

        int rowsUpdated = db.update("lappamtps", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    public boolean saveLapWalTPS(String idtps, String fakta, String analisa, String prediksi, String rekomendasi, String dokumentasi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idtps);
        values.put("fakta", fakta);
        values.put("analisa", analisa);
        values.put("prediksi", prediksi);
        values.put("rekomendasi", rekomendasi);
        values.put("dokumentasi", dokumentasi);
        values.put("local", 1);
        long result = db.insert("lapwal", null, values);
        return result != -1;
    }

    @SuppressLint("RANGE")
    public MLapWalTPS getLapWalTPS(String idtps) {
        MLapWalTPS data = new MLapWalTPS();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lapwal WHERE id_tps = ?", new String[]{idtps});
        if (cursor != null && cursor.moveToFirst()) {
            data.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            data.setFakta(cursor.getString(cursor.getColumnIndex("fakta")));
            data.setAnalisa(cursor.getString(cursor.getColumnIndex("analisa")));
            data.setPrediksi(cursor.getString(cursor.getColumnIndex("prediksi")));
            data.setRekomendasi(cursor.getString(cursor.getColumnIndex("rekomendasi")));
            data.setDokumentasi(cursor.getString(cursor.getColumnIndex("dokumentasi")));
            data.setLocal(cursor.getInt(cursor.getColumnIndex("local")));
            cursor.close();
        }

        return data;
    }

    public boolean updStatusWalTPS(String idtps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ?";
        String[] whereArgs = {idtps};

        int rowsUpdated = db.update("lapwal", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    public boolean saveLapSerahTPS(String idtps, String fakta, String analisa, String prediksi, String rekomendasi, String dokumentasi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idtps);
        values.put("fakta", fakta);
        values.put("analisa", analisa);
        values.put("prediksi", prediksi);
        values.put("rekomendasi", rekomendasi);
        values.put("dokumentasi", dokumentasi);
        values.put("local", 1);
        long result = db.insert("lapserah", null, values);
        return result != -1;
    }

    @SuppressLint("RANGE")
    public MLapSerahTPS getLapSerahTPS(String idtps) {
        MLapSerahTPS data = new MLapSerahTPS();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lapserah WHERE id_tps = ?", new String[]{idtps});
        if (cursor != null && cursor.moveToFirst()) {
            data.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            data.setFakta(cursor.getString(cursor.getColumnIndex("fakta")));
            data.setAnalisa(cursor.getString(cursor.getColumnIndex("analisa")));
            data.setPrediksi(cursor.getString(cursor.getColumnIndex("prediksi")));
            data.setRekomendasi(cursor.getString(cursor.getColumnIndex("rekomendasi")));
            data.setDokumentasi(cursor.getString(cursor.getColumnIndex("dokumentasi")));
            data.setLocal(cursor.getInt(cursor.getColumnIndex("local")));
            cursor.close();
        }

        return data;
    }

    public boolean updStatusSerahTPS(String idtps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ?";
        String[] whereArgs = {idtps};

        int rowsUpdated = db.update("lapserah", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    @SuppressLint("RANGE")
    public MFormC1 getFormC1(String idtps, String type) {
        MFormC1 data = new MFormC1();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM formc1 WHERE id_tps = ? AND type = ?", new String[]{idtps, type});
        if (cursor != null && cursor.moveToFirst()) {
            data.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            data.setType(cursor.getString(cursor.getColumnIndex("type")));
            data.setDokumentasi(cursor.getString(cursor.getColumnIndex("dokumentasi")));
            data.setLocal(cursor.getInt(cursor.getColumnIndex("local")));
            cursor.close();
        }else{
            return null;
        }

        return data;
    }

    public boolean saveFormC1(String idtps, String type, Uri uri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_tps", idtps);
        values.put("type", type);
        values.put("dokumentasi", String.valueOf(uri));
        values.put("local", 1);
        long result = db.insert("formc1", null, values);
        return result != -1;
    }

    public boolean updStatusFormC1(String idtps, String type) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ? AND type = ?";
        String[] whereArgs = {idtps, type};

        int rowsUpdated = db.update("formc1", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }


    public List<ListpresidenItem> getListCapres(String idtps, String type) {
        List<ListpresidenItem> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daftarpresiden WHERE id_tps = ?")
        return data;
    }

    public List<ListgubernurItem> getListCagub(String idtps, String type) {
    }

    public List<ListbupatiItem> getListCabup(String idtps, String type) {
    }

    public List<ListkadesItem> getListCakades(String idtps, String type) {
    }
}
