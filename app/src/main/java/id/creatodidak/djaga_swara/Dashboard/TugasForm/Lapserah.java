package id.creatodidak.djaga_swara.Dashboard.TugasForm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lapserah extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSIONS_REQUEST_LOCATION = 2;
    private String currentPhotoPath;
    private ImageView fotoImageView, wm1;
    private TextView wm2, wm3, wm4, wm5;
    private CardView wmbox;
    private boolean isInternetAvailable;
    Button ambilFotoButton, kirim;
    ConstraintLayout cl;
    ConstraintSet constraintSet;
    String judul, nrp, id_tps;
    String formattedDateTime, situasiSp;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    DatabaseHelper databaseHelper;
    Boolean cek, upd;
    Spinner spinner;
    EditText prediksi;
    Uri contentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapserah);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            ambilFotoButton = findViewById(R.id.btambilfoto);
            ambilFotoButton.setOnClickListener(this);
            kirim = findViewById(R.id.btkirimlaporan);
            kirim.setOnClickListener(this);
            fotoImageView = findViewById(R.id.fotonya);
            wmbox = findViewById(R.id.wmbox);
            wm1 = findViewById(R.id.wm1);
            wm2 = findViewById(R.id.wm2);
            wm3 = findViewById(R.id.wm3);
            wm4 = findViewById(R.id.wm4);
            wm5 = findViewById(R.id.wm5);
            cl = findViewById(R.id.foto);
            spinner = findViewById(R.id.situasi);
            prediksi = findViewById(R.id.prediksi);


            constraintSet = new ConstraintSet();

            // Set onClickListener pada tombol ambilFotoButton
            ambilFotoButton.setOnClickListener(this);

            judul = getIntent().getStringExtra("judul");
            id_tps = getIntent().getStringExtra("id_tps");
            SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            nrp = sharedPreferences.getString("nrp", "");
            Date date = new Date();

            // Format tanggal dan waktu
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy 'Pukul' HH:mm 'WIB'", new Locale("id"));
            formattedDateTime = dateFormat.format(date);

            // Cek dan minta izin lokasi jika belum diberikan
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            } else {
                getLocation();
            }

            databaseHelper = new DatabaseHelper(Lapserah.this);

            String[] isiSpinner = {"PILIH KONDISI KOTAK SUARA", "KOTAK SUARA DISERAHKAN DALAM KONDISI BAIK, SEGEL TERKUNCI DAN DOKUMEN LENGKAP", "KOTAK SUARA DISERAHKAN DALAM KONDISI KURANG BAIK, SEGEL TERKUNCI DAN DOKUMEN LENGKAP", "KOTAK SUARA DISERAHKAN DALAM KONDISI KURANG BAIK, SEGEL TIDAK TERKUNCI DAN DOKUMEN LENGKAP", "KOTAK SUARA DISERAHKAN DALAM KONDISI KURANG BAIK, SEGEL TIDAK TERKUNCI DAN DOKUMEN TIDAK LENGKAP", "KOTAK SUARA DISERAHKAN DALAM KONDISI RUSAK, NAMUN SEGEL TERKUNCI DAN DOKUMEN LENGKAP", "KOTAK SUARA DISERAHKAN DALAM KONDISI RUSAK, NAMUN SEGEL TERKUNCI DAN DOKUMEN TIDAK LENGKAP", "KOTAK SUARA DISERAHKAN DALAM KONDISI RUSAK, SEGEL TIDAK TERKUNCI DAN DOKUMEN LENGKAP", "KOTAK SUARA DISERAHKAN DALAM KONDISI RUSAK, SEGEL TIDAK TERKUNCI DAN DOKUMEN TIDAK LENGKAP"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, isiSpinner);
            adapter.setDropDownViewResource(R.layout.dropdown);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    situasiSp = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Kode yang akan dijalankan jika tidak ada item yang dipilih
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btambilfoto) {
            dispatchCameraIntent();
        }

        if (v.getId() == R.id.btkirimlaporan) {
            Bitmap bitmap = getBitmapFromView(cl);
            saveBitmapToGallery(bitmap);


            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isInternetAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if(!situasiSp.equals("PILIH SITUASI")){
                if(!prediksi.getText().toString().isEmpty()){
                    if (isInternetAvailable) {
                        showprogress("Menyimpan data ke server...");
                        uploadImage(bitmap);
                    } else {
                        updatedb("YES, LOCAL");
                    }
                }else{
                    notifikasi("DATA KOSONG!", "Silahkan isi keterangan tambahan!\nJika tidak ada, isikan dengan NIHIL", true, false);
                }
            }else{
                notifikasi("DATA KOSONG!", "PILIH SITUASI TPS!", true, false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Data belum dilaporkan data tidak akan tersimpan di draft dan database, tutup laporan?");
        builder.setPositiveButton("LANJUTKAN LAPORAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Ya, batalkan...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dispatchCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(this,
                            getApplicationContext().getPackageName() + ".provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "LAPSERAH_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Djaga Swara/Lap Serah");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Tampilkan foto di ImageView
            Glide.with(this)
                    .load(currentPhotoPath)
                    .into(fotoImageView);

            wmbox.setVisibility(View.VISIBLE);
            wm1.setVisibility(View.VISIBLE);
            wm2.setVisibility(View.VISIBLE);

            // Dapatkan rasio foto dari currentPhotoPath
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(currentPhotoPath, options);
            int width = options.outWidth;
            int height = options.outHeight;

            // Menyederhanakan rasio dengan FPB
            int fpb = findGreatestCommonDivisor(width, height);
            int simplifiedWidth = width / fpb;
            int simplifiedHeight = height / fpb;

            // Hasil rasio yang disederhanakan
            String ratio = String.format(Locale.getDefault(), "%d:%d", simplifiedWidth, simplifiedHeight);

            // Ubah rasio ConstraintLayout dengan id R.id.foto
            constraintSet.clone(cl);
            constraintSet.setDimensionRatio(R.id.fotonya, ratio);
            constraintSet.applyTo(cl);

            ambilFotoButton.setVisibility(View.GONE);
            kirim.setEnabled(true);
            wm2.setText(nrp);
            wm3.setText(judul);
            wm4.setText(formattedDateTime);

            // Dapatkan lokasi dan tampilkan di wm5
            getLocation();
        }
    }

    private int findGreatestCommonDivisor(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String locationText = "Coordinate: " + latitude + " , " + longitude+"\n©DjagaSwara | POLRES LANDAK";
                wm5.setText(locationText);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }
        };

        // Set minimum waktu dan jarak untuk mendapatkan pembaruan lokasi
        long minTime = 10000; // 10 detik
        float minDistance = 10; // 10 meter

        // Meminta pembaruan lokasi dari provider
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Izin lokasi ditolak.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Djaga Swara/Lap Serah");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        // Membuat file baru dengan nama unik
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "LAPSERAH_" + timeStamp + ".jpg";
        File imageFile = new File(storageDir, imageFileName);

        try {
            // Menyimpan bitmap sebagai file gambar
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Menambahkan gambar ke galeri
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(Bitmap bitmap) {
        // Convert bitmap to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Create request body for image
        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UpdResponse> call = apiService.uploadFoto(id_tps, "lapserah", situasiSp, prediksi.getText().toString(), image);
        call.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    UpdResponse updResponse = response.body();
                    if (updResponse != null && updResponse.getMsg().equals("Upload Berhasil!")) {
                        updatedb("YES, ALL");
                    } else {
                        updatedb("YES, LOCAL");
                    }
                } else {
                    updatedb("YES, LOCAL");
                }
            }

            @Override
            public void onFailure(Call<UpdResponse> call, Throwable t) {
                progressDialog.dismiss();
                updatedb("YES, LOCAL");
            }
        });
    }

    private void updatedb(String msg) {
        showprogress("Menyimpan data di local");
        cek = databaseHelper.updateTpsActivity(id_tps, "lapserah",msg);

        if(cek){
            progressDialog.dismiss();
            if (msg.equals("YES, ALL")){

                upd = databaseHelper.addLapserah(id_tps, String.valueOf(contentUri), situasiSp, prediksi.getText().toString(), "SERVER", getTimestamp());

                if (upd){
                    notifikasi("Berhasil", "Laporan berhasil dibuat, Laporan telah di upload ke server", true, true);
                }else{
                    notifikasi("Berhasil", "Laporan berhasil dibuat, Laporan telah di upload ke server namun gagal mengupdate database local", true, true);
                }
            }else{
                upd = databaseHelper.addLapserah(id_tps, String.valueOf(contentUri), situasiSp, prediksi.getText().toString(), "LOCAL", getTimestamp());

                if (upd){
                    notifikasi("Berhasil", "Laporan berhasil dibuat, namun tidak diupload ke server, Laporan tersimpan di draft", true, true);
                }else{
                    notifikasi("Gagal", "Laporan tidak berhasil dibuat dan tidak disimpan di draft!", true, true);
                }
            }
        }else{
            progressDialog.dismiss();
            notifikasi("Gagal", "Laporan gagal dibuat, hubungi Posko!", true, true);
        }
    }

    private void showprogress(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void notifikasi(String info, String s, Boolean cancel, Boolean close) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Lapserah.this);
        if(cancel){
            if(close){
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
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
