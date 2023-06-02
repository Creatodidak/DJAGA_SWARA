package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.DPRRI;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.FormInputSuara;
import id.creatodidak.djaga_swara.R;

public class DprriAdapter extends RecyclerView.Adapter<DprriAdapter.ViewHolder> {
    private List<DPRRI> dprriList;

    public DprriAdapter(FormInputSuara formInputSuara, List<DPRRI> dprriList) {
        this.dprriList = dprriList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DPRRI dprri = dprriList.get(position);
        holder.itemView.setTag(dprri.getId());
        holder.nourut.setText(dprri.getNomorurut());
        holder.namacalon.setText(dprri.getNama());
    }

    @Override
    public int getItemCount() {
        return dprriList.size();
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
