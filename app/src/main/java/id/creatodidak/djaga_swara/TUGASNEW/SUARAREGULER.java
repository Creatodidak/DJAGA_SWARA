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
import id.creatodidak.djaga_swara.API.NEWADAPTER.SuaraRegAdp;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MKoleksiSuara;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListbupatiItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListgubernurItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListkadesItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListpresidenItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListtpsItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.MDataSuaraReguler;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.UPDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SUARAREGULER extends AppCompatActivity {
    String IDTPS;
    String TYPE;
    RecyclerView rv;
    TextView tvJudulDataSuara;
    DBHelper db;
    SharedPreferences sh;
    Endpoint endpoint;
    List<ListpresidenItem> listpresidenItems = new ArrayList<>();
    List<ListgubernurItem> listgubernurItems = new ArrayList<>();
    List<ListbupatiItem> listbupatiItems = new ArrayList<>();
    List<ListkadesItem> listkadesItems = new ArrayList<>();
    SuaraRegAdp adp;
    List<MDataSuaraReguler> data = new ArrayList<>();
    List<MDataSuaraReguler> datasuara = new ArrayList<>();
    LinearLayoutManager lm;
    Button btnSendSuara;
    EditText etSuaratsah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suarareguler);
        Intent i = getIntent();
        IDTPS = i.getStringExtra("IDTPS");
        TYPE = i.getStringExtra("TYPE");
        db = new DBHelper(this);
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        adp = new SuaraRegAdp(this, data);
        lm = new LinearLayoutManager(this);

        rv = findViewById(R.id.rvCalon);
        tvJudulDataSuara = findViewById(R.id.tvJudulDataSuara);
        btnSendSuara = findViewById(R.id.btSendSuara);
        etSuaratsah = findViewById(R.id.etSuaratsah);

        rv.setLayoutManager(lm);
        rv.setAdapter(adp);
        rv.setItemViewCacheSize(200);
        rv.setDrawingCacheEnabled(true);
        loadData(IDTPS, TYPE);

        for(MDataSuaraReguler x : db.getCalonReg(TYPE.toLowerCase(), IDTPS)){
            Log.d("JSUARA", "ok");
            if(x.getjSuara().equals("")){
                btnSendSuara.setText("KIRIM DATA");
                break;
            }else{
                if(x.getLocal() == 1){
                    btnSendSuara.setText("KIRIM DRAFT");
                    break;
                }else{
                    btnSendSuara.setVisibility(View.GONE);
                }
            }
        }

        btnSendSuara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){

                    if(btnSendSuara.getText().equals("KIRIM DATA")){
                        CDialog.up(
                                SUARAREGULER.this,
                                "Konfirmasi",
                                "Anda yakin akan mengirimkan data ini?",
                                true, false, false,
                                "BATAL", "KIRIM", "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {
                                        alert.dismiss();
                                        saveDataToDb();
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
                    }else if(btnSendSuara.getText().equals("KIRIM DRAFT")){
                        uploadToServer();
                    }


                }else{
                    CDialog.up(
                            SUARAREGULER.this,
                            "Peringatan",
                            "Invalid Data",
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
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData(String idtps, String type) {
        ListtpsItem tps = db.getRincianTpsById(idtps);
        if(type.equals("PRESIDEN")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILPRES TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }else if(type.equals("GUBERNUR")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILGUB TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }else if(type.equals("BUPATI")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILBUP TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }else if(type.equals("KADES")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILKADES TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }else if(type.equals("DPDRI")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PIL. DPD - RI TPS "+tps.getNomorTps()+" DESA "+ sh.getString("KECAMATAN TPS", null));
        }

        String sTidakSah = db.getSuaraTidakSah(IDTPS, TYPE.toLowerCase());
        if(!sTidakSah.equals("")){
            etSuaratsah.setText(sTidakSah);
            etSuaratsah.setEnabled(false);
        }

        data.addAll(db.getCalonReg(type.toLowerCase(), IDTPS));
        adp.notifyDataSetChanged();
    }

    private void saveDataToDb(){

        for (int i = 0; i < data.size(); i++){
            View view = rv.getChildAt(i);
            if(view != null) { //jika view tidak null select edittext
                EditText reslt = (EditText) view.findViewById(R.id.etSuara);

                db.saveSuara(IDTPS, data.get(i).getIdcalon(), reslt.getText().toString());

                Log.d(data.get(i).getIdcalon(), reslt.getText().toString());
            }
        }

        db.saveSuaraTsah(IDTPS, TYPE.toLowerCase(), etSuaratsah.getText().toString());
        uploadToServer();
    }

    private void uploadToServer() {
        AlertDialog alerts = UPDialog.up(this, "Wait");
        alerts.show();
        List<MKoleksiSuara> data = new ArrayList<>();

        MKoleksiSuara tidaksah = new MKoleksiSuara();
        tidaksah.setIdTps(IDTPS);
        tidaksah.setKategori("SUARA TIDAK SAH");
        tidaksah.setType(TYPE);
        tidaksah.setJumlah(db.getSuaraTidakSah(IDTPS, TYPE.toLowerCase()));
        data.add(tidaksah);
        for(MDataSuaraReguler x : db.getCalonReg(TYPE.toLowerCase(), IDTPS)){
            MKoleksiSuara suarasah = new MKoleksiSuara();
            suarasah.setIdTps(IDTPS);
            suarasah.setKategori("SUARA SAH");
            suarasah.setType(TYPE);
            suarasah.setIdCalon(x.getIdcalon());
            suarasah.setJumlah(x.getjSuara());
            data.add(suarasah);
        }

        Call<MResponseServer> call = endpoint.uploadSuara(data);
        call.enqueue(new Callback<MResponseServer>() {
            @Override
            public void onResponse(Call<MResponseServer> call, Response<MResponseServer> response) {
                if(response.body().isStatus()){
                    alerts.dismiss();

                    for(MKoleksiSuara i : data){
                        db.updateStatusSuara(i.getKategori(), i.getType().toLowerCase(), i.getIdTps(), i.getIdCalon());
                    }

                    CDialog.up(
                            SUARAREGULER.this,
                            "Informasi",
                            "Data Suara Berhasil Diupload!",
                            false, false, false,
                            "", "LANJUTKAN", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    SUARAREGULER.this.recreate();
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
                            SUARAREGULER.this,
                            "Informasi",
                            "Server Tidak Meresponses!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                            false, false, false,
                            "", "MENGERTI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    SUARAREGULER.this.recreate();
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
                        SUARAREGULER.this,
                        "Informasi",
                        "Server Tidak Meresponsex!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                        false, false, false,
                        "", "MENGERTI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                SUARAREGULER.this.recreate();
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

    private boolean checkData(){
        boolean result = false;

        for (int i = 0; i < data.size(); i++){
            View view = rv.getChildAt(i);
            if(view != null) { //jika view tidak null select edittext
                EditText reslt = (EditText) view.findViewById(R.id.etSuara);

                /**
                 *
                 * Jika editext tidak null dan tidak kosong maka true, dsb..
                 */
                if (reslt != null && !reslt.getText().toString().isEmpty()) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }


    private boolean checkData_old(){
        boolean result = false;
        for (int i = 0; i < data.size(); i++){

            View v = rv.findViewWithTag(data.get(i).getIdcalon());
            EditText et = v.findViewById(R.id.etSuara);

            if (et != null && !TextUtils.isEmpty(et.getText())) {
                result = true;
            } else {
                result = false;
                break;
            }

        }

        return result;
    }

    @Override
    public void onBackPressed() {
        if (btnSendSuara.getVisibility() == View.GONE) {
            super.onBackPressed();
            Intent intent = new Intent(SUARAREGULER.this, LAPSUARA.class);
            intent.putExtra("IDTPS", IDTPS);
            startActivity(intent);
            finish();
        } else {
            String dialogTitle = "Konfirmasi";
            String dialogMessage = "Masih ada data yang belum dikirim ke server, keluar?";
            String cancelText = "BATAL";
            String exitText = "KELUAR";

            CDialog.up(
                    SUARAREGULER.this,
                    dialogTitle,
                    dialogMessage,
                    true, false, false,
                    cancelText, exitText, "",
                    new CDialog.AlertDialogListener() {
                        @Override
                        public void onOpt1(AlertDialog alert) {
                            Intent intent = new Intent(SUARAREGULER.this, LAPSUARA.class);
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