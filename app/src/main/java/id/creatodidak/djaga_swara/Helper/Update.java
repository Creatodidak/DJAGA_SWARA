package id.creatodidak.djaga_swara.Helper;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.UpdateApp;
import id.creatodidak.djaga_swara.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update {
    private Context context;

    public Update(Context context) {
        this.context = context;
    }

    public void checkForUpdates() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://example.com/") // Ganti dengan URL base server Anda
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService updateService = retrofit.create(ApiService.class);

        Call<UpdateApp> call = updateService.cekupdate();
        call.enqueue(new Callback<UpdateApp>() {
            @Override
            public void onResponse(Call<UpdateApp> call, Response<UpdateApp> response) {
                if (response.isSuccessful()) {
                    UpdateApp updateApp = response.body();
                    if (updateApp != null) {
                        int latestVersionCode = Integer.parseInt(updateApp.getVersionCode());
                        int currentVersionCode = BuildConfig.VERSION_CODE;

                        if (latestVersionCode > currentVersionCode) {
                            String updateUrl = updateApp.getLink();

                            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateUrl));

                            // Konfigurasi notifikasi unduhan
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setTitle("Pembaruan Aplikasi");
                            request.setDescription("Mengunduh pembaruan aplikasi...");

                            // Menentukan lokasi penyimpanan file unduhan
                            String fileName = "app-update.apk";
                            String folderName = "DjagaSwara/update";

                            File folder = new File(context.getExternalFilesDir(null), folderName);
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

                                            // File unduhan selesai, lanjutkan dengan instalasi
                                            installUpdate(file);
                                        } else if (status == DownloadManager.STATUS_FAILED) {
                                            downloading = false;
                                            // Penanganan kesalahan unduhan
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
                        } else {
                            // Tidak ada pembaruan tersedia
                        }
                    }
                } else {
                    // Gagal mendapatkan respons dari server
                }
            }

            @Override
            public void onFailure(Call<UpdateApp> call, Throwable t) {
                // Gagal melakukan permintaan ke server
            }
        });
    }

    private void updateProgressBar(int progress) {
        // Update progress bar dengan persentase yang diberikan
    }

    private void installUpdate(File apkFile) {
        // Metode untuk memulai instalasi APK
        // Anda dapat menggunakan Intent untuk memulai instalasi dengan Intent.ACTION_VIEW
        // Pastikan Anda memiliki izin yang diperlukan dalam AndroidManifest.xml
    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }
}

