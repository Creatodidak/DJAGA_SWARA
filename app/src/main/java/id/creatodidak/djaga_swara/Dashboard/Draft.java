package id.creatodidak.djaga_swara.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.CektpsAdapter;
import id.creatodidak.djaga_swara.API.Adapter.DptAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.BupatiAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.DpdRiAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.DprRiAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.DprdKabAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.DprdProvAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.GubernurAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.KadesAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.PresidenAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Draft.SuaraTidakSahAdapter;
import id.creatodidak.djaga_swara.API.Adapter.FormC1Adapter;
import id.creatodidak.djaga_swara.API.Adapter.LappamAdapter;
import id.creatodidak.djaga_swara.API.Adapter.LapserahAdapter;
import id.creatodidak.djaga_swara.API.Adapter.LapwalAdapter;
import id.creatodidak.djaga_swara.API.Adapter.LokasiAdapter;
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
import id.creatodidak.djaga_swara.API.Models.Multi.SuaraTidakSah;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;

public class Draft extends AppCompatActivity {
    private List<Lokasi> lokasiList = new ArrayList<>();
    private List<CekTps> cekTpsList = new ArrayList<>();
    private List<Lappam> lappamList = new ArrayList<>();
    private List<FormC1> formC1List = new ArrayList<>();
    private List<Lapwal> lapwalList = new ArrayList<>();
    private List<Lapserah> lapserahList = new ArrayList<>();
    private List<DraftDpt> draftDptList = new ArrayList<>();

    private List<Presiden> presidenList = new ArrayList<>();
    private List<Gubernur> gubernurList = new ArrayList<>();
    private List<Bupati> bupatiList = new ArrayList<>();
    private List<Kades> kadesList = new ArrayList<>();
    private List<DPRRI> dprriList = new ArrayList<>();
    private List<DPDRI> dpdriList = new ArrayList<>();
    private List<DPRDProv> dprdProvList = new ArrayList<>();
    private List<DPRDKab> dprdKabList = new ArrayList<>();
    private List<SuaraTidakSah> suaraTidakSahList = new ArrayList<>();
    private RecyclerView lokasi, cektps, lappam, formc1, lapwal, lapserah, dpt, presiden, gubernur, bupati, kades, dprri, dpdri, dprdprov, dprdkab, suaratidaksah;
    private CektpsAdapter cektpsAdapter;
    private SuaraTidakSahAdapter suaraTidakSahAdapter;
    private LokasiAdapter lokasiAdapter;

    private FormC1Adapter formC1Adapter;
    private LappamAdapter lappamAdapter;
    private LapwalAdapter lapwalAdapter;
    private LapserahAdapter lapserahAdapter;
    private DptAdapter dptAdapter;
    private PresidenAdapter presidenAdapter;
    private GubernurAdapter gubernurAdapter;
    private BupatiAdapter bupatiAdapter;
    private KadesAdapter kadesAdapter;
    private DprRiAdapter dprRiAdapter;
    private DpdRiAdapter dpdRiAdapter;
    private DprdProvAdapter dprdProvAdapter;
    private DprdKabAdapter dprdKabAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            // Inisialisasi semua RecyclerView
            lokasi = findViewById(R.id.lokasi);
            cektps = findViewById(R.id.cektps);
            lappam = findViewById(R.id.lappam);
            formc1 = findViewById(R.id.formc1);
            lapwal = findViewById(R.id.lapwal);
            lapserah = findViewById(R.id.lapserah);
            dpt = findViewById(R.id.dpt);
            gubernur = findViewById(R.id.gubernur);
            bupati = findViewById(R.id.bupati);
            kades = findViewById(R.id.kades);
            presiden = findViewById(R.id.presiden);
            dprri = findViewById(R.id.dprri);
            dpdri = findViewById(R.id.dpdri);
            dprdprov = findViewById(R.id.dprdprov);
            dprdkab = findViewById(R.id.dprdkab);
            suaratidaksah = findViewById(R.id.suaratidaksah);
            // Inisialisasi LayoutManager untuk setiap RecyclerView
            RecyclerView.LayoutManager lokasiLayoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager cektpsLayoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager lappamLayoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager formc1LayoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager lapwalLayoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager lapserahLayoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager dptLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager presidenLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager gubernurLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager bupatiLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager kadesLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager dprriLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager dpdriLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager dprdprovLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager dprdkabLM = new LinearLayoutManager(this);
            RecyclerView.LayoutManager suaratidaksahLM = new LinearLayoutManager(this);

            // Set LayoutManager untuk setiap RecyclerView
            lokasi.setLayoutManager(lokasiLayoutManager);
            cektps.setLayoutManager(cektpsLayoutManager);
            lappam.setLayoutManager(lappamLayoutManager);
            formc1.setLayoutManager(formc1LayoutManager);
            lapwal.setLayoutManager(lapwalLayoutManager);
            lapserah.setLayoutManager(lapserahLayoutManager);
            dpt.setLayoutManager(dptLM);
            presiden.setLayoutManager(presidenLM);
            gubernur.setLayoutManager(gubernurLM);
            bupati.setLayoutManager(bupatiLM);
            kades.setLayoutManager(kadesLM);
            dprri.setLayoutManager(dprriLM);
            dpdri.setLayoutManager(dpdriLM);
            dprdprov.setLayoutManager(dprdprovLM);
            dprdkab.setLayoutManager(dprdkabLM);
            suaratidaksah.setLayoutManager(suaratidaksahLM);

