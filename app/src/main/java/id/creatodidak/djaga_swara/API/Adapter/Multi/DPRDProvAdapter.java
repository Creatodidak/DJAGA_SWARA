package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.DPRDProv;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.FormInputSuara;
import id.creatodidak.djaga_swara.R;

public class DPRDProvAdapter extends RecyclerView.Adapter<DPRDProvAdapter.ViewHolder> {
    private List<DPRDProv> dprdProvList;

    public DPRDProvAdapter(FormInputSuara formInputSuara, List<DPRDProv> dprdProvList) {
        this.dprdProvList = dprdProvList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DPRDProv dprdProv = dprdProvList.get(position);
        holder.itemView.setTag(dprdProv.getId());
        holder.nourut.setText(dprdProv.getNomorurut());
        holder.namacalon.setText(dprdProv.getNama());
    }

    @Override
    public int getItemCount() {
        return dprdProvList.size();
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
