package id.creatodidak.djaga_swara.plugin;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import id.creatodidak.djaga_swara.R;

public class UPDialog {
    private static AlertDialog currentDialog;
    private static TextView dPesan;

    public static AlertDialog up(Context context, String pesan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.progressupload, null);

        dPesan = view.findViewById(R.id.dPesan);
        dPesan.setText(pesan);

        builder.setCancelable(false);
        currentDialog = builder.create(); // Simpan objek AlertDialog yang baru dibuat
        currentDialog.setView(view, 50, 0, 50, 0);
        Objects.requireNonNull(currentDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        return currentDialog;
    }

    public static void updateProgressText(String progressText) {
        dPesan.setText(progressText);
    }
}
