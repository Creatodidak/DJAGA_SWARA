package id.creatodidak.djaga_swara.LOGINNEW;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import id.creatodidak.djaga_swara.API.Adapter.Client;
import id.creatodidak.djaga_swara.API.Interface.EndpointVRS;
import id.creatodidak.djaga_swara.API.NEWMODEL.MLogin;
import id.creatodidak.djaga_swara.Login.SetLoginPin;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;
import id.creatodidak.djaga_swara.plugin.SessionData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginNew extends AppCompatActivity {

    EndpointVRS endpointVRS;
    EditText nrp, pass;
    Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        nrp = findViewById(R.id.nrp);
        pass = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginBtn);
        endpointVRS = Client.getClient().create(EndpointVRS.class);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(nrp.getText()) && !TextUtils.isEmpty(pass.getText())){
                    proseslogin(nrp.getText().toString(), pass.getText().toString());
                    nrp.clearFocus();
                    pass.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);
                    }
                }else{
                    CDialog.up(
                            LoginNew.this,
                            "Peringatan",
                            "NRP/Password tidak boleh kosong!",
                            false, false, false,
                            "",
                            "PERBAIKI",
                            "",
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

        if(SessionData.isUserLoggedIn(this)){
            Intent intent;
            if(SessionData.isUserPinExist(this)){
                intent = new Intent(this, Login2New.class);
            }else{
                intent = new Intent(this, SetLogin2New.class);
            }
            startActivity(intent);
            finish();
        }
    }

    private void proseslogin(String nrp, String pass) {
        AlertDialog alerts = CDialog.up(
                LoginNew.this,
                "Mencoba login...",
                "",
                false, false, true,
                "",
                "",
                "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {}

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );


        alerts.show();
        Call<MLogin> call = endpointVRS.login(nrp, pass);
        call.enqueue(new Callback<MLogin>() {
            @Override
            public void onResponse(Call<MLogin> call, Response<MLogin> response) {
                alerts.dismiss();
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    SessionData.saveuserdata(LoginNew.this, response.body().getLogindetail().get(0));
                    Intent intent = new Intent(LoginNew.this, SetLogin2New.class);
                    startActivity(intent);
                    finish();
                }else{
                    CDialog.up(
                            LoginNew.this,
                            "Informasi",
                            "Gagal login, periksa NRP / Password!",
                            false, false, false,
                            "",
                            "ULANGI",
                            "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {alert.dismiss();}

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
            public void onFailure(Call<MLogin> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        LoginNew.this,
                        "Informasi",
                        "Gagal login, silahkan periksa jaringan internet!",
                        false, false, false,
                        "",
                        "MENGERTI",
                        "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {alert.dismiss();}

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
}