package id.creatodidak.djaga_swara.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFigur;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFormC1;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapCekTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapPamTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapSerahTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapWalTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MPartai;
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
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.MDataSuaraReguler;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.MDptFinal;
import id.creatodidak.djaga_swara.R;

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

    String TBDPTFINAL = "CREATE TABLE IF NOT EXISTS dptfinal (id INTEGER PRIMARY KEY AUTOINCREMENT, id_tps TEXT, dptfinal TEXT, local INTEGER DEFAULT 1)";

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
        db.execSQL(TBDPTFINAL);
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS tps");
        db.execSQL("DROP TABLE IF EXISTS lapcektps");
        db.execSQL("DROP TABLE IF EXISTS lappamtps");
        db.execSQL("DROP TABLE IF EXISTS formc1");
        db.execSQL("DROP TABLE IF EXISTS lapwal");
        db.execSQL("DROP TABLE IF EXISTS lapserah");
        db.execSQL("DROP TABLE IF EXISTS datasuarasah");
        db.execSQL("DROP TABLE IF EXISTS datasuaratidaksah");
        db.execSQL("DROP TABLE IF EXISTS datasuarapartai");
        db.execSQL("DROP TABLE IF EXISTS daftarpresiden");
        db.execSQL("DROP TABLE IF EXISTS daftargubernur");
        db.execSQL("DROP TABLE IF EXISTS daftarbupati");
        db.execSQL("DROP TABLE IF EXISTS daftarkades");
        db.execSQL("DROP TABLE IF EXISTS daftardpdri");
        db.execSQL("DROP TABLE IF EXISTS daftardprri");
        db.execSQL("DROP TABLE IF EXISTS daftardprdprov");
        db.execSQL("DROP TABLE IF EXISTS daftardprdkab");
        db.execSQL("DROP TABLE IF EXISTS daftarpartainas");
        db.execSQL("DROP TABLE IF EXISTS daftarpartaiprov");
        db.execSQL("DROP TABLE IF EXISTS daftarpartaikab");
        db.execSQL("DROP TABLE IF EXISTS dptfinal");

        init();

        db.close();
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
    public List<ListtpsItem> getallTps() {
        SQLiteDatabase db = getReadableDatabase();
        List<ListtpsItem> data = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM tps", null);
        if (cursor != null & cursor.moveToFirst()) {
            do {
                ListtpsItem item = new ListtpsItem();
                item.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
                item.setPeriode(cursor.getString(cursor.getColumnIndex("periode")));
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
            data.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            data.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            data.setNomorTps(cursor.getString(cursor.getColumnIndex("nomor_tps")));
            data.setDptSementara(cursor.getString(cursor.getColumnIndex("dpt_sementara")));
            data.setDptFinal(cursor.getString(cursor.getColumnIndex("dpt_final")));
            cursor.close();
        }
        return data;
    }

    @SuppressLint("Range")
    public String cektugastps(String idtps, List<String> type) {
        String status = "";
        List<String> liststatus = new ArrayList<>();

        liststatus.add(cekLapcektps(idtps));
        liststatus.add(cekLappamtps(idtps));
        liststatus.add(ceksuaradone(idtps));
        liststatus.add(cekFormC1(idtps, type));
        liststatus.add(cekLapwal(idtps));
        liststatus.add(cekLapserah(idtps));

        if (liststatus.contains("BELUM ADA")) {
            status = "BELUM SELESAI";
        } else if (liststatus.contains("LOCAL")) {
            status = "LOCAL";
        } else if (liststatus.contains("SERVER")) {
            status = "SELESAI";
        }
        return status;
    }

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
    public String ceksuaradone(String idtps) {
        String status = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM datasuarasah WHERE id_tps = ?", new String[]{idtps});
        if (cursor.moveToFirst()) {
            do {
                String suara = cursor.getString(cursor.getColumnIndex("suara"));
                if (suara == null || suara.isEmpty()) {
                    status = "BELUM ADA";
                    break;
                } else {
                    int local = cursor.getInt(cursor.getColumnIndex("local"));
                    if (local == 1) {
                        status = "LOCAL";
                        break;
                    } else {
                        status = "SERVER";
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return status;
    }

    @SuppressLint("Range")
    public String ceksuarapertype(String idtps, String type) {
        String status = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM datasuarasah WHERE id_tps = ? AND type = ?", new String[]{idtps, type.toLowerCase().replace("-", "")});
        if (cursor.moveToFirst()) {
            do {
                String suara = cursor.getString(cursor.getColumnIndex("suara"));
                if (TextUtils.isEmpty(suara)) {
                    status = "BELUM ADA";
                    break;
                } else {
                    int local = cursor.getInt(cursor.getColumnIndex("local"));
                    if (local == 1) {
                        status = "LOCAL";
                        break;
                    } else {
                        status = "SERVER";
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.i("STATUS "+ type, status);
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

        String sql = "SELECT * FROM formc1 WHERE id_tps = ? AND type IN (" + placeholders + ")";
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
    public MLapCekTPS getLapCekTPS(String idtps) {
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

    public boolean updStatusCekTPS(String idtps) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ?";
        String[] whereArgs = {idtps};

        int rowsUpdated = db.update("lapcektps", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    public boolean saveLapPamTPS(String idtps, String fakta, String analisa, String prediksi, String rekomendasi, String dokumentasi) {
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

    public boolean updStatusPamTPS(String idtps) {
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

    public boolean updStatusWalTPS(String idtps) {
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

    public boolean updStatusSerahTPS(String idtps) {
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
        } else {
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

    @SuppressLint("Range")
    public List<ListpresidenItem> getListCapres() {
        List<ListpresidenItem> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM daftarpresiden", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ListpresidenItem item = new ListpresidenItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    item.setIdCalon(cursor.getString(cursor.getColumnIndex("id_calon")));
                    item.setNoUrut(cursor.getString(cursor.getColumnIndex("no_urut")));
                    item.setCapres(cursor.getString(cursor.getColumnIndex("capres")));
                    item.setCawapres(cursor.getString(cursor.getColumnIndex("cawapres")));
                    item.setPeriode(cursor.getString(cursor.getColumnIndex("periode")));

                    data.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return data;
    }


    @SuppressLint("Range")
    public List<ListgubernurItem> getListCagub() {
        List<ListgubernurItem> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daftargubernur", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListgubernurItem item = new ListgubernurItem();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                item.setIdCalon(cursor.getString(cursor.getColumnIndex("id_calon")));
                item.setNoUrut(cursor.getString(cursor.getColumnIndex("no_urut")));
                item.setCagub(cursor.getString(cursor.getColumnIndex("cagub")));
                item.setCawagub(cursor.getString(cursor.getColumnIndex("cawagub")));
                item.setPeriode(cursor.getString(cursor.getColumnIndex("periode")));

                data.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return data;
    }

    @SuppressLint("Range")
    public List<ListbupatiItem> getListCabup() {
        List<ListbupatiItem> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daftarbupati", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {

                ListbupatiItem item = new ListbupatiItem();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                item.setIdCalon(cursor.getString(cursor.getColumnIndex("id_calon")));
                item.setNoUrut(cursor.getString(cursor.getColumnIndex("no_urut")));
                item.setCabup(cursor.getString(cursor.getColumnIndex("cabup")));
                item.setCawabup(cursor.getString(cursor.getColumnIndex("cawabup")));
                item.setPeriode(cursor.getString(cursor.getColumnIndex("periode")));

                data.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return data;
    }

    @SuppressLint("Range")
    public List<ListkadesItem> getListCakades() {
        List<ListkadesItem> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daftarkades", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListkadesItem item = new ListkadesItem();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                item.setIdCalon(cursor.getString(cursor.getColumnIndex("id_calon")));
                item.setNoUrut(cursor.getString(cursor.getColumnIndex("no_urut")));
                item.setCakades(cursor.getString(cursor.getColumnIndex("cakades")));
                item.setPeriode(cursor.getString(cursor.getColumnIndex("periode")));

                data.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return data;
    }

    @SuppressLint("Range")
    public List<ListdpdriItem> getListDpdri() {
        List<ListdpdriItem> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daftardpdri", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListdpdriItem item = new ListdpdriItem();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                item.setIdCalon(cursor.getString(cursor.getColumnIndex("id_calon")));
                item.setNomorurut(cursor.getString(cursor.getColumnIndex("nomorurut")));
                item.setNama(cursor.getString(cursor.getColumnIndex("nama")));
                item.setPeriode(cursor.getString(cursor.getColumnIndex("periode")));

                data.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return data;
    }

    @SuppressLint("Range")
    public MDptFinal getDptFinal(String idtps) {
        MDptFinal data = new MDptFinal();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM dptfinal WHERE id_tps = ?", new String[]{idtps});
        if (cursor != null && cursor.moveToFirst()) {
            data.setIdTps(cursor.getString(cursor.getColumnIndex("id_tps")));
            data.setDptFinal(cursor.getString(cursor.getColumnIndex("dptfinal")));
            data.setLocal(cursor.getInt(cursor.getColumnIndex("local")));
        } else {
            data = null;
        }

        return data;
    }

    public boolean saveDptFinal(String idtps, String dptFinal) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_tps", idtps);
        contentValues.put("dptfinal", dptFinal);
        long result = db.insert("dptfinal", null, contentValues);
        return result != -1;
    }

    public boolean updDptFinal(String idtps) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ?";
        String[] whereArgs = {idtps};

        int rowsUpdated = db.update("dptfinal", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    public List<MDataSuaraReguler> getCalonReg(String type, String idtps) {
        List<MDataSuaraReguler> lists = new ArrayList<>();
        if (type.equals("presiden")) {
            for (ListpresidenItem i : getListCapres()) {
                ListsuaraItem suara = getSuaraPerCalon(type, idtps, i.getIdCalon());
                MDataSuaraReguler d = new MDataSuaraReguler();
                d.setIdcalon(i.getIdCalon());
                d.setCalon1(i.getCapres());
                d.setCalon2(i.getCawapres());
                d.setNoUrut(i.getNoUrut());
                d.setjSuara(suara.getSuara());
                d.setLocal(suara.getLocal());
                lists.add(d);
            }
        } else if (type.equals("gubernur")) {
            for (ListgubernurItem i : getListCagub()) {
                ListsuaraItem suara = getSuaraPerCalon(type, idtps, i.getIdCalon());
                MDataSuaraReguler d = new MDataSuaraReguler();
                d.setIdcalon(i.getIdCalon());
                d.setCalon1(i.getCagub());
                d.setCalon2(i.getCawagub());
                d.setNoUrut(i.getNoUrut());
                d.setjSuara(suara.getSuara());
                d.setLocal(suara.getLocal());
                lists.add(d);
            }
        } else if (type.equals("bupati")) {
            for (ListbupatiItem i : getListCabup()) {
                ListsuaraItem suara = getSuaraPerCalon(type, idtps, i.getIdCalon());
                MDataSuaraReguler d = new MDataSuaraReguler();
                d.setIdcalon(i.getIdCalon());
                d.setCalon1(i.getCabup());
                d.setCalon2(i.getCawabup());
                d.setNoUrut(i.getNoUrut());
                d.setjSuara(suara.getSuara());
                d.setLocal(suara.getLocal());
                lists.add(d);
            }
        } else if (type.equals("kades")) {
            for (ListkadesItem i : getListCakades()) {
                ListsuaraItem suara = getSuaraPerCalon(type, idtps, i.getIdCalon());
                MDataSuaraReguler d = new MDataSuaraReguler();
                d.setIdcalon(i.getIdCalon());
                d.setCalon1(i.getCakades());
                d.setjSuara(suara.getSuara());
                d.setNoUrut(i.getNoUrut());
                d.setLocal(suara.getLocal());
                lists.add(d);
            }
        } else if (type.equals("dpdri")) {
            for (ListdpdriItem i : getListDpdri()) {
                ListsuaraItem suara = getSuaraPerCalon(type, idtps, i.getIdCalon());
                MDataSuaraReguler d = new MDataSuaraReguler();
                d.setIdcalon(i.getIdCalon());
                d.setCalon1(i.getNama());
                d.setjSuara(suara.getSuara());
                d.setNoUrut(i.getNomorurut());
                d.setLocal(suara.getLocal());
                lists.add(d);
            }
        }

        return lists;
    }

    @SuppressLint("Range")
    public ListsuaraItem getSuaraPerCalon(String type, String idtps, String idCalon) {
        SQLiteDatabase db = getReadableDatabase();
        ListsuaraItem item = new ListsuaraItem();
        Cursor cursor = db.rawQuery("SELECT * FROM datasuarasah WHERE id_tps = ? AND type = ? AND id_calon = ?", new String[]{idtps, type, idCalon});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                item.setLocal(cursor.getInt(cursor.getColumnIndex("local")));
                item.setSuara(cursor.getString(cursor.getColumnIndex("suara")));
                item.setIdCalon(cursor.getString(cursor.getColumnIndex("id_calon")));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return item;
    }

    public void saveSuara(String idtps, String idcalon, String suara) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("suara", suara);
        String whereClause = "id_tps = ? AND id_calon = ?";
        String[] whereArgs = {idtps, idcalon};

        db.update("datasuarasah", values, whereClause, whereArgs);
    }

    public void saveSuaraTsah(String idtps, String type, String total) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM datasuaratidaksah WHERE id_tps = ? AND type = ?", new String[]{idtps, type});

        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("total", total);
            values.put("id_tps", idtps);
            values.put("type", type);
            db.insert("datasuaratidaksah", null, values);
        }

        cursor.close();
    }


    @SuppressLint("Range")
    public String getSuaraTidakSah(String idtps, String type) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM datasuaratidaksah WHERE id_tps = ? AND type = ?", new String[]{idtps, type});
        String data = "";
        if (cursor != null && cursor.moveToFirst()) {
            data = cursor.getString(cursor.getColumnIndex("total"));
        }

        return data;
    }

    public void updateStatusSuara(String kategori, String type, String idTps, String idCalon) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String table = "";
        String whereClause = "";
        String[] whereArgs = {};

        if (kategori.equals("SUARA SAH")) {
            table = "datasuarasah";
            whereClause = "id_tps = ? AND type = ? AND id_calon = ?";
            whereArgs = new String[]{idTps, type, idCalon};
        } else if (kategori.equals("SUARA TIDAK SAH")) {
            table = "datasuaratidaksah";
            whereClause = "id_tps = ? AND type = ?";
            whereArgs = new String[]{idTps, type};
        } else if (kategori.equals("SUARA PARTAI")) {
            table = "datasuarapartai";
            whereClause = "id_tps = ? AND type = ? AND id_partai = ?";
            whereArgs = new String[]{idTps, type, idCalon};
        }

        // Mengonversi whereArgs menjadi string untuk ditampilkan di log
        StringBuilder whereArgsString = new StringBuilder();
        for (String arg : whereArgs) {
            whereArgsString.append(arg).append(", ");
        }
        // Hapus koma dan spasi terakhir
        if (whereArgsString.length() >= 2) {
            whereArgsString.setLength(whereArgsString.length() - 2);
        }

        String sql = "UPDATE " + table + " SET local=0 WHERE " + whereClause;
        db.execSQL(sql, whereArgs);

        Log.d("UPDATE " + kategori, "SQL: " + sql);
        Log.d("UPDATE " + kategori, "WHERE ARGS: " + whereArgsString);
    }


    @SuppressLint("Range")
    public List<MPartai> getPartai(String type, String IDTPS) {
        SQLiteDatabase db = getReadableDatabase();

        List<MPartai> data = new ArrayList<>();
        Cursor cursor = null;

        if (type.equals("DPRRI")) {
            cursor = db.rawQuery("SELECT * FROM daftarpartainas", null);

        } else if (type.equals("DPRDPROV")) {
            cursor = db.rawQuery("SELECT * FROM daftarpartaiprov", null);
        } else if (type.equals("DPRDKAB")) {
            cursor = db.rawQuery("SELECT * FROM daftarpartaikab", null);
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                MPartai partai = new MPartai();
                partai.setIdPartai(cursor.getString(cursor.getColumnIndex("id_partai")));
                partai.setNomorurut(cursor.getString(cursor.getColumnIndex("nomorurut")));
                partai.setNama(cursor.getString(cursor.getColumnIndex("nama")));
                partai.setLogo(getLogoPartai(cursor.getString(cursor.getColumnIndex("nama"))));
                partai.setSuara(getSuaraPartai(type, cursor.getString(cursor.getColumnIndex("id_partai")), IDTPS));
                data.add(partai);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();

        return data;
    }

    private String getSuaraPartai(String type, String idPartai, String IDTPS) {
        SQLiteDatabase db = getReadableDatabase();
        String lv = "";
        if (type.equals("DPRRI")) {
            lv = "nasional";
        } else if (type.equals("DPRDPROV")) {
            lv = "provinsi";
        } else if (type.equals("DPRDKAB")) {
            lv = "kabupaten";
        }

        Cursor cursor = db.rawQuery("SELECT * FROM datasuarapartai WHERE id_partai = ? AND type = ? AND id_tps = ?", new String[]{idPartai, lv, IDTPS});

        if (cursor.moveToFirst()) {
            // Pindahkan kursor ke baris pertama
            @SuppressLint("Range") String suara = cursor.getString(cursor.getColumnIndex("suara"));
            cursor.close(); // Jangan lupa untuk menutup kursor setelah penggunaan
            if (TextUtils.isEmpty(suara)) {
                return "";
            } else {
                return suara;
            }
        } else {
            // Tidak ada baris yang cocok, kembalikan nilai default
            cursor.close(); // Tutup kursor jika tidak ada baris yang cocok
            return "";
        }
    }


    private int getLogoPartai(String nama) {
        if (nama.equals("PKB")) {
            return R.drawable.pkb;
        } else if (nama.equals("GERINDRA")) {
            return R.drawable.gerindra;
        } else if (nama.equals("PDI-P")) {
            return R.drawable.pdip;
        } else if (nama.equals("GOLKAR")) {
            return R.drawable.golkar;
        } else if (nama.equals("NASDEM")) {
            return R.drawable.nasdem;
        } else if (nama.equals("PARTAI BURUH")) {
            return R.drawable.partaiburuh;
        } else if (nama.equals("GELORA INDONESIA")) {
            return R.drawable.geloraindonesia;
        } else if (nama.equals("PKS")) {
            return R.drawable.pks;
        } else if (nama.equals("PKN")) {
            return R.drawable.pkn;
        } else if (nama.equals("HANURA")) {
            return R.drawable.hanura;
        } else if (nama.equals("GARUDA")) {
            return R.drawable.garuda;
        } else if (nama.equals("PAN")) {
            return R.drawable.pan;
        } else if (nama.equals("PBB")) {
            return R.drawable.pbb;
        } else if (nama.equals("DEMOKRAT")) {
            return R.drawable.demokrat;
        } else if (nama.equals("PSI")) {
            return R.drawable.psi;
        } else if (nama.equals("PERINDO")) {
            return R.drawable.perindo;
        } else if (nama.equals("PPP")) {
            return R.drawable.ppp;
        } else if (nama.equals("PARTAI UMMAT")) {
            return R.drawable.partaiummat;
        }else{
            return 0;
        }
    }

    @SuppressLint("range")
    public List<MFigur> getFigurList(String type, String idPartai, String IDTPS) {
        SQLiteDatabase db = getReadableDatabase();

        List<MFigur> data = new ArrayList<>();
        Cursor cursor = null;

        if (type.equals("DPRRI")) {
            cursor = db.rawQuery("SELECT * FROM daftardprri WHERE id_partai = ?", new String[]{idPartai});

        } else if (type.equals("DPRDPROV")) {
            cursor = db.rawQuery("SELECT * FROM daftardprdprov WHERE id_partai = ?", new String[]{idPartai});
        } else if (type.equals("DPRDKAB")) {
            cursor = db.rawQuery("SELECT * FROM daftardprdkab WHERE id_partai = ?", new String[]{idPartai});
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                MFigur figur = new MFigur();
                figur.setIdCalon(cursor.getString(cursor.getColumnIndex("id_calon")));
                figur.setIdPartai(cursor.getString(cursor.getColumnIndex("id_partai")));
                figur.setNomorurut(cursor.getString(cursor.getColumnIndex("nomorurut")));
                figur.setNama(cursor.getString(cursor.getColumnIndex("nama")));
                figur.setSuara(getSuaraPerCalon(type.toLowerCase(), IDTPS, cursor.getString(cursor.getColumnIndex("id_calon"))).getSuara());
                data.add(figur);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();

        return data;
    }

    public void saveSuaraPartai(String idtps, String type, String idPartai, String suara) {
        String types = "";

        if (type.equals("DPRRI")) {
            types = "nasional";
        } else if (type.equals("DPRDPROV")) {
            types = "provinsi";
        } else if (type.equals("DPRDKAB")) {
            types = "kabupaten";
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("suara", suara);

        String whereClause = "id_tps = ? AND type = ? AND id_partai = ?";
        String[] whereArgs = {idtps, types, idPartai};
        db.update("datasuarapartai", values, whereClause, whereArgs);
    }

    @SuppressLint("range")
    public String statusLocalLegislatif(String idtps, String type) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "";
        Cursor cursor = db.rawQuery("SELECT * FROM datasuarasah WHERE id_tps = ? AND type = ?", new String[]{idtps, type});

        if (cursor != null && cursor.moveToFirst()) {
            boolean hasLocal = false;
            boolean isEmpty = false;
            do {
                if (TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex("suara")))) {
                    isEmpty = true;
                    break;
                }else {
                    if (cursor.getInt(cursor.getColumnIndex("local")) == 1) {
                        hasLocal = true;
                        break;
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();

            if(isEmpty){
                status = "BELUM ADA";
            }else {
                if (hasLocal) {
                    status = "LOCAL";
                } else {
                    status = "SERVER";
                }
            }
        } else {
            status = "BELUM ADA";
        }

        return status;
    }

    public void updSuaraPartai(String idPartai, String type, String idtps) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ? AND id_partai = ? AND type = ?";
        String[] whereArgs = {idtps, idPartai, type};

        db.update("datasuarapartai", values, whereClause, whereArgs);
    }

    public void updSuaraLegislatif(String type, String idtps, String idCalon) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("local", 0);

        String whereClause = "id_tps = ? AND id_calon = ? AND type = ?";
        String[] whereArgs = {idtps, idCalon, type};

        db.update("datasuarasah", values, whereClause, whereArgs);
    }
}
