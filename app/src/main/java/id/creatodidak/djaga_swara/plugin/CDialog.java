package id.creatodidak.djaga_swara.plugin;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

import id.creatodidak.djaga_swara.R;

public class CDialog {
    private static AlertDialog currentDialog;
    private static LinearLayout konfirmasi;
    private static TextView dJudul;
    private static TextView dPesan;
    private static TextView dBtBatal;
    private static TextView dBtOpt2;
    private static TextView dBtOpt1;
    private static ProgressBar dProgress;
    private static View dv1;
    private static View dv2;
    private static View dv3;

    public static AlertDialog up(Context context, String judul, String pesan, boolean cancel, boolean opt2, boolean pb, String btBatal, String btOpt1, String btOpt2, AlertDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.customdialog, null);

        konfirmasi = view.findViewById(R.id.konfirmasi);

        dJudul = view.findViewById(R.id.dJudul);
        dPesan = view.findViewById(R.id.dPesan);
        dBtBatal = view.findViewById(R.id.dBtBatal);
        dBtOpt2 = view.findViewById(R.id.dBtOpt2);
        dBtOpt1 = view.findViewById(R.id.dBtOpt1);
        dProgress = view.findViewById(R.id.dProgress);
        dv1 = view.findViewById(R.id.dv1);
        dv2 = view.findViewById(R.id.dv2);
        dv3 = view.findViewById(R.id.dv3);

        dJudul.setText(judul);
        dPesan.setText(pesan);
        dBtBatal.setText(btBatal);
        dBtOpt1.setText(btOpt1);
        dBtOpt2.setText(btOpt2);

        if(pb){
            dPesan.setVisibility(View.GONE);
            dv3.setVisibility(View.GONE);
            dProgress.setVisibility(View.VISIBLE);
            konfirmasi.setVisibility(View.GONE);
        }else{
            dProgress.setVisibility(View.GONE);
        }

        if(!cancel){
            dBtBatal.setVisibility(View.GONE);
            dv1.setVisibility(View.GONE);
        }

        if(!opt2){
            dBtOpt2.setVisibility(View.GONE);
            dv2.setVisibility(View.GONE);
        }

        builder.setCancelable(false);
        currentDialog = builder.create(); // Simpan objek AlertDialog yang baru dibuat
        currentDialog.setView(view, 50, 0, 50, 0);
        Objects.requireNonNull(currentDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        dBtBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(currentDialog);
            }
        });

        dBtOpt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOpt1(currentDialog);
            }
        });

        dBtOpt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOpt2(currentDialog);
            }
        });

        return currentDialog;
    }

    public interface AlertDialogListener {
        void onOpt1(AlertDialog alert);
        void onOpt2(AlertDialog alert);
        void onCancel(AlertDialog alert);
    }
}
