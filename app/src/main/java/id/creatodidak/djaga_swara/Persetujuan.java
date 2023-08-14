package id.creatodidak.djaga_swara;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.IzinAdapter;
import id.creatodidak.djaga_swara.API.Models.ListIzin;
import id.creatodidak.djaga_swara.Helper.MockDetector;

public class Persetujuan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IzinAdapter izinAdapter;
    private List<ListIzin> listIzin;

    SharedPreferences sharedPreferences;
    CheckBox cb;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            recyclerView = findViewById(R.id.izin);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            cb = findViewById(R.id.setuju);
            listIzin = new ArrayList<>();
            listIzin.add(new ListIzin("1.", "Dilarang keras memberitahukan dan atau menyebarkan dan atau mempersalahgunakan sedikit/sebagian/sepenuhnya segala bentuk informasi dan data yang terkandung dalam aplikasi ini kepada orang lain dan atau anggota Polri yang tidak memiliki urgensi terhadap tujuan disediakannya aplikasi ini;"));
            listIzin.add(new ListIzin("2.", "Dilarang keras menyalin ataupun mendokumentasikan segala bentuk aktivitas didalam aplikasi ini untuk keuntungan pribadi ataupun golongan;"));
            listIzin.add(new ListIzin("3.", "Dilarang keras memasukan data yang tidak benar dan atau salah dan atau bohong didalam aplikasi ini;"));
            listIzin.add(new ListIzin("4.", "Dilarang keras memasukan data atau melakukan aktivitas yang dapat menyebabkan gangguan atau error pada aplikasi ini;"));
            listIzin.add(new ListIzin("5.", "Saya dengan kesadaran penuh mengizinkan aplikasi ini merekam dan atau mencatat dan atau menyimpan segala bentuk informasi dan data yang saya miliki demi terwujudnya maksud dan tujuan digunakannya aplikasi ini;"));
            listIzin.add(new ListIzin("6.", "Sanggup melaksanakan tugas yang diamanahkan kepada saya melalui aplikasi ini dengan semangat dan tetap memegang teguh Tribrata dan Catur Prasetya;"));
            listIzin.add(new ListIzin("7.", "Saya sanggup dan siap menjaga kerahasiaan segala bentuk informasi dan data yang ada dalam aplikasi ini;"));
            listIzin.add(new ListIzin("8.", "Jika saya melanggar saya siap menerima konsekuensi dan sanksi yang tertuang/diputuskan dalam Peraturan dan atau Hukum Pidana serta kebijakan Kapolres Landak selaku Pimpinan saya."));

            cb.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("persetujuan", isChecked);
                    editor.apply();

                    startIzinActivity();
                }
            });

            izinAdapter = new IzinAdapter(listIzin);
            recyclerView.setAdapter(izinAdapter);
        }

    }

    private void startIzinActivity() {
        Intent intent = new Intent(Persetujuan.this, Izin.class);
        startActivity(intent);
        finish();
    }
}