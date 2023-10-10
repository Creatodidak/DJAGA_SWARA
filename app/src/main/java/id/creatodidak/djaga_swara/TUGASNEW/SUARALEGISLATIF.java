package id.creatodidak.djaga_swara.TUGASNEW;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.NEWADAPTER.NonScrollableLayoutManager;
import id.creatodidak.djaga_swara.API.NEWADAPTER.PartaiAdapter;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MPartai;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;

public class SUARALEGISLATIF extends AppCompatActivity {
    Button btnSendSuara;
    String IDTPS, TYPE;
    RecyclerView rvCalon;
    EditText etSuaraTidakSah;
    PartaiAdapter adp;
    LinearLayoutManager lm;
    List<MPartai> data = new ArrayList<>();
    DBHelper db;
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

        db = new DBHelper(this);

        adp = new PartaiAdapter(this, data, TYPE);
        lm = new LinearLayoutManager(this);

        rvCalon.setLayoutManager(lm);
        rvCalon.setAdapter(adp);

        loadDataPartai();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadDataPartai() {
        data.addAll(db.getPartai(TYPE));
        adp.notifyDataSetChanged();
        Log.i("JUMLAH PARTAI "+TYPE+": ", String.valueOf(data.size()));
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