package id.creatodidak.djaga_swara.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.CektpsAdapter;
import id.creatodidak.djaga_swara.API.Adapter.FormC1Adapter;
import id.creatodidak.djaga_swara.API.Adapter.LappamAdapter;
import id.creatodidak.djaga_swara.API.Adapter.LapserahAdapter;
import id.creatodidak.djaga_swara.API.Adapter.LapwalAdapter;
import id.creatodidak.djaga_swara.API.Adapter.LokasiAdapter;
import id.creatodidak.djaga_swara.API.Models.Draft.CekTps;
import id.creatodidak.djaga_swara.API.Models.Draft.FormC1;
import id.creatodidak.djaga_swara.API.Models.Draft.Lappam;
import id.creatodidak.djaga_swara.API.Models.Draft.Lapserah;
import id.creatodidak.djaga_swara.API.Models.Draft.Lapwal;
import id.creatodidak.djaga_swara.API.Models.Draft.Lokasi;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;

public class Draft extends AppCompatActivity {
    private List<Lokasi> lokasiList;
    private List<CekTps> cekTpsList;
    private List<Lappam> lappamList;
    private List<FormC1> formC1List;
    private List<Lapwal> lapwalList;
    private List<Lapserah> lapserahList;
    private RecyclerView lokasi, cektps, lappam, formc1, lapwal, lapserah;
    private CektpsAdapter cektpsAdapter;

    private LokasiAdapter lokasiAdapter;
    private FormC1Adapter formC1Adapter;
    private LappamAdapter lappamAdapter;
    private LapwalAdapter lapwalAdapter;
    private LapserahAdapter lapserahAdapter;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        lokasi = findViewById(R.id.lokasi);
        cektps = findViewById(R.id.cektps);
        lappam = findViewById(R.id.lappam);
        formc1 = findViewById(R.id.formc1);
        lapwal = findViewById(R.id.lapwal);
        lapserah = findViewById(R.id.lapserah);

        RecyclerView.LayoutManager lokasiLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager cektpsLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager lappamLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager formc1LayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager lapwalLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager lapserahLayoutManager = new LinearLayoutManager(this);

        lokasi.setLayoutManager(lokasiLayoutManager);
        cektps.setLayoutManager(cektpsLayoutManager);
        lappam.setLayoutManager(lappamLayoutManager);
        formc1.setLayoutManager(formc1LayoutManager);
        lapwal.setLayoutManager(lapwalLayoutManager);
        lapserah.setLayoutManager(lapserahLayoutManager);

        databaseHelper = new DatabaseHelper(this);

        lokasiList = databaseHelper.getAllLokasi();
        lokasiAdapter = new LokasiAdapter(lokasiList, this);
        lokasi.setAdapter(lokasiAdapter);

        cekTpsList = databaseHelper.getAllCekTps();
        cektpsAdapter = new CektpsAdapter(cekTpsList, this);
        cektps.setAdapter(cektpsAdapter);

        formC1List = databaseHelper.getAllFormC1();
        formC1Adapter = new FormC1Adapter(formC1List);
        formc1.setAdapter(formC1Adapter);

        lappamList = databaseHelper.getAllLappam();
        lappamAdapter = new LappamAdapter(lappamList);
        lappam.setAdapter(lappamAdapter);

        lapwalList = databaseHelper.getAllLapwal();
        lapwalAdapter = new LapwalAdapter(lapwalList);
        lapwal.setAdapter(lapwalAdapter);

        lapserahList = databaseHelper.getAllLapserah();
        lapserahAdapter = new LapserahAdapter(lapserahList);
        lapserah.setAdapter(lapserahAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Tutup koneksi ke database saat activity dihancurkan
        databaseHelper.close();
    }
}
