package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.Bupati;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.FormInputSuara;
import id.creatodidak.djaga_swara.R;

public class BupatiAdapter extends RecyclerView.Adapter<BupatiAdapter.ViewHolder> {
    private List<Bupati> bupatiList;

    public BupatiAdapter(FormInputSuara formInputSuara, List<Bupati> bupatiList) {
        this.bupatiList = bupatiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bupati bupati = bupatiList.get(position);
        holder.itemView.setTag(bupati.getId());
        holder.nourut.setText(bupati.getNo_urut());
        holder.namacalon.setText(bupati.getCabup()+"\n"+bupati.getCawabup());
    }

    @Override
    public int getItemCount() {
        return bupatiList.size();
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
