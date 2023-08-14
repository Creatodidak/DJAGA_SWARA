package id.creatodidak.djaga_swara.Dashboard;

import static id.creatodidak.djaga_swara.Helper.AESHelper.decrypt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.Fungsi;
import id.creatodidak.djaga_swara.API.Models.Jabatan;
import id.creatodidak.djaga_swara.API.Models.Pangkat;
import id.creatodidak.djaga_swara.API.Models.Profile;
import id.creatodidak.djaga_swara.API.Models.Satker;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.API.Models.UpdateFoto;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class First extends AppCompatActivity implements View.OnClickListener {

    ImageView fpset;

    EditText eTnrp , eTnama , eTwa;
    Spinner sPpangkat , sPsatker , sPfungsi , sPjabatan;

    Button btEdit, btSimpan, btLanjutkan;
    Boolean isSaved;
    ProgressDialog progressDialog, progressDialog2;

    SharedPreferences sharedPreferences;

    ApiService apiService;

    String Jpangkat, Jsatker, Jfungpolres, JfungPolsek, JjabPolres, JjabPolsek, JjabPolsubsektor;

    List<Pangkat> Pangkats = new ArrayList<>();
    List<Satker> Satkers = new ArrayList<>();

    List<Fungsi> Fungsis = new ArrayList<>();
    List<Jabatan> Jabatans = new ArrayList<>();

    LinearLayout gantifoto;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private String currentPhotoPath;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            Jpangkat = "pangkat.json";
            Jsatker = "satker.json";
            Jfungpolres = "satfung-polres.json";
            JfungPolsek = "satfung-polsek.json";
            JjabPolres = "jabatan-polres.json";
            JjabPolsek = "jabatan-polsek.json";
            JjabPolsubsektor = "jabatan-polsubsektor.json";
            addPangkat(Jpangkat);
            addSatker(Jsatker);
            fpset = findViewById(R.id.fpset);
            sPpangkat = findViewById(R.id.spPangkat);
            sPsatker = findViewById(R.id.spSatker);
            sPfungsi = findViewById(R.id.spFungsi);
            sPjabatan = findViewById(R.id.spJabatan);
            eTnrp = findViewById(R.id.etNrp);
            eTnama = findViewById(R.id.etNama);
            eTwa = findViewById(R.id.etWa);
            btEdit = findViewById(R.id.btnEdit);
            btSimpan = findViewById(R.id.btnSimpan);
            btLanjutkan = findViewById(R.id.btnLanjut);

            sPpangkat.setEnabled(false);
            sPsatker.setEnabled(false);
            sPfungsi.setEnabled(false);
            sPjabatan.setEnabled(false);

            btEdit.setOnClickListener(this);
            btSimpan.setOnClickListener(this);
            btLanjutkan.setOnClickListener(this);

            isSaved = true;

            gantifoto = findViewById(R.id.gantifoto);

            gantifoto.setOnClickListener(this);

            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            String nrp = sharedPreferences.getString("nrp", "");
            if (isInternetAvailable()) {
                progressDialog2 = new ProgressDialog(this);
                progressDialog2.setMessage("Mengambil data...");
                progressDialog2.setCancelable(false);
                progressDialog2.setContentView(R.layout.progress_dialog);
                progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                ImageView iconImageView = progressDialog2.findViewById(R.id.iconImageView);
                iconImageView.setImageResource(R.drawable.logosmall); // Ganti 'ic_progress' dengan sumber daya ikon yang diinginkan

                progressDialog2.show();

                apiService = ApiClient.getClient().create(ApiService.class);

                Call<Profile> call = apiService.getProfile(nrp);
                call.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, @NonNull Response<Profile> response) {
                        assert response.body() != null;
                        progressDialog2.dismiss();
                        eTnrp.setText(decrypt(response.body().getNrp()));
                        eTnama.setText(decrypt(response.body().getNama()));
                        eTwa.setText(decrypt(response.body().getWa()));
                        String selectedPangkat = decrypt(response.body().getPangkat());
                        setSpinnerSelection(sPpangkat, selectedPangkat, "pangkat");
                        String selectedSatker = decrypt(response.body().getSatker());
                        setSpinnerSelection(sPsatker, selectedSatker, "satker");
                        String selFungsi = decrypt(response.body().getSatfung());
                        String selJabatan = decrypt(response.body().getJabatan());

                        String foto = decrypt(response.body().getFoto());
                        if (foto.equals("no")) {
                            Glide.with(First.this)
                                    .load(R.drawable.defaultfp)
                                    .circleCrop()
                                    .into(fpset);
                        } else {
                            Glide.with(First.this)
                                    .load(foto)
                                    .circleCrop()
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(First.this, "FOTO BELUM ADA!", Toast.LENGTH_SHORT).show();
                                                    Glide.with(First.this)
                                                            .load(R.drawable.defaultfp)
                                                            .circleCrop()
                                                            .into(fpset);
                                                }
                                            });
                                            return true;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    })
                                    .into(fpset);
                        }
                        assert selectedSatker != null;
                        if (selectedSatker.contains("POLRES")) {
                            addFungsi("polres");
                            addJabatan("polres");
                            setSpinnerSelection(sPfungsi, selFungsi, "fungsi");
                            setSpinnerSelection(sPjabatan, selJabatan, "jabatan");
                        } else if (selectedSatker.contains("POLSEK")) {
                            addFungsi("polsek");
                            addJabatan("polres");
                            setSpinnerSelection(sPfungsi, selFungsi, "fungsi");
                            setSpinnerSelection(sPjabatan, selJabatan, "jabatan");
                        } else if (selectedSatker.contains("POLSUBSEKTOR")) {
                            addFungsi("polsubsektor");
                            addJabatan("polres");
                            setSpinnerSelection(sPfungsi, selFungsi, "fungsi");
                            setSpinnerSelection(sPjabatan, selJabatan, "jabatan");
                        }


                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        progressDialog2.dismiss();

                        Glide.with(First.this)
                                .load(R.drawable.defaultfp)
                                .circleCrop()
                                .into(fpset);
                    }
                });

                ArrayAdapter<Pangkat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Pangkats);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.sPpangkat.setAdapter(adapter);

                ArrayAdapter<Satker> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Satkers);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.sPsatker.setAdapter(adapter2);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Maaf");
                builder.setMessage("Fungsi ini hanya bisa digunakan pada saat internet tersedia");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });


                // Menampilkan dialog
                builder.show();
            }
        }
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    private void setSpinnerSelection(Spinner spinner, String selectedItem, String jenis) {
        if (jenis.equals("pangkat")){
            ArrayAdapter<Pangkat> adapter = (ArrayAdapter<Pangkat>) spinner.getAdapter();
            if (adapter != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    Pangkat pangkat = adapter.getItem(i);
                    if (pangkat != null && pangkat.getSingkat().equals(selectedItem)) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        } else if (jenis.equals("satker")) {
            ArrayAdapter<Satker> adapter = (ArrayAdapter<Satker>) spinner.getAdapter();
            if (adapter != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    Satker satker = adapter.getItem(i);
                    if (satker != null && satker.getNama().equals(selectedItem)) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        } else if (jenis.equals("fungsi")) {
            ArrayAdapter<Fungsi> adapter = (ArrayAdapter<Fungsi>) spinner.getAdapter();
            if (adapter != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    Fungsi fungsi = adapter.getItem(i);
                    if (fungsi != null && fungsi.getSatfung().equals(selectedItem)) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        } else if (jenis.equals("jabatan")) {
            ArrayAdapter<Jabatan> adapter = (ArrayAdapter<Jabatan>) spinner.getAdapter();
            if (adapter != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    Jabatan jabatan = adapter.getItem(i);
                    if (jabatan != null && jabatan.getJabatan().equals(selectedItem)) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void addPangkat(String jpangkat) {
        String jsonString = readJSONFromAsset(jpangkat);
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                Pangkat judul = new Pangkat("0", "PANGKAT", "PANGKAT");
                Pangkats.add(judul);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String longName = jsonObject.getString("lengkap");
                    String shortName = jsonObject.getString("singkat");
                    Pangkat pangkat = new Pangkat(id, longName, shortName);

                    Pangkats.add(pangkat);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addSatker(String jsatker) {
        String jsonString = readJSONFromAsset(jsatker);
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                Satker judul = new Satker("0", "SATKER", "SATKER", "WILAYAH");
                Satkers.add(judul);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String nama = jsonObject.getString("nama");
                    String type = jsonObject.getString("type");
                    String wilayah = jsonObject.getString("wilayah");

                    Satker satker = new Satker(id, nama, type, wilayah);

                    Satkers.add(satker);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addFungsi(String jenis) {
        String jsonString = readJSONFromAsset("satfung-polres.json");
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                Fungsi judul = new Fungsi(0, "SATFUNG", "SATFUNG");
                Fungsis.add(judul);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String satker = jsonObject.getString("satker");

                    // Hanya menambahkan satfung jika satker-nya sesuai
                    if (satker.equalsIgnoreCase(jenis)) {
                        int id = jsonObject.getInt("id");
                        String nama = jsonObject.getString("satfung");
                        String satkers = jsonObject.getString("satker");

                        Fungsi fungsi = new Fungsi(id, nama, satkers);
                        Fungsis.add(fungsi);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            ArrayAdapter<Fungsi> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Fungsis);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.sPfungsi.setAdapter(adapter3);
        }
    }

    private void addJabatan(String jenis) {
        String jsonString = readJSONFromAsset("jabatan-polres.json");
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                Jabatan jabatan = new Jabatan(0, "JABATAN", "JABATAN", "JABATAN");
                Jabatans.add(jabatan);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String satker = jsonObject.getString("satker");

                    // Hanya menambahkan satfung jika satker-nya sesuai
                    if (satker.equalsIgnoreCase(jenis)) {
                        int id = jsonObject.getInt("id");
                        String nama = jsonObject.getString("jabatan");
                        String level = jsonObject.getString("level");
                        String satkers = jsonObject.getString("satker");

                        Jabatan jabatan2 = new Jabatan(id, nama, level, satkers);

                        Jabatans.add(jabatan2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            ArrayAdapter<Jabatan> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Jabatans);
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.sPjabatan.setAdapter(adapter4);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEdit:
                eTnama.setEnabled(true);
                eTwa.setEnabled(true);
                sPpangkat.setEnabled(true);
                sPsatker.setEnabled(true);
                sPfungsi.setEnabled(true);
                sPjabatan.setEnabled(true);
                btLanjutkan.setVisibility(View.GONE);
                btEdit.setVisibility(View.GONE);
                btSimpan.setVisibility(View.VISIBLE);
                sPsatker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Satker selected = (Satker) parent.getSelectedItem();
                        String selectedSatker = selected.getNama();

                        if(selectedSatker.contains("POLRES")){
                            Fungsis.clear();
                            Jabatans.clear();
                            addFungsi("polres");
                            addJabatan("polres");
                        }else if(selectedSatker.contains("POLSEK")){
                            Fungsis.clear();
                            Jabatans.clear();
                            addFungsi("polsek");
                            addJabatan("polsek");
                        }else if(selectedSatker.contains("POLSUBSEKTOR")){
                            Fungsis.clear();
                            Jabatans.clear();
                            addFungsi("polsubsektor");
                            addJabatan("polsubsektor");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case R.id.btnSimpan:
                eTnama.setEnabled(false);
                eTwa.setEnabled(false);
                sPpangkat.setEnabled(false);
                sPsatker.setEnabled(false);
                sPfungsi.setEnabled(false);
                sPjabatan.setEnabled(false);

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Memproses Data...");
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                ImageView iconImageView = progressDialog.findViewById(R.id.iconImageView);
                iconImageView.setImageResource(R.drawable.logosmall); // Ganti 'ic_progress' dengan sumber daya ikon yang diinginkan

                progressDialog.show();

                saveData(eTnrp.getText().toString(), eTnama.getText().toString(), eTwa.getText().toString(), sPpangkat.getSelectedItem().toString(), sPsatker.getSelectedItem().toString(), sPfungsi.getSelectedItem().toString(), sPjabatan.getSelectedItem().toString());

                break;
            case R.id.btnLanjut:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("firstcheck", true);
                editor.apply();
                Intent intent = new Intent(First.this, Sprin.class);
                startActivity(intent);
                finish();
                break;
            case R.id.gantifoto:
                showImagePickerDialog();
                break;
        }
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Gambar")
                .setItems(new String[]{"Kamera", "Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            dispatchCameraIntent();
                        } else {
                            dispatchGalleryIntent();
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dispatchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void dispatchCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void handleCameraResult(Intent data) {
        File file = new File(currentPhotoPath);
        Uri photoUri = Uri.fromFile(file);

        // Prepare the image file for uploading
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loadings)
                .circleCrop()
                .into(fpset);

        // Make the upload request
        String nrp = sharedPreferences.getString("nrp", "");
        Call<UpdateFoto> call = apiService.uploadImage(nrp, imagePart);
        call.enqueue(new Callback<UpdateFoto>() {
            @Override
            public void onResponse(Call<UpdateFoto> call, Response<UpdateFoto> response) {
                if (response.isSuccessful()) {
                    recreate();
                    Toast.makeText(First.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateFoto> call, Throwable t) {
                Toast.makeText(First.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Simpan foto ke penyimpanan internal
    private void saveImageToStorage(Bitmap bitmap) {
        String fileName = "fotoprofil.jpg";
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE | Context.MODE_APPEND);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            loadImageFromStorage(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadImageFromStorage(String fileName) {
        try {
            File file = new File(getFilesDir(), fileName);
            Glide.with(this)
                    .load(file)
                    .circleCrop()
                    .into(fpset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGalleryResult(Intent data) {
        if (data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    String imagePath = cursor.getString(column_index);

                    // Handle the image file obtained from the gallery
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        // Prepare the image file for uploading
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        Glide.with(this)
                                .asGif()
                                .load(R.drawable.loadings)
                                .circleCrop()
                                .into(fpset);

                        // Make the upload request
                        String nrp = sharedPreferences.getString("nrp", "");
                        Call<UpdateFoto> call = apiService.uploadImage(nrp, imagePart);
                        call.enqueue(new Callback<UpdateFoto>() {
                            @Override
                            public void onResponse(Call<UpdateFoto> call, Response<UpdateFoto> response) {
                                if (response.isSuccessful()) {
                                    // Simpan foto ke penyimpanan internal
                                    recreate();
                                    Toast.makeText(First.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateFoto> call, Throwable t) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(First.this);
                                builder.setTitle("ERROR");
                                builder.setMessage(t.getLocalizedMessage());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Kode yang akan dijalankan ketika tombol "OK" diklik
                                        // ...
                                    }
                                });
                                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Kode yang akan dijalankan ketika tombol "Batal" diklik
                                        // ...
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        });
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                handleCameraResult(data);
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                handleGalleryResult(data);
            }
        }
    }

    private void saveData(String NRP, String NAMA, String WA, String PANGKAT, String SATKER, String FUNGSI, String JABATAN) {

        Call<UpdResponse> upd = apiService.updateProfil(NRP, NAMA, WA, PANGKAT, SATKER, FUNGSI, JABATAN);
        upd.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                if (response.isSuccessful() && response.body().getMsg().equals("Update Berhasil!")) {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(First.this);
                    builder.setTitle("INFO")
                            .setMessage("Update Berhasil!")
                            .setIcon(R.drawable.logosmall)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("firstcheck", true);
                                    editor.apply();
                                    Intent intent = new Intent(First.this, Sprin.class);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                            .show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(First.this);
                    builder.setTitle("INFO")
                            .setMessage("Update Gagal!")
                            .setIcon(R.drawable.logosmall)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UpdResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(First.this);
                builder.setTitle("INFO")
                        .setMessage(t.getLocalizedMessage())
                        .setIcon(R.drawable.logosmall)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private String readJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}