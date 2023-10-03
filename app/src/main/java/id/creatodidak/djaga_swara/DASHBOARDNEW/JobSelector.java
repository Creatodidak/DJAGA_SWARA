package id.creatodidak.djaga_swara.DASHBOARDNEW;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.TUGASNEW.LAPCEKTPS;
import id.creatodidak.djaga_swara.TUGASNEW.LAPFORMC1;
import id.creatodidak.djaga_swara.TUGASNEW.LAPPAMTPS;
import id.creatodidak.djaga_swara.TUGASNEW.LAPPENGAWALAN;
import id.creatodidak.djaga_swara.TUGASNEW.LAPPENYERAHAN;
import id.creatodidak.djaga_swara.TUGASNEW.LAPSUARA;

public class JobSelector extends AppCompatActivity {
    CardView lapcektps, lappamtps, lapsuara, lapformc1, lappengawalan, lappenyerahan;
    String IDTPS;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_selector);
        Intent intent = getIntent();
        IDTPS = intent.getStringExtra("IDTPS");
        String MERAH = "#e5342f";
        String KUNING = "#eeba56";
        String HIJAU = "#8cc24b";

        dbHelper = new DBHelper(this);
        lapcektps = findViewById(R.id.lapcektps);
        lappamtps = findViewById(R.id.lappamtps);
        lapsuara = findViewById(R.id.lapsuara);
        lapformc1 = findViewById(R.id.lapformc1);
        lappengawalan = findViewById(R.id.lappengawalan);
        lappenyerahan = findViewById(R.id.lappenyerahan);
        String slapcektps = dbHelper.cekLapcektps(IDTPS);
        String slappamtps = dbHelper.cekLappamtps(IDTPS);
//        String slapsuara = dbHelper.(IDTPS);
//        String slapformc1 = dbHelper.(IDTPS);
        String slappengawalan = dbHelper.cekLapwal(IDTPS);
        String slappenyerahan = dbHelper.cekLapserah(IDTPS);

        if(slapcektps.equals("BELUM ADA")){
            lapcektps.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slapcektps.equals("LOCAL")){
            lapcektps.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slapcektps.equals("SERVER")){
            lapcektps.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        if(slappamtps.equals("BELUM ADA")){
            lappamtps.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slappamtps.equals("LOCAL")){
            lappamtps.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slappamtps.equals("SERVER")){
            lappamtps.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        if(slappengawalan.equals("BELUM ADA")){
            lappengawalan.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slappengawalan.equals("LOCAL")){
            lappengawalan.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slappengawalan.equals("SERVER")){
            lappengawalan.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        if(slappenyerahan.equals("BELUM ADA")){
            lappenyerahan.setCardBackgroundColor(Color.parseColor(MERAH));
        }else if(slappenyerahan.equals("LOCAL")){
            lappenyerahan.setCardBackgroundColor(Color.parseColor(KUNING));
        }else if(slappenyerahan.equals("SERVER")){
            lappenyerahan.setCardBackgroundColor(Color.parseColor(HIJAU));
        }

        lapcektps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPCEKTPS.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lappamtps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPPAMTPS.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lapsuara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPSUARA.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lapformc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(JobSelector.this, LAPFORMC1.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lappengawalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPPENGAWALAN.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });

        lappenyerahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelector.this, LAPPENYERAHAN.class);
                intent.putExtra("IDTPS", IDTPS);
                startActivity(intent);
            }
        });
    }
}