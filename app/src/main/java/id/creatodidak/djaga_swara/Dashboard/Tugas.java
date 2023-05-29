package id.creatodidak.djaga_swara.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.TpsListOffline;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;

public class Tugas extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    String id_tps;
    TextView namatps, lokasitps, dpttpss, dpttpst, dpttpsf;
    TpsListOffline tpslist;
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

        namatps.setText("TPS "+tpslist.getNomorTps()+" DESA "+tpslist.getNamaDes());
        lokasitps.setText(tpslist.getNamaKab()+", KEC. "+tpslist.getNamaKec()+", DESA "+tpslist.getNamaDes());
        dpttpst.setText(" : "+tpslist.getDptTetap()+" ORG");
        dpttpsf.setText(" : "+tpslist.getDptFinal()+" ORG");
        dpttpss.setText(" : "+tpslist.getDptSementara()+" ORG");

    }
}