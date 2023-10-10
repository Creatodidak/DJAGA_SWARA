package id.creatodidak.djaga_swara.TUGASNEW;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.MDptFinal;
import id.creatodidak.djaga_swara.DASHBOARDNEW.JobSelector;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.UPDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    EditText dptfinal;
    List<String> type = new ArrayList<>();
    TextView judul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapsuara);
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        db = new DBHelper(this);
        db.init();
        endpoint = ApiClient.getClient().create(Endpoint.class);

        Intent i = getIntent();
        IDTPS = i.getStringExtra("IDTPS");

        cvDPT = findViewById(R.id.cvDPT);
        cvForm = findViewById(R.id.cvForm);
        btInputDpt = findViewById(R.id.btInputDPT);
        dptfinal = findViewById(R.id.etDPT);
        judul = findViewById(R.id.textView53);

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

        MDptFinal dpt = db.getDptFinal(IDTPS);

        if(dpt == null){
            cvDPT.setVisibility(View.VISIBLE);
            btInputDpt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(dptfinal.getText())){
                        CDialog.up(
                                LAPSUARA.this,
                                "Konfirmasi",
                                "Anda yakin akan mengirimkan data ini?",
                                true, false, false,
                                "BATAL", "KIRIM", "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {
                                        alert.dismiss();
                                        saveDpt(IDTPS, dptfinal.getText().toString());
                                    }

                                    @Override
                                    public void onOpt2(AlertDialog alert) {

                                    }

                                    @Override
                                    public void onCancel(AlertDialog alert) {
                                        alert.dismiss();
                                    }
                                }
                        ).show();
                    }else{
                        CDialog.up(
                                LAPSUARA.this,
                                "Peringatan",
                                "Masukan jumlah DPT!",
                                false, false, false,
                                "", "PERBAIKI", "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {
                                        alert.dismiss();
                                    }

                                    @Override
                                    public void onOpt2(AlertDialog alert) {

                                    }

                                    @Override
                                    public void onCancel(AlertDialog alert) {

                                    }
                                }
                        ).show();
                    }
                }
            });
        }else {
            if(dpt.getLocal() == 1){
                cvDPT.setVisibility(View.VISIBLE);
                btInputDpt.setText("KIRIM DRAFT");
                dptfinal.setText(dpt.getDptFinal());
                dptfinal.setEnabled(false);
                judul.setText("Silahkan kirimkan draft data DPT ke server dengan menekan tombol KIRIM DRAFT!");
                btInputDpt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kirimDptKeServer();
                    }
                });
            }else if(dpt.getLocal() == 0){
                cvDPT.setVisibility(View.GONE);
                cvForm.setVisibility(View.VISIBLE);
            }

            for (String t : type) {
                checkData(t);
            }
        }

        PRESIDEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = "PRESIDEN";
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
                String t = "GUBERNUR";
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
                String t = "BUPATI";
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
                String t = "KADES";
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
                String t = "DPDRI";
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
                String t = "DPRRI";
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
                String t = "DPRDPROV";
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
                String t = "DPRDKAB";
                Intent i = new Intent(LAPSUARA.this, SUARALEGISLATIF.class);
                i.putExtra("IDTPS", IDTPS);
                i.putExtra("TYPE", t);
                startActivity(i);
                finish();
            }
        });

    }

    private void saveDpt(String idtps, String dptFinal) {
        if(db.saveDptFinal(idtps, dptFinal)){
            kirimDptKeServer();
        }
    }

    private void kirimDptKeServer() {
        AlertDialog alerts = UPDialog.up(this, "Wait");
        alerts.show();
        
        MDptFinal dpt = db.getDptFinal(IDTPS);
        Call<MResponseServer> call = endpoint.uploadDpt(IDTPS, dpt.getDptFinal());

        call.enqueue(new Callback<MResponseServer>() {
            @Override
            public void onResponse(Call<MResponseServer> call, Response<MResponseServer> response) {
                if (response.body().isStatus() && response.body() != null && response.isSuccessful()) {
                    if (db.updDptFinal(IDTPS)) {
                        alerts.dismiss();
                        CDialog.up(
                                LAPSUARA.this,
                                "Informasi",
                                "DPT Berhasil Di Update!",
                                false, false, false,
                                "", "LANJUTKAN", "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {
                                        alert.dismiss();
                                        LAPSUARA.this.recreate();
                                    }

                                    @Override
                                    public void onOpt2(AlertDialog alert) {

                                    }

                                    @Override
                                    public void onCancel(AlertDialog alert) {

                                    }
                                }
                        ).show();
                    }
                } else {
                    alerts.dismiss();
                    CDialog.up(
                            LAPSUARA.this,
                            "Informasi",
                            "Server Tidak Meresponse!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                            false, false, false,
                            "", "MENGERTI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    LAPSUARA.this.recreate();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {

                                }
                            }
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<MResponseServer> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        LAPSUARA.this,
                        "Informasi",
                        "Periksa Jaringan Internet anda!\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                        false, false, false,
                        "", "MENGERTI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                LAPSUARA.this.recreate();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {

                            }
                        }
                ).show();
            }
        });

    }

    private void checkData(String t) {
        if (t.equals("PRESIDEN")) {
            PRESIDEN.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilpres.setText("BELUM INPUT DATA SUARA");
                statusPilpres.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilpres.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilpres.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilpres.setText("SELESAI");
                statusPilpres.setTextColor(Color.parseColor("#8cc24b"));
            }
        } else if (t.equals("GUBERNUR")) {
            GUBERNUR.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilgub.setText("BELUM INPUT DATA SUARA");
                statusPilgub.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilgub.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilgub.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilgub.setText("SELESAI");
                statusPilgub.setTextColor(Color.parseColor("#8cc24b"));
            }
        } else if (t.equals("BUPATI")) {
            BUPATI.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilbup.setText("BELUM INPUT DATA SUARA");
                statusPilbup.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilbup.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilbup.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilbup.setText("SELESAI");
                statusPilbup.setTextColor(Color.parseColor("#8cc24b"));
            }
        } else if (t.equals("KADES")) {
            KADES.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusPilkades.setText("BELUM INPUT DATA SUARA");
                statusPilkades.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusPilkades.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusPilkades.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusPilkades.setText("SELESAI");
                statusPilkades.setTextColor(Color.parseColor("#8cc24b"));
            }
        } else if (t.equals("DPD-RI")) {
            DPDRI.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, "dpdri").equals("BELUM ADA")) {
                statusDpdri.setText("BELUM INPUT DATA SUARA");
                statusDpdri.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, "dpdri").equals("LOCAL")) {
                statusDpdri.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDpdri.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, "dpdri").equals("SERVER")) {
                statusDpdri.setText("SELESAI");
                statusDpdri.setTextColor(Color.parseColor("#8cc24b"));
            }
        } else if (t.equals("DPR-RI")) {
            t = "dprri";
            DPRRI.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusDprri.setText("BELUM INPUT DATA SUARA");
                statusDprri.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusDprri.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDprri.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusDprri.setText("SELESAI");
                statusDprri.setTextColor(Color.parseColor("#8cc24b"));
            }
        } else if (t.equals("DPRD-PROV")) {
            t = "dprdprov";
            DPRDPROV.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusDprdprov.setText("BELUM INPUT DATA SUARA");
                statusDprdprov.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusDprdprov.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDprdprov.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusDprdprov.setText("SELESAI");
                statusDprdprov.setTextColor(Color.parseColor("#8cc24b"));
            }
        } else if (t.equals("DPRD-KAB")) {
            t = "dprdkab";
            DPRDKAB.setVisibility(View.VISIBLE);
            if (db.ceksuarapertype(IDTPS, t).equals("BELUM ADA")) {
                statusDprdkab.setText("BELUM INPUT DATA SUARA");
                statusDprdkab.setTextColor(Color.parseColor("#e5342f"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("LOCAL")) {
                statusDprdkab.setText("DATA SUARA BELUM DIKIRIM KE SERVER");
                statusDprdkab.setTextColor(Color.parseColor("#eeba56"));
            } else if (db.ceksuarapertype(IDTPS, t).equals("SERVER")) {
                statusDprdkab.setText("SELESAI");
                statusDprdkab.setTextColor(Color.parseColor("#8cc24b"));
            }
        }
    }

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(LAPSUARA.this, JobSelector.class);
            intent.putExtra("IDTPS", IDTPS);
            startActivity(intent);
            finish();
    }
}