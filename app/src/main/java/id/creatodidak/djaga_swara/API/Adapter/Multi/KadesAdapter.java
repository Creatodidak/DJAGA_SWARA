package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.Kades;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.FormInputSuara;
import id.creatodidak.djaga_swara.R;

public class KadesAdapter extends RecyclerView.Adapter<KadesAdapter.ViewHolder> {
    private List<Kades> kadesList;

    public KadesAdapter(FormInputSuara formInputSuara, List<Kades> kadesList) {
        this.kadesList = kadesList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kades kades = kadesList.get(position);
        holder.itemView.setTag(kades.getId());
        holder.nourut.setText(kades.getNo_urut());
        holder.namacalon.setText(kades.getCakades());
    }

    @Override
    public int getItemCount() {
        return kadesList.size();
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
