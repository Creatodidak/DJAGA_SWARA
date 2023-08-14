package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.DPRDKab;
import id.creatodidak.djaga_swara.R;

public class DPRDKabAdapter extends RecyclerView.Adapter<DPRDKabAdapter.ViewHolder> {
    private final List<DPRDKab> dprdKabList;
    private final Context context;

    public DPRDKabAdapter(Context context, List<DPRDKab> dprdKabList) {
        this.dprdKabList = dprdKabList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DPRDKab dprdKab = dprdKabList.get(position);
        holder.itemView.setTag(dprdKab.getId());
        holder.nourut.setText(dprdKab.getNomorurut());
        holder.namacalon.setText(dprdKab.getNama());
    }

    @Override
    public int getItemCount() {
        return dprdKabList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nourut, namacalon;
        EditText suara;

        public ViewHolder(View itemView) {
            super(itemView);
            nourut = itemView.findViewById(R.id.tvNourut);
            namacalon = itemView.findViewById(R.id.tvNama);
            suara = itemView.findViewById(R.id.etSuara);
        }
    }
}
