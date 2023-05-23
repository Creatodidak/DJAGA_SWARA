package id.creatodidak.djaga_swara.API.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.ListIzin;
import id.creatodidak.djaga_swara.R;

public class IzinAdapter extends RecyclerView.Adapter<IzinAdapter.ViewHolder> {
    private final List<ListIzin> listIzin;

    public IzinAdapter(List<ListIzin> listIzin) {
        this.listIzin = listIzin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.persetujuan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListIzin izin = listIzin.get(position);

        holder.textView.setText(izin.getNumber());
        holder.isi.setText(izin.getText());
    }

    @Override
    public int getItemCount() {
        return listIzin.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, isi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.numberTextView);
            isi = itemView.findViewById(R.id.textView);
        }
    }
}