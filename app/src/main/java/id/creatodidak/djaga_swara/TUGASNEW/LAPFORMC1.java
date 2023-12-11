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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.NEWADAPTER.FORMC1ADP;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFormC1;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.DASHBOARDNEW.JobSelector;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.DATATYPEMANAGER;
import id.creatodidak.djaga_swara.plugin.PhotoResult;
import id.creatodidak.djaga_swara.plugin.ProgressUpload;
import id.creatodidak.djaga_swara.plugin.RandomStringGenerator;
import id.creatodidak.djaga_swara.plugin.SessionData;
import id.creatodidak.djaga_swara.plugin.UPDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LAPFORMC1 extends AppCompatActivity {
    String IDTPS;
    RecyclerView rvData;
    SharedPreferences sh;
    FORMC1ADP adp;
    List<MFormC1> data = new ArrayList<>();
    LinearLayoutManager lm;
    DBHelper dbHelper;
    ActivityResultLauncher<Intent> opencamera, openresult, opengaleri;
    String listFoto = "";
    String PHOTO_PATH = "";
    int typeOn;
    File storageDir;
    Endpoint endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapformc1);
        Intent intent = getIntent();
        dbHelper = new DBHelper(this);
        IDTPS = intent.getStringExtra("IDTPS");
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        rvData = findViewById(R.id.rvData);
        lm = new LinearLayoutManager(this);
        storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DJAGASWARA");
        endpoint = ApiClient.getClient().create(Endpoint.class);
        
        List<String> type = DATATYPEMANAGER.listType(this);
        for(String ty : type){
            MFormC1 lastData = dbHelper.getFormC1(IDTPS, ty);
            if(lastData == null){
                MFormC1 newdata = new MFormC1();
                newdata.setType(ty);
                data.add(newdata);
            }else{
                data.add(lastData);
            }

        }
        adp = new FORMC1ADP(this, IDTPS, data, new FORMC1ADP.OnItemClickListener() {
            @Override
            public void onClick(int Pos) {
                typeOn = Pos;
                CDialog.up(LAPFORMC1.this,
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

            @Override
            public void kirimDraft(String idTps, String type) {
                uploadToServer(idTps, type);
            }
        });
        rvData.setAdapter(adp);
        rvData.setLayoutManager(lm);

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
                        listFoto = String.valueOf(uri);
                        renewImage(uri);
                    }
                }
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
    }

    private void renewImage(Uri uri) {
        if(dbHelper.saveFormC1(IDTPS, data.get(typeOn).getType(), uri)){
            uploadToServer(IDTPS, data.get(typeOn).getType());
        }
    }

    private void uploadToServer(String idtps, String type) {
        AlertDialog alerts = UPDialog.up(this, "0%");
        alerts.show();
        
        MFormC1 datas = dbHelper.getFormC1(idtps, type);
        Uri uri = Uri.parse(datas.getDokumentasi());
        File img = new File(uri.getPath()).getAbsoluteFile();
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
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", img.getName(), countingRequestBody);


        Call<MResponseServer> call = endpoint.uploadFormC1(idtps, type, imagePart);
        
        call.enqueue(new Callback<MResponseServer>() {
            @Override
            public void onResponse(Call<MResponseServer> call, Response<MResponseServer> response) {
                if (response.body().isStatus() && response.body() != null && response.isSuccessful()) {
                    if (dbHelper.updStatusFormC1(idtps, type)) {
                        alerts.dismiss();
                        CDialog.up(
                                LAPFORMC1.this,
                                "Informasi",
                                "Form C1 Berhasil Diupload!",
                                false, false, false,
                                "", "LANJUTKAN", "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {
                                        alert.dismiss();
                                        LAPFORMC1.this.recreate();
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
                            LAPFORMC1.this,
                            "Informasi",
                            "Server Tidak Meresponse!\n\nCoba Untuk Periksa Jaringan Internet anda...\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                            false, false, false,
                            "", "MENGERTI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    LAPFORMC1.this.recreate();
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
                        LAPFORMC1.this,
                        "Informasi",
                        "Periksa Jaringan Internet anda!\nData Tersimpan Di Draft, Jangan Lupa Untuk Mengirimnya Kembali Saat Sudah Mendapatkan Jaringan Internet Yang Lebih Baik.",
                        false, false, false,
                        "", "MENGERTI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                LAPFORMC1.this.recreate();
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

    private void showPreview(String photoPath) {

        Intent intent = new Intent(this, PhotoResult.class);
        intent.putExtra("photopath", photoPath);
        intent.putExtra("judul", "FORM C1 "+data.get(typeOn).getType()+" "+ dbHelper.getRincianTpsById(IDTPS).getNomorTps() + " Desa" + sh.getString("DESA TPS", "TIDAK DIKETAHUI"));
        intent.putExtra("koordinat", SessionData.getNamaPangkat(LAPFORMC1.this).toUpperCase());
        intent.putExtra("storage", storageDir.getAbsolutePath());
        openresult.launch(intent);
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

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(LAPFORMC1.this, JobSelector.class);
            intent.putExtra("IDTPS", IDTPS);
            startActivity(intent);
            finish();
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