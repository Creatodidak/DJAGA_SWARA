package id.creatodidak.djaga_swara;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import id.creatodidak.djaga_swara.Login.Login;

public class Izin extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
    ColorStateList disabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);

        b1 = findViewById(R.id.btn1);
        b2 = findViewById(R.id.btn2);
        b3 = findViewById(R.id.btn3);
        b4 = findViewById(R.id.btn4);
        b5 = findViewById(R.id.btn5);
        b6 = findViewById(R.id.btn6);
        b7 = findViewById(R.id.btn7);
        b8 = findViewById(R.id.btn8);
        b9 = findViewById(R.id.btn9);
        b10 = findViewById(R.id.btn10);
        disabled = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.disabled));

        cekallperm();
    }

    private void cekallperm() {
        if (!checkPermission(Manifest.permission.INTERNET)) {
            b1.setText("BERIKAN IZIN AKSES INTERNET");
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestInternetPermission();
                }
            });
        } else {
            b1.setText("AKSES INTERNET DIBERIKAN");
            b1.setBackgroundTintList(disabled);
            b1.setEnabled(false);
        }

        if (!checkPermission(Manifest.permission.CAMERA)) {
            b2.setText("BERIKAN IZIN PENGGUNAAN KAMERA");
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestCameraPermission();
                }
            });
        } else {
            b2.setText("AKSES KAMERA DIBERIKAN");
            b2.setBackgroundTintList(disabled);
            b2.setEnabled(false);
        }

        if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            b3.setText("BERIKAN IZIN AKSES LOKASI");
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestCoarseLocationPermission();
                }
            });
        } else {
            b3.setText("AKSES LOKASI DIBERIKAN");
            b3.setBackgroundTintList(disabled);
            b3.setEnabled(false);
        }

        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            b4.setText("BERIKAN IZIN AKSES LOKASI2");
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestFineLocationPermission();
                }
            });
        } else {
            b4.setText("AKSES LOKASI DIBERIKAN");
            b4.setBackgroundTintList(disabled);
            b4.setEnabled(false);
        }


        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            if (!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                b5.setText("BERIKAN AKSES BACA PENYIMPANAN");
                b5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAndRequestReadExternalStoragePermission();
                    }
                });
            } else {
                b5.setText("AKSES BACA PENYIMPANAN DIBERIKAN");
                b5.setBackgroundTintList(disabled);
                b5.setEnabled(false);
            }

            if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                b6.setText("BERIKAN AKSES MENULIS PENYIMPANAN");
                b6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAndRequestWriteExternalStoragePermission();
                    }
                });
            } else {
                b6.setText("AKSES MENULIS PENYIMPANAN DIBERIKAN");
                b6.setBackgroundTintList(disabled);
                b6.setEnabled(false);
            }
        } else {
            b6.setVisibility(View.GONE);
            if (!checkPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
                b5.setText("BERIKAN AKSES BACA PENYIMPANAN");
                b5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAndRequestManage();
                    }
                });
            } else {
                b5.setText("AKSES MANAJEMEN PENYIMPANAN DIBERIKAN");
                b5.setBackgroundTintList(disabled);
                b5.setEnabled(false);
            }
        }

        if (!checkPermission(Manifest.permission.USE_BIOMETRIC)) {
            b7.setText("BERIKAN IZIN SIDIK JARI");
            b7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestUseBiometricPermission();
                }
            });
        } else {
            b7.setText("AKSES SIDIK JARI DIBERIKAN");
            b7.setBackgroundTintList(disabled);
            b7.setEnabled(false);
        }

        if (!checkPermission(Manifest.permission.READ_CONTACTS)) {
            b8.setText("BERIKAN IZIN MEMBACA KONTAK");
            b8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestReadContactsPermission();
                }
            });
        } else {
            b8.setText("AKSES KONTAK DIBERIKAN");
            b8.setBackgroundTintList(disabled);
            b8.setEnabled(false);
        }

        if (!checkPermission(Manifest.permission.RECORD_AUDIO)) {
            b9.setText("BERIKAN IZIN MEREKAM AUDIO");
            b9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestRecordAudioPermission();
                }
            });
        } else {
            b9.setText("REKAM AUDIO DIBERIKAN");
            b9.setBackgroundTintList(disabled);
            b9.setEnabled(false);
        }

        if (!checkPermission(Manifest.permission.POST_NOTIFICATIONS)) {
            b10.setText("BERIKAN IZIN NOTIFIKASI");
            b10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndRequestNotification();
                }
            });
        } else {
            b10.setText("AKSES NOTIFIKASI DIBERIKAN");
            b10.setBackgroundTintList(disabled);
            b10.setEnabled(false);
        }

        if (checkAllPermissionsGranted()) {
            startLoginActivity();
        }
    }



    private boolean checkPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(this, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(this, "Izin diperlukan untuk fitur ini.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    private void checkAndRequestInternetPermission() {
        String permission = Manifest.permission.INTERNET;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestManage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Izin.this);
            builder.setTitle("Berikan akses penyimpanan seperti dibawah ini...");
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int screenWidth = size.x;

            ImageView imageView = new ImageView(Izin.this);
            imageView.setImageResource(R.drawable.intruksi);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // Mengatur jenis skala gambar
            imageView.setAdjustViewBounds(true); // Mengatur agar ImageView menyesuaikan ukuran gambar
            imageView.setMaxHeight(600); // Mengatur tinggi maksimum ImageView
            imageView.setMaxWidth(screenWidth);
            builder.setView(imageView);

            builder.setPositiveButton("BERI IZIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void checkAndRequestCameraPermission() {
        String permission = Manifest.permission.CAMERA;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestCoarseLocationPermission() {
        String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestFineLocationPermission() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestReadExternalStoragePermission() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        Toast.makeText(Izin.this, String.valueOf(checkPermission(permission)), Toast.LENGTH_SHORT).show();
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestWriteExternalStoragePermission() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestUseBiometricPermission() {
        String permission = Manifest.permission.USE_BIOMETRIC;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestReadContactsPermission() {
        String permission = Manifest.permission.READ_CONTACTS;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestRecordAudioPermission() {
        String permission = Manifest.permission.RECORD_AUDIO;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private void checkAndRequestNotification() {
        String permission = Manifest.permission.POST_NOTIFICATIONS;
        if (!checkPermission(permission)) {
            requestPermission(permission);
        }
    }

    private boolean checkAllPermissionsGranted() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            String[] permissions = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };

            for (String permission : permissions) {
                if (!checkPermission(permission)) {
                    return false;
                }
            }
            return true;
        } else {
            if (Environment.isExternalStorageManager()) {
                String[] permissions = {
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.POST_NOTIFICATIONS
                };

                for (String permission : permissions) {
                    if (!checkPermission(permission)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cekallperm();
            }
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(Izin.this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    b5.setBackgroundTintList(disabled);
                    b5.setEnabled(false);
                } else {
                    Toast.makeText(this, "IZIN PENYIMPANAN WAJIB DIBERIKAN!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}