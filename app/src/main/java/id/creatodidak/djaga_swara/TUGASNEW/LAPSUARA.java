package id.creatodidak.djaga_swara.TUGASNEW;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.NEWADAPTER.GridSuaraAdapter;
import id.creatodidak.djaga_swara.API.NEWMODEL.GridLayoutData;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;

public class LAPSUARA extends AppCompatActivity {
    SharedPreferences sh;
    DBHelper db;
    Endpoint endpoint;
    CardView cvForm, cvDPT;
    Button btInputDpt;
    String IDTPS;
    CardView PRESIDEN, GUBERNUR, BUPATI, KADES, DPDRI, DPRRI, DPRDPROV, DPRDKAB;
    TextView statusPilpres;
    TextView statusPilgub;
    TextView statusPilbup;
    TextView statusPilkades;
    TextView statusDpdri;
    TextView statusDprri;
    TextView statusDprdprov;
    TextView statusDprdkab;
    List<String> type = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapsuara);
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        db = new DBHelper(this);
        endpoint = ApiClient.getClient().create(Endpoint.class);

        Intent i = getIntent();
        IDTPS = i.getStringExtra("IDTPS");

        cvDPT = findViewById(R.id.cvDPT);
        cvForm = findViewById(R.id.cvForm);
        btInputDpt = findViewById(R.id.btInputDPT);

        PRESIDEN = findViewById(R.id.cvPresiden);
        GUBERNUR = findViewById(R.id.cvGubernur);
        BUPATI = findViewById(R.id.cvBupati);
        KADES = findViewById(R.id.cvKades);
        DPDRI = findViewById(R.id.cvDpdRI);
        DPRRI = findViewById(R.id.cvDprRI);
        DPRDPROV = findViewById(R.id.cvDprdProv);
        DPRDKAB = findViewById(R.id.cvDprdKab);
        statusPilpres = findViewById(R.id.statusPilpres);
        statusPilgub = findViewById(R.id.statusPilgub);
        statusPilbup = findViewById(R.id.statusPilbup);
        statusPilkades = findViewById(R.id.statusPilkades);
        statusDpdri = findViewById(R.id.statusDpdri);
        statusDprri = findViewById(R.id.statusDprri);
        statusDprdprov = findViewById(R.id.statusDprdprov);
        statusDprdkab = findViewById(R.id.statusDprdkab);

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

        for (String t : type) {
            checkData(t);
        }

        PRESIDEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARAREGULER.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });
        GUBERNUR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARAREGULER.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });
        BUPATI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARAREGULER.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });
        KADES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARAREGULER.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });
        DPDRI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARAREGULER.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });
        DPRRI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARALEGISLATIF.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });
        DPRDPROV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARALEGISLATIF.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });
        DPRDKAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "";
                Intent i = new Intent(LAPSUARA.this, SUARALEGISLATIF.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });

    }

    private void checkData(String t) {
        if (t.equals("PRESIDEN")) {
            PRESIDEN.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilpres.setText("BELUM INPUT DATA SUARA");
                statusPilpres.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilpres.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilpres.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilpres.setText("SELESAI");
                statusPilpres.setTextColor(Color.GREEN);
            }
        } else if (t.equals("GUBERNUR")) {
            GUBERNUR.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilgub.setText("BELUM INPUT DATA SUARA");
                statusPilgub.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilgub.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilgub.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilgub.setText("SELESAI");
                statusPilgub.setTextColor(Color.GREEN);
            }
        } else if (t.equals("BUPATI")) {
            BUPATI.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilbup.setText("BELUM INPUT DATA SUARA");
                statusPilbup.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilbup.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilbup.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilbup.setText("SELESAI");
                statusPilbup.setTextColor(Color.GREEN);
            }
        } else if (t.equals("KADES")) {
            KADES.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilkades.setText("BELUM INPUT DATA SUARA");
                statusPilkades.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilkades.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilkades.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilkades.setText("SELESAI");
                statusPilkades.setTextColor(Color.GREEN);
            }
        } else if (t.equals("DPD-RI")) {
            DPDRI.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, "dpdri").equals("BELUM ADA")) {
                statusDpdri.setText("BELUM INPUT DATA SUARA");
                statusDpdri.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, "dpdri").equals("LOCAL")) {
                statusDpdri.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDpdri.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, "dpdri").equals("SERVER")) {
                statusDpdri.setText("SELESAI");
                statusDpdri.setTextColor(Color.GREEN);
            }
        } else if (t.equals("DPR-RI")) {
            t = "dprri";
            DPRRI.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusDprri.setText("BELUM INPUT DATA SUARA");
                statusDprri.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusDprri.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDprri.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusDprri.setText("SELESAI");
                statusDprri.setTextColor(Color.GREEN);
            }
        } else if (t.equals("DPRD-PROV")) {
            t = "dprdprov";
            DPRDPROV.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusDprdprov.setText("BELUM INPUT DATA SUARA");
                statusDprdprov.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusDprdprov.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDprdprov.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusDprdprov.setText("SELESAI");
                statusDprdprov.setTextColor(Color.GREEN);
            }
        } else if (t.equals("DPRD-KAB")) {
            t = "dprdkab";
            DPRDKAB.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusDprdkab.setText("BELUM INPUT DATA SUARA");
                statusDprdkab.setTextColor(Color.RED);
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusDprdkab.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDprdkab.setTextColor(Color.YELLOW);
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusDprdkab.setText("SELESAI");
                statusDprdkab.setTextColor(Color.GREEN);
            }
        }
    }
}