package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.Gubernur;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.FormInputSuara;
import id.creatodidak.djaga_swara.R;

public class GubernurAdapter extends RecyclerView.Adapter<GubernurAdapter.ViewHolder> {
    private List<Gubernur> gubernurList;

    public GubernurAdapter(FormInputSuara formInputSuara, List<Gubernur> gubernurList) {
        this.gubernurList = gubernurList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gubernur gubernur = gubernurList.get(position);
        holder.itemView.setTag(gubernur.getId());
        holder.nourut.setText(gubernur.getNo_urut());
        holder.namacalon.setText(gubernur.getCagub()+"\n"+gubernur.getCawagub());
    }

    @Override
    public int getItemCount() {
        return gubernurList.size();
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
