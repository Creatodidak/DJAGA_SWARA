package id.creatodidak.djaga_swara.Dashboard;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Adapter.TpsAdapter;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.Bigdata.Calon;
import id.creatodidak.djaga_swara.API.Models.Bigdata.Ringkasan;
import id.creatodidak.djaga_swara.API.Models.Multi.Bupati;
import id.creatodidak.djaga_swara.API.Models.Multi.DPDRI;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDKab;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDProv;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRRI;
import id.creatodidak.djaga_swara.API.Models.Multi.Gubernur;
import id.creatodidak.djaga_swara.API.Models.Multi.Kades;
import id.creatodidak.djaga_swara.API.Models.Multi.PartaiKab;
import id.creatodidak.djaga_swara.API.Models.Multi.PartaiNas;
import id.creatodidak.djaga_swara.API.Models.Multi.PartaiProv;
import id.creatodidak.djaga_swara.API.Models.Multi.Presiden;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard2 extends AppCompatActivity implements TpsAdapter.OnItemClickListener {


    ApiService apiService;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    DatabaseHelper databaseHelper;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String id_sprint, nrp;
    int jlokasi, jcektps, jlappam, jformc1, jlapwal, jlapserah, totalJumlahData, jdraftdpt;
    private boolean isInternetAvailable;
    Button resData;

    ProgressBar progressBar, progressBar2;

    LinearLayout btndraft;
    TextView judul, textViewProgress;
    private final int progressStatus = 0;
    private final Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            id_sprint = getIntent().getStringExtra("sprint_id");
            nrp = sharedPreferences.getString("nrp", "");
            apiService = ApiClient.getClient().create(ApiService.class);
            databaseHelper = new DatabaseHelper(this);

            recyclerView = findViewById(R.id.listTps);
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isInternetAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            btndraft = findViewById(R.id.draftbtn);
            judul = findViewById(R.id.jumlahdraft);

            resData = findViewById(R.id.resetdata);
            progressBar = findViewById(R.id.progressBar);
            progressBar2 = findViewById(R.id.progressBar2);
            textViewProgress = findViewById(R.id.textViewProgress);
//            getallcalon();
            getdatatps(id_sprint, nrp);

            resData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resData.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.VISIBLE);
                    lanjutkan();
                }
            });

            btndraft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard2.this, Draft.class);
                    startActivity(intent);
                }
            });

            ImageView profile = findViewById(R.id.profile);

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard2.this, First.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void getdatatps(String id_sprint, String nrp) {
        Call<List<TpsList>> call = apiService.getTPSList(id_sprint, nrp);
        call.enqueue(new Callback<List<TpsList>>() {
            @Override
            public void onResponse(Call<List<TpsList>> call, Response<List<TpsList>> response) {
                if (response.isSuccessful()) {
                    List<TpsList> tpsList = response.body();
                    if (tpsList != null && !tpsList.isEmpty()) {
                        List<TpsList> tpsList2 = databaseHelper.getSprinDetailData();
                        if (tpsList2.isEmpty()) {
                            for (TpsList tps : tpsList) {
                                databaseHelper.insertSprinDetailData(tps.getId(), tps.getLatitude(), tps.getLongitude(), tps.getIdProv(), tps.getIdSprin(), tps.getIdKab(), tps.getIdKec(), tps.getIdDes(), tps.getIdTps(), tps.getNomorTps(), tps.getKetuaKpps(), tps.getHpKpps(), tps.getDptSementara(), tps.getDptTetap(), tps.getDptFinal(), tps.getKeterangan(), tps.getStatus(), tps.getLokasiKotakSuara(), tps.getCreatedAt(), tps.getUpdatedAt(), tps.getNamaDes(), tps.getNamaKec(), tps.getNamaKab());
                                databaseHelper.newtpsactivity(tps.getIdTps());
                            }
                        } else {
                            if(tpsList.size() != tpsList2.size()){
                                databaseHelper.resetTables2();
                                for (TpsList tps : tpsList) {
                                    databaseHelper.insertSprinDetailData(tps.getId(), tps.getLatitude(), tps.getLongitude(), tps.getIdProv(), tps.getIdSprin(), tps.getIdKab(), tps.getIdKec(), tps.getIdDes(), tps.getIdTps(), tps.getNomorTps(), tps.getKetuaKpps(), tps.getHpKpps(), tps.getDptSementara(), tps.getDptTetap(), tps.getDptFinal(), tps.getKeterangan(), tps.getStatus(), tps.getLokasiKotakSuara(), tps.getCreatedAt(), tps.getUpdatedAt(), tps.getNamaDes(), tps.getNamaKec(), tps.getNamaKab());
                                    databaseHelper.newtpsactivity(tps.getIdTps());
                                }
                            }
                        }

                        getallcalon(tpsList2.size(), tpsList.get(0).getIdProv(), tpsList.get(0).getIdKab(), tpsList.get(0).getIdKec(), tpsList.get(0).getIdDes());

                    } else {
                        Toast.makeText(Dashboard2.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Dashboard2.this, "Gagal memuat data dari server", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<TpsList>> call, Throwable t) {
                loadOffline();
            }
        });
    }

    private void loadOffline() {
        showDialog("Memuat Data Dari Local Database...");

        // Retrieve data from local database
        List<TpsList> tpsList = databaseHelper.getSprinDetailData();
        if (tpsList.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(Dashboard2.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.dismiss();
            TpsAdapter adapter = new TpsAdapter(tpsList, Dashboard2.this);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(Dashboard2.this);
            jlokasi = databaseHelper.getDraft("lokasi").size();
            jcektps = databaseHelper.getDraft("cektps").size();
            jlappam = databaseHelper.getDraft("lappam").size();
            jformc1 = databaseHelper.getDraft("formc1").size();
            jlapwal = databaseHelper.getDraft("lapwal").size();
            jlapserah = databaseHelper.getDraft("lapserah").size();
            jdraftdpt = databaseHelper.getDraft("draftdpt").size();
            int presidenj = databaseHelper.getpresiden("LOCAL").size();
            int bupatij = databaseHelper.getbupati("LOCAL").size();
            int gubernurj = databaseHelper.getgubernur("LOCAL").size();
            int dprrij = databaseHelper.getdprri("LOCAL").size();
            int dpdrij = databaseHelper.getdpdri("LOCAL").size();
            int kadesj = databaseHelper.getkades("LOCAL").size();
            int dprdProvj = databaseHelper.getdprdprov("LOCAL").size();
            int dprdKabj = databaseHelper.getdprdkab("LOCAL").size();
            int suaratidaksahj = databaseHelper.getsuaratidaksah("LOCAL").size();

            totalJumlahData = jlokasi + jcektps + jlappam + jformc1 + jlapwal + jlapserah + jdraftdpt + presidenj + bupatij + gubernurj + dprrij + dpdrij + kadesj +dprdProvj + dprdKabj + suaratidaksahj;
            judul.setText(totalJumlahData +" Draft Tersimpan!");
        }
    }

    private void lanjutkan() {
        List<TpsList> tpsLists = databaseHelper.getSprinDetailData();
        boolean semuaBerhasil = true;
        databaseHelper.deleteAllPartainas();
        databaseHelper.emptyDprdkab();
        databaseHelper.emptyDprdprov();
        databaseHelper.emptyDpdri();
        databaseHelper.emptyDprri();
        databaseHelper.emptyKades();
        databaseHelper.emptyBupati();
        databaseHelper.emptyGubernur();
        databaseHelper.emptyPresiden();
        databaseHelper.emptyPartaikab();
        databaseHelper.emptyPartaiprov();

        for (TpsList tpsList : tpsLists) {
            if (!ambildariserver(tpsList.getIdTps(),
                                 tpsList.getIdProv(),
                                 tpsList.getIdKab(),
                                 tpsList.getIdKec(),
                                 tpsList.getIdDes()
            )) {
                semuaBerhasil = false;
                break;
            }
        }

        if (semuaBerhasil) {
            getallcalon(tpsLists.size(), tpsLists.get(0).getIdProv(), tpsLists.get(0).getIdKab(), tpsLists.get(0).getIdKec(), tpsLists.get(0).getIdDes());
        } else {
            getallcalon(tpsLists.size(), tpsLists.get(0).getIdProv(), tpsLists.get(0).getIdKab(), tpsLists.get(0).getIdKec(), tpsLists.get(0).getIdDes());
        }
    }

    private boolean ambildariserver(String idTps, String idProv, String idKab, String idKec, String idDes) {
        // Move the network call to an AsyncTask
        new NetworkTask(idTps, idProv, idKab, idKec, idDes).execute();
        return true;  // You can return true for now, AsyncTask will handle the response
    }

    private class NetworkTask extends AsyncTask<Void, Void, Boolean> {
        private final String idTps;
        private final String idProv;
        private final String idKab;
        private final String idKec;
        private final String idDes;

        public NetworkTask(String idTps, String idProv, String idKab, String idKec, String idDes) {
            this.idTps = idTps;
            this.idProv = idProv;
            this.idKab = idKab;
            this.idKec = idKec;
            this.idDes = idDes;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Call<Calon> call = apiService.getSemuaCalon(idProv, idKab, idKec, idDes);

            try {
                Response<Calon> response = call.execute();

                if (response.isSuccessful()) {
                    Calon calon = response.body();
                    if (calon != null) {
                        boolean successPartaiNas = processPartaiNas(calon, idTps);
                        if (successPartaiNas) {
                            if(processPartaiProv(calon, idTps)){
                                if(processPartaiKab(calon, idTps)){
                                    if(getDpdri(calon, idTps)){
                                        if(getKades(calon, idTps)){
                                            if(getGubernur(calon, idTps)){
                                                if(processPresiden(calon, idTps)){
                                                    if(getBupati(calon, idTps)){
                                                        if(getDprri(calon, idTps)){
                                                            if(getDprdprov(calon, idTps)){
                                                                return getDprdkab(calon, idTps);
                                                            }else{
                                                                return false;
                                                            }
                                                        }else{
                                                            return false;
                                                        }
                                                    }else{
                                                        return false;
                                                    }
                                                }else{
                                                    return false;
                                                }
                                            }else{
                                                return false;
                                            }
                                        }else{
                                            return false;
                                        }
                                    }else{
                                        return false;
                                    }
                                }else{
                                    return false;
                                }
                            }else{
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("BG TASK", e.getLocalizedMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            // Handle the response here if needed
            if (success) {
                // Success
            } else {
                // Failure
            }
        }
    }

    private boolean processPartaiNas(Calon calon, String idTps) {
        List<PartaiNas> partaiNasList = calon.getPnas();

        if (partaiNasList != null) {
                for (PartaiNas partaiNas : partaiNasList) {
                    boolean iPnas = databaseHelper.insertPartainas(
                            partaiNas.getId_partai(),
                            partaiNas.getNomorurut(),
                            partaiNas.getNama(),
                            partaiNas.getTahun(),
                            partaiNas.getPeriode(),
                            partaiNas.getCreated_at(),
                            partaiNas.getUpdated_at(),
                            idTps
                    );

                    if (!iPnas) {
                        return false;
                    }
                }

                return true;
        }else{
            return false;
        }
    }

    private boolean processPartaiProv(Calon calon, String idTps) {
        List<PartaiProv> partaiProvList = calon.getPprov();
        if (partaiProvList != null) {

                for (PartaiProv partaiProv : partaiProvList) {
                    boolean iPprov = databaseHelper.insertPartaiprov(
                            partaiProv.getId_partai(),
                            partaiProv.getNomorurut(),
                            partaiProv.getNama(),
                            partaiProv.getId_prov(),
                            partaiProv.getTahun(),
                            partaiProv.getPeriode(),
                            partaiProv.getCreated_at(),
                            partaiProv.getUpdated_at(),
                            idTps
                    );

                    if (!iPprov) {
                        Log.i("INSERT P PROV", "gagal");
                        return false;
                    }
                }
                    return true;
        }
        return false;
    }

    private boolean processPartaiKab(Calon calon, String idTps) {
        List<PartaiKab> partaiKabList = calon.getPkab();
        if (partaiKabList != null) {
                for (PartaiKab partaiKab : partaiKabList) {
                    boolean iPkab = databaseHelper.insertPartaikab(
                            partaiKab.getId_partai(),
                            partaiKab.getNomorurut(),
                            partaiKab.getNama(),
                            partaiKab.getId_kab(),
                            partaiKab.getTahun(),
                            partaiKab.getPeriode(),
                            partaiKab.getCreated_at(),
                            partaiKab.getUpdated_at(),
                            idTps
                    );

                    if (!iPkab) {
                        Log.i("INSERT P KAB", "gagal");
                        return false;
                    }
                }
                return true;
            }
        return false;
    }

    private boolean processPresiden(Calon calon, String idTps) {
        List<Presiden> presidenList = calon.getPresiden();

        if (presidenList != null) {
                for (Presiden presiden : presidenList) {
                    boolean pres = databaseHelper.insertPresiden(
                            presiden.getId(),
                            presiden.getIdCalon(),
                            presiden.getNoUrut(),
                            presiden.getCapres(),
                            presiden.getCawapres(),
                            presiden.getTahun(),
                            presiden.getPeriode(),
                            presiden.getCreatedAt(),
                            presiden.getUpdatedAt(),
                            idTps);

                    if (!pres) {
                        Log.i("INSERT PRESIDEN", "gagal");
                        return false;
                    }
                }
                return true;
        }

        return false;
    }

    private boolean getGubernur(Calon calon, String idTps) {
        List<Gubernur> gubernurList = calon.getGubernur();

        if (gubernurList != null) {
                for (Gubernur gubernur : gubernurList) {
                    boolean gub = databaseHelper.insertGubernur(
                            gubernur.getId(),
                            gubernur.getId_prov(),
                            gubernur.getId_calon(),
                            gubernur.getNo_urut(),
                            gubernur.getCagub(),
                            gubernur.getCawagub(),
                            gubernur.getTahun(),
                            gubernur.getPeriode(),
                            gubernur.getCreated_at(),
                            gubernur.getUpdated_at(),
                            idTps
                    );

                    if (!gub) {
                        Log.i("INSERT GUBERNUR", "gagal");
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    private boolean getBupati(Calon calon, String idTps) {
        List<Bupati> bupatiList = calon.getBupati();

        if (bupatiList != null) {
                for (Bupati bupati : bupatiList) {
                    boolean bup = databaseHelper.insertBupati(
                            bupati.getId(),
                            bupati.getId_kab(),
                            bupati.getId_calon(),
                            bupati.getNo_urut(),
                            bupati.getCabup(),
                            bupati.getCawabup(),
                            bupati.getTahun(),
                            bupati.getPeriode(),
                            bupati.getCreated_at(),
                            bupati.getUpdated_at(),
                            idTps
                    );

                    if (!bup) {
                        Log.i("INSERT BUPATI", "gagal");
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    private boolean getKades(Calon calon, String idTps) {
        List<Kades> kadesList = calon.getKades();

        if (kadesList != null) {
                for (Kades kades : kadesList) {
                    boolean kadess = databaseHelper.insertKades(
                            kades.getId(),
                            kades.getId_des(),
                            kades.getId_calon(),
                            kades.getNo_urut(),
                            kades.getCakades(),
                            kades.getTahun(),
                            kades.getPeriode(),
                            kades.getCreated_at(),
                            kades.getUpdated_at(),
                            idTps
                    );

                    if (!kadess) {
                        Log.i("INSERT KADES", "gagal");
                        return false;
                    }
                }
            return true;
        }
        return false;
    }

    private boolean getDprri(Calon calon, String idTps) {
        List<DPRRI> dprriList = calon.getDprri();

        if (dprriList != null) {
                for (DPRRI dprri : dprriList) {
                    boolean dprris = databaseHelper.insertDPRRI(
                            dprri.getId(),
                            dprri.getId_calon(),
                            dprri.getId_dapil(),
                            dprri.getId_partai(),
                            dprri.getNomorurut(),
                            dprri.getNama(),
                            dprri.getTahun(),
                            dprri.getPeriode(),
                            dprri.getCreated_at(),
                            dprri.getUpdated_at(),
                            idTps
                    );

                    if (!dprris) {
                        Log.i("INSERT DPRRI", "gagal");
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    private boolean getDpdri(Calon calon, String idTps) {
        List<DPDRI> dpdriList = calon.getDpdri();

        if (dpdriList != null) {
                for (DPDRI dpdri : dpdriList) {
                    boolean dpdris = databaseHelper.insertDPDRI(
                            dpdri.getId(),
                            dpdri.getId_calon(),
                            dpdri.getId_prov(),
                            dpdri.getNomorurut(),
                            dpdri.getNama(),
                            dpdri.getTahun(),
                            dpdri.getPeriode(),
                            dpdri.getCreated_at(),
                            dpdri.getUpdated_at(),
                            idTps
                    );

                    if (!dpdris) {
                        Log.i("INSERT DPDRI", "gagal");
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    private boolean getDprdprov(Calon calon, String idTps) {
        List<DPRDProv> dprdProvList = calon.getDprdprov();


        if (dprdProvList != null) {
                for (DPRDProv dprdProv : dprdProvList) {
                    boolean dprdprovs = databaseHelper.insertDPRDProv(
                            dprdProv.getId(),
                            dprdProv.getId_calon(),
                            dprdProv.getId_dapil(),
                            dprdProv.getId_partai(),
                            dprdProv.getNomorurut(),
                            dprdProv.getNama(),
                            dprdProv.getTahun(),
                            dprdProv.getPeriode(),
                            dprdProv.getCreated_at(),
                            dprdProv.getUpdated_at(),
                            idTps
                    );

                    if (!dprdprovs) {
                        Log.i("INSERT DPRDProv", "gagal");
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    private boolean getDprdkab(Calon calon, String idTps) {
        List<DPRDKab> dprdKabList = calon.getDprdkab();

        if (dprdKabList != null) {
              for (DPRDKab dprdKab : dprdKabList) {
                    boolean dprdkabs = databaseHelper.insertDPRDKab(
                            dprdKab.getId(),
                            dprdKab.getId_calon(),
                            dprdKab.getId_dapil(),
                            dprdKab.getId_partai(),
                            dprdKab.getNomorurut(),
                            dprdKab.getNama(),
                            dprdKab.getTahun(),
                            dprdKab.getPeriode(),
                            dprdKab.getCreated_at(),
                            dprdKab.getUpdated_at(),
                            idTps
                    );

                    if (!dprdkabs) {
                        Log.i("INSERT DPRDKab", "gagal");
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    private void getallcalon(int size, String idProv, String idKab, String idKec, String idDes) {
        resData.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.GONE);
        Call<Ringkasan> call = apiService.getAllCalon(idProv, idKab, idKec, idDes);
        call.enqueue(new Callback<Ringkasan>() {
            @Override
            public void onResponse(Call<Ringkasan> call, Response<Ringkasan> response) {
                int totalItems = response.body().getJumlah() * size;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("TOTALDATA", totalItems);
                editor.apply();

                int completedItems = databaseHelper.totaldata(); // Number of items completed

                if (completedItems == 0) {
                    completedItems = 1;
                }

                int totalProgress = 0;

                if (totalItems > 0) {
                    // Calculate the total progress percentage based on completed items
                    totalProgress = (completedItems * 100) / totalItems;
                }

                CardView loaded = findViewById(R.id.loaded);

                if (totalProgress >= 100) {
                    loadOffline();
                    loaded.setVisibility(View.GONE);
                }

                int finalTotalProgress = totalProgress;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int progressStatus = 0;
                        while (progressStatus < finalTotalProgress) {
                            progressStatus += 1;
                            int finalProgressStatus = progressStatus;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(finalProgressStatus);
                                    textViewProgress.setText(finalProgressStatus + "%");
                                    if (finalProgressStatus > 50) {
                                        textViewProgress.setTextColor(Color.parseColor("#000000"));
                                    }
                                }
                            });
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                resData.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Ringkasan> call, Throwable t) {
                int totals = sharedPreferences.getInt("TOTALDATA", 1);
                int completedItems = databaseHelper.totaldata();

                if (totals == completedItems) {
                    loadOffline();
                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dashboard2.this);
                    builder.setTitle("TIDAK DAPAT MELANJUTKAN!")
                            .setMessage("ANDA HARUS MENDOWNLOAD SELURUH DATA TERLEBIH DAHULU!")
                            .setIcon(R.drawable.logosmall)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });
    }


    @Override
    public void onItemClick(TpsList tpsListOffline) {
        Intent intent = new Intent(Dashboard2.this, Tugas.class);
        intent.putExtra("id_tps", tpsListOffline.getIdTps());
        intent.putExtra("type", getIntent().getStringExtra("type"));

        if(cekcalon(tpsListOffline.getIdTps())){
            startActivity(intent);
        }
    }

    private void showDialog(String message) {
        progressDialog = new ProgressDialog(Dashboard2.this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        jlokasi = databaseHelper.getDraft("lokasi").size();
        jcektps = databaseHelper.getDraft("cektps").size();
        jlappam = databaseHelper.getDraft("lappam").size();
        jformc1 = databaseHelper.getDraft("formc1").size();
        jlapwal = databaseHelper.getDraft("lapwal").size();
        jlapserah = databaseHelper.getDraft("lapserah").size();
        jdraftdpt = databaseHelper.getDraft("draftdpt").size();
        int presidenj = databaseHelper.getpresiden("LOCAL").size();
        int bupatij = databaseHelper.getbupati("LOCAL").size();
        int gubernurj = databaseHelper.getgubernur("LOCAL").size();
        int dprrij = databaseHelper.getdprri("LOCAL").size();
        int dpdrij = databaseHelper.getdpdri("LOCAL").size();
        int kadesj = databaseHelper.getkades("LOCAL").size();
        int dprdProvj = databaseHelper.getdprdprov("LOCAL").size();
        int dprdKabj = databaseHelper.getdprdkab("LOCAL").size();
        int suaratidaksahj = databaseHelper.getsuaratidaksah("LOCAL").size();

        totalJumlahData = jlokasi + jcektps + jlappam + jformc1 + jlapwal + jlapserah + jdraftdpt + presidenj + bupatij + gubernurj + dprrij + dpdrij + kadesj +dprdProvj + dprdKabj + suaratidaksahj;
        judul.setText(totalJumlahData +" Draft Tersimpan!");
    }

    private boolean cekcalon(String id_tps) {
        String text = getIntent().getStringExtra("type");
        text = text.substring(1, text.length() - 1);
        text = text.replaceAll("\\s+", "").replaceAll("-", "").replaceAll("\"", "");
        String[] array = text.split(",");

        for (int i = 0; i < array.length; i++) {
            String item = array[i];
            boolean isEmpty = databaseHelper.isTableEmpty(item, id_tps);
            if (isEmpty) {
                String message = "Data peserta pemilu bidang " + item + " pada tps " + id_tps + " kosong, hubungi Posko!.";
                notifikasi("DATA PESERTA TIDAK DITEMUKAN!", message, true, false);
                return false;
            }
        }

        return true;
    }

    private void notifikasi(String info, String s, Boolean cancel, Boolean close) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dashboard2.this);

        if(cancel){
            if(close){
                builder.setTitle(info)
                        .setMessage(s)
                        .setIcon(R.drawable.logosmall)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .show();
            }else{
                builder.setTitle(info)
                        .setMessage(s)
                        .setIcon(R.drawable.logosmall)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }else{
            builder.setTitle(info)
                    .setMessage(s)
                    .setIcon(R.drawable.logosmall)
                    .setCancelable(false)
                    .show();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}