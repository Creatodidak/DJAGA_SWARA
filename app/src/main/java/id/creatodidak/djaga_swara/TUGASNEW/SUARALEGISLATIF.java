package id.creatodidak.djaga_swara.TUGASNEW;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.NEWADAPTER.PartaiAdapter;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFigur;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MKoleksiSuara;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MPartai;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListtpsItem;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.UPDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SUARALEGISLATIF extends AppCompatActivity {
    Button btnSendSuara;
    String IDTPS, TYPE;
    RecyclerView rvCalon;
    EditText etSuaraTidakSah;
    PartaiAdapter adp;
    LinearLayoutManager lm;
    List<MPartai> data = new ArrayList<>();
    DBHelper db;
    TextView tvJudulDataSuara;
    SharedPreferences sh;
    Endpoint endpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suaralegislatif);
        Intent i = getIntent();
        IDTPS = i.getStringExtra("IDTPS");
        TYPE = i.getStringExtra("TYPE");
        btnSendSuara = findViewById(R.id.btSendSuara);
        rvCalon = findViewById(R.id.rvCalon);
        etSuaraTidakSah = findViewById(R.id.etSuaratsah);
        tvJudulDataSuara = findViewById(R.id.tvJudulDataSuara);
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        db = new DBHelper(this);
        adp = new PartaiAdapter(this, data, TYPE, IDTPS);

        lm = new LinearLayoutManager(this);
        rvCalon.setLayoutManager(lm);
        rvCalon.setAdapter(adp);

        rvCalon.setHasFixedSize(true);
        rvCalon.setNestedScrollingEnabled(true);
        rvCalon.setItemViewCacheSize(200);
        rvCalon.setDrawingCacheEnabled(true);

        if(!TextUtils.isEmpty(db.getSuaraTidakSah(IDTPS, TYPE.toLowerCase()))){
            etSuaraTidakSah.setText(db.getSuaraTidakSah(IDTPS, TYPE.toLowerCase()));
            etSuaraTidakSah.setEnabled(false);
        }
        String status = db.statusLocalLegislatif(IDTPS, TYPE.toLowerCase());
        if(status.equals("BELUM ADA")){
            btnSendSuara.setText("KIRIM DATA");
            btnSendSuara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(etSuaraTidakSah.getText())){
                        db.saveSuaraTsah(IDTPS, TYPE.toLowerCase(), etSuaraTidakSah.getText().toString());
                        int totaldata = 0;
                        for(MPartai i : data){
                            View vx = rvCalon.findViewWithTag(i.getIdPartai());
                            Button btn = vx.findViewById(R.id.btnSimpanDataPerPartai);

                            if(btn.getVisibility() == View.GONE){
                                totaldata++;
                            }else{
                                CDialog.up(
                                        SUARALEGISLATIF.this,
                                        "Peringatan",
                                        "Lengkapi seluruh data pada data suara partai " + i.getNama().replace("PARTAI ", "") + "!",
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
                                break;
                            }
                        }

                        if(totaldata == data.size()){
                            kirimDataKeServer();
                        }
                    }else{
                        CDialog.up(
                                SUARALEGISLATIF.this,
                                "Peringatan",
                                "Suara Tidak Sah Wajib Diisi!\nisi dengan 0 jika tidak ada suara tidak sah!",
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
        }else if(status.equals("LOCAL")){
            btnSendSuara.setText("KIRIM DRAFT");
            btnSendSuara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kirimDataKeServer();
                }
            });
        }else if(status.equals("SERVER")){
            btnSendSuara.setVisibility(View.GONE);
        }

        loadDataPartai();
        getRincianTPS();
    }

    private void getRincianTPS() {
        ListtpsItem tps = db.getRincianTpsById(IDTPS);
        if(TYPE.equals("DPRRI")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILEG DPR - RI TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }else if(TYPE.equals("DPRDPROV")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILEG DPRD - PROVINSI TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }else if(TYPE.equals("DPRDKAB")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILEG DPRD - KABUPATEN TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadDataPartai() {
        data.addAll(db.getPartai(TYPE, IDTPS));
        adp.notifyDataSetChanged();
        Log.i("JUMLAH PARTAI "+TYPE+": ", String.valueOf(data.size()));
    }

    private void kirimDataKeServer(){
        AlertDialog alerts = UPDialog.up(this, "Wait");
        alerts.show();
        
        String types = "";
        String ty = "";

        if (TYPE.equals("DPRRI")) {
            types = "DPR-RI";
            ty = "nasional";
        } else if (TYPE.equals("DPRDPROV")) {
            types = "DPRD-PROV";
            ty = "provinsi";
        } else if (TYPE.equals("DPRDKAB")) {
            types = "DPRD-KAB";
            ty = "kabupaten";
        }

        List<MPartai> partai = db.getPartai(TYPE, IDTPS);
        List<MFigur> figur = new ArrayList<>();

        List<MKoleksiSuara> suara = new ArrayList<>();
        String suaraTidakSah = db.getSuaraTidakSah(IDTPS, TYPE.toLowerCase());
        MKoleksiSuara tidaksah = new MKoleksiSuara();
        tidaksah.setIdTps(IDTPS);
        tidaksah.setKategori("SUARA TIDAK SAH");
        tidaksah.setType(TYPE);
        tidaksah.setJumlah(suaraTidakSah);
        suara.add(tidaksah);

        for(MPartai i : partai){
            figur.addAll(db.getFigurList(TYPE, i.getIdPartai(), IDTPS));
            MKoleksiSuara suarapartai = new MKoleksiSuara();
            suarapartai.setKategori("SUARA PARTAI");
            suarapartai.setType("SUARA PARTAI "+types);
            suarapartai.setIdCalon(i.getIdPartai());
            suarapartai.setJumlah(i.getSuara());
            suarapartai.setIdTps(IDTPS);
            suara.add(suarapartai);
        }

        for (MFigur i : figur){
            MKoleksiSuara suarasah = new MKoleksiSuara();
            suarasah.setIdTps(IDTPS);
            suarasah.setKategori("SUARA SAH");
            suarasah.setType(TYPE);
            suarasah.setIdCalon(i.getIdCalon());
            suarasah.setJumlah(i.getSuara());
            suara.add(suarasah);
        }

        Call<MResponseServer> call = endpoint.uploadSuara(suara);
        String finalTy = ty;
        call.enqueue(new Callback<MResponseServer>() {
            @Override
            public void onResponse(Call<MResponseServer> call, Response<MResponseServer> response) {
                if(response.body().isStatus()){
                    alerts.dismiss();

                    for(MPartai i : partai){
                        db.updSuaraPartai(i.getIdPartai(), finalTy, IDTPS);
                    }
                    db.updateStatusSuara("SUARA TIDAK SAH", TYPE.toLowerCase(), IDTPS, "");
                    for(MFigur i : figur){
                        db.updSuaraLegislatif(TYPE.toLowerCase(), IDTPS, i.getIdCalon());
                    }

                    CDialog.up(
                            SUARALEGISLATIF.this,
                            "Informasi",
                            "Data Suara Berhasil Diupload!",
                            false, false, false,
                            "", "LANJUTKAN", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    SUARALEGISLATIF.this.recreate();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {

                                }
                            }
                    ).show();
                }else{
                    alerts.dismiss();
                    CDialog.up(
                            SUARALEGISLATIF.this,
                            "Informasi",
                            "Server Tidak Meresponses!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                            false, false, false,
                            "", "MENGERTI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    SUARALEGISLATIF.this.recreate();
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
                        SUARALEGISLATIF.this,
                        "Informasi",
                        "Server Tidak Meresponsex!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                        false, false, false,
                        "", "MENGERTI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                SUARALEGISLATIF.this.recreate();
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

    @Override
    public void onBackPressed() {
        if (btnSendSuara.getVisibility() == View.GONE) {
            super.onBackPressed();
            Intent intent = new Intent(SUARALEGISLATIF.this, LAPSUARA.class);
            intent.putExtra("IDTPS", IDTPS);
            startActivity(intent);
            finish();
        } else {
            String dialogTitle = "Konfirmasi";
            String dialogMessage = "Masih ada data yang belum dikirim ke server, keluar?";
            String cancelText = "BATAL";
            String exitText = "KELUAR";

            CDialog.up(
                    SUARALEGISLATIF.this,
                    dialogTitle,
                    dialogMessage,
                    true, false, false,
                    cancelText, exitText, "",
                    new CDialog.AlertDialogListener() {
                        @Override
                        public void onOpt1(AlertDialog alert) {
                            Intent intent = new Intent(SUARALEGISLATIF.this, LAPSUARA.class);
                            intent.putExtra("IDTPS", IDTPS);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onOpt2(AlertDialog alert) {
                            // Tidak ada tindakan yang diambil jika tombol KELUAR ditekan
                        }

                        @Override
                        public void onCancel(AlertDialog alert) {
                            alert.dismiss();
                        }
                    }
            ).show();
        }
    }
}