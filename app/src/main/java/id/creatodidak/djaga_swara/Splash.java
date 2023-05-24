package id.creatodidak.djaga_swara;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import id.creatodidak.djaga_swara.Login.Login;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3070;
    private static final int REQUEST_LOCATION_SETTINGS = 1001;

    private SharedPreferences sharedPreferences;
    private ImageView tirex;
    private ProgressBar progressBar;

    private int animationDuration = 3010;
    private int maxProgress = 100;
    private LocationManager locationManager;
    private boolean isGPSEnabled;

    TextView ver;
    private AlertDialog locationEnableDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ver = findViewById(R.id.versi);
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);

        tirex = findViewById(R.id.tirex);
        progressBar = findViewById(R.id.progressBar3);
        ver.setText("DjagaSwara BETA v"+BuildConfig.VERSION_NAME);
        animationDuration = 3010;
        maxProgress = 100;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        cekGPS();
    }

    private void cekGPS() {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            mulaiLoader();
        } else {
            showLocationEnableDialog();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cekGPS();
    }

    private void showLocationEnableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aktifkan Lokasi");
        builder.setMessage("Lokasi tidak aktif. Aktifkan lokasi sekarang?");
        builder.setPositiveButton("Pengaturan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, REQUEST_LOCATION_SETTINGS);
            }
        });
        builder.setNegativeButton("KELUAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setCancelable(false);
        locationEnableDialog = builder.create();
        locationEnableDialog.show();
    }

    private void mulaiLoader() {
        Glide.with(this)
                .asGif()
                .load(R.drawable.trex)
                .into(tirex);

        progressBar.setMax(maxProgress);
        progressBar.setProgress(0);

        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", 0, maxProgress);
        animator.setDuration(animationDuration);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(Splash.this, CekUpdate.class);
                startActivity(intent);


                finish();
            }
        }, SPLASH_DURATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION_SETTINGS) {
            cekGPS(); // Cek GPS kembali setelah pengaturan lokasi selesai
        }
    }
}