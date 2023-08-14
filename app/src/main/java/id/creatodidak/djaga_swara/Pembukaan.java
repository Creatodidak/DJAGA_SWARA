package id.creatodidak.djaga_swara;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import id.creatodidak.djaga_swara.Helper.NotificationHelper;

public class Pembukaan extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3070;
    private static final int REQUEST_LOCATION_SETTINGS = 1001;

    private SharedPreferences sharedPreferences;
    private ImageView tirex;
    private ProgressBar progressBar;

    private int animationDuration = 3010;
    private int maxProgress = 100;
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1000;


    TextView ver;
    private AlertDialog locationEnableDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

            ver = findViewById(R.id.versi);
            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);

            tirex = findViewById(R.id.tirex);
            progressBar = findViewById(R.id.progressBar3);
            ver.setText("DjagaSwara v"+ BuildConfig.VERSION_NAME+ " | RELEASE");
            animationDuration = 3010;
            maxProgress = 100;

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            cekGPS();
            showWelcomeNotification();

    }

    private void showWelcomeNotification() {
        String title = "Selamat Datang Di Djaga Swara Polres Landak";
        String message = "Silahkan gunakan aplikasi ini dengan penuh rasa tanggung jawab!";

        // Memanggil metode showNotification pada NotificationHelper untuk menampilkan notifikasi
        NotificationHelper.showNotification(this, title, message);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Periksa apakah izin sudah diberikan atau belum
                    if (!Settings.canDrawOverlays(Pembukaan.this)) {
                        // Jika izin belum diberikan, tampilkan permintaan izin
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
                    } else {
                        // Izin sudah diberikan, lanjutkan ke aktivitas berikutnya
                        startNextActivity();
                    }
                } else {
                    // Jika versi Android di bawah Marshmallow, tidak perlu meminta izin
                    startNextActivity();
                }
            }
        }, SPLASH_DURATION);
    }

    private void startNextActivity() {
        Intent intent = new Intent(Pembukaan.this, Izin.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION_SETTINGS) {
            cekGPS(); // Cek GPS kembali setelah pengaturan lokasi selesai
        }
    }
}