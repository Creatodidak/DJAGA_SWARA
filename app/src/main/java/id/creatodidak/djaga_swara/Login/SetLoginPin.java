package id.creatodidak.djaga_swara.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.creatodidak.djaga_swara.Dashboard.Sprin;
import id.creatodidak.djaga_swara.R;

public class SetLoginPin extends AppCompatActivity implements View.OnClickListener {

    private EditText etPin1, etPin2, etPin3, etPin4;

    private String PIN1, PIN2, PIN3, PIN4;
    private TextView pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, pin9, pin0;

    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_login_pin);

            etPin1 = findViewById(R.id.etPin1);
            etPin2 = findViewById(R.id.etPin2);
            etPin3 = findViewById(R.id.etPin3);
            etPin4 = findViewById(R.id.etPin4);

            PIN1 = etPin1.getText().toString();
            PIN2 = etPin2.getText().toString();
            PIN3 = etPin3.getText().toString();
            PIN4 = etPin4.getText().toString();

            pin1 = findViewById(R.id.pin1);
            pin2 = findViewById(R.id.pin2);
            pin3 = findViewById(R.id.pin3);
            pin4 = findViewById(R.id.pin4);
            pin5 = findViewById(R.id.pin5);
            pin6 = findViewById(R.id.pin6);
            pin7 = findViewById(R.id.pin7);
            pin8 = findViewById(R.id.pin8);
            pin9 = findViewById(R.id.pin9);
            pin0 = findViewById(R.id.pin10);

            pin1.setOnClickListener(this);
            pin2.setOnClickListener(this);
            pin3.setOnClickListener(this);
            pin4.setOnClickListener(this);
            pin5.setOnClickListener(this);
            pin6.setOnClickListener(this);
            pin7.setOnClickListener(this);
            pin8.setOnClickListener(this);
            pin9.setOnClickListener(this);
            pin0.setOnClickListener(this);
            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pin10:
                insertpin("0");
                break;
            case R.id.pin1:
                insertpin("1");
                break;
            case R.id.pin2:
                insertpin("2");
                break;
            case R.id.pin3:
                insertpin("3");
                break;
            case R.id.pin4:
                insertpin("4");
                break;
            case R.id.pin5:
                insertpin("5");
                break;
            case R.id.pin6:
                insertpin("6");
                break;
            case R.id.pin7:
                insertpin("7");
                break;
            case R.id.pin8:
                insertpin("8");
                break;
            case R.id.pin9:
                insertpin("9");
                break;
        }
    }

    private void insertpin(String nilai) {
        if (PIN1.isEmpty()) {
            etPin1.setText(nilai);
            PIN1 = nilai;
        } else if (PIN2.isEmpty()) {
            etPin2.setText(nilai);
            PIN2 = nilai;
        } else if (PIN3.isEmpty()) {
            etPin3.setText(nilai);
            PIN3 = nilai;
        } else if (PIN4.isEmpty()) {
            etPin4.setText(nilai);
            PIN4 = nilai;

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("PIN", PIN1+PIN2+PIN3+PIN4);
            editor.putBoolean("login", true);
            editor.putInt("kesempatan", 0);
            editor.apply();

            Toast.makeText(SetLoginPin.this, "PIN berhasil disimpan", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SetLoginPin.this, Sprin.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}