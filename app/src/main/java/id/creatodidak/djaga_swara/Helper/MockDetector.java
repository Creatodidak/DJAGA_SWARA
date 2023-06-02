package id.creatodidak.djaga_swara.Helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Login.Blocked;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockDetector {
    private Context context;
    private SharedPreferences sharedPreferences;
    private boolean isMockLocationDetected;

    public MockDetector(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        isMockLocationDetected = false;
    }

    public boolean checkMockLocation() {
        Location location = getCurrentLocation();
        boolean isMockLocation = isMockLocation(location);

        if (isMockLocation && !isMockLocationDetected) {
            String mock = sharedPreferences.getString("mock", "");
            String nrp = sharedPreferences.getString("nrp", "");
            String tokens = RandomStringGenerator.generateRandomString(20);

            if (!mock.isEmpty()) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<UpdResponse> call = apiService.sendnrp(nrp, tokens);
                call.enqueue(new Callback<UpdResponse>() {
                    @Override
                    public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                        showMockLocationAlertDialog(tokens);
                    }

                    @Override
                    public void onFailure(Call<UpdResponse> call, Throwable t) {
                        showMockLocationAlertDialog(tokens);
                    }
                });
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mock", "pernah");
                editor.apply();
                showMockLocationWarning();
                isMockLocationDetected = true;
            }
        } else {
            Log.i("MOCK", context.getClass().getSimpleName() + " | AMAN MASEEEEHHH");
        }

        return isMockLocationDetected;
    }

    private Location getCurrentLocation() {
        Location currentLocation = null;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    currentLocation = lastKnownLocation;
                }
            }
        }

        return currentLocation;
    }

    private boolean isMockLocation(Location location) {
        return location != null && location.isFromMockProvider();
    }

    private void showMockLocationWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Peringatan");
        builder.setMessage("ANDA TERDETEKSI MENGGUNAKAN FAKE GPS / MOCK GPS / LOKASI PALSU, SILAHKAN AKTIFKAN LOKASI ASLI ANDA...!!!\n\n" +
                "PERINGATAN INI HANYA MUNCUL SEKALI, APABILA SETELAH ANDA MENDAPATKAN PERINGATAN INI ANDA MASIH MENGGUNAKAN LOKASI PALSU, " +
                "MAKA SECARA OTOMATIS APLIKASI ANDA AKAN TERBLOKIR!!!");

        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showMockLocationAlertDialog(String tokens) {
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Oooopppsss!!!");
            builder.setMessage("ANDA TERDETEKSI MENGGUNAKAN FAKE GPS / MOCK GPS / LOKASI PALSU KEMBALI, SILAHKAN TEKAN TOMBOL OK!");

            builder.setCancelable(false);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("blocked", true);
                    editor.putString("token", tokens);
                    editor.apply();

                    Intent intent = new Intent(context, Blocked.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
