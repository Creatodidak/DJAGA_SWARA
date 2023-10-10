package id.creatodidak.djaga_swara.LOGINNEW;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.creatodidak.djaga_swara.DASHBOARDNEW.NewDashboard;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.SessionData;

public class SetLogin2New extends AppCompatActivity {
    EditText newPin;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;
    BiometricManager biometricManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_login2_new);
        newPin = findViewById(R.id.newPin);
        newPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(SessionData.edituserdata(SetLogin2New.this, false, false, "PIN", newPin.getText().toString())){
                        setBiometric();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setBiometric() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            biometricManager = (BiometricManager) getSystemService(BIOMETRIC_SERVICE);
            if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {
                Toast.makeText(this, "Berhasil mengatur sidik jari!", Toast.LENGTH_SHORT).show();
                SessionData.edituserdata(SetLogin2New.this, true, true, "BIOMETRIC", "");
                finishing();
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPrompt.Builder(SetLogin2New.this)
                    .setTitle("Scan sidik jari untuk mempermudah login kedepannya!")
                    .setNegativeButton("Tidak perlu sidik jari", getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(SetLogin2New.this, "Sidik Jari Tidak Diatur!", Toast.LENGTH_SHORT).show();
                            SessionData.edituserdata(SetLogin2New.this, true, false, "BIOMETRIC", "");
                            finishing();
                        }
                    }).build();

            authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    Toast.makeText(SetLogin2New.this, "Berhasil mengatur sidik jari!", Toast.LENGTH_SHORT).show();
                    SessionData.edituserdata(SetLogin2New.this, true, true, "BIOMETRIC", "");
                    finishing();
                }

                @Override
                public void onAuthenticationFailed() {
                    Toast.makeText(SetLogin2New.this, "Gagal mengatur sidik jari!", Toast.LENGTH_SHORT).show();
                    SessionData.edituserdata(SetLogin2New.this, true, false, "BIOMETRIC", "");
                    finishing();
                }
            };

            CancellationSignal cancellationSignal = new CancellationSignal();
            biometricPrompt.authenticate(cancellationSignal, getMainExecutor(), authenticationCallback);
        }
    }

    private void finishing() {
        Intent intent = new Intent(this, NewDashboard.class);
        startActivity(intent);
        finish();
    }
}