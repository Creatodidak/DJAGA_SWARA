package id.creatodidak.djaga_swara.DASHBOARDNEW;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFormC1;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.TUGASNEW.LAPCEKTPS;
import id.creatodidak.djaga_swara.TUGASNEW.LAPFORMC1;
import id.creatodidak.djaga_swara.TUGASNEW.LAPPAMTPS;
import id.creatodidak.djaga_swara.TUGASNEW.LAPPENGAWALAN;
import id.creatodidak.djaga_swara.TUGASNEW.LAPPENYERAHAN;
import id.creatodidak.djaga_swara.TUGASNEW.LAPSUARA;

public class JobSelector extends AppCompatActivity {
    CardView lapcektps, lappamtps, lapsuara, lapformc1, lappengawalan, lappenyerahan;
    String IDTPS;
    DBHelper dbHelper;
    String MERAH = "#e5342f";
    String KUNING = "#eeba56";
    String HIJAU = "#8cc24b";
    List<String> type = new ArrayList<>();
    SharedPreferences sh;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_selector);
        Intent intent = getIntent();
        IDTPS = intent.getStringExtra("IDTPS");
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        db = new DBHelper(this);

        dbHelper = new DBHelper(this);
        lapcektps = findViewById(R.id.lapcektps);
        lappamtps = findViewById(R.id.lappamtps);
        lapsuara = findViewById(R.id.lapsuara);
        lapformc1 = findViewById(R.id.lapformc1);
        lappengawalan = findViewById(R.id.lappengawalan);
        lappenyerahan = findViewById(R.id.lappenyerahan);
        String slapcektps = dbHelper.cekLapcektps(IDTPS);
        String slappamtps = dbHelper.cekLappamtps(IDTPS);
//        String slapsuara = dbHelper.(IDTPS);
//        String slapformc1 = dbHelper.(IDTPS);
        String slappengawalan = dbHelper.cekLapwal(IDTPS);
        String slappenyerahan = dbHelper.cekLapserah(IDTPS);

        if(slapcektps.equals("BELUM ADA")){
            lapcektps.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slapcektps.equals("LOCAL")){
            lapcektps.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slapcektps.equals("SERVER")){
            lapcektps.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        if(slappamtps.equals("BELUM ADA")){
            lappamtps.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slappamtps.equals("LOCAL")){
            lappamtps.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slappamtps.equals("SERVER")){
            lappamtps.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        if(slappengawalan.equals("BELUM ADA")){
            lappengawalan.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slappengawalan.equals("LOCAL")){
            lappengawalan.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slappengawalan.equals("SERVER")){
            lappengawalan.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        if(slappenyerahan.equals("BELUM ADA")){
            lappenyerahan.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slappenyerahan.equals("LOCAL")){
            lappenyerahan.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slappenyerahan.equals("SERVER")){
            lappenyerahan.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        if (sh.getBoolean("PRESIDEN", false)) {
            type.add("PRESIDEN");
        }
        if (sh.getBoolean("GUBERNUR", false)) {
            type.add("GUBERNUR");
        }
        if (sh.getBoolean("BUPATI", false)) {
            type.add("BUPATI");
        }
        if (sh.getBoolean("KADES", false)) {
            type.add("KADES");
        }
        if (sh.getBoolean("DPD-RI", false)) {
            type.add("DPD-RI");
        }
        if (sh.getBoolean("DPR-RI", false)) {
            type.add("DPR-RI");
        }
        if (sh.getBoolean("DPRD-PROV", false)) {
            type.add("DPRD-PROV");
        }
        if (sh.getBoolean("DPRD-KAB", false)) {
            type.add("DPRD-KAB");
        }

        lapcektps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPCEKTPS.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lappamtps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPPAMTPS.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lapsuara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPSUARA.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lapformc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(JobSelector.this, LAPFORMC1.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lappengawalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPPENGAWALAN.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lappenyerahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPPENYERAHAN.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        loadStatusSuara();
        loadStatusFormC1();
    }

    private void loadStatusFormC1() {
        for(String ty : type){
            MFormC1 lastData = dbHelper.getFormC1(IDTPS, ty);

            if (lastData == null) {
                lapformc1.setCardBackgroundColor(Color.parseColor(MERAH));
                break;
            } else if (lastData.getLocal() == 1) {
                lapformc1.setCardBackgroundColor(Color.parseColor(KUNING));
                break;
            } else if (lastData.getLocal() == 0) {
                lapformc1.setCardBackgroundColor(Color.parseColor(HIJAU));
            }
        }
    }

    private void loadStatusSuara() {
        for(String t : type){
            Log.d("STATUS SUARA", db.ceksuarapertype(IDTPS, t));
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA") || db.ceksuarapertype(IDTPS, t).equals("")) {
                lapsuara.setCardBackgroundColor(Color.parseColor(MERAH));
                break;
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                lapsuara.setCardBackgroundColor(Color.parseColor(KUNING));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                lapsuara.setCardBackgroundColor(Color.parseColor(HIJAU));
            }
        }
    }
}