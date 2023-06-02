package id.creatodidak.djaga_swara.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.DataCalon;
import id.creatodidak.djaga_swara.API.Models.TpsActivity;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Cektps;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Dpt;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.FormC1;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Hasilsuara;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lappam;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lapwal;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lapserah;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.Lokasi;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tugas extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper databaseHelper;
    String id_tps;
    TextView namatps, lokasitps, dpttpss, dpttpst, dpttpsf;
    TpsList tpslist;
    ApiService apiService;
    TpsActivity tpsActivity;
    CardView cv1, cv2, cv3, cv4, cv5, cv6, cv7, cv8, cv9;

    ProgressDialog progressDialog;
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

        String text = getIntent().getStringExtra("type");

        text = text.substring(1, text.length() - 1);
        text = text.replaceAll("\\s+", "").replaceAll("-", "").replaceAll("\"", "");

        String[] array = text.split(",");

        boolean dataExists2 = false;

        for (int i = 0; i < array.length; i++) {
            String item = array[i];

            String[] tables = {item}; // Convert the table name to an array of tables
            if (databaseHelper.ceksuara2(tables, getIntent().getStringExtra("id_tps"))) {
                dataExists2 = true;
                break; // Break out of the loop if data exists in any table
            }
        }

        if (dataExists2) {
            cv5.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
        } else {
            cv5.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
        }

        switch (tpsActivity.getLokasi()) {
            case "NO":
                cv1.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv1.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
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
                cv2.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
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
                cv3.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
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
                cv4.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
            case "YES, ALL":
                cv4.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
                break;
        }

        switch (tpsActivity.getFormc1()) {
            case "NO":
                cv6.setCardBackgroundColor(Color.parseColor("#FAD5D5"));
                break;
            case "YES, LOCAL":
                cv6.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
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
                cv7.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
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
                cv8.setCardBackgroundColor(Color.parseColor("#c5e8b7"));
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
                if(tpsActivity.getDpt().equals("NO")){
                    Intent intent = new Intent(Tugas.this, Dpt.class);
                    intent.putExtra("id_tps", id_tps);
                    startActivity(intent);
                }else{
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
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
                String text = getIntent().getStringExtra("type");

                text = text.substring(1, text.length() - 1);
                text = text.replaceAll("\\s+", "").replaceAll("-", "").replaceAll("\"", "");

                String[] array = text.split(",");

                boolean dataExists = false;

                for (int i = 0; i < array.length; i++) {
                    String item = array[i];

                    String[] tables = {item}; // Convert the table name to an array of tables
                    if (databaseHelper.ceksuara2(tables, getIntent().getStringExtra("id_tps"))) {
                        dataExists = true;
                        break; // Break out of the loop if data exists in any table
                    }
                }

                if (dataExists) {
                    Intent intent = new Intent(Tugas.this, Hasilsuara.class);
                    intent.putExtra("id_tps", id_tps);
                    intent.putExtra("type", getIntent().getStringExtra("type"));
                    intent.putExtra("namatps", "TPS " + tpslist.getNomorTps() + " Desa " + tpslist.getNamaDes());
                    startActivity(intent);
                } else {
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
                break;
            case R.id.actformc1:
                if(tpsActivity.getFormc1().equals("NO")){
                    Intent intent = new Intent(Tugas.this, FormC1.class);
                    intent.putExtra("id_tps", id_tps);
                    intent.putExtra("type", getIntent().getStringExtra("type"));
                    startActivity(intent);
                }else{
                    notifikasi("INFO", "ANDA SUDAH MENGISI TUGAS INI");
                }
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
                showPilihanAksi(tpslist.getHpKpps());
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

    private void showPilihanAksi(String nomorhp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("HUBUNGI KETUA KPPS")
                .setIcon(R.drawable.logosmall)
                .setItems(new CharSequence[]{"Hub. Ketua KPPS via Telepon", "Hub. Ketua KPPS via SMS", "Hub. Ketua KPPS via Panggilan WhatsApp", "Hub. Ketua KPPS via Pesan WhatsApp"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tangani aksi sesuai pilihan pengguna
                        switch (which) {
                            case 0:
                                // Aksi panggil via seluler
                                panggilViaSeluler(nomorhp);
                                break;
                            case 1:
                                // Aksi kirim SMS
                                kirimSMS(nomorhp);
                                break;
                            case 2:
                                // Aksi panggil via WhatsApp
                                panggilViaWhatsApp(nomorhp);
                                break;
                            case 3:
                                // Aksi kirim pesan WhatsApp
                                kirimPesanWhatsApp(nomorhp);
                                break;
                        }
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void panggilViaSeluler(String nomorHp) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + nomorHp));
        startActivity(intent);
    }

    private void kirimSMS(String nomorHp) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + nomorHp));
        intent.putExtra("sms_body", "Halo, Selamat "+getWaktu()+"\nSaya...");
        startActivity(intent);
    }

    private void panggilViaWhatsApp(String nomorHp) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + nomorHp));
        startActivity(intent);
    }

    private void kirimPesanWhatsApp(String nomorHp) {
        String url = "https://api.whatsapp.com/send?phone=" + nomorHp;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private String getWaktu() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 0 && hourOfDay < 6) {
            return "Pagi";
        } else if (hourOfDay >= 6 && hourOfDay < 12) {
            return "Siang";
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            return "Sore";
        } else {
            return "Malam";
        }
    }

    private void showDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}