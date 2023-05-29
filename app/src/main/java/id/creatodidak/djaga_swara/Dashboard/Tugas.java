package id.creatodidak.djaga_swara.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lokasi;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;

public class Tugas extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper databaseHelper;
    String id_tps;
    TextView namatps, lokasitps, dpttpss, dpttpst, dpttpsf;
    TpsList tpslist;

    CardView cv1, cv2, cv3, cv4, cv5, cv6, cv7, cv8, cv9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas);
        databaseHelper = new DatabaseHelper(Tugas.this);
        id_tps = getIntent().getStringExtra("id_tps");
        tpslist = databaseHelper.getSprindetail(id_tps);
        namatps = findViewById(R.id.tslistTPS);
        lokasitps = findViewById(R.id.tslistLOC);
        dpttpst = findViewById(R.id.tslistDPTt);
        dpttpss = findViewById(R.id.tslistDPTs);
        dpttpsf = findViewById(R.id.tslistDPTf);
        cv1 = findViewById(R.id.actlokasitps);
        cv2 = findViewById(R.id.actcektps);
        cv3 = findViewById(R.id.actdpttps);
        cv4 = findViewById(R.id.actlappam);
        cv5 = findViewById(R.id.acthasilsuara);
        cv6 = findViewById(R.id.actformc1);
        cv7 = findViewById(R.id.actlapwal);
        cv8 = findViewById(R.id.actlappenyerahan);
        cv9 = findViewById(R.id.actcall);

        namatps.setText("TPS "+tpslist.getNomorTps()+" DESA "+tpslist.getNamaDes());
        lokasitps.setText(tpslist.getNamaKab()+", KEC. "+tpslist.getNamaKec()+", DESA "+tpslist.getNamaDes());
        dpttpst.setText(" : "+tpslist.getDptTetap()+" ORG");
        dpttpsf.setText(" : "+tpslist.getDptFinal()+" ORG");
        dpttpss.setText(" : "+tpslist.getDptSementara()+" ORG");

        cv1.setOnClickListener(this);
        cv2.setOnClickListener(this);
        cv3.setOnClickListener(this);
        cv4.setOnClickListener(this);
        cv5.setOnClickListener(this);
        cv6.setOnClickListener(this);
        cv7.setOnClickListener(this);
        cv8.setOnClickListener(this);
        cv9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actlokasitps:
                Intent intent = new Intent(Tugas.this, Lokasi.class);
                intent.putExtra("id_tps", id_tps);
                startActivity(intent);
                break;
            case R.id.actcektps:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actdpttps:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actlappam:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.acthasilsuara:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actformc1:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actlapwal:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actlappenyerahan:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actcall:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}