package id.creatodidak.djaga_swara.Dashboard.TugasForm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dpt extends AppCompatActivity {

    EditText dpts, dptt, dptf, ketdptf;
    Button svdpt;
    DatabaseHelper databaseHelper;
    ApiService apiService;
    String dpt_final, keterangan;
    TpsList tpsList;
    ProgressDialog progressDialog;
    Boolean cek, upd, add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpt);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            dpts = findViewById(R.id.Dpts);
            dptt = findViewById(R.id.Dptt);
            dptf = findViewById(R.id.Dptf);
            ketdptf = findViewById(R.id.ketDptf);
            svdpt = findViewById(R.id.svDptf);

            databaseHelper = new DatabaseHelper(this);

            tpsList = databaseHelper.getDpt(getIntent().getStringExtra("id_tps"));

            dpts.setText(tpsList.getDptSementara());
            dptt.setText(tpsList.getDptTetap());

            svdpt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dptf.getText().toString().trim().isEmpty()) {
                        notifikasi("WAJIB DIISI!", "JUMLAH DPT FINAL HARUS DI ISI!", true, false);
                    } else {
                        if (ketdptf.getText().toString().trim().isEmpty()) {
                            notifikasi("WAJIB DIISI!", "KETERANGAN JUMLAH DPT FINAL HARUS DI ISI!", true, false);
                        } else {
                            showprogress("UPDATE DATA TPS...");
                            svDpt();
                        }
                    }
                }
            });
        }
    }

    private void svDpt() {
        dpt_final = dptf.getText().toString().trim();
        keterangan = ketdptf.getText().toString();
        apiService = ApiClient.getClient().create(ApiService.class);

        Call<UpdResponse> call = apiService.updatetps(getIntent().getStringExtra("id_tps"), dpt_final, keterangan);
        call.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getMsg().equals("ok")){
                        updatedb("YES, ALL");
                    }else{
                        updatedb("YES, LOCAL");
                    }
                }else{
                    updatedb("YES, LOCAL");
                }
            }

            @Override
            public void onFailure(Call<UpdResponse> call, Throwable t) {
                progressDialog.dismiss();
                updatedb("YES, LOCAL");
            }
        });

    }

    private void notifikasi(String info, String s, Boolean cancel, Boolean close) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dpt.this);
        if(cancel){
            if(close){
                builder.setTitle(info)
                        .setMessage(s)
                        .setIcon(R.drawable.logosmall)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .show();
            }else{
                builder.setTitle(info)
                        .setMessage(s)
                        .setIcon(R.drawable.logosmall)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }else{
            builder.setTitle(info)
                    .setMessage(s)
                    .setIcon(R.drawable.logosmall)
                    .setCancelable(false)
                    .show();
        }

    }
    private void showprogress(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void updatedb(String msg) {
        showprogress("Menyimpan data di local");
        cek = databaseHelper.updateTpsActivity(getIntent().getStringExtra("id_tps"), "dpt", msg);
        upd = databaseHelper.updateDpt(getIntent().getStringExtra("id_tps"), dpt_final, keterangan);

        if(cek){
            progressDialog.dismiss();
            if (msg.equals("YES, ALL")){
                if(databaseHelper.addDraftdpt(getIntent().getStringExtra("id_tps"), dpt_final, keterangan, "SERVER")){
                    if (upd){
                        notifikasi("Berhasil", "Update DPT berhasil, data telah di upload ke server", true, true);
                    }else{
                        notifikasi("Berhasil", "Update DPT berhasil, data telah di upload ke server namun gagal mengupdate database local", true, true);
                    }
                }else{
                    notifikasi("Berhasil", "Update DPT berhasil, data telah di upload ke server namun gagal menyimpan draft", true, true);
                }
            }else{
                if(databaseHelper.addDraftdpt(getIntent().getStringExtra("id_tps"), dpt_final, keterangan, "LOCAL")){
                    if (upd){
                        notifikasi("Berhasil", "Update DPT berhasil, namun data tidak diupload ke server, data tersimpan di draft", true, true);
                    }else{
                        notifikasi("Gagal", "Update DPT gagal, data tidak disimpan di draft!", true, true);
                    }
                }else{
                    notifikasi("Gagal", "Update DPT gagal, data tidak disimpan di draft!", true, true);
                }
            }
        }else{
            progressDialog.dismiss();
            notifikasi("Gagal", "Update DPT gagal, hubungi Posko!", true, true);
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Data belum dilaporkan data tidak akan tersimpan di draft dan database, tutup laporan?");
        builder.setPositiveButton("LANJUTKAN LAPORAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Ya, batalkan...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}