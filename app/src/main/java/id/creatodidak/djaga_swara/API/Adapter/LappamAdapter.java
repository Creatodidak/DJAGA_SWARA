package id.creatodidak.djaga_swara.API.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Draft.Lappam;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;

public class LappamAdapter extends RecyclerView.Adapter<LappamAdapter.ViewHolder> {
    private List<Lappam> LappamList;

    public LappamAdapter(List<Lappam> LappamList) {
        this.LappamList = LappamList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoImageView;
        TextView judul, tanggal;
        LinearLayout data;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImageView = itemView.findViewById(R.id.fotoDraft);
            judul = itemView.findViewById(R.id.judulDraft);
            tanggal = itemView.findViewById(R.id.tanggalDraft);
            data = itemView.findViewById(R.id.datadraft);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draft, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lappam Lappam = LappamList.get(position);
        DatabaseHelper databaseHelper = new DatabaseHelper(holder.itemView.getContext());
        TpsList tpsList = databaseHelper.getSprindetail(Lappam.getId_tps());

        holder.judul.setText("Laporan Pengecekan TPS " + tpsList.getNomorTps() + " Desa " +tpsList.getNamaDes());
        holder.tanggal.setText(Lappam.getCreated_at());
        Glide.with(holder.itemView.getContext())
                .load(Lappam.getFoto())
                .into(holder.fotoImageView);

        TextView situasiTextView = new TextView(holder.itemView.getContext());
        TextView prediksiTextView = new TextView(holder.itemView.getContext());
        situasiTextView.setText("SITUASI:\n" + Lappam.getSituasi());
        prediksiTextView.setText("PREDIKSI GANGGUAN KAMTIBMAS:\n" + Lappam.getPrediksi());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16);

        situasiTextView.setLayoutParams(layoutParams);
        prediksiTextView.setLayoutParams(layoutParams);
        holder.data.addView(situasiTextView);
        holder.data.addView(prediksiTextView);
    }

    @Override
    public int getItemCount() {
        return LappamList.size();
    }
}
