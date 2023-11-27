package id.creatodidak.djaga_swara.TUGASNEW;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapCekTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.DASHBOARDNEW.JobSelector;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.GPSChecker;
import id.creatodidak.djaga_swara.plugin.PhotoResult;
import id.creatodidak.djaga_swara.plugin.ProgressUpload;
import id.creatodidak.djaga_swara.plugin.RandomStringGenerator;
import id.creatodidak.djaga_swara.plugin.SessionData;
import id.creatodidak.djaga_swara.plugin.TextHelper;
import id.creatodidak.djaga_swara.plugin.UPDialog;
import id.creatodidak.djaga_swara.plugin.WaktuLokal;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LAPCEKTPS extends AppCompatActivity implements LocationListener {

    String IDTPS;
    DBHelper dbHelper;
    Endpoint endpoint;
    ImageView loadingLoc;
    TextView tvLoadingKoordinat;
    ScrollView itemFormCekTps;
    TextView tvLatitude;
    TextView tvLongitude;
    EditText etFakta;
    EditText etAnalisa;
    EditText etRekomendasi;
    LinearLayout btTambahDokumentasi, listDokumentasi;
    Button btKirimData;
    ActivityResultLauncher<Intent> aktifkanGPS, opencamera, openresult;
    LocationManager locationManager;
    File storageDir;
    String PHOTO_PATH;
    Location currentLocation;
    String listFoto = "";
    SharedPreferences sh;
    CardView formCekTps;
    Spinner spFakta1;
    Spinner spFakta2;
    Spinner spAnalisa1;
    Spinner spPrediksi1;
    Spinner spPrediksi2;
    Spinner spPrediksi3;
    EditText etPrediksi1;
    EditText etPrediksi2;
    EditText etPrediksi3;
    List<String> opt1 = Arrays.asList("PILIH JAWABAN", "SUDAH ADA", "BELUM ADA");
    List<String> opt2 = Arrays.asList("PILIH JAWABAN", "SUDAH SIAP", "BELUM SIAP");
    List<String> opt3 = Arrays.asList("PILIH JAWABAN", "SANGAT LAYAK", "CUKUP LAYAK", "KURANG LAYAK", "SANGAT TIDAK LAYAK");
    List<String> opt4 = Arrays.asList("PILIH JAWABAN", "ADA POTENSI", "TIDAK ADA POTENSI");
    List<String> opt5 = Arrays.asList("PILIH JAWABAN", "ADA POTENSI", "TIDAK ADA POTENSI");
    List<String> opt6 = Arrays.asList("PILIH JAWABAN", "ADA POTENSI", "TIDAK ADA POTENSI");

    String PREDIKSI1 = "";
    String PREDIKSI2 = "";
    String PREDIKSI3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapcektps);
        Intent intent = getIntent();
        IDTPS = intent.getStringExtra("IDTPS");
        dbHelper = new DBHelper(this);
        storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DJAGASWARA");
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);

        loadingLoc = findViewById(R.id.loadingLoc);
        tvLoadingKoordinat = findViewById(R.id.tvLoadingKoordinat);
        itemFormCekTps = findViewById(R.id.itemFormCekTps);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        etFakta = findViewById(R.id.etFakta);
        etAnalisa = findViewById(R.id.etAnalisa);
        etRekomendasi = findViewById(R.id.etRekomendasi);
        btTambahDokumentasi = findViewById(R.id.btTambahDokumentasi);
        btKirimData = findViewById(R.id.btKirimData);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listDokumentasi = findViewById(R.id.listDokumentasi);
        formCekTps = findViewById(R.id.formCekTps);
        spFakta1 = findViewById(R.id.spFakta1);
        spFakta2 = findViewById(R.id.spFakta2);
        spAnalisa1 = findViewById(R.id.spAnalisa1);
        spPrediksi1 = findViewById(R.id.spPrediksi1);
        spPrediksi2 = findViewById(R.id.spPrediksi2);
        spPrediksi3 = findViewById(R.id.spPrediksi3);
        etPrediksi1 = findViewById(R.id.etPrediksi1);
        etPrediksi2 = findViewById(R.id.etPrediksi2);
        etPrediksi3 = findViewById(R.id.etPrediksi3);

        aktifkanGPS = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (GPSChecker.isGPSActive(this)) {
                getKoordinat();
            } else {
                this.finish();
            }
        });

        opencamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                showPreview(PHOTO_PATH);
            }
        });

        openresult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    String gambar = result.getData().getStringExtra("gambar");

                    if (gambar != null && !gambar.isEmpty()) {
                        File imageFile = new File(gambar);
                        Uri uri = Uri.fromFile(imageFile);

                        if (listFoto.equals("")) {
                            listFoto = String.valueOf(uri);
                        } else {
                            listFoto = listFoto + "," + uri;
                        }

                        ImageView imageView = new ImageView(this);
                        imageView.setImageURI(uri);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        listDokumentasi.addView(imageView, layoutParams);
                    }
                }
            }
        });

        itemFormCekTps.setVisibility(View.GONE);

        Glide.with(this)
                .asGif()
                .load(R.drawable.radar)
                .into(loadingLoc);

        String status = dbHelper.cekLapcektps(IDTPS);
        if (status.equals("BELUM ADA")) {
            showform();
        } else if (status.equals("LOCAL")) {
            showdata(true);
        } else if (status.equals("SERVER")) {
            showdata(false);
        }

        if (GPSChecker.isGPSActive(this)) {
            getKoordinat();
        } else {
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            aktifkanGPS.launch(i);
        }

        btTambahDokumentasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });

        btKirimData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataValid() && !listFoto.equals("")) {
                    CDialog.up(
                            LAPCEKTPS.this,
                            "Konfirmasi",
                            "Anda yakin akan mengirimkan data ini?",
                            true, false, false,
                            "BATAL", "KIRIM", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    saveDataToDB();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {
                                    alert.dismiss();
                                }
                            }
                    ).show();
                } else {
                    String addPesan = "";
                    if (listFoto.equals("")) {
                        addPesan = "Anda Wajib Melampirkan Min. 1 (Satu) Dokumentasi";
                    } else {
                        addPesan = "Seluruh data harus diisi/dipilih!";
                    }

                    CDialog.up(
                            LAPCEKTPS.this,
                            "Peringatan",
                            addPesan,
                            false, false, false,
                            "", "PERBAIKI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {

                                }
                            }
                    ).show();
                }
            }
        });
    }

    private void getKoordinat() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        long mintime = 2000;
        float dist = 3;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, mintime, dist, this);
    }

    private void showdata(boolean showbuttondraft)  {
        CardView datacektps = findViewById(R.id.dataCekTps);
        TextView tvPreambule = findViewById(R.id.tvPreambule);
        TextView tvFakta = findViewById(R.id.tvFakta);
        TextView tvAnalisa = findViewById(R.id.tvAnalisa);
        TextView tvPrediksi = findViewById(R.id.tvPrediksi);
        TextView tvRekomendasi = findViewById(R.id.tvRekomendasi);
        TextView tvClosing = findViewById(R.id.tvClosing);
        LinearLayout listDokumentasiLocal = findViewById(R.id.listDokumentasiLocal);
        Button btKirimDraft = findViewById(R.id.btKirimDraft);
        datacektps.setVisibility(View.VISIBLE);

        if (showbuttondraft) {
            btKirimDraft.setVisibility(View.VISIBLE);
            btKirimDraft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tryUploadData();
                }
            });
        } else {
            btKirimDraft.setVisibility(View.GONE);
        }

        MLapCekTPS data = dbHelper.getLapCekTPS(IDTPS);
        String FAKTA = TextHelper.numberingData(data.getFakta());
        String ANALISA = TextHelper.numberingData(data.getAnalisa());
        String PREDIKSI = data.getPrediksi();
        String REKOMENDASI = TextHelper.numberingData(data.getRekomendasi());
        String[] FOTO = data.getDokumentasi().split(",");
        String pre = "Kepada: Yth. Kapolres Landak\nDari: Pers Pam TPS " + dbHelper.getRincianTpsById(IDTPS).getNomorTps() + " Desa " + TextHelper.capitalize(sh.getString("DESA TPS", "TIDAK DIKETAHUI")) + "\n\n" + WaktuLokal.deteksiWaktu() + " Komandan, \nIzin melaporkan kegiatan pengecekan TPS " + dbHelper.getRincianTpsById(IDTPS).getNomorTps() + " Desa " + TextHelper.capitalize(sh.getString("DESA TPS", "TIDAK DIKETAHUI")) + " oleh " + TextHelper.capitalize(SessionData.getuserdata(this).getPangkat() + " " + SessionData.getuserdata(this).getNama()) + " sbb:\n";
        tvPreambule.setText(pre);
        tvAnalisa.setText(ANALISA);
        tvFakta.setText(FAKTA);
        tvPrediksi.setText(PREDIKSI);
        tvRekomendasi.setText(REKOMENDASI);
        tvClosing.setText("Demikian Komandan yang dapat dilaporkan.\n\nTembusan:\n1. Distribusi A, B dan C Polres Landak");
        for (String foto : FOTO) {
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(Uri.parse(foto));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            listDokumentasiLocal.addView(imageView, layoutParams);
        }

    }

    private void showform() {
        formCekTps.setVisibility(View.VISIBLE);
        ArrayAdapter adp1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opt1);
        ArrayAdapter adp2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opt2);
        ArrayAdapter adp3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opt3);
        ArrayAdapter adp4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opt4);
        ArrayAdapter adp5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opt5);
        ArrayAdapter adp6 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opt6);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFakta1.setAdapter(adp1);
        spFakta2.setAdapter(adp2);
        spAnalisa1.setAdapter(adp3);
        spPrediksi1.setAdapter(adp4);
        spPrediksi2.setAdapter(adp5);
        spPrediksi3.setAdapter(adp6);

        spPrediksi1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spPrediksi1.getSelectedItem().equals("ADA POTENSI")){
                    etPrediksi1.setVisibility(View.VISIBLE);
                    PREDIKSI1 = "1. Menurut prediksi saya Ada Potensi Gangguan Kamtibmas saat dilaksanakannya proses Pemungutan Suara sbb:";
                }else if(spPrediksi1.getSelectedItem().equals("TIDAK ADA POTENSI")){
                    etPrediksi1.setVisibility(View.GONE);
                    PREDIKSI1 = "1. Menurut prediksi saya Tidak Ada Potensi Gangguan Kamtibmas saat dilaksanakannya proses Pemungutan Suara";
                }else if(spPrediksi1.getSelectedItem().equals("PILIH JAWABAN")){
                    etPrediksi1.setVisibility(View.GONE);
                    PREDIKSI1 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPrediksi2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spPrediksi2.getSelectedItem().equals("ADA POTENSI")){
                    etPrediksi2.setVisibility(View.VISIBLE);
                    PREDIKSI2 = "2. Menurut prediksi saya Ada Potensi Gangguan Kamtibmas saat dilaksanakannya proses Penghitungan Suara sbb:";
                }else if(spPrediksi2.getSelectedItem().equals("TIDAK ADA POTENSI")){
                    etPrediksi2.setVisibility(View.GONE);
                    PREDIKSI2 = "2. Menurut prediksi saya Tidak Ada Potensi Gangguan Kamtibmas saat dilaksanakannya proses Penghitungan Suara";
                }else if(spPrediksi2.getSelectedItem().equals("PILIH JAWABAN")){
                    etPrediksi2.setVisibility(View.GONE);
                    etPrediksi2.setText("");
                    PREDIKSI2 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPrediksi3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spPrediksi3.getSelectedItem().equals("ADA POTENSI")){
                    etPrediksi3.setVisibility(View.VISIBLE);
                    PREDIKSI3 = "3. Menurut prediksi saya Ada Potensi Gangguan Kamtibmas saat berjalannya proses rangkaian Pemilu secara keseluruhan di TPS ini karena:";
                }else if(spPrediksi3.getSelectedItem().equals("TIDAK ADA POTENSI")){
                    etPrediksi3.setVisibility(View.GONE);
                    PREDIKSI3 = "3. Menurut prediksi saya Tidak Ada Potensi Gangguan Kamtibmas saat berjalannya proses rangkaian Pemilu secara keseluruhan di TPS ini";
                }else if(spPrediksi3.getSelectedItem().equals("PILIH JAWABAN")){
                    etPrediksi3.setVisibility(View.GONE);
                    PREDIKSI3 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showCamera() {
        String imageFileName = RandomStringGenerator.generateRandomString(30);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File imageFile;
        try {
            imageFile = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PHOTO_PATH = imageFile.getAbsolutePath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getPackageName() + ".provider", imageFile));
        opencamera.launch(intent);
    }

    private void showPreview(String photoPath) {

        Intent intent = new Intent(this, PhotoResult.class);
        intent.putExtra("photopath", photoPath);
        intent.putExtra("judul", "Pengecekan TPS " + dbHelper.getRincianTpsById(IDTPS).getNomorTps() + " Desa" + sh.getString("DESA TPS", "TIDAK DIKETAHUI"));
        intent.putExtra("koordinat", currentLocation.getLatitude() + "," + currentLocation.getLongitude());
        intent.putExtra("storage", storageDir.getAbsolutePath());
        openresult.launch(intent);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        loadingLoc.setVisibility(View.GONE);
        currentLocation = location;
        tvLoadingKoordinat.setVisibility(View.GONE);
        itemFormCekTps.setVisibility(View.VISIBLE);
        tvLatitude.setText(String.valueOf(location.getLatitude()));
        tvLongitude.setText(String.valueOf(location.getLongitude()));
    }

    private void saveDataToDB() {
        String FAKTA = "Saat laporan ini dibuat, Kotak Suara "+TextHelper.capitalize(spFakta1.getSelectedItem().toString())+" di TPS\n\nMenurut KPPS sarana dan prasarana di TPS untuk melaksanakan proses Pemilu dinilai "+TextHelper.capitalize(spFakta2.getSelectedItem().toString())+"\n\n"+etFakta.getText().toString();

        String ANALISA = "Menurut analisa dan penilaian saya, kondisi TPS tempat saya bertugas "+TextHelper.capitalize(spAnalisa1.getSelectedItem().toString())+" untuk digunakan\n\n"+etAnalisa.getText().toString();
        String P1 = "";
        String P2 = "";
        String P3 = "";

        if(spPrediksi1.getSelectedItem().equals("ADA POTENSI")){
            P1 = PREDIKSI1 + "\n" + etPrediksi1.getText().toString()+ "\n";
        }else{
            P1 = PREDIKSI1 + "\n";
        }
        if(spPrediksi2.getSelectedItem().equals("ADA POTENSI")){
            P2 = PREDIKSI2 + "\n" + etPrediksi2.getText().toString()+ "\n";
        }else{
            P2 = PREDIKSI2 + "\n";
        }
        if(spPrediksi3.getSelectedItem().equals("ADA POTENSI")){
            P3 = PREDIKSI3 + "\n" + etPrediksi3.getText().toString()+ "\n";
        }else{
            P3 = PREDIKSI3 + "\n";
        }

//        String PREDIKSI = PREDIKSI1+"\n"+etPrediksi1.getText().toString()+"\n" + PREDIKSI2+"\n"+etPrediksi2.getText().toString()+"\n" + PREDIKSI3+"\n"+etPrediksi3.getText().toString()+"\n";
        String PREDIKSI = P1 + P2 + P3;
        String REKOMENDASI = etRekomendasi.getText().toString();
        String LATITUDE = tvLatitude.getText().toString();
        String LONGITUDE = tvLongitude.getText().toString();
        String FOTO = listFoto;

        if (dbHelper.saveLapCekTPS(IDTPS, FAKTA, ANALISA, PREDIKSI, REKOMENDASI, LATITUDE, LONGITUDE, FOTO)) {
            tryUploadData();
        }
    }

    private void tryUploadData() {
        AlertDialog alerts = UPDialog.up(this, "0%");
        alerts.show();

        MLapCekTPS data = dbHelper.getLapCekTPS(IDTPS);
        String FAKTA = data.getFakta();
        String ANALISA = data.getAnalisa();
        String PREDIKSI = data.getPrediksi();
        String REKOMENDASI = data.getRekomendasi();
        String LATITUDE = data.getLatitude();
        String LONGITUDE = data.getLongitude();
        String[] FOTO = data.getDokumentasi().split(",");

        List<MultipartBody.Part> imageParts = new ArrayList<>();

        for (String uri : FOTO) {
            File img = new File(Uri.parse(uri).getPath()).getAbsoluteFile();
            if (img.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), img);

                RequestBody countingRequestBody = new ProgressUpload(requestFile, new ProgressUpload.ProgressListener() {
                    @Override
                    public void onProgress(long bytesRead, long contentLength) {
                        int progress = (int) ((100 * bytesRead) / contentLength);
                        String progressText = progress + "%";
                        UPDialog.updateProgressText(progressText);

                        if (progress == 100) {
                            UPDialog.updateProgressText("wait");
                        }
                    }
                });

                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image[]", img.getName(), countingRequestBody);
                imageParts.add(imagePart);
            }
        }

        Call<MResponseServer> call = endpoint.uploadLaporanCekTPS(IDTPS, FAKTA, ANALISA, PREDIKSI, REKOMENDASI, LATITUDE, LONGITUDE, imageParts);
        call.enqueue(new Callback<MResponseServer>() {
            @Override
            public void onResponse(Call<MResponseServer> call, Response<MResponseServer> response) {
                if (response.body().isStatus() && response.body() != null && response.isSuccessful()) {
                    if (dbHelper.updStatusCekTPS(IDTPS)) {
                        alerts.dismiss();
                        CDialog.up(
                                LAPCEKTPS.this,
                                "Informasi",
                                "Laporan Berhasil Diupload!",
                                false, false, false,
                                "", "LANJUTKAN", "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {
                                        alert.dismiss();
                                        LAPCEKTPS.this.recreate();
                                    }

                                    @Override
                                    public void onOpt2(AlertDialog alert) {

                                    }

                                    @Override
                                    public void onCancel(AlertDialog alert) {

                                    }
                                }
                        ).show();
                    }
                } else {
                    alerts.dismiss();
                    CDialog.up(
                            LAPCEKTPS.this,
                            "Informasi",
                            "Server Tidak Meresponse!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                            false, false, false,
                            "", "MENGERTI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    LAPCEKTPS.this.recreate();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {

                                }
                            }
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<MResponseServer> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        LAPCEKTPS.this,
                        "Informasi",
                        "Periksa Jaringan Internet anda!\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                        false, false, false,
                        "", "MENGERTI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                LAPCEKTPS.this.recreate();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {

                            }
                        }
                ).show();
            }
        });
    }

    private boolean isDataValid() {
        return !TextUtils.isEmpty(etFakta.getText()) &&
                !TextUtils.isEmpty(etAnalisa.getText()) &&
                !TextUtils.isEmpty(etRekomendasi.getText()) &&
                !PREDIKSI1.equals("") &&
                !PREDIKSI2.equals("") &&
                !PREDIKSI3.equals("") &&
                !spFakta1.equals("PILIH JAWABAN") &&
                !spFakta2.equals("PILIH JAWABAN") &&
                !spAnalisa1.equals("PILIH JAWABAN");
    }

    @Override
    public void onBackPressed() {
        if (formCekTps.getVisibility() == View.VISIBLE) {
            CDialog.up(this,
                    "Konfirmasi",
                    "Laporan belum anda kirim, jika anda kembali maka data yang sudah anda input akan hilang...\nLanjutkan?",
                    true, false, false,
                    "BATAL",
                    "LANJUTKAN",
                    "",
                    new CDialog.AlertDialogListener() {
                        @Override
                        public void onOpt1(AlertDialog alert) {
                            Intent intent = new Intent(LAPCEKTPS.this, JobSelector.class);
                            intent.putExtra("IDTPS", IDTPS);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onOpt2(AlertDialog alert) {

                        }

                        @Override
                        public void onCancel(AlertDialog alert) {
                            alert.dismiss();
                        }
                    }
            ).show();
        }else{
            Intent intent = new Intent(LAPCEKTPS.this, JobSelector.class);
            intent.putExtra("IDTPS", IDTPS);
            startActivity(intent);
            finish();
        }
    }
}