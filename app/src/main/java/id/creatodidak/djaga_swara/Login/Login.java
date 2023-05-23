package id.creatodidak.djaga_swara.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.ValidationLoginResponse;
import id.creatodidak.djaga_swara.Dashboard.Sprin;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.RandomStringGenerator;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final String SESSION_NAME = "session";
    private static final String LOGIN = "login";
    private static final String BLOCKED = "blocked";
    private static final String TOKEN = "token";

    private SharedPreferences sharedPreferences;

    private EditText nrp, password;

    private DatabaseHelper databaseHelper;
    private int loginAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(SESSION_NAME, MODE_PRIVATE);
        loginAttempts = sharedPreferences.getInt("kesempatan", 0);

        boolean isLogin = sharedPreferences.getBoolean(LOGIN, false);
        boolean isBlocked = sharedPreferences.getBoolean(BLOCKED, false);

        databaseHelper = new DatabaseHelper(Login.this);

        nrp = findViewById(R.id.nrp);
        password = findViewById(R.id.password);

        Button btnLogin = findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(this);

        if (isBlocked) {
            Intent intent = new Intent(Login.this, Blocked.class);
            startActivity(intent);
            finish();
        } else if (isLogin) {
            Intent intent = new Intent(Login.this, Loginpin.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginBtn) {
            String pass = password.getText().toString();
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Gagal mengambil token!", Toast.LENGTH_SHORT).show();
                    }

                    String token = task.getResult();
                    validateLogin(nrp.getText().toString(), pass, token);
                }
            });
        }
    }

    private void validateLogin(String nrp, String password, String token) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ValidationLoginResponse> call = apiService.loginUser(nrp, password, token);
        call.enqueue(new Callback<ValidationLoginResponse>() {
            @Override
            public void onResponse(Call<ValidationLoginResponse> call, Response<ValidationLoginResponse> response) {
                if (response.isSuccessful()) {
                    ValidationLoginResponse validationResponse = response.body();
                    if (validationResponse != null && validationResponse.getMessage().equals("BERHASIL LOGIN!")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(LOGIN, true);
                        editor.putBoolean(BLOCKED, false);
                        editor.putInt("kesempatan", 0);
                        editor.putString("nrp", nrp);
                        editor.apply();

                        Intent intent = new Intent(Login.this, SetLoginPin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        onLoginFailure();
                    }
                } else {
                    onLoginFailure();
                }
            }

            @Override
            public void onFailure(Call<ValidationLoginResponse> call, Throwable t) {
                String errorMessage = t.getLocalizedMessage();
                showErrorDialog(errorMessage);
            }

            private void showErrorDialog(String errorMessage) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Error");
                builder.setMessage(errorMessage);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void onLoginFailure() {
        loginAttempts++;
        if (loginAttempts >= 3) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(BLOCKED, true);
            editor.putString(TOKEN, RandomStringGenerator.generateRandomString(20));
            editor.apply();

            Intent intent = new Intent(Login.this, Blocked.class);
            startActivity(intent);
            finish();
        } else {
            int sisa = 3 - loginAttempts;
            Toast.makeText(this, "NRP / PASSWORD SALAH!\nKesempatan sisa " + sisa + " Kali", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("kesempatan", loginAttempts);
            editor.apply();
        }
    }
}