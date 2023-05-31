package id.creatodidak.djaga_swara.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.creatodidak.djaga_swara.API.Models.TpsActivity;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Cektps;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lappam;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lapwal;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lapserah;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lokasi;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;

public class Tugas extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper databaseHelper;
    String id_tps;
    TextView namatps, lokasitps, dpttpss, dpttpst, dpttpsf;
    TpsList tpslist;

    TpsActivity tpsActivity;
    CardView cv1, cv2, cv3, cv4, cv5, cv6, cv7, cv8, cv9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas);
        databaseHelper = new DatabaseHelper(Tugas.this);
        id_tps = getIntent().getStringExtra("id_tps");
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
        cv1.setOnClickListener(this);
        cv2.setOnClickListener(this);
        cv3.setOnClickListener(this);
        cv4.setOnClickListener(this);
        cv5.setOnClickListener(this);
        cv6.setOnClickListener(this);
        cv7.setOnClickListener(this);
        cv8.setOnClickListener(this);
        cv9.setOnClickListener(this);

        cekall();
    }

    private void cekall() {
        tpslist = databaseHelper.getSprindetail(id_tps);
        tpsActivity = databaseHelper.getTpsActivity(id_tps);

        namatps.setText("TPS "+tpslist.getNomorTps()+" DESA "+tpslist.getNamaDes());
        lokasitps.setText(tpslist.getNamaKab()+", KEC. "+tpslist.getNamaKec()+", DESA "+tpslist.getNamaDes());
        dpttpst.setText(" : "+tpslist.getDptTetap()+" ORG");
        dpttpsf.setText(" : "+tpslist.getDptFinal()+" ORG");
        dpttpss.setText(" : "+tpslist.getDptSementara()+" ORG");

        switch (tpsActivity.getLokasi()) {
            case "NO":
                cv1.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv1.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv1.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getCekTps()) {
            case "NO":
                cv2.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv2.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv2.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getDpt()) {
            case "NO":
                cv3.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv3.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv3.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getLappam()) {
            case "NO":
                cv4.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv4.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv4.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getLaphasil()) {
            case "NO":
                cv5.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv5.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv5.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getFormc1()) {
            case "NO":
                cv6.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv6.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv6.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getLapwal()) {
            case "NO":
                cv7.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv7.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv7.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getLapserah()) {
            case "NO":
                cv8.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv8.setCardBackgroundColor(Color.parseColor("#FFEFC8"));
                break;
            case "YES, ALL":
                cv8.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actlokasitps:
                if(tpsActivity.getLokasi().equals("NO")){
                    Intent intent = new Intent(Tugas.this, Lokasi.class);
                    intent.putExtra("id_tps", id_tps);
                    startActivity(intent);
                }else{
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
                break;
            case R.id.actcektps:
                if(tpsActivity.getCekTps().equals("NO")){
                    Intent intent = new Intent(Tugas.this, Cektps.class);
                    intent.putExtra("id_tps", id_tps);
                    intent.putExtra("judul", "Pengecekan TPS "+tpslist.getNomorTps()+" DESA "+tpslist.getNamaDes());
                    startActivity(intent);
                }else{
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
                break;
            case R.id.actdpttps:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actlappam:
                if(tpsActivity.getLappam().equals("NO")){
                    Intent intent = new Intent(Tugas.this, Lappam.class);
                    intent.putExtra("id_tps", id_tps);
                    intent.putExtra("judul", "Pengamanan Pemungutan Suara di TPS "+tpslist.getNomorTps()+" DESA "+tpslist.getNamaDes());
                    startActivity(intent);
                }else{
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
                break;
            case R.id.acthasilsuara:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actformc1:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actlapwal:
                if(tpsActivity.getLapwal().equals("NO")){
                    Intent intent = new Intent(Tugas.this, Lapwal.class);
                    intent.putExtra("id_tps", id_tps);
                    intent.putExtra("judul", "Pengawalan Kotak Suara TPS "+tpslist.getNomorTps()+" DESA "+tpslist.getNamaDes()+" dari KPPS ke PPS Desa "+tpslist.getNamaDes());
                    startActivity(intent);
                }else{
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
                break;
            case R.id.actlappenyerahan:
                if(tpsActivity.getLapserah().equals("NO")){
                    Intent intent = new Intent(Tugas.this, Lapserah.class);
                    intent.putExtra("id_tps", id_tps);
                    intent.putExtra("judul", "Penyerahan Kotak Suara TPS "+tpslist.getNomorTps()+" DESA "+tpslist.getNamaDes()+" dari KPPS kepada PPS Desa "+tpslist.getNamaDes());
                    startActivity(intent);
                }else{
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
                break;
            case R.id.actcall:
                Toast.makeText(this, "Fungsi Segera tersedia", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void notifikasi(String title, String msg) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Tugas.this);
        builder.setTitle(title)
                .setMessage(msg)
                .setIcon(R.drawable.logosmall)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cekall();
    }
}