package id.creatodidak.djaga_swara.Dashboard.TugasForm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;

public class Hasilsuara extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasilsuara);
        databaseHelper = new DatabaseHelper(this);
        String text = getIntent().getStringExtra("type");
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            text = text.substring(1, text.length() - 1);
            text = text.replaceAll("\\s+", "").replaceAll("-", "").replaceAll("\"", "");

            String[] array = text.split(",");

            GridLayout gridLayout = findViewById(R.id.subtugas);

            for (int i = 0; i < array.length; i++) {
                String item = array[i];
                Log.i("TYPE LIST", item);
                int drawableId = getDrawableId(item);
                if (drawableId != 0) {
                    final ImageView imageView = new ImageView(this);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 0;
                    params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                    params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                    imageView.setLayoutParams(params);

                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageResource(drawableId);
                    imageView.setBackgroundColor(Color.TRANSPARENT);
                    imageView.setPadding(10, 10, 10, 10);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (databaseHelper.ceksuara(item.toLowerCase().trim(), getIntent().getStringExtra("id_tps"))) {
                                Intent intent = new Intent(Hasilsuara.this, FormInputSuara.class);
                                intent.putExtra("type", item);
                                intent.putExtra("id_tps", getIntent().getStringExtra("id_tps"));
                                intent.putExtra("namatps", getIntent().getStringExtra("namatps"));
                                startActivity(intent);
                            } else {
                                notifikasi("INFO", "Anda sudah mengisi data ini!", true, false);
                            }
                        }
                    });
                    gridLayout.addView(imageView);
                }
            }
        }
    }

    private int getDrawableId(String item) {
        switch (item) {
            case "PRESIDEN":
                return R.drawable.lgpresiden;
            case "DPRRI":
                return R.drawable.lgdprri;
            case "DPDRI":
                return R.drawable.lgdpdri;
            case "GUBERNUR":
                return R.drawable.lggubernur;
            case "DPRDPROV":
                return R.drawable.lgdprdprov;
            case "BUPATI":
                return R.drawable.lgbupati;
            case "DPRDKAB":
                return R.drawable.lgdprdkab;
            case "KADES":
                return R.drawable.lgkades;
            default:
                return 0;
        }
    }

    private void notifikasi(String info, String s, Boolean cancel, Boolean close) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Hasilsuara.this);
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

}
