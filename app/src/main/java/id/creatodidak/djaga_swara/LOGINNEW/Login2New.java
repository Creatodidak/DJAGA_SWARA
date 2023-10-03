package id.creatodidak.djaga_swara.LOGINNEW;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.creatodidak.djaga_swara.API.NEWMODEL.LogindetailItem;
import id.creatodidak.djaga_swara.DASHBOARDNEW.NewDashboard;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.SessionData;

public class Login2New extends AppCompatActivity {

    ImageView sidikjari;
    EditText pin;
    LogindetailItem data;
    TextView ket;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;
    BiometricManager biometricManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2_new);
        data = SessionData.getuserdata(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        sidikjari = findViewById(R.id.sidikjari);
        pin = findViewById(R.id.Pin);
        ket = findViewById(R.id.textView7);

        pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pin.getText().length() == 4){
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(pin.getWindowToken(), 0);
                    }

                    if(data.getPin() != null){
                        if(data.getPin().equals(pin.getText().toString())){
                            Intent intent = new Intent(Login2New.this, NewDashboard.class);
                            startActivity(intent);
                            finish();
                        }else{
                            CDialog.up(
                                    Login2New.this,
                                    "Informasi",
                                    "Pin Salah!",
                                    false, false, false,
                                    "",
                                    "ULANGI",
                                    "",
                                    new CDialog.AlertDialogListener() {
                                        @Override
                                        public void onOpt1(AlertDialog alert) {
                                            imm.showSoftInput(pin, 0);
                                            pin.setText("");
                                            pin.requestFocus();
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
                    }else{
                        CDialog.up(
                                Login2New.this,
                                "Informasi",
                                "Anda belum mengatur PIN!\nAnda harus menginstall ulang aplikasi ini dan login kembali!",
                                false, false, false,
                                "",
                                "MENGERTI",
                                "",
                                new CDialog.AlertDialogListener() {
                                    @Override
                                    public void onOpt1(AlertDialog alert) {alert.dismiss();finish();}

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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(!SessionData.isUserBiometricExist(Login2New.this)) {
            ket.setVisibility(View.GONE);
            sidikjari.setVisibility(View.GONE);
        }

        sidikjari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(pin.getWindowToken(), 0);
                }
                pin.setText("");

                showSidikJari();
            }
        });
    }

    private void showSidikJari() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            biometricManager = (BiometricManager) getSystemService(BIOMETRIC_SERVICE);
            if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {
                finishing();
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPrompt.Builder(Login2New.this)
                    .setTitle("Silahkan scan sidik jari!")
                    .setNegativeButton("BATAL", getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).build();

            authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    finishing();
                }

                @Override
                public void onAuthenticationFailed() {
                    Toast.makeText(Login2New.this, "Sidik Jari Tidak Cocok!", Toast.LENGTH_SHORT).show();
                }
            };

            CancellationSignal cancellationSignal = new CancellationSignal();
            biometricPrompt.authenticate(cancellationSignal, getMainExecutor(), authenticationCallback);
        }
    }

    private void finishing() {
        Intent intent = new Intent(Login2New.this, NewDashboard.class);
        startActivity(intent);
        finish();
    }
}