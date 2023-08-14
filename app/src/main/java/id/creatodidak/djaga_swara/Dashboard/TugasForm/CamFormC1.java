package id.creatodidak.djaga_swara.Dashboard.TugasForm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import id.creatodidak.djaga_swara.API.Models.TpsList;
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

public class CamFormC1 extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    TpsList tps;
    ProgressDialog progressDialog;
    String type, idTps, nrp, types, stt;
    Button kirim;
    LinearLayout kamera, galeri, selectorfoto;
    ImageView fotonya, wm1;
    TextView wm2, wm3, headerc1, belumada;
    ConstraintLayout foto;
    SharedPreferences sharedPreferences;
    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private String currentPhotoPath;
    ConstraintSet constraintSet;
    Uri contentUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_form_c1);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            nrp = sharedPreferences.getString("nrp", "");
            type = getIntent().getStringExtra("type");
            idTps = getIntent().getStringExtra("id_tps");
            kirim = findViewById(R.id.uploadc1);
            kamera = findViewById(R.id.kamera);
            galeri = findViewById(R.id.galeri);
            foto = findViewById(R.id.fotoc1);
            belumada = findViewById(R.id.belumadafoto);
            selectorfoto = findViewById(R.id.selectorfoto);
            fotonya = findViewById(R.id.fotonyac1);
            wm1 = findViewById(R.id.wm1c1);
            wm2 = findViewById(R.id.wm2c1);
            wm3 = findViewById(R.id.wm3c1);
            headerc1 = findViewById(R.id.headerc1);
            kamera.setOnClickListener(this);
            galeri.setOnClickListener(this);
            kirim.setOnClickListener(this);
            constraintSet = new ConstraintSet();
            DatabaseHelper databaseHelper = new DatabaseHelper(CamFormC1.this);
            tps = databaseHelper.getSprindetail(idTps);

            if(type.equals("PRESIDEN")){
                types = "PEMILIHAN PRESIDEN";
            }else if(type.equals("GUBERNUR")){
                types = "PEMILIHAN GUBERNUR";
            }else if(type.equals("BUPATI")){
                types = "PEMILIHAN BUPATI / WALIKOTA";
            }else if(type.equals("KADES")){
                types = "PEMILIHAN KEPALA DESA";
            }else if(type.equals("DPRRI")){
                types = "PEMILIHAN DPR-RI";
            }else if(type.equals("DPDRI")){
                types = "PEMILIHAN DPD-RI";
            }else if(type.equals("DPRDPROV")){
                types = "PEMILIHAN DPRD PROVINSI";
            }else if(type.equals("DPRDKAB")){
                types = "PEMILIHAN DPRD "+tps.getNamaKab();
            }

            wm3.setText("FORM C1 "+types+"\nTPS "+tps.getNomorTps()+" DESA "+tps.getNamaDes()+"\n©DjagaSwara | POLRES LANDAK");
            headerc1.setText("FORM C1 "+types);

            galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    handleGalleryResult(data);
                }
            });

            cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    hadleCameraResult(data);
                }
            });
        }
    }

    private void hadleCameraResult(Intent data){
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        if (bitmap != null) {
            Glide.with(this)
                    .load(currentPhotoPath)
                    .into(fotonya);

            wm1.setVisibility(View.VISIBLE);
            wm2.setVisibility(View.VISIBLE);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(currentPhotoPath, options);
            int width = options.outWidth;
            int height = options.outHeight;
            int fpb = findGreatestCommonDivisor(width, height);
            int simplifiedWidth = width / fpb;
            int simplifiedHeight = height / fpb;
            String ratio = String.format(Locale.getDefault(), "%d:%d", simplifiedWidth, simplifiedHeight);
            constraintSet.clone(foto);
            constraintSet.setDimensionRatio(R.id.fotonya, ratio);
            constraintSet.applyTo(foto);

            selectorfoto.setVisibility(View.GONE);
            kirim.setVisibility(View.VISIBLE);
            wm2.setText(nrp);
            belumada.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadc1:
                kirimdata();
                break;
            case R.id.kamera:
                opencamera();
                break;
            case R.id.galeri:
                opengaleri();
                break;
        }
    }

    private void kirimdata() {
        showprogress("MENYIMPAN FORM C1");
        Bitmap bitmap = getBitmapFromView(foto);
        if(saveBitmapToGallery(bitmap)){
            progressDialog.dismiss();
            showprogress("MENGUPLOAD FORM C1");
            uploadImage(bitmap);
        }else{
            progressDialog.dismiss();
            notifikasi("GAGAL!", "GAGAL MENYIMPAN GAMBAR FORM C1, PROSES UPLOAD TIDAK AKAN DILUNCURKAN!", true, false);
        }
    }

    private void opengaleri() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }


    private void opencamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(this,
                            getApplicationContext().getPackageName() + ".provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    cameraLauncher.launch(takePictureIntent);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = idTps + "_" + timeStamp + "_";
        File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Djaga Swara/Form C1");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File imageFile = new File(storageDir, imageFileName + ".jpg");
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }


    private void handleGalleryResult(Intent data) {
        if (data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    String imagePath = cursor.getString(column_index);

                    // Handle the image file obtained from the gallery
                    if (imagePath != null) { // Check if imagePath is not null
                        currentPhotoPath = imagePath; // Update currentPhotoPath with the selected image path

                        File imageFile = new File(imagePath);
                        Uri imageUri = Uri.fromFile(imageFile);
                        Glide.with(this)
                                .load(imageUri)
                                .into(fotonya);

                        wm1.setVisibility(View.VISIBLE);
                        wm2.setVisibility(View.VISIBLE);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(currentPhotoPath, options);
                        int width = options.outWidth;
                        int height = options.outHeight;
                        int fpb = findGreatestCommonDivisor(width, height);
                        int simplifiedWidth = width / fpb;
                        int simplifiedHeight = height / fpb;
                        String ratio = String.format(Locale.getDefault(), "%d:%d", simplifiedWidth, simplifiedHeight);
                        constraintSet.clone(foto);
                        constraintSet.setDimensionRatio(R.id.fotonya, ratio);
                        constraintSet.applyTo(foto);

                        selectorfoto.setVisibility(View.GONE);
                        kirim.setVisibility(View.VISIBLE);
                        wm2.setText(nrp);
                        belumada.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(this, "Image path is null", Toast.LENGTH_SHORT).show();
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
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

    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private boolean saveBitmapToGallery(Bitmap bitmap) {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Djaga Swara/Form C1");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        // Membuat file baru dengan nama unik
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = idTps + "_" + timeStamp + ".jpg";
        File imageFile = new File(storageDir, imageFileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

    private void uploadImage(Bitmap bitmap) {
        // Convert bitmap to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Create request body for image
        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UpdResponse> call = apiService.uploadFormC1(idTps, type, image);
        call.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                progressDialog.dismiss();
                UpdResponse updResponse = response.body();
                if (updResponse != null && updResponse.getMsg().equals("Upload Berhasil!")) {
                    Log.i("STATUS SERVER", "BERHASIL");
                    showprogress("UPDATE DATABASE LOCAL");
                    updatedb(idTps, type, "YES, ALL", contentUri);
                } else {
                    Log.i("STATUS SERVER", "GAGAL, BERHASIL AKSES");
                    showprogress("UPDATE DATABASE LOCAL");
                    updatedb(idTps, type, "NO, LOCAL", contentUri);
                }
            }

            @Override
            public void onFailure(Call<UpdResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.i("STATUS SERVER", "GAGAL AKSES, GAGAL UPLOAD");
                showprogress("UPDATE DATABASE LOCAL");
                updatedb(idTps, type, "NO, LOCAL", contentUri);
            }
        });
    }

    private void updatedb(String idTps, String type, String status, Uri contentUri) {
        if(status.equals("YES, ALL")){
            stt = "SERVER";
        }else if(status.equals("NO, LOCAL")){
            stt = "LOCAL";
        }
        DatabaseHelper databaseHelper = new DatabaseHelper(CamFormC1.this);

        boolean add = databaseHelper.addFormc1(idTps, String.valueOf(contentUri), type, stt);

        if (add){
            progressDialog.dismiss();
            if(stt.equals("SERVER")){
                notifikasi("BERHASIL!", "FORM C1 "+types.toUpperCase()+" TPS "+tps.getNomorTps()+" DESA "+tps.getNamaDes()+" BERHASIL DI UPLOAD!", true, true);
            } else if (stt.equals("LOCAL")) {
                notifikasi("GAGAL!", "FORM C1 "+types.toUpperCase()+" TPS "+tps.getNomorTps()+" DESA "+tps.getNamaDes()+" GAGAL DI UPLOAD NAMUN TERSIMPAN DI DRAFT!", true, true);
            }
        }else{
            progressDialog.dismiss();
            if(stt.equals("SERVER")){
                notifikasi("BERHASIL!", "FORM C1 "+types.toUpperCase()+" TPS "+tps.getNomorTps()+" DESA "+tps.getNamaDes()+" BERHASIL DI UPLOAD NAMUN GAGAL MENGUPDATE DATABASE LOCAL!", true, true);
            } else if (stt.equals("LOCAL")) {
                notifikasi("GAGAL!", "FORM C1 "+types.toUpperCase()+" TPS "+tps.getNomorTps()+" DESA "+tps.getNamaDes()+" GAGAL DI UPLOAD DAN TIDAK TERSIMPAN DI DRAFT!", true, true);
            }
        }
    }

    private void showprogress(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void notifikasi(String info, String s, Boolean cancel, Boolean close) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CamFormC1.this);
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
}