package id.creatodidak.djaga_swara.Login;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.creatodidak.djaga_swara.Helper.Enkripsi;
import id.creatodidak.djaga_swara.R;

public class Blocked extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    EditText kode, unblock;
    Button btnSalin, btnUnblock;
    String blockedkode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked);

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        blockedkode = sharedPreferences.getString("token", "");

        kode = findViewById(R.id.kode);
        unblock = findViewById(R.id.unblock);
        btnSalin = findViewById(R.id.btnSalinkode);
        btnUnblock = findViewById(R.id.btnUnblocking);

        kode.setText(blockedkode);

        btnSalin.setOnClickListener(this);
        btnUnblock.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSalinkode:
                copyToClipboard(blockedkode);
                break;

            case R.id.btnUnblocking:
                String enc = Enkripsi.hashSHA256(blockedkode);
                if (unblock.getText().toString().isEmpty()){
                    Toast.makeText(this, "KODE KOSONG!", Toast.LENGTH_SHORT).show();
                }else {
                    if (enc.equals(unblock.getText().toString())) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("blocked", false);
                        editor.remove("token");
                        editor.remove("PIN");
                        editor.apply();

                        Intent intent = new Intent(Blocked.this, SetLoginPin.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(this, "KODE SALAH!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void copyToClipboard(String blockedkode) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", blockedkode);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Kode berhasil disalin!", Toast.LENGTH_SHORT).show();
    }
}