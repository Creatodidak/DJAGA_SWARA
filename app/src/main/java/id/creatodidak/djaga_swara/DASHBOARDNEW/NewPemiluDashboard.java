package id.creatodidak.djaga_swara.DASHBOARDNEW;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.NEWADAPTER.GridLayoutAdapter;
import id.creatodidak.djaga_swara.API.NEWMODEL.GridLayoutData;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListtpsItem;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.TextHelper;

public class NewPemiluDashboard extends AppCompatActivity {

    GridView listTps;
    TextView namaDesaTugas, periodePemilu;
    List<GridLayoutData> data = new ArrayList<>();
    GridLayoutAdapter adapter;
    String PERIODE;
    SharedPreferences sh;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pemilu_dashboard);
        Intent intent = getIntent();
        PERIODE = intent.getStringExtra("PERIODE");
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        dbHelper = new DBHelper(this);
        listTps = findViewById(R.id.listTps);
        namaDesaTugas = findViewById(R.id.namaDesaTugas);
        periodePemilu = findViewById(R.id.periodePemilu);

        namaDesaTugas.setText("Daftar Tps Penugasan Anda di Desa "+ TextHelper.capitalize(sh.getString("DESA TPS", null)));

        for (ListtpsItem item : dbHelper.getTps(PERIODE)){
            GridLayoutData newdata = new GridLayoutData();
            newdata.setJudul("TPS "+item.getNomorTps());
            newdata.setGambar(R.drawable.tpsic);
            newdata.setIdTps(item.getIdTps());
            data.add(newdata);
        }

        if(PERIODE.equals("0")){
            periodePemilu.setText("SIMULASI PEMILU 2024");
        }else{
            periodePemilu.setText("PEMILU PERIODE "+PERIODE+" 2024");
        }

        adapter = new GridLayoutAdapter(this, data, new GridLayoutAdapter.OnClickListener() {
            @Override
            public void onClick(String idTps) {
                Intent intents = new Intent(NewPemiluDashboard.this, JobSelector.class);
                intents.putExtra("IDTPS", idTps);
                startActivity(intents);
            }
        });
        listTps.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, NewDashboard.class);
        startActivity(intent);
        finish();
    }
}