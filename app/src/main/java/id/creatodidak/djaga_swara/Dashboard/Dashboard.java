package id.creatodidak.djaga_swara.Dashboard;

// import statements

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Adapter.TpsAdapter;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.DataCalon;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lappam;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Dashboard extends AppCompatActivity implements TpsAdapter.OnItemClickListener {

    ApiService apiService;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    DatabaseHelper databaseHelper;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String id_sprint, nrp;
    int jlokasi, jcektps, jlappam, jformc1, jlapwal, jlapserah, totalJumlahData, jdraftdpt;
    private boolean isInternetAvailable;

    LinearLayout btndraft;
    TextView judul;

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

            // Check internet availability
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isInternetAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            btndraft = findViewById(R.id.draftbtn);
            judul = findViewById(R.id.jumlahdraft);

            if (isInternetAvailable) {
                loadDataFromServer();
            } else {
                loadDataFromLocalDatabase();
            }

            btndraft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard.this, Draft.class);
                    startActivity(intent);
                }
            });

            swipeRefreshLayout.setOnRefreshListener(() -> {
                if (isInternetAvailable) {
                    loadDataFromServer();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
            });

            ImageView profile = findViewById(R.id.profile);

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard.this, First.class);
                    startActivity(intent);
                }
            });
        }
    }



    private void loadDataFromServer() {
        showDialog("Memuat Data Dari Server...");

        // Make API request to fetch data from the server
        Call<List<TpsList>> call = apiService.getTPSList(id_sprint, nrp);
        call.enqueue(new Callback<List<TpsList>>() {
            @Override
            public void onResponse(Call<List<TpsList>> call, Response<List<TpsList>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    List<TpsList> tpsList = response.body();
                    if (tpsList != null && !tpsList.isEmpty()) {
                        List<TpsList> tpsList2 = databaseHelper.getSprinDetailData();
                        if (tpsList2.isEmpty()) {
                            for (TpsList tps : tpsList) {
                                databaseHelper.insertSprinDetailData(tps.getId(), tps.getLatitude(), tps.getLongitude(), tps.getIdProv(), tps.getIdSprin(), tps.getIdKab(), tps.getIdKec(), tps.getIdDes(), tps.getIdTps(), tps.getNomorTps(), tps.getKetuaKpps(), tps.getHpKpps(), tps.getDptSementara(), tps.getDptTetap(), tps.getDptFinal(), tps.getKeterangan(), tps.getStatus(), tps.getLokasiKotakSuara(), tps.getCreatedAt(), tps.getUpdatedAt(), tps.getNamaDes(), tps.getNamaKec(), tps.getNamaKab());
                                getdaftarcalon(tps.getIdTps());
                                databaseHelper.newtpsactivity(tps.getIdTps());
                            }
                            loadDataFromLocalDatabase();
                        } else {
                            if(tpsList.size() != tpsList2.size()){
                                databaseHelper.resetTables2();
                                for (TpsList tps : tpsList) {
                                    databaseHelper.insertSprinDetailData(tps.getId(), tps.getLatitude(), tps.getLongitude(), tps.getIdProv(), tps.getIdSprin(), tps.getIdKab(), tps.getIdKec(), tps.getIdDes(), tps.getIdTps(), tps.getNomorTps(), tps.getKetuaKpps(), tps.getHpKpps(), tps.getDptSementara(), tps.getDptTetap(), tps.getDptFinal(), tps.getKeterangan(), tps.getStatus(), tps.getLokasiKotakSuara(), tps.getCreatedAt(), tps.getUpdatedAt(), tps.getNamaDes(), tps.getNamaKec(), tps.getNamaKab());
                                    getdaftarcalon(tps.getIdTps());
                                    databaseHelper.newtpsactivity(tps.getIdTps());
                                }
                            }
                            loadDataFromLocalDatabase();
                        }
                    } else {
                        Toast.makeText(Dashboard.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Dashboard.this, "Gagal memuat data dari server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TpsList>> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(Dashboard.this, "Gagal memuat data dari server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getdaftarcalon(String id_tps) {
        String text = getIntent().getStringExtra("type");
        text = text.substring(1, text.length() - 1);
        text = text.replaceAll("\\s+", "").replaceAll("-", "").replaceAll("\"", "");
        String[] array = text.split(",");

        for (int i = 0; i < array.length; i++) {
            String item = array[i];
            switch (item) {
                case "PRESIDEN":
                    getcalon("presiden", id_tps);
                    break;
                case "DPRRI":
                    getcalon("dprri", id_tps);
                    break;
                case "DPDRI":
                    getcalon("dpdri", id_tps);
                    break;
                case "GUBERNUR":
                    getcalon("gubernur", id_tps);
                    break;
                case "DPRDPROV":
                    getcalon("dprdprov", id_tps);
                    break;
                case "BUPATI":
                    getcalon("bupati", id_tps);
                    break;
                case "DPRDKAB":
                    getcalon("dprdkab", id_tps);
                    break;
                case "KADES":
                    getcalon("kades", id_tps);
                    break;
            }
        }
    }

    private void getcalon(String type, String id) {
        Log.i("MENGAMBIL DATA "+type, "hehe");
        apiService = ApiClient.getClient().create(ApiService.class);

        if(type.equals("presiden")){
            Call<List<DataCalon.Presiden>> call = apiService.datapresiden(id, type);
            call.enqueue(new Callback<List<DataCalon.Presiden>>() {
                @Override
                public void onResponse(Call<List<DataCalon.Presiden>> call, Response<List<DataCalon.Presiden>> response) {
                    List<DataCalon.Presiden> presidenList = databaseHelper.getAllPresiden(id);
                    List<DataCalon.Presiden> presidens = response.body();

                    if(presidens != null && !presidens.isEmpty()){
                        if(presidenList.isEmpty()){
                            for(DataCalon.Presiden presiden : presidens){
                                databaseHelper.insertPresiden(
                                        presiden.getId(), presiden.getId_calon(), presiden.getNo_urut(), presiden.getCapres(), presiden.getCawapres(), presiden.getTahun(), presiden.getPeriode(), presiden.getCreated_at(), presiden.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(presidens.size() != presidenList.size()){
                                databaseHelper.resetPresiden();
                                for(DataCalon.Presiden presiden : presidens){
                                    databaseHelper.insertPresiden(
                                            presiden.getId(), presiden.getId_calon(), presiden.getNo_urut(), presiden.getCapres(), presiden.getCawapres(), presiden.getTahun(), presiden.getPeriode(), presiden.getCreated_at(), presiden.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.Presiden>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }else if(type.equals("dprri")){
            Call<List<DataCalon.DPRRI>> call = apiService.datadprri(id, type);
            call.enqueue(new Callback<List<DataCalon.DPRRI>>() {
                @Override
                public void onResponse(Call<List<DataCalon.DPRRI>> call, Response<List<DataCalon.DPRRI>> response) {
                    List<DataCalon.DPRRI> dprriList = databaseHelper.getAllDprri(id);
                    List<DataCalon.DPRRI> dprris = response.body();


                    if(dprris != null && !dprris.isEmpty()){
                        if(dprriList.isEmpty()){
                            for(DataCalon.DPRRI dprri : dprris){
                                databaseHelper.insertDPRRI(
                                        dprri.getId(), dprri.getId_calon(), dprri.getId_dapil(), dprri.getId_partai(), dprri.getNomorurut(), dprri.getNama(), dprri.getTahun(), dprri.getPeriode(), dprri.getCreated_at(), dprri.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(dprriList.size() != dprris.size()){
                                databaseHelper.resetDPRRI();
                                for(DataCalon.DPRRI dprri : dprris){
                                    databaseHelper.insertDPRRI(
                                            dprri.getId(), dprri.getId_calon(), dprri.getId_dapil(), dprri.getId_partai(), dprri.getNomorurut(), dprri.getNama(), dprri.getTahun(), dprri.getPeriode(), dprri.getCreated_at(), dprri.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.DPRRI>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }else if(type.equals("dpdri")){
            Call<List<DataCalon.DPDRI>> call = apiService.datadpdri(id, type);
            call.enqueue(new Callback<List<DataCalon.DPDRI>>() {
                @Override
                public void onResponse(Call<List<DataCalon.DPDRI>> call, Response<List<DataCalon.DPDRI>> response) {
                    List<DataCalon.DPDRI> dpdriList = databaseHelper.getAllDpdri(id);
                    List<DataCalon.DPDRI> dpdris = response.body();


                    if(dpdris != null && !dpdris.isEmpty()){
                        if(dpdriList.isEmpty()){
                            for(DataCalon.DPDRI dpdri : dpdris){
                                databaseHelper.insertDPDRI(
                                        dpdri.getId(), dpdri.getId_calon(), dpdri.getId_prov(), dpdri.getNomorurut(), dpdri.getNama(), dpdri.getTahun(), dpdri.getPeriode(), dpdri.getCreated_at(), dpdri.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(dpdriList.size() != dpdris.size()){
                                databaseHelper.resetDPDRI();
                                for(DataCalon.DPDRI dpdri : dpdris){
                                    databaseHelper.insertDPDRI(
                                            dpdri.getId(), dpdri.getId_calon(), dpdri.getId_prov(), dpdri.getNomorurut(), dpdri.getNama(), dpdri.getTahun(), dpdri.getPeriode(), dpdri.getCreated_at(), dpdri.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.DPDRI>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }else if(type.equals("dprdprov")){
            Call<List<DataCalon.DPRDProv>> call = apiService.datadprdprov(id, type);
            call.enqueue(new Callback<List<DataCalon.DPRDProv>>() {
                @Override
                public void onResponse(Call<List<DataCalon.DPRDProv>> call, Response<List<DataCalon.DPRDProv>> response) {
                    List<DataCalon.DPRDProv> dprdprovList = databaseHelper.getAllDPRDprov(id);
                    List<DataCalon.DPRDProv> dprdprovs = response.body();

                    if(dprdprovs != null && !dprdprovs.isEmpty()){
                        if(dprdprovList.isEmpty()){
                            for(DataCalon.DPRDProv dprdprov : dprdprovs){
                                databaseHelper.insertDPRDProv(
                                        dprdprov.getId(), dprdprov.getId_calon(), dprdprov.getId_dapil(), dprdprov.getId_partai(), dprdprov.getNomorurut(), dprdprov.getNama(), dprdprov.getTahun(), dprdprov.getPeriode(), dprdprov.getCreated_at(), dprdprov.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(dprdprovList.size() != dprdprovs.size()){
                                databaseHelper.resetDPRDProv();
                                for(DataCalon.DPRDProv dprdprov : dprdprovs){
                                    databaseHelper.insertDPRDProv(
                                            dprdprov.getId(), dprdprov.getId_calon(), dprdprov.getId_dapil(), dprdprov.getId_partai(), dprdprov.getNomorurut(), dprdprov.getNama(), dprdprov.getTahun(), dprdprov.getPeriode(), dprdprov.getCreated_at(), dprdprov.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.DPRDProv>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }else if(type.equals("dprdkab")){
            Call<List<DataCalon.DPRDKab>> call = apiService.datadprdkab(id, type);
            call.enqueue(new Callback<List<DataCalon.DPRDKab>>() {
                @Override
                public void onResponse(Call<List<DataCalon.DPRDKab>> call, Response<List<DataCalon.DPRDKab>> response) {
                    List<DataCalon.DPRDKab> dprdkabList = databaseHelper.getAllDPRDkab(id);
                    List<DataCalon.DPRDKab> dprdkabs = response.body();


                    if(dprdkabs != null && !dprdkabs.isEmpty()){
                        if(dprdkabList.isEmpty()){
                            for(DataCalon.DPRDKab dprdkab : dprdkabs){
                                databaseHelper.insertDPRDKab(
                                        dprdkab.getId(), dprdkab.getId_calon(), dprdkab.getId_dapil(), dprdkab.getId_partai(), dprdkab.getNomorurut(), dprdkab.getNama(), dprdkab.getTahun(), dprdkab.getPeriode(), dprdkab.getCreated_at(), dprdkab.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(dprdkabList.size() != dprdkabs.size()){
                                databaseHelper.resetDPRDKab();
                                for(DataCalon.DPRDKab dprdkab : dprdkabs){
                                    databaseHelper.insertDPRDKab(
                                            dprdkab.getId(), dprdkab.getId_calon(), dprdkab.getId_dapil(), dprdkab.getId_partai(), dprdkab.getNomorurut(), dprdkab.getNama(), dprdkab.getTahun(), dprdkab.getPeriode(), dprdkab.getCreated_at(), dprdkab.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.DPRDKab>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }else if(type.equals("gubernur")){
            Call<List<DataCalon.Gubernur>> call = apiService.datagubernur(id, type);
            call.enqueue(new Callback<List<DataCalon.Gubernur>>() {
                @Override
                public void onResponse(Call<List<DataCalon.Gubernur>> call, Response<List<DataCalon.Gubernur>> response) {
                    List<DataCalon.Gubernur> gubernurList = databaseHelper.getAllGubernur(id);
                    List<DataCalon.Gubernur> gubernurs = response.body();


                    if(gubernurs != null && !gubernurs.isEmpty()){
                        if(gubernurList.isEmpty()){
                            for(DataCalon.Gubernur gubernur : gubernurs){
                                databaseHelper.insertGubernur(
                                        gubernur.getId(), gubernur.getId_prov(), gubernur.getId_calon(), gubernur.getNo_urut(), gubernur.getCagub(), gubernur.getCawagub(), gubernur.getTahun(), gubernur.getPeriode(), gubernur.getCreated_at(), gubernur.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(gubernurs.size() != gubernurList.size()){
                                databaseHelper.resetGubernur();
                                for(DataCalon.Gubernur gubernur : gubernurs){
                                    databaseHelper.insertGubernur(
                                            gubernur.getId(), gubernur.getId_prov(), gubernur.getId_calon(), gubernur.getNo_urut(), gubernur.getCagub(), gubernur.getCawagub(), gubernur.getTahun(), gubernur.getPeriode(), gubernur.getCreated_at(), gubernur.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.Gubernur>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }else if(type.equals("bupati")){
            Call<List<DataCalon.Bupati>> call = apiService.databupati(id, type);
            call.enqueue(new Callback<List<DataCalon.Bupati>>() {
                @Override
                public void onResponse(Call<List<DataCalon.Bupati>> call, Response<List<DataCalon.Bupati>> response) {
                    List<DataCalon.Bupati> bupatiList = databaseHelper.getAllBupati(id);
                    List<DataCalon.Bupati> bupatis = response.body();


                    if(bupatis != null && !bupatis.isEmpty()){
                        if(bupatiList.isEmpty()){
                            for(DataCalon.Bupati bupati : bupatis){
                                databaseHelper.insertBupati(
                                        bupati.getId(), bupati.getId_kab(), bupati.getId_calon(), bupati.getNo_urut(), bupati.getCabup(), bupati.getCawabup(), bupati.getTahun(), bupati.getPeriode(), bupati.getCreated_at(), bupati.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(bupatis.size() != bupatiList.size()){
                                databaseHelper.resetBupati();
                                for(DataCalon.Bupati bupati : bupatis){
                                    databaseHelper.insertBupati(
                                            bupati.getId(), bupati.getId_kab(), bupati.getId_calon(), bupati.getNo_urut(), bupati.getCabup(), bupati.getCawabup(), bupati.getTahun(), bupati.getPeriode(), bupati.getCreated_at(), bupati.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.Bupati>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }else if(type.equals("kades")){
            Call<List<DataCalon.Kades>> call = apiService.datakades(id, type);
            call.enqueue(new Callback<List<DataCalon.Kades>>() {
                @Override
                public void onResponse(Call<List<DataCalon.Kades>> call, Response<List<DataCalon.Kades>> response) {
                    List<DataCalon.Kades> kadesList = databaseHelper.getAllKades(id);
                    List<DataCalon.Kades> kadess = response.body();


                    if(kadess != null && !kadess.isEmpty()){
                        if(kadesList.isEmpty()){
                            for(DataCalon.Kades kades : kadess){
                                databaseHelper.insertKades(
                                        kades.getId(), kades.getId_des(), kades.getId_calon(), kades.getNo_urut(), kades.getCakades(), kades.getTahun(), kades.getPeriode(), kades.getCreated_at(), kades.getUpdated_at(), id
                                );
                            }
                        }else{
                            if(kadess.size() != kadesList.size()){
                                databaseHelper.resetKades();
                                for(DataCalon.Kades kades : kadess){
                                    databaseHelper.insertKades(
                                            kades.getId(), kades.getId_des(), kades.getId_calon(), kades.getNo_urut(), kades.getCakades(), kades.getTahun(), kades.getPeriode(), kades.getCreated_at(), kades.getUpdated_at(), id
                                    );
                                }
                            }
                        }
                    }else{
                        Toast.makeText(Dashboard.this, "TIdak ada data "+type+" di server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<DataCalon.Kades>> call, Throwable t) {
                    Log.i("MENGAMBIL DATA "+type, "Error: "+t.getLocalizedMessage());;
                }
            });
        }
    }
    
    private void loadDataFromLocalDatabase() {
        showDialog("Memuat Data Dari Local Database...");

        // Retrieve data from local database
        List<TpsList> tpsList = databaseHelper.getSprinDetailData();
        if (tpsList.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(Dashboard.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.dismiss();
            TpsAdapter adapter = new TpsAdapter(tpsList, Dashboard.this);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(Dashboard.this);
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

    @Override
    public void onItemClick(TpsList tpsListOffline) {
        Intent intent = new Intent(Dashboard.this, Tugas.class);
        intent.putExtra("id_tps", tpsListOffline.getIdTps());
        intent.putExtra("type", getIntent().getStringExtra("type"));

        if(cekcalon(tpsListOffline.getIdTps())){
            startActivity(intent);
        }
    }

    private void showDialog(String message) {
        progressDialog = new ProgressDialog(Dashboard.this);
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dashboard.this);
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

}