package id.creatodidak.djaga_swara.DASHBOARDNEW;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.NEWADAPTER.TpsSelectAdapter;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.DesaItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.KabItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.KecItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MDesa;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MKabupaten;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MKecamatan;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MTps;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.TpsavailableItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListtpsItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.RespSelectTps;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.AppDetail;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.NonScrollableLayoutManager;
import id.creatodidak.djaga_swara.plugin.SessionData;
import id.creatodidak.djaga_swara.plugin.TextHelper;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDashboard extends AppCompatActivity {
    private static final int RC_ALL_PERMISSIONS = 123;
    @SuppressLint("InlinedApi")
    private final String[] allPermissions = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.USE_FINGERPRINT,
            Manifest.permission.USE_BIOMETRIC,
            Manifest.permission.VIBRATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.WAKE_LOCK,
            "com.google.android.c2dm.permission.RECEIVE",
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
    };
    ImageView menu1, menu2, menu3, btnBatal;
    TextView build, pbText;
    ArrayList<String> item = new ArrayList<>();
    List<String> listkab = new ArrayList<>();
    List<String> listkec = new ArrayList<>();
    List<String> listDes = new ArrayList<>();
    List<KabItem> kab = new ArrayList<>();
    List<KecItem> kec = new ArrayList<>();
    List<DesaItem> des = new ArrayList<>();
    List<TpsavailableItem> tps = new ArrayList<>();
    List<String> selectedtps = new ArrayList<>();
    List<String> tugas = new ArrayList<>();
    Spinner spPeriode;
    LinearLayout wKabPenugasan;
    LinearLayout wKecPenugasan;
    LinearLayout wDesPenugasan;
    LinearLayout wTps;
    Spinner spKabupatenPenugasan;
    Spinner spKecamatanPenugasan;
    Spinner spDesaPenugasan;
    Button btnDownloadData;
    CardView cvSetTugas, cvSaveData;
    ProgressBar pbSaveData;
    ArrayAdapter<String> adapterDesa, adapterKecamatan, adapterKabupaten;
    Endpoint endpoint;
    TpsSelectAdapter adapter;
    RecyclerView rvTps;
    LinearLayoutManager lm;
    String cPeriode = "";
    String cProv = "61";
    String cKab = "";
    String cKec = "";
    String cDes = "";
    AlertDialog alertx;
    DBHelper dbHelper;
    SharedPreferences sh;
    int total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dashboard);
        if (EasyPermissions.hasPermissions(this, allPermissions)) {
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Seluruh izin dibutuhkan!",
                    RC_ALL_PERMISSIONS,
                    allPermissions
            );
        }

        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        dbHelper = new DBHelper(this);
        dbHelper.init();
        endpoint = ApiClient.getClient().create(Endpoint.class);
        build = findViewById(R.id.build);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        btnBatal = findViewById(R.id.btBatal);
        build.setText(AppDetail.getVersionName(this));
        wKabPenugasan = findViewById(R.id.wKabPenugasan);
        wKecPenugasan = findViewById(R.id.wKecPenugasan);
        wDesPenugasan = findViewById(R.id.wDesPenugasan);
        wTps = findViewById(R.id.wTps);
        spPeriode = findViewById(R.id.spPeriode);
        spKabupatenPenugasan = findViewById(R.id.spKabupatenPenugasan);
        spKecamatanPenugasan = findViewById(R.id.spKecamatanPenugasan);
        spDesaPenugasan = findViewById(R.id.spDesaPenugasan);
        btnDownloadData = findViewById(R.id.btnDownloadData);
        rvTps = findViewById(R.id.rvTps);
        cvSetTugas = findViewById(R.id.cvSetTugas);
        cvSaveData = findViewById(R.id.cvSaveData);
        pbSaveData = findViewById(R.id.pbSaveData);
        pbText = findViewById(R.id.pbText);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvSetTugas.setVisibility(View.VISIBLE);
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CDialog.up(NewDashboard.this,
                        "Konfirmasi",
                        "Batal Memilih Tugas?",
                        true, false, false,
                        "TIDAK",
                        "YA",
                        "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                cvSetTugas.setVisibility(View.GONE);
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });
        lm = new NonScrollableLayoutManager(this);
        wKabPenugasan.setVisibility(View.GONE);
        wKecPenugasan.setVisibility(View.GONE);
        wDesPenugasan.setVisibility(View.GONE);
        wTps.setVisibility(View.GONE);
        btnDownloadData.setVisibility(View.GONE);
        item.add("PILIH PERIODE");
        item.add("SIMULASI");
        item.add("PEMILU 2024 PERIODE I");
        item.add("PEMILU 2024 PERIODE II");

        ArrayAdapter<String> adapterPeriode = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, item);
        adapterPeriode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriode.setAdapter(adapterPeriode);

        adapterKabupaten = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listkab);
        adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKabupatenPenugasan.setAdapter(adapterKabupaten);

        adapterKecamatan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listkec);
        adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKecamatanPenugasan.setAdapter(adapterKecamatan);

        adapterDesa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDes);
        adapterDesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDesaPenugasan.setAdapter(adapterDesa);

        spPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spPeriode.getSelectedItem().toString().equals("PILIH PERIODE")) {
                    if (spPeriode.getSelectedItem().toString().equals("SIMULASI")) {
                        cPeriode = "0";
                    } else if (spPeriode.getSelectedItem().toString().equals("PEMILU 2024 PERIODE I")) {
                        cPeriode = "1";
                    } else if (spPeriode.getSelectedItem().toString().equals("PEMILU 2024 PERIODE II")) {
                        cPeriode = "2";
                    }

                    if(sh.getBoolean("DATA"+cPeriode, false)){
                        cvSaveData.setVisibility(View.GONE);
                        cvSetTugas.setVisibility(View.GONE);
                        Intent intent = new Intent(NewDashboard.this, NewPemiluDashboard.class);
                        intent.putExtra("PERIODE", cPeriode);
                        startActivity(intent);
                        finish();
                    }else{
                        loadKab("61");
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spKabupatenPenugasan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spKabupatenPenugasan.getSelectedItem().toString().equals("PILIH KABUPATEN")) {
                    for (KabItem selected : kab) {
                        if (selected.getNamaKab().equals(spKabupatenPenugasan.getSelectedItem().toString())) {
                            cKab = String.valueOf(selected.getIdKab());
                            loadKec(String.valueOf(selected.getIdKab()));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spKecamatanPenugasan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spKecamatanPenugasan.getSelectedItem().toString().equals("PILIH KECAMATAN")) {
                    for (KecItem selected : kec) {
                        if (selected.getNamaKec().equals(spKecamatanPenugasan.getSelectedItem().toString())) {
                            cKec = String.valueOf(selected.getIdKec());
                            loadDes(String.valueOf(selected.getIdKec()));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDesaPenugasan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spDesaPenugasan.getSelectedItem().toString().equals("PILIH DESA")) {
                    for (DesaItem selected : des) {
                        if (selected.getNamaDes().equals(spDesaPenugasan.getSelectedItem().toString())) {
                            cDes = String.valueOf(selected.getIdDes());
                            loadTps(cPeriode, cProv, cKab, cKec, String.valueOf(selected.getIdDes()));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new TpsSelectAdapter(this, tps, new TpsSelectAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckItem(String idtps) {
                selectedtps.add(idtps);
                btnDownloadData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUncheckItem(String idtps) {
                for (int i = 0; i < selectedtps.size(); i++) {
                    if (selectedtps.get(i).equals(idtps)) {
                        selectedtps.remove(i);
                    }
                }

                if (selectedtps.size() != 0) {
                    btnDownloadData.setVisibility(View.GONE);
                }
            }
        });

        rvTps.setLayoutManager(lm);
        rvTps.setAdapter(adapter);

        alertx = CDialog.up(
                this,
                "Memproses data...",
                "",
                false, false, true, "", "", "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {

                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );

        btnDownloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertx.show();
                btnDownloadData.setVisibility(View.GONE);
                updateTps();
            }
        });

    }

    private void updateTps() {
        String nrp = SessionData.getuserdata(this).getNrp();
        String idtps = "";
        for (String s : selectedtps) {
            idtps = idtps + "," + s;
        }

        Call<RespSelectTps> call = endpoint.pilihTps(nrp, idtps, cPeriode, cProv, cKab, cKec, cDes);
        call.enqueue(new Callback<RespSelectTps>() {
            @Override
            public void onResponse(Call<RespSelectTps> call, Response<RespSelectTps> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isBerhasil()) {
                    RespSelectTps data = response.body();
                    int ttps = data.getListtps().size();
                    int tsuara = data.getListsuara().size();
                    int tsuarapartai = data.getListsuarapartai().size();
                    int tpartainas = data.getListpartainas().size();
                    int tpartaiprov = data.getListpartaiprov().size();
                    int tpartaikab = data.getListpartaikab().size();
                    int tpresiden = data.getListpresiden().size();
                    int tgubernur = data.getListgubernur().size();
                    int tbupati = data.getListbupati().size();
                    int tkades = data.getListkades().size();
                    int tdpdri = data.getListdpdri().size();
                    int tdprri = data.getListdprri().size();
                    int tdprdprov = data.getListdprdprov().size();
                    int tdprdkab = data.getListdprdkab().size();

                    total = ttps + tsuara + tsuarapartai + tpresiden + tgubernur + tbupati + tkades + tdpdri + tdprri + tdprdprov + tdprdkab + tpartainas + tpartaiprov + tpartaikab;
                    int totaldone = 0;

                    for(int i = 0; i < ttps; i++){
                        if(dbHelper.saveTps(data.getListtps().get(i))){
                            totaldone++;
                        }
                    }
                    for(int i = 0; i < tsuara; i++){
                        if(dbHelper.createSuara(data.getListsuara().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tsuarapartai; i++){
                        if(dbHelper.createSuaraPartai(data.getListsuarapartai().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tpartainas; i++){
                        if(dbHelper.savePartaiNas(data.getListpartainas().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tpartaiprov; i++){
                        if(dbHelper.savePartaiProv(data.getListpartaiprov().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tpartaikab; i++){
                        if(dbHelper.savePartaiKab(data.getListpartaikab().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tpresiden; i++){
                        if(dbHelper.savePresiden(data.getListpresiden().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tgubernur; i++){
                        if(dbHelper.saveGubernur(data.getListgubernur().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tbupati; i++){
                        if(dbHelper.saveBupati(data.getListbupati().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tkades; i++){
                        if(dbHelper.saveKades(data.getListkades().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tdpdri; i++){
                        if(dbHelper.saveDpdRi(data.getListdpdri().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tdprri; i++){
                        if(dbHelper.saveDprRi(data.getListdprri().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tdprdprov; i++){
                        if(dbHelper.saveDprdProv(data.getListdprdprov().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }
                    for(int i = 0; i < tdprdkab; i++){
                        if(dbHelper.saveDprdKab(data.getListdprdkab().get(i))){
                            totaldone++;
                            setPbProgress(totaldone);
                        }
                    }

                    if(totaldone == total){
                        SharedPreferences.Editor ed = sh.edit();
                        ed.putBoolean("DATA"+cPeriode, true);
                        ed.putString("KABUPATEN TPS", spKabupatenPenugasan.getSelectedItem().toString());
                        ed.putString("KECAMATAN TPS", spKecamatanPenugasan.getSelectedItem().toString());
                        ed.putString("DESA TPS", spDesaPenugasan.getSelectedItem().toString());
                        ed.putBoolean("PRESIDEN", tpresiden != 0);
                        ed.putBoolean("GUBERNUR", tgubernur != 0);
                        ed.putBoolean("BUPATI", tbupati != 0);
                        ed.putBoolean("KADES", tkades != 0);
                        ed.putBoolean("DPD-RI", tdpdri != 0);
                        ed.putBoolean("DPR-RI", tdprri != 0);
                        ed.putBoolean("DPRD-PROV", tdprdprov != 0);
                        ed.putBoolean("DPRD-KAB", tdprdkab != 0);
                        ed.apply();
                        alertx.dismiss();
                        cvSetTugas.setVisibility(View.GONE);
                        Intent intent = new Intent(NewDashboard.this, NewPemiluDashboard.class);
                        intent.putExtra("PERIODE", cPeriode);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    alertx.dismiss();
                    String[] gagal = response.body().getPesan().split(",");
                    String msg = "";
                    for (String item : gagal) {
                        msg = TextHelper.capitalize(item) + "\n\n";
                    }

                    CDialog.up(
                            NewDashboard.this,
                            "Informasi",
                            msg,
                            false, false, false, "", "ULANGI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    cvSetTugas.setVisibility(View.GONE);
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {

                                }
                            }
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<RespSelectTps> call, Throwable t) {
                alertx.dismiss();
                CDialog.up(
                        NewDashboard.this,
                        "Informasi",
                        "Periksa jaringan internet anda!",
                        false, false, false, "", "ULANGI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                cvSetTugas.setVisibility(View.GONE);
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {

                            }
                        }
                ).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadTps(String periode, String idprov, String idkab, String idkec, String idDes) {

        tps.clear();
        selectedtps.clear();
        adapter.notifyDataSetChanged();
        Call<MTps> call = endpoint.getTps(periode, idprov, idkab, idkec, idDes);
        AlertDialog alerts = CDialog.up(
                this,
                "Memuat data...",
                "",
                false, false, true,
                "",
                "",
                "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {

                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );
        alerts.show();

        call.enqueue(new Callback<MTps>() {
            @Override
            public void onResponse(Call<MTps> call, Response<MTps> response) {
                alerts.dismiss();
                if (response.body() != null && response.isSuccessful() && response.body().isIsexist()) {
                    tps.addAll(response.body().getTpsavailable());
                    adapter.notifyDataSetChanged();
                    wTps.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MTps> call, Throwable t) {

            }
        });
    }

    private void loadKab(String id) {
        listkab.clear();
        kab.clear();
        adapterKabupaten.notifyDataSetChanged();
        wKecPenugasan.setVisibility(View.GONE);
        wDesPenugasan.setVisibility(View.GONE);
        wTps.setVisibility(View.GONE);
        AlertDialog alerts = CDialog.up(
                this,
                "Memuat data...",
                "",
                false, false, true,
                "",
                "",
                "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {

                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );
        alerts.show();
        Call<MKabupaten> call = endpoint.getKab(id);
        call.enqueue(new Callback<MKabupaten>() {
            @Override
            public void onResponse(Call<MKabupaten> call, Response<MKabupaten> response) {
                alerts.dismiss();
                if (response.body() != null && response.isSuccessful()) {
                    kab.addAll(response.body().getKab());
                    listkab.add("PILIH KABUPATEN");
                    for (KabItem item : response.body().getKab()) {
                        listkab.add(item.getNamaKab());
                    }
                    adapterKabupaten.notifyDataSetChanged();
                    wKabPenugasan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MKabupaten> call, Throwable t) {
                CDialog.up(NewDashboard.this,
                        "Informasi",
                        "Sepertinya ada sistem failure, silahkan restart activity!",
                        false, false, false,
                        "",
                        "RESTART",
                        "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                NewDashboard.this.finish();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });
    }

    private void loadKec(String id) {
        listkec.clear();
        kec.clear();
        adapterKecamatan.notifyDataSetChanged();
        wDesPenugasan.setVisibility(View.GONE);
        wTps.setVisibility(View.GONE);
        AlertDialog alerts = CDialog.up(
                this,
                "Memuat data...",
                "",
                false, false, true,
                "",
                "",
                "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {

                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );
        alerts.show();
        Call<MKecamatan> call = endpoint.getKec(id);
        call.enqueue(new Callback<MKecamatan>() {
            @Override
            public void onResponse(Call<MKecamatan> call, Response<MKecamatan> response) {
                alerts.dismiss();
                if (response.body() != null && response.isSuccessful()) {
                    listkec.add("PILIH KECAMATAN");
                    kec.addAll(response.body().getKec());
                    for (KecItem item : response.body().getKec()) {
                        listkec.add(item.getNamaKec());
                    }
                    adapterKecamatan.notifyDataSetChanged();
                    wKecPenugasan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MKecamatan> call, Throwable t) {
                CDialog.up(NewDashboard.this,
                        "Informasi",
                        "Sepertinya ada sistem failure, silahkan restart activity!",
                        false, false, false,
                        "",
                        "RESTART",
                        "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                NewDashboard.this.finish();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });
    }

    private void loadDes(String id) {
        listDes.clear();
        des.clear();
        wTps.setVisibility(View.GONE);
        adapterDesa.notifyDataSetChanged();
        AlertDialog alerts = CDialog.up(
                this,
                "Memuat data...",
                "",
                false, false, true,
                "",
                "",
                "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {

                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );
        alerts.show();
        Call<MDesa> call = endpoint.getDes(id);
        call.enqueue(new Callback<MDesa>() {
            @Override
            public void onResponse(Call<MDesa> call, Response<MDesa> response) {
                alerts.dismiss();
                if (response.body() != null && response.isSuccessful()) {
                    des.addAll(response.body().getDesa());
                    listDes.add("PILIH DESA");
                    for (DesaItem item : response.body().getDesa()) {
                        listDes.add(item.getNamaDes());
                    }
                    adapterDesa.notifyDataSetChanged();
                    wDesPenugasan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MDesa> call, Throwable t) {
                CDialog.up(NewDashboard.this,
                        "Informasi",
                        "Sepertinya ada sistem failure, silahkan restart activity!",
                        false, false, false,
                        "",
                        "RESTART",
                        "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                NewDashboard.this.finish();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void setPbProgress(int totals) {
        if (total <= 0) {
            pbSaveData.setProgress(0);
            pbText.setText("0 / 0");
        } else {
            int progress = (totals * 100) / total;
            pbSaveData.setProgress(progress);
            pbText.setText(totals + " / " + total);
        }
    }

    @Override
    public void onBackPressed() {
        if (cvSetTugas.getVisibility() == View.VISIBLE) {
            CDialog.up(this,
                    "Konfirmasi",
                    "Batal Memilih Tugas?",
                    true, false, false,
                    "TIDAK",
                    "YA",
                    "",
                    new CDialog.AlertDialogListener() {
                        @Override
                        public void onOpt1(AlertDialog alert) {
                            alert.dismiss();
                            cvSetTugas.setVisibility(View.GONE);
                        }

                        @Override
                        public void onOpt2(AlertDialog alert) {

                        }

                        @Override
                        public void onCancel(AlertDialog alert) {
                            alert.dismiss();
                        }
                    }
            ).show();
        } else {
            CDialog.up(this,
                    "Konfirmasi",
                    "Keluar dari aplikasi?",
                    true, false, false,
                    "TIDAK",
                    "YA",
                    "",
                    new CDialog.AlertDialogListener() {
                        @Override
                        public void onOpt1(AlertDialog alert) {
                            finishAffinity();
                        }

                        @Override
                        public void onOpt2(AlertDialog alert) {

                        }

                        @Override
                        public void onCancel(AlertDialog alert) {
                            alert.dismiss();
                        }
                    }
            ).show();
        }
    }

    public void resetdata(View view){
        CDialog.up(this,
                "Konfirmasi",
                "Anda yakin untuk mereset data?",
                true, false, false,
                "TIDAK",
                "YA",
                "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {
                        alert.dismiss();
                        doReset();
                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {
                        alert.dismiss();
                    }
                }
        ).show();
    }

    private void doReset() {
        AlertDialog alerts = CDialog.up(
                this,
                "Mereset data...",
                "",
                false, false, true,
                "",
                "",
                "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {

                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );
        alerts.show();

        String tps = "";
        for(ListtpsItem i : dbHelper.getallTps()){
            if(tps.isEmpty()){
                tps = i.getIdTps();
            }else{
                tps = tps+","+i.getIdTps();
            }
        }

        Call<MResponseServer> call = endpoint.resetData(tps);
        call.enqueue(new Callback<MResponseServer>() {
            @Override
            public void onResponse(Call<MResponseServer> call, Response<MResponseServer> response) {
                alerts.dismiss();
                if(response.isSuccessful() && response.body() != null && response.body().isStatus()){
                    dbHelper.reset();
                    SharedPreferences.Editor ed = sh.edit();
                    ed.clear();
                    ed.apply();

                    CDialog.up(NewDashboard.this,
                            "Informasi",
                            "Berhasil mereset data!",
                            false, false, false,
                            "TIDAK",
                            "LANJUTKAN",
                            "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {
                                    alert.dismiss();
                                }
                            }
                    ).show();
                }else{
                    CDialog.up(NewDashboard.this,
                            "Informasi",
                            "Gagal mereset data!",
                            false, false, false,
                            "TIDAK",
                            "ULANGI",
                            "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {
                                    alert.dismiss();
                                }
                            }
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<MResponseServer> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(NewDashboard.this,
                        "Informasi",
                        "Gagal mereset data!",
                        false, false, false,
                        "TIDAK",
                        "ULANGI",
                        "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });
    }
}
