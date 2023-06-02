package id.creatodidak.djaga_swara.Dashboard.TugasForm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Dashboard.Dashboard;
import id.creatodidak.djaga_swara.Dashboard.Sprin;
import id.creatodidak.djaga_swara.Dashboard.Tugas;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lokasi extends AppCompatActivity implements LocationListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;

    String id_tps;
    TextView tvlng, tvlat, tvid;
    private boolean isInternetAvailable;
    ProgressDialog progressDialog;
    Button btn;

    ApiService apiService;

    DatabaseHelper databaseHelper;
    Boolean cek, upd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            tvlat = findViewById(R.id.latitude);
            tvlng = findViewById(R.id.longitude);
            tvid = findViewById(R.id.idtpsreal);
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            id_tps = getIntent().getStringExtra("id_tps");
            btn = findViewById(R.id.btnset);
            tvid.setText("ID: " + id_tps);
            databaseHelper = new DatabaseHelper(Lokasi.this);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    isInternetAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    if (isInternetAvailable) {
                        setlokasi();
                    } else {
                        updatedb("YES, LOCAL");
                    }
                }
            });
            refresh();
        }
    }

    private void updatedb(String msg) {
        showprogress("Menyimpan data di local");
        cek = databaseHelper.updateTpsActivity(id_tps, "lokasi",msg);

        if(cek){
            progressDialog.dismiss();
            if (msg.equals("YES, ALL")){
                upd = databaseHelper.addLokasi(id_tps, String.valueOf(latitude), String.valueOf(longitude), "SERVER", getTimestamp());

                if (upd){
                    notifikasi("Berhasil", "Lokasi berhasil diupdate, Lokasi telah di upload ke server", true);
                }else{
                    notifikasi("Berhasil", "Lokasi berhasil diupdate, Lokasi telah di upload ke server namun gagal diperbarui di Local Database", true);
                }
            }else{
                upd = databaseHelper.addLokasi(id_tps, String.valueOf(latitude), String.valueOf(longitude), "LOCAL", getTimestamp());

                if (upd) {
                    notifikasi("Berhasil", "Lokasi berhasil diupdate, namun tidak diupload ke server dan tersimpan di Draft", true);
                }else{
                    notifikasi("Gagal", "Lokasi Gagal diupdate dan tidak tersimpan di Draft!", true);
                }
            }
        }else{
            progressDialog.dismiss();
            notifikasi("Gagal", "Lokasi gagal diupdate, hubungi Posko!", true);
        }
    }

    private void setlokasi() {
        showprogress("Menyimpan data ke server...");
        apiService = ApiClient.getClient().create(ApiService.class);

        Call<UpdResponse> call = apiService.updateloc(id_tps, String.valueOf(latitude), String.valueOf(longitude));

        call.enqueue(new Callback<UpdResponse>(){

            @Override
            public void onResponse(retrofit2.Call<UpdResponse> call, Response<UpdResponse> response) {
                if (response.body() != null){
                    progressDialog.dismiss();
                    if (response.body().getMsg().equals("ok")){
                        updatedb("YES, ALL");
                    }else{
                        updatedb("YES, LOCAL");
                    }
                }else{
                    updatedb("YES, LOCAL");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UpdResponse> call, Throwable t) {
                progressDialog.dismiss();
                updatedb("YES, LOCAL");
            }
        });
       
    }

    private void showprogress(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void refresh() {
        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isLocationEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Jika izin tidak diberikan, minta izin akses lokasi
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0, this);
            }
        } else {
            showLocationAlertDialog();
        }
    }

    private void showLocationAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Lokasi tidak aktif");
        alertDialog.setMessage("Lokasi tidak diaktifkan. Apakah Anda ingin membuka pengaturan untuk mengaktifkannya?");
        alertDialog.setPositiveButton("Pengaturan", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        });
        alertDialog.setCancelable(false);

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        tvlat.setText(String.valueOf(latitude));
        tvlng.setText(String.valueOf(longitude));
        btn.setEnabled(true);
    }

    // Metode lain dari LocationListener

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                refresh();
            } else {
                // Izin akses lokasi tidak diberikan, lakukan sesuatu (misalnya tampilkan pesan)
            }
        }
    }

    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void notifikasi(String info, String s, Boolean cancel) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Lokasi.this);
                if(cancel){
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
                            .setCancelable(false)
                            .show();
                }
                
    }

    public String getTimestamp() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        return timestamp;
    }
}
