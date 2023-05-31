package id.creatodidak.djaga_swara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.UpdateApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekUpdate extends AppCompatActivity {

    private static final int REQUEST_INSTALL_PERMISSION = 100;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1000;
    TextView txt;
    ProgressBar pr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_update);
        txt = findViewById(R.id.txPembaruan);
        pr = findViewById(R.id.loading);

        // Cek versi Android apakah sudah di atas Marshmallow (6.0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Periksa apakah izin sudah diberikan atau belum
            if (!Settings.canDrawOverlays(this)) {
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

    private void checkForUpdates() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<UpdateApp> call = apiService.cekupdate();
        call.enqueue(new Callback<UpdateApp>() {
            @Override
            public void onResponse(Call<UpdateApp> call, Response<UpdateApp> response) {
                if (response.isSuccessful()) {
                    UpdateApp updateApp = response.body();
                    if (updateApp != null) {
                        int latestVersionCode = Integer.parseInt(updateApp.getVersionCode());
                        int currentVersionCode = BuildConfig.VERSION_CODE;

                        if (latestVersionCode > currentVersionCode) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CekUpdate.this);
                            builder.setTitle("Versi Baru Ditemukan");
                            builder.setMessage("Release Beta v" + response.body().getVersionName() + "\n\nRINCIAN UPDATE:\n" + response.body().getDescription());

                            builder.setPositiveButton("PERBARUI", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    txt.setText("Pembaruan Tersedia!\nMemulai unduhan...\nJangan keluar atau menutup aplikasi!");
                                    String updateUrl = updateApp.getLink(); // Ganti dengan URL pembaruan APK yang diberikan oleh server

                                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateUrl));

                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                                    request.setTitle("Pembaruan Aplikasi");
                                    request.setDescription("Mengunduh pembaruan aplikasi...");

                                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                                    String todayDate = dateFormat.format(new Date());
                                    String fileName = "djagaswara-" + todayDate + "-upd.apk";
                                    String folderName = "Djaga Swara/release";

                                    File folder = new File(Environment.getExternalStorageDirectory(), folderName);
                                    if (!folder.exists()) {
                                        folder.mkdirs();
                                    }
                                    File file = new File(folder, fileName);
                                    request.setDestinationUri(Uri.fromFile(file));

                                    // Memulai unduhan
                                    long downloadId = downloadManager.enqueue(request);

                                    // Memantau kemajuan unduhan
                                    new Thread(() -> {
                                        boolean downloading = true;

                                        while (downloading) {
                                            DownloadManager.Query query = new DownloadManager.Query();
                                            query.setFilterById(downloadId);
                                            Cursor cursor = downloadManager.query(query);
                                            if (cursor.moveToFirst()) {
                                                @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                                                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                                    downloading = false;
                                                    installUpdate(file);
                                                } else if (status == DownloadManager.STATUS_FAILED) {
                                                    downloading = false;
                                                } else {
                                                    @SuppressLint("Range") int progress = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                                    @SuppressLint("Range") int total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                                                    // Update progress bar
                                                    int progressPercentage = (int) ((progress * 100L) / total);
                                                    updateProgressBar(progressPercentage);
                                                }
                                            }
                                            cursor.close();
                                        }
                                    }).start();
                                }
                            });

                            builder.setNegativeButton("jangan perbarui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder newDialogBuilder = new AlertDialog.Builder(CekUpdate.this);
                                    newDialogBuilder.setTitle("Hmmmmmm");
                                    newDialogBuilder.setMessage("Anda harus menginstall pembaruan ini, aplikasi akan otomatis tertutup dalam 3 detik...");

                                    AlertDialog newAlertDialog = newDialogBuilder.create();
                                    newAlertDialog.setCancelable(false); // Menonaktifkan tombol Cancel pada dialog baru
                                    newAlertDialog.show();

                                    // Menutup aplikasi setelah 3 detik
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            newAlertDialog.dismiss(); // Menutup dialog baru
                                            finish(); // Menutup activity dan aplikasi
                                        }
                                    }, 3000);

                                    dialog.dismiss();
                                }
                            });


                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            txt.setText("Tidak ada pembaruan,\nmelanjutkan Proses Login...");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(CekUpdate.this, Izin.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 2000);
                        }
                    }
                } else {
                    txt.setText("Gagal terhubung ke server...");
                    pr.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(CekUpdate.this, Izin.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onFailure(Call<UpdateApp> call, Throwable t) {
                txt.setText("Tidak ada pembaruan, melanjutkan...");
                pr.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(CekUpdate.this, Izin.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        });
    }

    private void installUpdate(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri apkUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            apkUri = Uri.fromFile(file);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void updateProgressBar(int progress) {
        runOnUiThread(() -> pr.setProgress(progress));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            // Periksa kembali apakah izin diberikan setelah pengguna memilih
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // Izin sudah diberikan, lanjutkan ke aktivitas berikutnya
                    startNextActivity();
                } else {
                    // Izin tidak diberikan, mungkin pengguna menolaknya
                    // Anda dapat menampilkan pesan kesalahan atau mengambil tindakan lain sesuai kebutuhan
                }
            }
        }
    }

    private void startNextActivity() {
        checkForUpdates();
    }
}
