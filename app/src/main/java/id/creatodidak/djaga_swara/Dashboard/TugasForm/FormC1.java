package id.creatodidak.djaga_swara.Dashboard.TugasForm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import id.creatodidak.djaga_swara.R;

public class FormC1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_c1);

        String text = getIntent().getStringExtra("type");

        text = text.substring(1, text.length() - 1);
        text = text.replaceAll("\\s+", "").replaceAll("-", "").replaceAll("\"", "");

        String[] array = text.split(",");

        GridLayout gridLayout = findViewById(R.id.subtugas);

        for (int i = 0; i < array.length; i++) {
            String item = array[i];
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
                        // Aksi yang ingin Anda lakukan saat ImageView diklik
                        // Misalnya, tampilkan pesan Toast
                        Toast.makeText(FormC1.this, item, Toast.LENGTH_SHORT).show();
                    }
                });
                gridLayout.addView(imageView);
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
}
