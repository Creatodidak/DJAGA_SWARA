package id.creatodidak.djaga_swara.TUGASNEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MLapPamTPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.DASHBOARDNEW.JobSelector;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
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

public class LAPPAMTPS extends AppCompatActivity {
    String IDTPS;
    DBHelper dbHelper;
    Endpoint endpoint;
    EditText etFakta;
    EditText etJumlahOrang;
    EditText etAnalisa;
    EditText etRekomendasi;
    EditText etPrediksi;
    LinearLayout btTambahDokumentasi, listDokumentasi;
    Button btKirimData;
    ActivityResultLauncher<Intent> opencamera, openresult, opengaleri;
    File storageDir;
    String PHOTO_PATH;
    String listFoto = "";
    SharedPreferences sh;
    CardView formPamTps, data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lappamtps);
        Intent intent = getIntent();
        IDTPS = intent.getStringExtra("IDTPS");
        dbHelper = new DBHelper(this);
        storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DJAGASWARA");
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);

        etFakta = findViewById(R.id.etFakta);
        etJumlahOrang = findViewById(R.id.etJumlahOrang);
        etAnalisa = findViewById(R.id.etAnalisa);
        etRekomendasi = findViewById(R.id.etRekomendasi);
        etPrediksi = findViewById(R.id.etPrediksi);
        btTambahDokumentasi = findViewById(R.id.btTambahDokumentasi);
        btKirimData = findViewById(R.id.btKirimData);
        listDokumentasi = findViewById(R.id.listDokumentasi);
        formPamTps = findViewById(R.id.formPamTps);
        data = findViewById(R.id.data);

        opencamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                showPreview(PHOTO_PATH);
            }
        });
        opengaleri = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    String imagePath = getRealPathFromUri(selectedImageUri);
                    if (imagePath != null) {
                        showPreview(imagePath);
                    }
                }
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

        String status = dbHelper.cekLappamtps(IDTPS);
        if (status.equals("BELUM ADA")) {
            showform();
        } else if (status.equals("LOCAL")) {
            showdata(true);
        } else if (status.equals("SERVER")) {
            showdata(false);
        }

        btTambahDokumentasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CDialog.up(LAPPAMTPS.this,
                        "Konfirmasi",
                        "Pilih Sumber Dokumentasi",
                        true, true, false,
                        "BATAL",
                        "KAMERA",
                        "GALERI",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                showCamera();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {
                                showGaleri();
                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });

        btKirimData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataValid() && !listFoto.equals("")) {
                    CDialog.up(
                            LAPPAMTPS.this,
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
                            LAPPAMTPS.this,
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

    private void saveDataToDB() {
        String FAKTA = "Jumlah masyarakat di sekitar TPS rata - rata berjumlah ±"+etJumlahOrang.getText().toString()+" orang.\n\n"+etFakta.getText().toString();

        String ANALISA = etAnalisa.getText().toString();
        String PREDIKSI = etPrediksi.getText().toString();
        String REKOMENDASI = etRekomendasi.getText().toString();
        String FOTO = listFoto;

        if (dbHelper.saveLapPamTPS(IDTPS, FAKTA, ANALISA, PREDIKSI, REKOMENDASI, FOTO)) {
            tryUploadData();
        }
    }

    private boolean isDataValid() {
        return !TextUtils.isEmpty(etFakta.getText()) &&
                !TextUtils.isEmpty(etAnalisa.getText()) &&
                !TextUtils.isEmpty(etPrediksi.getText()) &&
                !TextUtils.isEmpty(etRekomendasi.getText()) &&
                !TextUtils.isEmpty(etJumlahOrang.getText());
    }

    private void showform() {
        formPamTps.setVisibility(View.VISIBLE);
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
        intent.putExtra("judul", "PENGAMANAN TPS " + dbHelper.getRincianTpsById(IDTPS).getNomorTps() + " Desa" + sh.getString("DESA TPS", "TIDAK DIKETAHUI"));
        intent.putExtra("koordinat", SessionData.getNamaPangkat(LAPPAMTPS.this).toUpperCase());
        intent.putExtra("storage", storageDir.getAbsolutePath());
        openresult.launch(intent);
    }

    private void tryUploadData() {
        AlertDialog alerts = UPDialog.up(this, "0%");
        alerts.show();

        MLapPamTPS data = dbHelper.getLapPAMTPS(IDTPS);
        String FAKTA = data.getFakta();
        String ANALISA = data.getAnalisa();
        String PREDIKSI = data.getPrediksi();
        String REKOMENDASI = data.getRekomendasi();
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

        Call<MResponseServer> call = endpoint.uploadLaporanPamTPS(IDTPS, FAKTA, ANALISA, PREDIKSI, REKOMENDASI, imageParts);
        call.enqueue(new Callback<MResponseServer>() {
            @Override
            public void onResponse(Call<MResponseServer> call, Response<MResponseServer> response) {
                if (response.body().isStatus() && response.body() != null && response.isSuccessful()) {
                    if (dbHelper.updStatusPamTPS(IDTPS)) {
                        alerts.dismiss();
                        CDialog.up(
                                LAPPAMTPS.this,
                                "Informasi",
                                "Laporan Berhasil Diupload!",
                                false, false, false,
                                "", "LANJUTKAN", "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {
                                        alert.dismiss();
                                        LAPPAMTPS.this.recreate();
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
                            LAPPAMTPS.this,
                            "Informasi",
                            "Server Tidak Meresponse!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                            false, false, false,
                            "", "MENGERTI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    LAPPAMTPS.this.recreate();
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
                        LAPPAMTPS.this,
                        "Informasi",
                        "Periksa Jaringan Internet anda!\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                        false, false, false,
                        "", "MENGERTI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                LAPPAMTPS.this.recreate();
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

    private void showdata(boolean showbuttondraft)  {
        CardView datacektps = findViewById(R.id.data);
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

        MLapPamTPS data = dbHelper.getLapPAMTPS(IDTPS);
        String FAKTA = TextHelper.numberingData(data.getFakta());
        String ANALISA = TextHelper.numberingData(data.getAnalisa());
        String PREDIKSI = TextHelper.numberingData(data.getPrediksi());
        String REKOMENDASI = TextHelper.numberingData(data.getRekomendasi());
        String[] FOTO = data.getDokumentasi().split(",");
        String pre = "Kepada: Yth. Kapolres Landak\nDari: Pers Pam TPS " + dbHelper.getRincianTpsById(IDTPS).getNomorTps() + " Desa " + TextHelper.capitalize(sh.getString("DESA TPS", "TIDAK DIKETAHUI")) + "\n\n" + WaktuLokal.deteksiWaktu() + " Komandan, izin melaporkan kegiatan Pengamanan Proses Pemilu di TPS " + dbHelper.getRincianTpsById(IDTPS).getNomorTps() + " Desa " + TextHelper.capitalize(sh.getString("DESA TPS", "TIDAK DIKETAHUI")) + " oleh " + TextHelper.capitalize(SessionData.getuserdata(this).getPangkat() + " " + SessionData.getuserdata(this).getNama()) + " sbb:\n";
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

    @Override
    public void onBackPressed() {
        if (formPamTps.getVisibility() == View.VISIBLE) {
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
                            Intent intent = new Intent(LAPPAMTPS.this, JobSelector.class);
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
            Intent intent = new Intent(LAPPAMTPS.this, JobSelector.class);
            intent.putExtra("IDTPS", IDTPS);
            startActivity(intent);
            finish();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            return uri.getPath(); // Fallback if the cursor is null
        }
    }

    private void showGaleri() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        opengaleri.launch(intent);
    }
}