            // Inisialisasi adapter untuk setiap RecyclerView
            lokasiAdapter = new LokasiAdapter(lokasiList, this);
            dptAdapter = new DptAdapter(draftDptList, this);
            cektpsAdapter = new CektpsAdapter(cekTpsList, this);
            formC1Adapter = new FormC1Adapter(formC1List, this);
            lappamAdapter = new LappamAdapter(lappamList, this);
            lapwalAdapter = new LapwalAdapter(lapwalList, this);
            lapserahAdapter = new LapserahAdapter(lapserahList, this);
            presidenAdapter = new PresidenAdapter(presidenList, this);
            gubernurAdapter = new GubernurAdapter(gubernurList, this);
            bupatiAdapter = new BupatiAdapter(bupatiList, this);
            kadesAdapter = new KadesAdapter(kadesList, this);
            dprRiAdapter = new DprRiAdapter(dprriList, this);
            dpdRiAdapter = new DpdRiAdapter(dpdriList, this);
            dprdProvAdapter = new DprdProvAdapter(dprdProvList, this);
            dprdKabAdapter = new DprdKabAdapter(dprdKabList, this);
            suaraTidakSahAdapter = new SuaraTidakSahAdapter(suaraTidakSahList, this);

            // Set adapter untuk setiap RecyclerView
            lokasi.setAdapter(lokasiAdapter);
            dpt.setAdapter(dptAdapter);
            cektps.setAdapter(cektpsAdapter);
            formc1.setAdapter(formC1Adapter);
            lappam.setAdapter(lappamAdapter);
            lapwal.setAdapter(lapwalAdapter);
            lapserah.setAdapter(lapserahAdapter);
            presiden.setAdapter(presidenAdapter);
            gubernur.setAdapter(gubernurAdapter);
            bupati.setAdapter(bupatiAdapter);
            kades.setAdapter(kadesAdapter);
            dprri.setAdapter(dprRiAdapter);
            dpdri.setAdapter(dpdRiAdapter);
            dprdprov.setAdapter(dprdProvAdapter);
            dprdkab.setAdapter(dprdKabAdapter);
            suaratidaksah.setAdapter(suaraTidakSahAdapter);

            // Mengambil data dari database dan memperbarui tampilan RecyclerView
            fetchDataAndUpdateViews();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Tutup koneksi ke database saat activity dihancurkan
        databaseHelper.close();
    }

    private void fetchDataAndUpdateViews() {
        // Mengambil data dari database
        databaseHelper = new DatabaseHelper(this);
        lokasiList = databaseHelper.getAllLokasi();
        draftDptList = databaseHelper.getAllDraftDpt();
        cekTpsList = databaseHelper.getAllCekTps();
        formC1List = databaseHelper.getAllFormC1();
        lappamList = databaseHelper.getAllLappam();
        lapwalList = databaseHelper.getAllLapwal();
        lapserahList = databaseHelper.getAllLapserah();
        presidenList = databaseHelper.getpresiden("LOCAL");
        bupatiList = databaseHelper.getbupati("LOCAL");
        gubernurList = databaseHelper.getgubernur("LOCAL");
        dprriList = databaseHelper.getdprri("LOCAL");
        dpdriList = databaseHelper.getdpdri("LOCAL");
        kadesList = databaseHelper.getkades("LOCAL");
        dprdProvList = databaseHelper.getdprdprov("LOCAL");
        dprdKabList = databaseHelper.getdprdkab("LOCAL");
        suaraTidakSahList = databaseHelper.getsuaratidaksah("LOCAL");

        // Memperbarui tampilan RecyclerView
        lokasiAdapter.setData(lokasiList);
        dptAdapter.setData(draftDptList);
        cektpsAdapter.setData(cekTpsList);
        formC1Adapter.setData(formC1List);
        lappamAdapter.setData(lappamList);
        lapwalAdapter.setData(lapwalList);
        lapserahAdapter.setData(lapserahList);
        presidenAdapter.setData(presidenList);
        gubernurAdapter.setData(gubernurList);
        bupatiAdapter.setData(bupatiList);
        kadesAdapter.setData(kadesList);
        dprRiAdapter.setData(dprriList);
        dpdRiAdapter.setData(dpdriList);
        dprdProvAdapter.setData(dprdProvList);
        dprdKabAdapter.setData(dprdKabList);
        suaraTidakSahAdapter.setData(suaraTidakSahList);

        // Memperbarui tampilan RecyclerView
        lokasiAdapter.notifyDataSetChanged();
        dptAdapter.notifyDataSetChanged();
        cektpsAdapter.notifyDataSetChanged();
//        formc1Adapter.notifyDataSetChanged();
        lappamAdapter.notifyDataSetChanged();
        lapwalAdapter.notifyDataSetChanged();
        lapserahAdapter.notifyDataSetChanged();
        presidenAdapter.notifyDataSetChanged();
        gubernurAdapter.notifyDataSetChanged();
        bupatiAdapter.notifyDataSetChanged();
        kadesAdapter.notifyDataSetChanged();
        dprRiAdapter.notifyDataSetChanged();
        dpdRiAdapter.notifyDataSetChanged();
        dprdProvAdapter.notifyDataSetChanged();
        dprdKabAdapter.notifyDataSetChanged();
        suaraTidakSahAdapter.notifyDataSetChanged();
    }
}
